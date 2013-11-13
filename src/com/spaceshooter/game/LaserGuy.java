package com.spaceshooter.game;

import android.graphics.Rect;

public class LaserGuy extends Enemy {
	
	boolean isShooting = false;
	int shooting = 200;

	public LaserGuy(int centerX, int centerY) {
		
		setCenterX(centerX);
		setCenterY(centerY);
		
		speedY = 1;
		speedX = 1;
		health = 3;
		firerate = 100;
		
		image = Assets.laserguy;
		r = new Rect(0, 0, 0, 0);
		
	}
	
	@Override
	public void update() {
		
		if (player.isDying == 0) {
			if(centerY <= 100)
				centerY += (speedY + bg.getSpeedY());
			else
				if(!isShooting)
					centerX += speedX;
			
			if(centerY >= 25)
				r.set(centerX - 30, centerY - 20, centerX + 30, centerY + 25);
		
			if (Rect.intersects(r, Player.yellowRed))
				checkCollision();
			
			firerate--;
			if(firerate <= 0) {
				shooting--;
				if(shooting != 0) {
					this.shoot();
					isShooting = true;
				}
				else {
					firerate = 100;
					shooting = 100;
					isShooting = false;
				}
			}
		}
		
		if(centerY - 40 >= 800) {
			health = 0;
			GameScreen.score -= 10;
		}
		
		if (centerX + speedX >= 600 || centerX + speedX <= 0)
			speedX = -speedX;

	}

	@Override
	public void shoot() {
		
		if(health > 0) {
			Projectile p = new Projectile(centerX, centerY+35);
			p.setSpeedY(-10);
			projectiles.add(p);
		}
		
	}

}
