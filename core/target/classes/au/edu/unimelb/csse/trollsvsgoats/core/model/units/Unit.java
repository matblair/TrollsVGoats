package au.edu.unimelb.csse.trollsvsgoats.core.model.units;

import java.util.Map;

import au.edu.unimelb.csse.trollsvsgoats.core.model.Animation;
import au.edu.unimelb.csse.trollsvsgoats.core.model.Square;
import au.edu.unimelb.csse.trollsvsgoats.core.view.LevelScreenEx;
import playn.core.Image;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Icons;
import tripleplay.ui.layout.AbsoluteLayout;

public abstract class Unit {
	public static enum State {
		MOVING, PUSHING, REMOVED, BLOCKED, DYING, SPECIALABILITY, BEINGEATEN
	};

	private float oldX = 0; //->

	// An unit has a reference to its front and back unit.
	private Unit front;
	private Unit back;

	// How many seconds for a unit at normal speed to cover a segment.
	private float movementTime = 2;

	private float speed;
	private float force;

	// Controls the moving of the unit.
	private float moveDelay;
	//--> private float timer;

	Animation moveAnimation;
	Animation pushAnimation;
	Animation specialAnimation;
	Animation dyingAnimation;

	private Image icon;

	private Button widget;
	private Square square;
	private State state;
	private State prvState;
	//--> private boolean moved;
	private float tempNewX;

	protected Group parent;
	protected boolean canMove = false;
	protected float moveDelta = -1;
	private boolean movedAnimation = false;
	private boolean dead=false;


	public Unit() {
		init();
	}

	public void setParent(Group parent) {
		this.parent = parent;
	}

	/**
	 * Sets the speed, force and cost of this unit.
	 */
	abstract void init();

	public float speed() {
		return this.speed;
	}

	public void setMovementTime(float seconds) {
		this.movementTime = seconds;
		setSpeed(speed);
	}

	public void setSpeed(float speed) {
		this.speed = speed;
		moveDelay = movementTime * 1000 / speed;
		//--> timer = moveDelay;
		this.moveDelta = -1;
		if (moveAnimation != null)
			moveAnimation.setFrameTime(frameTime());
	}

	//--> public void setTimer(float timer) {
	//-->     this.timer = timer;
	//--> }

	public float force() {
		return this.force;
	}

	public void setForce(float force) {
		this.force = force;
	}

	/** The widget contains this unit. */
	public Button widget() {
		if (this.widget == null)
			widget = new Button(Icons.image(icon));
		return this.widget;
	}

	public void setLayer(Button layer) {
		this.widget = layer;
	}

	public void setSquare(Square square) {
		this.square = square;
		tempNewX = square.getX(); 
		canMove = false;
	}

	public void setTempNewX(float x){
		tempNewX = x;
	}

	public void move(Square square) {
		setSquare(square);
	}

	public boolean canMove() {
		return this.canMove;
	}

	public Square square() {
		return this.square;
	}

	//--> public float timer() {
	//-->     return this.timer;
	//--> }

	public float frameTime() {
		return 100 / this.speed;
	}

	/** Reset this unit to initial state. */
	public void reset() {
		init();
		//--> this.moved = false;
		//--> this.timer = moveDelay;
		this.moveDelta = -1;
		this.front = null;
		this.back = null;
		this.dead=false;
		this.movedAnimation=false;
		widget.layer.setVisible(true);
		widget.icon.update(icon != null ? Icons.image(icon) : Icons.image(moveAnimation.frame(0)));
		parent.add(AbsoluteLayout.at(widget(), tempNewX, square().getY()));
	}

	//--> public float updateTimer(float delta) {
	//-->     if (timer <= 0 && moved) {
	//-->          timer = moveDelay;
	//-->           moved = false;

	public boolean updatePosition(float timeDelta) { 

		if (this.state().equals(State.BEINGEATEN) || state().equals(State.DYING)){
			canMove = false;
			return false;
		}
		this.moveDelta = LevelScreenEx.SQUARE_WIDTH / (this.moveDelay / timeDelta); 

		tempNewX = tempNewX + (this instanceof Troll ? this.moveDelta : - this.moveDelta);

		float previousX = square.getX();

		if (Math.abs(tempNewX - previousX) >= LevelScreenEx.SQUARE_WIDTH) { 
			canMove = true; 
		}else { 
			parent.add(AbsoluteLayout.at(widget(), tempNewX, square().getY())); 
		}

		return canMove; 
	}

