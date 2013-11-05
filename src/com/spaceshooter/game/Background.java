package com.spaceshooter.game;


public class Background {
	
	private int bgX, bgY, speedY;
	
	public Background(int x, int y){
		bgX = x;
		bgY = y;
		speedY = 1;
	}
	
	// THIS UPDATED ON GITHUB, AND AGAIN
	
	public void update() {
		
		// Scrolls the background down, so it looks like the player is moving
		bgY += speedY;

		// Once the background has moved far enough off screen, it is reset at the top
		if (bgY >= 800) 
			bgY -= 1600;
		
	}

	public int getBgX() {
		return bgX;
	}

	public int getBgY() {
		return bgY;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setBgX(int bgX) {
		this.bgX = bgX;
	}

	public void setBgY(int bgY) {
		this.bgY = bgY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	
	
	
}
