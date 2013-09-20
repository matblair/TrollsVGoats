package au.edu.unimelb.csse.trollsvsgoats.core.view;

import java.util.ArrayList;
import java.util.List;

import playn.core.Font;
import playn.core.Json;
import playn.core.PlayN;
import playn.core.Json.Object;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import react.UnitSlot;

import au.edu.unimelb.csse.trollsvsgoats.core.TrollsVsGoatsGame;
import au.edu.unimelb.csse.trollsvsgoats.core.model.Badge;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Icon;
import tripleplay.ui.Label;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.ui.layout.AxisLayout;

public class LevelWinScreen extends View {

	//Strings for image paths
	public static String IMAGEPATH = "cut_screens/winner/";
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
	private Group starBoard;
	private Group nextButtonPanel;
	private Group retryButtonPanel;

	//Json object for scores
	private Json.Object scores;
	
	//lAST SCORE FOR levels
	private int lastScore;

	public LevelWinScreen(Json.Object scores, TrollsVsGoatsGame game, int lastScore) {
		super(game);
		this.scores = scores;
		this.lastScore = lastScore;
	}

	@Override
	protected Group createIface() {
		//Set the background
		root.addStyles(Style.BACKGROUND.is(Background.image(getImage("backgrounds/1024_720/winner_back_1024_720"))));
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
		//Create and add the starboard
		createStarBoard();
		panel.add(AbsoluteLayout.at(starBoard,10,112));

		//Create and add the titlebaord
		createTitleBoard();
		panel.add(AbsoluteLayout.at(titleBoard,0,0));

		//Now create the button panels with the strings
		createNextButton();
		panel.add(AbsoluteLayout.at(nextButtonPanel,40,355));
		createRetryButton();
		panel.add(AbsoluteLayout.at(retryButtonPanel,40,428));


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

		//Add the ropes below to the left and right
		Label rope = new Label(getIcon(IMAGEPATH+"rope"));
		Label rope2 = new Label(getIcon(IMAGEPATH+"rope"));
		titleBoard.add(AbsoluteLayout.at(rope,22,77));
		titleBoard.add(AbsoluteLayout.at(rope2,313,77));

		//Add the title text 
		titleBoard.add(AbsoluteLayout.at(new Label(getIcon(IMAGEPATH+"level_completed_title")), 100,20));

	}

	private void createStarBoard(){
		//Create the board
		starBoard = new Group(new AbsoluteLayout());

		//Create the panel bg
		Label board = new Label(getIcon(IMAGEPATH+"star_board"));
		starBoard.add(AbsoluteLayout.at(board,0,0));

		Group stars = new Group(new AbsoluteLayout());
		//Work out our score
		int score = model.levelScore(model.levelIndex());

		//Add starts
		for(int i=0; i<3; i++){
			Label star = null;
			if(i<lastScore || i==0){
				star = new Label(getIcon(IMAGEPATH+"star"));
			}
			if(star!=null){
				if(i==1){
					//To handle the raised star
					stars.add(AbsoluteLayout.at(star,(int)82.5*i,-20));
				}else{
					stars.add(AbsoluteLayout.at(star,(int)82.5*i,0));
				}
			} else {
				break;
			}
		}

		//		//Now check if we have to add text
		if(score==3){
			//Then we will present a congrats string.
			Label strLabel = new Label("Congratulations! Minimal cost!").setStyles(Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.BOLD, 14)),
					Style.TEXT_EFFECT.shadow,
					Style.SHADOW.is(0xFF412C2C),
					Style.HALIGN.center, 
					Style.COLOR.is(0xFFFFFFFF));
			starBoard.add(AbsoluteLayout.at(strLabel,35,110));
		} else {
			//Need to get the score to remove it too.
			String nextLevel = null;

			for (String cost : scores.keys()) {
				if (score == (scores.getInt(cost)-1)) {
					nextLevel = cost;
				}
			}

			Label strLabel = new Label("Reduce cost to $" + nextLevel + " for the next star!").setStyles(Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.BOLD, 14)),
					Style.TEXT_EFFECT.shadow,
					Style.SHADOW.is(0xFF412C2C),
					Style.HALIGN.center, 
					Style.COLOR.is(0xFFFFFFFF));
			starBoard.add(AbsoluteLayout.at(strLabel,20,110));

		}

		//Get the badge and add it
		final List<Badge> badges = model.newAchievedBadges();
		//Set the badge to be achieved
		for (Badge badge : badges) {
			if (!badge.isAchieved())
				game.setBadgeAchieve(badge);
				model.setBadgeAchieved(badge.name());

		}

		//Now get the first item and then use it to display
		if(!model.momentOverZero()){
			Label rs = new Label("").setStyles(Style.BACKGROUND.is(Background.image(getImage("badges/a_level"))));
			starBoard.add(AbsoluteLayout.at(rs, getIcon(IMAGEPATH+"star_board").width()/2-29,150,58,58));
		}

		//Add the starboard
		starBoard.add(AbsoluteLayout.at(stars,63,44));

	}

	private void createNextButton(){
		nextButtonPanel = new Group(new AbsoluteLayout());

		//Create the button
		Button next = createButton(BUTTONB);
		nextButtonPanel.add(AbsoluteLayout.at(next,0,0));

		//Configure the button to load the next level
		next.clicked().connect(new UnitSlot(){
			@Override
			public void onEmit() {
				game.loadNextLevel();				
			}
		});;

		//Add the ropes to the top
		nextButtonPanel.add(AbsoluteLayout.at(new Label(getIcon(IMAGEPATH+"rope")),12,-34));
		nextButtonPanel.add(AbsoluteLayout.at(new Label(getIcon(IMAGEPATH+"rope")),243,-34));

		//Add the texd
		nextButtonPanel.add(AbsoluteLayout.at(new Label(getIcon(IMAGEPATH+"t_next")),110,20));

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
		names.add("backgrounds/1024_720/winner_back_1024_720");

		//Add the rock solid badge
		names.add("cut_screens/select_levels/rock_solid");

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

		//Add the board
		names.add(IMAGEPATH+"star_board");
		names.add(IMAGEPATH+"star");

		//Add the text
		names.add(IMAGEPATH+"level_completed_title");
		names.add(IMAGEPATH+"t_lower_cost");
		names.add(IMAGEPATH+"t_next");
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
