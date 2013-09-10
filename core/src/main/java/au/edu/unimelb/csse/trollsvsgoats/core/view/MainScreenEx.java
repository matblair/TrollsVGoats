package au.edu.unimelb.csse.trollsvsgoats.core.view;

import java.awt.Cursor;
import java.util.ArrayList;

import playn.core.Font;
import playn.core.Image;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.PlayN;
import au.edu.unimelb.csse.trollsvsgoats.core.TrollsVsGoatsGame;
import au.edu.unimelb.csse.trollsvsgoats.core.model.Badge;
import au.edu.unimelb.csse.trollsvsgoats.core.view.MessageBox.SimpleCallBack;
import react.UnitSlot;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Constraints;
import tripleplay.ui.Group;
import tripleplay.ui.Icon;
import tripleplay.ui.Label;
import tripleplay.ui.Layout.Constraint;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;
import tripleplay.ui.bgs.Scale9Background;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.ui.layout.AxisLayout;

public class MainScreenEx extends View {

	//these values are for the 1024x720 resolution.
	//you have to calculate these for the 800x600 resolution
	private final float LOCAL_TOP_MARGIN = 70;
	private final float X_POS = 610;
	private final float Y_POS_LABEL = 8;
	private final float Y_POS_BUTTON = 72;
	private final float Y_BUTTON_OFFSET = 80;
	private final float BUTTON_WIDTH = 275;
	private final float BUTTON_HEIGHT = 60;
	private final float LABEL_HEIGHT = 30;

	private final int Y_START_POS = 0;

	public MainScreenEx(TrollsVsGoatsGame game) {
		super(game);
		TOP_MARGIN = (int)LOCAL_TOP_MARGIN;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected Group createIface() {
		Image bg;
		if (this.width() == 800)
			bg = getImage("backgrounds/800_600/main_back_800_600");
		else
			bg = getImage("backgrounds/1024_720/main_back_1024_720");
		root.addStyles(Style.BACKGROUND.is(Background.image(bg)));
		
		back.setVisible(false);

		Group tiles = new Group(new AbsoluteLayout());
		Group myroot = new Group(new AbsoluteLayout());
		Group top = new Group(new AbsoluteLayout());

		Icon rope_l = getIcon("cut_screens/home_page/rope");
		int y_pos = Y_START_POS + 55;
		int boardHeight = (int)getImage("cut_screens/home_page/button_b_inactive").height();
		int boardWidth = (int)getImage("cut_screens/home_page/button_b_inactive").width();
		int y_pos_offset = (int)(boardHeight + (rope_l.height()-55));

		// Add top panel stuff
		float title_board_x = this.width() - 480;
		float title_board_y = -47;
		//Board
		Icon titleBoard = getIcon ("cut_screens/home_page/title_board");
		Icon title = getIcon ("cut_screens/home_page/welcome_title");
		top.add (AbsoluteLayout.at (new Label (titleBoard), title_board_x, title_board_y, titleBoard.width(), titleBoard.height()));
		float title_x = title_board_x + titleBoard.width()/2 - title.width()/2;
		float title_y = title_board_y + titleBoard.height()/2 - title.height()/2;
		top.add (AbsoluteLayout.at (new Label (title), title_x, title_y-10, title.width(), title.height()));
		//Top ropes
		Icon lRope = getIcon ("cut_screens/home_page/rope_title_l");
		Icon rRope = getIcon ("cut_screens/home_page/rope_title_r");
		top.add(AbsoluteLayout.at (new Label (lRope), title_board_x+79, title_board_y-38, lRope.width(), lRope.height()));
		top.add(AbsoluteLayout.at (new Label (rRope), title_board_x+256, title_board_y-38, rRope.width(), rRope.height()));

		Styles bigLabel = Styles.make(
				Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.BOLD, 20)),
				Style.HALIGN.center,
				Style.TEXT_EFFECT.shadow,
				Style.TEXT_EFFECT.SHADOW.is(0xFF412C2C),
				Style.COLOR.is(0xFFFFFFFF));
		final Label labelUser = new Label(game.userName()).addStyles(bigLabel);
		float labelWidth = 200;
		top.add(AbsoluteLayout.at(labelUser, title_board_x + (titleBoard.width() - labelWidth)/2, title_board_y + 5 + titleBoard.height()/2, labelWidth, 0));
		
