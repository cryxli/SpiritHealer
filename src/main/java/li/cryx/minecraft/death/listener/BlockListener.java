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
package li.cryx.minecraft.death.listener;

import li.cryx.minecraft.death.ISpiritHealer;
import li.cryx.minecraft.util.PermNode;

import org.bukkit.World.Environment;
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

	private final ISpiritHealer plugin;

	public BlockListener(final ISpiritHealer plugin) {
		this.plugin = plugin;
	}

	/**
	 * Listen to block placement events.
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(final BlockPlaceEvent event) {
		if (!PermNode.ALTAR.hasPermission(event.getPlayer())) {
			return;
		}

		Block block = event.getBlockPlaced();
		if (block.getWorld().getEnvironment() != Environment.NORMAL) {
			return;
		} else if (block.getType() != plugin.getAltarMaterial()) {
			return;
		}

		Block below = block.getRelative(BlockFace.DOWN);
		if (below.getType() != plugin.getAltarBaseMaterial()) {
			return;
		}

		// AltarMaterial was placed on AltarBaseMaterial -> new altar
		plugin.addAltarLocation(block.getLocation());
	}

}
