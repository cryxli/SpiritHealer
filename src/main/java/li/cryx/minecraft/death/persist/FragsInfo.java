package li.cryx.minecraft.death.persist;

import li.cryx.minecraft.util.LivingEntityAffection;
import li.cryx.minecraft.util.LivingEntityType;

import org.bukkit.entity.LivingEntity;

/**
 * This structure represents kills and deaths of a player.
 * 
 * @author cryxli
 */
public interface FragsInfo {

	int getKillers(LivingEntity entity);

	/** How often the player was killed by mobs with the given affection. */
	int getKillers(LivingEntityAffection affection);

	int getKillers(LivingEntityType type);

	int getKills(LivingEntity entity);

	/** How many mobs with the given affection the player killed. */
	int getKills(LivingEntityAffection affection);

	int getKills(LivingEntityType type);

}
