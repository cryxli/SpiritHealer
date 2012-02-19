package li.cryx.minecraft.death;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import li.cryx.minecraft.persist.AbstractPersistManager;
import li.cryx.minecraft.persist.PersistenceFlatFile;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Death extends JavaPlugin {

	private DeathListener playerListener;

	private BlockListener blockListener;

	private PlayerInteractListener altarListener;

	private final List<Location> altars = new LinkedList<Location>();

	private AbstractPersistManager persist;

	/**
	 * Add an altar at the given location.
	 * 
	 * @param location
	 */
	public void addAlterLocation(final Location location) {
		System.out.println(location);

		if (location != null && !isAltar(location)) {
			altars.add(location);

			// TODO store altar location
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

	AbstractPersistManager getPersist() {
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
		FileConfiguration config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		loadAltarLocations();

		createListeners();

		persist = new PersistenceFlatFile(this);

		// TODO Auto-generated method stub

		getLogger().info(getDescription().getFullName() + " enabled");
	}

	void restoreItems(final Player player) {
		List<ItemStack> items = persist.restoreItems(player);
		if (items == null || items.size() == 0) {
			return;
		}

		Location location = player.getLocation();
		World world = player.getWorld();
		for (ItemStack item : items) {
			world.dropItem(location, item);
		}
		persist.deleteItems(player);
	}
}
