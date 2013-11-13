/* GameScreen.java
 * 
 * The purpose of this class is to maintain and update the display of the phone
 * as long as the player is in-game. In-game is defined as the moment the user
 * hits Play to the time they return to the Main Menu screen, be that through
 * game over or through quitting via the pause menu.
 * 
 */

package com.spaceshooter.game;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;

import com.spaceshooter.game.Doppleganger;
import com.spaceshooter.game.LineShooter;
import com.spaceshooter.game.Mine;
import com.spaceshooter.game.Projectile;
import com.spaceshooter.framework.Game;
import com.spaceshooter.framework.Graphics;
import com.spaceshooter.framework.Image;
import com.spaceshooter.framework.Input.TouchEvent;
import com.spaceshooter.framework.Music;
import com.spaceshooter.framework.Screen;

@SuppressLint("NewApi")
public class GameScreen extends Screen {
	
	enum GameState {
		Ready, Running, Paused, GameOver
	}

	GameState state = GameState.Ready;

	// Variable Setup
	private static boolean updateScore, bossBattle;
	public static boolean playSound, playMusic;
	private int newEnemy = 5, newEnemyTimer = 180, warningCount = 0, Twarn = 0;
	public static int score = 0, turnValue = 0, livesLeft, bombs;
	private static Background bg1, bg2;
	private static Player player;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<PowerUp> powerups;
	public static ArrayList<Explosion> explosions;
	public static Music music;

	private Image currentSprite, ship, shipr;
	private Animation anim, deathanim, expanim;

	Paint paint, paint2;

	public GameScreen(Game game) {
		
		super(game);
		
		// Sets up a scrolling background. The background is as big as the display, 800x600,
		// so we reset set background every 800 pixels scrolled
		bg1 = new Background(0, 0);
		bg2 = new Background(0, -800);
		player = new Player();
		
		// Variables that keep certain statistics of the user
		bombs = 0;
		livesLeft = 3;
		score = 0;
		
		enemies = new ArrayList<Enemy>();
		powerups = new ArrayList<PowerUp>();
		explosions = new ArrayList<Explosion>();

		ship = Assets.ship;
		shipr = Assets.ship_respawn;

		anim = new Animation();
		anim.addFrame(ship, 1250);
		
		deathanim = new Animation();
		deathanim.addFrame(ship, 100);
		deathanim.addFrame(shipr, 100);

		currentSprite = anim.getImage();

		// Defining a paint object
		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

		paint2 = new Paint();
		paint2.setTextSize(100);
		paint2.setTextAlign(Paint.Align.CENTER);
		paint2.setAntiAlias(true);
		paint2.setColor(Color.WHITE);
		
		updateScore = true;
		bossBattle = false;
		
		music = Assets.ingame;
		
		if(playMusic)
			music.play();

	}

	@Override
	public void update(float deltaTime) {
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		// We have four separate update methods in this example.
		// Depending on the state of the game, we call different update methods.
		// Refer to Unit 3's code. We did a similar thing without separating the
		// update methods.

		if (state == GameState.Ready)
			updateReady(touchEvents);
		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	private void updateReady(List<TouchEvent> touchEvents) {

		// This example starts with a "Ready" screen.
		// When the user touches the screen, the game begins.
		// state now becomes GameState.Running.
		// Now the updateRunning() method will be called!

		if (touchEvents.size() > 0)
			state = GameState.Running;
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		
		// All touch input is handled here
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
            
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {

				if(inBounds(event, 540, 200, 60, 60)) {
					if(bombs > 0)
						bomb();
						bombs--;
				}
				// By setting eventx, we tell the Player class the location of the touch event,
				// so that the ship can move to the location the was last touched by the player
				else if(!inBounds(event, 0, 0, 60, 60)) {
					player.setEventx(event.x);
					player.setEventy(event.y-40);
				}

			}

			if (event.type == TouchEvent.TOUCH_UP) {
				
				if (inBounds(event, 0, 0, 60, 60)) {
					pause();
					System.out.println("PAUSED");
				}
				else if(!inBounds(event, 540, 200, 60, 60)) {
					player.setEventx(event.x);
					player.setEventy(event.y-40);
				}
			
			}
			
			if((event.type == TouchEvent.TOUCH_DRAGGED || event.type == TouchEvent.TOUCH_HOLD) && !inBounds(event, 0, 0, 60, 60) && !inBounds(event, 540, 200, 60, 60)){
            	
        		player.setEventx(event.x);
        		player.setEventy(event.y-40);
        		
            }
			

		}
		
		// Checks for death with no lives remaining, aka Game over
		if (livesLeft == 0) {
			state = GameState.GameOver;
			
			if(GameScreen.playSound)
				Assets.playerDeath.play(0.1f);
		}

		// The next several lines update each object on screen, like the player, enemies, power-ups, and projectiles
		player.update();
		currentSprite = anim.getImage();

		// Updates player projectiles
		ArrayList projectiles = player.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = (Projectile) projectiles.get(i);
			if (p.isVisible() == true)
				p.update(false);
			else 
				projectiles.remove(i);
		}
		
