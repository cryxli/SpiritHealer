package li.cryx.minecraft.util;

import junit.framework.Assert;

import org.bukkit.entity.Player;
import org.junit.Test;

public class DummyPlayerTest {

	@Test
	public void uuidStructure() {
		Player p = new DummyPlayer("cryxli");
		Assert.assertEquals("00000000-0000-0000-ffff-ffffaf6f0dcb", p
				.getUniqueId().toString());

		p = new DummyPlayer("foobar", "5fc42a74-235e-4534-be0a-64f2bf75d29a");
		Assert.assertEquals("5fc42a74-235e-4534-be0a-64f2bf75d29a", p
				.getUniqueId().toString());
	}

}
