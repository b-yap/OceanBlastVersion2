/**
 * ScoreScene.java
 * Jan 16, 2013
 * 9:53:28 PM
 *
 * @author B. Carla Yap 
 * email: bcarlayap@ymail.com
 *
 */

package school.project.game.meerusa.oceanblastversion2.SCENES;

import org.andengine.ui.activity.BaseGameActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import school.project.game.meerusa.oceanblastversion2.IObserver;

public class ScoreObserver implements IObserver
 {
	private static final String OBSERVER_NAME = "ScoreObserver";
	private static final String PREFS_NAME ="GAME_USERDATA";
	private static final String UNLOCKED_LEVEL_KEY = "unlockedLevels";
	private static String SOUND_KEY = "soundKey";
	
	
	private SharedPreferences mSettings;
	private SharedPreferences.Editor mEditor;
	
	private int mUnlockedLevels;
	private final BaseGameActivity mActivity;
	private boolean mSoundEnabled;
	
	public synchronized void init(Context pContext){
		if(mSettings == null){
			mSettings = pContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
			mEditor = mSettings.edit();
			
			mUnlockedLevels = mSettings.getInt(UNLOCKED_LEVEL_KEY, 1);
			mSoundEnabled = mSettings.getBoolean(SOUND_KEY, true);
		}
	}
	
	public synchronized void unlockNextLevel(){
		mUnlockedLevels++;
		mEditor.putInt(UNLOCKED_LEVEL_KEY, mUnlockedLevels);
		mEditor.commit();
	}
	
	public ScoreObserver(BaseGameActivity activity){
		Log.d(OBSERVER_NAME+" intialized"," ");
		this.mActivity=activity;
	}

	public void update(String note) {
		// TODO Auto-generated method stub

       // Toast.makeText(mActivity.getBaseContext(), "High Score is now "+note, Toast.LENGTH_SHORT).show();	
		Log.d(note+" is notified", " ");
	}

	public String getObserverName() {
		// TODO Auto-generated method stub
		return OBSERVER_NAME;
		
	}


	

}

