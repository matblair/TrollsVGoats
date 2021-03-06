package au.edu.unimelb.csse.trollsvsgoats.core;

import static playn.core.PlayN.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import playn.core.*;
import playn.core.Json.Object;
import playn.core.util.Callback;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Icon;
import tripleplay.ui.Icons;
import au.edu.unimelb.csse.trollsvsgoats.core.handlers.IPlatformHandler;
import au.edu.unimelb.csse.trollsvsgoats.core.model.*;
import au.edu.unimelb.csse.trollsvsgoats.core.view.*;

/**
 * Main game interface which updates the game states and views.
 */
public class TrollsVsGoatsGame extends Game.Default implements Game {

	private final View[] screens;
	private PersistenceClient persistence;
	private GameModel model;
	private String userName;

    private Map<String, Image> images = new HashMap<String, Image>();
    private Map<String, Sound> sounds = new HashMap<String, Sound>();
    private Map<String, Icon>  icons = new HashMap<String, Icon>();
    private Map<String, String> text = new HashMap<String, String>();

	// Views
	private MainScreenEx mainScreen;
	private LoadingScreen loadScreen;
	private ThemeSelScreen themeSelScreen;
	private LevelSelScreenEx levelSelScreen;
	private MessageBox messageBox;
	private BadgesScreenEx badgesScreen;
	private OptionScreen optionScreen;
	private HelpScreenEx helpScreen;
	private LevelWinScreen winScreen;
	private LevelLoseScreen loseScreen;
	private LevelLoadScreen levelLoadScreen;

	private LevelScreenEx previousLevel;

	//tripleplay 1.7.2
	private static final int UPDATE_RATE = 30; // FPS
	private static final int UPDATE_PERIOD = 1000 / UPDATE_RATE;
	private final Clock.Source _clock = new Clock.Source(UPDATE_RATE);
	protected float _lastTime;
	private IPlatformHandler handler;
	//

	public TrollsVsGoatsGame(PersistenceClient persistence, IPlatformHandler handler) {
		super(UPDATE_PERIOD); // call update every 33ms (30 times per second)
		this.persistence = persistence;
		this.handler = handler;
		handler.setSize(1024, 720);

		this.model = new GameModel();
		screens = new View[] { mainScreen = new MainScreenEx(this),
				loadScreen = new LoadingScreen(this),
				themeSelScreen = new ThemeSelScreen(this),
				levelSelScreen = new LevelSelScreenEx(this),
				new LevelScreenEx(this), badgesScreen = new BadgesScreenEx(this),
				optionScreen = new OptionScreen(this),
				helpScreen = new HelpScreenEx(this),
				winScreen = new LevelWinScreen(null, this, 0),
				loseScreen = new LevelLoseScreen(this),
				levelLoadScreen = new LevelLoadScreen(this, 1, true)};
	}

    @Override
    public void init() {
        userName = "LocalTest@student.unimelb.edu.au";
        populate();
        stack.push(loadScreen);
        loadResources();
    }

	/** Load all the images and sounds. */
	private void loadResources() {
		AssetWatcher asset = new AssetWatcher(new AssetWatcher.Listener() {

			@Override
			public void error(Throwable e) {
				log().error("Error loading asset: " + e.getMessage());
			}

            @Override
            public void done() {
            	//http://code.google.com/p/playn/source/detail?spec=svn02ad17652134fc1a47d647a9fec2e72bd7a2134b&r=2ee14ffb17a4935e3db3079fd1bcab45831d9105
            	if(handler != null)
            	{
            		handler.setSize(model.width, model.height);
            	}
            	
                stack.replace(mainScreen, ScreenStack.NOOP);
            }
        });
        for (View screen : screens) {
            if (screen.images() != null) {
                for (String path : screen.images()) {
                	System.out.println("images/" + path + ".png");
                    Image image = assets().getImage("images/" + path + ".png");
                    asset.add(image);
                    images.put(path, image);
                    
                    Icon icon = Icons.image(PlayN.assets().getImage("images/" + path + ".png"));
                    icons.put(path, icon);
                }
            }
            if (screen.sounds() != null) {
                for (String path : screen.sounds()) {
                    Sound sound = assets().getSound("sounds/" + path);
                    asset.add(sound);
                    sounds.put(path, sound);
                }
            }
        }
        
        // Load help text
        for (final String path : this.helpScreen.helpFiles()) {
			try {
				assets().getText(path + ".txt", new Callback<String>() {
					@Override
					public void onSuccess(String result) {
						text.put(path, result);
					}

					@Override
					public void onFailure(Throwable cause) {
						log().error(cause.getMessage());
						
					}
				});
			} catch (Exception e1) {
				log().error("Error loading asset: " + e1.getMessage());
			}
        }
        // Load level text
        for (int i=1; i<=this.getNumLevels(); i++) {
        	try {
        		final String path = "levelinfo/" + i;
				assets().getText(path + ".txt", new Callback<String>() {
					@Override
					public void onSuccess(String result) {
						text.put(path, result);
					}

					@Override
					public void onFailure(Throwable cause) {
						log().error(cause.getMessage());
						
					}
				});
			} catch (Exception e1) {
				log().error("Error loading asset: " + e1.getMessage());
			}
        }
        
        asset.start();
	}

	public void setScreenSize(int width, int height) {
		if(handler != null)
		{
			handler.setSize(width, height);
		}

		persistence.persist(model);
	}

	/** Grab data from persistence source */
	public void populate() {
		persistence.getUserName(new PersistenceClient.Callback<String>() {

			@Override
			public void onSuccess(String result) {
				if (result != null)
					userName = result;
			}

			@Override
			public void onFailure(Throwable caught) {
				log().error(caught.getMessage());
			}
		});
		persistence.populate(model);
	}