		// Start button
		final Button btnStart = createButton("button_b").setConstraint(Constraints.fixedSize(boardWidth, boardHeight));
		tiles.add(AbsoluteLayout.at(btnStart, 0, y_pos, boardWidth, boardHeight));
		tiles.add(AbsoluteLayout.at(new Label(rope_l), 12, y_pos-34, rope_l.width(), rope_l.height()));
		tiles.add(AbsoluteLayout.at(new Label(rope_l), (boardWidth-45), y_pos-34, rope_l.width(), rope_l.height()));
		btnStart.clicked().connect(new UnitSlot() {
			@Override
			public void onEmit() {
				game.showLevelSelScreen(0);;
			}
		});
		title = getIcon("cut_screens/home_page/t_start_p");
		tiles.add(AbsoluteLayout.at(new Label(title), (boardWidth - title.width())/2, y_pos + (boardHeight - title.height()) / 2, title.width(), title.height()));
		
		y_pos += y_pos_offset;
		
		// Badges button
		final Button btnBadges = createButton("button_b").setConstraint(Constraints.fixedSize(boardWidth, boardHeight));
		tiles.add(AbsoluteLayout.at(btnBadges, 0, y_pos, boardWidth, boardHeight));
		tiles.add(AbsoluteLayout.at(new Label(rope_l), 12, y_pos-34, rope_l.width(), rope_l.height()));
		tiles.add(AbsoluteLayout.at(new Label(rope_l), (boardWidth-45), y_pos-34, rope_l.width(), rope_l.height()));
		btnBadges.clicked().connect(new UnitSlot() {
			@Override
			public void onEmit() {
				game.showBadgesScreen();
			}
		});
		title = getIcon("cut_screens/home_page/t_your_b");
		tiles.add(AbsoluteLayout.at(new Label(title), (boardWidth - title.width())/2, y_pos + (boardHeight - title.height()) / 2, title.width(), title.height()));

		y_pos += y_pos_offset;
		
		// Options button
		final Button btnOptions = createButton("button_b").setConstraint(Constraints.fixedSize(boardWidth, boardHeight));
		tiles.add(AbsoluteLayout.at(btnOptions, 0, y_pos, boardWidth, boardHeight));
		tiles.add(AbsoluteLayout.at(new Label(rope_l), 12, y_pos-34, rope_l.width(), rope_l.height()));
		tiles.add(AbsoluteLayout.at(new Label(rope_l), (boardWidth-45), y_pos-34, rope_l.width(), rope_l.height()));
		btnOptions.clicked().connect(new UnitSlot() {
			@Override
			public void onEmit() {
				game.showOptionScreen();
			}
		});
		title = getIcon("cut_screens/home_page/t_options");
		tiles.add(AbsoluteLayout.at(new Label(title), (boardWidth - title.width())/2, y_pos + (boardHeight - title.height()) / 2, title.width(), title.height()));

		y_pos += y_pos_offset;
		
		//No longer neaded, being moved into level select
//		// Leaderboard button
//		final Button btnLeaderboards = createButton("button_b").setConstraint(Constraints.fixedSize(boardWidth, boardHeight));
//		tiles.add(AbsoluteLayout.at(btnLeaderboards, 0, y_pos, boardWidth, boardHeight));
//		tiles.add(AbsoluteLayout.at(new Label(rope_l), 12, y_pos-34, rope_l.width(), rope_l.height()));
//		tiles.add(AbsoluteLayout.at(new Label(rope_l), (boardWidth-45), y_pos-34, rope_l.width(), rope_l.height()));
//		btnLeaderboards.clicked().connect(new UnitSlot() {
//			@Override
//			public void onEmit() {
//				showTempMessageBox();
//			}
//		});
//		title = getIcon("cut_screens/home_page/t_liderboards");
//		tiles.add(AbsoluteLayout.at(new Label(title), (boardWidth - title.width())/2, y_pos + (boardHeight - title.height()) / 2, title.width(), title.height()));

