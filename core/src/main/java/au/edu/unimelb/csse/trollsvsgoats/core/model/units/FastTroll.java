package au.edu.unimelb.csse.trollsvsgoats.core.model.units;

public class FastTroll extends Troll {

    @Override
    void init() {
        setSpeed(4);
        setForce(2);
        setCost(5);
    }
    
    @Override
    public String type() {
        return "fast";
    }

}
