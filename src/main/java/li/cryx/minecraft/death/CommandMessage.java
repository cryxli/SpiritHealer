package li.cryx.minecraft.death;

import li.cryx.minecraft.death.persist.FragsInfo;
import li.cryx.minecraft.util.LivingEntityAffection;
import li.cryx.minecraft.util.LivingEntityType;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum CommandMessage {
	INSTANCE;

	private void sendDetailed(final CommandSender sender, final FragsInfo info) {
		sender.sendMessage(ChatColor.GOLD + "===== Your kills =====");
		boolean kills = false;
		for (LivingEntityType t : LivingEntityType.values()) {
			if (info.getKillers(t) > 0 || info.getKills(t) > 0) {
				StringBuffer buf = new StringBuffer();
				buf.append(ChatColor.YELLOW);
				buf.append(t.toString());
				buf.append(' ');
				buf.append(ChatColor.GREEN);
				buf.append(info.getKills(t));
				buf.append(ChatColor.YELLOW);
				buf.append("/");
				buf.append(ChatColor.RED);
				buf.append(info.getKillers(t));
				sender.sendMessage(buf.toString());
				kills = true;
			}
		}
		if (!kills) {
			sender.sendMessage(ChatColor.GOLD + "no kills/deaths yet");
		}
	}

	public void sendFragsAnswer(final CommandSender sender,
			final String[] args, final String fragsFormat, final FragsInfo info) {
		if (args.length == 0) {
			// no arg to command, use config settings
			if ("full".equalsIgnoreCase(fragsFormat)) {
				sendFull(sender, info);
			} else if ("detailed".equalsIgnoreCase(fragsFormat)) {
				sendDetailed(sender, info);
			} else {
				sendMinimal(sender, info);
			}
		} else {
			// try to interpret command arg
			String s = args[0];
			if (s == null || s.length() == 0 || s.startsWith("m")) {
				sendMinimal(sender, info);
			} else if (s.startsWith("f")) {
				sendFull(sender, info);
			} else {
				sendDetailed(sender, info);
			}
		}
	}

	/** Send the complete list of kills and deaths to the player. */
	private void sendFull(final CommandSender sender, final FragsInfo info) {
		sender.sendMessage(ChatColor.GOLD + "===== Your kills =====");

		StringBuffer buf = new StringBuffer();
		buf.append(ChatColor.YELLOW);
		buf.append("PVP: ");
		buf.append(ChatColor.GREEN);
		buf.append(info.getKills(LivingEntityAffection.PVP));
		buf.append(ChatColor.YELLOW);
		buf.append("/");
		buf.append(ChatColor.RED);
		buf.append(info.getKillers(LivingEntityAffection.PVP));
		sender.sendMessage(buf.toString());

		buf = new StringBuffer();
		buf.append(ChatColor.YELLOW);
		buf.append("Aggressive mobs: ");
		buf.append(ChatColor.GREEN);
		buf.append(info.getKills(LivingEntityAffection.AGGRESSIVE));
		buf.append(ChatColor.YELLOW);
		buf.append("/");
		buf.append(ChatColor.RED);
		buf.append(info.getKillers(LivingEntityAffection.AGGRESSIVE));
		sender.sendMessage(buf.toString());

		buf = new StringBuffer();
		buf.append(ChatColor.YELLOW);
		buf.append("Neutral mobs: ");
		buf.append(ChatColor.GREEN);
		buf.append(info.getKills(LivingEntityAffection.NEUTRAL));
		buf.append(ChatColor.YELLOW);
		buf.append("/");
		buf.append(ChatColor.RED);
		buf.append(info.getKillers(LivingEntityAffection.NEUTRAL));
		sender.sendMessage(buf.toString());

		buf = new StringBuffer();
		buf.append(ChatColor.YELLOW);
		buf.append("Friendly mobs: ");
		buf.append(ChatColor.GREEN);
		buf.append(info.getKills(LivingEntityAffection.FRIENDLY));
		buf.append(ChatColor.YELLOW);
		buf.append("/");
		buf.append(ChatColor.RED);
		buf.append(info.getKillers(LivingEntityAffection.FRIENDLY));
		sender.sendMessage(buf.toString());
	}

	/** Send the minimalistic list of kills and deaths to the player. */
	private void sendMinimal(final CommandSender sender, final FragsInfo info) {
		int pvp = info.getKills(LivingEntityAffection.PVP)
				- info.getKillers(LivingEntityAffection.PVP);
		int aggro = info.getKills(LivingEntityAffection.AGGRESSIVE)
				- info.getKillers(LivingEntityAffection.AGGRESSIVE);
		int neutral = info.getKills(LivingEntityAffection.NEUTRAL)
				- info.getKillers(LivingEntityAffection.NEUTRAL);
		int friend = info.getKills(LivingEntityAffection.FRIENDLY)
				- info.getKillers(LivingEntityAffection.FRIENDLY);

		sender.sendMessage(ChatColor.GOLD + "===== Your kills =====");

		StringBuffer buf = new StringBuffer();
		buf.append(ChatColor.YELLOW);
		buf.append("PVP: ");
		if (pvp == 0) {
			buf.append(ChatColor.WHITE);
		} else if (pvp < 0) {
			buf.append(ChatColor.RED);
		} else {
			buf.append(ChatColor.GREEN);
		}
		buf.append(pvp);
		sender.sendMessage(buf.toString());

		buf = new StringBuffer();
		buf.append(ChatColor.YELLOW);
		buf.append("Aggressive mobs: ");
		if (aggro == 0) {
			buf.append(ChatColor.WHITE);
		} else if (aggro < 0) {
			buf.append(ChatColor.RED);
		} else {
			buf.append(ChatColor.GREEN);
		}
		buf.append(aggro);
		sender.sendMessage(buf.toString());

		buf = new StringBuffer();
		buf.append(ChatColor.YELLOW);
		buf.append("Neutral mobs: ");
		if (neutral == 0) {
			buf.append(ChatColor.WHITE);
		} else if (neutral < 0) {
			buf.append(ChatColor.RED);
		} else {
			buf.append(ChatColor.GREEN);
		}
		buf.append(neutral);
		sender.sendMessage(buf.toString());

		buf = new StringBuffer();
		buf.append(ChatColor.YELLOW);
		buf.append("Friendly mobs: ");
		if (friend == 0) {
			buf.append(ChatColor.WHITE);
		} else if (friend < 0) {
			buf.append(ChatColor.RED);
		} else {
			buf.append(ChatColor.GREEN);
		}
		buf.append(friend);
		sender.sendMessage(buf.toString());
	}

}
