package au.edu.unimelb.csse.trollsvsgoats.core.model;

public class FirstBloodBadge extends Badge {

    @Override
    public boolean achieved(GameModel game) {
        return game.goatEaten();
    }

    @Override
    public String name() {
        return "first_blood";
    }

    @Override
    public String displayName() {
        return "First Blood";
    }

    @Override
    public String description() {
        return "Eaten a goat";
    }
}
