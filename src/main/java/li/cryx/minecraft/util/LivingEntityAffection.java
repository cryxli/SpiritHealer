package li.cryx.minecraft.util;

/**
 * This enum describes the affection of NPCs towards players.
 * 
 * @author cryxli
 * 
 */
public enum LivingEntityAffection {
	/** Player versus player */
	PVP,

	/** Friendly mobs like animals */
	FRIENDLY,

	/** Neutral mobs that do not attack unless the player attacks them first. */
	NEUTRAL,

	/** Aggressive mobs that try to kill players. */
	AGGRESSIVE;
}
