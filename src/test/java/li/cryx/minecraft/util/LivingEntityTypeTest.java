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

import org.bukkit.entity.EntityType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Ensures the creature mapping defined in {@link LivingEntityType}.
 * 
 * @author cryxli
 */
public class LivingEntityTypeTest {

	@Test
	public void getType() {
		// make the test fail when LivingEntityType is changed.
		Assert.assertEquals(28, LivingEntityType.values().length);

		// read test starts here
		Assert.assertEquals(LivingEntityType.PLAYER,
				LivingEntityType.getType(EntityType.PLAYER));
		Assert.assertEquals(LivingEntityType.SKELETON,
				LivingEntityType.getType(EntityType.SKELETON));
		Assert.assertEquals(LivingEntityType.CREEPER,
				LivingEntityType.getType(EntityType.CREEPER));
		Assert.assertEquals(LivingEntityType.CAVE_SPIDER,
				LivingEntityType.getType(EntityType.CAVE_SPIDER));
		Assert.assertEquals(LivingEntityType.SPIDER,
				LivingEntityType.getType(EntityType.SPIDER));
		Assert.assertEquals(LivingEntityType.ENDERMAN,
				LivingEntityType.getType(EntityType.ENDERMAN));
		Assert.assertEquals(LivingEntityType.ENDER_DRAGON,
				LivingEntityType.getType(EntityType.ENDER_DRAGON));
		Assert.assertEquals(LivingEntityType.BLAZE,
				LivingEntityType.getType(EntityType.BLAZE));
		Assert.assertEquals(LivingEntityType.PIG_ZOMBIE,
				LivingEntityType.getType(EntityType.PIG_ZOMBIE));
		Assert.assertEquals(LivingEntityType.ZOMBIE,
				LivingEntityType.getType(EntityType.ZOMBIE));
		Assert.assertEquals(LivingEntityType.SILVERFISH,
				LivingEntityType.getType(EntityType.SILVERFISH));
		Assert.assertEquals(LivingEntityType.VILLAGER,
				LivingEntityType.getType(EntityType.VILLAGER));
		Assert.assertEquals(LivingEntityType.SQUID,
				LivingEntityType.getType(EntityType.SQUID));
		Assert.assertEquals(LivingEntityType.GHAST,
				LivingEntityType.getType(EntityType.GHAST));
		Assert.assertEquals(LivingEntityType.SLIME,
				LivingEntityType.getType(EntityType.SLIME));
		Assert.assertEquals(LivingEntityType.MAGMA_CUBE,
				LivingEntityType.getType(EntityType.MAGMA_CUBE));
		Assert.assertEquals(LivingEntityType.MUSHROOM_COW,
				LivingEntityType.getType(EntityType.MUSHROOM_COW));
		Assert.assertEquals(LivingEntityType.COW,
				LivingEntityType.getType(EntityType.COW));
		Assert.assertEquals(LivingEntityType.CHICKEN,
				LivingEntityType.getType(EntityType.CHICKEN));
		Assert.assertEquals(LivingEntityType.SHEEP,
				LivingEntityType.getType(EntityType.SHEEP));
		Assert.assertEquals(LivingEntityType.PIG,
				LivingEntityType.getType(EntityType.PIG));
		Assert.assertEquals(LivingEntityType.WOLF,
				LivingEntityType.getType(EntityType.WOLF));
		Assert.assertEquals(LivingEntityType.OCELOT,
				LivingEntityType.getType(EntityType.OCELOT));
		Assert.assertEquals(LivingEntityType.SNOWMAN,
				LivingEntityType.getType(EntityType.SNOWMAN));
		Assert.assertEquals(LivingEntityType.IRON_GOLEM,
				LivingEntityType.getType(EntityType.IRON_GOLEM));
		Assert.assertEquals(LivingEntityType.WITHER,
				LivingEntityType.getType(EntityType.WITHER));
		Assert.assertEquals(LivingEntityType.WITCH,
				LivingEntityType.getType(EntityType.WITCH));
		Assert.assertEquals(LivingEntityType.BAT,
				LivingEntityType.getType(EntityType.BAT));
	}
}