		// Updates power-ups if any exist
		ArrayList<PowerUp> localpowerups = new ArrayList<PowerUp>(powerups);
		for (PowerUp i:localpowerups) {
			if (i.isVisible() == true)
				i.update();
			else 
				powerups.remove(i);
		}
		
		// Updates power-ups if any exist
		ArrayList<Explosion> localexplosions = new ArrayList<Explosion>(explosions);
		for (Explosion exp:localexplosions) {
			if (exp.isVisible())
				exp.update();
			else 
				explosions.remove(exp);
		}
		
		// Generates new enemies randomly on the map, if a Boss Battle isn't currently going on
		if(!bossBattle) {
			
			// Only generates new enemies if the player is not regenerating
			if(player.isDying == 0) {
				newEnemy--;
				
				// The next few lines check if a boss battle is occurring
				if(enemies.size() != 0)
					if(enemies.get(0) instanceof Doppleganger)
						newEnemy++;
				
				// Generates new enemies based on percentages for now 50% Grunt, 30% Drone, 20% LaserGuy
				if(newEnemy <= 0) {
					int type = (int) (Math.random() * 100);
					if(type < 30)
						enemies.add(new Grunt((int)((Math.random() * (500)) + 50), 0));
					else if (type < 50)
						enemies.add(new Drone((int)((Math.random() * (500)) + 50), 0));
					else if(type < 70)
						enemies.add(new LaserGuy((int)((Math.random() * (500)) + 50), 0));
					else if(type < 80)
						enemies.add(new Mine((int)((Math.random() * (500)) + 50), 0));
					else if(type < 90)
						enemies.add(new LineShooter((int)((Math.random() * (450)) + 50), 0));
					else
						specialForm(type);
					
					
					// newEnemy is a counter that is checked when trying to make a new Enemy. If the count
					// is zero, than a new enemy is generated and it is set equal to newEnemyTimer, which
					// decreases to increase the spawn rate of enemies. It is capped at 20 for now
					newEnemy = newEnemyTimer;
					if(--newEnemyTimer <= 20)
						newEnemyTimer = 20;
				}
			}
		}
		
		// Boss battles start every 500 points
		if(score % 500 < 20 && score > 500) 
			bossBattle = true;
		
		// Creates the boss only after the screen is void of enemies
		if(enemies.isEmpty() && bossBattle) {
			enemies.add(new Doppleganger());
			bossBattle = false;
		}
		
		// Handles updating enemies
		ArrayList<Enemy> localEnemies = new ArrayList<Enemy>(enemies);
		for(Enemy enemy:localEnemies) {
			
			// Updates enemy projectiles
			ArrayList enemyprojectiles = enemy.getProjectiles();
			for (int i = 0; i < enemyprojectiles.size(); i++) {
				
				Projectile p = (Projectile) enemyprojectiles.get(i);

				if (p.isVisible() == true)
					p.update(true);
				else
					enemyprojectiles.remove(i);
			}
			
			if(enemy.health > 0 || (enemy instanceof Mine && enemy.health == 0))
				enemy.update();
			else {
				// If the enemy dies, a power up has a chance of appearing based on the percentages below
				if(enemy.health == 0){
					updateScore(enemy);
					enemy.health--;
					if(Math.random()*100 < 2)
						powerups.add(new PowerUp(enemy.getCenterX(), enemy.getCenterY(), 1));
					else if (Math.random()*100 < 2 && livesLeft < 3)
						powerups.add(new PowerUp(enemy.getCenterX(), enemy.getCenterY(), 2));
					else if (Math.random()*100 < 2)
						powerups.add(new PowerUp(enemy.getCenterX(), enemy.getCenterY(), 3));
					
					explosions.add(new Explosion(enemy.getCenterX(), enemy.getCenterY(), enemy));
				}
				
				// The enemy is only removed once all of it's projectiles are no longer visible
				// If not, then the enemy is placed off screen and has no movement or shooting,
				// where it remains until it's bullets move off screen
				if(enemy.getProjectiles().isEmpty()) 
					enemies.remove(enemy);
				else 
					enemy.die();

			}
			
		}
		