	public void setMoveAnimation(Animation animation) {
		this.moveAnimation = animation;
		widget.icon.update(Icons.image(animation.frame(0)));
	}

	public void setPushAnimation(Animation animaiton) {
		this.pushAnimation = animaiton;
	}

	public void setSpecialAnimation(Animation animaiton) {
		this.specialAnimation = animaiton;
	}

	public void setDyingAnimation(Animation animaiton) {
		this.dyingAnimation = animaiton;
	}

	public Unit front() {
		return this.front;
	}

	public void setFront(Unit unit) {
		this.front = unit;
		if (unit != null)
			unit.back = this;
	}

	public Unit back() {
		return this.back;
	}

	public void setBack(Unit unit) {
		this.back = unit;
		if (unit != null)
			unit.front = this;
	}

	public void removeFront() {
		if(this.front!=null){
			this.setFront(this.front.front);
		}
	}

	public void removeBack() {
		this.setBack(this.back.back);
	}

	public State state() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setPrvState(State state) {
		this.prvState = state;
	}
	/**
	 * Sets the image to show when the unit is deployed. If it's not set, then
	 * it's the first frame of the move animation.
	 */
	public void setDefaultImage(Image image) {
		this.icon = image;
		widget.icon.update(Icons.image(image));
	}

	/** Handles collision with the front unit. 
	 * @param headGoats 
	 * @param headTrolls */
	public void notifyColliedWithFront(Map<Integer, Unit> headTrolls, Map<Integer, Unit> headGoats) {
	};

	/** Handles collision with the back unit. 
	 * @param headGoats 
	 * @param headTrolls */
	public void notifyColliedWithBack(Map<Integer, Unit> headTrolls, Map<Integer, Unit> headGoats) {
	};

	/** Special ability of this unit. */
	public String ability() {
		return "";
	}

	//--> public abstract void update(float delta);
	public void update(float delta) {

		if (state() == null || state().equals(State.REMOVED)
				|| (this.speed() == 0 && (state!=State.SPECIALABILITY && state!=State.DYING))){
			return;
		}

		if (canMove){
			parent.add(AbsoluteLayout.at(widget(), square().getX(), square()
					.getY()));
		}

		if (state().equals(State.MOVING))
			widget().icon.update(Icons.image(moveAnimation.nextFrame(delta)));
		else if (state().equals(State.PUSHING)) {
			if (pushAnimation != null)
				widget().icon.update(Icons.image(pushAnimation.nextFrame(delta)));
		} else if (state().equals(State.SPECIALABILITY)){ 
			if (specialAnimation != null){
				widget().icon.update(Icons.image(specialAnimation.nextFrame(delta)));
				//Now check if the animation is over or dying.
				if(specialAnimation.isFinished()){
					if(this.prvState!=null){
						this.state=prvState;
					} else {
						this.state=State.MOVING;
					}
				}
			}
		}else if (state().equals(State.DYING)){ 
			if (dyingAnimation != null && !dead){
				widget().icon.update(Icons.image(dyingAnimation.nextFrame(delta)));
				if(!movedAnimation){
					movedAnimation=true;
					this.moveSquares(2);
				}
				if(dyingAnimation.isFinished()){
					dead=true;
					if(!(this instanceof Troll)){
						state = State.REMOVED;
					}
				}      	
			} else if(!dead) {
				state = State.REMOVED;
			}
		}
	} 

	public abstract String type();

	public float getOldX() {
		return oldX;
	}

	public void setOldX(float oldX) {
		this.oldX = oldX;
	}
	
	public void moveSquares(int squares){
		System.out.println(tempNewX);
		System.out.println( tempNewX - squares*48 +22);
		parent.add(AbsoluteLayout.at(widget(), tempNewX - squares*48 +23, square.getY()-1));
	}


}
