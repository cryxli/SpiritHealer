/*
 * Copyright (c) 2011 Urs P. Stettler, https://github.com/cryxli
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package li.cryx.minecraft.death;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.persistence.PersistenceException;

import li.cryx.minecraft.death.i18n.AclLanguage;
import li.cryx.minecraft.death.i18n.FallbackTranslation;
import li.cryx.minecraft.death.i18n.ITranslator;
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
import li.cryx.minecraft.util.PermNode;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of the plugin managing persistence and listeners.
 * 
 * @author cryxli
 */
public class Death extends JavaPlugin implements ISpiritHealer {

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

	private ITranslator i18n;

	private Material altarMaterial;

	private Material altarBaseMaterial;

	/**
	 * Add an altar at the given location.
	 * 
	 * @param location
	 */
	@Override
	public void addAltarLocation(final Location location) {
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

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(playerListener, this);
		pm.registerEvents(blockListener, this);
		pm.registerEvents(altarListener, this);
	}

	@Override
	public Material getAltarBaseMaterial() {
		return altarBaseMaterial;
	}

	@Override
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

	@Override
	public AbstractPersistManager getPersist() {
		return persist;
	}

	@Override
	public ITranslator getTranslator() {
		return i18n;
	}

	@Override
	public boolean isAltar(final Location location) {
		return altars.contains(location);
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
	@SuppressWarnings("deprecation")
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
		if ((sender instanceof Player) //
				&& PermNode.FRAGS.hasPermission(sender)) {
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

		// translations
		Plugin language = Bukkit.getServer().getPluginManager()
				.getPlugin("Language");
		if (language != null) {
			i18n = new AclLanguage(this);
		} else {
			i18n = new FallbackTranslation();
		}
		CommandMessage.setPlugin(this);

		getLogger().info(getDescription().getFullName() + " enabled");
	}

	@Override
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