		//y_pos += y_pos_offset;
				
		final Button btnHelp = createButton("button_u").setConstraint(Constraints.fixedSize(boardWidth, boardHeight));
		tiles.add(AbsoluteLayout.at(btnHelp, 0, y_pos, boardWidth, boardHeight));
		tiles.add(AbsoluteLayout.at(new Label(rope_l), 12, y_pos-34, rope_l.width(), rope_l.height()));
		tiles.add(AbsoluteLayout.at(new Label(rope_l), (boardWidth-45), y_pos-34, rope_l.width(), rope_l.height()));
		btnHelp.clicked().connect(new UnitSlot() {
			@Override
			public void onEmit() {
				game.showHelpScreen();
			}
		});
		title = getIcon("cut_screens/home_page/t_help");
		tiles.add(AbsoluteLayout.at(new Label(title), (boardWidth - title.width())/2, y_pos + (boardHeight - title.height()) / 2, title.width(), title.height()));
		
		y_pos += y_pos_offset;

		myroot.add(AbsoluteLayout.at(top, 0, 0));
		myroot.add(AbsoluteLayout.at(tiles, title_board_x + 40, 9, boardWidth, y_pos));
		
		return myroot;
	}

	private Button createButton(final String btnName) {
		Icon icon = getIcon("cut_screens/home_page/" + btnName + "_inactive");
		final Button btn = new Button(icon).setStyles(Style.VALIGN.center, Style.HALIGN.center, Style.BACKGROUND.is(Background.blank()));

		btn.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseDown(ButtonEvent event) {
				Icon selectIcon = getIcon("cut_screens/home_page/" + btnName + "_select");
				btn.icon.update(selectIcon);
				super.onMouseUp(event);
			}

			@Override
			public void onMouseUp(ButtonEvent event) {
				Icon selectIcon = getIcon("cut_screens/home_page/" + btnName + "_active");
				btn.icon.update(selectIcon);
				super.onMouseUp(event);
			}

			@Override
			public void onMouseOver(MotionEvent event) {
				Icon activeIcon = getIcon("cut_screens/home_page/" + btnName + "_active");
				btn.icon.update(activeIcon);
			}

			@Override
			public void onMouseOut(MotionEvent event) {
				btn.icon.update(getIcon("cut_screens/home_page/" + btnName + "_inactive"));
			}
		});

		return btn;
	}

	private void showTempMessageBox() {
		MessageBox temp = new MessageBox(game, "Under Construction", "OK",
				new SimpleCallBack() {

			@Override
			public void onClose() {
				game.closeMessageBox();
			}
		});

		game.showMessageBox(this,temp);
	}

	@Override
	public String[] images() {
		ArrayList<String> names = new ArrayList<String>();
		
		names.add("back_select");
		names.add("back_active");
		names.add("back_inactive");
		names.add("main_screen");

		// Boards
		names.add("cut_screens/home_page/button_b_active");
		names.add("cut_screens/home_page/button_b_inactive");
		names.add("cut_screens/home_page/button_b_select");
		names.add("cut_screens/home_page/button_u_active");
		names.add("cut_screens/home_page/button_u_inactive");
		names.add("cut_screens/home_page/button_u_select");

		// Ropes
		names.add("cut_screens/home_page/rope_title_l");
		names.add("cut_screens/home_page/rope_title_r");
		names.add("cut_screens/home_page/rope");

		// Titles
		names.add("cut_screens/home_page/title_board");
		names.add("cut_screens/home_page/welcome_title");
		names.add("cut_screens/home_page/t_help");
		names.add("cut_screens/home_page/t_liderboards");
		names.add("cut_screens/home_page/t_options");
		names.add("cut_screens/home_page/t_start_p");
		names.add("cut_screens/home_page/t_your_b");


		// Background
		names.add("backgrounds/800_600/main_back_800_600");
		names.add("backgrounds/1024_720/main_back_1024_720");

		return names.toArray(new String[names.size()]);
	}
	
	@Override
	public void wasShown() {
		wasAdded();
	}

	@Override
	protected String title() {

		return null;
	}
}
