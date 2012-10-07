package li.cryx.minecraft.util;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

/**
 * This enum helps to determine which living entity you're facing.
 * 
 * @author cryxli
 */
public enum LivingEntityType {
	PLAYER(EntityType.PLAYER, LivingEntityAffection.PVP), //
	SKELETON(EntityType.SKELETON, LivingEntityAffection.AGGRESSIVE), //
	CREEPER(EntityType.CREEPER, LivingEntityAffection.AGGRESSIVE), //
	CAVE_SPIDER(EntityType.CAVE_SPIDER, LivingEntityAffection.AGGRESSIVE), //
	SPIDER(EntityType.SPIDER, LivingEntityAffection.AGGRESSIVE), //
	ENDERMAN(EntityType.ENDERMAN, LivingEntityAffection.NEUTRAL), //
	ENDER_DRAGON(EntityType.ENDER_DRAGON, LivingEntityAffection.AGGRESSIVE), //
	BLAZE(EntityType.BLAZE, LivingEntityAffection.AGGRESSIVE), //
	PIG_ZOMBIE(EntityType.PIG_ZOMBIE, LivingEntityAffection.NEUTRAL), //
	ZOMBIE(EntityType.ZOMBIE, LivingEntityAffection.AGGRESSIVE), //
	SILVERFISH(EntityType.SILVERFISH, LivingEntityAffection.AGGRESSIVE), //
	VILLAGER(EntityType.VILLAGER, LivingEntityAffection.FRIENDLY), //
	SQUID(EntityType.SQUID, LivingEntityAffection.FRIENDLY), //
	GHAST(EntityType.GHAST, LivingEntityAffection.AGGRESSIVE), //
	SLIME(EntityType.SLIME, LivingEntityAffection.AGGRESSIVE), //
	MAGMA_CUBE(EntityType.MAGMA_CUBE, LivingEntityAffection.AGGRESSIVE), //
	MUSHROOM_COW(EntityType.MUSHROOM_COW, LivingEntityAffection.FRIENDLY), //
	COW(EntityType.COW, LivingEntityAffection.FRIENDLY), //
	CHICKEN(EntityType.CHICKEN, LivingEntityAffection.FRIENDLY), //
	SHEEP(EntityType.SHEEP, LivingEntityAffection.FRIENDLY), //
	PIG(EntityType.PIG, LivingEntityAffection.FRIENDLY), //
	WOLF(EntityType.WOLF, LivingEntityAffection.NEUTRAL), //
	OCELOT(EntityType.OCELOT, LivingEntityAffection.NEUTRAL), //
	SNOWMAN(EntityType.SNOWMAN, LivingEntityAffection.FRIENDLY), //
	IRON_GOLEM(EntityType.IRON_GOLEM, LivingEntityAffection.NEUTRAL);

	// new in weeklies
	// WITCH(LivingEntityAffection.AGGRESSIVE)
	// BAT(LivingEntityAffection.NEUTRAL)

	/**
	 * Get the type of the given entity instance.
	 * 
	 * @param entityType
	 *            <code>EntityType</code> to inspect.
	 * @return Matching LivingEntityType, or, <code>null</code>.
	 */
	public static LivingEntityType getType(final EntityType entityType) {
		for (LivingEntityType type : values()) {
			if (type.getBukkitType() == entityType) {
				return type;
			}
		}
		return null;
	}

	/**
	 * Get the type of the given entity instance.
	 * 
	 * @param entity
	 *            <code>LivingEntity</code> to inspect.
	 * @return Matching LivingEntityType, or, <code>null</code>.
	 */
	public static LivingEntityType getType(final LivingEntity entity) {
		return getType(entity.getType());
	}

	/**
	 * Get a list of LivingEntityType with the given affection.
	 * 
	 * @param affection
	 *            LivingEntityAffection describing the list.
	 * @return A list of LivingEntityType with the given affection.
	 */
	public static List<LivingEntityType> getTypes(
			final LivingEntityAffection affection) {
		List<LivingEntityType> list = new LinkedList<LivingEntityType>();
		for (LivingEntityType type : values()) {
			if (type.getAffection() == affection) {
				list.add(type);
			}
		}

		return list;
	}

	/** Bukkit's <code>EntityType</code> matching this creature. */
	private final EntityType type;

	/** How the create acts when it meets a player. */
	private final LivingEntityAffection affection;

	private LivingEntityType(EntityType type,
			final LivingEntityAffection affection) {
		this.type = type;
		this.affection = affection;
	}

	public LivingEntityAffection getAffection() {
		return affection;
	}

	public EntityType getBukkitType() {
		return type;
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
