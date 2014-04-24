package li.cryx.minecraft.death.i18n;

import org.bukkit.command.CommandSender;

public interface ITranslator {

	void sendMessage(CommandSender sender, final String msgKey,
			final Object... arguments);

	String translate(final CommandSender sender, final String msgKey,
			final Object... arguments);

}
