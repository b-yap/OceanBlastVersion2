/**
 * Enemy.java
 * Jan 20, 2013
 * 1:55:46 PM
 *
 * @author B. Carla Yap 
 * email: bcarlayap@ymail.com
 *
 */

package school.project.game.meerusa.oceanblastversion2.SCENES;

import java.util.Random;

import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import school.project.game.meerusa.oceanblastversion2.ConstantsList;


public class Enemy extends GameObject {
	private int speed;
	// constructors
	public Enemy(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion,
					final VertexBufferObjectManager pVertexBufferObjectManager, int velocity)
	{
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.speed=velocity;
	}

	@Override
	public void move() {
		this.mPhysicsHandler.setVelocityX(this.speed);	
		OutofScreenX();
	}

	public void animation(int frameDuration) {
		this.animate(frameDuration);		
	}
	
	
	public void OutofScreenX() {
		
		if(mX> ConstantsList.CAMERA_WIDTH)
		{	mX=0;
			Random rand = new Random();
			int b=rand.nextInt(420);
			this.setY(b); 
		}
		else if(mX<0)
		{	mX=ConstantsList.CAMERA_WIDTH;
		Random rand = new Random();
		int b=rand.nextInt(420);
		this.setY(b);
		}	
	}
//	public void dispose(){
//		this.dispose();
//	}
//	public void dispatch(){
//		this.dispatch();
//	}

}

