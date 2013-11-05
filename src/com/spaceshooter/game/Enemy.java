/*
 * Enemy.java
 * 
 * Enemy is an abstract class that is used to describe all enemies. Three methods that every Enemy will have
 * to implement are update, follow, and shoot, since each of those will vary depending on the type. The main
 * reason to have Enemy as an abstract class is so that the GameScreen class doesn't have to have an ArrayList
 * for each type of Enemy, but rather one for the whole group
 */

package com.spaceshooter.game;

import java.util.ArrayList;

import com.spaceshooter.framework.Image;

import android.graphics.Rect;


public abstract class Enemy {

	// Variable declarations
	protected int centerX, speedY, speedX, centerY;
	protected Background bg = GameScreen.getBg1();
	protected Player player = GameScreen.getPlayer();
	protected Image image;

	protected Rect r;
	
	// firerate - the speed at which the enemy will fire. ***Lower Number = Faster Shooting***
	public int health, firerate;
	
	protected ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	// Behavioral Methods
	public abstract void update();

	public void checkCollision() {
		
		if(player.isDying == 0) {
			// Checks the y value so that if possible, the device saves some memory from executing the 
			// Rect.intersects methods
			if(centerY + 100 > GameScreen.getPlayer().getCenterY() - 40 && centerY - 100 < GameScreen.getPlayer().getCenterY() + 40) {
				if (Rect.intersects(r, Player.rect)|| Rect.intersects(r, Player.rect2)) {
					GameScreen.livesLeft--;
					health--;
				}
			}
		}
	}

	public abstract void follow();

	// Places the enemy off screen, gives him no movement, and moves the collision rectangle out of the
	// the way so that other projectiles don't run into it
	public void die() {
			
		centerX = -600;
		centerY = -600;
		speedY = 0;
		speedX = 0;
		
		r.set(centerX - 25, centerY - 25, centerX + 25, centerY + 35);
		
	}

	public abstract void shoot(); 

	public int getSpeedY() {
		return speedY;
	}

	public int getCenterX() {
		return centerX;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(ArrayList<Projectile> projectiles) {
		this.projectiles = projectiles;
	}

	public int getCenterY() {
		return centerY;
	}

	public Background getBg() {
		return bg;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setBg(Background bg) {
		this.bg = bg;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}