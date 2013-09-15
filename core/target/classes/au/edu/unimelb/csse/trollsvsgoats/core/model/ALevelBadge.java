package au.edu.unimelb.csse.trollsvsgoats.core.model;

public class ALevelBadge extends Badge {

    @Override
    public boolean achieved(GameModel game) {
        boolean achieved;
        if (achieved = game.isLevelCompleted() && !game.momentOverZero())
            return true;
        else
            return false;
    }

    @Override
    public String name() {
        return "a_level";
    }

    @Override
    public String displayName() {
        return "A Level";
    }

    @Override
    public String description() {
        return "Complete a level without the gate leaving 0 Nm";
    }

}
