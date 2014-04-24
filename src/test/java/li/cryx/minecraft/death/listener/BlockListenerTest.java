package li.cryx.minecraft.death.listener;

import java.util.logging.Logger;

import li.cryx.minecraft.death.Death;
import li.cryx.minecraft.death.ISpiritHealer;
import li.cryx.minecraft.death.perm.PermissionsManager;
import li.cryx.minecraft.util.PermNode;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.PluginManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * This test ensures that the block-place-listener of the plugin only calls the
 * {@link Death#addAltarLocation(Location)} method when all conditions are met.
 * 
 * @author cryxli
 */
public class BlockListenerTest {

	/**
	 * Create a mocked server. This ensures that the {@link PermNode} uses the
	 * delegate implementation from {@link PermissionsManager}.
	 */
	@BeforeClass
	public static void initBukkit() {
		final PluginManager mng = Mockito.mock(PluginManager.class);

		final Server server = Mockito.mock(Server.class);
		Mockito.when(server.getPluginManager()).thenReturn(mng);
		Mockito.when(server.getLogger())
				.thenReturn(Logger.getAnonymousLogger());
		Bukkit.setServer(server);
	}

	/**
	 * We want to check how often the method
	 * {@link Death#addAltarLocation(Location)} was called.
	 */
	private ISpiritHealer plugin;

	/** The listener we want to test. */
	private BlockListener listener;

	private void checkNever() {
		Mockito.verify(plugin, Mockito.never()).addAltarLocation(
				Mockito.any(Location.class));
	}

	private Block getBlockOnBlock(final Location loc,
			final Material placedMaterial, final Material baseMaterial) {
		final Block base = Mockito.mock(Block.class);
		Mockito.when(base.getType()).thenReturn(baseMaterial);

		final Block placed = Mockito.mock(Block.class);
		Mockito.when(placed.getType()).thenReturn(placedMaterial);
		Mockito.when(placed.getLocation()).thenReturn(loc);
		Mockito.when(placed.getWorld()).thenReturn(loc.getWorld());
		Mockito.when(placed.getRelative(BlockFace.DOWN)).thenReturn(base);

		return placed;
	}

	private Block getDirtOnObsidian(final Location loc) {
		return getBlockOnBlock(loc, Material.DIRT, Material.OBSIDIAN);
	}

	private Block getEnderStoneOnObsidian(final Location loc) {
		return getBlockOnBlock(loc, Material.ENDER_STONE, Material.OBSIDIAN);
	}

	private Block getEnderStoneOnSand(final Location loc) {
		return getBlockOnBlock(loc, Material.ENDER_STONE, Material.SAND);
	}

	private World getNetherWorld() {
		final World world = Mockito.mock(World.class);
		Mockito.when(world.getEnvironment()).thenReturn(Environment.NETHER);
		return world;
	}

	private World getNormalWorld() {
		final World world = Mockito.mock(World.class);
		Mockito.when(world.getEnvironment()).thenReturn(Environment.NORMAL);
		return world;
	}

	private Player getPlayerWithNode() {
		final Player player = Mockito.mock(Player.class);
		Mockito.when(player.hasPermission(Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(player.isOp()).thenReturn(false);
		return player;
	}

	private Player getPlayerWithOp() {
		final Player player = Mockito.mock(Player.class);
		Mockito.when(player.hasPermission(Mockito.anyString())).thenReturn(
				false);
		Mockito.when(player.isOp()).thenReturn(true);
		return player;
	}

	private Player getPlayerWithoutNodeOrOp() {
		final Player player = Mockito.mock(Player.class);
		PermNode.setPermissionsManager(new PermissionsManager());
		Mockito.when(player.hasPermission(Mockito.anyString())).thenReturn(
				false);
		return player;
	}

	@Before
	public void initListener() {
		plugin = Mockito.mock(ISpiritHealer.class);
		Mockito.when(plugin.getAltarMaterial())
				.thenReturn(Material.ENDER_STONE);
		Mockito.when(plugin.getAltarBaseMaterial()).thenReturn(
				Material.OBSIDIAN);

		listener = new BlockListener(plugin);
	}

	@Test
	public void placeAltarInNether() {
		// prepare
		final Location loc = new Location(getNetherWorld(), 1, 2, 3);

		// test
		final BlockPlaceEvent event = new BlockPlaceEvent( //
				getEnderStoneOnObsidian(loc), //
				null, //
				null, //
				null, //
				getPlayerWithOp(), //
				true //
		);
		listener.onBlockPlace(event);

		// verify
		checkNever();
	}

	@Test
	public void placeAltarWithOp() {
		// prepare
		final Location loc = new Location(getNormalWorld(), 1, 2, 3);

		// test
		final BlockPlaceEvent event = new BlockPlaceEvent( //
				getEnderStoneOnObsidian(loc), //
				null, //
				null, //
				null, //
				getPlayerWithOp(), //
				true //
		);
		listener.onBlockPlace(event);

		// verify - called once
		Mockito.verify(plugin).addAltarLocation(loc);
	}

	@Test
	public void placeAltarWithPermNode() {
		// prepare
		final Location loc = new Location(getNormalWorld(), 1, 2, 3);

		// test
		final BlockPlaceEvent event = new BlockPlaceEvent( //
				getEnderStoneOnObsidian(loc), //
				null, //
				null, //
				null, //
				getPlayerWithNode(), //
				true //
		);
		listener.onBlockPlace(event);

		// verify - called once
		Mockito.verify(plugin).addAltarLocation(loc);
	}

	@Test
	public void placeAlterPlayerNotAllowed() {
		// prepare
		final Block placed = Mockito.mock(Block.class);

		// test
		final BlockPlaceEvent event = new BlockPlaceEvent( //
				placed, //
				null, //
				null, //
				null, //
				getPlayerWithoutNodeOrOp(), //
				true //
		);
		listener.onBlockPlace(event);

		// verify
		checkNever();
	}

	@Test
	public void placeDirtOnObsidian() {
		// prepare
		final Location loc = new Location(getNormalWorld(), 1, 2, 3);

		// test
		final BlockPlaceEvent event = new BlockPlaceEvent( //
				getDirtOnObsidian(loc), //
				null, //
				null, //
				null, //
				getPlayerWithNode(), //
				true //
		);
		listener.onBlockPlace(event);

		// verify
		checkNever();
	}

	@Test
	public void placeEnderStoneOnSand() {
		// prepare
		final Location loc = new Location(getNormalWorld(), 1, 2, 3);

		// test
		final BlockPlaceEvent event = new BlockPlaceEvent( //
				getEnderStoneOnSand(loc), //
				null, //
				null, //
				null, //
				getPlayerWithNode(), //
				true //
		);
		listener.onBlockPlace(event);

		// verify
		checkNever();
	}

}
