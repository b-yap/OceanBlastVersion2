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

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;

import school.project.game.meerusa.oceanblastversion2.SCENES.ISceneCreator;
import school.project.game.meerusa.oceanblastversion2.SCENES.GameScene;
import school.project.game.meerusa.oceanblastversion2.SCENES.MenuScene;
import school.project.game.meerusa.oceanblastversion2.SCENES.PauseScene;
import school.project.game.meerusa.oceanblastversion2.SCENES.ScoreObserver;

public class SceneManager implements ILoaderObserver {

	private ConstantsList.SceneType currentScene;
	private BaseGameActivity mainActivity;
	private Engine mEngine;
	private Camera mCamera;
	private ArrayList<IObserver> observers= new ArrayList<IObserver>();
	private IObserver scoreObserver; 
	private ISceneCreator menuScreen;
	private ISceneCreator gameScreen;
	private ISceneCreator pauseScreen;
	

	public SceneManager(BaseGameActivity activity, Engine engine, Camera camera) {
		this.mEngine = engine;
		this.mCamera = camera;
		this.mainActivity = activity;
		this.scoreObserver = new ScoreObserver(mainActivity);
		registerObserver(this.scoreObserver);		
	}

	//Method loads all of the resources for the game scenes
	public void loadGameSceneResources() {
		menuScreen = new MenuScene(mainActivity);
		gameScreen = new GameScene(mainActivity, this.mCamera);
		pauseScreen = new PauseScene(mainActivity, this.mCamera, this.mEngine);
		}

	//Method creates all of the Game Scenes
	public void createGameScenes() {
		menuScreen.createScene(this);
		gameScreen.createScene(this);
		pauseScreen.createScene(this);
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
			{mEngine.setScene(menuScreen.getScene());			
			 Log.d("set Menu", " ");
			break;
			}
		case MAINGAME:{
			notifyObservers();
			gameScreen.getScene().setIgnoreUpdate(false);
			gameScreen.getScene().clearChildScene();
			mEngine.setScene(gameScreen.getScene());
			 Log.d("set Main game", " ");
			break;
			}
		case SCORE:{
			Log.d("Scoring", " ");
			break;
		}
		case PAUSE:{	
			gameScreen.getScene().setIgnoreUpdate(true);
			gameScreen.getScene().setChildScene(pauseScreen.getScene(),false, true,true);
			Log.d("paused", "done");
			
			break;
		}
	}
	}
	public void registerObserver(IObserver observer) {
        observers.add(observer);
        Log.d(observer.getObserverName()+ " successfully registered"," ");	
	}

	public void removeObserver(IObserver observer) {
			observers.remove(observer);
		
	}

	public void notifyObservers() {
		for (IObserver ob : observers) {
             Log.d("Notifying Observers on change in Score", " ");
             ob.update("updated");
		}
		
	}
	

}



