package com.spaceshooter.game;

import com.spaceshooter.framework.Game;
import com.spaceshooter.framework.Graphics;
import com.spaceshooter.framework.Graphics.ImageFormat;
import com.spaceshooter.framework.Screen;

public class LoadingScreen extends Screen {
	public LoadingScreen(Game game) {
		
		super(game);
	}

	// Loads all assets that are used in the game. You can find all the assets in the assets folder
	@Override
	public void update(float deltaTime) {
		
		Graphics g = game.getGraphics();
		Assets.menu = g.newImage("menu2.jpg", ImageFormat.RGB565);
		Assets.menu_back = g.newImage("menu_back.png", ImageFormat.RGB565);
		Assets.background = g.newImage("background.jpg", ImageFormat.ARGB4444);
		Assets.lives = g.newImage("lives.png", ImageFormat.ARGB4444);
		Assets.trishot = g.newImage("trishot.png", ImageFormat.ARGB4444);
		Assets.pause = g.newImage("pause.png", ImageFormat.ARGB4444);
		Assets.oneplus = g.newImage("oneplus.png", ImageFormat.ARGB4444);
		Assets.ship_respawn = g.newImage("ship_respawn.png", ImageFormat.ARGB4444);
		Assets.bomb = g.newImage("bomb.png", ImageFormat.ARGB4444);
		Assets.bombia = g.newImage("bomb_icon_active.png", ImageFormat.ARGB4444);
		Assets.bombii = g.newImage("bomb_icon_inactive.png", ImageFormat.ARGB4444);
		
		Assets.ship = g.newImage("ship.png", ImageFormat.ARGB4444);
		Assets.shipL1 = g.newImage("ship_bank_left1.png", ImageFormat.ARGB4444);
		Assets.shipL2 = g.newImage("ship_bank_left2.png", ImageFormat.ARGB4444);
		Assets.shipL3 = g.newImage("ship_bank_left3.png", ImageFormat.ARGB4444);
		Assets.shipR1 = g.newImage("ship_bank_right1.png", ImageFormat.ARGB4444);
		Assets.shipR2 = g.newImage("ship_bank_right2.png", ImageFormat.ARGB4444);
		Assets.shipR3 = g.newImage("ship_bank_right3.png", ImageFormat.ARGB4444);

		
		Assets.grunt = g.newImage("enemy.png", ImageFormat.ARGB4444);
		Assets.drone = g.newImage("drone.png", ImageFormat.ARGB4444);
		Assets.laserguy = g.newImage("laserguy.png", ImageFormat.ARGB4444);
		Assets.doppleganger = g.newImage("doppleganger.png", ImageFormat.ARGB4444);
		Assets.laser = g.newImage("laser.png", ImageFormat.ARGB4444);
		Assets.elaser = g.newImage("elaser.png", ImageFormat.ARGB4444);
		Assets.laserlong = g.newImage("laserlong.png", ImageFormat.ARGB4444);

		
		game.setScreen(new MainMenuScreen(game));

	}

	// Paints the loading screen on the display
	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawImage(Assets.splash, 0, 0);
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