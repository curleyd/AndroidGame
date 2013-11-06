package com.spaceshooter.game;

import android.graphics.Rect;

import com.spaceshooter.framework.Image;

public class PowerUp {
	
	// Variable declarations
	private int x, y, speedY, type;
	private boolean visible;
	
	private Image typeImage;
	
	private Rect r;
	
	public PowerUp(int startX, int startY, int type){
		
		x = startX;
		y = startY;
		this.type = type;
		speedY = 3;
		
		visible = true;
		
		// Switch statement to get the correct type of image for the PowerUp
		switch(this.type) {
			case 1:
				typeImage = Assets.trishot;
				break;
			case 2:
				typeImage = Assets.oneplus;
				break;
			case 3:
				typeImage = Assets.bomb;
				break;
		}
		
		r = new Rect(0, 0, 0, 0);
	}
	
	// Behaves almost exactly like a projectile
	public void update(){
		
		// Increases the yCoordinate of the PowerUp, moving it downscreen towards the player
		y += speedY;
		
		// Setting the rectangle that will be used for collision detection
		r.set(x-15, y-15, x+15, y+15);

		// Checks if the PowerUp has moved off screen
		if (y > 800){
			visible = false;
			r = null;
		}
		if (visible){
			checkCollision();
		}
		
	}
	
	// Checks collision between the player and the PowerUp
	private void checkCollision() {
		
		if (Rect.intersects(r, Player.rect)|| Rect.intersects(r, Player.rect2)) {
			
			visible = false;
			
			// Depending on the PowerUp, certain properties are modified. Pretty 
			// self-explanatory by the variable names
			switch(this.type) {
				case 1:
					GameScreen.getPlayer().triShot = true;
					GameScreen.getPlayer().triShotCounter = 50;
					break;
				case 2:
					if(GameScreen.livesLeft < 5)
						GameScreen.livesLeft++;
					break;
				case 3:
					GameScreen.bombs++;
					break;
			}
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSpeedY() {
		return speedY;
	}

	public boolean isVisible() {
		return visible;
	}

	public Image getTypeImage() {
		return typeImage;
	}

	public void setTypeImage(Image typeImage) {
		this.typeImage = typeImage;
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

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	

}
