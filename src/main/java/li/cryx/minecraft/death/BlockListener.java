package li.cryx.minecraft.death;

import org.bukkit.Material;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

	private final Death plugin;

	public BlockListener(final Death plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(final BlockPlaceEvent event) {
		Block block = event.getBlockPlaced();
		if (block.getWorld().getWorldType() != WorldType.NORMAL) {
			return;
		} else if (block.getType() != Material.ENDER_STONE) {
			return;
		}

		Block below = block.getRelative(BlockFace.DOWN);
		if (below.getType() != Material.OBSIDIAN) {
			return;
		}

		// EnderStone was placed on Obsidian -> new altar
		plugin.addAlterLocation(block.getLocation());
	}

}
