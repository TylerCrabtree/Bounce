package bounce;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Enemy extends Entity {


	private Vector velocity;
	int countdown = 0;
	public Enemy(final float x, final float y, final float vx, final float vy) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(BounceGame.DRAGON_RSC));
		velocity = new Vector(vx, vy);
	}

	public void setVelocity(final Vector v) {
		velocity = v;
	}

	public void boostVelocity(final Vector v) {
		velocity.scale(2);
	}
	
	public Vector getVelocity() {
		return velocity;
	}
	public void bounce(float surfaceTangent) {
		countdown = 500;
		velocity = velocity.bounce(surfaceTangent);
	}
	
	public void destroyed() {
		removeImage(ResourceManager
				.getImage(BounceGame.DRAGON_RSC));
		
	}
	public void update(final int delta) {
		translate(velocity.scale(delta));
		
		}
	public void create(final float x, final float y, final float vx, final float vy) {
		addImageWithBoundingBox(ResourceManager
				.getImage(BounceGame.DRAGON_RSC));
		velocity = new Vector(vx, vy);
		
		}
}
