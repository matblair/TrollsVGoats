package au.edu.unimelb.csse.trollsvsgoats.core.view;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.json;
import static playn.core.PlayN.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import playn.core.Font;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Json;
import playn.core.Mouse;
import playn.core.PlayN;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import react.UnitSlot;
import au.edu.unimelb.csse.trollsvsgoats.core.TrollsVsGoatsGame;
import au.edu.unimelb.csse.trollsvsgoats.core.model.Animation;
import au.edu.unimelb.csse.trollsvsgoats.core.model.Square;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.BarrowTroll;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.BigGoat;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.ButtingGoat;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.CheerleaderTroll;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.DiggingTroll;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.FastGoat;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.FastTroll;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.Goat;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.HungryTroll;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.JumpingGoat;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.LittleGoat;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.LittleTroll;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.MegaTroll;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.NormalGoat;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.NormalTroll;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.SpittingTroll;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.Troll;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.Unit;
import au.edu.unimelb.csse.trollsvsgoats.core.model.units.Unit.State;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Constraints;
import tripleplay.ui.Group;
import tripleplay.ui.Icon;
import tripleplay.ui.Icons;
import tripleplay.ui.Label;
import tripleplay.ui.Shim;
import tripleplay.ui.Style;
import tripleplay.ui.Style.Binding;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.layout.TableLayout;

public class LevelScreenEx extends View {

	//User interface information.
	public static final int SQUARE_HEIGHT = 44, SQUARE_WIDTH = SQUARE_HEIGHT;

	//Button Bindings
	private static Binding<Background> selOn;
	private static Binding<Background> selOff;

	//Gameplay constants
	private static final float MAX_MOMENT = 20;
	private static final int PUSHING_DISTANCE = 3;
	private boolean started = false, paused = false, hasMovingUnit = false;

	//For changes between big and small bridges
	private static String BRIDGEBG = "gameplay/1024_720/gameplay_bridge";
	private static String BIGBRIDGEBG = "gameplay/1024_720/gameplay_bridge_big";
	private static String SMALLWALL = "gameplay/1024_720/gameplay_bridge_wall";
	private static String BIGWALL = "gameplay/1024_720/gameplay_bridge_wall_big";
	private static String GAMEBG = "gameplay/1024_720/gameplay_back";

	//The Gates and Pivots
	private static String BIGGATE = "gameplay/1024_720/gameplay_gate_big";
	private static String LATCH = "gameplay/1024_720/gameplay_latch";
	private static String BOTTOMLATCH = "gameplay/1024_720/gameplay_latch_bottom";
	private static String SMALLGATE = "gameplay/1024_720/gameplay_gate";

	//For changes between big and small bridges
	private static String LRBRIDGEBG = "gameplay/800_600/gameplay_bridge";
	private static String LRBIGBRIDGEBG = "gameplay/800_600/gameplay_bridge_big";
	private static String LRSMALLWALL = "gameplay/800_600/gameplay_bridge_wall";
	private static String LRBIGWALL = "gameplay/800_600/gameplay_bridge_wall_big";
	private static String LRGAMEBG = "gameplay/800_600/gameplay_back";

	//The Gates and Pivots
	private static String LRBIGGATE = "gameplay/800_600/gameplay_gate_big";
	private static String LRLATCH = "gameplay/800_600/gameplay_latch";
	private static String LRBOTTOMLATCH = "gameplay/800_600/gameplay_latch_bottom";
	private static String LRSMALLGATE = "gameplay/800_600/gameplay_gate";


	//The Unit Frames
	private static String HIGHLIGHTEDIMAGE = "cut_screens/gameplay/unit_frame_active";
	private static String UNSELECTEDIMAGE = "cut_screens/gameplay/unit_frame_inactive";
	private static String SELECTEDIMAGE = "cut_screens/gameplay/unit_frame_select";

	//For positioning of UI Groups

	private static final float BIGTOPBOARDY = -70;
	private static final float BIGGAMEBOARDY = -55;
	private static final float BIGBOTTOMPANELY = 457;
	private static final float BIGGATEINSIDE = 488;
	private static final float SMALLTOPBOARDY = -70;
	private static final float SMALLGAMEBOARDY = 120;
	private static final float SMALLBOTTOMPANELY = 440;
	private static final float SMALLGATEINSIDE = 488;
	private static final float TOPLATCH = 460;

	private static final float LRBIGTOPBOARDY = -70;
	private static final float LRBIGGAMEBOARDY = -55;
	private static final float LRBIGBOTTOMPANELY = 457;
	private static final float LRBIGGATEINSIDE = 488;
	private static final float LRSMALLTOPBOARDY = -70;
	private static final float LRSMALLGAMEBOARDY = -80;
	private static final float LRSMALLBOTTOMPANELY = 320;
	private static final float LRSMALLGATEINSIDE = 488;
	private static final float LRTOPLATCH = 360;




	//User Interface Elements that are static
	private static final String MOMENTBOARD = "cut_screens/gameplay/nm_board";
	private static final String COSTBOARD = "cut_screens/gameplay/cost_board";
	private static final String STARTBUTTON = "cut_screens/gameplay/play";
	private static final String RESETBUTTON = "cut_screens/gameplay/reset";
	private static final String HEADPATH = "trolls_goats_menu/";
	private static final String BACKUNIT = "cut_screens/gameplay/next_unit";
	private static final String NEXTUNIT = "cut_screens/gameplay/back_unit";
	private static final String UNITSLOCKED = "_locked";
	private static final String STRENGTHICON = "cut_screens/gameplay/strength";
	private static final String SPEEDICON = "cut_screens/gameplay/speed";

	//The interface margins
	private static final float LEFTMARGIN = 15;
	private static final float RIGHTMARGIN = 30;
	private static final float GATESHIM = 40;
	private static float CENTERLEFT = 380;
	private static final float CENTERRIGHT = 380;
	private static final float BOTTOMBRIDGEOFFSET = -20;
	private static final int INITIALEDGE = 25;
	private static final int TILEGAP = 4;


	//Dynamic user interface information
	private static String BRIDGE;
	private static String GATE;
	private static String WALL;
	private boolean messageShown = false;
	protected Label momentLabel;
	protected Label costLabel;
	private static float LATCHLOC;
	private static float GATELOC;
	private static float TOPBOARDY;
	private static float GAMEBOARDY;
	private static float BOTTOMPANELY;

	//Walk animations
	private static Map<String, Integer> walkAnims = new HashMap<String, Integer>();

	//Our count labels to update
	protected Label bigTrollLabel;
	protected Label normalTrollLabel;
	protected Label fastTrollLabel;
	protected Label hungryTrollLabel;
	protected Label littleTrollLabel;
	protected Label spittingTrollLabel;
	protected Label megaTrollLabel;
	protected Label cheerLeaderTrollLabel;
	protected Label diggingTrollLabel;



	//ArrayLists for accesing the 
	private ArrayList<Group> trollHeads = new ArrayList<Group>();
	private ArrayList<Group> goatHeads = new ArrayList<Group>();
	private int goatIndex=0;
	private int trollIndex=0;
	private static int MAXHEADS= 4;
	private Group trollScroll;
	private Group goatScroll;

	// The first unit in a lane.
	// A lane of units is represented as a linked list.
	private Map<Integer, Unit> headTrolls = new HashMap<Integer, Unit>();
	private Map<Integer, Unit> headGoats = new HashMap<Integer, Unit>();

