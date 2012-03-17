package li.cryx.minecraft.death.persist;

/**
 * This structure represents kills and deaths of a player.
 * 
 * @author cryxli
 */
public interface FragsInfo {

	/** How often the player was killed by aggressive mobs. */
	int getAggroKillers();

	/** How many aggressive mobs the player killed. */
	int getAggroKills();

	/**
	 * How often the player was killed by friendly mobs. ATM this will always be
	 * <code>0</code>.
	 */
	int getFriendlyKillers();

	/** How many friendly mobs the player killed. */
	int getFriendlyKills();

	/** How often the player was killed by neutral mobs. */
	int getNeutralKillers();

	/** How many neutral mobs the player killed. */
	int getNeutralKills();

	/** How often the player was killed by other players. */
	int getPvpKillers();

	/** How many other players the player killed. */
	int getPvpKills();

}
