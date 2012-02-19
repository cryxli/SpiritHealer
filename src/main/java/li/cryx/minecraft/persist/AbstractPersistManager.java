package li.cryx.minecraft.persist;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractPersistManager {

	protected final JavaPlugin plugin;

	public AbstractPersistManager(final JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public abstract void deleteItems(final Player player);

	public abstract boolean hasInventory(Player player);

	public abstract boolean persistItems(final Player player);

	public abstract List<ItemStack> restoreItems(final Player player);

	public void shutdown() {
	}
}
