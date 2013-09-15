package au.edu.unimelb.csse.trollsvsgoats.core.model;

public class BringItOnBadge extends Badge {
    
    @Override
    public boolean achieved(GameModel game) {
        return game.threeCheerleadersBoost();
    }
    
    @Override
    public String name() {
        return "bring_it_on";
    }
    
    @Override
    public String displayName() {
        return "Bring It On";
    }
    
    @Override
    public String description() {
        return "Boosted 3 trolls simultaneously with a cheerleader troll";
    }
    
}
