package com.spaceshooter.game;

import java.util.List;

import com.spaceshooter.game.Assets;
import com.spaceshooter.game.GameScreen;
import com.spaceshooter.game.HighScoresScreen;
import com.spaceshooter.framework.Game;
import com.spaceshooter.framework.Graphics;
import com.spaceshooter.framework.Screen;
import com.spaceshooter.framework.Input.TouchEvent;

public class MainMenuScreen extends Screen {
	public MainMenuScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		
		Graphics g = game.getGraphics();
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {

				if(inBounds(event, 170, 495, 260, 55))								
					game.setScreen(new GameScreen(game));
				
				if(inBounds(event, 170, 600, 260, 55)){
					System.out.println("HS HS HS");
					game.setScreen(new HighScoresScreen(game));
				}
					
				if(inBounds(event, 170, 695, 260, 55)){
					android.os.Process.killProcess(android.os.Process.myPid());
				}

			}
		}
	}

	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
	public void paint(float deltaTime) {
		
		Graphics g = game.getGraphics();
		g.drawImage(Assets.menu, 0, 0);
		
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
        android.os.Process.killProcess(android.os.Process.myPid());

	}
}
