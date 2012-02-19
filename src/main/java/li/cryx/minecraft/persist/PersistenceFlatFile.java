package li.cryx.minecraft.persist;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

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
 * @author cryxli
 */
// TODO test enchanted items
public class PersistenceFlatFile extends AbstractPersistManager {

	// private final File countFolder;

	/** Folder that contains inventories. */
	private final File itemFolder;

	public PersistenceFlatFile(final JavaPlugin plugin) {
		super(plugin);

		File rootFolder = plugin.getDataFolder();
		// countFolder = new File(rootFolder, "stats");
		// if (!countFolder.mkdirs()) {
		// plugin.getLogger().log(Level.SEVERE,
		// "Failed to create stats folder");
		// }
		itemFolder = new File(rootFolder, "inventory");
		if (!itemFolder.mkdirs()) {
			plugin.getLogger().log(Level.SEVERE,
					"Failed to create items folder");
		}
	}

	@Override
	public void deleteItems(final Player player) {
		File file = itemsFile(player);
		if (!file.delete()) {
			plugin.getLogger().log(Level.SEVERE,
					"Unable to delete inventory for " + player.getName());
		}
	}

	@Override
	public boolean hasInventory(final Player player) {
		return itemsFile(player).exists();
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
}
