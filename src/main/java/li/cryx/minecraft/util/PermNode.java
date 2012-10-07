package li.cryx.minecraft.util;

import org.bukkit.permissions.Permissible;

/**
 * This enum contains all the permission nodes of the plugin.
 * 
 * @author cryxli
 */
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

	/**
	 * Get permission node's name.
	 * 
	 * @return The node's name.
	 */
	public String getNode() {
		return node;
	}

	/**
	 * Tests the given player or sender against the current permission.
	 * 
	 * @param permissible
	 *            A player or sender (console) to test
	 * @return <code>true</code>, if the <code>permissible</code> has the
	 *         current permission node.
	 */
	public boolean hasPermission(Permissible permissible) {
		return permissible.hasPermission(getNode());
	}
}
