package au.edu.unimelb.csse.trollsvsgoats.core.model.units;

import java.util.Map;

public class ButtingGoat extends Goat {

	@Override
	void init() {
		setSpeed(1);
		setForce(2);
	}

	@Override
	public void notifyColliedWithFront(Map<Integer, Unit> headTrolls, Map<Integer, Unit> headGoats) {
		if (this.front() instanceof DiggingTroll
				|| this.front() instanceof SpittingTroll) {

			//Need to swap around in linked list. 
			Unit u = this.front();
			//Add the front to the back, making sure the link continues
			u.setBack(this.back());
			if (this.back()!=null){
				System.out.println(this.back().type());
				this.back().setFront(u);
			}

			//Add the correct units to this 
			this.setFront(u.front());
			if(this.front()!=null){
				this.front().setBack(this);
			}
			this.setBack(u);
			
			//Tell the unit to die
			u.setState(State.DYING);

			//Now update the game linked list if it is now the front.
			if(this.front()==null){
				headGoats.put(this.square().lane(), this);
			}
		}
	}

	@Override
	public String ability() {
		return "will remove digging and spitting trolls";
	}

	@Override
	public String type() {
		return "butting";
	}

}
