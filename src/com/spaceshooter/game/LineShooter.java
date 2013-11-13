package com.spaceshooter.game;

import android.graphics.Rect;

public class LineShooter extends Enemy {
	
	private boolean isShooting = false;
	private int fireCount = -75, shotsFired = 0;
	
	public LineShooter(int x, int y){
		
		centerX = x;
		centerY = y;
		
		speedY = 2;
		speedX = 1;
		
		firerate = 200;
		health = 10;
		
		image = Assets.lineshooter;
		
		r = new Rect(0,0,0,0);
		
	}
	
		@Override
		public void update() {
					
			if(this.centerY <= 100){
				centerY += speedY;
				r.set(centerX -53 , centerY-64, centerX + 46, centerY + 64);
			}
			
			else{
					
				r.set(centerX -53 , centerY-64, centerX + 46, centerY + 64);
				if(!isShooting){
					centerX += speedX;
					
					firerate--;
					
					if (centerX + speedX >= 500 || centerX + speedX <= 0)
						speedX = -speedX;
						
					if(firerate == 0){
						
						if(GameScreen.playSound)
							Assets.LSlasCharge.play(1.0f);
						
						isShooting = true;
						firerate = 200;
					}
				}
				
				else {	
					
					if(player.isDying == 0) {
					
						fireCount++;					
						if(fireCount == 8){		
								
							this.shoot();
							
							if(GameScreen.playSound)
								Assets.LSshoot.play(1.0f);
							
							shotsFired++;
							fireCount = 0;
							
							if(shotsFired == 20){
								isShooting = false;
								shotsFired = 0;
								fireCount = -75;
							}
						}
					}
				}
			}
					
			if(Rect.intersects(r, player.yellowRed))
				checkCollision();				
		}
	
		@Override
		public void shoot() {
				Projectile las = new Projectile(centerX, centerY + 64);
				las.setWidth(15);
				las.setHeight(10);
				las.setSpeedY(-12);
				projectiles.add(las);	
		}
		
		

}
