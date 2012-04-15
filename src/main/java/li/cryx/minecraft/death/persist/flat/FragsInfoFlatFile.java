package li.cryx.minecraft.death.persist.flat;

import li.cryx.minecraft.death.persist.FragsInfo;
import li.cryx.minecraft.util.LivingEntityAffection;
import li.cryx.minecraft.util.LivingEntityType;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;

/**
 * Implementation of {@link FragsInfo} for {@link PersistenceFlatFile}.
 * 
 * @author cryxli
 */
public class FragsInfoFlatFile implements FragsInfo {

	private final YamlConfiguration conf;

	public FragsInfoFlatFile(final YamlConfiguration conf) {
		this.conf = conf;
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
		if (type == null) {
			return 0;
		} else {
			return conf.getInt("killed." + type.toString());
		}
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
		if (type == null) {
			return 0;
		} else {
			return conf.getInt("kills." + type.toString());
		}
	}

}
