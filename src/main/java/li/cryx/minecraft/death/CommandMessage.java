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
package li.cryx.minecraft.death;

import li.cryx.minecraft.death.i18n.ITranslator;
import li.cryx.minecraft.death.i18n.LangKeys;
import li.cryx.minecraft.death.persist.FragsInfo;
import li.cryx.minecraft.util.LivingEntityAffection;
import li.cryx.minecraft.util.LivingEntityType;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum CommandMessage {
	INSTANCE;

	private static ITranslator i18n;

	public static void setPlugin(final ISpiritHealer plugin) {
		CommandMessage.i18n = plugin.getTranslator();
	}

	private ChatColor color(final int value) {
		if (value == 0) {
			return ChatColor.WHITE;
		} else if (value < 0) {
			return ChatColor.RED;
		} else {
			return ChatColor.GREEN;
		}
	}

	private void sendDetailed(final CommandSender sender, final FragsInfo info) {
		i18n.sendMessage(sender, LangKeys.FRAGS.TITLE);
		boolean kills = false;
		for (LivingEntityType t : LivingEntityType.values()) {
			if (info.getDeaths(t) > 0 || info.getKills(t) > 0) {
				i18n.sendMessage( //
						sender, //
						LangKeys.FRAGS.DETAIL_LINE, //
						i18n.translate(sender, t.toString()), //
						info.getKills(t), //
						info.getDeaths(t) //
				);
				kills = true;
			}
		}
		if (!kills) {
			i18n.sendMessage(sender, LangKeys.FRAGS.NO_DATA);
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
		i18n.sendMessage(sender, LangKeys.FRAGS.TITLE);
		i18n.sendMessage( //
				sender, //
				LangKeys.FRAGS.DETAIL_LINE, //
				i18n.translate(sender, LangKeys.FRAGS.TYPE_PVP), //
				info.getKills(LivingEntityAffection.PVP), //
				info.getDeaths(LivingEntityAffection.PVP) //
		);
		i18n.sendMessage( //
				sender, //
				LangKeys.FRAGS.DETAIL_LINE, //
				i18n.translate(sender, LangKeys.FRAGS.TYPE_AGGRO), //
				info.getKills(LivingEntityAffection.AGGRESSIVE), //
				info.getDeaths(LivingEntityAffection.AGGRESSIVE) //
		);
		i18n.sendMessage( //
				sender, //
				LangKeys.FRAGS.DETAIL_LINE, //
				i18n.translate(sender, LangKeys.FRAGS.TYPE_NEUTRAL), //
				info.getKills(LivingEntityAffection.NEUTRAL), //
				info.getDeaths(LivingEntityAffection.NEUTRAL) //
		);
		i18n.sendMessage( //
				sender, //
				LangKeys.FRAGS.DETAIL_LINE, //
				i18n.translate(sender, LangKeys.FRAGS.TYPE_FRIENDLY), //
				info.getKills(LivingEntityAffection.FRIENDLY), //
				info.getDeaths(LivingEntityAffection.FRIENDLY) //
		);
	}

	/** Send the minimalistic list of kills and deaths to the player. */
	private void sendMinimal(final CommandSender sender, final FragsInfo info) {
		int pvp = info.getKills(LivingEntityAffection.PVP)
				- info.getDeaths(LivingEntityAffection.PVP);
		int aggro = info.getKills(LivingEntityAffection.AGGRESSIVE)
				- info.getDeaths(LivingEntityAffection.AGGRESSIVE);
		int neutral = info.getKills(LivingEntityAffection.NEUTRAL)
				- info.getDeaths(LivingEntityAffection.NEUTRAL);
		int friend = info.getKills(LivingEntityAffection.FRIENDLY)
				- info.getDeaths(LivingEntityAffection.FRIENDLY);

		i18n.sendMessage(sender, LangKeys.FRAGS.TITLE);
		i18n.sendMessage( //
				sender, //
				LangKeys.FRAGS.MIN_LINE, //
				i18n.translate(sender, LangKeys.FRAGS.TYPE_PVP), //
				color(pvp), //
				pvp //
		);
		i18n.sendMessage( //
				sender, //
				LangKeys.FRAGS.MIN_LINE, //
				i18n.translate(sender, LangKeys.FRAGS.TYPE_AGGRO), //
				color(aggro), //
				aggro //
		);
		i18n.sendMessage( //
				sender, //
				LangKeys.FRAGS.MIN_LINE, //
				i18n.translate(sender, LangKeys.FRAGS.TYPE_NEUTRAL), //
				color(neutral), //
				neutral //
		);
		i18n.sendMessage( //
				sender, //
				LangKeys.FRAGS.MIN_LINE, //
				i18n.translate(sender, LangKeys.FRAGS.TYPE_FRIENDLY), //
				color(friend), //
				friend //
		);
	}
}
