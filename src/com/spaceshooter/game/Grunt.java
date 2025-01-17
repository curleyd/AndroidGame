package com.spaceshooter.game;

import android.graphics.Rect;

public class Grunt extends Enemy {

	public Grunt(int centerX, int centerY) {

		setCenterX(centerX);
		setCenterY(centerY);
		
		speedY = 1;
		speedX = 0;
		firerate = 30;
		health = 1;
		
		image = Assets.grunt;
		r = new Rect(0, 0, 0, 0);
		

	}

	@Override
	public void update() {
		
		if(player.isDying == 0)
			centerY += (speedY + bg.getSpeedY());
		
		if(centerY >= 25)
			r.set(centerX - 30, centerY - 15, centerX + 30, centerY + 20);

		if (Rect.intersects(r, Player.yellowRed))
			checkCollision();
		
		if (player.isDying == 0) {
			firerate--;
			if(firerate <= 0) {
				firerate = (int) (Math.random() * 30) + 20;
				this.shoot();
			}
		}
		
		if(centerY - 25 >= 800) 
			GameScreen.enemies.remove(this);
	}

	@Override
	public void shoot() {
		
		if(health > 0) {
			Projectile p = new Projectile(centerX, centerY+25);
			p.setSpeedY(-8);
			projectiles.add(p);
		}
		
	}

}
