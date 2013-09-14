package li.cryx.minecraft.death.perm;

import li.cryx.minecraft.util.PermNode;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import ru.tehkode.permissions.exceptions.PermissionsNotAvailable;

public class PermissionsExManager extends PermissionsManager {

	private final PermissionManager pex;

	public PermissionsExManager() throws PermissionsNotAvailable {
		PermissionManager pex;
		try {
			pex = PermissionsEx.getPermissionManager();
		} catch (PermissionsNotAvailable e) {
			pex = null;
		}
		this.pex = pex;
	}

	@Override
	public boolean hasPermission(final PermNode node,
			final Permissible permissible) {
		if (pex != null && permissible instanceof Player) {
			return pex.has((Player) permissible, node.getNode());
		} else {
			return super.hasPermission(node, permissible);
		}
	}

}
