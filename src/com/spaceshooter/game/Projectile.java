package com.spaceshooter.game;

import android.graphics.Rect;


public class Projectile {

	// Variable declarations
	private int x, y, speedY, speedX;
	private boolean visible;
	
	private Rect r;
	
	public Projectile(int startX, int startY){
		
		x = startX;
		y = startY;
		speedY = 7;
		speedX = 0;
		visible = true;
		
		r = new Rect(0, 0, 0, 0);
	}
	
	// Updates the movement of the projectile
	// isEnemy exists so that the collision detection will know what to check collisions against,
	// preventing enemies from shooting each other or the player running into it's own bullets
	public void update(boolean isEnemy){
		
		// Modifies the location of the projectile
		y -= speedY;
		x -= speedX;
		
		// Sets the rectangle that is used to check collisions
		r.set(x, y, x+5, y+10);
		if (y < 0 || y > 800){
			visible = false;
			r = null;
		}
		
		if (visible)
			checkCollision(isEnemy);
		
		
	}

	// Is called for checking collisions. isEnemy behaves as described above in it's call
	private void checkCollision(boolean isEnemy) {
		
		// If the shooter is NOT an enemy, then it checks collision against enemies
		if(!isEnemy) {
			for(Enemy hb: GameScreen.enemies) {
				if(Rect.intersects(r,hb.r)) {
					visible = false;
					hb.health -= 1;
				}
			}
		}
		else {
			Player p = GameScreen.getPlayer();
			// isDying is used here to save the player from colliding with a bullet if the player
			// is regenerating, meaning they were killed but have lives left.
			if(p.isDying == 0) {
				
				if(Rect.intersects(r, Player.yellowRed)) {
					if (Rect.intersects(r, Player.rect) || Rect.intersects(r, Player.rect2)) {
						GameScreen.livesLeft--;
						p.isDying = 100;
						visible = false;
					}
				}
			}
		}
			
	}


	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getspeedY() {
		return speedY;
	}
	
	public int getspeedX() {
		return speedX;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
	
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Rect getR() {
		return r;
	}

	public void setR(Rect r) {
		this.r = r;
	}
	
	
	
	
}
