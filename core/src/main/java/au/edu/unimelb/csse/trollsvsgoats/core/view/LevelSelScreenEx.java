package au.edu.unimelb.csse.trollsvsgoats.core.view;

import java.util.ArrayList;

import playn.core.Image;
import playn.core.Mouse;
import playn.core.PlayN;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import react.UnitSlot;
import au.edu.unimelb.csse.trollsvsgoats.core.TrollsVsGoatsGame;
import au.edu.unimelb.csse.trollsvsgoats.core.model.Badge;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Constraints;
import tripleplay.ui.Group;
import tripleplay.ui.Icon;
import tripleplay.ui.Label;
import tripleplay.ui.Style;
import tripleplay.ui.Style.Binding;
import tripleplay.ui.layout.AbsoluteLayout;

public class LevelSelScreenEx extends View {
	private int score = 0;

	private final static int TILE_WIDTH = 400;

	Icon iconChildBoard = null;
	private final int Y_START_POS = 0;//-22;
	public final static int ICON_WIDTH = 60;
	public final static int DESCRIPTION_WIDTH = TILE_WIDTH - ICON_WIDTH;
	private final static int BUTTON_SCROLL_DISTANCE = 10;
	final Binding<Background> selOn = Style.BACKGROUND.is(Background.blank()
			.inset(2, 1, -3, 3));
	final Binding<Background> selOff = Style.BACKGROUND.is(Background.blank()
			.inset(1, 2, -2, 2));

	private int tileCount = 7;
	private ScrollBar scroll;

	public LevelSelScreenEx(TrollsVsGoatsGame game) {
		super(game);
	}

