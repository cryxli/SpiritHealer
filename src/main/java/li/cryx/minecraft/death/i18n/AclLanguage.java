package li.cryx.minecraft.death.i18n;

import li.cryx.minecraft.acl.Language;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class AclLanguage implements ITranslator {

	private final Language lng;

	public AclLanguage(final JavaPlugin plugin) {
		lng = Language.getInstanceFor(plugin);
	}

	@Override
	public void sendMessage(final CommandSender sender, final String msgKey,
			final Object... arguments) {
		lng.sendMessage(sender, msgKey, arguments);
	}

	@Override
	public String translate(final CommandSender sender, final String msgKey,
			final Object... arguments) {
		return lng.translate(sender, msgKey, arguments);
	}

}
