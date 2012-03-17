package li.cryx.minecraft.death.persist.flat;

import li.cryx.minecraft.death.persist.FragsInfo;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Implementation of {@link FragsInfo} for {@link PersistenceFlatFile}.
 * 
 * @author cryxli
 */
public class FragsInfoFlatFile implements FragsInfo {

	static final class KILLED {
		static final String AGGRO = "KilledAggro";
		static final String FRIEND = "KilledFriend";
		static final String NEUTRAL = "KilledNeutral";
		static final String PVP = "KilledPvp";
	}

	static final class KILLED_BY {
		static final String AGGRO = "KilledByAggro";
		static final String FRIEND = "KilledByFriend";
		static final String NEUTRAL = "KilledByNeutral";
		static final String PVP = "KilledByPvp";
	}

	private final YamlConfiguration conf;

	public FragsInfoFlatFile(final YamlConfiguration conf) {
		this.conf = conf;
	}

	@Override
	public int getAggroKillers() {
		return conf.getInt(KILLED_BY.AGGRO);
	}

	@Override
	public int getAggroKills() {
		return conf.getInt(KILLED.AGGRO);
	}

	@Override
	public int getFriendlyKillers() {
		return conf.getInt(KILLED_BY.FRIEND);
	}

	@Override
	public int getFriendlyKills() {
		return conf.getInt(KILLED.FRIEND);
	}

	@Override
	public int getNeutralKillers() {
		return conf.getInt(KILLED_BY.NEUTRAL);
	}

	@Override
	public int getNeutralKills() {
		return conf.getInt(KILLED.NEUTRAL);
	}

	@Override
	public int getPvpKillers() {
		return conf.getInt(KILLED_BY.PVP);
	}

	@Override
	public int getPvpKills() {
		return conf.getInt(KILLED.PVP);
	}

}