	//A count of each troll used in the game (for limits)
	private Map<String, Integer> trollCounts = new HashMap<String, Integer>();
	private HashSet<String> goatTypes = new HashSet<String>();

	//A collection of all icons created thusfar.
	private Map<String, Button> trollIcons = new HashMap<String, Button>();
	private Map<String, Button> goatIcons = new HashMap<String, Button>();

	// The unit locations before the game starts.
	private Map<Square, Unit> unitsLocations = new HashMap<Square, Unit>();

	// Level specific stats
	private int laneCount;
	private int pivotLocation, bridgeLocation;
	private boolean showUnitMoment = false;
	private int cost, score, moment;

	// Dynamic variables for message passing between methods
	protected Button selTrollIcon;
	private String selTrollType;	
	private Button preSelGoat;

	//The level data
	private Json.Object json;

	//Our user interface groups
	protected Group middleDrawingPanel;
	protected Group middlePanel;
	protected Group bottomPanel;

	//The sub interface groups
	protected Group topMomentPanel;
	protected Group bottomInfoPanel;
	protected Group bottomBridgePanel;
	protected Group bottomTrollPanel;
	protected Group bottomButtonPanel;
	protected Group bottomGoatPanel;
	protected Group goatGroup;
	protected Group trollGroup;

	//For creating the middle game
	private boolean hasPivot;
	private boolean isTrollSide;
	private float midPanelX;
	private float midPanelY;

	/* The standard constructor */
	public LevelScreenEx(TrollsVsGoatsGame game) {
		super(game);
	}

	/** The constructor for a particular level
	 * @param game
	 * @param levelJson the information for our particular level
	 */
	public LevelScreenEx(TrollsVsGoatsGame game, String levelJson) {
		super(game);

		this.json = json().parse(levelJson);
		laneCount = json.getArray("tiles").length();

		System.out.println(game.model().width);
		if(this.width()==800){
			if(laneCount<7){
				BRIDGE = LRBRIDGEBG;
				GATE = LRSMALLGATE;
				WALL = LRSMALLWALL; 
				GAMEBOARDY = LRSMALLGAMEBOARDY;
				BOTTOMPANELY = LRSMALLBOTTOMPANELY;
				TOPBOARDY = LRSMALLTOPBOARDY;
				GATELOC = LRSMALLGATEINSIDE;
				LATCHLOC = LRTOPLATCH;

			} else {
				BRIDGE = LRBIGBRIDGEBG;
				GATE = LRBIGGATE;
				WALL = LRBIGWALL;
				GAMEBOARDY = LRBIGGAMEBOARDY;
				BOTTOMPANELY = LRBIGBOTTOMPANELY;
				TOPBOARDY = LRBIGTOPBOARDY;
				GATELOC = LRBIGGATEINSIDE;
				LATCHLOC = LRTOPLATCH;
			}

		}else {
			//Set the appropriate bridge background
			if(laneCount<7){
				BRIDGE = BRIDGEBG;
				GATE = SMALLGATE;
				WALL = SMALLWALL; 
				GAMEBOARDY = SMALLGAMEBOARDY;
				BOTTOMPANELY = SMALLBOTTOMPANELY;
				TOPBOARDY = SMALLTOPBOARDY;
				GATELOC = SMALLGATEINSIDE;
				LATCHLOC = TOPLATCH;
			} else {
				BRIDGE = BIGBRIDGEBG;
				GATE = BIGGATE;
				WALL = BIGWALL;
				GAMEBOARDY = BIGGAMEBOARDY;
				BOTTOMPANELY = BIGBOTTOMPANELY;
				TOPBOARDY = BIGTOPBOARDY;
				GATELOC = BIGGATEINSIDE;
				LATCHLOC = TOPLATCH;

			}
		}


		//Check if we should be showing moments
		if (model.levelIndex() <= 1){
			showUnitMoment = true;
		}


		//Set the styles for our level
		selOn = Style.BACKGROUND.is(Background.image(getImage(HIGHLIGHTEDIMAGE)));
		selOff =Style.BACKGROUND.is(Background.image(getImage(UNSELECTEDIMAGE)));
	}

	@Override
	protected Group createIface() {
		// Add our background images to the root sytyle
		root.addStyles(Style.BACKGROUND.is(Background.image(getImage(GAMEBG))));
		topPanel.addStyles(Style.BACKGROUND.is(Background.blank()));

		//Create our main interface group for this screen
		Group iface = new Group(new AbsoluteLayout());

		//Enable the back button to be used 
		//to-do: Fix this so it promps to close the game
		back.clicked().disconnect(backSlot);
		back.clicked().connect(new UnitSlot() {
			@Override
			public void onEmit() {
				game.stack().remove(_this, ScreenStack.NOOP);
			}
		});

		//Create the top panel
		createTopPanel();
		//Create the middle panel
		createMiddlePanel();
		//Create the bottom panel
		createBottomPanel();

		//Trying everything as an absolute layout
		iface.add(AbsoluteLayout.at(middlePanel,0,GAMEBOARDY));
		iface.add(AbsoluteLayout.at(bottomPanel,0,BOTTOMPANELY));
		iface.add(AbsoluteLayout.at(topMomentPanel,0,TOPBOARDY));


		return iface;
	}

	////////////////////////////////////////////////////////////////////
	// CREATING TOP UI PANEL									    ////
	////////////////////////////////////////////////////////////////////

