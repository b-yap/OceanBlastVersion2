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

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.HorizontalAlign;
import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.TextOptions;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

import school.project.game.meerusa.oceanblastversion2.ConstantsList;
import school.project.game.meerusa.oceanblastversion2.SceneManager;

public class MainScene implements ISceneCreator 
{
	private BaseGameActivity mActivity;
	private Music mMusic;
	private SceneManager manager;
	//background
	private TextureRegion 		backgroundTextureRegion;
	private BitmapTextureAtlas 	backgroundAtlas;
	
	//menu buttons
	private BitmapTextureAtlas 	buttonAtlas;
	private TextureRegion 		playButtonRegion;
	private TextureRegion 		playPushedButtonRegion;
	private TextureRegion 		soundOnButtonRegion;
	private TextureRegion 		soundOffButtonRegion;
	
	//sprites
	public Sprite oBackground;
	public Sprite playButton;
	public Sprite soundButton;
	
	//constructor - load resources
	public MainScene(BaseGameActivity activity)
	{
		this.mActivity = activity;	
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");	
		
		//background
		backgroundAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(),1000,1000);
			backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundAtlas, mActivity, "background.png",0,0);
		backgroundAtlas.load();		
		
		//menu buttons
		buttonAtlas	= new BitmapTextureAtlas(mActivity.getTextureManager(),400,410);
			playButtonRegion 		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonAtlas, mActivity,"playBig.png",0,0);
			playPushedButtonRegion 	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonAtlas, mActivity,"playBigPushed.png",0,151);
			soundOnButtonRegion 	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonAtlas, mActivity,"soundOn.png",0,303);
			soundOffButtonRegion 	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonAtlas, mActivity,"soundOff.png",0,352);
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
				if(mMusic.isPlaying())
					mMusic.pause();
				else
					mMusic.play();	
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
		
		soundButton = new ButtonSprite(600,380,this.soundOnButtonRegion,this.soundOnButtonRegion,mActivity.getVertexBufferObjectManager(), musicListener);		
		mScene.registerTouchArea(soundButton);
		mScene.setTouchAreaBindingOnActionDownEnabled(true);
		mScene.attachChild(soundButton); 
	}
	
	private void dispatch(){
		mScene.detachChildren();
		mScene.detachSelf();
		mScene.dispose();
		this.manager.setCurrentScene(ConstantsList.SceneType.MAINGAME);
	}

	public Scene getScene() {
		// TODO Auto-generated method stub
		return mScene;
	}
    
	
	
}

