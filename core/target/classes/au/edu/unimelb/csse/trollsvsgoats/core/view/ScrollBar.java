package au.edu.unimelb.csse.trollsvsgoats.core.view;

import static playn.core.PlayN.mouse;
import playn.core.Image;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import playn.core.Platform;
import playn.core.PlayN;
import pythagoras.f.IPoint;
import tripleplay.ui.*;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.util.Dragger;

public class ScrollBar extends Elements<ScrollBar> {

	protected final static int DEFAULT_BG_COLOR = 0xFF13B5B1;
	protected final static int DEFAULT_DRAGGER_COLOT = 0xFFBFBFBF;
	protected final static float DEFAULT_BAR_WIDTH = 30;
	protected final static float DEFAULT_BAR_HEIGHT = 300;
	protected final static int DEFAULT_WHEEL_DISTANCE = 20;
	protected final static int DEFAULT_BUTTON__DISTANCE = 10;

	protected Elements<?> view;
	protected Group scroll;
	protected Button dragger;
	protected Button upButton, downButton;
	protected float barWidth = DEFAULT_BAR_WIDTH;
	protected float barHeight = DEFAULT_BAR_HEIGHT;
	protected float scrollDist = 245;
	protected int bgColor = DEFAULT_BG_COLOR;
	protected int draggerColor = DEFAULT_DRAGGER_COLOT;
	protected int wheelDistance = DEFAULT_WHEEL_DISTANCE;
	protected int buttonDistance = DEFAULT_BUTTON__DISTANCE;
	protected int jumps;
	protected int jumpDist;
	protected float draggerHeight;
	protected boolean upButDown;
	protected boolean downButDown;
	protected Image bgImage;

	/**
	 * @param view
	 *            The view to be scrolled.
	 * @param scrollRange
	 *            The range/height of the scrolled view.
	 * @param pageRange
	 *            The range/height of one page.
	 */
	public ScrollBar(Elements<?> view, int jumpDist, int jumps) {
		super(AxisLayout.vertical());
		this.view = view;
		this.jumpDist = jumpDist;
		this.jumps = jumps;
		setBarSize(58, 423);
		addStyles(Style.VALIGN.top);
	}

	protected void init() {
		if (upButton == null)
			upButton = new Button();
		if (downButton == null)
			downButton = new Button();

		scroll = new Group(new AbsoluteLayout());
		scroll.add(AbsoluteLayout.at (upButton, 14, 31));
		scroll.add(AbsoluteLayout.at (dragger, -3, 65));
		scroll.add(AbsoluteLayout.at (downButton, 14, 380));

		this.add(scroll);

		upButton.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseDown(ButtonEvent event) {
				upButDown = true;
				scrollUp(buttonDistance);
			}

			@Override
			public void onMouseUp(ButtonEvent event) {
				upButDown = false;
			}
		});

		downButton.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseDown(ButtonEvent event) {
				downButDown = true;
				scrollDown(buttonDistance);
			}

			@Override
			public void onMouseUp(ButtonEvent event) {
				downButDown = false;
			}
		});

		// Handles mouse wheel.
		mouse().setListener(new Mouse.Adapter() {
			public void onMouseWheelScroll(playn.core.Mouse.WheelEvent event) {
				if (event.velocity() > 0)
					scrollDown(wheelDistance);
				// Scroll up.
				else
					scrollUp(wheelDistance);

			};
		});

		// Handles dragging of the scroll.
		dragger.layer.addListener(new Dragger() {
			float originY;
			boolean originSet = false;

			@Override
			public void onDragged(IPoint current, IPoint start) {
				if (!originSet) {
					originY = start.y() + dragger.layer.originY();
					originSet = true;
				}

				float targetOffset = current.y() - originY;
				if (targetOffset < 0)
					targetOffset = 0;
				else if (targetOffset > scrollDist)
					targetOffset = scrollDist;

				dragger.layer.setOrigin(0, -targetOffset);
				float scrollDistance = jumpDist * (int)(jumps * targetOffset / scrollDist);
				view.layer.setOrigin(0, scrollDistance);

				showHideElements();
			}
		});
	}

	public ScrollBar setBarSize(float width, float height) {
		this.barWidth = width;
		this.barHeight = height;
		if (scroll != null)
			scroll.setConstraint(Constraints.fixedSize(width, height));
		//		if (scrollRange > pageRange)
		//			draggerHeight = barHeight / 2 * pageRange / scrollRange;
		return this;
	}

	public void setBarColor(int color) {
		this.bgColor = color;
		scroll.addStyles(Style.BACKGROUND.is(Background.solid(color)));
	}

	public void setBarBackgroundImage(Image bg) {
		this.bgImage = bg;
		this.setBarSize(bg.width(), bg.height());
		scroll.addStyles(Style.BACKGROUND.is(Background.image(bg)));
	}

	public void setDraggerColor(int color) {
		this.draggerColor = color;
		dragger.addStyles(Style.BACKGROUND.is(Background.solid(color)));
	}

	public void wheelScrollDistance(int distance) {
		this.wheelDistance = distance;
	}

	public void buttonScrollDistance(int distance) {
		this.buttonDistance = distance;
	}

	protected void scrollUp(int distance) {
		float y = -dragger.layer.originY();
		float dragDistance = (scrollDist - draggerHeight)
				/ (jumpDist * jumps / distance);
		float target = y - dragDistance;

		if (target < 0)
			target = 0;
		else if (target > scrollDist)
			target = scrollDist;

		view.layer.setOrigin(0, Math.max(0, jumpDist * (int)(jumps * target / scrollDist)));
		dragger.layer.setOrigin(0, -target);

		showHideElements();
	}

	private void showHideElements() {
		Element<?> el;
		float viewPos = view.layer.originY();

		for (int i=0; i<view.childCount(); i++) {
			el = view.childAt(i);
			if (el.y() < viewPos - 30)
				el.setVisible(false);
			else
				el.setVisible(true);
		}
	}

	protected void scrollDown(int distance) {
		scrollUp(-distance);
	}

	public Button upButton() {
		return this.upButton;
	}

	public Button downButton() {
		return this.downButton;
	}

	public Button draggerButton() {
		return this.dragger;
	}

	public void setdraggerHeight(float height) {
		this.draggerHeight = height;
	}

	public boolean isUpButtonDown() {
		return this.upButDown;
	}

	public boolean isDownButtonDown() {
		return this.downButDown;
	}

	@Override
	protected Class<?> getStyleClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
