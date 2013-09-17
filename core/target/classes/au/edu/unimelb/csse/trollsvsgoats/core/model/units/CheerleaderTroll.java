package au.edu.unimelb.csse.trollsvsgoats.core.model.units;

import java.util.Map;

public class CheerleaderTroll extends Troll {

    @Override
    void init() {
        setSpeed(2);
        setForce(2);
        setCost(25);
    }

    @Override
    public void notifyColliedWithFront(Map<Integer, Unit> headTrolls, Map<Integer, Unit> headGoats) {
        Unit unit = this.front();
        if (unit.state().equals(State.PUSHING)) {
            while (unit != null) {
                unit.setForce(2 * unit.force());
                unit = unit.front();
            }
        }
    }

    @Override
    public String type() {
        return "cheerleader";
    }

}
