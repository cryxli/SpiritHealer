package li.cryx.minecraft.death.listener;

import java.util.HashMap;
import java.util.Map;

import li.cryx.minecraft.death.Death;
import li.cryx.minecraft.util.PermNode;

import org.bukkit.Material;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * A listener to capture players approaching a spirit altar.
 * 
 * @author cryxli
 */
public class PlayerInteractListener implements Listener {

	/** Reference to plugin. */
	private final Death plugin;

	/** Keep warnings for players. */
	private final Map<Player, Integer> warnings = new HashMap<Player, Integer>();

	public PlayerInteractListener(final Death plugin) {
		this.plugin = plugin;
	}

	/**
	 * Get warning count for given player.
	 * 
	 * @param player
	 *            Player to warn.
	 * @return Warning count. Starts with zero.
	 */
	private int getWarning(final Player player) {
		Integer i = warnings.get(player);
		if (i == null) {
			return 0;
		} else {
			return i;
		}
	}

	/**
	 * Intercept when player left-clicks something. Might be a spirit altar.
	 * 
	 * @param event
	 *            A player interact event.
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockHit(final PlayerInteractEvent event) {
		Block block = event.getClickedBlock();
		if (block == null || block.getType() != plugin.getAltarMaterial()) {
			return;
		} else if (block.getWorld().getWorldType() != WorldType.NORMAL) {
			return;
		} else if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
			return;
		}

		Player player = event.getPlayer();
		if (!PermNode.INVENTORY.hasPermission(player)) {
			return;
		}

		ItemStack item = player.getItemInHand();
		if (item == null || item.getType() == Material.AIR) {
			warnings.remove(player);
			if (plugin.isAltar(block.getLocation())) {
				if (plugin.getPersist().hasInventory(player)) {
					plugin.restoreItems(player);
				} else {
					player.sendMessage(plugin.getConfig().getString(
							"YouAreNotDead"));
				}
			}
		} else {
			if (plugin.isAltar(block.getLocation())) {
				// warn player
				sendWarning(player);
			}
		}
	}

	/**
	 * Warn and if necessary, punish the given player.
	 * 
	 * @param player
	 *            A player
	 */
	private void sendWarning(final Player player) {
		int warn = getWarning(player);
		warn++;

		switch (warn) {
		case 1:
			// first time, just warn the player
			player.sendMessage(plugin.getConfig().getString("NoWeapons"));
			break;
		case 2:
			player.sendMessage(plugin.getConfig().getString("Warn1"));
			// half the hunger bar
			player.setExhaustion(player.getExhaustion() / 2);
			player.setFoodLevel((int) Math.ceil(player.getFoodLevel() / 2.0));
			break;
		case 3:
			player.sendMessage(plugin.getConfig().getString("Warn2"));
			// half the health bar
			player.setHealth(player.getHealth() / 2);
			break;
		default:
		case 4:
			// kick the player
			player.kickPlayer(plugin.getConfig().getString("Warn3"));
			plugin.getLogger().info("kicked " + player.getName());
			warn = 4;
			break;
		}

		warnings.put(player, warn);
	}
}
