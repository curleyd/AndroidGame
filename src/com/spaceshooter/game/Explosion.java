package com.spaceshooter.game;

import com.spaceshooter.framework.Image;

public class Explosion {
	
	private int centerX, centerY, timer, updateTime;
	private Animation expanim;
	private Enemy e;
	public Image image;
	private boolean visible;
	
	public Explosion(int centerX, int centerY, Enemy e) {
		
		this.centerX = centerX-10;
		this.centerY = centerY-10;
		this.e = e;
		
		timer = 15;
		
		expanim = new Animation();
		
		getExplosionSize();
		visible = true;
		
		updateTime = 33;
		
		image = expanim.getImage();
		
		if(GameScreen.playSound)
			Assets.explode.play(0.15f);
	}
	
	private void getExplosionSize() {
		
		if(e instanceof Drone) {
			expanim.addFrame(Assets.explosion1, 100);
			expanim.addFrame(Assets.explosion1, 100);
			expanim.addFrame(Assets.explosion1, 100);
			expanim.addFrame(Assets.explosion2, 100);
			expanim.addFrame(Assets.explosion2, 100);
		}
		if(e instanceof Grunt) {
			expanim.addFrame(Assets.explosion1, 100);
			expanim.addFrame(Assets.explosion2, 100);
			expanim.addFrame(Assets.explosion2, 100);
			expanim.addFrame(Assets.explosion3, 100);
			expanim.addFrame(Assets.explosion3, 100);
		}
		if(e instanceof LaserGuy || e instanceof Mine) {
			expanim.addFrame(Assets.explosion1, 100);
			expanim.addFrame(Assets.explosion2, 100);
			expanim.addFrame(Assets.explosion3, 100);
			expanim.addFrame(Assets.explosion4, 100);
			expanim.addFrame(Assets.explosion4, 100);
		}
		if(e instanceof Doppleganger || e instanceof LineShooter) {
			expanim.addFrame(Assets.explosion1, 100);
			expanim.addFrame(Assets.explosion2, 100);
			expanim.addFrame(Assets.explosion3, 100);
			expanim.addFrame(Assets.explosion4, 100);
			expanim.addFrame(Assets.explosion5, 100);
		}
		else
			updateTime = 6;
		
	}
	
	public void update() {
		
		int curImageWidth = image.getWidth();
		timer--;
		
		if(timer <= 0)
			visible = false;
		
		expanim.update(updateTime);
		image = expanim.getImage();
		
		if(curImageWidth != image.getWidth()) {
			centerX = centerX - 5;
			centerY = centerY - 5;
		}
		
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getTimer() {
		return timer;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	


	
	

}
