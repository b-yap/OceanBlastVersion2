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
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
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
import org.andengine.util.HorizontalAlign;
import org.andengine.util.debug.Debug;
import android.opengl.GLES20;
import android.util.Log;

import school.project.game.meerusa.oceanblastversion2.ConstantsList;
import school.project.game.meerusa.oceanblastversion2.SceneManager;

public class MainGameScene implements ISceneCreator  {

	private BaseGameActivity mActivity;
	private VertexBufferObjectManager vertextBufferObjectManager;
	private PhysicsHandler physicsHandler;
	private Camera mCamera;
	private Scene mScene=new Scene();
	
	//player
		private BitmapTextureAtlas playerAtlas;
		private TiledTextureRegion submarineRegion;
	
	//enemy
		private  BuildableBitmapTextureAtlas animatedEnemyAtlas;
		private  TiledTextureRegion goldfishRegion;
		
	//pellet
		private BuildableBitmapTextureAtlas mShootTextureAtlas;
		private ITextureRegion mFace1TextureRegion;
		private ITextureRegion mFace2TextureRegion;
		private ITextureRegion mFace3TextureRegion;
		
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
		private Enemy goldfish;
		
	
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
				
		this.mShootTextureAtlas = new BuildableBitmapTextureAtlas(mActivity.getTextureManager(), 512, 512);
		this.mFace1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mShootTextureAtlas, mActivity, "face_box_tiled.png");
		this.mFace2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mShootTextureAtlas, mActivity, "face_circle_tiled.png");
		this.mFace3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mShootTextureAtlas, mActivity, "face_hexagon_tiled.png");
		
		try {
			this.mShootTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			this.mShootTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		
		//animated sprite: enemies
				this.animatedEnemyAtlas = new BuildableBitmapTextureAtlas(mActivity.getTextureManager(), 512, 256, TextureOptions.NEAREST);
				goldfishRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedEnemyAtlas, 
								 mActivity, "goldfish_tiled.png",2,1);
				
				try {
					this.animatedEnemyAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
					this.animatedEnemyAtlas.load();
				} catch (TextureAtlasBuilderException e) {
					Debug.e(e);
				}
		
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
		
		//enemy
	    goldfish = new Enemy(650,50,this.goldfishRegion,this.vertextBufferObjectManager,-100);
		goldfish.animation(200);
		final PhysicsHandler physicsHand = new PhysicsHandler(goldfish);
		goldfish.registerUpdateHandler(physicsHand);
		mScene.attachChild(goldfish);
			
		controls();
		pelletShooting();
		
		mScene.registerUpdateHandler(new IUpdateHandler() {
			public void reset() { }

			public void onUpdate(final float pSecondsElapsed) {
				//final Text centerText = new Text(350, 240, null, "Game Over!!", new TextOptions(HorizontalAlign.CENTER),vertextBufferObjectManager );				
				if(userPlayer.collidesWith(goldfish)) {
					Log.d("gameOver!", " ");
				}else{}
				
				
				if(!mCamera.isRectangularShapeVisible(userPlayer)) {
					//nothing
				}
			}
		});
	}

	public Scene getScene() {
		// TODO Auto-generated method stub
		return this.mScene;
	}
	private void pelletShooting(){
		// shoot bullet listener
		
		OnClickListener shootListener = new OnClickListener() {
			
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				//the bullet
			final Rectangle bullet = new Rectangle(userPlayer.getX()+100,userPlayer.getY()+50, 32, 32, vertextBufferObjectManager);
				bullet.setColor(1,0,0);
				PhysicsHandler bulletHandler = new PhysicsHandler(bullet);
				bulletHandler.setVelocityX(100);
				bullet.registerUpdateHandler(bulletHandler);
				mScene.attachChild(bullet);
				
				mScene.registerUpdateHandler(new IUpdateHandler() {
					public void reset() { 
											}

					public void onUpdate(final float pSecondsElapsed) {							
						if(bullet.collidesWith(goldfish)){
							mActivity.runOnUpdateThread(new Runnable(){

								@Override
								public void run() {
									mScene.detachChild(bullet);
									Log.d("hit!", " ");
								}
								
							});	 
							
							//bullet.dispose();
//								//bullet.detachSelf();
//								//bullet = null;
//								mScene.detachChild(bullet);
//								//bullet.setIgnoreUpdate(true);
//								bullet.clearEntityModifiers();
//								bullet.clearUpdateHandlers();		
								
						}	
					
					}
					
					
				});					
			}
		};
		
		//the shoot button
		final Sprite fire = new ButtonSprite(700, 420, this.mFace1TextureRegion, 
				this.mFace2TextureRegion, this.mFace3TextureRegion, this.vertextBufferObjectManager,shootListener);
		mScene.registerTouchArea(fire);
		mScene.attachChild(fire);
		mScene.setTouchAreaBindingOnActionDownEnabled(true);
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
	}
	
//	private void removeSprite(Sprite s) {
//        if ( s == null) {
//            return;
//        }
//
//        nmys.remove(s);
//        nmys_r.remove(s);
//
//        runOnUpdateThread(new Runnable() {
//            @Override
//            public void run() {
//                    /* Now it is save to remove the entity! */
//                    mEngine.getScene().getTopLayer().removeEntity(s);
//                    BufferObjectManager.getActiveInstance().unloadBufferObject(s.getVertexBuffer());
//            }
//        });
//
//    }

}

