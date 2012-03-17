package li.cryx.minecraft.death;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import li.cryx.minecraft.death.listener.BlockListener;
import li.cryx.minecraft.death.listener.DeathListener;
import li.cryx.minecraft.death.listener.PlayerInteractListener;
import li.cryx.minecraft.death.persist.AbstractPersistManager;
import li.cryx.minecraft.death.persist.FragsInfo;
import li.cryx.minecraft.death.persist.flat.PersistenceFlatFile;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of the plugin managing persistence and listeners.
 * 
 * @author cryxli
 */
public class Death extends JavaPlugin {

	/** Capture player deaths. */
	private DeathListener playerListener;

	/** Capture altar creation */
	private BlockListener blockListener;

	/** Capture prayers on altars. */
	private PlayerInteractListener altarListener;

	/** Remember locations of altars. */
	private final List<Location> altars = new LinkedList<Location>();

	/** Store inventories on death */
	private AbstractPersistManager persist;

	private Material altarMaterial;

	private Material altarBaseMaterial;

	/**
	 * Add an altar at the given location.
	 * 
	 * @param location
	 */
	public void addAltarLocation(final Location location) {
		System.out.println(location);

		if (location != null && !isAltar(location)) {
			altars.add(location);

			// store altar location
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("world", location.getWorld().getName());
			map.put("x", location.getBlockX());
			map.put("y", location.getBlockY());
			map.put("z", location.getBlockZ());

			FileConfiguration conf = getConfig();
			conf.set("altar" + altars.size(), map);
			saveConfig();
		}
	}

	private void createListeners() {
		playerListener = new DeathListener(this);
		blockListener = new BlockListener(this);
		altarListener = new PlayerInteractListener(this);

		// TODO Auto-generated method stub

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(playerListener, this);
		pm.registerEvents(blockListener, this);
		pm.registerEvents(altarListener, this);
	}

	public Material getAltarBaseMaterial() {
		return altarBaseMaterial;
	}

	public Material getAltarMaterial() {
		return altarMaterial;
	}

	public AbstractPersistManager getPersist() {
		return persist;
	}