		// Updating the backgrounds
		bg1.update();
		bg2.update();
		
		animate();

		// Handles the regeneration display
		if(player.isDying > 0) {
			player.isDying--;
			currentSprite = deathanim.getImage();
		}
	}
	
	private void specialForm(int type) {
		
		switch(type) {
			case 90:
				for(int i = 1; i <= 3; i++){
					LineShooter ls = new LineShooter(150 * i, 0);
					ls.firerate = 50;
					enemies.add(ls);
				}
				break;
			case 91:
				for(int i = 1; i <= 3; i++){
					LineShooter ls = new LineShooter(150 * i, 0);
					ls.firerate = 50;
					enemies.add(ls);
				}
				break;
			case 92:
				for(int i = 1; i <= 3; i++){
					LineShooter ls = new LineShooter(150 * i, 0);
					ls.firerate = 50;
					enemies.add(ls);
				}
				break;
			case 93:
				for(int i = 1; i <= 3; i++){
					LineShooter ls = new LineShooter(150 * i, 0);
					ls.firerate = 50;
					enemies.add(ls);
				}
				break;
			case 94:
				for(int i = 1; i <= 3; i++){
					LineShooter ls = new LineShooter(150 * i, 0);
					ls.firerate = 50;
					enemies.add(ls);
				}
				break;
			case 95:
				for(int i = 1; i <= 3; i++){
					LineShooter ls = new LineShooter(150 * i, 0);
					ls.firerate = 50;
					enemies.add(ls);
				}
				break;
			case 96:
				for(int i = 1; i <= 6; i++)
					enemies.add(new Drone(99 * i, 0));
				break;
			case 97:
				for(int i = 1; i <= 6; i++)
					enemies.add(new Drone(99 * i, 0));
				break;
			case 98:
				for(int i = 1; i <= 6; i++)
					enemies.add(new Drone(99 * i, 0));
				break;
			case 99:
				for(int i = 1; i <= 6; i++)
					enemies.add(new Drone(99 * i, 0));
				break;
		}
		
	}
	
	// Takens in a touch event, and sees if it is within the bounds given by the other 4 variables
	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}
	
	// Update the score based on the enemy killed
	private void updateScore(Enemy e) {
		
		if(e instanceof Drone)
			score += 5;
		if(e instanceof Grunt)
			score += 10;
		if(e instanceof LaserGuy || e instanceof Mine)
			score += 20;
		if(e instanceof LineShooter)
			score += 30;
		if(e instanceof Doppleganger)
			score += 50;
		
	}
	
	// If the player touches the bomb icon while having a bomb, this method is called..
	// It clears all enemies off the screen and adjusts the score based on each enemy, unless
	// it is a boss battle. Bombs cannot defeat bosses
	public void bomb() {
		
		ArrayList<Enemy> localEnemies = new ArrayList<Enemy>(enemies);
		for(Enemy enemy:localEnemies) {
			if(enemy instanceof Doppleganger)
				continue;
			enemies.remove(enemy);
			updateScore(enemy);
		}
		
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 600, 800, Color.WHITE);
		
	}

	// When the game is paused, this method will be called instead of the update method above
	private void updatePaused(List<TouchEvent> touchEvents) {
		
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (inBounds(event, 100, 200, 400, 200)) {
					resume();
				}

				if (inBounds(event, 100, 400, 400, 200)) {
					nullify();
					music.stop();
					goToMenu();
				}
				
				if(inBounds(event, 495, 0, 50, 50)) {
				
					if(playMusic) {
						playMusic = false;
						music.stop();
					}
					else {
						music.play();
						playMusic = true;
					}
					game.setAudio();
				}
				if(inBounds(event, 550, 0, 50, 50)) {
					playSound = !playSound;
					game.setAudio();
				}
			}
		}
	}

	// If the user has run out of lives, this update method is called
	private void updateGameOver(List<TouchEvent> touchEvents) {
		
		// Update High Score
		if(updateScore) {
			game.setHighScores(score);
			updateScore = false;
		}
		
		music.stop();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (inBounds(event, 200, 400, 200, 200)) {
					
					// Resets all variables and goes back to the Main Menu
					nullify();
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}
	}

	// Paint is called every iteration of the game, whether it is paused or at game over. This handles painting
	// the objects to the display and updating their positions
	@Override
	public void paint(float deltaTime) {
		
		Graphics g = game.getGraphics();

		// Draws the backgrounds
		g.drawImage(Assets.background, bg1.getBgX(), bg1.getBgY());
		g.drawImage(Assets.background, bg2.getBgX(), bg2.getBgY());
		
		// Draws the score in the top right corner of the screen
		paint.setTextAlign(Paint.Align.RIGHT);
		g.drawString(Integer.toString(score), 600, 30, paint);
		paint.setTextAlign(Paint.Align.CENTER);

		// Draws the players bullets
		ArrayList projectiles = player.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = (Projectile) projectiles.get(i);
			g.drawImage(Assets.laser, p.getX() - Assets.laser.getWidth()/2, p.getY() - Assets.laser.getHeight()/2);
		}
		
		// Draws the powerups
		for (PowerUp i:powerups)
			g.drawImage(i.getTypeImage(), i.getX(), i.getY());
		
		for (Explosion exp: explosions)
			g.drawImage(exp.image, exp.getCenterX(), exp.getCenterY());
		
		
		// This check verifies that the player isn't dying, and sets the banking of the ship
		// according to its y velocity
		if(player.isDying == 0)
			getShipBank();
		
		// Draws the player. currentSprite is the current display of player. If the player is dying, then
		// deathanim is the current sprite. If the player is moving laterally, then getShipBank()
		// above determines how far the ship is tilted to the right or left
		g.drawImage(currentSprite, player.getCenterX() - currentSprite.getWidth()/2, player.getCenterY() - currentSprite.getHeight()/2);
		
		// Draws the enemies
		for(Enemy enemy: enemies) {
			g.drawImage(enemy.getImage(), enemy.getCenterX() - (enemy.getImage().getWidth()/2), enemy.getCenterY() - (enemy.getImage().getHeight()/2));
			
			ArrayList<Projectile> eproj = enemy.getProjectiles();
			for(Projectile ep : eproj){
				
				if(enemy instanceof LineShooter)
					g.drawImage(Assets.lsLas, ep.getX() - 16, ep.getY());
				else if(enemy instanceof Mine)
					g.drawImage(Assets.mSpike, ep.getX() - 10, ep.getY());
				else if(enemy instanceof Doppleganger)
					g.drawImage(Assets.lsLas, ep.getX() - 16, ep.getY());
				else
					g.drawImage(Assets.elaser, ep.getX() - 16, ep.getY() - 6);
			}					
			
			// If there is a boss fight, then the drawBossUI() method is called to update the display
			if(bossBattle)
				drawBossUI();
		}
		
		
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();

	}

	public void animate() {
		anim.update(10);
		deathanim.update(20);
	}

	private void nullify() {

		// Set all variables to null. You will be recreating them in the
		// constructor.
		paint = null;
		bg1 = null;
		bg2 = null;
		player = null;
		enemies = new ArrayList<Enemy>();
		powerups = new ArrayList<PowerUp>();
		currentSprite = null;
		ship = null;
		shipr = null;
		anim = null;
		deathanim = null;
		newEnemy = 0;
		newEnemyTimer = 180;
		
		score = 0;

		// Call garbage collector to clean up memory.
		System.gc();

	}

	// If a boss battle is going on, the health of the enemy is displayed at the top of the screen
	private void drawBossUI() {
		
		Graphics g = game.getGraphics();
		Enemy e = enemies.get(0);
		
		if(e.health > 0)
			g.drawRect(100, 40, (e.health * 4), 10, Color.GREEN);
		else {
			bossBattle = false;
			Twarn = 0;
			warningCount = 0;
		}
		
		if(warningCount < 10 && Twarn < 3)
			g.drawImage(Assets.warning, 0, 600);
		
		if(warningCount >= 20){
			warningCount = 0;
			Twarn++;
		}
		
		warningCount++;
	}
	
	// Displays the Tap to Start at the beginning 
	private void drawReadyUI() {
		Graphics g = game.getGraphics();

		// Darkens the screen so the text can be seen easier
		g.drawARGB(155, 0, 0, 0);
		paint.setTextAlign(Paint.Align.CENTER);
		g.drawString("Tap to Start.", 300, 400, paint);
		paint.setTextAlign(Paint.Align.RIGHT);
	}

	// Displays the user interface when the game is running. This includes lives left, bombs, and the pause button
	private void drawRunningUI() {
		
		Graphics g = game.getGraphics();
		g.drawImage(Assets.pause, 0, 0);
		
		if(bombs > 0) {
			g.drawImage(Assets.bombia, 550, 200);
			String bombCount = bombs + "";
			paint.setTextAlign(Paint.Align.CENTER);
			paint.setTextSize(10);
			g.drawString(bombCount, 575, 234, paint);
			paint.setTextAlign(Paint.Align.RIGHT);
			paint.setTextSize(30);
		}
		else
			g.drawImage(Assets.bombii, 550, 200);
		
		paint.setTextAlign(Paint.Align.RIGHT);
		g.drawString("Lives:", 240, 30, paint);
		paint.setTextAlign(Paint.Align.CENTER);
		
		for(int i = 0; i < livesLeft; i++)
			g.drawImage(Assets.lives, 240+(Assets.lives.getWidth()*i), 0);

	}
	
	// This gets the lateral velocity of the ship and assigns a bank image based on the magnitude and direction of
	// the velocity
	private void getShipBank() {
		
		// turnValue is the lateral velocity of the ship
		if(turnValue < -3)
			currentSprite = Assets.shipL3;
		else if(turnValue < -1)
			currentSprite = Assets.shipL2;
		else if(turnValue < 0)
			currentSprite = Assets.shipL1;
		else if(turnValue == 0)
			currentSprite = Assets.ship;
		else if(turnValue > 0 && turnValue <= 1)
			currentSprite = Assets.shipR1;
		else if(turnValue > 1 && turnValue <= 3)
			currentSprite = Assets.shipR2;
		else if(turnValue > 3)
			currentSprite = Assets.shipR3;
		
		// Resets the value at the end so that if the user stops suddenly, the ship isn't stuck banking
		// to a certain way
		turnValue = 0;

	}
	
	private void drawPausedUI() {
		
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Resume", 300, 300, paint2);
		g.drawString("Menu", 300, 500, paint2);
		
		if(playMusic)
			g.drawImage(Assets.musicon, 495, 0);
		else
			g.drawImage(Assets.musicoff, 495, 0);
		
		if(playSound)
			g.drawImage(Assets.soundon, 550, 0);
		else
			g.drawImage(Assets.soundoff, 550, 0);

	}

	private void drawGameOverUI() {
		
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 610, 810, Color.BLACK);
		g.drawString("GAME OVER", 300, 300, paint2);
		paint.setTextAlign(Paint.Align.CENTER);
		g.drawString("Tap to return.", 300, 450, paint);
		String finalScore = "Your final score was: " + score;
		g.drawString(finalScore, 300, 500, paint);
		paint.setTextAlign(Paint.Align.RIGHT);
		

	}

	@Override
	public void pause() {
		if (state == GameState.Running)
			state = GameState.Paused;
		
		music.setVolume(0.1f);

	}

	@Override
	public void resume() {
		if (state == GameState.Paused)
			state = GameState.Running;
		music.setVolume(0.3f);
	}

	@Override
	public void dispose() {
	}

	// If the back button is hit on the Android device, the game is paused
	@Override
	public void backButton() {
		pause();
	}
	

	private void goToMenu() {
		// TODO Auto-generated method stub
		game.setScreen(new MainMenuScreen(game));

	}

	public static Background getBg1() {
		// TODO Auto-generated method stub
		return bg1;
	}

	public static Background getBg2() {
		// TODO Auto-generated method stub
		return bg2;
	}

	public static Player getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}

}