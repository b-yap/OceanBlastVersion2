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
import org.andengine.ui.activity.BaseGameActivity;

import school.project.game.meerusa.oceanblastversion2.SCENES.TitleScene;

public class SceneManager {

	private SceneType currentScene;
	private BaseGameActivity mainActivity;
	private Engine mEngine;
		
	//private Scene titleScene;
	private TitleScene titleScene;
    private Scene mainGameScene;

	public enum SceneType
	{
		SPLASH,
		TITLE,
		MAINGAME
	}

	public SceneManager(BaseGameActivity activity, Engine engine) {
		this.mEngine = engine;
		this.mainActivity = activity;
	}

	//Method loads all of the resources for the game scenes
	public void loadGameSceneResources() {
		titleScene = new TitleScene(mainActivity);
	}

	//Method creates all of the Game Scenes
	public void createGameScenes() {
		//Create the Title Scene
		titleScene.createScene();

		//Create the Main Game Scene and set background colour to blue
		mainGameScene = new Scene();
		mainGameScene.setBackground(new Background(0, 0, 1));

	}

	//Method allows you to get the currently active scene
	public SceneType getCurrentScene() {
		return currentScene;
	}

	//Method allows you to set the currently active scene
	public void setCurrentScene(SceneType scene) {
		currentScene = scene;
		switch (scene)
		{
		case SPLASH:
			break;
		case TITLE:
			mEngine.setScene(titleScene.getScene());
			break;
		case MAINGAME:
			mEngine.setScene(mainGameScene);
			break;
		}
	}

}



