package bounce;

import java.util.ArrayList;

import jig.Entity;
import jig.ResourceManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class BounceGame extends StateBasedGame {
	
	public static final int STARTUPSTATE = 0;
	public static final int PLAYINGSTATE = 1;
	public static final int GAMEOVERSTATE = 2;
	public static final int WINSTATE = 3;

	public static final String BALL_BALLIMG_RSC = "bounce/resource/ball.png";
	public static final String BALL_BROKENIMG_RSC = "bounce/resource/brokenball.png";
	public static final String GAMEOVER_BANNER_RSC = "bounce/resource/gameover.png";
	public static final String STARTUP_BANNER_RSC = "bounce/resource/PressSpace.png";
	public static final String BANG_EXPLOSIONIMG_RSC = "bounce/resource/explosion.png";
	public static final String BANG_EXPLOSIONSND_RSC = "bounce/resource/explosion.wav";
	public static final String PADDLE_RSC = "bounce/resource/paddle.png";
	public static final String BRICK_RSC = "bounce/resource/brick.png";
	public static final String WIN_RSC = "bounce/resource/trophy.png";
	public static final String DRAGON_RSC = "bounce/resource/dragon.png";
	public static final String DRAGON2_RSC = "bounce/resource/dragon2.png";
	public static final String FIRE_RSC = "bounce/resource/fire.png";
	public static final String HEART_RSC = "bounce/resource/heart.png";

	public final int ScreenWidth;
	public final int ScreenHeight;

	
	// list of game objects
	Ball ball;
	Paddle paddle;
	Enemy enemy;
	Enemy enemy2;
	Fire fire;
	Heart heart;
	Brick brick;
	Brick b2;
	Brick b3;
	Brick b4;
	Brick b5;
	Brick b6;
	Brick b7;
	Brick b8;
	
	Fire f1;
	Fire f2;
	Fire f3;
	Fire f4;
	Fire f5;
	Fire f6;
	Fire f7;
	Fire f8;
	Fire f9;
	

	
	
	
	ArrayList<Bang> explosions;

	/**
	 * Create the BounceGame frame, saving the width and height for later use.
	 * 
	 * @param title
	 *            the window's title
	 * @param width
	 *            the window's width
	 * @param height
	 *            the window's height
	 */
	public BounceGame(String title, int width, int height) {
		super(title);
		ScreenHeight = height;
		ScreenWidth = width;

		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
		explosions = new ArrayList<Bang>(10);
				
	}


	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartUpState());
		addState(new GameOverState());
		addState(new PlayingState());
		addState(new WinState());

		// the sound resource takes a particularly long time to load,
		// we preload it here to (1) reduce latency when we first play it
		// and (2) because loading it will load the audio libraries and
		// unless that is done now, we can't *disable* sound as we
		// attempt to do in the startUp() method.
		ResourceManager.loadSound(BANG_EXPLOSIONSND_RSC);	

		// preload all the resources to avoid warnings & minimize latency...
		ResourceManager.loadImage(BALL_BALLIMG_RSC);
		ResourceManager.loadImage(BALL_BROKENIMG_RSC);
		ResourceManager.loadImage(GAMEOVER_BANNER_RSC);
		ResourceManager.loadImage(STARTUP_BANNER_RSC);
		ResourceManager.loadImage(BANG_EXPLOSIONIMG_RSC);
		ResourceManager.loadImage(PADDLE_RSC);
		ResourceManager.loadImage(BRICK_RSC);
		ResourceManager.loadImage(WIN_RSC);
		ResourceManager.loadImage(DRAGON_RSC);
		ResourceManager.loadImage(DRAGON2_RSC);
		ResourceManager.loadImage(FIRE_RSC);
		ResourceManager.loadImage(HEART_RSC);
		//set placement of images
		ball = new Ball(ScreenWidth / 2, ScreenHeight / 2, .1f, .2f);
		paddle = new Paddle(ScreenWidth/ 2, ScreenHeight -50, .1f, .2f);
		brick = new Brick(ScreenWidth -50 , ScreenHeight - 550, .1f, .2f);
		b2 = new Brick(ScreenWidth  -150, ScreenHeight - 550, .1f, .2f);
		b3 = new Brick(ScreenWidth  -250, ScreenHeight - 550, .1f, .2f);
		b4 = new Brick(ScreenWidth  -350, ScreenHeight - 550, .1f, .2f);
		b5 = new Brick(ScreenWidth  -450, ScreenHeight - 550, .1f, .2f);
		b6 = new Brick(ScreenWidth  -550, ScreenHeight - 550, .1f, .2f);
		b7 = new Brick(ScreenWidth  -650, ScreenHeight - 550, .1f, .2f);
		b8 = new Brick(ScreenWidth  -750, ScreenHeight - 550, .1f, .2f);
		f1 = new Fire(ScreenWidth  -50, ScreenHeight - 10, .1f, .2f);
		f2 = new Fire(ScreenWidth  -150, ScreenHeight - 10, .1f, .2f);
		f3 = new Fire(ScreenWidth  -250, ScreenHeight - 10, .1f, .2f);
		f4 = new Fire(ScreenWidth  -350, ScreenHeight - 10, .1f, .2f);
		f5 = new Fire(ScreenWidth  -450, ScreenHeight -10, .1f, .2f);
		f6 = new Fire(ScreenWidth  -550, ScreenHeight - 10, .1f, .2f);
		f7 = new Fire(ScreenWidth  -650, ScreenHeight - 10, .1f, .2f);
		f8 = new Fire(ScreenWidth  -750, ScreenHeight - 10, .1f, .2f);
		f9 = new Fire(ScreenWidth  -850, ScreenHeight - 10, .1f, .2f);
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new BounceGame("Bounce!", 800, 600));
			app.setDisplayMode(800, 600, false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	
}
