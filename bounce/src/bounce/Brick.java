package bounce;

import jig.Entity;
import jig.ResourceManager;


// brick class
public class Brick extends Entity {
	
	public Brick(final float x, final float y, final float vx, final float vy) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(BounceGame.BRICK_RSC));
	}


	//destroy brick
	public void destroyed() {
		removeImage(ResourceManager
				.getImage(BounceGame.BRICK_RSC));
		
	}
	//Create image
	public void refresh() {
		addImageWithBoundingBox(ResourceManager
				.getImage(BounceGame.BRICK_RSC));
		//velocity = new Vector(1, 1);
	}
	
}
