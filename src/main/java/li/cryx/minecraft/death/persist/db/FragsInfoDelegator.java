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
package li.cryx.minecraft.death.persist.db;

import li.cryx.minecraft.death.persist.FragsInfo;
import li.cryx.minecraft.util.LivingEntityAffection;
import li.cryx.minecraft.util.LivingEntityType;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * This class delegates kills and deaths requests to the database.
 * 
 * @author cryxli
 */
public class FragsInfoDelegator implements FragsInfo {
	/** Reference to the DAO */
	private final PersistenceDatabase database;
	/** Name of the player for this {@link FragsInfo} */
	private final Player player;

	public FragsInfoDelegator(final PersistenceDatabase database,
			final Player player) {
		this.database = database;
		this.player = player;
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
		return database.getSingleKillEntry(player, type).getDeaths();
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
		return database.getSingleKillEntry(player, type).getKills();
	}

}
