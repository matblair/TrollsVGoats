package au.edu.unimelb.csse.trollsvsgoats.core.view;

import static tripleplay.ui.layout.TableLayout.*;

import java.util.ArrayList;

import playn.core.Font;
import playn.core.Image;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import pythagoras.f.IPoint;
import au.edu.unimelb.csse.trollsvsgoats.core.TrollsVsGoatsGame;
import react.Function;
import react.UnitSlot;
import tripleplay.ui.*;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.layout.TableLayout;
import tripleplay.util.Dragger;

public class OptionScreen extends View {

	private final int Y_START_POS = 0;
	private static final int MAX_SPEED = 6;
	private Label speedValue;

	public OptionScreen(TrollsVsGoatsGame game) {
		super(game);
	}

	@Override
	protected Group createIface() {
		Image bg;
		if (this.width() == 800)
			bg = getImage("backgrounds/800_600/badges_back_800_600");
		else
			bg = getImage("backgrounds/1024_720/badges_back_1024_720");
		root.addStyles(Style.BACKGROUND.is(Background.image(bg)));
		
		topPanel.addStyles(Style.BACKGROUND.is(Background.blank()));

		Group myroot = new Group(new AbsoluteLayout());
		Group top = new Group(new AbsoluteLayout());
		Group tiles = new Group(new AbsoluteLayout());

		Icon rope_l = getIcon("cut_screens/options/rope");
		int y_pos = Y_START_POS + 30;
		int boardWidth = (int)getImage("cut_screens/options/options_board").width();
		int boardHeight = (int)getImage("cut_screens/options/options_board").height();

		// Add top panel stuff
		float title_board_x = this.width() - 480;
		float title_board_y = y_pos - 102;
		//Board
		Icon titleBoard = getIcon ("cut_screens/options/title_board");
		Icon title = getIcon ("cut_screens/options/options_title");
		top.add (AbsoluteLayout.at (new Label (titleBoard), title_board_x, title_board_y, titleBoard.width(), titleBoard.height()));
		float title_x = title_board_x + titleBoard.width()/2 - title.width()/2;
		float title_y = title_board_y + titleBoard.height()/2 - title.height()/2;
		top.add (AbsoluteLayout.at (new Label (title), title_x, title_y, title.width(), title.height()));
		//Top ropes
		Icon lRope = getIcon ("cut_screens/options/rope_title_l");
		Icon rRope = getIcon ("cut_screens/options/rope_title_r");
		top.add(AbsoluteLayout.at (new Label (lRope), title_board_x+79, title_board_y-38, lRope.width(), lRope.height()));
		top.add(AbsoluteLayout.at (new Label (rRope), title_board_x+256, title_board_y-38, rRope.width(), rRope.height()));

		// Add the main board and ropes
		Icon board = getIcon("cut_screens/options/options_board");
		tiles.add(AbsoluteLayout.at (new Label(board), 0, 0));
		tiles.add(AbsoluteLayout.at(new Label(rope_l), 12, -34, rope_l.width(), rope_l.height()));
		tiles.add(AbsoluteLayout.at(new Label(rope_l), (boardWidth-46), -34, rope_l.width(), rope_l.height()));

		// Sound buttons
		int soundBtnX = 126;
		final Image soundOnSel = getImage("cut_screens/options/sound_on_b");
		final Image soundOffSel = getImage("cut_screens/options/sound_off_b");

		final Button soundOnButton = createButton("sound_on_b");
		tiles.add (AbsoluteLayout.at(soundOnButton, soundBtnX + 56, 40));
		final Button soundOffButton = createButton("sound_off_b");
		tiles.add (AbsoluteLayout.at(soundOffButton, soundBtnX, 40));

		if (model.isSoundEnabled())
			soundOnButton.setStyles(Style.BACKGROUND.is(Background.image(soundOnSel)));
		else
			soundOffButton.setStyles(Style.BACKGROUND.is(Background.image(soundOffSel)));

		soundOnButton.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseUp(ButtonEvent event) {
				soundOffButton.setStyles(Style.BACKGROUND.is(Background.blank()));
				soundOnButton.setStyles(Style.BACKGROUND.is(Background.image(soundOnSel)));
				model.setSoundEnabled(true);
				game.persist();
				super.onMouseUp(event);
			}
		});

		soundOffButton.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseUp(ButtonEvent event) {
				soundOnButton.setStyles(Style.BACKGROUND.is(Background.blank()));
				soundOffButton.setStyles(Style.BACKGROUND.is(Background.image(soundOffSel)));
				model.setSoundEnabled(false);
				game.persist();
				super.onMouseUp(event);
			}
		});

		// Speed slider
		
		/*
	       
        int currentSpeed = (int) (MAX_SPEED + 1 - (int) (model.movementTime() * 2));
        Slider speed = new Slider(currentSpeed, 1, MAX_SPEED).setIncrement(1);
        speedValue = new Label(String.valueOf(currentSpeed)).setConstraint(
                Constraints.minSize("0")).setStyles(Style.HALIGN.right);
        speed.value.map(new Function<Float, String>() {

            @Override
            public String apply(Float value) {
                return String.valueOf(value.intValue());
            }
        }).connect(speedValue.text.slot());
        iface.add(new Label("Speed").addStyles(Style.FONT.is(SUBTITLE_FONT)))
                .add(new Group(AxisLayout.vertical()).add(speed)
                        .add(speedValue));
*/
		int currentSpeed = MAX_SPEED + 1 - (int)(model.movementTime() * 2);
		final Button speedSlider = createButton("slider");
		speedSlider.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseDown(ButtonEvent event) {
				Icon activeIcon = getIcon("cut_screens/options/slider_select");
				speedSlider.icon.update(activeIcon);
				super.onMouseDown(event);
			}

			@Override
			public void onMouseUp(ButtonEvent event) {
				Icon activeIcon = getIcon("cut_screens/options/slider_active");
				speedSlider.icon.update(activeIcon);
				super.onMouseUp(event);
			}
		});
		
		speedSlider.layer.addListener(new Dragger() {
			float originX;
			boolean originSet = false;
			int horizDist = 170;

			@Override
			public void onDragged(IPoint current, IPoint start) {
				if (!originSet) {
					originX = start.x() + speedSlider.layer.originX();
					originSet = true;
				}

				float targetOffset = current.x() - originX;
				if (targetOffset < 0)
					targetOffset = 0;
				else if (targetOffset > horizDist)
					targetOffset = horizDist;

				float sliderPos = (int)targetOffset / 34;
				speedSlider.layer.setOrigin(-sliderPos * 34, 0);
				
				float targetSpeed = (MAX_SPEED - sliderPos) / 2;
				game.setMovementTime(targetSpeed);
			}
		});
		
		tiles.add (AbsoluteLayout.at(speedSlider, 115, 107));
		speedSlider.layer.setOrigin(-(MAX_SPEED - (int) (model.movementTime() * 2)) * 34, 0);
		
		// Screen size buttons
		final Image screen800Sel = getImage("cut_screens/options/screen_b_800");
		final Image screen1024Sel = getImage("cut_screens/options/screen_b_1024");

		final Button screen800Btn = createButton("screen_b_800");
		tiles.add (AbsoluteLayout.at(screen800Btn, soundBtnX, 166));
		final Button screen1024Btn = createButton("screen_b_1024");
		tiles.add (AbsoluteLayout.at(screen1024Btn, soundBtnX + 91, 166));
		
		if (this.width() == 1024)
			screen1024Btn.setStyles(Style.BACKGROUND.is(Background.image(screen1024Sel)));
		else
			screen800Btn.setStyles(Style.BACKGROUND.is(Background.image(screen800Sel)));

		screen800Btn.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseUp(ButtonEvent event) {
				soundOffButton.setStyles(Style.BACKGROUND.is(Background.blank()));
				screen800Btn.setStyles(Style.BACKGROUND.is(Background.image(screen800Sel)));
				game.setScreenSize(800, 600);
				game.refreshMainScreen();
				game.persist();
				wasAdded();
				super.onMouseUp(event);
			}
		});

		screen1024Btn.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseUp(ButtonEvent event) {
				screen800Btn.setStyles(Style.BACKGROUND.is(Background.blank()));
				screen1024Btn.setStyles(Style.BACKGROUND.is(Background.image(screen1024Sel)));
				game.setScreenSize(1024, 720);
				game.refreshMainScreen();
				game.persist();
				wasAdded();
				super.onMouseUp(event);
			}
		});

		myroot.add(AbsoluteLayout.at(top, 0, 0));
		myroot.add(AbsoluteLayout.at(tiles, title_board_x + 10, 39, boardWidth, boardHeight));

		return myroot;
	}

	private Button createButton(final String btnName) {
		Icon icon = getIcon("cut_screens/options/" + btnName + "_inactive");
		final Button btn = new Button(icon).setStyles(Style.VALIGN.center, Style.HALIGN.center, Style.BACKGROUND.is(Background.blank()));

		btn.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseOver(MotionEvent event) {
				Icon activeIcon = getIcon("cut_screens/options/" + btnName + "_active");
				btn.icon.update(activeIcon);
			}

			@Override
			public void onMouseOut(MotionEvent event) {
				btn.icon.update(getIcon("cut_screens/options/" + btnName + "_inactive"));
			}
		});

		return btn;
	}

	@Override
	public String[] images() {
		ArrayList<String> names = new ArrayList<String>();

		names.add("cut_screens/options/title_board");
		names.add("cut_screens/options/options_board");
		names.add("cut_screens/options/options_title");
		names.add("cut_screens/options/rope_title_l");
		names.add("cut_screens/options/rope_title_r");
		names.add("cut_screens/options/rope");

		names.add("cut_screens/options/slider_active");
		names.add("cut_screens/options/slider_inactive");
		names.add("cut_screens/options/slider_select");

		names.add("cut_screens/options/sound_off_b");
		names.add("cut_screens/options/sound_off_b_active");
		names.add("cut_screens/options/sound_off_b_inactive");
		names.add("cut_screens/options/sound_on_b");
		names.add("cut_screens/options/sound_on_b_active");
		names.add("cut_screens/options/sound_on_b_inactive");

		names.add("cut_screens/options/screen_b_800");
		names.add("cut_screens/options/screen_b_800_active");
		names.add("cut_screens/options/screen_b_800_inactive");
		names.add("cut_screens/options/screen_b_1024");
		names.add("cut_screens/options/screen_b_1024_active");
		names.add("cut_screens/options/screen_b_1024_inactive");

		names.add("backgrounds/800_600/badges_back_800_600");
		names.add("backgrounds/1024_720/badges_back_1024_720");

		return names.toArray(new String[0]);
	}

	@Override
	protected String title() {
		return "";
	}

	@Override
	public void wasHidden() {
		//        game.setMovementTime((MAX_SPEED + 1 - Float.valueOf(speedValue.text
		//                .get())) / 2);
	}

}
