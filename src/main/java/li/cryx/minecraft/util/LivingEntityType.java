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
	PLAYER("P"), //
	ZOMBIE("Z"), //
	SPIDER("S"), //
	SKELETON("SK"), //
	CREEPER("C"), //
	CAVE_SPIDER("CS"), //
	ENDERMAN("E"), //
	DRAGON("D"), //
	BLAZE("B"), //
	PIGMAN("PM"), //
	SILVERFISH("SF"), //
	VILLAGER("V"), //
	SQUID("SQ"), //
	GHAST("G"), //
	SLIME("SL"), //
	MAGMA_CUBE("M"), //
	MOOSHROOM("MS"), //
	COW("CW"), //
	CHICKEN("CK"), //
	SHEEP("SH"), //
	PIG("PG"), //
	WOLF("W");

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

	// TODO short names ... ?
	private final String shortName;

	private LivingEntityType(final String shortName) {
		this.shortName = shortName;
	}

	public String getShortName() {
		return shortName;
	}
}
