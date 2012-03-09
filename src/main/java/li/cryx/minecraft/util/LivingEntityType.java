package li.cryx.minecraft.util;

import org.bukkit.entity.LivingEntity;

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
	ENDER_DRAGON(LivingEntityAffection.AGGRESSIVE), //
	BLAZE(LivingEntityAffection.AGGRESSIVE), //
	PIG_ZOMBIE(LivingEntityAffection.NEUTRAL), //
	SILVERFISH(LivingEntityAffection.AGGRESSIVE), //
	VILLAGER(LivingEntityAffection.FRIENDLY), //
	SQUID(LivingEntityAffection.FRIENDLY), //
	GHAST(LivingEntityAffection.AGGRESSIVE), //
	SLIME(LivingEntityAffection.AGGRESSIVE), //
	MAGMA_CUBE(LivingEntityAffection.AGGRESSIVE), //
	MUSHROOM_COW(LivingEntityAffection.FRIENDLY), //
	COW(LivingEntityAffection.FRIENDLY), //
	CHICKEN(LivingEntityAffection.FRIENDLY), //
	SHEEP(LivingEntityAffection.FRIENDLY), //
	PIG(LivingEntityAffection.FRIENDLY), //
	WOLF(LivingEntityAffection.NEUTRAL);
	// OCELOT(LivingEntityAffection.NEUTRAL);

	/**
	 * Get the type of the given entity instance.
	 * 
	 * @param entity
	 *            <code>LivingEntity</code> to inspect.
	 * @return Entity type, or, <code>null</code>.
	 */
	public static LivingEntityType getType(final LivingEntity entity) {
		switch (entity.getType()) {
		case PLAYER:
			return PLAYER;
		case CAVE_SPIDER:
			return CAVE_SPIDER;
		case SPIDER:
			return SPIDER;
		case SKELETON:
			return SKELETON;
		case CREEPER:
			return CREEPER;
		case ENDERMAN:
			return ENDERMAN;
		case ENDER_DRAGON:
			return ENDER_DRAGON;
		case BLAZE:
			return BLAZE;
		case PIG_ZOMBIE:
			return PIG_ZOMBIE;
		case ZOMBIE:
			return ZOMBIE;
		case SILVERFISH:
			return SILVERFISH;
		case VILLAGER:
			return VILLAGER;
		case SQUID:
			return SQUID;
		case GHAST:
			return GHAST;
		case MAGMA_CUBE:
			return MAGMA_CUBE;
		case SLIME:
			return SLIME;
		case MUSHROOM_COW:
			return MUSHROOM_COW;
		case COW:
			return COW;
		case CHICKEN:
			return CHICKEN;
		case SHEEP:
			return SHEEP;
		case PIG:
			return PIG;
		case WOLF:
			return WOLF;
			// case OCELOT:
			// return OCELOT;
		default:
			return null;
		}
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
