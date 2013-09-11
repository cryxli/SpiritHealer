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
	BAT(EntityType.BAT, LivingEntityAffection.NEUTRAL), //
	BLAZE(EntityType.BLAZE, LivingEntityAffection.AGGRESSIVE), //
	CAVE_SPIDER(EntityType.CAVE_SPIDER, LivingEntityAffection.AGGRESSIVE), //
	CHICKEN(EntityType.CHICKEN, LivingEntityAffection.FRIENDLY), //
	COW(EntityType.COW, LivingEntityAffection.FRIENDLY), //
	CREEPER(EntityType.CREEPER, LivingEntityAffection.AGGRESSIVE), //
	ENDER_DRAGON(EntityType.ENDER_DRAGON, LivingEntityAffection.AGGRESSIVE), //
	ENDERMAN(EntityType.ENDERMAN, LivingEntityAffection.NEUTRAL), //
	GHAST(EntityType.GHAST, LivingEntityAffection.AGGRESSIVE), //
	HORSE(EntityType.HORSE, LivingEntityAffection.FRIENDLY), //
	IRON_GOLEM(EntityType.IRON_GOLEM, LivingEntityAffection.NEUTRAL), //
	MAGMA_CUBE(EntityType.MAGMA_CUBE, LivingEntityAffection.AGGRESSIVE), //
	MUSHROOM_COW(EntityType.MUSHROOM_COW, LivingEntityAffection.FRIENDLY), //
	OCELOT(EntityType.OCELOT, LivingEntityAffection.NEUTRAL), //
	PIG(EntityType.PIG, LivingEntityAffection.FRIENDLY), //
	PIG_ZOMBIE(EntityType.PIG_ZOMBIE, LivingEntityAffection.NEUTRAL), //
	PLAYER(EntityType.PLAYER, LivingEntityAffection.PVP), //
	SHEEP(EntityType.SHEEP, LivingEntityAffection.FRIENDLY), //
	SILVERFISH(EntityType.SILVERFISH, LivingEntityAffection.AGGRESSIVE), //
	SKELETON(EntityType.SKELETON, LivingEntityAffection.AGGRESSIVE), //
	SLIME(EntityType.SLIME, LivingEntityAffection.AGGRESSIVE), //
	SNOWMAN(EntityType.SNOWMAN, LivingEntityAffection.FRIENDLY), //
	SPIDER(EntityType.SPIDER, LivingEntityAffection.AGGRESSIVE), //
	SQUID(EntityType.SQUID, LivingEntityAffection.FRIENDLY), //
	VILLAGER(EntityType.VILLAGER, LivingEntityAffection.FRIENDLY), //
	WITCH(EntityType.WITCH, LivingEntityAffection.AGGRESSIVE), //
	WITHER(EntityType.WITHER, LivingEntityAffection.AGGRESSIVE), //
	// WITHER_SKELETON(???, LivingEntityAffection.AGGRESSIVE), //
	WOLF(EntityType.WOLF, LivingEntityAffection.NEUTRAL), //
	ZOMBIE(EntityType.ZOMBIE, LivingEntityAffection.AGGRESSIVE);

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

	private LivingEntityType(final EntityType type,
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
