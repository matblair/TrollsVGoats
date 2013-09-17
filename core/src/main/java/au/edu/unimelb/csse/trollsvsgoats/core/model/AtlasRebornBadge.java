package au.edu.unimelb.csse.trollsvsgoats.core.model;

public class AtlasRebornBadge extends Badge {

	@Override
	public boolean achieved(GameModel game) {
		for (int i=1; i<=7; i++)
			if (!game.rockSolidCompletedForLevel(i))
				return false;
		return true;
	}

	@Override
	public String name() {
		return "atlas_reborn";
	}

	@Override
	public String displayName() {
		return "Atlas Reborn";
	}

	@Override
	public String description() {
		return "Completed all levels without the gate leaving 0 Nm";
	}

}
