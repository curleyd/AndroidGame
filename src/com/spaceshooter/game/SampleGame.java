package com.spaceshooter.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.spaceshooter.game.R;
import com.spaceshooter.framework.Screen;
import com.spaceshooter.framework.implementation.AndroidGame;

public class SampleGame extends AndroidGame {

	public static String map;
	boolean firstTimeCreate = true;

	@Override
	public Screen getInitScreen() {

		if (firstTimeCreate) {
			Assets.load(this);
			firstTimeCreate = false;
		}

		InputStream is = getResources().openRawResource(R.raw.map1);
		map = convertStreamToString(is);

		return new SplashLoadingScreen(this);

	}

	@Override
	public void onBackPressed() {
		getCurrentScreen().backButton();
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append((line + "\n"));
			}
		} catch (IOException e) {
			Log.w("LOG", e.getMessage());
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				Log.w("LOG", e.getMessage());
			}
		}
		return sb.toString();
	}

	@Override
	public void onResume() {
		super.onResume();
		Assets.theme.play();

	}

	@Override
	public void onPause() {
		super.onPause();
		Assets.theme.pause();

	}
	
	@Override
	public int[] getHighScores() {
		
		SharedPreferences prefs = this.getSharedPreferences("highScores", Context.MODE_PRIVATE);
		int[] scores = new int[10];
		for(int i = 0; i < 10; i++) {
			String key = "" + i;
			scores[i] = prefs.getInt(key, 0); //0 is the default value
		}
		
		return scores;
		
	}

	@Override
	public void setHighScores(int score) {
		
		SharedPreferences prefs = this.getSharedPreferences("highScores", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		int[] scores = getHighScores();
		
		if(score > scores[0]) {
			
			String value;
			
			scores[0] = score;
			Arrays.sort(scores);
			
			for(int i = 0; i < 10; i++) {
				
				String key = "" + i;
				editor.putInt(key, scores[i]);
				editor.commit();
				System.out.println(scores[i]);
			}
		}
		
	}
	
	
}