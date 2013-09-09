package au.edu.unimelb.csse.trollsvsgoats.core.view;

import static playn.core.PlayN.assets;
import playn.core.AssetWatcher;
import playn.core.Font;
import playn.core.Image;
import playn.core.PlayN;
import au.edu.unimelb.csse.trollsvsgoats.core.TrollsVsGoatsGame;
import au.edu.unimelb.csse.trollsvsgoats.core.model.Animation;
import tripleplay.ui.Background;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Style;
import java.io.File;
import java.net.URI;

public class LoadingScreen extends View {

	Animation loadingAnim;
	
    public LoadingScreen(TrollsVsGoatsGame game) {
        super(game);
    }

    @Override
    protected Group createIface() {
    	
    	Image loading_bg = PlayN.assets().getImage("images/loading.png");

    	root.addStyles(Style.BACKGROUND.is(Background.image(loading_bg)));
    	topPanel.addStyles(Style.BACKGROUND.is(Background.blank()));
    	
    	root.add(new Label("LOADING ...").addStyles(Style.FONT.is(font(Font.Style.BOLD, 40))));
    	
        return null;
    }

    @Override
    protected String title() {
        return null;
    }

	@Override
	public void update(int delta) {
		super.update(delta);
		if(this.loadingAnim!=null){
			this.loadingAnim.nextFrame(delta);
		}
	}

}