	/** Initialises the top panel including all information for 
	 * 	the moment board
	 */
	private void createTopPanel(){

		topMomentPanel = new Group(new AbsoluteLayout());
		Icon momentBoard = getIcon(MOMENTBOARD);
		topMomentPanel.add(AbsoluteLayout.at(new Label(momentBoard),model.screenWidth()/2-momentBoard.width()/2,0));

		Label levelLabel = new Label("LEVEL " + this.model.levelIndex());
		levelLabel.setStyles(Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.BOLD, 26)),
				Style.HALIGN.left, 
				Style.COLOR.is(0xFFCCFF00),
				Style.TEXT_EFFECT.shadow,
				Style.TEXT_EFFECT.SHADOW.is(0xFF412C2C));

		topMomentPanel.add(AbsoluteLayout.at(levelLabel,this.width()/2 - 48,-45));

		momentLabel = new Label();
		momentLabel.setStyles(Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.BOLD, 20)),
				Style.TEXT_EFFECT.shadow,
				Style.TEXT_EFFECT.SHADOW.is(0xFF412C2C),
				Style.HALIGN.center, 
				Style.COLOR.is(0xFFFFFFFF));
		momentLabel.setConstraint(Constraints.fixedWidth(momentBoard.width()));
		momentLabel.text.update(String.valueOf("0 N/m"));
		horizTitle.add(new Shim(365,0));
		horizTitle.add(momentLabel);
		horizTitle.add(new Shim(290,0));

	}

	////////////////////////////////////////////////////////////////////
	// CREATING MIDDLE GAME PANEL								    ////
	////////////////////////////////////////////////////////////////////

	private void createMiddlePanel(){
		middlePanel = new Group(new TableLayout(TableLayout.COL.stretch()));
		createMiddleDrawingPanel();
		middlePanel.add(middleDrawingPanel);
	}

	private void createMiddleDrawingPanel() {
		middleDrawingPanel = new Group(new AbsoluteLayout());
		//Set the background
		middleDrawingPanel.add(AbsoluteLayout.at(new Label(getIcon(BRIDGE)),0,0));

		//Get the layout
		Json.Array tiles = json.getArray("tiles");

		//Work out bridge location
		int width = tiles.getString(1).length();
		bridgeLocation = (width - 1) / 2;

		//Add the latch and gate
		middleDrawingPanel.add(AbsoluteLayout.at(new Label(getIcon(LATCH)),LATCHLOC,0));
		middleDrawingPanel.add(AbsoluteLayout.at(new Label(getIcon(GATE)),GATELOC,GATESHIM));
		System.out.println(laneCount);
		if(laneCount>6){
			//Draw bottom latch
			middleDrawingPanel.add(AbsoluteLayout.at(new Label(getIcon(BOTTOMLATCH)),(segmentToX(bridgeLocation, getIcon(LATCH)))+(getImage(LATCH).width()/4),(getImage(BRIDGE).height()-getImage(BOTTOMLATCH).height()/2)));

		}


		//Set values for working out booleans
		isTrollSide = true;
		hasPivot = false;

		//Go through each line of the lvel and add the appropriate tiles
		for (int lane = laneCount; lane > 0; lane--) {
			isTrollSide = true;
			createLane(width,lane, tiles);
		}

	}

	public void createLane(int width, int lane, Json.Array tiles){
		//Values used for placement calculation
		char tileSymbol;
		Image image;
		int distance;
		//Loop through each segment and determin what needs to be done.
		for (int segment = 0; segment < width; segment++) {

			//Work out the image required
			tileSymbol = tiles.getString(laneCount - lane).charAt(segment);
			image = getTileImages(tileSymbol).get(0);
			Icon icon = getTileIcon(tileSymbol).get(0);
			Button tile = new Button().addStyles(Style.BACKGROUND.is(Background.blank())).setConstraint(Constraints.fixedWidth(SQUARE_WIDTH));

			//Find the segment locations x and y to add squares to
			midPanelX = segmentToX(segment,icon);  
			midPanelY = laneToY(lane,image);
			distance = Math.abs(bridgeLocation - segment);


			//Get the layer for the tile
			GroupLayer squareLayer = tile.layer;

			//Now create the tile symbol for that tile
			switch (tileSymbol) {
			// A segment of a lane.
			case '.':
			case ' ':
				//Set local variables to manipulat
				final float _x = midPanelX;
				final float _y = midPanelY + SQUARE_HEIGHT;
				final boolean _isTrollSide = isTrollSide;
				final int _lane = lane;
				final int _segment = segment;
				final int _distance = distance;
				//Add a listener to the squareLayer (initially will not have
				//any trolls in this tile
				squareLayer.addListener(new Mouse.LayerAdapter() {
					private Square square = new Square(_lane, _segment);
					// Deploy a troll at the square where the
					// mouse clicks.
					@Override
					public void onMouseDown(ButtonEvent event) {
						deployTroll(square, _distance, _segment, _lane, _isTrollSide, _x, _y);
					}
				});
				break;  
				// Gate.
			case '|':
				//creates the gate tile
				isTrollSide = false;
				createGateTile(lane,squareLayer);
				break;
				// Pivot.
			case 'o':
				hasPivot = true;
				pivotLocation = lane;
				squareLayer.setDepth(2);
				break;
				//Goat tiles
			case 'g':// Little goat.
			case 'G':// Normal goat.
			case 'B':// Big goat.
			case 'F':// Fast goat.
			case 'T':// Butting goat.
			case 'J':// Jumping goat.
				// Add the tile layer for this unit.
				deployGoat(squareLayer, segment, tileSymbol, lane, tile, distance, image);
				break;
			default:
				break;
			}
			if (image != null) {
				tile.icon.update(Icons.image(image));
				middleDrawingPanel.add(AbsoluteLayout.at(tile, midPanelX, midPanelY));
			}
		}
	}

	public void deployTroll(final Square square,int _distance, int _segment, int _lane, boolean _isTrollSide, float _x, float _y){
		if (!started && !paused) {
			square.setDistance(_distance);
			square.setX(_x);
			square.setY(_y);
			if(selTrollType == null)
				return;

			if (trollCounts.get(selTrollType) == 0)
				return;


			final Troll troll = newTroll(selTrollType);
			if ((troll instanceof DiggingTroll || troll instanceof SpittingTroll)
					&& _distance > 4)
				return;
			if ((troll instanceof HungryTroll && _distance >1)) return;
			if (_isTrollSide ^ troll.isOnTrollSide())
				return;

			GroupLayer layer = troll.widget().layer;
			troll.widget()
			.addStyles(
					Style.BACKGROUND.is(Background.blank()));

			//			Animation moveAnimation = new Animation(
			//					getImage("animations/trolls_animations/walk/"+selTrollType+"_troll_walk"), 16, troll
			//							.frameTime());

			Animation moveAnimation = new Animation(
					getImage("animations/trolls_animations/walk/"+selTrollType+"_troll_walk"), walkAnims.get(selTrollType), troll
					.frameTime());

			Animation pushAnimation = new Animation(getImage("animations/trolls_animations/push/" + selTrollType + "_troll_push"), 23, troll.frameTime());
			troll.setPushAnimation(pushAnimation);

			troll.setSquare(square);
			troll.setMoveAnimation(moveAnimation);
			troll.setDefaultImage(getImage("animations/trolls_animations/normal/"+selTrollType+"_troll_normal"));


			if (_distance == 1 && troll.speed() != 0)
				troll.setState(State.PUSHING);
			else
				troll.setState(State.MOVING);

			unitsLocations.put(square, troll);
			trollCounts.put(troll.type(),
					trollCounts.get(troll.type()) - 1);

			layer.setOrigin(0, getImage("animations/trolls_animations/normal/"+selTrollType+"_troll_normal")
					.height());
			layer.setDepth(1);

			middleDrawingPanel.add(AbsoluteLayout.at(troll.widget(), _x, _y));
			troll.setParent(middleDrawingPanel);

			layer.addListener(new Mouse.LayerAdapter() {
				@Override
				public void onMouseDown(ButtonEvent event) {
					// Removed the troll if game is not yet
					// started.
					if (!started && !paused) {
						troll.widget().layer.destroy();
						unitsLocations.remove(square);
						trollCounts.put(troll.type(),
								trollCounts.get(troll
										.type()) + 1);
						//updateTrollCounter(troll.type());
						updateCost(-troll.cost());
						updateTrollInfo(troll.type());
					}
				}

				@Override
				public void onMouseOver(MotionEvent event) {
					if(!trollIcons.get(troll.type()).equals(selTrollType)){
						trollIcons.get(troll.type()).setStyles(
								selOn);
					}
					updateTrollInfo(troll.type());
					if (showUnitMoment)
					{
						updateMomentLabel((int) unitMoment(troll));
					}
				}

				@Override
				public void onMouseOut(MotionEvent event) {
					if(!trollIcons.get(troll.type()).equals(selTrollType)){

						trollIcons.get(troll.type()).setStyles(
								selOff);
					}
					updateMomentLabel(moment);
				}
			});
			updateCost(troll.cost());
			updateTrollInfo(troll.type());
			playSound("sound_unitDeployed");
		}
	}

	public void deployGoat(final GroupLayer squareLayer, int segment, char tileSymbol, int lane, Button tile, int distance, Image image){

		Label goatTile = new Label().addStyles(Style.BACKGROUND
				.is(Background.blank())).setConstraint(Constraints.fixedWidth(SQUARE_WIDTH));
		if ((segment + 1) % 5 != 0) {
			goatTile.icon.update(getIcon("segment"));
		} else
			goatTile.icon.update(getIcon("gap"));

		middleDrawingPanel.add(AbsoluteLayout.at(goatTile, midPanelX, midPanelY));

		// Add the unit layer.
		final Goat goat = newGoat(tileSymbol);
		goat.setParent(middleDrawingPanel);
		goatTypes.add(String.valueOf(tileSymbol));
		goat.setLayer(tile);
		Square square = new Square(lane, segment);
		square.setDistance(distance);
		if (distance == 1)
			goat.setState(State.PUSHING);
		else
			goat.setState(State.MOVING);

		midPanelY += SQUARE_HEIGHT;
		square.setX(midPanelX);
		square.setY(midPanelY);
		goat.setSquare(square);
		unitsLocations.put(square, goat);
		System.out.println(tileSymbol);
		System.out.println(getTileImages(
				tileSymbol).get(1));
		Animation moveAnimation = new Animation(getTileImages(
				tileSymbol).get(1), 16, goat.frameTime());
		goat.setMoveAnimation(moveAnimation);

		Animation pushAnimation = new Animation(getImage("animations/goats_animations/push/" +goat.type() + "_goat_push"), 23, goat.frameTime());
		goat.setPushAnimation(pushAnimation);

		goat.setDefaultImage(image);
		squareLayer.setOrigin(0, image.height());
		squareLayer.setDepth(1);
		squareLayer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseOver(MotionEvent event) {
				if (preSelGoat != null)
					preSelGoat.setStyles(selOff);
				preSelGoat = goatIcons.get(goat.type()).setStyles(
						selOn);
				if (showUnitMoment)
				{
					updateMomentLabel((int) unitMoment(goat));
				}
			}

			@Override
			public void onMouseOut(MotionEvent event) {
				updateMomentLabel(moment);
			}
		});

	}

	public void createGateTile(int lane, GroupLayer squareLayer){
		squareLayer.setDepth(1);
		if (lane == 1 && !hasPivot) {
			pivotLocation = 0;
			ImageLayer pivotLayer = graphics().createImageLayer(
					getImage("pivot"));
			Label pivot = new Label(getIcon("pivot"));
			pivotLayer.setDepth(2);
			middleDrawingPanel.add(AbsoluteLayout.at(pivot, midPanelX, midPanelY + SQUARE_HEIGHT));
		}

	}

	////////////////////////////////////////////////////////////////////
	// CREATING BOTTOM UI PANEL									    ////
	////////////////////////////////////////////////////////////////////

	private void createBottomPanel(){
		//declare the whole bottom panel as two panels in an absolute layout
		bottomPanel = new Group(new AbsoluteLayout());

		//declare bottom pannel as a table with two columns
		bottomInfoPanel = new Group(new TableLayout(TableLayout.COL.alignLeft().fixed(),
				TableLayout.COL.stretch(),
				TableLayout.COL.alignRight().fixed()));

		//declare the bridge bacgrkound
		bottomBridgePanel = new Group(new AxisLayout.Vertical());
		bottomBridgePanel.add(AbsoluteLayout.at(new Label(getIcon(WALL)),0,BOTTOMBRIDGEOFFSET));

		//bottomBridgePanel.addStyles(Style.BACKGROUND.is(Background.image(getImage(WALL))));
		//add elements to first table column - trolls side
		trollGroup = new Group(AxisLayout.vertical());
		bottomTrollPanel = new Group(AxisLayout.horizontal());
		createTrollsInfoPanel();
		bottomTrollPanel.add(new Shim(40,0));
		trollGroup.add(bottomTrollPanel).addStyles(Style.HALIGN.left);

		//Create the cost label
		Group costBoard = createCostBoard();	
		trollGroup.add(costBoard);
		bottomInfoPanel.add(trollGroup);

		//add elements to middle column (the buttons)
		createBottomButtonPanel();

		//add elements to third table column - goats side
		goatGroup = new Group(AxisLayout.vertical());
		bottomGoatPanel = new Group(AxisLayout.horizontal());
		createGoatsInfoPanel(goatTypes);
		goatGroup.add(bottomGoatPanel);
		goatGroup.add(new Shim(0,50));
		bottomInfoPanel.add(goatGroup);

		//Add both the bridge and the info to the 
		bottomPanel.add(AbsoluteLayout.at(bottomBridgePanel,0,0));
		bottomPanel.add(AbsoluteLayout.at(bottomInfoPanel,0,20));
		//bottomPanel.setConstraint(new Constraint())
	}

	private Group createCostBoard(){
		Group costBoard = new Group(new AbsoluteLayout());
		Icon costBoardIcon = getIcon(COSTBOARD);
		costBoard.add(AbsoluteLayout.at(new Label(costBoardIcon),LEFTMARGIN+4,-10));
		costLabel = new Label();
		costLabel.setStyles(Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.BOLD, 12)),
				Style.HALIGN.left, 
				Style.COLOR.is(0xFFFFFFFF));
		costLabel.text.update("$0");
		costBoard.add(AbsoluteLayout.at(costLabel,LEFTMARGIN+65,-3));
		return costBoard;
	}

	private void createTrollsInfoPanel() {
		// Creates troll information panel.
		final Json.Object trolls = json.getObject("trolls");

		//Add the right spacing
		bottomTrollPanel.add(new Shim(LEFTMARGIN,0));

		//Add the troll icons
		for (final String type : trolls.keys()) {
			Troll troll = newTroll(type);
			trollCounts.put(type, (int) trolls.getNumber(type));
			createTrollPanel(troll,trolls);
		}

		Group scrollGroup = new Group(new AxisLayout.Horizontal());

		if(this.trollHeads.size()>4){
			//Add the first thing
			Button backButton = this.createButton(BACKUNIT);
			backButton.clicked().connect(new UnitSlot(){
				@Override
				public void onEmit() {
					trollIndex = (trollIndex-1);
					if(trollIndex<0){
						trollIndex = (trollHeads.size()-1);
					}
					updateTrollScroll();
				}
			});
			scrollGroup.add(backButton);
		} else {
			scrollGroup.add(new Label(getIcon(BACKUNIT+UNITSLOCKED)));
		}


		//Add the mid board
		this.trollScroll = new Group(new AxisLayout.Horizontal());
		this.trollScroll.setConstraint(Constraints.fixedWidth(410));
		scrollGroup.add(this.trollScroll);
		updateTrollScroll();

		//Add the next button
		if(this.trollHeads.size()>4){
			Button next = createButton(NEXTUNIT);
			next.clicked().connect(new UnitSlot(){
				@Override
				public void onEmit() {
					trollIndex = (trollIndex+1);
					if(trollIndex>=trollHeads.size()){
						trollIndex = 0;
					}
					updateTrollScroll();
				}
			});
			scrollGroup.add(next);
		} else {
			scrollGroup.add(new Label(getIcon(NEXTUNIT+UNITSLOCKED)));
		}

		bottomTrollPanel.add(scrollGroup);

	}

	private void updateTrollScroll(){
		this.trollScroll.removeAll();
		for(int i=0; i<4; i++){
			this.trollScroll.add(this.trollHeads.get((this.trollIndex + i)%this.trollHeads.size()));
		}
	}

	private Group createTrollPanel(final Troll troll, Json.Object trolls){

		Group goatGroup = new Group(new AbsoluteLayout());

		final Button trollIcon = new Button(getIcon(UNSELECTEDIMAGE)).addStyles(Style.HALIGN.center);
		trollIcon.setStyles(selOff);
		trollIcon.setConstraint(Constraints.fixedSize(68,68));

		System.out.println(HEADPATH + troll.type() + "_troll");
		Label icon = new Label(getIcon(HEADPATH + troll.type() + "_troll"));

		trollIcons.put(troll.type(), trollIcon);
		trollIcon.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseOver(MotionEvent event) {
				if (!trollIcon.equals(selTrollIcon)){
					trollIcon.icon.update(getIcon(HIGHLIGHTEDIMAGE));
				}

			}

			@Override
			public void onMouseOut(MotionEvent event) {
				if (!trollIcon.equals(selTrollIcon))
					trollIcon.icon.update(getIcon(UNSELECTEDIMAGE));
			}

			@Override
			public void onMouseDown(ButtonEvent event) {
				selTrollIcon.icon.update(getIcon(UNSELECTEDIMAGE));
				selTrollIcon = trollIcon;
				trollIcon.icon.update(getIcon(SELECTEDIMAGE));
				selTrollType = troll.type();
			}
		});

		//Add the imagery
		goatGroup.add(AbsoluteLayout.at(trollIcon, 10,10));
		goatGroup.add(AbsoluteLayout.at(new Label(getIcon(STRENGTHICON)),0.0f,88-(getIcon(STRENGTHICON).height())));
		goatGroup.add(AbsoluteLayout.at(new Label(getIcon(SPEEDICON)),88-(getImage(SPEEDICON).width()),88-(getIcon(STRENGTHICON).height())));
		System.out.println(troll.type());
		goatGroup.add(AbsoluteLayout.at(icon, 44-(getIcon(HEADPATH + troll.type() + "_troll").width()/2), 44-(getIcon(HEADPATH + troll.type() + "_troll").height()/2)));

		//Add stuff for the adding
		Label speed = new Label(String.valueOf((int)troll.speed())).setStyles(Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.BOLD, 10)),
				Style.HALIGN.left, 
				Style.COLOR.is(0xFFFFFFFF),
				Style.TEXT_EFFECT.shadow,
				Style.TEXT_EFFECT.SHADOW.is(0xFF412C2C));
		Label strength = new Label(Integer.toString((int)troll.speed())).setStyles(Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.BOLD, 10)),
				Style.HALIGN.left, 
				Style.COLOR.is(0xFFFFFFFF),
				Style.TEXT_EFFECT.shadow,
				Style.TEXT_EFFECT.SHADOW.is(0xFF412C2C));
		Label name = new Label(troll.type().toUpperCase() + " TROLL").setStyles(Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.BOLD, 12)),
				Style.HALIGN.left, 
				Style.COLOR.is(0xFFFFFFFF),
				Style.TEXT_EFFECT.shadow,
				Style.TEXT_EFFECT.SHADOW.is(0xFF412C2C));
		Label count = new Label("x" + (int) this.trollCounts.get(troll.type())).setStyles(Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.BOLD, 16)),
				Style.HALIGN.left, 
				Style.COLOR.is(0xFF77FF00),
				Style.TEXT_EFFECT.shadow,
				Style.TEXT_EFFECT.SHADOW.is(0xFF412C2C));
		Label cost = new Label("$" + (int)troll.cost()).setStyles(Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.BOLD, 16)),
				Style.HALIGN.left, 
				Style.COLOR.is(0xFFFFCC00),
				Style.TEXT_EFFECT.shadow,
				Style.TEXT_EFFECT.SHADOW.is(0xFF412C2C));


		//Position the labels
		goatGroup.add(AbsoluteLayout.at(name, 0,-10));
		goatGroup.add(AbsoluteLayout.at(count, 56,5));
		goatGroup.add(AbsoluteLayout.at(cost, 6, 5));

		goatGroup.add(AbsoluteLayout.at(strength, 20,64));
		goatGroup.add(AbsoluteLayout.at(speed, 72,64));

		//Set the count label
		this.setTrollCountLabel(troll.type(), count);

		//Preselect the first one
		if(this.trollCounts.size()==1){
			this.selTrollIcon = trollIcon;
		}

		this.trollHeads.add(goatGroup);
		return goatGroup;
	}

	private void createBottomButtonPanel() {
		final Button start = createButton(STARTBUTTON);
		bottomButtonPanel = new Group(AxisLayout.vertical());
		Group buttons = new Group(AxisLayout.horizontal());
		buttons.add(start);
		start.clicked().connect(new UnitSlot() {
			@Override
			public void onEmit() {
				if (messageShown)
					return;
				if (!started && !paused) {
					proceed();
				} else {
					started = false;
					pause(false);
					restart();
				}
			}
		});


		final Button reset = createButton(RESETBUTTON);

		buttons.add(reset);
		reset.clicked().connect(new UnitSlot() {
			@Override
			public void onEmit(){
				if(messageShown){
					return;
				} else if(!started && !paused) {
					resetBoard();
				}
			}
		});
		bottomButtonPanel.add(buttons);
		horizTitle.add(bottomButtonPanel);

	}

	private void createGoatsInfoPanel(Set<String> types) {
		
		for (String symbol : types) {
			final Goat goat = newGoat(symbol.charAt(0));
			createGoatPanel(goat);
		}
		
		Group scrollGroup = new Group(new AxisLayout.Horizontal());

		if(this.goatHeads.size()>4){
			//Add the first thing
			Button backButton = this.createButton(BACKUNIT);
			backButton.clicked().connect(new UnitSlot(){
				@Override
				public void onEmit() {
					goatIndex = (goatIndex-1);
					if(goatIndex<0){
						goatIndex = (goatHeads.size()-1);
					}
					updateGoatScroll();
				}
			});
			scrollGroup.add(backButton);
		} else {
			scrollGroup.add(new Label(getIcon(BACKUNIT+UNITSLOCKED)));
		}


		//Add the mid board
		this.goatScroll = new Group(new AxisLayout.Horizontal());
		this.goatScroll.setConstraint(Constraints.fixedWidth(410));
		scrollGroup.add(this.goatScroll);

		updateGoatScroll();

		//Add the next button
		if(this.goatHeads.size()>4){
			Button next = createButton(NEXTUNIT);
			scrollGroup.add(next);
		} else {
			scrollGroup.add(new Label(getIcon(NEXTUNIT+UNITSLOCKED)));
		}

		bottomTrollPanel.add(scrollGroup);

		//Finally add the shim
		bottomGoatPanel.add(new Shim(RIGHTMARGIN,0));
	}

	private void updateGoatScroll(){
		this.goatScroll.removeAll();
		for(int i=0; i<4; i++){
			this.goatScroll.add(this.goatHeads.get((this.goatIndex + i)%this.goatHeads.size()));
		}
	}

	private Group createGoatPanel(final Goat goat){

		Group goatGroup = new Group(new AbsoluteLayout());

		final Button goatIcon = new Button(getIcon(UNSELECTEDIMAGE)).addStyles(Style.HALIGN.center);
		goatIcon.setStyles(selOff);
		goatIcon.setConstraint(Constraints.fixedSize(68,68));

		Label icon = new Label(getIcon(HEADPATH + goat.type() + "_goat"));

		goatIcons.put(goat.type(), goatIcon);
		goatIcon.layer.addListener(new Mouse.LayerAdapter() {
			@Override
			public void onMouseOver(MotionEvent event) {
				if (preSelGoat != null)
					preSelGoat.icon.update(getIcon(UNSELECTEDIMAGE));
				preSelGoat = goatIcon;
				goatIcon.icon.update(getIcon(SELECTEDIMAGE));
			}

		});

		//Add the imagery
		goatGroup.add(AbsoluteLayout.at(goatIcon, 10,10));
		goatGroup.add(AbsoluteLayout.at(new Label(getIcon(STRENGTHICON)),0.0f,88-(getIcon(STRENGTHICON).height())));
		goatGroup.add(AbsoluteLayout.at(new Label(getIcon(SPEEDICON)),88-(getImage(SPEEDICON).width()),88-(getIcon(STRENGTHICON).height())));
		goatGroup.add(AbsoluteLayout.at(icon, 44-(getIcon(HEADPATH + goat.type() + "_goat").width()/2), 44-(getIcon(HEADPATH + goat.type() + "_goat").height()/2)));

		//Add stuff for the adding
		Label speed = new Label(String.valueOf((int)goat.speed())).setStyles(Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.BOLD, 10)),
				Style.HALIGN.left, 
				Style.COLOR.is(0xFFFFFFFF),
				Style.TEXT_EFFECT.shadow,
				Style.TEXT_EFFECT.SHADOW.is(0xFF412C2C));
		Label strength = new Label(String.valueOf((int)goat.speed())).setStyles(Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.BOLD, 10)),
				Style.HALIGN.left, 
				Style.COLOR.is(0xFFFFFFFF),
				Style.TEXT_EFFECT.shadow,
				Style.TEXT_EFFECT.SHADOW.is(0xFF412C2C));
		Label name = new Label(goat.type().toUpperCase() + " GOAT").setStyles(Style.FONT.is(PlayN.graphics().createFont("komika_title", Font.Style.BOLD, 12)),
				Style.HALIGN.left, 
				Style.COLOR.is(0xFFFFFFFF),
				Style.TEXT_EFFECT.shadow,
				Style.TEXT_EFFECT.SHADOW.is(0xFF412C2C));

		//Position the labels
		goatGroup.add(AbsoluteLayout.at(name, 0,-10));
		goatGroup.add(AbsoluteLayout.at(strength, 20,64));
		goatGroup.add(AbsoluteLayout.at(speed, 72,64));
		this.goatHeads.add(goatGroup);
		return goatGroup;
	}

	////////////////////////////////////////////////////////////////////
	// GAME CONTROL METHODS 									    ////
	////////////////////////////////////////////////////////////////////

	private void pause(boolean paused) {
		this.paused = paused;
	}

	public void restart() {
		started = false;
		paused = false;
		messageShown = false;
		score = 0;
		moment = 0;
		headTrolls.clear();
		headGoats.clear();
		model.levelRestart();

		// Reset units.
		Unit unit = null;
		for (Square square : unitsLocations.keySet()) {
			unit = unitsLocations.get(square);
			unit.setSquare(square);
			if (square.distance() == 1 && unit.speed() != 0)
				unit.setState(State.PUSHING);
			else
				unit.setState(State.MOVING);
			unit.update(0);
			unit.reset();
		}
	}

	public void resetBoard(){
		started = false;
		paused = false;
		messageShown = false;
		score = 0;
		moment = 0;
		headTrolls.clear();
		headGoats.clear();
		model.levelRestart();

		// Reset units.
		Unit unit = null;
		for (Square square : unitsLocations.keySet()) {
			unit = unitsLocations.get(square);
			//Check if not goat and remove.
			if(unit instanceof Troll){
				this.trollCounts.put(unit.type(), this.trollCounts.get(unit.type())+1);
				removeUnit(unit);
				this.updateTrollInfo(unit.type());
			}
		}

		this.cost=0;
		this.updateCost(0);
	}

	private void proceed() {
		if (paused) {
			paused = false;
			return;
		}
		started = true;
		creatUnitLinklists();
	}

	////////////////////////////////////////////////////////////////////
	// UPDATE METHODS 												////
	////////////////////////////////////////////////////////////////////

	@Override
	public void update(int delta) {
		super.update(delta);

		//Check that we have actually started 
		if (!started || paused)
			return;

		//Initiate function variables
		hasMovingUnit = false;
		float trollsMoments = 0;
		float goatsMoments = 0;

		//Calculate all the goat moments
		for (Unit goat : headGoats.values()) {
			goatsMoments += updateUnits(goat, delta);
		}

		//Calculate the toll moments
		for (Unit troll : headTrolls.values()) {
			troll.setOldX(troll.square().getX());
			trollsMoments += updateUnits(troll, delta);
		}

		//Check who is winning and if not
		if (trollsMoments != goatsMoments){
			model.setMomentOverZero();
		}

		this.moment = (int) (trollsMoments - goatsMoments);
		updateMomentLabel((int) (trollsMoments - goatsMoments));

		if (model.levelIndex() == 0)
			return;

		// To check whether the game should end.
		if (!hasMovingUnit
				|| Math.abs(trollsMoments - goatsMoments) > MAX_MOMENT) {
			paused = true;
			messageShown = true;

			if (trollsMoments - goatsMoments != 0) {
				showLevelFailed();
			} else {
				showLevelComplete();
			}
		}
	}

	private void updateMomentLabel(int moment)
	{
		momentLabel.text.update(String.valueOf(moment)+" N/m");
	}

	/**
	 * Moves all the units of a lane forward if it's possible, handles collision
	 * with the front unit. Returns the overall moments generated by this lane
	 * of units.
	 */
	private float updateUnits(Unit unit, float delta) {
		float moments = 0;
		while (unit != null) {
			boolean pushing = false;

			if (!unit.state().equals(State.PUSHING)
					&& !unit.state().equals(State.BLOCKED) && unit.speed() != 0) {
				hasMovingUnit = true;

				if (adjacent(unit, unit.front())) {
					State state = unit.front().state();
					if (state.equals(State.PUSHING))
						pushing = true;
					else if (state.equals(State.BLOCKED))
						unit.setState(State.BLOCKED);
				}

				// If a unit can move.
				if (unit.updatePosition(delta)
						&& !unit.state().equals(State.BLOCKED)) {
					// Handles collision.
					if (adjacent(unit, unit.front())) {
						unit.notifyColliedWithFront();
						if (unit.front() != null)
							unit.front().notifyColliedWithBack();
						// Jumping goat becomes the head of a lane.
						else if (unit instanceof JumpingGoat)
							headGoats.put(unit.square().lane(), unit);

						if (unit instanceof FastTroll) {
							removeUnit(unit.front());
							removeUnit(unit);
						}
					}

					if (!adjacent(unit, unit.front()) || unit.speed() == unit.front().speed()
							|| unit.state().equals(State.JUMPING)) {
						Square s1 = unit.square();
						// Initialises the front square.
						int moveDistance = unit.state().equals(State.JUMPING) ? 2 : 1;
						Square s2 = new Square(s1.lane(),s1.segment() < bridgeLocation ? s1.segment() + moveDistance : s1.segment() - moveDistance);

						s2.setX(segmentToX(s2.segment(),unit.widget().icon.get())+6);
						s2.setY(s1.getY());

						s2.setDistance(s1.distance() - moveDistance);

						unit.move(s2);

						unit.setState(State.MOVING);
						if (s2.distance() == 1 || adjacent(unit, unit.front())
								&& unit.front().state().equals(State.PUSHING))
							pushing = true;
					}

				}
			} else if (unit.speed() != 0)
				pushing = true;

			if (pushing) {
				// Handles HungryTroll.
				if (unit.square().distance() == 1
						&& unit instanceof HungryTroll
						&& !((HungryTroll) unit).hasEaten()) {
					Unit head = headGoats.get(unit.square().lane());
					if (head != null && head.square().distance() == 1
							&& head instanceof Goat) {
						removeUnit(headGoats.get(unit.square().lane()));
						((HungryTroll) unit).setEaten();
						model.setGoatEaten();
						moments += unitMoment(head);
					}
				}
				if (unit.square().distance() <= PUSHING_DISTANCE) {
					unit.setState(State.PUSHING);
					moments += unitMoment(unit);
				} else
					unit.setState(State.BLOCKED);
			}

			unit.update(delta);
			unit = unit.back();
		}

		return moments;
	}

	private void updateTrollInfo(String type) {
		Label countLabel = getTrollCountLabel(type);
		countLabel.text.update(String.valueOf("x" + this.trollCounts.get(type)));
	}

	private void updateCost(float cost) {
		this.cost += cost;
		costLabel.text.update("$" + this.cost);
	}


	////////////////////////////////////////////////////////////////////
	// CREATION AND REMOVAL METHODS 							    ////
	////////////////////////////////////////////////////////////////////

	private Goat newGoat(char type) {
		Goat goat = null;
		switch (type) {
		case 'g':
			goat = new LittleGoat();
			break;
		case 'G':
			goat = new NormalGoat();
			break;
		case 'B':
			goat = new BigGoat();
			break;
		case 'F':
			goat = new FastGoat();
			break;
		case 'T':
			goat = new ButtingGoat();
			break;
		case 'J':
			goat = new JumpingGoat();
			break;
		default:
			goat = null;
		}
		goat.setMovementTime(model.movementTime());
		return goat;
	}

	private Troll newTroll(String type) {
		Troll troll = null;
		if (type.equals("normal"))
			troll = new NormalTroll();
		else if (type.equals("little"))
			troll = new LittleTroll();
		else if (type.equals("fast"))
			troll = new FastTroll();
		else if (type.equals("digging"))
			troll = new DiggingTroll();
		else if (type.equals("barrow"))
			troll = new BarrowTroll();
		else if (type.equals("cheerLeader"))
			troll = new CheerleaderTroll();
		else if (type.equals("hungry"))
			troll = new HungryTroll();
		else if (type.equals("mega"))
			troll = new MegaTroll();
		else if (type.equals("spitting"))
			troll = new SpittingTroll();
		if(troll!=null){
			troll.setMovementTime(model.movementTime());
		}
		return troll;
	}

	private void removeUnit(Unit unit) {
		if (unit == null)
			return;
		if (unit.front() != null)
			unit.front().removeBack();
		else {
			if (unit.back() != null) {
				unit.back().removeFront();
				if (unit instanceof Troll)
					headTrolls.put(unit.square().lane(), unit.back());
				else
					headGoats.put(unit.square().lane(), unit.back());
			} else {
				if (unit instanceof Troll)
					headTrolls.remove(unit.square().lane());
				else {
					headGoats.remove(unit.square().lane());
				}
			}
		}
		unit.widget().layer.setVisible(false);
	}

	private void creatUnitLinklists() {
		List<Square> squares = new ArrayList<Square>(unitsLocations.keySet());
		// Sorts the squares.
		Collections.sort(squares, new Comparator<Square>() {
			@Override
			public int compare(Square s1, Square s2) {
				if (s1.lane() > s2.lane())
					return 1;
				else if (s1.lane() == s2.lane()) {
					if (s1.distance() > s2.distance())
						return 1;
				}
				return -1;
			}
		});

		// Constructs the unit linked lists.
		Map<Integer, Unit> headUnits = null;
		Unit unit;
		for (Square square : squares) {
			unit = unitsLocations.get(square);
			if (unit instanceof Goat || unit instanceof Troll
					&& !((Troll) unit).isOnTrollSide()) {
				headUnits = headGoats;
			} else if (unitsLocations.get(square) instanceof Troll) {
				headUnits = headTrolls;
			} else
				continue;
			if (headUnits.get(square.lane()) != null) {
				tailUnit(headUnits.get(square.lane())).setBack(
						unitsLocations.get(square));
			} else
				headUnits.put(square.lane(), unitsLocations.get(square));
		}
	}

	////////////////////////////////////////////////////////////////////
	// CALCULATION METHODS 										    ////
	////////////////////////////////////////////////////////////////////

	private float unitMoment(Unit unit) {
		if (unit.square().lane() > pivotLocation)
			return (unit.square().lane() - pivotLocation) * unit.force();
		else if (unit.square().lane() < pivotLocation)
			return -(pivotLocation - unit.square().lane()) * unit.force();
		else
			return 0;
	}

	////////////////////////////////////////////////////////////////////
	// UTILITY METHODS 					       					    ////
	////////////////////////////////////////////////////////////////////

	private float segmentToX(float segment, Icon icon) {
		//We want our image centered, so we have to take away a third of the icon so it sits on the tile
		if(segment>this.bridgeLocation){
			//Do stuff here to fix things
		}
		float tiledist = SQUARE_WIDTH * segment;
		float tilespacing = INITIALEDGE + segment*TILEGAP;
		return tiledist+tilespacing - (90/3);
	}

	private float laneToY(float lane, Image img) {
		//Start with square_height to push down, then calculat the negative offset from the height
		float initOffset = 44;
		float tiledist = (SQUARE_HEIGHT * (laneCount - lane));// - (img.height());
		float tileSpacing = (laneCount-lane)*TILEGAP;
		return initOffset + tiledist + tileSpacing;
	}

	@Override
	protected String title() {

		return null;
	}

	// Returns a list of symbol string representing trolls deployment.
	private List<String> trollsDeployment() {
		Unit unit = null;
		for (Square square : unitsLocations.keySet()) {
			unit = unitsLocations.get(square);
			unit.setSquare(square);
			unit.reset();
		}
		headTrolls.clear();
		headGoats.clear();
		creatUnitLinklists();
		ArrayList<String> lanes = new ArrayList<String>();
		for (int l = laneCount; l > 0; l--) {
			String laneString = "";
			Unit troll = headTrolls.get(l);
			for (int i = bridgeLocation - 1; i >= 0; i--) {
				if (troll != null && troll.square().segment() == i) {
					laneString = troll.type().substring(0, 1) + laneString;
					troll = troll.back();
				} else {
					if ((bridgeLocation - 1 - i) % 5 == 0)
						laneString = " " + laneString;
					else
						laneString = "-" + laneString;
				}
			}
			lanes.add(laneString);
		}
		return lanes;
	}

	private boolean adjacent(Unit u1, Unit u2) {
		if (u1 == null || u2 == null)
			return false;
		return Math.abs(u1.square().distance() - u2.square().distance()) == 1;
	}

	private Label getTrollCountLabel(String type){
		if(type.equals("normal")){
			return this.normalTrollLabel;
		} else if(type.equals("fast")){
			return this.fastTrollLabel;
		} else if(type.equals("little")){
			return this.littleTrollLabel;
		} else if(type.equals("hungry")){
			return this.hungryTrollLabel;
		} else if(type.equals("cheerleader")){
			return this.cheerLeaderTrollLabel;
		} else if(type.equals("mega")){
			return this.megaTrollLabel;
		} else if(type.equals("digging")){
			return this.diggingTrollLabel;
		} else if(type.equals("spitting")){
				return this.spittingTrollLabel;
		}
			
		return new Label();
	}

	private void setTrollCountLabel(String type, Label label){
		if(type.equals("normal")){
			this.normalTrollLabel = label;
		} else if(type.equals("fast")){
			this.fastTrollLabel = label;
		}else if(type.equals("little")){
			this.littleTrollLabel = label;
		} else if(type.equals("hungry")){
			this.hungryTrollLabel = label;
		} else if(type.equals("cheerleader")){
			this.cheerLeaderTrollLabel = label;
		} else if(type.equals("mega")){
			this.megaTrollLabel = label;
		} else if(type.equals("digging")){
			this.diggingTrollLabel = label;
		} else if(type.equals("spitting")){
			this.spittingTrollLabel = label;
		}

	}

	private List<Image> getTileImages(char symbol) {
		List<String> names = new ArrayList<String>();
		List<Image> images = new ArrayList<Image>();
		switch (symbol) {
		case '.':
			names.add("cut_screens/gameplay/segment");
			break;
		case ' ':
			names.add("cut_screens/gameplay/gap");
			break;
		case '|':
			names.add("cut_screens/gameplay/gate");
			break;
		case 'o':
			names.add("cut_screens/gameplay/pivot");
			break;
		case 'g':// Little goat.
		case 'G':// Normal goat.
		case 'B':// Big goat.
		case 'F':// Fast goat.
		case 'T':// Butting goat.
			names.add("animations/goats_animations/normal/"+newGoat(symbol).type()+"_goat_normal");
			names.add("animations/goats_animations/walk/"+newGoat(symbol).type()+"_goat_walk");
			break;
		default:
			log().error("Unsupported square type character '" + symbol + "'");
			return null;
		}

		for (String name : names) {
			images.add(getImage(name));
		}
		return images;
	}

	private List<Icon> getTileIcon(char symbol) {
		List<String> names = new ArrayList<String>();
		List<Icon> images = new ArrayList<Icon>();
		switch (symbol) {
		case '.':
			names.add("cut_screens/gameplay/segment");
			break;
		case ' ':
			names.add("cut_screens/gameplay/gap");
			break;
		case '|':
			names.add("cut_screens/gameplay/gate");
			break;
		case 'o':
			names.add("cut_screens/gameplay/pivot");
			break;
		case 'g':// Little goat.
		case 'G':// Normal goat.
		case 'B':// Big goat.
		case 'F':// Fast goat.
		case 'T':// Butting goat.
		case 'J':// Jumping goat.
			names.add("animations/goats_animations/normal/"+newGoat(symbol).type()+"_goat_normal");
			names.add("animations/goats_animations/walk/"+newGoat(symbol).type()+"_goat_walk");
			break;
		default:
			log().error("Unsupported square type character '" + symbol + "'");
			return null;
		}

		for (String name : names) {
			images.add(getIcon(name));
		}
		return images;
	}

	private Unit tailUnit(Unit unit) {
		Unit next = unit;
		while (next.back() != null) {
			next = next.back();
		}
		return next;
	}

	////////////////////////////////////////////////////////////////////
	// ACCESSING DATA METHODS 					       			    ////
	////////////////////////////////////////////////////////////////////

	@Override
	public String[] images() {
		//Gameplay tiles
		String[] tiles = new String[] { "cut_screens/gameplay/segment", "cut_screens/gameplay/gap", "cut_screens/gameplay/gate", "cut_screens/gameplay/pivot" };

		//Types of trolls and goats
		String[] normalTrolls = new String[] { "normal", "little", "fast","cheerleader", "hungry", "mega", "spitting", "digging"};
		String[] goats = new String[] { "little", "normal", "big", "fast","butting" };
		//Create new array of names
		List<String> names = new ArrayList<String>(Arrays.asList(tiles));

		//Add menu heads
		for (String name: normalTrolls) {
			names.add(HEADPATH + name + "_troll");
		} for (String name :goats){
			names.add(HEADPATH + name + "_goat");
		}

		//Add standard strolls and goats animations
		String[] anim_types = new String[] {"normal", "push", "walk"};
		for(String type: anim_types){
			for(String name: normalTrolls){
				names.add("animations/trolls_animations/"+type+"/"+name+"_troll_"+type);
			} for (String gName: goats){
				names.add("animations/goats_animations/"+type+"/"+gName+"_goat_"+type);
			}
		}



		//The UIBoards
		names.add(MOMENTBOARD);
		names.add(COSTBOARD);

		//The bridge backgrounds
		names.add(BRIDGEBG);
		names.add(BIGBRIDGEBG);
		names.add(SMALLWALL);
		names.add(BIGWALL);
		names.add(GAMEBG);

		//The gates
		names.add(SMALLGATE);
		names.add(BIGGATE);
		names.add(LATCH);
		names.add(BOTTOMLATCH);

		//The bridge backgrounds
		names.add(LRBRIDGEBG);
		names.add(LRBIGBRIDGEBG);
		names.add(LRSMALLWALL);
		names.add(LRBIGWALL);
		names.add(LRGAMEBG);

		//The gates
		names.add(LRSMALLGATE);
		names.add(LRBIGGATE);
		names.add(LRLATCH);
		names.add(LRBOTTOMLATCH);

		//The Buttons
		String[] buttons = new String[]{"_inactive", "_active", "_select"};
		for(String btype: buttons){
			names.add(RESETBUTTON+btype);
			names.add(STARTBUTTON+btype);
			names.add(NEXTUNIT+btype);
			names.add(BACKUNIT+btype);
		}

		names.add(BACKUNIT+UNITSLOCKED);
		names.add(NEXTUNIT+UNITSLOCKED);


		names.add(SELECTEDIMAGE);
		names.add(HIGHLIGHTEDIMAGE);
		names.add(UNSELECTEDIMAGE);

		//Initialise the hashmap
		walkAnims.put("normal", 16);
		walkAnims.put("little", 16);
		walkAnims.put("fast", 9);
		walkAnims.put("cheerleader", 16);
		walkAnims.put("hungry", 1);
		walkAnims.put("mega", 16);
		walkAnims.put("spitting", 1);
		walkAnims.put("digging", 1);


		//Add the label icons for the bottom
		names.add(STRENGTHICON);
		names.add(SPEEDICON);

		return names.toArray(new String[names.size()]);

	}

	@Override
	public String[] sounds() {
		List<String> sounds = new ArrayList<String>();
		sounds.add( "sound_unitDeployed");
		return sounds.toArray(new String[sounds.size()]);
	}

	////////////////////////////////////////////////////////////////////
	// SHOWING GUI METHODS 					       				    ////
	////////////////////////////////////////////////////////////////////

	private void showLevelComplete(){
		Json.Object scores = json.getObject("scores");
		for (String cost : scores.keys()) {
			if (this.cost <= Integer.valueOf(cost)) {
				if (score < scores.getInt(cost))
					score = scores.getInt(cost);
			}
		}
		game.levelCompleted(score);
		// Log trolls deployment when complete the level.
		game.logTrollsDeployment(trollsDeployment());
		game.showWinnerScreen();
	}

	private void showLevelFailed(){
		momentLabel.addStyles(Style.COLOR.is(12));
		game.showLoserScreen();
	}

	private Button createButton(final String btnName) {
		Icon icon = getIcon(btnName + "_inactive");
		final Button btn = new Button(icon).setStyles(Style.BACKGROUND.is(Background.blank()));

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