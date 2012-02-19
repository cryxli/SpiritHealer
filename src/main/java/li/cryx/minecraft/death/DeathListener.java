package li.cryx.minecraft.death;

import java.util.logging.Level;

import li.cryx.minecraft.util.LivingEntityType;

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

			// } else {
			// processNpcDeath((LivingEntity) event.getEntity());
		}
	}

	private void processNpcDeath(final LivingEntity entity) {
		LivingEntityType let = LivingEntityType.getType(entity);
		System.out.println(let + " died " + entity.getEntityId());
		System.out.println(" killer=" + entity.getKiller());

		// TODO Auto-generated method stub

		EntityDamageEvent dmg = entity.getLastDamageCause();
		findKiller(dmg);
	}

	/**
	 * Player died. Store his/her items and prevent death drops.
	 * 
	 * @param event
	 *            The <code>PlayerDeathEvent</code>
	 */
	private void processPlayerDeath(final PlayerDeathEvent event) {
		Player player = (Player) event.getEntity();
		// persisting player's items
		boolean success = plugin.getPersist().persistItems(player);

		if (success) {
			// prevent item drops
			event.getDrops().clear();
		} else {
			player.sendMessage("The Spirit Healer cannot recover your items.");
		}

		plugin.getLogger().log(Level.INFO, player.getName() + " died");

		// EntityDamageEvent dmg = player.getLastDamageCause();
		// findKiller(dmg);
	}

}
