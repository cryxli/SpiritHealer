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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import li.cryx.minecraft.util.LivingEntityType;

import org.bukkit.entity.Player;

/**
 * Structure to store kills and deaths of a player and {@link LivingEntityType}.
 * 
 * @author cryxli
 */
@Entity
@Table(name = "sh_kills")
public class Kills {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "player", nullable = false)
	private String player;

	@Column(name = "player_uuid", nullable = false)
	private String playerUuid;

	@Column(name = "entity", nullable = false)
	@Enumerated(EnumType.STRING)
	private LivingEntityType entity;

	@Column(name = "kills", nullable = false)
	private int kills;

	@Column(name = "deaths", nullable = false)
	private int deaths;

	public int getDeaths() {
		return deaths;
	}

	public LivingEntityType getEntity() {
		return entity;
	}

	public Long getId() {
		return id;
	}

	public int getKills() {
		return kills;
	}

	public String getPlayer() {
		return player;
	}

	public String getPlayerUuid() {
		return playerUuid;
	}

	public void increaseDeaths() {
		setDeaths(getDeaths() + 1);
	}

	public void increaseKills() {
		setKills(getKills() + 1);
	}

	public void setDeaths(final int deaths) {
		this.deaths = deaths;
	}

	public void setEntity(final LivingEntityType entity) {
		this.entity = entity;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setKills(final int kills) {
		this.kills = kills;
	}

	public void setPlayer(final String player) {
		this.player = player;
	}

	public void setPlayerEntity(final Player player) {
		setPlayer(player.getName());
		setPlayerUuid(player.getUniqueId().toString());
	}

	public void setPlayerUuid(final String playerUuid) {
		this.playerUuid = playerUuid;
	}

	public String toJson() {
		StringBuffer buf = new StringBuffer();
		buf.append('{');
		buf.append("\"id\":").append(id);
		buf.append(',');
		if (player == null) {
			buf.append("\"player\":null");
		} else {
			buf.append("\"player\":\"").append(player).append('"');
		}
		buf.append(',');
		if (entity == null) {
			buf.append("\"entity\":null");
		} else {
			buf.append("\"entity\":\"").append(entity).append('"');
		}
		buf.append(',');
		buf.append("\"kills\":").append(kills);
		buf.append(',');
		buf.append("\"death\":").append(deaths);
		buf.append('}');
		return buf.toString();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ':' + toJson();

	}
}
