package au.edu.unimelb.csse.trollsvsgoats.core.model;

public class OCDBadge extends Badge {

	@Override
	public boolean achieved(GameModel game) {
		if (game.maxCompletedLevel() >= 9) {
			for (int i = 1; i <= 9; i++)
				if (game.scores().get(i) != 3)
					return false;
			
			return true;
		}
				
		return false;
	}

	@Override
	public String name() {
		return "ocd";
	}

	@Override
	public String displayName() {
		return "OCD";
	}

	@Override
	public String description() {
		return "Completed levels 1 to 9 with 3 star ratings";
	}

}
