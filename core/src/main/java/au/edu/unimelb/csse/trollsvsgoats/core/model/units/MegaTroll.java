package au.edu.unimelb.csse.trollsvsgoats.core.model.units;

public class MegaTroll extends Troll {

    @Override
    void init() {
        setSpeed(1);
        setForce(4);
        setCost(20);
    }

    @Override
    public String type() {
        return "mega";
    }
    
}
