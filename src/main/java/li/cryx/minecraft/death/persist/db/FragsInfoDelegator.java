package li.cryx.minecraft.death.persist.db;

import li.cryx.minecraft.death.persist.FragsInfo;
import li.cryx.minecraft.util.LivingEntityAffection;
import li.cryx.minecraft.util.LivingEntityType;

import org.bukkit.entity.LivingEntity;

public class FragsInfoDelegator implements FragsInfo {
	private final PersistenceDatabase databse;
	private final String playerName;

	public FragsInfoDelegator(final PersistenceDatabase database,
			final String playerName) {
		this.databse = database;
		this.playerName = playerName;
	}

	@Override
	public int getKillers(final LivingEntity entity) {
		return getKillers(LivingEntityType.getType(entity));
	}

	@Override
	public int getKillers(final LivingEntityAffection affection) {
		int sum = 0;
		for (LivingEntityType type : LivingEntityType.getTypes(affection)) {
			sum += getKillers(type);
		}
		return sum;
	}

	@Override
	public int getKillers(final LivingEntityType type) {
		return databse.getSingleKillEntry(playerName, type).getDeaths();
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
		return databse.getSingleKillEntry(playerName, type).getKills();
	}

}
