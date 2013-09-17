package au.edu.unimelb.csse.trollsvsgoats.core.view;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.log;

import java.util.ArrayList;

import playn.core.Font;
import playn.core.Image;
import playn.core.Mouse;
import playn.core.PlayN;
import playn.core.Mouse.MotionEvent;
import playn.core.util.Callback;
import au.edu.unimelb.csse.trollsvsgoats.core.TrollsVsGoatsGame;
import react.Function;
import react.UnitSlot;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Constraints;
import tripleplay.ui.Group;
import tripleplay.ui.Icon;
import tripleplay.ui.Label;
import tripleplay.ui.Slider;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;
import tripleplay.ui.ValueLabel;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.ui.layout.AxisLayout;

//this is a test class to check controls
public class LevelLoadScreen extends View {
	private final int Y_START_POS = 0;
	
	private Styles titleStyle = Styles.make(
			Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.BOLD, 25)),
			Style.HALIGN.center,
			Style.TEXT_EFFECT.vectorOutline,
			Style.OUTLINE_WIDTH.is(4.0f),
			Style.HIGHLIGHT.is(0xFF412C2C),
			Style.COLOR.is(0xFFFFFFFF));
	
	private Styles bigLabel = Styles.make(
			Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.BOLD, 15)),
			Style.HALIGN.center,
			Style.TEXT_EFFECT.vectorOutline,
			Style.OUTLINE_WIDTH.is(3.0f),
			Style.HIGHLIGHT.is(0xFF412C2C),
			Style.COLOR.is(0xFFFFFFFF));
	
	private Styles helpStyle = Styles.make(
			//Style.BACKGROUND.is(Background.solid(bgColor)),
			Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.PLAIN, 11)),
			Style.TEXT_WRAP.on,
			Style.HALIGN.left,
			Style.TEXT_EFFECT.shadow,
			Style.SHADOW.is(0xFF412C2C),
			Style.COLOR.is(0xFFFFFFFF));
	private Label previewText;
	private Label previewImage;
	private int levelID;
	
	public LevelLoadScreen(TrollsVsGoatsGame game, int level) {
		super(game);
		levelID = level;
	}

	@Override
	protected Group createIface() {
		Image bg;
		if (this.width() == 800)
			bg = getImage("backgrounds/800_600/help_back_800_600");
		else
			bg = getImage("backgrounds/1024_720/help_back_1024_720");
		root.addStyles(Style.BACKGROUND.is(Background.image(bg)));
		
		topPanel.addStyles(Style.BACKGROUND.is(Background.blank()));

		Group myroot = new Group(new AbsoluteLayout());
		Group top = new Group(new AbsoluteLayout());
		Group tiles = new Group(new AbsoluteLayout());
		Group left = new Group(new AbsoluteLayout());

		Icon rope_l = getIcon("cut_screens/level_load/rope");
		int y_pos = Y_START_POS + 30;
		int boardWidth = (int)getImage("cut_screens/level_load/content_board").width();
		int boardHeight = (int)getImage("cut_screens/level_load/content_board").height();

		// Add top panel stuff
		float title_board_x = this.width() - 430;
		float title_board_y = y_pos - 102;
		//Board
		Icon titleBoard = getIcon ("cut_screens/level_load/title_board");
		top.add (AbsoluteLayout.at (new Label (titleBoard), title_board_x, title_board_y, titleBoard.width(), titleBoard.height()));
		float title_x = title_board_x + titleBoard.width()/2 - 100;
		float title_y = title_board_y + titleBoard.height()/2 - 20;
		top.add (AbsoluteLayout.at (new Label ("Level " + levelID).setStyles(titleStyle), title_x, title_y, 200, 40));
		//Top ropes
		Icon lRope = getIcon ("cut_screens/level_load/rope_title_l");
		Icon rRope = getIcon ("cut_screens/level_load/rope_title_r");
		top.add(AbsoluteLayout.at (new Label (lRope), title_board_x+79, title_board_y-38, lRope.width(), lRope.height()));
		top.add(AbsoluteLayout.at (new Label (rRope), title_board_x+256, title_board_y-38, rRope.width(), rRope.height()));

		// Add the main board and ropes
		Icon board = getIcon("cut_screens/level_load/content_board");
		tiles.add(AbsoluteLayout.at (new Label(board), 0, 0));
		tiles.add(AbsoluteLayout.at(new Label(rope_l), 12, -34, rope_l.width(), rope_l.height()));
		tiles.add(AbsoluteLayout.at(new Label(rope_l), (boardWidth-46), -34, rope_l.width(), rope_l.height()));
		previewText = new Label().setStyles(helpStyle);
		tiles.add(AbsoluteLayout.at(previewText, 20, 30, 300, 150));
		
		// Load content for the main board
		String path = "levelinfo/" + levelID + ".txt";
		assets().getText(path, new Callback<String>() {
			@Override
			public void onSuccess(String result) {
				previewText.text.update(result);
			}

			@Override
			public void onFailure(Throwable cause) {
				log().error(cause.toString());
			}
        });
		
		previewImage = new Label("");
		setPreviewImage(levelID);
		tiles.add(AbsoluteLayout.at(previewImage, 23, 188, 304, 228));
		
		// Left boards
		int leftXPos = (int) (title_board_x - getIcon("cut_screens/level_load/tab_button_u_active").width() - 60);
		int leftYPos = 45;
		float labelWidth = 150;
		float labelXPos = leftXPos + getIcon("cut_screens/level_load/tab_button_u_active").width()/2 - labelWidth/2;
		
		// play
		final Button playButton = createButton("tab_button_u", "principles");
		playButton.clicked().connect(new UnitSlot() {
			@Override
			public void onEmit() {
				game.loadLevel(levelID, true);
			}
		});
		
		left.add(AbsoluteLayout.at(playButton, leftXPos, leftYPos));
		final Label labelPrinciples = new Label("Play").setStyles(bigLabel);
		left.add(AbsoluteLayout.at (labelPrinciples, labelXPos, leftYPos+20, labelWidth, 20));
		
		Icon longRope = getIcon("cut_screens/level_load/rope_title_long");
		left.add(AbsoluteLayout.at(new Label(longRope), leftXPos+12, title_board_y-37));
		left.add(AbsoluteLayout.at(new Label(longRope), leftXPos+173, title_board_y-37));

		// Add groups to root group
		myroot.add(AbsoluteLayout.at(top, 0, 0));
		myroot.add(AbsoluteLayout.at(tiles, title_board_x + 10, 39, boardWidth, boardHeight));
		myroot.add(AbsoluteLayout.at(left, 0, 0));

		return myroot;
	}
	
	private Button createButton(final String btnName, final String helpPage) {
		Icon icon = getIcon("cut_screens/level_load/" + btnName + "_inactive");
		final Button btn = new Button(icon).setStyles(Style.VALIGN.center, Style.HALIGN.center, Style.BACKGROUND.is(Background.blank()));
		
		btn.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseOver(MotionEvent event) {
				Icon activeIcon = getIcon("cut_screens/level_load/" + btnName + "_active");
				btn.icon.update(activeIcon);
			}

			@Override
			public void onMouseOut(MotionEvent event) {
				btn.icon.update(getIcon("cut_screens/level_load/" + btnName + "_inactive"));
			}
		});
		
		return btn;
	}
	
	@Override
	public String[] images() {
		ArrayList<String> names = new ArrayList<String>();
		for (int i=1; i<=game.getNumLevels(); i++) {
			names.add("cut_screens/level_load/i_" + i);
		}
		
		names.add("cut_screens/level_load/back_active");
		names.add("cut_screens/level_load/back_inactive");
		names.add("cut_screens/level_load/back_select");
		
		names.add("cut_screens/level_load/content_board");
		names.add("cut_screens/level_load/title_board");

		names.add("cut_screens/level_load/rope_title_long");
		names.add("cut_screens/level_load/rope_title_l");
		names.add("cut_screens/level_load/rope_title_r");
		names.add("cut_screens/level_load/rope");
		names.add("cut_screens/level_load/tab_button_u_active");
		names.add("cut_screens/level_load/tab_button_u_inactive");
		names.add("cut_screens/level_load/tab_button_u_select");
		
		names.add("backgrounds/800_600/help_back_800_600");
		names.add("backgrounds/1024_720/help_back_1024_720"); // loading this causes out of memory exceptions

		return names.toArray(new String[names.size()]);
	}
	
	private void setPreviewImage(int toLoad) {
		previewImage.setStyles(Style.BACKGROUND.is(Background.image(getImage("cut_screens/level_load/i_" + toLoad))));
	}
	
	protected Group sliderAndLabel (Slider slider, String minText) {
        ValueLabel label = new ValueLabel(slider.value.map(FORMATTER)).
            setStyles(Style.HALIGN.right, Style.FONT.is(FIXED)).
            setConstraint(Constraints.minSize(minText));
        return new Group(AxisLayout.horizontal()).add(slider, label);
    }
	
	protected Function<Float,String> FORMATTER = new Function<Float,String>() {
        public String apply (Float value) {
            return String.valueOf(value.intValue());
        }
    };
    
    protected static Font FIXED = PlayN.graphics().createFont("Fixed", Font.Style.PLAIN, 16);

	@Override
	protected String title() {
		
		return "";
	}

}
