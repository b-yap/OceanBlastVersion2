/**
 * SceneManager.java
 * Jan 11, 2013
 * 2:04:14 PM
 *
 * @author B. Carla Yap 
 * email: bcarlayap@ymail.com
 *
 */

package school.project.game.meerusa.oceanblastversion2;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;

import school.project.game.meerusa.oceanblastversion2.SCENES.ISceneCreator;
import school.project.game.meerusa.oceanblastversion2.SCENES.MainScene;

public class SceneManager {

	private ConstantsList.SceneType currentScene;
	private BaseGameActivity mainActivity;
	private Engine mEngine;
		
	public ISceneCreator menuScene;
    public Scene mainGameScene;

	public SceneManager(BaseGameActivity activity, Engine engine) {
		this.mEngine = engine;
		this.mainActivity = activity;
	}

	//Method loads all of the resources for the game scenes
	public void loadGameSceneResources() {
		menuScene = new MainScene(mainActivity);
	}

	//Method creates all of the Game Scenes
	public void createGameScenes() {
		//Create the Title Scene
		menuScene.createScene(this);
		//Create the Main Game Scene and set background colour to blue
		mainGameScene = new Scene();
		mainGameScene.setBackground(new Background(0, 0, 1));

	}

	//Method allows you to get the currently active scene
	public ConstantsList.SceneType getCurrentScene() {
		return currentScene;
	}	

	//Method allows you to set the currently active scene
	public void setCurrentScene(ConstantsList.SceneType scene) {
		currentScene = scene;
		switch (scene)
		{
		case SPLASH:
			break;
		case MENU:
			{mEngine.setScene(menuScene.getScene());
			
			 Log.d("------------------------END HERE-----------------------", " ");
			break;}
			
		case MAINGAME:{
			mEngine.setScene(mainGameScene);
			 Log.d("------------------------END HERE 3-----------------------", " ");
			break;
			}
		}
	}
	

}



