package li.cryx.minecraft.death.persist.db;

import li.cryx.minecraft.death.persist.FragsInfo;
import li.cryx.minecraft.util.LivingEntityAffection;
import li.cryx.minecraft.util.LivingEntityType;

import org.bukkit.entity.LivingEntity;

/**
 * This class delegates kills and deaths requests to the database.
 * 
 * @author cryxli
 */
public class FragsInfoDelegator implements FragsInfo {
	/** Reference to the DAO */
	private final PersistenceDatabase database;
	/** Name of the player for this {@link FragsInfo} */
	private final String playerName;

	public FragsInfoDelegator(final PersistenceDatabase database,
			final String playerName) {
		this.database = database;
		this.playerName = playerName;
	}

	@Override
	public int getDeaths(final LivingEntity entity) {
		return getDeaths(LivingEntityType.getType(entity));
	}

	@Override
	public int getDeaths(final LivingEntityAffection affection) {
		int sum = 0;
		for (LivingEntityType type : LivingEntityType.getTypes(affection)) {
			sum += getDeaths(type);
		}
		return sum;
	}

	@Override
	public int getDeaths(final LivingEntityType type) {
		return database.getSingleKillEntry(playerName, type).getDeaths();
	}

	@Override
	public int getKills(final LivingEntity entity) {
		return getKills(LivingEntityType.getType(entity));
	}

	@Override
	public int getKills(final LivingEntityAffection affection) {
		int sum = 0;
		for (LivingEntityType type : LivingEntityType.getTypes(affection)) {
			sum += getKills(type);
		}
		return sum;
	}

	@Override
	public int getKills(final LivingEntityType type) {
		return database.getSingleKillEntry(playerName, type).getKills();
	}

}