	public boolean isAltar(final Location location) {
		if (location == null) {
			return false;
		}

		for (Location loc : altars) {
			if (loc.equals(location)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the stored altar locations.
	 */
	private void loadAltarLocations() {
		FileConfiguration conf = getConfig();

		// read first altar location
		int counter = 1;
		Object obj = conf.get("altar" + counter);
		while (obj != null) {
			String worldName = conf.getString("altar" + counter + ".world");
			World world = getServer().getWorld(worldName);
			if (world == null) {
				getLogger().log(Level.SEVERE, "World not found: " + worldName);
			} else {
				int x = conf.getInt("altar" + counter + ".x");
				int y = conf.getInt("altar" + counter + ".y");
				int z = conf.getInt("altar" + counter + ".z");
				altars.add(new Location(world, x, y, z));
			}

			// read next altar location
			counter++;
			obj = conf.get("altar" + counter);
		}
	}

	/**
	 * Get the configured altar block and altar base block materials.
	 * <p>
	 * Default altar block is {@link Material#ENDER_STONE}, default altar base
	 * block is {@link Material#OBSIDIAN}.
	 * </p>
	 */
	private void loadAltarMaterials() {
		FileConfiguration conf = getConfig();

		// try block name
		Material m = Material.matchMaterial(conf.getString("Material.Altar"));
		if (m == null) {
			// try block ID and set default
			m = Material.getMaterial(conf.getInt("Material.Altar",
					Material.ENDER_STONE.getId()));
		}
		altarMaterial = m;
		conf.set("Material.Altar", altarMaterial.toString());

		// try block name
		m = Material.matchMaterial(conf.getString("Material.AltarBase"));
		if (m == null) {
			// try block ID and set default
			m = Material.getMaterial(conf.getInt("Material.AltarBase",
					Material.OBSIDIAN.getId()));
		}
		altarBaseMaterial = m;
		conf.set("Material.AltarBase", altarBaseMaterial.toString());
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String label, final String[] args) {
		if (sender instanceof Player) {
			FragsInfo info = getPersist().getFrags((Player) sender);
			if ("full".equalsIgnoreCase(getConfig().getString("FragsDisplay"))) {
				sendFull(sender, info);
			} else {
				sendMinimal(sender, info);
			}
			return true;
		} else {
			return super.onCommand(sender, command, label, args);
		}
	}

	@Override
	public void onDisable() {
		persist.shutdown();
		persist = null;
		blockListener = null;
		altarListener = null;
		altars.clear();

		// TODO Auto-generated method stub

		getLogger().info(getDescription().getFullName() + " disabled");
	}

	@Override
	public void onEnable() {
		// create and process configuration
		FileConfiguration config = getConfig();
		config.options().copyDefaults(true);
		loadAltarLocations();
		loadAltarMaterials();
		saveConfig();

		createListeners();

		persist = new PersistenceFlatFile(this);

		// TODO Auto-generated method stub

		getLogger().info(getDescription().getFullName() + " enabled");
	}

	public void restoreItems(final Player player) {
		List<ItemStack> items = persist.restoreItems(player);
		if (items == null || items.size() == 0) {
			// nothing to recover
			return;
		}

		// player's location is 1.6 blocks above ground
		// so player will immediately collect drops
		Location location = player.getLocation();
		World world = player.getWorld();
		for (ItemStack item : items) {
			world.dropItem(location, item);
		}
		// prevent double collecting
		persist.deleteItems(player);
	}

	/** Send the complete list of kills and deaths to the player. */
	private void sendFull(final CommandSender sender, final FragsInfo info) {
		sender.sendMessage(ChatColor.GOLD + "===== Your kills =====");

		StringBuffer buf = new StringBuffer();
		buf.append(ChatColor.YELLOW);
		buf.append("PVP: ");
		buf.append(ChatColor.GREEN);
		buf.append(info.getPvpKills());
		buf.append(ChatColor.YELLOW);
		buf.append("/");
		buf.append(ChatColor.RED);
		buf.append(info.getPvpKillers());
		sender.sendMessage(buf.toString());

		buf = new StringBuffer();
		buf.append(ChatColor.YELLOW);
		buf.append("Aggressive mobs: ");
		buf.append(ChatColor.GREEN);
		buf.append(info.getAggroKills());
		buf.append(ChatColor.YELLOW);
		buf.append("/");
		buf.append(ChatColor.RED);
		buf.append(info.getAggroKillers());
		sender.sendMessage(buf.toString());

		buf = new StringBuffer();
		buf.append(ChatColor.YELLOW);
		buf.append("Neutral mobs: ");
		buf.append(ChatColor.GREEN);
		buf.append(info.getNeutralKills());
		buf.append(ChatColor.YELLOW);
		buf.append("/");
		buf.append(ChatColor.RED);
		buf.append(info.getNeutralKillers());
		sender.sendMessage(buf.toString());

		buf = new StringBuffer();
		buf.append(ChatColor.YELLOW);
		buf.append("Friendly mobs: ");
		buf.append(ChatColor.GREEN);
		buf.append(info.getFriendlyKills());
		buf.append(ChatColor.YELLOW);
		buf.append("/");
		buf.append(ChatColor.RED);
		buf.append(info.getFriendlyKillers());
		sender.sendMessage(buf.toString());
	}

	/** Send the minimalistic list of kills and deaths to the player. */
	private void sendMinimal(final CommandSender sender, final FragsInfo info) {
		int pvp = info.getPvpKills() - info.getPvpKillers();
		int aggro = info.getAggroKills() - info.getAggroKillers();
		int neutral = info.getNeutralKills() - info.getNeutralKillers();
		int friend = info.getFriendlyKills() - info.getFriendlyKillers();

		sender.sendMessage(ChatColor.GOLD + "===== Your kills =====");

		StringBuffer buf = new StringBuffer();
		buf.append(ChatColor.YELLOW);
		buf.append("PVP: ");
		if (pvp == 0) {
			buf.append(ChatColor.WHITE);
		} else if (pvp < 0) {
			buf.append(ChatColor.RED);
		} else {
			buf.append(ChatColor.GREEN);
		}
		buf.append(pvp);
		sender.sendMessage(buf.toString());

		buf = new StringBuffer();
		buf.append(ChatColor.YELLOW);
		buf.append("Aggressive mobs: ");
		if (aggro == 0) {
			buf.append(ChatColor.WHITE);
		} else if (aggro < 0) {
			buf.append(ChatColor.RED);
		} else {
			buf.append(ChatColor.GREEN);
		}
		buf.append(aggro);
		sender.sendMessage(buf.toString());

		buf = new StringBuffer();
		buf.append(ChatColor.YELLOW);
		buf.append("Neutral mobs: ");
		if (neutral == 0) {
			buf.append(ChatColor.WHITE);
		} else if (neutral < 0) {
			buf.append(ChatColor.RED);
		} else {
			buf.append(ChatColor.GREEN);
		}
		buf.append(neutral);
		sender.sendMessage(buf.toString());

		buf = new StringBuffer();
		buf.append(ChatColor.YELLOW);
		buf.append("Friendly mobs: ");
		if (friend == 0) {
			buf.append(ChatColor.WHITE);
		} else if (friend < 0) {
			buf.append(ChatColor.RED);
		} else {
			buf.append(ChatColor.GREEN);
		}
		buf.append(friend);
		sender.sendMessage(buf.toString());
	}
}
