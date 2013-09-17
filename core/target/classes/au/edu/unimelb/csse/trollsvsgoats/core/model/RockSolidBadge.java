package au.edu.unimelb.csse.trollsvsgoats.core.model;

public class RockSolidBadge extends Badge {
	private int num;

	public RockSolidBadge (int levelID) {
		super();
		num = levelID;
	}
	
	@Override
	public boolean achieved(GameModel game) {
		if (game.levelIndex() == num && !game.momentOverZero())
			return true;
		else
			return false;
	}

	@Override
	public String name() {
		return "rock_solid_" + num;
	}

	@Override
	public String displayName() {
		return "";
	}

	@Override
	public String description() {
		return "";
	}

}
