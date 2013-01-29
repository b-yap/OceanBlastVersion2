/**
 * PauseScene.java
 * Jan 29, 2013
 * 4:50:55 PM
 *
 * @author B. Carla Yap 
 * email: bcarlayap@ymail.com
 *
 */

package school.project.game.meerusa.oceanblastversion2.SCENES;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;

import school.project.game.meerusa.oceanblastversion2.ConstantsList;
import school.project.game.meerusa.oceanblastversion2.SceneManager;

public class PauseScene implements ISceneCreator {
	
	private CameraScene mScene;
	private Engine mEngine;
	private SceneManager manager;
	private BaseGameActivity mActivity;
	private TextureRegion mPausedTextureRegion;
	private BitmapTextureAtlas pausedAtlas;
	private Sprite pauseButton;
	
	public PauseScene(BaseGameActivity activity, Camera camera, Engine engine){
		mActivity = activity;
		mScene = new CameraScene(camera);
		mEngine = engine;

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		pausedAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 200, 50, TextureOptions.DEFAULT);
		mPausedTextureRegion = BitmapTextureAtlasTextureRegionFactory		
			    .createFromAsset(this.pausedAtlas, mActivity, "paused.png",0, 0);
		pausedAtlas.load();

		
	}
	 /**
     * Creates a Sprite that manages Pausing
     * @param pX X Position to be created at
     * @param pY Y Position to be created at
     * @return
     */
    private Sprite createPauseSprite(float centerX, float centerY, TextureRegion button) {
    	
    	final Sprite pauseButton = new Sprite(centerX, centerY,	button,
				mActivity.getVertexBufferObjectManager()) {

				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
						final float pTouchAreaLocalX, final float pTouchAreaLocalY) {				
						// Toggle pause or not				
						switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							Log.d("action down on pause", " ");
							dispatch();
							break;
						case TouchEvent.ACTION_MOVE:
							Log.d("action down on move", " ");
							dispatch();
							break;
						case TouchEvent.ACTION_UP:
							break;
						}
				return true;
				}
		};
		
	return pauseButton;
    }

	
	@Override
	public void createScene(SceneManager sceneManager) {
		manager = sceneManager;
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final float centerX = (ConstantsList.CAMERA_WIDTH - this.mPausedTextureRegion.getWidth()) / 2;
		final float centerY = (ConstantsList.CAMERA_HEIGHT - this.mPausedTextureRegion.getHeight()) / 2;
		
		final Sprite pausedSprite = createPauseSprite(centerX, centerY,this.mPausedTextureRegion);
		mScene.registerTouchArea(pausedSprite);
		 mScene.setTouchAreaBindingOnActionDownEnabled(true);
		mScene.attachChild(pausedSprite);
		/* Makes the paused Game look through. */
		mScene.setBackgroundEnabled(false);
		
	}
	
	
	private void dispatch(){
		
		manager.setCurrentScene(ConstantsList.SceneType.MAINGAME);
	}

	@Override
	public Scene getScene() {
		return mScene;
	}
	
	

}

