package li.cryx.minecraft.death.persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import li.cryx.minecraft.util.TypedProperties;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;

public class AltarLocations {

	private static final class KEYS {
		private static final String ALTAR_COUNT = "altar.count";

		private static final String ALTAR_WORLD = "altar.{0}.world";
		private static final String ALTAR_X = "altar.{0}.x";
		private static final String ALTAR_Y = "altar.{0}.y";
		private static final String ALTAR_Z = "altar.{0}.z";
	}

	public static AltarLocations load(final File inputFile, final Server server)
			throws IOException {
		TypedProperties prop = new TypedProperties();
		FileInputStream stream = new FileInputStream(inputFile);
		prop.load(stream);
		stream.close();

		AltarLocations altarLoc = new AltarLocations();
		altarLoc.deserialise(prop, server);
		altarLoc.setFile(inputFile);
		return altarLoc;
	}

	private final List<Location> altars = new LinkedList<Location>();

	private File file;

	public void addLocation(final Location loc) {
		if (loc != null && !isAltar(loc)) {
			altars.add(loc);
		}
	}

	public void addLocationAndSave(final Location loc) throws IOException {
		addLocation(loc);
		store(file);
	}

	public void Altarlocations() {
	}

	private void deserialise(final TypedProperties prop, final Server server) {
		int count = prop.getInteger(KEYS.ALTAR_COUNT);
		for (int i = 1; i <= count; i++) {
			String worldName = prop.getProperty(MessageFormat.format(
					KEYS.ALTAR_WORLD, i));
			World world = server.getWorld(worldName);
			if (world != null) {
				Location loc = new Location(world,
						prop.getInteger(MessageFormat.format(KEYS.ALTAR_X, i)),
						prop.getInteger(MessageFormat.format(KEYS.ALTAR_Y, i)),
						prop.getInteger(MessageFormat.format(KEYS.ALTAR_Z, i)));
				altars.add(loc);
			}
		}
	}

	public File getFile() {
		return file;
	}

	public boolean isAltar(final Location location) {
		return altars.contains(location);
	}

	public void setFile(final File file) {
		this.file = file;
	}

	public void store(final File outputFile) throws IOException {
		final TypedProperties prop = new TypedProperties();
		prop.setInteger(KEYS.ALTAR_COUNT, altars.size());
		int count = 1;
		for (Location loc : altars) {
			prop.setProperty(MessageFormat.format(KEYS.ALTAR_WORLD, count), loc
					.getWorld().getName());
			prop.setInteger(MessageFormat.format(KEYS.ALTAR_X, count),
					loc.getBlockX());
			prop.setInteger(MessageFormat.format(KEYS.ALTAR_Y, count),
					loc.getBlockY());
			prop.setInteger(MessageFormat.format(KEYS.ALTAR_Z, count),
					loc.getBlockZ());
			count++;
		}
		prop.store(outputFile, "Altar locations");
	}

}