	@Override
	protected Group createIface() {

		root.addStyles(Style.BACKGROUND.is(Background.image(getImage("backgrounds/1024_720/main_back_1024_720"))));

		//topPanel.addStyles(Style.BACKGROUND.is(Background.image(getImage("cut_screens/select_levels/title_board"))));

		//topPanel.add(new Shim(0, 20));

		// topPanel.add(getIcon("cut_screens/select_levels/title_board"));
		Group tiles = new Group(new AbsoluteLayout());
		Group myroot = new Group(new AbsoluteLayout());
		Group top = new Group(new AbsoluteLayout());
		
		iconChildBoard = getIcon("cut_screens/select_levels/level_board");
		Icon rope_l = getIcon("cut_screens/select_levels/rope");
		int y_pos = Y_START_POS ;
		
		// Add top panel stuff
		float title_board_x = 565;
		float title_board_y = -101;
		//Board
		Icon titleBoard = getIcon ("cut_screens/select_levels/title_board");
		Icon title = getIcon ("cut_screens/select_levels/title_sl");
		top.add (AbsoluteLayout.at (new Label (titleBoard), title_board_x, title_board_y, titleBoard.width(), titleBoard.height()));
		float title_x = title_board_x + titleBoard.width()/2 - title.width()/2;
		float title_y = title_board_y + titleBoard.height()/2 - title.height()/2;
		top.add (AbsoluteLayout.at (new Label (title), title_x, title_y, title.width(), title.height()));
		//Top ropes
		Icon lRope = getIcon ("cut_screens/select_levels/rope_title_l");
		Icon rRope = getIcon ("cut_screens/select_levels/rope_title_r");
		top.add(AbsoluteLayout.at (new Label (lRope), title_board_x+79, title_board_y-38, lRope.width(), lRope.height()));
		top.add(AbsoluteLayout.at (new Label (rRope), title_board_x+256, title_board_y-38, rRope.width(), rRope.height()));
		//Bottom ropes
		float bottomRopeY = title_board_y+77;
		top.add(AbsoluteLayout.at (new Label (rope_l), title_board_x+22, bottomRopeY, rope_l.width(), rope_l.height()));
		top.add(AbsoluteLayout.at (new Label (rope_l), title_board_x+253, bottomRopeY, rope_l.width(), rope_l.height()));


		for (int i = 1; i <= tileCount; i++) {

			//add board
			if(i==tileCount){
				Icon board = getIcon("cut_screens/select_levels/level_board_ultra");
				tiles.add(AbsoluteLayout.at(new Label(board), 0, y_pos, board.width(), board.height()));
			} else {
				tiles.add(AbsoluteLayout.at(new Label(iconChildBoard), 0, y_pos, iconChildBoard.width(), iconChildBoard.height()));
			}
			//Add the ropes to the top and bottom
			tiles.add(AbsoluteLayout.at(new Label(rope_l), 12, y_pos-34, rope_l.width(), rope_l.height()));
			tiles.add(AbsoluteLayout.at(new Label(rope_l), (iconChildBoard.width()-45), y_pos-34, rope_l.width(), rope_l.height()));
			
			//add level number button
			Icon levelIcon = null;
			
			final int _i = i;
			if (i > model.maxCompletedLevel() + 1)
			{
				levelIcon = getIcon("cut_screens/select_levels/level_b_lock");
				Button levelLockButton = new Button(levelIcon).setStyles(Style.VALIGN.center, Style.HALIGN.center);
				tiles.add(AbsoluteLayout.at(levelLockButton,42 , y_pos+55  - 35, 56,56));
				levelLockButton.setStyles(Style.BACKGROUND.is(butBg), Style.ICON_POS.below);
			}
			else
			{
				levelIcon = getIcon("cut_screens/select_levels/level_b_inactive");
				final Button levelButton = new Button(levelIcon).setConstraint(Constraints.fixedSize(levelIcon.width(),levelIcon.height()));
				tiles.add(AbsoluteLayout.at(levelButton, 42, y_pos+20, 56, 56));
				levelButton.setStyles(Style.BACKGROUND.is(butBg), Style.ICON_POS.below);
				levelButton.clicked().connect(new UnitSlot() {

					@Override
					public void onEmit() {
						game.loadLevel(_i, false);
					}
				});

				levelButton.layer.addListener(new Mouse.LayerAdapter() {
					@Override
					public void onMouseDown(ButtonEvent event) {
						Icon selectIcon = getIcon("cut_screens/select_levels/level_b_select");
						levelButton.icon.update(selectIcon);
						super.onMouseUp(event);
					}

					@Override
					public void onMouseOver(MotionEvent event) {
						Icon activeIcon = getIcon("cut_screens/select_levels/level_b_active");
						levelButton.icon.update(activeIcon);
					}

					@Override
					public void onMouseOut(MotionEvent event) {
						levelButton.icon.update(getIcon("cut_screens/select_levels/level_b_inactive"));
					}
				});
				
				Icon numberIcon = getIcon (getLevelIcon(i));
				tiles.add (AbsoluteLayout.at (new Label (numberIcon), 70-numberIcon.width()/2, y_pos+48-numberIcon.height()/2, numberIcon.width(), numberIcon.height()));

				//add starts
				score = model.levelScore(i);
				if (score > 3)
					score = 3;
				else if (score < 0)
					score = 0;

				for (int j = 1; j <= 3; j++) {
					if (j <= score)
					{
						if(j == 1)
							tiles.add(AbsoluteLayout.at(new Label(getIcon("cut_screens/select_levels/star")), 118, y_pos + 24 , 27, 26));
						else if(j == 2)
							tiles.add(AbsoluteLayout.at(new Label(getIcon("cut_screens/select_levels/star")), 153, y_pos + 14 , 27, 26));
						else if(j == 3)
							tiles.add(AbsoluteLayout.at(new Label(getIcon("cut_screens/select_levels/star")), 188, y_pos + 24 , 27, 26));
					}
				}
			}

			y_pos += iconChildBoard.height() + (rope_l.height()-55 ); //30 - hanger rope height
		}

		myroot.add(AbsoluteLayout.at(tiles, 575, 10, iconChildBoard.width(), iconChildBoard.height() * tileCount));
		
		int pageHeight = (int)(iconChildBoard.height() + (rope_l.height()-55 ));
		int scrollRange = tileCount * pageHeight;
		float PAGE_SIZE = ((height() - 400) / pageHeight) + 1;
		float PAGE_RANGE = PAGE_SIZE * pageHeight;

		Icon dragerIcon = getIcon("cut_screens/select_levels/scroll_b_inactive");
		scroll = new ScrollBar(tiles, scrollRange, PAGE_RANGE);
		
		final Button upBtn = createButton ("scroll_arrow_up");
		final Button downBtn = createButton ("scroll_arrow_down");
		final Button draggerBtn = createButton ("scroll_b");
		scroll.upButton = upBtn;
		scroll.downButton = downBtn;
		scroll.dragger = draggerBtn;
		scroll.init();

		Image scrollBG = getImage("cut_screens/select_levels/scroll_bar");
		scroll.setBarBackgroundImage(scrollBG);

		if (scrollRange > PAGE_RANGE)
		{
			top.add(AbsoluteLayout.at (new Label (rope_l), title_board_x+319, bottomRopeY, rope_l.width(), rope_l.height()));
			myroot.add(AbsoluteLayout.at(scroll, 869, Y_START_POS + 9, scrollBG.width(), scrollBG.height()));
		}
		
		myroot.add(AbsoluteLayout.at(top, 0, 0));

		return myroot;
	}
	
