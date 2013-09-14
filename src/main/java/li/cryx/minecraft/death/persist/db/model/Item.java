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

/**
 * Structure to store an item.
 * 
 * @author cryxli
 */
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
		ItemStack item = new ItemStack(material, amount, data);
		item.setDurability(durability);
		// enchantment
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

	@SuppressWarnings("deprecation")
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
