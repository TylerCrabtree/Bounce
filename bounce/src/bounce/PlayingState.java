package bounce;

import java.util.Iterator;

import jig.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

//ty
/**
 * This state is active when the Game is being played. In this state, sound is
 * turned on, the bounce counter begins at 0 and increases until 10 at which
 * point a transition to the Game Over state is initiated. The user can also
 * control the ball using the WAS & D keys.
 * 
 * Transitions From StartUpState
 * 
 * Transitions To GameOverState
 */
class PlayingState extends BasicGameState {
	int bounces;   // various counters for lives, levels, bricks, and safety checks
	int lives;
	int level;
	int bricks;
	int check = 0;
	int safety = 0;
	int b8 = 1;
	int b7 = 1;
	int b6 = 1;
	int b5 = 1;
	int b4 = 1;
	int b3 = 1;
	int b2 = 1;
	int b1 = 1;
	int s8 = 0;	 // "s" for safety checks
	int s7 = 0;
	int s6 = 0;
	int s5 = 0;
	int s4 = 0;
	int s3 = 0;
	int s2 = 0;
	int s1 = 0;
	int heartCounter = 0;
	Enemy enemy; // for enemy design
	Enemy2 enemy2;
	Heart heart;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		BounceGame bg = (BounceGame)game;

		bounces = 0;   // in game counters here
		lives = 3;
		level = 1;
		
		enemy = new Enemy(bg.ScreenWidth/2, bg.ScreenHeight, .1f, .2f);   // for enemies
		enemy2 = new Enemy2(bg.ScreenWidth/2 , bg.ScreenHeight, .1f, .2f);
		heart = new Heart(bg.ScreenWidth/2 , bg.ScreenHeight, .1f, .2f);

