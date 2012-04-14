package li.cryx.minecraft.death.persist.db.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Entity
@Table(name = "sh_item")
public class Item {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(nullable = false)
	private String player;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Material material;

	@Column(name = "data")
	private Byte data;

	@Column(nullable = false)
	private int amount;

	@Column(name = "dur")
	private short durability;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "item_id", referencedColumnName = "id")
	private List<Enchant> enchantments;

	public void addEnchantment(final Enchant en) {
		if (enchantments == null) {
			enchantments = new LinkedList<Enchant>();
		}
		enchantments.add(en);
	}

	public void addEnchantment(final Enchantment effect, final int level) {
		addEnchantment(new Enchant(effect, level));
	}

	public int getAmount() {
		return amount;
	}

	public Byte getData() {
		return data;
	}

	public short getDurability() {
		return durability;
	}

	public List<Enchant> getEnchantments() {
		return enchantments;
	}

	public Long getId() {
		return id;
	}

	public ItemStack getItemStack() {
		ItemStack item = new ItemStack(material, amount, durability, data);
		// TODO enchantment
		for (Enchant e : getEnchantments()) {
			item.addEnchantment(e.getEnchantment(), e.getLevel());
		}
		return item;
	}

	public Material getMaterial() {
		return material;
	}

	public String getPlayer() {
		return player;
	}

	public void setAmount(final int amount) {
		this.amount = amount;
	}

	public void setData(final Byte data) {
		this.data = data;
	}

	public void setDurability(final short durability) {
		this.durability = durability;
	}

	public void setEnchantments(final List<Enchant> enchantments) {
		this.enchantments = enchantments;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setItemStack(final ItemStack item) {
		setMaterial(item.getType());
		setData(item.getData().getData());
		setAmount(item.getAmount());
		setDurability(item.getDurability());
		// TODO enchantment
		setEnchantments(null);
		for (Enchantment e : item.getEnchantments().keySet()) {
			addEnchantment(e, item.getEnchantmentLevel(e));
		}
	}

	public void setMaterial(final Material material) {
		this.material = material;
	}

	public void setPlayer(final String player) {
		this.player = player;
	}

	public void setPlayerEntity(final Player player) {
		setPlayer(player.getName());
	}

	public String toJson() {
		StringBuffer buf = new StringBuffer();
		buf.append('{');
		buf.append("\"id\":").append(id);
		buf.append(',');
		buf.append("\"material\":\"").append(material).append('"');
		buf.append(',');
		buf.append("\"data\":");
		if (data == null) {
			buf.append("null");
		} else {
			buf.append('"').append(data).append('"');
		}
		buf.append(',');
		buf.append("\"amount\":").append(amount);
		buf.append(',');
		buf.append("\"durability\":").append(durability);
		buf.append(',');
		buf.append("\"player\":\"").append(player).append('"');
		buf.append('}');
		return buf.toString();
	}

	@Override
	public String toString() {
		return "Item" + toJson();
	}
}
