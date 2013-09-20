package au.edu.unimelb.csse.trollsvsgoats.core.view;

import playn.core.Image;
import playn.core.PlayN;
import au.edu.unimelb.csse.trollsvsgoats.core.TrollsVsGoatsGame;
import au.edu.unimelb.csse.trollsvsgoats.core.model.Animation;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Icon;
import tripleplay.ui.Icons;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AbsoluteLayout;

public class LoadingScreen extends View {
	private final int FRAMES=44, FRAME_WIDTH=160;
	Button frame;
	Animation loadingAnim;

	public LoadingScreen(TrollsVsGoatsGame game) {
		super(game);
	}

	@Override
	protected Group createIface() {
		Image loading_bg = PlayN.assets().getImage("images/loading_screen/loading_screen_1024_720-01.png");
		root.addStyles(Style.BACKGROUND.is(Background.image(loading_bg)));
		topPanel.addStyles(Style.BACKGROUND.is(Background.blank()));

		Group myroot = new Group(new AbsoluteLayout());
		Image animSprite = PlayN.assets().getImage("images/loading_screen/loadingSpriteSheet.png");
		System.out.println(animSprite.width());
		loadingAnim = new Animation(animSprite, FRAMES, 160, 90, 0.03f);
		Icon firstFrame = Icons.image(loadingAnim.nextFrame(0));
		
		frame = new Button(firstFrame);
		frame.layer.setInteractive(false);
		frame.addStyles(Style.BACKGROUND.is(Background.blank()));
		myroot.add(AbsoluteLayout.at(frame, (1024-FRAME_WIDTH)/2, 400));

		return myroot;
	}

	@Override
	protected String title() {
		return null;
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		if(this.loadingAnim!=null){
			frame.icon.update(Icons.image(this.loadingAnim.nextFrame(delta)));
		}
	}

}
