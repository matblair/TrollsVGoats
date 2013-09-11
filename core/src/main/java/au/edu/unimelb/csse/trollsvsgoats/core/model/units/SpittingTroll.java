package au.edu.unimelb.csse.trollsvsgoats.core.model.units;

import java.util.Map;

import au.edu.unimelb.csse.trollsvsgoats.core.model.units.Unit.State;

public class SpittingTroll extends Troll {

	@Override
	void init() {
		setSpeed(0);
		setForce(0);
		setCost(15);
	}

	@Override
	public void notifyColliedWithBack(Map<Integer, Unit> headTrolls, Map<Integer, Unit> headGoats){
		if(this.back()!=null && (!(this.back() instanceof ButtingGoat))){
			this.back().setSpeed(this.back().speed() / 2);
			this.setState(State.SPECIALABILITY);

			//Need to swap around in linked list. 
			//First handle the main.
			if(this.front()!=null){
				Unit u = this.front();
				//Add the front to the back, making sure the link continues
				u.setBack(this.back());
				if (this.back()!=null){
					this.back().setFront(u);
				}

				//Add the correct units to this 
				this.setFront(u.front());
				if(this.front()!=null){
					this.front().setBack(this);
				}
				this.setBack(u);

				//Now update the game linked list if it is now the front.

				if(this.front()==null){
					headGoats.put(this.square().lane(), this);
				} 
			}	else {
				//Then we are the head.
				if(this.back()!=null){
					System.out.println(this.back().type());
				}
				Unit u = this.back();

				//Add ourselves 
				this.setFront(u);		
				this.setBack(u.back());


				if(this.front()!=null){
					System.out.println(this.front().type());
				}

				//Add ourselves back to the list
				u.setFront(null);
				u.setBack(this);

				//Update the head
				if(u!=null){
					headGoats.put(u.square().lane(), u);
				}

			}
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
