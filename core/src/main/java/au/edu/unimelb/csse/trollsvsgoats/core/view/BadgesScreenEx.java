package au.edu.unimelb.csse.trollsvsgoats.core.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import playn.core.Font;
import playn.core.Image;
import playn.core.Key;
import playn.core.Mouse;
import playn.core.PlayN;
import playn.core.Keyboard.Event;
import playn.core.Keyboard.Listener;
import playn.core.Keyboard.TypedEvent;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import au.edu.unimelb.csse.trollsvsgoats.core.TrollsVsGoatsGame;
import au.edu.unimelb.csse.trollsvsgoats.core.model.Badge;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Constraints;
import tripleplay.ui.Group;
import tripleplay.ui.Icon;
import tripleplay.ui.Label;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;
import tripleplay.ui.Style.Binding;
import tripleplay.ui.layout.AbsoluteLayout;

public class BadgesScreenEx extends View {

	private final static int TILE_WIDTH = 400;
	public final static int ICON_WIDTH = 60;
	public final static int DESCRIPTION_WIDTH = TILE_WIDTH - ICON_WIDTH;
	private final static int BUTTON_SCROLL_DISTANCE = 10;
	final Binding<Background> selOn = Style.BACKGROUND.is(Background.blank()
			.inset(2, 1, -3, 3));
	final Binding<Background> selOff = Style.BACKGROUND.is(Background.blank()
			.inset(1, 2, -2, 2));

	private ScrollBar scroll;

	Icon iconChildBoard = null;
	private final int Y_START_POS = 0;
	
