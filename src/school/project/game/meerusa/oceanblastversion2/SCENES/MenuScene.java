/**
 * MenuScene.java
 * Jan 11, 2013
 * 2:01:15 PM
 *
 * @author B. Carla Yap 
 * email: bcarlayap@ymail.com
 *
 */

package school.project.game.meerusa.oceanblastversion2.SCENES;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

import school.project.game.meerusa.oceanblastversion2.ConstantsList;
import school.project.game.meerusa.oceanblastversion2.SceneManager;

public class MenuScene implements ISceneCreator 
{
	private BaseGameActivity mActivity;
	private Music mMusic;
	private SceneManager manager;
	private Scene mScene=new Scene();
	//background
	private TextureRegion 		backgroundTextureRegion;
	private BitmapTextureAtlas 	backgroundAtlas;
	
	//menu buttons
	private BitmapTextureAtlas 	buttonAtlas;
	private TextureRegion 		playButtonRegion;
	private TextureRegion 		playPushedButtonRegion;
	private TextureRegion		soundButtonRegion;
	private TextureRegion 		onRegion;
	private TextureRegion 		offRegion;
	//sprites
	private Sprite oBackground;
	private Sprite playButton;
	private ButtonSprite soundButton;
	private Sprite onButton;
	private Sprite offButton;
	
	
	//constructor - load resources
	public MenuScene(BaseGameActivity activity)
	{
		this.mActivity = activity;	
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");	
		
		//background
		backgroundAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(),1000,1000);
			backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundAtlas, mActivity, "background.png",0,0);
		backgroundAtlas.load();		
		
		//menu buttons
		buttonAtlas	= new BitmapTextureAtlas(mActivity.getTextureManager(),400,435);
			playButtonRegion 		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonAtlas, mActivity,"playBig.png",0,0);
			playPushedButtonRegion 	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonAtlas, mActivity,"playBigPushed.png",0,151);
			onRegion 				= BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonAtlas, mActivity,"on.png",0,303);
			offRegion 				= BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonAtlas, mActivity,"off.png",0,353);
			soundButtonRegion 		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonAtlas, mActivity,"soundToggles.png",0,405);
			buttonAtlas.load();
		
		
		//add music
		MusicFactory.setAssetBasePath("musix/");
		try	{
				this.mMusic = MusicFactory.createMusicFromAsset(mActivity.getMusicManager(),mActivity, "mainMenuMusic.mid");
				this.mMusic.setVolume(0.5f);
				this.mMusic.setLooping(true);
			}
		catch (IOException e)
			{
				e.printStackTrace();
			}	
	}
	
	public void createScene(SceneManager sceneManager)
	{	
		this.manager = sceneManager;
		//measurements
		final int centerX=(ConstantsList.CAMERA_WIDTH - buttonAtlas.getWidth())/2;
		final int centerY=(ConstantsList.CAMERA_HEIGHT - buttonAtlas.getHeight())/2;
		
		//listeners
        OnClickListener musicListener = new OnClickListener() {
			
			//play Music
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				if(mMusic.isPlaying()){	
					onButton.setVisible(false);
					offButton.setVisible(true);
					mMusic.pause();
				}
				else{	
					mMusic.play();
					onButton.setVisible(true);
					offButton.setVisible(false);
				}
				
			}
		};
		
		 OnClickListener playListener = new OnClickListener(){
			//play Music
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				dispatch();
			}
		};	
		
		//background
		 oBackground = new Sprite(0,0,this.backgroundTextureRegion,mActivity.getVertexBufferObjectManager());
		mScene.attachChild(oBackground); 
		
		//menu buttons
		playButton = new ButtonSprite(centerX,centerY,this.playButtonRegion,this.playPushedButtonRegion,
				mActivity.getVertexBufferObjectManager(), playListener);		
		mScene.registerTouchArea(playButton);
		mScene.setTouchAreaBindingOnActionDownEnabled(true);
		mScene.attachChild(playButton);
		
		//music
		soundButton = new ButtonSprite(600,380,this.soundButtonRegion,mActivity.getVertexBufferObjectManager(), musicListener);		
		mScene.registerTouchArea(soundButton);
		mScene.attachChild(soundButton);
		mScene.setTouchAreaBindingOnActionDownEnabled(true);
		
		onButton = new ButtonSprite(600,400,this.onRegion,mActivity.getVertexBufferObjectManager());	
		offButton= new ButtonSprite (600,400,this.offRegion, mActivity.getVertexBufferObjectManager());
		mScene.attachChild(onButton);
		mScene.attachChild(offButton);
		onButton.setVisible(true);
		offButton.setVisible(false);
		mMusic.play();
	}
	
	private void dispatch(){
		this.manager.setCurrentScene(ConstantsList.SceneType.MAINGAME);
	}

	public Scene getScene() {
		// TODO Auto-generated method stub
		return mScene;
	}
    
	
	
}

