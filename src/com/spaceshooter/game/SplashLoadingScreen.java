package com.spaceshooter.game;

import com.spaceshooter.framework.Game;
import com.spaceshooter.framework.Graphics;
import com.spaceshooter.framework.Graphics.ImageFormat;
import com.spaceshooter.framework.Screen;

public class SplashLoadingScreen extends Screen {
	public SplashLoadingScreen(Game game) {
		super(game);
	}

	// Loads the loading screen for the game
	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.splash= g.newImage("splash.jpg", ImageFormat.RGB565);

		
		game.setScreen(new LoadingScreen(game));

	}

	@Override
	public void paint(float deltaTime) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {

	}
}