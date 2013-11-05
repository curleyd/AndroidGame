package com.spaceshooter.game;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.spaceshooter.framework.Game;
import com.spaceshooter.framework.Graphics;
import com.spaceshooter.framework.Screen;
import com.spaceshooter.framework.Input.TouchEvent;

public class HighScoresScreen extends Screen {
	
	public HighScoresScreen(Game game) {
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

				if (inBounds(event, 250, 750, 100, 50)) {
					game.setScreen(new MainMenuScreen(game));
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
		
		int[] scores = game.getHighScores();
		
		Graphics g = game.getGraphics();
		
		Paint paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		
		g.drawImage(Assets.background, 0, 0);
		g.drawARGB(155, 0, 0, 0);
		g.drawImage(Assets.menu_back, 250, 750);
		
		for(int i = 0; i < 10; i++) {
			String sc = "" + scores[9-i] + " pts";
			g.drawString(sc, 300, ((800/11)*i)+30, paint);
		}
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
