package li.cryx.minecraft.death.persist.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import li.cryx.minecraft.util.LivingEntityType;

@Entity
@Table(name = "sh_kills")
public class Kills {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "player", nullable = false)
	private String player;

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
