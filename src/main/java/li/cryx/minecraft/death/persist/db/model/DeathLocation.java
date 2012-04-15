package li.cryx.minecraft.death.persist.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Structure to store a death location.
 * 
 * @author cryxli
 */
@Entity
@Table(name = "sh_death_location")
public class DeathLocation {

	@Id
	private Long id;

	@Column
	private String player;

	@Column
	private String world;

	@Column(name = "pos_x")
	private double x;

	@Column(name = "pos_y")
	private double y;

	@Column(name = "pos_z")
	private double z;

	public DeathLocation() {
	}

	public Long getId() {
		return id;
	}

	public Location getLocation(final Server server) {
		World world = server.getWorld(this.world);
		if (world != null) {
			return new Location(world, x, y, z);
		} else {
			return null;
		}
	}

	public String getPlayer() {
		return player;
	}

	public String getWorld() {
		return world;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setLocation(final Location loc) {
		if (loc == null) {
			return;
		}

		setWorld(loc.getWorld().getName());
		setX(loc.getX());
		setY(loc.getY());
		setZ(loc.getZ());
	}

	public void setPlayer(final String player) {
		this.player = player;
	}

	public void setPlayerEntity(final Player player) {
		setPlayer(player.getName());
	}

	public void setWorld(final String world) {
		this.world = world;
	}

	public void setX(final double x) {
		this.x = x;
	}

	public void setY(final double y) {
		this.y = y;
	}

	public void setZ(final double z) {
		this.z = z;
	}

}
