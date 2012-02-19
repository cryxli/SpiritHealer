package li.cryx.minecraft.util;

import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

/**
 * This enum helps to determine which living entity you're facing.
 * 
 * @author cryxli
 */
public enum LivingEntityType {
	PLAYER(LivingEntityAffection.PVP), //
	ZOMBIE(LivingEntityAffection.AGGRESSIVE), //
	SPIDER(LivingEntityAffection.AGGRESSIVE), //
	SKELETON(LivingEntityAffection.AGGRESSIVE), //
	CREEPER(LivingEntityAffection.AGGRESSIVE), //
	CAVE_SPIDER(LivingEntityAffection.AGGRESSIVE), //
	ENDERMAN(LivingEntityAffection.NEUTRAL), //
	DRAGON(LivingEntityAffection.AGGRESSIVE), //
	BLAZE(LivingEntityAffection.AGGRESSIVE), //
	PIGMAN(LivingEntityAffection.NEUTRAL), //
	SILVERFISH(LivingEntityAffection.AGGRESSIVE), //
	VILLAGER(LivingEntityAffection.FRIENDLY), //
	SQUID(LivingEntityAffection.FRIENDLY), //
	GHAST(LivingEntityAffection.AGGRESSIVE), //
	SLIME(LivingEntityAffection.AGGRESSIVE), //
	MAGMA_CUBE(LivingEntityAffection.AGGRESSIVE), //
	MOOSHROOM(LivingEntityAffection.FRIENDLY), //
	COW(LivingEntityAffection.FRIENDLY), //
	CHICKEN(LivingEntityAffection.FRIENDLY), //
	SHEEP(LivingEntityAffection.FRIENDLY), //
	PIG(LivingEntityAffection.FRIENDLY), //
	WOLF(LivingEntityAffection.NEUTRAL);

	/**
	 * Get the type of the given entity instance.
	 * 
	 * @param entity
	 *            <code>LivingEntity</code> to inspect.
	 * @return Entity type, or, <code>null</code>.
	 */
	public static LivingEntityType getType(final LivingEntity entity) {
		if (entity instanceof Player) {
			return PLAYER;
		} else if (entity instanceof CaveSpider) {
			// CaveSpider is a Spider
			return CAVE_SPIDER;
		} else if (entity instanceof Spider) {
			return SPIDER;
		} else if (entity instanceof Skeleton) {
			return SKELETON;
		} else if (entity instanceof Creeper) {
			return CREEPER;
		} else if (entity instanceof Enderman) {
			return ENDERMAN;
		} else if (entity instanceof EnderDragon) {
			return DRAGON;
		} else if (entity instanceof Blaze) {
			return BLAZE;
		} else if (entity instanceof PigZombie) {
			// Zombie-Pigman is a Zombie
			return PIGMAN;
		} else if (entity instanceof Zombie) {
			return ZOMBIE;
		} else if (entity instanceof Silverfish) {
			return SILVERFISH;
		} else if (entity instanceof Villager) {
			return VILLAGER;
		} else if (entity instanceof Squid) {
			return SQUID;
		} else if (entity instanceof Ghast) {
			return GHAST;
		} else if (entity instanceof MagmaCube) {
			return MAGMA_CUBE;
		} else if (entity instanceof Slime) {
			return SLIME;
		} else if (entity instanceof MushroomCow) {
			// Mooshroom is a Cow
			return MOOSHROOM;
		} else if (entity instanceof Cow) {
			return COW;
		} else if (entity instanceof Chicken) {
			return CHICKEN;
		} else if (entity instanceof Sheep) {
			return SHEEP;
		} else if (entity instanceof Pig) {
			return PIG;
		} else if (entity instanceof Wolf) {
			return WOLF;
		}

		return null;
	}

	private final LivingEntityAffection affection;

	private LivingEntityType(final LivingEntityAffection affection) {
		this.affection = affection;
	}

	public LivingEntityAffection getAffection() {
		return affection;
	}

	public boolean isAggressive() {
		return affection == LivingEntityAffection.AGGRESSIVE;
	}

	public boolean isFriendly() {
		return affection == LivingEntityAffection.FRIENDLY;
	}

	public boolean isNeutral() {
		return affection == LivingEntityAffection.NEUTRAL;
	}

	public boolean isPvp() {
		return affection == LivingEntityAffection.PVP;
	}
}
