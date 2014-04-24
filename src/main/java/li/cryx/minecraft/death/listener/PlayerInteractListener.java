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
package li.cryx.minecraft.death.listener;

import java.util.HashMap;
import java.util.Map;

import li.cryx.minecraft.death.ISpiritHealer;
import li.cryx.minecraft.death.i18n.LangKeys;
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
	private final ISpiritHealer plugin;

	/** Keep warnings for players. */
	private final Map<Player, Integer> warnings = new HashMap<Player, Integer>();

	public PlayerInteractListener(final ISpiritHealer plugin) {
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
					plugin.getTranslator().sendMessage(player,
							LangKeys.SPIRITHEALER.NOT_DEAD);
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
			plugin.getTranslator().sendMessage(player,
					LangKeys.SPIRITHEALER.WARNING);
			break;
		case 2:
			plugin.getTranslator().sendMessage(player,
					LangKeys.SPIRITHEALER.WARNING1);
			// half the hunger bar
			player.setExhaustion(player.getExhaustion() / 2);
			player.setFoodLevel((int) Math.ceil(player.getFoodLevel() / 2.0));
			break;
		case 3:
			plugin.getTranslator().sendMessage(player,
					LangKeys.SPIRITHEALER.WARNING2);
			// half the health bar
			player.setHealth(player.getHealth() / 2);
			break;
		default:
		case 4:
			// kick the player
			player.kickPlayer(plugin.getTranslator().translate(player,
					LangKeys.SPIRITHEALER.WARNING3));
			plugin.getLogger().info("kicked " + player.getName());
			warn = 4;
			break;
		}

		warnings.put(player, warn);
	}
}
