package au.edu.unimelb.csse.trollsvsgoats.core.model.units;

public class FastGoat extends Goat {

    @Override
    void init() {
        setSpeed(4);
        setForce(4);
    }
    
    @Override
    public String type() {
        return "fast";
    }

}
