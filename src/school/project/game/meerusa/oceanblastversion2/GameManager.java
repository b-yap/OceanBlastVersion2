/**
 * GameManager.java
 * Feb 1, 2013
 * 11:35:55 AM
 *
 * @author B. Carla Yap 
 * email: bcarlayap@ymail.com
 *
 */

package school.project.game.meerusa.oceanblastversion2;

public class GameManager {
	private static GameManager INSTANCE;
	private int mCurrentScore=0;
	
	public GameManager(){}
	
	public static GameManager getInstance(){
		if(INSTANCE == null){
			INSTANCE = new GameManager();
		}
		return INSTANCE;
	}
	
	public int getCurrentScore(){
		return this.mCurrentScore;
	}
	
	public void incrementScore(int pIncrementBy){
		mCurrentScore +=pIncrementBy;
	}
	
	public void resetGame(){
		this.mCurrentScore = 0;
	}

}

