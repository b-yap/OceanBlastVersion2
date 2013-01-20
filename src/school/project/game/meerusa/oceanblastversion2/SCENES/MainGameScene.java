/**
 * MainGameScene.java
 * Jan 19, 2013
 * 9:58:01 AM
 *
 * @author B. Carla Yap 
 * email: bcarlayap@ymail.com
 *
 */

package school.project.game.meerusa.oceanblastversion2.SCENES;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import android.opengl.GLES20;

import school.project.game.meerusa.oceanblastversion2.ConstantsList;
import school.project.game.meerusa.oceanblastversion2.SceneManager;

public class MainGameScene implements ISceneCreator {

	private BaseGameActivity mActivity;
	private VertexBufferObjectManager vertextBufferObjectManager;
	private PhysicsHandler physicsHandler;
	private Camera mCamera;
	private Scene mScene=new Scene();
	//player
		private BitmapTextureAtlas playerAtlas;
		private TiledTextureRegion submarineRegion;
		
	//parallax background
		private BitmapTextureAtlas autoParallaxAtlas;
		private ITextureRegion parallaxLayerBack;
		private ITextureRegion parallaxLayerFront;
		
	//controls
		private BitmapTextureAtlas onScreenControlAtlas;
		private ITextureRegion onScreenControlBaseRegion;
		private ITextureRegion onScreenControlKnobRegion;
	
	//sprites
		private AutoParallaxBackground autoParallaxBackground;
		private Sprite userPlayer;
	
	public MainGameScene(BaseGameActivity activity, Camera camera){
		this.mActivity = activity;
		this.mCamera =camera;
		
		//player: submarine
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");	
		playerAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(),200,200);
			submarineRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.playerAtlas,
							mActivity,"submarine.png",0,0,1,1);
		playerAtlas.load();
			
		//parallax background
		autoParallaxAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 1024, 1024);
			parallaxLayerFront = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.autoParallaxAtlas,
								mActivity, "parallax_background_layer_front.png", 0, 0);
			parallaxLayerBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.autoParallaxAtlas,
								mActivity, "background.png", 0, 188);
		autoParallaxAtlas.load();	
		
		//control images
		onScreenControlAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
			onScreenControlBaseRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(onScreenControlAtlas, 
										mActivity, "onscreen_control_base.png", 0, 0);
			onScreenControlKnobRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(onScreenControlAtlas, 
										mActivity, "onscreen_control_knob.png", 128, 0);
		onScreenControlAtlas.load();
				
				
		
	}
	
	public void createScene(SceneManager sceneManager) {
		//adjustment
		final int centerX=(ConstantsList.CAMERA_WIDTH - playerAtlas.getWidth())/2;
		final int centerY=(ConstantsList.CAMERA_HEIGHT - playerAtlas.getHeight())/2;	
		 this.vertextBufferObjectManager = mActivity.getVertexBufferObjectManager();
		//background
		 autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		 autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, ConstantsList.CAMERA_HEIGHT - 
				 this.parallaxLayerBack.getHeight(),this.parallaxLayerBack, this.vertextBufferObjectManager)));
		 autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-10.0f, new Sprite(0, ConstantsList.CAMERA_HEIGHT - 
				 this.parallaxLayerFront.getHeight(),this.parallaxLayerFront, this.vertextBufferObjectManager)));
		 mScene.setBackground(autoParallaxBackground);
		
		//player
		 this.userPlayer = new Sprite(centerX, centerY, this.submarineRegion,this.vertextBufferObjectManager);
		 physicsHandler = new PhysicsHandler(userPlayer);
		 userPlayer.registerUpdateHandler(physicsHandler);
		 mScene.attachChild(userPlayer);
		
		controls();
	}

	public Scene getScene() {
		// TODO Auto-generated method stub
		return this.mScene;
	}
	
	private void controls(){
		final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl(15, ConstantsList.CAMERA_HEIGHT -  
				this.onScreenControlBaseRegion.getHeight()-15, this.mCamera, 
				this.onScreenControlBaseRegion, this.onScreenControlKnobRegion, 
				0.1f, 200, this.vertextBufferObjectManager, new IAnalogOnScreenControlListener() {

					public void onControlChange(BaseOnScreenControl pBaseOnScreenControl,float pValueX, float pValueY) {
						physicsHandler.setVelocity(pValueX * 250, pValueY * 250);
						
						// setting boundaries
						int zeroA = 0;
						int zeroB = (int) userPlayer.getHeight();
						float velX = physicsHandler.getVelocityX();
						float velY = physicsHandler.getVelocityY();
        
						if (userPlayer.getX() < zeroA) {
                
							if (userPlayer.getY() < zeroA) {
								if (velX < 0)
									physicsHandler.setVelocityX(0.0f);
								if (velY < 0)
									physicsHandler.setVelocityY(0.0f);
							} 
							else if (userPlayer.getY() > ConstantsList.CAMERA_HEIGHT - zeroB) {
								if (velX < 0)
									physicsHandler.setVelocityX(0.0f);
								if (velY > 0)
									physicsHandler.setVelocityY(0.0f);
							} else {
								if (velX < 0)
									physicsHandler.setVelocityX(0.0f);
							}
							
						} else if (userPlayer.getX() > ConstantsList.CAMERA_WIDTH - zeroB) {
							
							if (userPlayer.getY() < zeroA) {
								if (velX > 0)
									physicsHandler.setVelocityX(0.0f);
								if (velY < 0)
									physicsHandler.setVelocityY(0.0f);

							} else if (userPlayer.getY() > ConstantsList.CAMERA_HEIGHT - zeroB) {
								if (velX > 0)
									physicsHandler.setVelocityX(0.0f);
								if (velY > 0)
									physicsHandler.setVelocityY(0.0f);

							} else {
                                if (velX > 0)
                                physicsHandler.setVelocityX(0.0f);
							  }
						
						} else {
								if (userPlayer.getY() < zeroA) {
                      
									if (velY < 0)
										physicsHandler.setVelocityY(0.0f);

								} else if (userPlayer.getY() > ConstantsList.CAMERA_HEIGHT - zeroB) {
                                     if (velY > 0)
                                    	 physicsHandler.setVelocityY(0.0f);

								} 
						}		
				} // end of onControlChange


					public void onControlClick(AnalogOnScreenControl pAnalogOnScreenControl) {
						userPlayer.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.25f, 1, 1.5f), new ScaleModifier(0.25f, 1.5f, 1)));
						
					}});
					
		analogOnScreenControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControl.getControlBase().setAlpha(0.5f);
		analogOnScreenControl.getControlBase().setScaleCenter(0, 128);
		analogOnScreenControl.getControlBase().setScale(1.25f);
		analogOnScreenControl.getControlKnob().setScale(1.25f);
		analogOnScreenControl.refreshControlKnobPosition();
		
		mScene.setChildScene(analogOnScreenControl);
		//mScene.attachChild(analogOnScreenControl);
		
		
	}

}

