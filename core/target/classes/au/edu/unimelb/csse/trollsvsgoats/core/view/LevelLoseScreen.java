package au.edu.unimelb.csse.trollsvsgoats.core.view;

import java.util.ArrayList;
import java.util.List;

import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import react.UnitSlot;
import au.edu.unimelb.csse.trollsvsgoats.core.TrollsVsGoatsGame;
import au.edu.unimelb.csse.trollsvsgoats.core.model.Badge;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Icon;
import tripleplay.ui.Label;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AbsoluteLayout;

public class LevelLoseScreen extends View {

	//Strings for image paths
	public static String IMAGEPATH = "cut_screens/failed/";
	public static String BUTTONB = IMAGEPATH+"button_b";
	public static String BUTTONU = IMAGEPATH+"button_u";

	//Strings for interface constants
	private static int PANELWIDTH = 185;
	private static int PANELHEIGHT = 50;
	private static float PANELSTART = -74;

	//The groups for the interface
	private Group iFace;
	private Group panel;

	//The sub panels
	private Group titleBoard;
	private Group levelButtonPanel;
	private Group retryButtonPanel;
	
	public LevelLoseScreen(TrollsVsGoatsGame game) {
		super(game);
	}

	@Override
	protected Group createIface() {
		//Set the background
		root.addStyles(Style.BACKGROUND.is(Background.image(getImage("backgrounds/1024_720/loser_back_1024_720"))));
		//Instantiate the group
		iFace = new Group(new AbsoluteLayout());
		//Create the button panel to put in the group
		createPanel();
		//Add the panel to the top layer, centered and dropped from the top.
		iFace.add(AbsoluteLayout.at(panel, ((model.width)-2*PANELWIDTH-30), PANELSTART));
		return iFace;
	}

	private void createPanel(){
		//Create the overall panel
		panel = new Group(new AbsoluteLayout());

		//Create and add the titlebaord
		createTitleBoard();
		panel.add(AbsoluteLayout.at(titleBoard,0,0));

		//Now create the button panels with the strings
		createLevelButton();
		panel.add(AbsoluteLayout.at(levelButtonPanel,40,110));
		createRetryButton();
		panel.add(AbsoluteLayout.at(retryButtonPanel,40,183));

	}

	private void createTitleBoard(){
		titleBoard = new Group(new AbsoluteLayout());
		//Make title label
		Label title = new Label(getIcon(IMAGEPATH+"title_board"));
		titleBoard.add(AbsoluteLayout.at(title,0,0));

		//Add the left and right title ropes
		Label leftRope = new Label(getIcon(IMAGEPATH+"rope_title_l"));
		titleBoard.add(AbsoluteLayout.at(leftRope,80,-37));
		Label rightRope = new Label(getIcon(IMAGEPATH+"rope_title_r"));
		titleBoard.add(AbsoluteLayout.at(rightRope,256,-37));

		//Add the title text 
		titleBoard.add(AbsoluteLayout.at(new Label(getIcon(IMAGEPATH+"leve_failed_title")), 135,20));
	}

	private void createLevelButton(){
		levelButtonPanel = new Group(new AbsoluteLayout());

		//Create the button
		Button next = createButton(BUTTONB);
		levelButtonPanel.add(AbsoluteLayout.at(next,0,0));

		//Configure the button to load the next level
		next.clicked().connect(new UnitSlot(){
			@Override
			public void onEmit() {
				game.stack().remove(_this, ScreenStack.NOOP);
			}
		});;

		//Add the ropes to the top
		levelButtonPanel.add(AbsoluteLayout.at(new Label(getIcon(IMAGEPATH+"rope")),12,-34));
		levelButtonPanel.add(AbsoluteLayout.at(new Label(getIcon(IMAGEPATH+"rope")),243,-34));

		//Add the texd
		levelButtonPanel.add(AbsoluteLayout.at(new Label(getIcon(IMAGEPATH+"t_level")),110,20));

	}

	private void createRetryButton(){
		retryButtonPanel = new Group(new AbsoluteLayout());
		//Create the button
		Button retry = createButton(BUTTONU);
		retryButtonPanel.add(AbsoluteLayout.at(retry,0,0));

		//Connect the retry button to the appropriate output
		retry.clicked().connect(new UnitSlot(){
			@Override
			public void onEmit() {
				//Swap back to the game view and update
				game.loadLevel(model.levelIndex(), true);
			}

		});

		//Add the ropes to the top
		retryButtonPanel.add(AbsoluteLayout.at(new Label(getIcon(IMAGEPATH+"rope")),12,-34));
		retryButtonPanel.add(AbsoluteLayout.at(new Label(getIcon(IMAGEPATH+"rope")),243,-34));
		//Add the texd
		retryButtonPanel.add(AbsoluteLayout.at(new Label(getIcon(IMAGEPATH+"t_retry")),102,20));
	}
	
	@Override
	protected String title() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] images() {
		ArrayList<String> names = new ArrayList<String>();
		//aDD THE BACKGROUND
		names.add("backgrounds/1024_720/loser_back_1024_720");

		//Add the buttons
		names.add(BUTTONB+"_active");
		names.add(BUTTONB+"_inactive");
		names.add(BUTTONB+"_select");
		names.add(BUTTONU+"_active");
		names.add(BUTTONU+"_inactive");
		names.add(BUTTONU+"_select");

		//Add the ropes
		names.add(IMAGEPATH+"rope_title_l");
		names.add(IMAGEPATH+"rope_title_r");
		names.add(IMAGEPATH+"rope");

		//Add the text
		names.add(IMAGEPATH+"leve_failed_title");
		names.add(IMAGEPATH+"t_level");
		names.add(IMAGEPATH+"t_retry");

		//Add the titleboard
		names.add(IMAGEPATH+"title_board");



		return names.toArray(new String[names.size()]);
	}

	private Button createButton(final String btnName) {
		Icon icon = getIcon(btnName + "_inactive");
		final Button btn = new Button(icon).setStyles(Style.VALIGN.center, Style.HALIGN.center, Style.BACKGROUND.is(Background.blank()));

		btn.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseDown(ButtonEvent event) {
				Icon selectIcon = getIcon(btnName + "_select");
				btn.icon.update(selectIcon);
				super.onMouseUp(event);
			}

			@Override
			public void onMouseUp(ButtonEvent event) {
				Icon selectIcon = getIcon(btnName + "_active");
				btn.icon.update(selectIcon);
				super.onMouseUp(event);
			}

			@Override
			public void onMouseOver(MotionEvent event) {
				Icon activeIcon = getIcon(btnName + "_active");
				btn.icon.update(activeIcon);
			}

			@Override
			public void onMouseOut(MotionEvent event) {
				btn.icon.update(getIcon(btnName + "_inactive"));
			}
		});

		return btn;
	}
}
