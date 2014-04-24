package li.cryx.minecraft.death.i18n;

public interface LangKeys {

	public static final class FRAGS {

		public static final String TITLE = "frags.title";

		public static final String NO_DATA = "frags.nodata";

		public static final String DETAIL_LINE = "frags.detail";

		public static final String MIN_LINE = "frags.mini";

		public static final String TYPE_PVP = "frags.type.pvp";
		public static final String TYPE_AGGRO = "frags.type.aggro";
		public static final String TYPE_NEUTRAL = "frags.type.neutral";
		public static final String TYPE_FRIENDLY = "frags.type.friendly";
	}

	public static final class SPIRITHEALER {

		/** Msg when player does not have items to recover */
		public static final String NOT_DEAD = "YouAreNotDead";

		/** warning when approaching an altar with anything but bare hands */
		public static final String WARNING = "NoWeapons";

		/** second warning, first punishment; half food bar */
		public static final String WARNING1 = "Warn1";

		/** third warning, second punishment; half health bar */
		public static final String WARNING2 = "Warn2";

		/** last warning, third punishment; kick player */
		public static final String WARNING3 = "Warn3";
	}

}