	private Styles nameStyle = Styles.make(
			//Style.BACKGROUND.is(Background.solid(bgColor)),
			Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.PLAIN, 15)),
			Style.TEXT_WRAP.on,
			Style.HALIGN.left,
			Style.TEXT_EFFECT.vectorOutline,
			Style.OUTLINE_WIDTH.is(3.0f),
			Style.HIGHLIGHT.is(0xFF412C2C),
			Style.COLOR.is(0xFFAFFF13));
	
	private Styles descriptionStyle = Styles.make(
			//Style.BACKGROUND.is(Background.solid(bgColor)),
			Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.PLAIN, 10)),
			Style.TEXT_WRAP.on,
			Style.HALIGN.left,
			Style.VALIGN.top,
			Style.TEXT_EFFECT.vectorOutline,
			Style.OUTLINE_WIDTH.is(2.0f),
			Style.HIGHLIGHT.is(0xFF412C2C),
			Style.COLOR.is(0xFFFFFFFF));


	public BadgesScreenEx(TrollsVsGoatsGame game) {
		super(game);
	}

	private void addCheatKeys() {
		PlayN.keyboard().setListener(new Listener() {

			@Override
			public void onKeyUp(Event event) {
				if (event.key().equals(Key.ENTER)) {
					for (Badge badge : model.badges())
						badge.setAchieved();
					wasAdded();
				}
			}

			@Override
			public void onKeyTyped(TypedEvent event) {
			}

			@Override
			public void onKeyDown(Event event) {
			}
		});
	}

	@Override
	protected Group createIface() {
		Image bg;
		if (this.width() == 800)
			bg = getImage("backgrounds/800_600/badges_back_800_600");
		else
			bg = getImage("backgrounds/1024_720/badges_back_1024_720");
		root.addStyles(Style.BACKGROUND.is(Background.image(bg)));

		Group tiles = new Group(new AbsoluteLayout());
		Group myroot = new Group(new AbsoluteLayout());
		Group top = new Group(new AbsoluteLayout());

		iconChildBoard = getIcon("cut_screens/badges/board");
		Icon rope_l = getIcon("cut_screens/badges/rope");
		int y_pos = Y_START_POS + 30;

		// Add top panel stuff
		float title_board_x = this.width() - 480;
		float title_board_y = y_pos - 102;
		//Board
		Icon titleBoard = getIcon ("cut_screens/badges/title_board");
		Icon title = getIcon ("cut_screens/badges/badges_title");
		top.add (AbsoluteLayout.at (new Label (titleBoard), title_board_x, title_board_y, titleBoard.width(), titleBoard.height()));
		float title_x = title_board_x + titleBoard.width()/2 - title.width()/2;
		float title_y = title_board_y + titleBoard.height()/2 - title.height()/2;
		top.add (AbsoluteLayout.at (new Label (title), title_x, title_y, title.width(), title.height()));
		//Top ropes
		Icon lRope = getIcon ("cut_screens/badges/rope_title_l");
		Icon rRope = getIcon ("cut_screens/badges/rope_title_r");
		top.add(AbsoluteLayout.at (new Label (lRope), title_board_x+79, title_board_y-38, lRope.width(), lRope.height()));
		top.add(AbsoluteLayout.at (new Label (rRope), title_board_x+256, title_board_y-38, rRope.width(), rRope.height()));
		//Bottom ropes
		float bottomRopeY = title_board_y+77;
		top.add(AbsoluteLayout.at (new Label (rope_l), title_board_x+22, bottomRopeY, rope_l.width(), rope_l.height()));
		top.add(AbsoluteLayout.at (new Label (rope_l), title_board_x+253, bottomRopeY, rope_l.width(), rope_l.height()));

		Badge[] allBadges = model.badges();
		ArrayList<Badge> keepBadges = new ArrayList<Badge>(allBadges.length);
		for (Badge b : allBadges)
			if (!b.name().startsWith("rock_solid"))
				keepBadges.add(b);
		
		Badge[] badges = keepBadges.toArray(new Badge[0]);
		Image badgeImage = null;

		for (int i = 0; i < badges.length; i++) {

			Badge badge = badges[i];
			
			//add board
			if(i==badges.length-1){
				Icon board = getIcon("cut_screens/badges/board_ultra");
				tiles.add(AbsoluteLayout.at(new Label(board), 0, y_pos, board.width(), board.height()));
			} else {
				tiles.add(AbsoluteLayout.at(new Label(iconChildBoard), 0, y_pos, iconChildBoard.width(), iconChildBoard.height()));
			}
			//Add the ropes to the top and bottom
			tiles.add(AbsoluteLayout.at(new Label(rope_l), 12, y_pos-34, rope_l.width(), rope_l.height()));
			tiles.add(AbsoluteLayout.at(new Label(rope_l), (iconChildBoard.width()-45), y_pos-34, rope_l.width(), rope_l.height()));
			
			float xy = 58;
			
			if (!badge.isAchieved())
			{
				badgeImage = getImage("badges/badge_lock");
				Label badgeLockButton = new Label("").setConstraint(Constraints.fixedSize(xy, xy));
				badgeLockButton.setStyles(Style.BACKGROUND.is(Background.image(badgeImage)));
				tiles.add(AbsoluteLayout.at(badgeLockButton, 45, y_pos+20, xy, xy));
			}
			else
			{
				badgeImage = getImage("badges/" + badge.iconName());
				final Label badgeButton = new Label("").setConstraint(Constraints.fixedSize(xy, xy));
				badgeButton.setStyles(Style.BACKGROUND.is(Background.image(badgeImage)));
				tiles.add(AbsoluteLayout.at(badgeButton, 45, y_pos+20, xy, xy));
			}

			final Label l = new Label(badge.description()).setStyles(descriptionStyle);
			tiles.add(AbsoluteLayout.at((new Label(badge.displayName()).addStyles(nameStyle)), 117, y_pos + 10 , 150, 26));
			tiles.add(AbsoluteLayout.at(l, 117, y_pos + 10 + 24, 130, 60));

			y_pos += iconChildBoard.height() + (rope_l.height()-55 );
		}

		myroot.add(AbsoluteLayout.at(tiles, title_board_x+10, 10, iconChildBoard.width(), iconChildBoard.height() * badges.length));

		scroll = new ScrollBar(tiles, (int)(iconChildBoard.height() + (rope_l.height()-55 )), badges.length - 4);
		
		final Button upBtn = createButton ("scroll_arrow_up");
		final Button downBtn = createButton ("scroll_arrow_down");
		final Button draggerBtn = createButton ("scroll_b");
		scroll.upButton = upBtn;
		scroll.downButton = downBtn;
		scroll.dragger = draggerBtn;
		scroll.init();

		Image scrollBG = getImage("cut_screens/badges/scroll_bar");
		scroll.setBarBackgroundImage(scrollBG);
		
		if (badges.length > 4)
		{
			top.add(AbsoluteLayout.at (new Label (rope_l), title_board_x+319, bottomRopeY, rope_l.width(), rope_l.height()));
			myroot.add(AbsoluteLayout.at(scroll, title_board_x+304, 38, scrollBG.width(), scrollBG.height()));
		}
		
		myroot.add(AbsoluteLayout.at(top, 0, 0));

		return myroot;
	}
	
	private Button createButton(final String btnName) {
		Icon icon = getIcon("cut_screens/badges/" + btnName + "_inactive");
		final Button btn = new Button(icon).setStyles(Style.VALIGN.center, Style.HALIGN.center, Style.BACKGROUND.is(Background.blank()));
		
		btn.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseDown(ButtonEvent event) {
				Icon selectIcon = getIcon("cut_screens/badges/" + btnName + "_select");
				btn.icon.update(selectIcon);
				super.onMouseUp(event);
			}
			
			@Override
			public void onMouseUp(ButtonEvent event) {
				Icon selectIcon = getIcon("cut_screens/badges/" + btnName + "_active");
				btn.icon.update(selectIcon);
				super.onMouseUp(event);
			}

			@Override
			public void onMouseOver(MotionEvent event) {
				Icon activeIcon = getIcon("cut_screens/badges/" + btnName + "_active");
				btn.icon.update(activeIcon);
			}

			@Override
			public void onMouseOut(MotionEvent event) {
				btn.icon.update(getIcon("cut_screens/badges/" + btnName + "_inactive"));
			}
		});
		
		return btn;
	}

	@Override
	protected Group createButtomPanel() {
		return null;
	}

	@Override
	public String[] images() {
		ArrayList<String> names = new ArrayList<String>();
		for (Badge badge : model.badges()) {
			if (!badge.name().startsWith("rock_solid"))
				names.add("badges/" + badge.name());
		}

		names.add("cut_screens/badges/back_active");
		names.add("cut_screens/badges/back_inactive");
		names.add("cut_screens/badges/back_select");
		names.add("cut_screens/badges/board_ultra");
		names.add("cut_screens/badges/board");

		names.add("cut_screens/badges/rope_title_l");
		names.add("cut_screens/badges/rope_title_r");
		names.add("cut_screens/badges/rope");
		names.add("cut_screens/badges/scroll_arrow_down_active");
		names.add("cut_screens/badges/scroll_arrow_down_inactive");
		names.add("cut_screens/badges/scroll_arrow_down_select");
		names.add("cut_screens/badges/scroll_arrow_up_select");
		names.add("cut_screens/badges/scroll_arrow_up_inactive");
		names.add("cut_screens/badges/scroll_arrow_up_active");
		names.add("cut_screens/badges/scroll_b_active");
		names.add("cut_screens/badges/scroll_b_inactive");
		names.add("cut_screens/badges/scroll_b_select");
		names.add("cut_screens/badges/scroll_bar");
		names.add("cut_screens/badges/title_board");
		names.add("cut_screens/badges/badges_title");

		names.add("badges/badge_lock");
		
		names.add("backgrounds/800_600/badges_back_800_600");
		names.add("backgrounds/1024_720/badges_back_1024_720");

		return names.toArray(new String[names.size()]);
	}

	@Override
	protected String title() {
		return "";
	}

	@Override
	public void wasShown() {
		addCheatKeys();
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
