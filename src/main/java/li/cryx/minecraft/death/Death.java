package li.cryx.minecraft.death;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.persistence.PersistenceException;

import li.cryx.minecraft.death.listener.BlockListener;
import li.cryx.minecraft.death.listener.DeathListener;
import li.cryx.minecraft.death.listener.PlayerInteractListener;
import li.cryx.minecraft.death.persist.AbstractPersistManager;
import li.cryx.minecraft.death.persist.FragsInfo;
import li.cryx.minecraft.death.persist.db.PersistenceDatabase;
import li.cryx.minecraft.death.persist.db.model.DeathLocation;
import li.cryx.minecraft.death.persist.db.model.Enchant;
import li.cryx.minecraft.death.persist.db.model.Item;
import li.cryx.minecraft.death.persist.db.model.Kills;
import li.cryx.minecraft.death.persist.flat.PersistenceFlatFile;

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

	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> classes = new LinkedList<Class<?>>();
		classes.add(Kills.class);
		classes.add(DeathLocation.class);
		classes.add(Item.class);
		classes.add(Enchant.class);
		return classes;
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
			CommandMessage.INSTANCE.sendFragsAnswer( //
					sender, //
					args, //
					getConfig().getString("FragsDisplay"), //
					info //
					);
			return true;
		} else {
			return super.onCommand(sender, command, label, args);
		}
	}

	@Override
	public void onDisable() {
		playerListener = null;
		blockListener = null;
		altarListener = null;
		altars.clear();
		if (persist != null) {
			persist.shutdown();
		}
		persist = null;

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

		if ("FlatFile".equalsIgnoreCase(config.getString("Database"))) {
			persist = new PersistenceFlatFile(this);
		} else {
			// install database
			try {
				getDatabase().find(Kills.class).findRowCount();
			} catch (PersistenceException ex) {
				installDDL();
			}
			// create database abstraction
			persist = new PersistenceDatabase(this);
		}

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

}
