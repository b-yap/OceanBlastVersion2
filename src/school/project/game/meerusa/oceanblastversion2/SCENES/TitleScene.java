/**
 * TitleScene.java
 * Jan 11, 2013
 * 2:01:15 PM
 *
 * @author B. Carla Yap 
 * email: bcarlayap@ymail.com
 *
 */

package school.project.game.meerusa.oceanblastversion2.SCENES;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public class TitleScene implements ISceneCreator {

	private BaseGameActivity titleActivity;
	
	private TextureRegion backgroundTextureRegion;
	private BitmapTextureAtlas backgroundAtlas;
	
	public TitleScene(BaseGameActivity activity){
		this.titleActivity =activity;
		
		//load resources
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");	
		backgroundAtlas = new BitmapTextureAtlas(titleActivity.getTextureManager(),1000,1000);
		backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.
				createFromAsset(backgroundAtlas, titleActivity, "background.png",0,0);
		backgroundAtlas.load();		
	}
	
	public void createScene(){	
		final Sprite oBackground = new Sprite(0,0,this.backgroundTextureRegion,titleActivity.getVertexBufferObjectManager());
		mScene.attachChild(oBackground); 
	}
	
	public Scene getScene(){
		return mScene;
	}
}

