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

import li.cryx.minecraft.death.perm.PermissionsExManager;
import li.cryx.minecraft.death.perm.PermissionsManager;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;

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

	private static PermissionsManager perm;

	static {
		Plugin permEx = Bukkit.getServer().getPluginManager()
				.getPlugin("PermissionsEx");
		if (permEx != null) {
			perm = new PermissionsExManager();
		} else {
			perm = new PermissionsManager();
		}
	}

	public static void setPermissionsManager(final PermissionsManager perm) {
		PermNode.perm = perm;
	}

	private String node;

	private PermNode(final String node) {
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
	public boolean hasPermission(final Permissible permissible) {
		return perm.hasPermission(this, permissible);
	}
}
