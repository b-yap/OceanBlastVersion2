/**
 * ILoaderObserver.java
 * Jan 16, 2013
 * 9:52:37 PM
 *
 * @author B. Carla Yap 
 * email: bcarlayap@ymail.com
 *
 */

package school.project.game.meerusa.oceanblastversion2;

public interface ILoaderObserver {
	   public void registerObserver(IObserver observer);

       public void removeObserver(IObserver observer);

       public void notifyObservers();

}

