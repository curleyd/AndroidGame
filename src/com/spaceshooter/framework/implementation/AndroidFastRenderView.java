package com.spaceshooter.framework.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable {
	
	AndroidGame game;
	Bitmap framebuffer;
	Thread renderThread = null;
	SurfaceHolder holder;
	volatile boolean running = false;
	
	//Used to make FPS steady
		private final int TicksPerSecond = 60;
		private final int SkipTicks = 1000 / TicksPerSecond;
		private final int MaxFrameSkip = 10;	
		private int loops;		
		private long NextGameTick = System.currentTimeMillis();
	
		public AndroidFastRenderView(AndroidGame game, Bitmap framebuffer){
			super(game);
			this.game = game;
			this.framebuffer = framebuffer;
			this.holder = getHolder();
		}
		
		public void resume(){
			running = true;
			renderThread = new Thread(this);
			renderThread.start();
		}

		public void run() {
			
			Rect dstRect = new Rect();
			long startTime = System.nanoTime();
			
				while(running){
					
					if(!holder.getSurface().isValid())
						continue;
					
					float deltaTime = (System.nanoTime() - startTime) / 10000000.000f;
					startTime = System.nanoTime();
					
					if(deltaTime > 3.15)
						deltaTime = (float) 3.15;
				
				//This code around the update call steadys the FPS to whatever you have TicksPerSecond set to.
					loops = 0;
					
					while(System.currentTimeMillis() > NextGameTick && loops < MaxFrameSkip){
						game.getCurrentScreen().update(deltaTime);
						NextGameTick += SkipTicks;
						loops++;
					}
						
					game.getCurrentScreen().paint(deltaTime);
					
					Canvas canvas = holder.lockCanvas();
					canvas.getClipBounds(dstRect);
					canvas.drawBitmap(framebuffer, null, dstRect, null);
					holder.unlockCanvasAndPost(canvas);
				}
		}
			
			public void pause(){
				
				running = false;
				
					while(true){
						
						try{
							renderThread.join();
							break;
							
						} catch (InterruptedException e){
							//retry
						}
					}
			}
		
}
