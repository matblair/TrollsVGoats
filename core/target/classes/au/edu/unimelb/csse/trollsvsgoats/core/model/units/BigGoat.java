package au.edu.unimelb.csse.trollsvsgoats.core.model.units;

public class BigGoat extends Goat {

    @Override
    void init() {
        setSpeed(1);
        setForce(8);
    }

    @Override
    public String type() {
        return "big";
    }

}
