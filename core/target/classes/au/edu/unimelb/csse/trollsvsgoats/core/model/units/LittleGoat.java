package au.edu.unimelb.csse.trollsvsgoats.core.model.units;

public class LittleGoat extends Goat {

    @Override
    void init() {
        setSpeed(2);
        setForce(2);
    }
    
    @Override
    public String type() {
        return "little";
    }

}
