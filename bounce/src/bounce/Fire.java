package bounce;

import jig.Entity;
import jig.ResourceManager;



public class Fire extends Entity {




	public Fire(final float x, final float y, final float vx, final float vy) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(BounceGame.FIRE_RSC));
		
	}

}
