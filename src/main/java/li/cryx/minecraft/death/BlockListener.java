package li.cryx.minecraft.death;

import org.bukkit.Material;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Listen for new altars. Whenever a player (probably op) will place an
 * EnderStone on an Obsidian block, the EnderStone will be treated as a new
 * Spirit Altar. This only holds for worlds with type NORMAL, not End, nor
 * Nether.
 * 
 * @author cryxli
 */
public class BlockListener implements Listener {

	private final Death plugin;

	public BlockListener(final Death plugin) {
		this.plugin = plugin;
	}

	/**
	 * Listen to block placement events.
	 * 
	 * @param event
	 */
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
		plugin.addAltarLocation(block.getLocation());
	}

}
