package com.spaceshooter.game;

import android.graphics.Rect;

public class Drone extends Enemy {

	public Drone(int centerX, int centerY) {
		
		setCenterX(centerX);
		setCenterY(centerY);
		
		speedY = 4;
		speedX = 4;
		health = 1;
		
		image = Assets.drone;
		r = new Rect(0, 0, 0, 0);
	}
	
	@Override
	public void update() {
		
		if(player.isDying == 0)
			follow();
		
		if(centerY >= 25)
			r.set(centerX - 20, centerY - 20, centerX + 20, centerY + 20);

		if (Rect.intersects(r, Player.yellowRed))
			checkCollision();
		
		if(centerY - 25 >= 800) {
			health = 0;
			GameScreen.score -= 10;
		}

	}

	@Override
	public void follow() {
		
		int i = player.getCenterX() - centerX;
		int j = player.getCenterY() - centerY;
		
		double distance = Math.sqrt((i*i) + (j*j));
		double unitX = i / distance;
		double unitY = j / distance;
		
		
		speedX = (int) (unitX * 8);
		speedY = (int) (unitY * 8);
		
		centerX += speedX;
		centerY += speedY;

	}

	@Override
	public void shoot() {
		
	}

}