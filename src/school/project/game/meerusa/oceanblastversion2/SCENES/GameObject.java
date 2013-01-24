/**
 * GameObject.java
 * Jan 20, 2013
 * 1:57:32 PM
 *
 * @author B. Carla Yap 
 * email: bcarlayap@ymail.com
 *
 */

package school.project.game.meerusa.oceanblastversion2.SCENES;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class GameObject extends AnimatedSprite{

	//field
	public PhysicsHandler mPhysicsHandler;
	
	//constructors
	public GameObject(final float pX, final float pY, final ITiledTextureRegion pTiledTextureRegion,
					final VertexBufferObjectManager pVertexBufferObjectManager)
	{
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(this.mPhysicsHandler);
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed)
	{
		move();
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	public abstract void move();
}

