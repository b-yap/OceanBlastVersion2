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

import android.util.Log;
import android.widget.Toast;
import school.project.game.meerusa.oceanblastversion2.IObserver;

public class ScoreObserver implements IObserver
 {
	private static final String OBSERVER_NAME = "ScoreObserver";
	private final BaseGameActivity mActivity;
	
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

