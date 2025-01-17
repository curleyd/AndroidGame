/*
 * Player.java
 * 
 * This is where the player and most of his interactions are handled. A lot of the variables below control
 * large aspects of the game, so look to them. Tweaking them will make the gameplay change, so look to doing so
 * to get the best feel of it.
 */

package com.spaceshooter.game;


import java.util.ArrayList;

import android.graphics.Rect;

public class Player {

	// Variable declarations
	private int centerX = 300, centerY = 800-22;
	private int eventx = centerX, eventy = centerY;


	// health - one hit and you die, shouldn't be changed
	// triShotCounter - the duration of the triShot PowerUp, currently lasts for 50 bullets at the normal firerate
	// isDying - set to 100 when the user dies and has lives left, this keeps the user invicible to damage for
	// 			 a short amount of time
	public int health = 1, triShotCounter = 50, isDying = 0;
	private int firerate = 13;
	
	// triShot - whether or not the triShot PowerUp is currently active
	public boolean triShot = false;
	
	
	public static Rect rect = new Rect(0, 0, 0, 0);
	public static Rect rect2 = new Rect(0, 0, 0, 0);
	public static Rect yellowRed = new Rect(0, 0, 0, 0);
	
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public void update() {
		
		// This handles shooting. If the player is about to respawn, then they are allowed to shoot a little
		// bit before the enemies move. This also checks if the player has triShot active, and calls the
		// appropriate shoot method if so
		if(isDying <= 30) { 
			firerate--;
			if(firerate <= 0) {
				firerate = 13;
				if(!triShot)
					this.shoot();
				else {
					this.triShot();
					triShotCounter--;
					if(triShotCounter == 0)
						triShot = false;
				}
					
			}
		}
		
		// Updates movement
		move();
		
		// Sets up the rectangles for collision detection
		rect.set(centerX - 35, centerY - 10, centerX + 35, centerY + 17);
		rect2.set(centerX - 8, centerY - 22, centerX + 8, centerY + 14);
		yellowRed.set(centerX - 40, centerY - 26, centerX + 40, centerY + 26);

	}

	public void shoot() {
		
		// Creates a new projectile, sets its Y speed at 15, and adds it to the ArrayList
		Projectile p = new Projectile(centerX, centerY-22);
		p.setSpeedY(13);
		projectiles.add(p);
		
		if(GameScreen.playSound)
			Assets.shoot.play(0.1f);
		
	}
	
	// Shoots 3 bullets in a spread instead of 1. This is done by giving two of the projectiles X
	// velocities in the opposite directions, so that the three bullets fork
	public void triShot() {
		
		Projectile p = new Projectile(centerX, centerY-22);
		Projectile p1 = new Projectile(centerX, centerY-22);
		Projectile p2 = new Projectile(centerX, centerY-22);
		p.setSpeedY(13);
		projectiles.add(p);
		p1.setSpeedY(13);
		p1.setSpeedX(-2);
		projectiles.add(p1);
		p2.setSpeedY(13);
		p2.setSpeedX(2);
		projectiles.add(p2);
		
		if(GameScreen.playSound)
			Assets.shoot.play(0.1f);
		
	}
	
	// Moves the ship
	public void move() {
		
		// Gets the i and j components of the vector from the location of the touch to the center of the ship
		int i = eventx - centerX;
		int j = eventy - centerY;
		
		// Calculates the magnitude of the vector and then the unit vector
		double distance = Math.sqrt((i*i) + (j*j));
		double unitX = i / distance;
		double unitY = j / distance;
		
		// Eachcomponent of the unit vector is then multiplied by the movespeed to get the 
		// movement in the X direction.
		int moveX = (int) (unitX * 8);
		int moveY = (int) (unitY * 8);
		
		// If the moveSpeed is too high, the ship will overshoot the touch event, resulting in 
		// erratic ship behavior. The for loops check if the movement speed would overshoot the event,
		// and decrease the factor that the unit vector is being multiplied by, which is essentially
		// how far the ship will travel.
		if(moveX > 0) {
			for(int a = 7; a >= 0; a--) {
				if(centerX + moveX < eventx)
					break;
				moveX = (int) (unitX * a);
			}
		} 
		else {
			for(int a = 7; a >= 0; a--) {
				if(centerX + moveX > eventx)
					break;
				moveX = (int) (unitX * a);
			}
		}
		
		if(moveY > 0) {
			for(int a = 7; a >= 0; a--) {
				if(centerY + moveY < eventy)
					break;
				moveY = (int) (unitY * a);
			}
		} 
		else {
			for(int a = 7; a >= 0; a--) {
				if(centerY + moveY > eventy)
					break;
				moveY = (int) (unitY * a);
			}
		}
		
		// Moving the ship by the movespeed of each component
		centerX += moveX;
		centerY += moveY;
		
		// Sets the turnValue to the moveX value so that the correct ship bank image is displayed
		GameScreen.turnValue = moveX;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public ArrayList getProjectiles() {
		return projectiles;
	}

	public int getEventx() {
		return eventx;
	}

	public int getEventy() {
		return eventy;
	}

	public void setEventx(int eventx) {
		this.eventx = eventx;
	}

	public void setEventy(int eventy) {
		this.eventy = eventy;
	}
	
	

}
