package li.cryx.minecraft.death.persist;

import java.util.LinkedList;
import java.util.List;

import li.cryx.minecraft.util.LivingEntityType;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Super class for persistence managers defining the common interface.
 * 
 * @author cryxli
 */
public abstract class AbstractPersistManager {

	protected final JavaPlugin plugin;

	public AbstractPersistManager(final JavaPlugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * Delete all stored items for the given player.
	 * 
	 * @param player
	 *            Player in question.
	 * @see #persistItems(Player)
	 */
	public abstract void deleteItems(final Player player);

	protected List<ItemStack> getAllItemsOfPlayer(final Player player) {
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
		return items;
	}

	// TODO
	public abstract Location getDeathLocation(final Player player);

	// TODO
	public abstract FragsInfo getFrags(Player player);

	/**
	 * Check whether the given player has not recovered items.
	 * 
	 * @param player
	 *            Player in question.
	 * @return <code>true</code>, if there are items to recover.
	 */
	public abstract boolean hasInventory(Player player);

	/**
	 * Increase the amount of entities that killed the given player.
	 * 
	 * @param player
	 * @param type
	 */
	public abstract void increaseDeaths(Player player, LivingEntityType type);

	/**
	 * Increase the amount of entities of the given type the given player
	 * killed.
	 * 
	 * @param player
	 * @param type
	 */
	public abstract void increaseKills(Player player, LivingEntityType type);

	/**
	 * Store player's location (assuming s/he died there).
	 * 
	 * @param player
	 *            Player in question.
	 * @return <code>true</code> on success.
	 */
	public abstract boolean persistDeathLocation(final Player player);

	/**
	 * Store player's inventory.
	 * 
	 * @param player
	 *            Player in question.
	 * @return <code>true</code> on success.
	 * @see #restoreItems(Player)
	 */
	public abstract boolean persistItems(final Player player);

	/**
	 * Load player's inventory. After this call the items are still stored. User
	 * {@link #deleteItems(Player)} to remove them.
	 * 
	 * @param player
	 *            Player in question.
	 * @return List of saved items. May be empty but not <code>null</code>.
	 * @see #persistItems(Player)
	 * @see #deleteItems(Player)
	 */
	public abstract List<ItemStack> restoreItems(final Player player);

	/**
	 * Call this method when the persistence manager should shutdown. This
	 * method does nothing, but gives implementing sub-classes a chance to react
	 * on this event.
	 */
	public void shutdown() {
	}
}