	/** Save game data to persistence source */
	public void persist() {
		persistence.persist(model);
	}

	public void logTrollsDeployment(List<String> lanes) {
		persistence.logTrollsDeployment(model.levelIndex(), lanes);
	}

	public void refreshMainScreen() {
		mainScreen.wasAdded();
	}

	public void showThemeSelScreen() {
		stack.push(themeSelScreen);
	}

	public void showWinnerScreen(Json.Object scores, int score) {
		LevelWinScreen newScreen = new LevelWinScreen(scores,this, score);
		stack.replace(newScreen);
	}

	public void showLoserScreen() {
		stack.replace(loseScreen);
	}

	public void showLevelSelScreen(int index) {
		//model.setThemeIndex(index);
		stack.push(levelSelScreen);
	}

	public void showBadgesScreen() {
		stack.push(badgesScreen);
	}

	public void showOptionScreen() {
		stack.push(optionScreen);
	}

	public void showHelpScreen() {
		stack.push(helpScreen);
	}

	public void showMessageBox(View currentScreen, MessageBox messageBox) {
		closeMessageBox();
		this.messageBox = messageBox;
		messageBox.wasAdded();
		currentScreen.layer.addAt(messageBox.layer(), graphics().width() / 2
				- messageBox.width() / 2,
				graphics().height() / 2 - messageBox.height() / 2);
		messageBox.layer().setDepth(3);
	}

	public void closeMessageBox() {
		if (messageBox != null) {
			messageBox.wasRemoved();
			messageBox = null;
		}
	}

	public String userName() {
		return this.userName;
	}

	public GameModel model() {
		return this.model;
	}

	public void loadLevelLoad(int index, boolean replace, final boolean refreshLevel) {
		LevelLoadScreen lls = new LevelLoadScreen(this, index, refreshLevel);
		if (replace)
			stack.replace(lls);
		else
			stack.push(lls);
	}

	public void loadLevel(final int index, final boolean replace, final boolean refreshLevel) {
		String levelPath = "levels/" + model.currentTheme() + "_level_"
				+ String.valueOf(index) + ".txt";
//		if(!refreshLevel){
//			if (replace)
//				stack.replace(this.previousLevel);
//			else
//				stack.push(this.previousLevel);
//		} else {
			final TrollsVsGoatsGame game = this;
			assets().getText(levelPath, new Callback<String>() {
				@Override
				public void onSuccess(String result) {
					game.model().levelStart(index);
					//-> LevelScreen level = new LevelScreen(game, result);
					LevelScreenEx level = new LevelScreenEx(game, result, refreshLevel);
					previousLevel = level;
					if (replace)
						stack.replace(level);
					else
						stack.push(level);
				}

				@Override
				public void onFailure(Throwable cause) {
					log().error(cause.getMessage());

				}
			});
//		}
	}

	public void loadLeaderboard(final int index, final boolean replace) {
		LeaderBoard lb = new LeaderBoard(this, index);
		if (replace)
			stack.replace(lb, ScreenStack.NOOP);
		else
			stack.push(lb);
	}

	public void loadNextLevel(final boolean refreshLevel) {
		loadLevelLoad(model.nextLevelIndex(), true, refreshLevel);
	}
	
	public int getNumLevels() {
		return 7;
	}

	/** Called when completed the current level, persists the level index. */
	public void levelCompleted(int score) {
		model.levelCompleted(score);
		persistence.persist(model);
	}

	public void setBadgeAchieve(Badge badge) {
		persistence.achieveBadge(badge);
	}

	/** How long for a unit to cover a segment. */
	public void setMovementTime(float seconds) {
		model.setMovementTime(seconds);
		persistence.persist(model);
	}

	// TODO Just for cheating
	public void setLevelScore(int score) {
		model.setLevelScore(score);
		persistence.persist(model);
	}

	// TODO Just for cheating
	public void increaseMaxLevel() {
		if (model.maxCompletedLevel() < 6) {
			model.setMaxCompletedLevel(model.maxCompletedLevel() + 1);
			model.setLevelDataDirty();
			persistence.persist(model);
		}
	}

	public void decreaseMaxLevel() {
		if (model.maxCompletedLevel() > 0) {
			model.setMaxCompletedLevel(model.maxCompletedLevel() - 1);
			model.setLevelDataDirty();
			persistence.persist(model);
		}
	}

    /**
     * Retrieves images which should be type of png.
     **/
    public Image getImage(String path) {
        return images.get(path);
    }
    
    public Icon getIcon(String path) {
    	return icons.get(path);
    }
    
    public String getText(String path) {
    	return text.get(path);
    }
    
	/**
	 * Retrieves and caches sounds.
	 **/
	public Sound getSound(String path) {
		return sounds.get(path);
	}

	@Override
	public void paint(float alpha) {
		_clock.paint(alpha);
		stack.paint(_clock);
		if (messageBox != null)
			messageBox.paint(_clock);
	}

	@Override
	public void update(int delta) {
		_clock.update(delta);
		stack.update(delta);
	}

	public ScreenStack stack() {
		return this.stack;
	}

	private final ScreenStack stack = new ScreenStack() {
		@Override
		protected void handleError(RuntimeException error) {
			PlayN.log().warn("Screen failure", error);
		}

		@Override
		protected Transition defaultPushTransition() {
			return slide();
		}

		@Override
		protected Transition defaultPopTransition() {
			return slide().right();
		}
	};
}
