package li.cryx.minecraft.death.persist.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bukkit.enchantments.Enchantment;

@Entity
@Table(name = "sh_enchant")
public class Enchant {

	@Id
	private Long id;

	@Column
	private String effect;

	@Column
	private int level;

	public Enchant() {
	}

	public Enchant(final Enchantment effect, final int level) {
		setEffect(effect.getName());
		setLevel(level);
	}

	public String getEffect() {
		return effect;
	}

	public Enchantment getEnchantment() {
		return Enchantment.getByName(effect);
	}

	public Long getId() {
		return id;
	}

	public int getLevel() {
		return level;
	}

	public void setEffect(final String effect) {
		this.effect = effect;
	}

	public void setEnchantment(final Enchantment effect) {
		setEffect(effect.getName());
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setLevel(final int level) {
		this.level = level;
	}

}
