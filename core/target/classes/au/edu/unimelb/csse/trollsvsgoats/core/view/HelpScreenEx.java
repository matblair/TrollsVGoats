package au.edu.unimelb.csse.trollsvsgoats.core.view;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import playn.core.Font;
import playn.core.Image;
import playn.core.Mouse;
import playn.core.PlayN;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.util.Callback;
import au.edu.unimelb.csse.trollsvsgoats.core.TrollsVsGoatsGame;
import au.edu.unimelb.csse.trollsvsgoats.core.model.Badge;
import react.Function;
import react.UnitSlot;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.ClickableTextWidget;
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
public class HelpScreenEx extends View {
	private final int Y_START_POS = 0;

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
			Style.VALIGN.top,
			Style.HALIGN.left,
			Style.TEXT_EFFECT.shadow,
			Style.TEXT_EFFECT.SHADOW.is(0xFF412C2C),
			Style.COLOR.is(0xFFFFFFFF));
	private Label helpText;
	private Label helpImage;
	private Button contentBoard;

	public HelpScreenEx(TrollsVsGoatsGame game) {
		super(game);
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

		Icon rope_l = getIcon("cut_screens/help/rope");
		int y_pos = Y_START_POS + 30;
		int boardWidth = (int)getImage("cut_screens/help/content_board").width();
		int boardHeight = (int)getImage("cut_screens/help/content_board").height();

		// Add top panel stuff
		float title_board_x = this.width() - 430;
		float title_board_y = y_pos - 102;
		//Board
		Icon titleBoard = getIcon ("cut_screens/help/title_board");
		Icon title = getIcon ("cut_screens/help/help_title");
		top.add (AbsoluteLayout.at (new Label (titleBoard), title_board_x, title_board_y, titleBoard.width(), titleBoard.height()));
		float title_x = title_board_x + titleBoard.width()/2 - title.width()/2;
		float title_y = title_board_y + titleBoard.height()/2 - title.height()/2;
		top.add (AbsoluteLayout.at (new Label (title), title_x, title_y, title.width(), title.height()));
		//Top ropes
		Icon lRope = getIcon ("cut_screens/help/rope_title_l");
		Icon rRope = getIcon ("cut_screens/help/rope_title_r");
		top.add(AbsoluteLayout.at (new Label (lRope), title_board_x+79, title_board_y-38, lRope.width(), lRope.height()));
		top.add(AbsoluteLayout.at (new Label (rRope), title_board_x+256, title_board_y-38, rRope.width(), rRope.height()));

		// Add the main board and ropes
		contentBoard = new Button(getIcon("cut_screens/help/content_board"));
		contentBoard.setStyles(Style.BACKGROUND.is(Background.blank()));
		contentBoard.layer.setInteractive(false);
		tiles.add(AbsoluteLayout.at (contentBoard, 0, 0));
		tiles.add(AbsoluteLayout.at(new Label(rope_l), 12, -34, rope_l.width(), rope_l.height()));
		tiles.add(AbsoluteLayout.at(new Label(rope_l), (boardWidth-46), -34, rope_l.width(), rope_l.height()));
		helpText = new Label().setStyles(helpStyle);
		tiles.add(AbsoluteLayout.at(helpText, 20, 30, 300, 390));

		// Load content for the main board
		helpImage = new Label("");
		loadText("introduction");
		setHelpImage("introduction");
		tiles.add(AbsoluteLayout.at(helpImage, 23, 188, 304, 228));

		// Left boards
		int leftXPos = (int) (title_board_x - getIcon("cut_screens/help/tab_button_active").width() - 60);
		int leftYPos = 45;
		int leftYIncr = (int) (getIcon("cut_screens/help/tab_button_active").height() + 4);
		float labelWidth = 150, labelHeight = getIcon("cut_screens/help/tab_button_active").height();
		float labelXPos = leftXPos + getIcon("cut_screens/help/tab_button_active").width()/2 - labelWidth/2;

		// introduction
		final Button introButton = createButton("tab_button", "introduction");
		left.add(AbsoluteLayout.at(introButton, leftXPos, leftYPos));
		final Label labelIntro = new Label("Introduction").setStyles(bigLabel);
		left.add(AbsoluteLayout.at (labelIntro, labelXPos, leftYPos, labelWidth, labelHeight));

		Icon longRope = getIcon("cut_screens/help/rope_title_long");
		left.add(AbsoluteLayout.at(new Label(longRope), leftXPos+12, title_board_y-37));
		left.add(AbsoluteLayout.at(new Label(longRope), leftXPos+173, title_board_y-37));

		leftYPos += leftYIncr;

		// deployment
		final Button deployButton = createButton("tab_button", "deployment");
		left.add(AbsoluteLayout.at(deployButton, leftXPos, leftYPos));
		final Label labelDeploy = new Label("Setup").setStyles(bigLabel);
		left.add(AbsoluteLayout.at (labelDeploy, labelXPos, leftYPos, labelWidth, labelHeight));

		left.add(AbsoluteLayout.at(new Label(rope_l), leftXPos+12, leftYPos-34));
		left.add(AbsoluteLayout.at(new Label(rope_l), leftXPos+173, leftYPos-34));

		leftYPos += leftYIncr;

		// running
		final Button runButton = createButton("tab_button", "running");
		left.add(AbsoluteLayout.at(runButton, leftXPos, leftYPos));
		final Label labelRun = new Label("Play").setStyles(bigLabel);
		left.add(AbsoluteLayout.at (labelRun, labelXPos, leftYPos, labelWidth, labelHeight));

		left.add(AbsoluteLayout.at(new Label(rope_l), leftXPos+12, leftYPos-34));
		left.add(AbsoluteLayout.at(new Label(rope_l), leftXPos+173, leftYPos-34));

		leftYPos += leftYIncr;

		// principles 1
		final Button principlesButton = createButton("tab_button", "principles1");
		left.add(AbsoluteLayout.at(principlesButton, leftXPos, leftYPos));
		final Label labelPrinciples = new Label("Principles 1").setStyles(bigLabel);
		left.add(AbsoluteLayout.at (labelPrinciples, labelXPos, leftYPos, labelWidth, labelHeight));

		left.add(AbsoluteLayout.at(new Label(rope_l), leftXPos+12, leftYPos-34));
		left.add(AbsoluteLayout.at(new Label(rope_l), leftXPos+173, leftYPos-34));

		leftYPos += leftYIncr;


		// principles 2
		final Button principlesButton2 = createButton("tab_button_u", "principles2");
		left.add(AbsoluteLayout.at(principlesButton2, leftXPos, leftYPos));
		final Label labelPrinciples2 = new Label("Principles 2").setStyles(bigLabel);
		left.add(AbsoluteLayout.at (labelPrinciples2, labelXPos, leftYPos, labelWidth, labelHeight));

		left.add(AbsoluteLayout.at(new Label(rope_l), leftXPos+12, leftYPos-34));
		left.add(AbsoluteLayout.at(new Label(rope_l), leftXPos+173, leftYPos-34));

		// Add groups to root group
		myroot.add(AbsoluteLayout.at(top, 0, 0));
		myroot.add(AbsoluteLayout.at(tiles, title_board_x + 10, 39, boardWidth, boardHeight));
		myroot.add(AbsoluteLayout.at(left, 0, 0));

		return myroot;
	}

	private Button createButton(final String btnName, final String helpPage) {
		Icon icon = getIcon("cut_screens/help/" + btnName + "_inactive");
		final Button btn = new Button(icon).setStyles(Style.VALIGN.center, Style.HALIGN.center, Style.BACKGROUND.is(Background.blank()));

		btn.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseDown(ButtonEvent event) {
				Icon selectIcon = getIcon("cut_screens/help/" + btnName + "_select");
				loadText(helpPage);
				btn.icon.update(selectIcon);
				super.onMouseUp(event);
			}

			@Override
			public void onMouseUp(ButtonEvent event) {
				Icon selectIcon = getIcon("cut_screens/help/" + btnName + "_active");
				btn.icon.update(selectIcon);
				super.onMouseUp(event);
			}

			@Override
			public void onMouseOver(MotionEvent event) {
				Icon activeIcon = getIcon("cut_screens/help/" + btnName + "_active");
				btn.icon.update(activeIcon);
			}

			@Override
			public void onMouseOut(MotionEvent event) {
				btn.icon.update(getIcon("cut_screens/help/" + btnName + "_inactive"));
			}
		});

		return btn;
	}

	@Override
	public String[] images() {
		ArrayList<String> names = new ArrayList<String>();

		names.add("cut_screens/help/back_active");
		names.add("cut_screens/help/back_inactive");
		names.add("cut_screens/help/back_select");

		names.add("cut_screens/help/content_board");
		names.add("cut_screens/help/content_board_noimage");
		names.add("cut_screens/help/help_title");
		names.add("cut_screens/help/title_board");

		names.add("cut_screens/help/rope_title_long");
		names.add("cut_screens/help/rope_title_l");
		names.add("cut_screens/help/rope_title_r");
		names.add("cut_screens/help/rope");

		names.add("cut_screens/help/i_introduction");
		names.add("cut_screens/help/i_deployment");
		names.add("cut_screens/help/i_running");
		names.add("cut_screens/help/i_principles");

		names.add("cut_screens/help/tab_button_active");
		names.add("cut_screens/help/tab_button_inactive");
		names.add("cut_screens/help/tab_button_select");
		names.add("cut_screens/help/tab_button_u_active");
		names.add("cut_screens/help/tab_button_u_inactive");
		names.add("cut_screens/help/tab_button_u_select");

		names.add("backgrounds/800_600/help_back_800_600");
		names.add("backgrounds/1024_720/help_back_1024_720"); // loading this causes out of memory exceptions

		return names.toArray(new String[names.size()]);
	}

	public String[] helpFiles() {
		String[] files = new String[] {
				"helpinfo/introduction",
				"helpinfo/running",
				"helpinfo/deployment",
				"helpinfo/principles1",
				"helpinfo/principles2"
		};
		return files;
	}

	private void loadText(String helpPage) {
		String text = game.getText("helpinfo/" + helpPage);
		helpText.text.update(text);
		if (text.length() > 350) {
			contentBoard.icon.update(getIcon("cut_screens/help/content_board_noimage"));
			setHelpImage(null);
		} else {
			contentBoard.icon.update(getIcon("cut_screens/help/content_board"));
			setHelpImage(helpPage);
		}
	}

	private void setHelpImage(String toLoad) {
		if (toLoad == null)
			helpImage.setStyles(Style.BACKGROUND.is(Background.blank()));
		else
			helpImage.setStyles(Style.BACKGROUND.is(Background.image(getImage("cut_screens/help/i_" + toLoad))));
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
