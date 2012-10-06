package li.cryx.minecraft.util;

import org.bukkit.permissions.Permissible;

public enum PermNode {
	/** SpiritHealer will prevent item drops on death. */
	INVENTORY("spirithealer.inventory"),
	/** SpiritHealer will count deaths and kills. */
	FRAGS("spirithealer.frags"),
	/** Player can build new altars. */
	ALTAR("spirithealer.altar");

	private String node;

	private PermNode(String node) {
		this.node = node;
	}

	public String getNode() {
		return node;
	}

	public boolean hasPermission(Permissible perm) {
		return perm.hasPermission(getNode());
	}
}
