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

import li.cryx.minecraft.death.Death;
import li.cryx.minecraft.util.LivingEntityType;
import li.cryx.minecraft.util.PermNode;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Listen to player and NPC deaths.
 * 
 * @author cryxli
 */
public class DeathListener implements Listener {

	private final Death plugin;

	public DeathListener(final Death plugin) {
		this.plugin = plugin;
	}

	/**
	 * Get the <code>LivingEntity</code> that landed the final stroke.
	 * 
	 * @param dmg
	 *            The last event causing damage on a dying entity.
	 * @return Killing <code>LivingEntity</code>, or, <code>null</code>.
	 */
	private LivingEntity findKiller(final EntityDamageEvent dmg) {
		if (dmg instanceof EntityDamageByEntityEvent) {
			Entity damager = ((EntityDamageByEntityEvent) dmg).getDamager();
			if (damager instanceof Projectile) {
				return ((Projectile) damager).getShooter();
			} else if (damager != null && damager instanceof LivingEntity) {
				return (LivingEntity) damager;
			}
		}
		return null;
	}

	/**
	 * Listen to death events.
	 * 
	 * @param event
	 *            Events that may also contain player deaths.
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onNpcDeath(final EntityDeathEvent event) {
		if (event instanceof PlayerDeathEvent) {
			processPlayerDeath((PlayerDeathEvent) event);
		} else {
			processNpcDeath(event.getEntity());
		}
	}

	private void processNpcDeath(final LivingEntity entity) {
		LivingEntityType type = LivingEntityType.getType(entity);

		LivingEntity killer = findKiller(entity.getLastDamageCause());
		if (killer != null && killer instanceof Player) {
			Player player = (Player) killer;
			if (PermNode.FRAGS.hasPermission(player)) {
				plugin.getPersist().increaseKills(player, type);
				plugin.getLogger()
						.fine(type + " killed by " + player.getName());
			}
		}
	}

	/**
	 * Player died. Store his/her items and prevent death drops.
	 * 
	 * @param event
	 *            The <code>PlayerDeathEvent</code>
	 */
	private void processPlayerDeath(final PlayerDeathEvent event) {
		Player player = event.getEntity();

		if (PermNode.INVENTORY.hasPermission(player)) {
			// persisting player's items
			boolean success = plugin.getPersist().persistItems(player);

			if (success) {
				// prevent item drops
				event.getDrops().clear();
			} else {
				player.sendMessage("The Spirit Healer cannot recover your items.");
			}
			plugin.getLogger().info(player.getName() + " died");
			plugin.getPersist().persistDeathLocation(player);
		}

		if (PermNode.FRAGS.hasPermission(player)) {
			// count frags
			LivingEntity killer = findKiller(player.getLastDamageCause());
			if (killer != null) {
				plugin.getPersist().increaseDeaths(player,
						LivingEntityType.getType(killer));
			}
		}
	}

}
