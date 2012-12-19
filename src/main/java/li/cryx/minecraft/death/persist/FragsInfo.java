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
package li.cryx.minecraft.death.persist;

import li.cryx.minecraft.util.LivingEntityAffection;
import li.cryx.minecraft.util.LivingEntityType;

import org.bukkit.entity.LivingEntity;

/**
 * This structure represents kills and deaths of a player.
 * 
 * @author cryxli
 */
public interface FragsInfo {

	/**
	 * How often the player was killed by mobs with the given entity.
	 * 
	 * @param entity
	 *            Group deaths by <code>LivingEntity</code>
	 */
	int getDeaths(LivingEntity entity);

	/**
	 * How often the player was killed by mobs with the given affection.
	 * 
	 * @param affection
	 *            Group the deaths by {@link LivingEntityAffection}
	 */
	int getDeaths(LivingEntityAffection affection);

	/**
	 * How often the player was killed by mobs with the given affection.
	 * 
	 * @param type
	 *            Group deaths by {@link LivingEntityType}
	 */
	int getDeaths(LivingEntityType type);

	/**
	 * How many mobs of the given affection the player killed.
	 * 
	 * @param entity
	 *            Group kills by <code>LivingEntity</code>
	 */
	int getKills(LivingEntity entity);

	/**
	 * How many mobs of the given affection the player killed.
	 * 
	 * @param affection
	 *            Group kills by {@link LivingEntityAffection}
	 */
	int getKills(LivingEntityAffection affection);

	/**
	 * How many mobs of the given affection the player killed.
	 * 
	 * @param type
	 *            Group kills by {@link LivingEntityType}
	 */
	int getKills(LivingEntityType type);

}