		container.setSoundOn(true);
	}
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		BounceGame bg = (BounceGame)game;
		if(level == 1){
			enemy.destroyed();
			enemy2.destroyed();
			heart.destroyed();
		}
		if(level == 2){
			enemy2.destroyed();
			heart.destroyed();
		}
		bg.paddle.render(g);
		enemy.render(g);
		enemy2.render(g);
		heart.render(g);

		
		
		bg.f1.render(g);
		bg.f2.render(g);
		bg.f3.render(g);
		bg.f4.render(g);
		bg.f5.render(g);
		bg.f6.render(g);
		bg.f7.render(g);
		bg.f8.render(g);
		bg.f9.render(g);
		bg.ball.render(g);
		bg.brick.render(g);
		bg.b2.render(g);
		bg.b3.render(g);
		bg.b4.render(g);
		bg.b5.render(g);
		bg.b6.render(g);
		bg.b7.render(g);
		bg.b8.render(g);
		g.drawString("Bounces: " + bounces, 10, 30);
		g.drawString("Lives: " + lives, 10, 50);
		g.drawString("Level: " + level, 10, 70);

		for (Bang b : bg.explosions)
			b.render(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		BounceGame bg = (BounceGame)game;

		
		//helpful collision detection
		float xdirection = bg.ball.getVelocity().getX();
		float ydirection = bg.ball.getVelocity().getY();
		float ydragon = enemy.getVelocity().getY();
		float xdragon = enemy.getVelocity().getX();
		float ydragon2 = enemy2.getVelocity().getY();
		float xdragon2 = enemy2.getVelocity().getX();
		float yheart = heart.getVelocity().getY();
		float xheart = heart.getVelocity().getX();
		float xpaddle = bg.paddle.getVelocity().getX();
		
		
		
		// "Spin"
		if (input.isKeyDown(Input.KEY_W)) {
			bg.ball.setVelocity(bg.ball.getVelocity().add(new Vector(0f, -.0001f)));
		}
		if (input.isKeyDown(Input.KEY_S)) {
			bg.ball.setVelocity(bg.ball.getVelocity().add(new Vector(0f, +.0001f)));
		}
		if (input.isKeyDown(Input.KEY_A)) {
			bg.ball.setVelocity(bg.ball.getVelocity().add(new Vector(-.001f, 0)));
		}
		if (input.isKeyDown(Input.KEY_D)) {
			bg.ball.setVelocity(bg.ball.getVelocity().add(new Vector(+.001f, 0f)));
		}
		
		
		
		
		// Paddle
		if (input.isKeyDown(Input.KEY_A)) {
			bg.paddle.setVelocity(bg.paddle.getVelocity().add(new Vector(-.01f, 0)));
		}
		
		if (input.isKeyDown(Input.KEY_D)) {
			bg.paddle.setVelocity(bg.paddle.getVelocity().add(new Vector(+.01f, 0)));
		}
	
		
		
		// bounce the ball...
		
		boolean bounced = false;
		if ((bg.ball.getCoarseGrainedMaxX() > bg.ScreenWidth) && (xdirection > 0)){
				bg.ball.bounce(90);
				bounced = true;
		} 
		else if ((bg.ball.getCoarseGrainedMinX() < 0 ) && (xdirection < 0)){
			bg.ball.bounce(90);
			bounced = true;
		} 
		
		else if (( bg.ball.getCoarseGrainedMinY() < 0 )&& (ydirection < 0)) {
			bg.ball.bounce(0);
			bounced = true;
		
		}else if (bg.ball.getCoarseGrainedMaxY() > bg.ScreenHeight && (ydirection > 0) ) {
			bg.ball.bounce(0);
			//bounced = true;
			lives--;
		}
		
		if (bounced) {
			bg.explosions.add(new Bang(bg.ball.getX(), bg.ball.getY()));
			bounces++;
		}
		
		//heart
		
		if ((heart.getCoarseGrainedMaxX() > bg.ScreenWidth) && (xheart > 0)){
			heart.bounce(90);
			//bounced = true;
		} 
		else if ((heart.getCoarseGrainedMinX() < 0 ) && (xheart < 0)){
			heart.bounce(90);
			//bounced = true;
		} 
	
		else if (( heart.getCoarseGrainedMinY() < 0 )&& (yheart < 0)) {
			heart.bounce(0);
		//bounced = true;
	
		}else if (heart.getCoarseGrainedMaxY() > bg.ScreenHeight && (yheart > 0) ) {
		heart.bounce(0);
		//bounced = true;
		//lives--;
		}

		
		
		
		
		
		
		
		
		
		
		
		
		if ((enemy.getCoarseGrainedMaxX() > bg.ScreenWidth) && (xdragon > 0)){
			enemy.bounce(90);
			//bounced = true;
		} 
		else if ((enemy.getCoarseGrainedMinX() < 0 ) && (xdragon < 0)){
			enemy.bounce(90);
			//bounced = true;
		} 
	
		else if (( enemy.getCoarseGrainedMinY() < 0 )&& (ydragon < 0)) {
			enemy.bounce(0);
			//bounced = true;
	
		}else if (enemy.getCoarseGrainedMaxY() > bg.ScreenHeight && (ydragon > 0) ) {
			enemy.bounce(0);
			//bounced = true;
			//lives--;
		}
	
	
		if ((enemy2.getCoarseGrainedMaxX() > bg.ScreenWidth) && (xdragon2 > 0)){
			enemy2.bounce(90);
			//bounced = true;
		} 
		else if ((enemy2.getCoarseGrainedMinX() < 0 ) && (xdragon2 < 0)){
			enemy2.bounce(90);
			//bounced = true;
		}	 
	
		else if (( enemy2.getCoarseGrainedMinY() < 0 )&& (ydragon2 < 0)) {
			enemy2.bounce(0);
			//bounced = true;
	
		}else if (enemy2.getCoarseGrainedMaxY() > bg.ScreenHeight && (ydragon2 > 0) ) {
			enemy2.bounce(0);
			//bounced = true;
			//lives--;
		}
	
		
		// bounce paddle
		if ((bg.paddle.getCoarseGrainedMaxX() > bg.ScreenWidth) && (xpaddle > 0)){
			bg.paddle.bounce(90);
			bounced = true;
			
		} else if((bg.paddle.getCoarseGrainedMinX() < 0) && (xpaddle < 0)){
			bg.paddle.bounce(90);
			bounced = true;
		}
		
		else if ((bg.paddle.getCoarseGrainedMaxY() > bg.ScreenHeight) && (ydirection > 0)) {
				
			bg.paddle.bounce(0);
			bounced = true;
		} else if ((bg.paddle.getCoarseGrainedMinY() < 0)&& (ydirection > 0)) {
			bg.paddle.bounce(0);
			bounced = true;
		}
		if ((bg.paddle.collides(bg.ball) != null) && (ydirection > 0)) {
			bg.ball.translate(xpaddle, ydirection);
			bg.ball.bounce(0);
			bounced = true;
			
		}
		
		//enemy collisions
		if ((bg.paddle.collides(enemy) != null))  {
			if((ydragon < 0)){
			enemy.bounce(90);
				if (safety == 0){
					if ((level == 2) || (level == 3)){
					safety = 1;
			
					lives = lives -1;
					}
				}
			}
		}
		else if ((bg.paddle.collides(enemy2) != null) && (ydragon2 < 0)) {
			enemy2.bounce(90);
			if (safety == 0){
				if ( (level == 3)){
				lives--;
				safety++;
				}
			}
		}
		else if ((bg.paddle.collides(enemy) != null) && (ydragon > 0)) {
			enemy.bounce(0);
			if (safety == 0){
				if ((level == 2) || (level == 3)){
				lives--;
				safety++;
				}
			}
			//lives= lives -1;
		}
		else if ((bg.paddle.collides(enemy2) != null) && (ydragon2 > 0)) {
			enemy2.bounce(0);
			if (safety == 0){
				if ( (level == 3)){
				lives--;
				safety++;
				}
			}
			//lives= lives -1;
		}
		
		
		if ((bg.paddle.collides(heart) != null))  {
			if(level == 3){
				if(heartCounter == 0){
					lives= lives + 1;
					heart.destroyed();
					heartCounter = 1;
				}
			}
		}

		
		if (s8 == 0){
			if ((bg.brick.collides(bg.ball) != null) && (ydirection < 0)) {
				bg.brick.destroyed();
				bg.ball.bounce(0);
				bounced = true;			
				s8++;
				b8 =0;
				bounces++;

			}
		}
		if (s8 == 0){
			if ((bg.brick.collides(bg.ball) != null) && (ydirection > 0)) {
				bg.brick.destroyed();
				bg.ball.bounce(90);
				bounced = true;			
				s8++;
				b8 =0;
				bounces++;

				}
			}
		
		if (s7 == 0){
			if ((bg.b2.collides(bg.ball) != null) && (ydirection < 0)) {
				bg.b2.destroyed();
				bg.ball.bounce(0);
				bounced = true;
				bounces++;

				s7++;
				b7 = 0;
			}
		}
		
		if (s7 == 0){
			if ((bg.b2.collides(bg.ball) != null) && (ydirection > 0)) {
				bg.b2.destroyed();
				bg.ball.bounce(90);
				bounced = true;
				bounces++;
				s7++;
				b7 = 0;
				}
			}
		
		
		
		if (s6 == 0){
			if ((bg.b3.collides(bg.ball) != null) && (ydirection < 0)) {
				bg.b3.destroyed();
				bg.ball.bounce(0);
				bounced = true;
				bounces++;

				s6++;
				b6 = 0;
			}
		}
		if (s6 == 0){
			if ((bg.b3.collides(bg.ball) != null) && (ydirection > 0)) {
				bg.b3.destroyed();
				bg.ball.bounce(90);
				bounced = true;
				bounces++;

				s6++;
				b6 = 0;
				}
		}
		
		if (s5 == 0){
			if ((bg.b4.collides(bg.ball) != null) && (ydirection < 0)) {
				bg.b4.destroyed();
				bg.ball.bounce(0);
				bounced = true;
				bounces++;

				s5++;
				b5 = 0;
			}
		}
		if (s5 == 0){

			if ((bg.b4.collides(bg.ball) != null) && (ydirection > 0)) {
				bg.b4.destroyed();
				bg.ball.bounce(90);
				bounced = true;
				bounces++;

				s5++;
				b5 = 0;
		}
		
		}
		
		if (s4 == 0){
			if ((bg.b5.collides(bg.ball) != null) && (ydirection < 0)) {
				bg.b5.destroyed();
				bg.ball.bounce(0);
				bounced = true;
				bounces++;

				s4++;
				b4 = 0;
			}
		}
		if (s4 == 0){

			if ((bg.b5.collides(bg.ball) != null) && (ydirection > 0)) {
				bg.b5.destroyed();
				bg.ball.bounce(90);
				bounced = true;
				bounces++;

				s4++;
				b4 = 0;
			}
		}
		
		if (s3 == 0){
			if ((bg.b6.collides(bg.ball) != null) && (ydirection < 0)) {
				bg.b6.destroyed();
				bg.ball.bounce(0);
				bounced = true;
				bounces++;

				s3++;
				b3 = 0;
			}
		}
		if (s3 == 0){
			if ((bg.b6.collides(bg.ball) != null) && (ydirection > 0)) {
				bg.b6.destroyed();
				bg.ball.bounce(90);
				bounced = true;
				bounces++;

				s3++;
				b3 = 0;
			}
		}
		
		
		
		
		if (s2 == 0){
			if ((bg.b7.collides(bg.ball) != null) && (ydirection < 0)) {
				bg.b7.destroyed();
				bg.ball.bounce(0);
				bounced = true;
				bounces++;

				s2++;
				b2 = 0;
			}
		
			}
		if (s2 == 0){
			if ((bg.b7.collides(bg.ball) != null) && (ydirection > 0)) {
				bg.b7.destroyed();
				bg.ball.bounce(90);
				bounced = true;
				bounces++;

				s2++;
				b2 = 0;
			}
		
		}
		
		if (s1 == 0){
			if ((bg.b8.collides(bg.ball) != null) && (ydirection < 0)) {
				bg.b8.destroyed();
				bg.ball.bounce(0);
				bounced = true;
				s1++;
				b1 =0;
				bounces++;
			}
		}
		if (s1 == 0){
			if ((bg.b8.collides(bg.ball) != null) && (ydirection < 0)) {
				bg.b8.destroyed();
				bg.ball.bounce(90);
				bounced = true;
				s1++;
				b1 =0;
				bounces++;
			}
		
		}
	
		
		// level checks
		int sum = (b1+b2+b3+b4+b5+b6+b7+b8);
		
		if(sum == 0){
			level = level+1;
			bricks = 8;
			 s1 = 0;
			 s2 = 0;
			 s3 = 0;
			 s4 = 0;
			 s5 = 0;
			 s6 = 0;
			 s7 = 0;
			 s8 = 0;
			 b1 = 1;
			 b2 = 1;
			 b3 = 1;
			 b4 = 1;
			 b5 = 1;
			 b6 = 1;
			 b7 = 1;
			 b8 = 1;
			 
		}
		//cheat codes
		if (input.isKeyDown(Input.KEY_1)) {
			level = 1;
			 s1 = 0;
			 s2 = 0;
			 s3 = 0;
			 s4 = 0;
			 s5 = 0;
			 s6 = 0;
			 s7 = 0;
			 s8 = 0;
		
			 
			enemy.destroyed();
			enemy2.destroyed();
			heart.destroyed();

			bg.brick.destroyed();
			bg.b2.destroyed();
			bg.b3.destroyed();
			bg.b4.destroyed();
			bg.b5.destroyed();
			bg.b6.destroyed();
			bg.b7.destroyed();
			bg.b8.destroyed();
			bg.brick.refresh();
			bg.b2.refresh();
			bg.b3.refresh();
			bg.b4.refresh();
			bg.b5.refresh();
			bg.b6.refresh();
			bg.b7.refresh();
			bg.b8.refresh();
		 	b1 = 1;
		 	b2 = 1;
		 	b3 = 1;
		 	b4 = 1;
		 	b5 = 1;
		 	b6 = 1;
		 	b7 = 1;
		 	b8 = 1;
			check = 0;
			
		}
		
		if (input.isKeyDown(Input.KEY_2)) {
			enemy.destroyed();
			enemy.create(2, 2,.1f, -.02f);
			enemy2.destroyed();
			heart.destroyed();

			level = 2;
			 s1 = 0;
			 s2 = 0;
			 s3 = 0;
			 s4 = 0;
			 s5 = 0;
			 s6 = 0;
			 s7 = 0;
			 s8 = 0;
			bg.brick.destroyed();
			bg.b2.destroyed();
			bg.b3.destroyed();
			bg.b4.destroyed();
			bg.b5.destroyed();
			bg.b6.destroyed();
			bg.b7.destroyed();
			bg.b8.destroyed();
			bg.brick.refresh();
			bg.b2.refresh();
			bg.b3.refresh();
			bg.b4.refresh();
			bg.b5.refresh();
			bg.b6.refresh();
			bg.b7.refresh();
			bg.b8.refresh();
		 	b1 = 1;
		 	b2 = 1;
		 	b3 = 1;
		 	b4 = 1;
		 	b5 = 1;
		 	b6 = 1;
		 	b7 = 1;
		 	b8 = 1;
			check = 1;
			heartCounter = 0;

		}
		
		
		if (input.isKeyDown(Input.KEY_3)) {
			enemy.destroyed();
			enemy.create(0, 0,.1f, -.02f);
			enemy2.destroyed();
			enemy2.create(2, 2,-.02f, .1f);
			heart.destroyed();
			heart.create(2, 2,-.02f, -.1f);
			
			level = 3;
			 s1 = 0;
			 s2 = 0;
			 s3 = 0;
			 s4 = 0;
			 s5 = 0;
			 s6 = 0;
			 s7 = 0;
			 s8 = 0;
			bg.brick.destroyed();
			bg.b2.destroyed();
			bg.b3.destroyed();
			bg.b4.destroyed();
			bg.b5.destroyed();
			bg.b6.destroyed();
			bg.b7.destroyed();
			bg.b8.destroyed();
			bg.brick.refresh();
			bg.b2.refresh();
			bg.b3.refresh();
			bg.b4.refresh();
			bg.b5.refresh();
			bg.b6.refresh();
			bg.b7.refresh();
			bg.b8.refresh();
		 	b1 = 1;
		 	b2 = 1;
		 	b3 = 1;
		 	b4 = 1;
		 	b5 = 1;
		 	b6 = 1;
		 	b7 = 1;
		 	b8 = 1;
			check = 2;
			heartCounter = 0;

			
		}
		
		
		if(level == 2){
			if(check == 0){
				enemy.destroyed();
				enemy.create(0, 0,.1f, -.02f);
				enemy2.destroyed();
				heart.destroyed();
				bg.brick.refresh();
				bg.b2.refresh();
				bg.b3.refresh();
				bg.b4.refresh();
				bg.b5.refresh();
				bg.b6.refresh();
				bg.b7.refresh();
				bg.b8.refresh();
			 	//b1 = 1;
			 	//b2 = 1;
			 //	b3 = 1;
			 //	b4 = 1;
			 //	b5 = 1;
			 //	b6 = 1;
			 //	b7 = 1;
			 //	b8 = 1;
				check = 1;
				heartCounter = 0;

			}
		}
		
		if(level == 3){
			if(check == 1){
				enemy.destroyed();
				enemy.create(0, 0,.1f, -.02f);
				enemy2.destroyed();
				enemy2.create(2, 2,-.02f, .1f);
				heart.destroyed();
				heart.create(2, 2,-.02f, -.1f);
				
				bg.brick.refresh();
				bg.b2.refresh();
				bg.b3.refresh();
				bg.b4.refresh();
				bg.b5.refresh();
				bg.b6.refresh();
				bg.b7.refresh();
				bg.b8.refresh();
		
				check = 2;
				heartCounter = 0;

			}
		}

		bg.ball.update(delta);
		bg.paddle.update(delta);
		enemy.update(delta);
		enemy2.update(delta);
		heart.update(delta);

		safety = 0;
		
		// check if there are any finished explosions, if so remove them
		for (Iterator<Bang> i = bg.explosions.iterator(); i.hasNext();) {
			if (!i.next().isActive()) {
				i.remove();
			}
		}
		
	
		if (lives == 00) {
			level = 1;
			 s1 = 0;
			 s2 = 0;
			 s3 = 0;
			 s4 = 0;
			 s5 = 0;
			 s6 = 0;
			 s7 = 0;
			 s8 = 0;
			bg.brick.destroyed();
			bg.b2.destroyed();
			bg.b3.destroyed();
			bg.b4.destroyed();
			bg.b5.destroyed();
			bg.b6.destroyed();
			bg.b7.destroyed();
			bg.b8.destroyed();
			bg.brick.refresh();
			bg.b2.refresh();
			bg.b3.refresh();
			bg.b4.refresh();
			bg.b5.refresh();
			bg.b6.refresh();
			bg.b7.refresh();
			bg.b8.refresh();
		 	b1 = 1;
		 	b2 = 1;
		 	b3 = 1;
		 	b4 = 1;
		 	b5 = 1;
		 	b6 = 1;
		 	b7 = 1;
		 	b8 = 1;
			check = 0;
			heartCounter = 0;

			
			((GameOverState)game.getState(BounceGame.GAMEOVERSTATE)).setUserScore(bounces);
			game.enterState(BounceGame.GAMEOVERSTATE);
		}
		
		
		if ((level == 4) || (input.isKeyDown(Input.KEY_4))) {
			((WinState)game.getState(BounceGame.WINSTATE)).setUserScore(bounces);
			game.enterState(BounceGame.WINSTATE);
			level = 1;
			 s1 = 0;
			 s2 = 0;
			 s3 = 0;
			 s4 = 0;
			 s5 = 0;
			 s6 = 0;
			 s7 = 0;
			 s8 = 0;
			bg.brick.destroyed();
			bg.b2.destroyed();
			bg.b3.destroyed();
			bg.b4.destroyed();
			bg.b5.destroyed();
			bg.b6.destroyed();
			bg.b7.destroyed();
			bg.b8.destroyed();
			bg.brick.refresh();
			bg.b2.refresh();
			bg.b3.refresh();
			bg.b4.refresh();
			bg.b5.refresh();
			bg.b6.refresh();
			bg.b7.refresh();
			bg.b8.refresh();
		 	b1 = 1;
		 	b2 = 1;
		 	b3 = 1;
		 	b4 = 1;
		 	b5 = 1;
		 	b6 = 1;
		 	b7 = 1;
		 	b8 = 1;
			check = 0;
			heartCounter = 0;
			
			
		}
	}

	@Override
	public int getID() {
		return BounceGame.PLAYINGSTATE;
	}
	
}