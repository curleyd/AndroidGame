package com.spaceshooter.game;

import android.graphics.Rect;

public class Doppleganger extends Enemy {
	
	boolean created, isMoving;
	
	public Doppleganger() {
		
		centerX = 300;
		centerY = -150;
		
		created = true;
		isMoving = false;
		
		speedY = 2;
		speedX = 2;
		health = 100;
		firerate = 15;
		
		image = Assets.doppleganger;
		r = new Rect(0, 0, 0, 0);
		
	}

	@Override
	public void update() {

		if(created) {
			if(centerY <= 150)
				centerY += (speedY + bg.getSpeedY());
			else
				created = false;
		}
		else {
			if(!isMoving) {
				speedX = (int) (Math.random() * 7) - 3;
				if(speedX == 0)
					speedX++;
				speedY = (int) (Math.random() * 7) - 3;
				if(speedY == 0)
					speedY++;
				isMoving = true;
			}
			else {
				
				if(centerX + speedX >= 500 || centerX + speedX <= 100)
					speedX = -speedX;
				if(centerY + speedY >= 200 || centerY + speedY <= 0)
					speedY = -speedY;
				
				centerX += speedX;
				centerY += speedY;
			}
		}
		
		if(centerY >= 25)
			r.set(centerX - 170, centerY - 50, centerX + 170, centerY + 80);

		if (Rect.intersects(r, Player.yellowRed))
			checkCollision();
		
		if (player.isDying == 0) {
			firerate--;
			if(firerate <= 0) {
				firerate = 15;
				this.shoot();
			}
		}

	}

	@Override
	public void follow() {

	}

	@Override
	public void shoot() {

		Projectile p = new Projectile(centerX, centerY+114);
		Projectile p1 = new Projectile(centerX, centerY+114);
		Projectile p2 = new Projectile(centerX, centerY+114);
		p.setSpeedY(-7);
		projectiles.add(p);
		p1.setSpeedX(-3);
		p1.setSpeedY(-7);
		projectiles.add(p1);
		p2.setSpeedX(3);
		p2.setSpeedY(-7);
		projectiles.add(p2);
		
	}

}
