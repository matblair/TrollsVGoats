package au.edu.unimelb.csse.trollsvsgoats.core.model.units;

import java.util.Map;

public class Obstacle extends Troll {

    @Override
    void init() {
        setSpeed(0);
        setForce(0);
    }

    @Override
    public void notifyColliedWithBack(Map<Integer, Unit> headTrolls, Map<Integer, Unit> headGoats) {
        this.back().setState(State.REMOVED);
    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub

    }

    @Override
    public String type() {
        return "obstacle";
    }

}
