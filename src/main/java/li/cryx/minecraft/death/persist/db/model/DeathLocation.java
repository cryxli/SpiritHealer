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
