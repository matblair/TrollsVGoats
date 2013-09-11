package au.edu.unimelb.csse.trollsvsgoats.core.model.units;

import java.util.Map;

public class DiggingTroll extends Troll {

	@Override
	void init() {
		setSpeed(0);
		setForce(0);
		setCost(20);
	}

	@Override
	public void notifyColliedWithBack(Map<Integer, Unit> headTrolls, Map<Integer, Unit> headGoats) {
		if(this.back()!=null && (!(this.back() instanceof ButtingGoat))){
			this.back().widget().layer.setVisible(false);
			this.back().setState(State.REMOVED);
			
			//Update the lists
			if(this.back().back()!=null){
				headGoats.put(this.back().back().square().lane(), (this.back().back()));
			}
			
			removeBack();
		}
	}

	@Override
	public boolean isOnTrollSide() {
		return false;
	}

	@Override
	public String ability() {
		return "deployed on the goat side,digs a hole that"
				+ "\ngoats fall into, can only be placed in the first 4 segments";
	}

	@Override
	public String type() {
		return "digging";
	}

}
