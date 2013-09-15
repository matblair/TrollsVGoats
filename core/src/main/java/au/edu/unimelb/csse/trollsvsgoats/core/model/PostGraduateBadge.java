package au.edu.unimelb.csse.trollsvsgoats.core.model;

public class PostGraduateBadge extends Badge {

	@Override
	public boolean achieved(GameModel game) {
		return (game.maxCompletedLevel() >= 9);
	}

	@Override
	public String name() {
		return "post_graduate";
	}

	@Override
	public String displayName() {
		return "Post Graduate";
	}

	@Override
	public String description() {
		return "Completed levels 1 to 9";
	}

}
