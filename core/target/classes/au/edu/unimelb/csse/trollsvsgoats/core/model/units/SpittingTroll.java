package au.edu.unimelb.csse.trollsvsgoats.core.model.units;

import java.util.Map;

import au.edu.unimelb.csse.trollsvsgoats.core.model.units.Unit.State;

public class SpittingTroll extends Troll {

	@Override
	void init() {
		setSpeed(0);
		setForce(0);
		setCost(5);
	}

	@Override
	public void notifyColliedWithBack(Map<Integer, Unit> headTrolls, Map<Integer, Unit> headGoats){
		if(this.back()!=null && (!(this.back() instanceof ButtingGoat))){
			this.back().setSpeed(this.back().speed() / 2);
			this.setState(State.SPECIALABILITY);
		}
	}


	@Override
	public boolean isOnTrollSide() {
		return false;
	}

	@Override
	public String type() {
		return "spitting";
	}
}
