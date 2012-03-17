package li.cryx.minecraft.death.persist.flat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import li.cryx.minecraft.death.persist.AbstractPersistManager;
import li.cryx.minecraft.death.persist.FragsInfo;
import li.cryx.minecraft.util.LivingEntityType;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Persist plugin data to flat files (YAML format). There is a mechanism to
 * store complete <code>ItemStack</code>s using YAML. (ItemStack = item instance
 * in game world.)
 * 
 * <p>
 * This implementation will create one YML file per dead player. The contents of
 * the file will be kept in memory for 5 minutes before it is unloaded. After
 * that The YML structure has to be rebuild from the persisted file. This is
 * done to prevent memory leaks.
 * </p>
 * 
 * @author cryxli
 */
public class PersistenceFlatFile extends AbstractPersistManager {

	/** How often to save files [ms]. Defaults to every 10s. */
	private static final long SAVE_INTERVAL = 10 * 1000;

	private static final int TIMEOUT_TICKS = (int) Math
			.ceil(5 * 60 * 1000 / SAVE_INTERVAL);

	private static final int DIRTY_TICK = 0;

	private static final int NOT_DIRTY_TICK = 1;

	private boolean autoSave = false;

	/** Folder that contains killing stats. */
	private final File countFolder;

	/** Folder that contains inventories. */
	private final File itemFolder;

	private final Map<Player, YamlConfiguration> kills = new HashMap<Player, YamlConfiguration>();

	private final Map<Player, Integer> timeout = new HashMap<Player, Integer>();

	public PersistenceFlatFile(final JavaPlugin plugin) {
		super(plugin);

		File rootFolder = plugin.getDataFolder();
		countFolder = new File(rootFolder, "stats");
		if (!countFolder.isDirectory() && !countFolder.mkdirs()) {
			plugin.getLogger().log(Level.SEVERE,
					"Failed to create stats folder");
		}
		itemFolder = new File(rootFolder, "inventory");
		if (!itemFolder.isDirectory() && !itemFolder.mkdirs()) {
			plugin.getLogger().log(Level.SEVERE,
					"Failed to create items folder");
		}

		// start storing interval
		Thread intervalStorage = new Thread() {
			@Override
			public void run() {
				while (autoSave) {
					flush();
					try {
						Thread.sleep(SAVE_INTERVAL);
					} catch (InterruptedException e) {
					}
				}
			}
		};
		autoSave = true;
		intervalStorage.setDaemon(true);
		intervalStorage.start();
	}

	@Override
	public void deleteItems(final Player player) {
		File file = itemsFile(player);
		if (!file.delete()) {
			plugin.getLogger().log(Level.SEVERE,
					"Unable to delete inventory for " + player.getName());
		}
	}

	public synchronized void flush() {
		List<Player> toUnload = new LinkedList<Player>();
		for (Player player : kills.keySet()) {
			YamlConfiguration data = getKills(player);
			int tick = timeout.get(player);
			if (tick == 0) {
				try {
					data.save(new File(countFolder, player.getName()
							.toLowerCase() + ".yml"));
					timeout.put(player, NOT_DIRTY_TICK);
				} catch (IOException e) {
					plugin.getLogger().log(Level.SEVERE,
							"Unable to persist kills", e);
				}
			} else if (tick == TIMEOUT_TICKS) {
				toUnload.add(player);
			} else {
				timeout.put(player, 1 + tick);
			}
		}
		for (Player player : toUnload) {
			kills.remove(player);
			timeout.remove(player);
			plugin.getLogger().fine("Unloaded " + player.getName());
		}
	}

	@Override
	public FragsInfo getFrags(final Player player) {
		return new FragsInfoFlatFile(getKills(player));
	}

	private synchronized YamlConfiguration getKills(final Player player) {
		YamlConfiguration kp = kills.get(player);
		if (kp == null) {
			kp = YamlConfiguration.loadConfiguration(new File(countFolder,
					player.getName().toLowerCase() + ".yml"));
			kills.put(player, kp);
			timeout.put(player, NOT_DIRTY_TICK);
		}
		return kp;
	}

	@Override
	public boolean hasInventory(final Player player) {
		return itemsFile(player).exists();
	}

	@Override
	public void increaseKilled(final Player player, final LivingEntityType type) {
		YamlConfiguration conf = getKills(player);

		String key;
		switch (type.getAffection()) {
		case AGGRESSIVE:
			key = FragsInfoFlatFile.KILLED_BY.AGGRO;
			break;
		default:
		case FRIENDLY:
			key = FragsInfoFlatFile.KILLED_BY.FRIEND;
			break;
		case NEUTRAL:
			key = FragsInfoFlatFile.KILLED_BY.NEUTRAL;
			break;
		case PVP:
			key = FragsInfoFlatFile.KILLED_BY.PVP;
			break;
		}

		conf.set(key, 1 + conf.getInt(key));
		timeout.put(player, DIRTY_TICK);
	}

	@Override
	public void increaseKills(final Player player, final LivingEntityType type) {
		YamlConfiguration conf = getKills(player);

		String key;
		switch (type.getAffection()) {
		case AGGRESSIVE:
			key = FragsInfoFlatFile.KILLED.AGGRO;
			break;
		default:
		case FRIENDLY:
			key = FragsInfoFlatFile.KILLED.FRIEND;
			break;
		case NEUTRAL:
			key = FragsInfoFlatFile.KILLED.FRIEND;
			break;
		case PVP:
			key = FragsInfoFlatFile.KILLED.PVP;
			break;
		}

		conf.set(key, 1 + conf.getInt(key));
		timeout.put(player, DIRTY_TICK);
	}

	/**
	 * Get the file containing the inventory for the given player.
	 * 
	 * @param player
	 *            Player in question
	 * @return A <code>File</code> object.
	 */
	private File itemsFile(final Player player) {
		return new File(itemFolder, player.getName().toLowerCase() + ".yml");
	}

	@Override
	public boolean persistItems(final Player player) {
		PlayerInventory inv = player.getInventory();
		// add inventory
		List<ItemStack> items = new LinkedList<ItemStack>();
		for (ItemStack item : inv.getContents()) {
			if (item != null && item.getType() != Material.AIR) {
				items.add(item);
			}
		}
		// add worn armor to list
		for (ItemStack item : inv.getArmorContents()) {
			if (item != null && item.getType() != Material.AIR) {
				items.add(item);
			}
		}

		try {
			File file = itemsFile(player);
			YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
			int counter = 1;
			for (ItemStack item : items) {
				data.set("item" + counter, item);
				counter++;
			}
			data.save(file);
			return true;
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE,
					"Cannot store player's inventory", e);
			return false;
		}
	}

	@Override
	public List<ItemStack> restoreItems(final Player player) {
		File file = new File(itemFolder, player.getName().toLowerCase()
				+ ".yml");
		YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
		plugin.getLogger().log(Level.INFO,
				"Loading inventory for " + player.getName());
		List<ItemStack> items = new LinkedList<ItemStack>();
		for (int i = 1; i <= 40; i++) {
			if (!data.isItemStack("item" + i)) {
				break;
			}
			items.add(data.getItemStack("item" + i));
		}
		return items;
	}

	@Override
	public void shutdown() {
		autoSave = false;
		flush();
	}
}
