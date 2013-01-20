/**
 * ButtonSpriteExtension.java
 * Jan 19, 2013
 * 10:41:20 AM
 *
 * @author B. Carla Yap 
 * email: bcarlayap@ymail.com
 *
 */

package ViewsExtensions;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
 
//this is a class that animates a menu item button when pressed by using a tiled texture with two images (normal and pressed). 
//it also displays the supplied text on the button
public class AnimatedButtonSprite extends ButtonSprite {
 private float textX = 0;
 private float textY = 0;
 private Text buttonText;
  
  
 public AnimatedButtonSprite(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
  super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
 }
  
 public AnimatedButtonSprite(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, OnClickListener pOnClickListener) {
  super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager, pOnClickListener);
 }
  
 public AnimatedButtonSprite(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, String pText, Font pFont) {
  super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
  buttonText = new Text(0, 0, pFont, pText, pVertexBufferObjectManager);
  textX = (this.getWidth() - buttonText.getWidth()) / 2;
  textY = (this.getHeight() - buttonText.getHeight()) / 2;
  buttonText.setPosition(textX, textY);
  this.attachChild(buttonText);
 }
  
 public AnimatedButtonSprite(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, OnClickListener pOnClickListener, String pText, Font pFont) {
  super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager, pOnClickListener);
  buttonText = new Text(0, 0, pFont, pText, pVertexBufferObjectManager);
  textX = (this.getWidth() - buttonText.getWidth()) / 2;
  textY = (this.getHeight() - buttonText.getHeight()) / 2;
  buttonText.setPosition(textX, textY);
  this.attachChild(buttonText);
 }
  
 
 public void pressButton() {
  setCurrentTileIndex(1);
  if(this.buttonText != null) {
   buttonText.setX(textX + 5);
   buttonText.setY(textY + 5);
  }
 }
 
 public void depressButton() {
  setCurrentTileIndex(0);
  if(this.buttonText != null) {
   buttonText.setX(textX);
   buttonText.setY(textY);
  }
 }
  
 @Override
 public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
  if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
   if(this.buttonText != null) {
    buttonText.setX(textX + 5);
    buttonText.setY(textY + 5);
   }
  }
  else if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
   if(this.buttonText != null) {
    buttonText.setX(textX);
    buttonText.setY(textY);
   }
  }
  return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
 }
 
}

