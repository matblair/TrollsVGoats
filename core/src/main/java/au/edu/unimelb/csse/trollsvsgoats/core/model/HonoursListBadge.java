package au.edu.unimelb.csse.trollsvsgoats.core.model;

public class HonoursListBadge extends Badge {

	@Override
	public boolean achieved(GameModel game) {
		if (game.maxCompletedLevel() >= 5) {
			for (int i = 1; i <= 5; i++)
				if (game.scores().get(i) != 3)
					return false;
			
			return true;
		}
				
		return false;
	}

	@Override
	public String name() {
		return "honors_list";
	}

	@Override
	public String displayName() {
		return "Honours List";
	}

	@Override
	public String description() {
		return "Completed levels 1 to 5 with 3 star ratings";
	}

}
