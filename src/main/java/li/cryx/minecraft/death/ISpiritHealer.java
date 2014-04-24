package li.cryx.minecraft.death;

import java.util.logging.Logger;

import li.cryx.minecraft.death.i18n.ITranslator;
import li.cryx.minecraft.death.persist.AbstractPersistManager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public interface ISpiritHealer {

	void addAltarLocation(Location loc);

	Material getAltarBaseMaterial();

	Material getAltarMaterial();

	// FileConfiguration getConfig();

	Logger getLogger();

	AbstractPersistManager getPersist();

	ITranslator getTranslator();

	boolean isAltar(Location loc);

	void restoreItems(Player player);

}
