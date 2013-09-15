package au.edu.unimelb.csse.trollsvsgoats.core.model;

public class ManDownBadge extends Badge {

	@Override
	public boolean achieved(GameModel game) {
            return game.manDown();
	}

	@Override
	public String name() {
		return "man_down";
	}

	@Override
	public String displayName() {
		return "Man Down";
	}

	@Override
	public String description() {
		return "Lost a troll down a hole";
	}

}
