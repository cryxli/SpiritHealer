package li.cryx.minecraft.death.perm;

import li.cryx.minecraft.util.PermNode;

import org.bukkit.permissions.Permissible;

public class PermissionsManager {

	public boolean hasPermission(final PermNode node,
			final Permissible permissible) {
		return permissible.hasPermission(node.getNode());
	}

}
