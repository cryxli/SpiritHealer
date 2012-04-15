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

	/**
	 * How often the player was killed by mobs with the given entity.
	 * 
	 * @param entity
	 *            Group deaths by <code>LivingEntity</code>
	 */
	int getDeaths(LivingEntity entity);

	/**
	 * How often the player was killed by mobs with the given affection.
	 * 
	 * @param affection
	 *            Group the deaths by {@link LivingEntityAffection}
	 */
	int getDeaths(LivingEntityAffection affection);

	/**
	 * How often the player was killed by mobs with the given affection.
	 * 
	 * @param type
	 *            Group deaths by {@link LivingEntityType}
	 */
	int getDeaths(LivingEntityType type);

	/**
	 * How many mobs of the given affection the player killed.
	 * 
	 * @param entity
	 *            Group kills by <code>LivingEntity</code>
	 */
	int getKills(LivingEntity entity);

	/**
	 * How many mobs of the given affection the player killed.
	 * 
	 * @param affection
	 *            Group kills by {@link LivingEntityAffection}
	 */
	int getKills(LivingEntityAffection affection);

	/**
	 * How many mobs of the given affection the player killed.
	 * 
	 * @param type
	 *            Group kills by {@link LivingEntityType}
	 */
	int getKills(LivingEntityType type);

}
