/**
 * GameActivity.java
 * Jan 11, 2013
 * 2:03:28 PM
 *
 * @author B. Carla Yap 
 * email: bcarlayap@ymail.com
 *
 */

package school.project.game.meerusa.oceanblastversion2;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;
import android.widget.Toast;

public class GameActivity extends BaseGameActivity
{
		private Camera camera;
		private Scene splashScene;
		private boolean mPlaceOnScreenControlsAtDifferentVerticalLocations = false;	
		public SceneManager sceneManager;
		
		    private BitmapTextureAtlas splashTextureAtlas;
		    private ITextureRegion splashTextureRegion;
		    private Sprite splash;
		
		public EngineOptions onCreateEngineOptions()
		{
		Log.d("-------onCreateEngineOptions()---------", " ");
				camera = new Camera(0, 0, ConstantsList.CAMERA_WIDTH, ConstantsList.CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		if(MultiTouch.isSupported(this)) {
			if(MultiTouch.isSupportedDistinct(this)) {
				Toast.makeText(this, "MultiTouch detected --> Both controls will work properly!", Toast.LENGTH_SHORT).show();
			} else {
				this.mPlaceOnScreenControlsAtDifferentVerticalLocations = true;
				Toast.makeText(this, "MultiTouch detected, but your device has problems distinguishing between fingers." +
						"\n\nControls are placed at different vertical locations.", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, "Sorry your device does NOT support MultiTouch!\n\n(Falling back to SingleTouch.)" +
					"\n\nControls are placed at different vertical locations.", Toast.LENGTH_LONG).show();
			}		
		
		return engineOptions;
		}

		public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception
		{
		Log.d("-------onCreateResources()---------", " ");
		sceneManager = new SceneManager(this,this.mEngine, this.camera);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		        splashTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
		        splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, this, "splash.png", 0, 0);
		        splashTextureAtlas.load();
		     
		        pOnCreateResourcesCallback.onCreateResourcesFinished();
		}

		public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception
		{
		Log.d("-------onCreateScene()---------", " ");
		initSplashScene();
		sceneManager.setCurrentScene(ConstantsList.SceneType.SPLASH);
		        pOnCreateSceneCallback.onCreateSceneFinished(this.splashScene);
		}

		public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception
		{
		Log.d("-------onPopulateScene()---------", " ");
		mEngine.registerUpdateHandler(new TimerHandler(5f, new ITimerCallback()
		{

					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						  mEngine.unregisterUpdateHandler(pTimerHandler);
			                loadResources();
			                loadScenes();        
			                splash.detachSelf();
			                splash.dispose();
			              /*  mEngine.setScene(mainScene);
			                currentScene = SceneType.MAIN;  */
			                sceneManager.setCurrentScene(ConstantsList.SceneType.MENU);
			   			 Log.d("------------------------END HERE 2--------------", " ");
			   			
					}
		}));
		 
		pOnPopulateSceneCallback.onPopulateSceneFinished();
		}

		public void loadResources()
		{
		Log.d("-------loadResources()()---------", " ");
		// Load your game resources here!
		sceneManager.loadGameSceneResources();
		}

		private void loadScenes()
		{
		Log.d("-------loadScenes()---------", " ");
				sceneManager.createGameScenes();
		}
		
		public Camera getCamera(){
			return this.camera;
		}

		// ===========================================================
		// INITIALIZE
		// ===========================================================

		private void initSplashScene()
		{
		Log.d("-------initSplashScene()---------", " ");
		    splashScene = new Scene();
		    splash = new Sprite(0, 0, splashTextureRegion, mEngine.getVertexBufferObjectManager())
		    {
		    @Override
		            protected void preDraw(GLState pGLState, Camera pCamera)
		    {
		                super.preDraw(pGLState, pCamera);
		                pGLState.enableDither();
		            }
		    };
		   
		    splash.setScale(1.5f);
		    splash.setPosition((ConstantsList.CAMERA_WIDTH - splash.getWidth()) * 0.5f, (ConstantsList.CAMERA_HEIGHT - splash.getHeight()) * 0.5f);
		    splashScene.attachChild(splash);
		}
		

	}
