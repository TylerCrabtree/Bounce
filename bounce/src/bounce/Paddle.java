package bounce;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;



public class Paddle extends Entity {



	private Vector velocity;
	private int countdown;

	public Paddle(final float x, final float y, final float vx, final float vy) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(BounceGame.PADDLE_RSC));
		velocity = new Vector(0, 0);
		countdown = 0;
	}

	public void setVelocity(final Vector v) {
		velocity = v;
	}

	public Vector getVelocity() {
		return velocity;
	}

	
	public void bounce(float surfaceTangent) {
		removeImage(ResourceManager.getImage(BounceGame.PADDLE_RSC));
		addImageWithBoundingBox(ResourceManager
				.getImage(BounceGame.PADDLE_RSC));
		countdown = 500;
		velocity = velocity.bounce(surfaceTangent);
	}

	/**
	 * Update the Ball based on how much time has passed...
	 * 
	 * @param delta
	 *            the number of milliseconds since the last update
	 */
	public void update(final int delta) {
		translate(velocity.scale(delta));
		if (countdown > 0) {
			countdown -= delta;

			
		}
	}
}
