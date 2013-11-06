package com.spaceshooter.framework.implementation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.spaceshooter.framework.Audio;
import com.spaceshooter.framework.FileIO;
import com.spaceshooter.framework.Game;
import com.spaceshooter.framework.Graphics;
import com.spaceshooter.framework.Input;
import com.spaceshooter.framework.Screen;

@SuppressLint("NewApi")
public abstract class AndroidGame extends Activity implements Game {
	
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
			int frameBufferWidth = isPortrait ? 600: 800;
			int frameBufferHeight = isPortrait ? 800: 600;
		Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);
		
		Point point = new Point();
        try {
        	getWindowManager().getDefaultDisplay().getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = getWindowManager().getDefaultDisplay().getWidth();
            point.y = getWindowManager().getDefaultDisplay().getHeight();
        }
		
		float scaleX = (float) frameBufferWidth / point.x;
		float scaleY = (float) frameBufferHeight / point.y;
		
		Screen.x = point.x;
		Screen.y = point.y;
		
		renderView = new AndroidFastRenderView(this, frameBuffer);
		graphics = new AndroidGraphics(getAssets(), frameBuffer);
		fileIO = new AndroidFileIO(this);
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, renderView, scaleX, scaleY);
		screen = getInitScreen();
		setContentView(renderView);
		
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyGame");			
    	
   /*     super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        Point point = new Point();
        try {
        	getWindowManager().getDefaultDisplay().getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = getWindowManager().getDefaultDisplay().getWidth();
            point.y = getWindowManager().getDefaultDisplay().getHeight();
        }
        
        float scaleX = (float) 1;
        float scaleY = (float) 1;
        
        Bitmap frameBuffer = Bitmap.createBitmap(point.x,
                point.y, Config.RGB_565);
        
        Screen.x = point.x;
        Screen.y = point.y;
        
        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getInitScreen();
        setContentView(renderView);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyGame"); */
    }
    
    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();
        
        if(isFinishing())
        	screen.dispose();
        
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }
    
    public Screen getCurrentScreen() {

        return screen;
    }
}
