package com.spaceshooter.game;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.spaceshooter.framework.Game;
import com.spaceshooter.framework.Graphics;
import com.spaceshooter.framework.Input.TouchEvent;
import com.spaceshooter.framework.Screen;

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

	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
		
	public void paint(float deltaTime) {
		
		Graphics g = game.getGraphics();
		
		String[] names = game.getNHighScores();
		int[] scores = game.getHighScores();
		
        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
		
		g.drawImage(Assets.highScores, 0, 0);
		g.drawImage(Assets.menu_back, 250, 750);
			
		for(int i = 0; i < 10; i ++){
			
			String score, numbering, name;
			
				numbering = (i+1) + " : "; 
						
					if(scores[9-i] != 0){
						
						name = names[9-i];
						score = scores[9-i] + "";									
					}
					
					else{
						name = "TBD";
						score = "To Be Determined";
					}
			
			g.drawString(numbering, 90, 255 + (i * 52), paint);
			g.drawString(name, 150, 255 + (i * 52), paint);
			paint.setTextAlign(Paint.Align.RIGHT);
			g.drawString(score, 510, 255 + (i * 52), paint);
			paint.setTextAlign(Paint.Align.LEFT);
			
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
