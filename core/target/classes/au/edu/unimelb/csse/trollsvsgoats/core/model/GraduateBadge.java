package au.edu.unimelb.csse.trollsvsgoats.core.model;

public class GraduateBadge extends Badge {

	@Override
	public boolean achieved(GameModel game) {
		return (game.maxCompletedLevel() >= 5);
	}

	@Override
	public String name() {
		return "graduate";
	}

	@Override
	public String displayName() {
		return "Graduate";
	}

	@Override
	public String description() {
		return "Completed levels 1 to 5";
	}

}
