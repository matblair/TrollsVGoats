package au.edu.unimelb.csse.trollsvsgoats.core.view;

import java.util.ArrayList;

import playn.core.Image;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import pythagoras.f.IPoint;
import au.edu.unimelb.csse.trollsvsgoats.core.TrollsVsGoatsGame;
import au.edu.unimelb.csse.trollsvsgoats.core.model.Badge;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Icon;
import tripleplay.ui.Label;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.util.Dragger;

public class LeaderBoard extends View {
	private final int Y_START_POS = 0;
	
	int scores;

	public LeaderBoard(TrollsVsGoatsGame game, int levelID) {
		super(game);
		//scores = model.scores().get(levelID);
	}

	@Override
	protected Group createIface() {
		Image bg;
		if (this.width() == 800)
			bg = getImage("backgrounds/800_600/main_back_800_600");
		else
			bg = getImage("backgrounds/1024_720/main_back_1024_720");
		root.addStyles(Style.BACKGROUND.is(Background.image(bg)));
		
		topPanel.addStyles(Style.BACKGROUND.is(Background.blank()));

		Group myroot = new Group(new AbsoluteLayout());
		Group top = new Group(new AbsoluteLayout());
		Group tiles = new Group(new AbsoluteLayout());

		Icon rope_l = getIcon("cut_screens/leaderboards/rope");
		int y_pos = Y_START_POS + 30;
		int boardWidth = (int)getImage("cut_screens/leaderboards/liderboards_board").width();
		int boardHeight = (int)getImage("cut_screens/leaderboards/liderboards_board").height();

		// Add top panel stuff
		float title_board_x = this.width() - 480;
		float title_board_y = y_pos - 102;
		//Board
		Icon titleBoard = getIcon ("cut_screens/leaderboards/title_board");
		Icon title = getIcon ("cut_screens/leaderboards/liderboards_title");
		top.add (AbsoluteLayout.at (new Label (titleBoard), title_board_x, title_board_y, titleBoard.width(), titleBoard.height()));
		float title_x = title_board_x + titleBoard.width()/2 - title.width()/2;
		float title_y = title_board_y + titleBoard.height()/2 - title.height()/2 + 10;
		top.add (AbsoluteLayout.at (new Label (title), title_x, title_y, title.width(), title.height()));
		//Top ropes
		Icon lRope = getIcon ("cut_screens/leaderboards/rope_title_l");
		Icon rRope = getIcon ("cut_screens/leaderboards/rope_title_r");
		top.add(AbsoluteLayout.at (new Label (lRope), title_board_x+79, title_board_y-38, lRope.width(), lRope.height()));
		top.add(AbsoluteLayout.at (new Label (rRope), title_board_x+256, title_board_y-38, rRope.width(), rRope.height()));

		// Add the main board and ropes
		Icon board = getIcon("cut_screens/leaderboards/liderboards_board");
		tiles.add(AbsoluteLayout.at (new Label(board), 0, 0));
		tiles.add(AbsoluteLayout.at(new Label(rope_l), 12, -34, rope_l.width(), rope_l.height()));
		tiles.add(AbsoluteLayout.at(new Label(rope_l), (boardWidth-46), -34, rope_l.width(), rope_l.height()));

		myroot.add(AbsoluteLayout.at(top, 0, 0));
		myroot.add(AbsoluteLayout.at(tiles, title_board_x + 10, 39, boardWidth, boardHeight));

		return myroot;
	}

	@Override
	public String[] images() {
		ArrayList<String> names = new ArrayList<String>();
		
		names.add("cut_screens/leaderboards/back_active");
		names.add("cut_screens/leaderboards/back_inactive");
		names.add("cut_screens/leaderboards/back_select");
		
		names.add("cut_screens/leaderboards/liderboards_board");
		names.add("cut_screens/leaderboards/liderboards_title");
		names.add("cut_screens/leaderboards/title_board");

		names.add("cut_screens/leaderboards/rope_title_l");
		names.add("cut_screens/leaderboards/rope_title_r");
		names.add("cut_screens/leaderboards/rope");
		
		names.add("cut_screens/leaderboards/select_results");
		names.add("cut_screens/leaderboards/separator");
		names.add("cut_screens/leaderboards/slider_active");
		names.add("cut_screens/leaderboards/slider_inactive");
		names.add("cut_screens/leaderboards/slider_select");

		return names.toArray(new String[names.size()]);
	}
	
	@Override
	protected String title() {
		return "";
	}

}