	private String getLevelIcon (int level){
		return ("cut_screens/select_levels/level_n" + level);
	}
	
	private Button createButton(final String btnName) {
		Icon icon = getIcon("cut_screens/select_levels/" + btnName + "_inactive");
		final Button btn = new Button(icon).setStyles(Style.VALIGN.center, Style.HALIGN.center, Style.BACKGROUND.is(Background.blank()));
		
		btn.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseDown(ButtonEvent event) {
				Icon selectIcon = getIcon("cut_screens/select_levels/" + btnName + "_select");
				btn.icon.update(selectIcon);
				super.onMouseUp(event);
			}
			
			@Override
			public void onMouseUp(ButtonEvent event) {
				Icon selectIcon = getIcon("cut_screens/select_levels/" + btnName + "_active");
				btn.icon.update(selectIcon);
				super.onMouseUp(event);
			}

			@Override
			public void onMouseOver(MotionEvent event) {
				Icon activeIcon = getIcon("cut_screens/select_levels/" + btnName + "_active");
				btn.icon.update(activeIcon);
			}

			@Override
			public void onMouseOut(MotionEvent event) {
				btn.icon.update(getIcon("cut_screens/select_levels/" + btnName + "_inactive"));
			}
		});
		
		return btn;
	}

	protected void addButtonListener(final Button button) {
		button.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseDown(ButtonEvent event) {
				button.addStyles(selOn);
			}

			@Override
			public void onMouseUp(ButtonEvent event) {
				button.addStyles(selOff);
			}
		});
	}

	@Override
	public String[] images() {
		ArrayList<String> names = new ArrayList<String>();
		for (Badge badge : model.badges()) {
			names.add("badge_" + badge.name());
		}

		names.add("cut_screens/select_levels/back_active");
		names.add("cut_screens/select_levels/back_inactive");
		names.add("cut_screens/select_levels/back_select");
		names.add("cut_screens/select_levels/level_b_active");
		names.add("cut_screens/select_levels/level_b_inactive");
		names.add("cut_screens/select_levels/level_b_lock");
		names.add("cut_screens/select_levels/level_b_select");
		names.add("cut_screens/select_levels/level_board_ultra");
		names.add("cut_screens/select_levels/level_board");

		//Level numbers
		names.add("cut_screens/select_levels/level_n1");
		names.add("cut_screens/select_levels/level_n2");
		names.add("cut_screens/select_levels/level_n3");
		names.add("cut_screens/select_levels/level_n4");
		names.add("cut_screens/select_levels/level_n5");
		names.add("cut_screens/select_levels/level_n6");
		names.add("cut_screens/select_levels/level_n7");
		names.add("cut_screens/select_levels/level_n8");
		names.add("cut_screens/select_levels/level_n9");
		names.add("cut_screens/select_levels/level_n10");


		names.add("cut_screens/select_levels/rope_title_l");
		names.add("cut_screens/select_levels/rope_title_r");
		names.add("cut_screens/select_levels/rope");
		names.add("cut_screens/select_levels/score_b_active");
		names.add("cut_screens/select_levels/score_b_inactive");
		names.add("cut_screens/select_levels/score_b_lock");
		names.add("cut_screens/select_levels/scroll_arrow_down_active");
		names.add("cut_screens/select_levels/scroll_arrow_down_inactive");
		names.add("cut_screens/select_levels/scroll_arrow_down_select");
		names.add("cut_screens/select_levels/scroll_arrow_up_select");
		names.add("cut_screens/select_levels/scroll_arrow_up_inactive");
		names.add("cut_screens/select_levels/scroll_arrow_up_active");
		names.add("cut_screens/select_levels/scroll_b_active");
		names.add("cut_screens/select_levels/scroll_b_inactive");
		names.add("cut_screens/select_levels/scroll_b_select");
		names.add("cut_screens/select_levels/scroll_bar");
		names.add("cut_screens/select_levels/star");
		names.add("cut_screens/select_levels/title_board");
		names.add("cut_screens/select_levels/title_sl");

		names.add("backgrounds/1024_720/main_back_1024_720");

		return names.toArray(new String[names.size()]);
	}

	@Override
	protected String title() {
		return " ";
	}

	@Override
	public void wasShown() {
		wasAdded();
	}

	@Override
	public void wasHidden() {
		PlayN.keyboard().setListener(null);
	}

	@Override
	public void update(int delta) {
		if (scroll.isUpButtonDown())
			scroll.scrollUp(BUTTON_SCROLL_DISTANCE);
		else if (scroll.isDownButtonDown())
			scroll.scrollDown(BUTTON_SCROLL_DISTANCE);
		super.update(delta);
	}

}
