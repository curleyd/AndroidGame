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
		
		SharedPreferences prefs = this.getSharedPreferences("options", Context.MODE_PRIVATE);
		GameScreen.playSound = prefs.getBoolean("sound", true);
		GameScreen.playMusic = prefs.getBoolean("music", true);
		

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
		
		if(this.getCurrentScreen() instanceof GameScreen) {
			if(GameScreen.playMusic)
				GameScreen.music.play();
		}
		if(this.getCurrentScreen() instanceof MainMenuScreen || this.getCurrentScreen() instanceof HighScoresScreen)
			if(GameScreen.playMusic)
				Assets.theme.play();

	}

	@Override
	public void onPause() {
		
		super.onPause();
		
		if(this.getCurrentScreen() instanceof GameScreen)
			GameScreen.music.stop();
		if(this.getCurrentScreen() instanceof MainMenuScreen || this.getCurrentScreen() instanceof HighScoresScreen)
			Assets.theme.stop();

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
	public void setAudio() {
		
		SharedPreferences prefs = this.getSharedPreferences("options", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		
		editor.putBoolean("sound", GameScreen.playSound);
		editor.putBoolean("music", GameScreen.playMusic);
		editor.commit();
		
	}

	@Override
	public String[] getNHighScores(){
		
		SharedPreferences prefs = this.getSharedPreferences("NhighScores", Context.MODE_PRIVATE);
		String[] names = new String[10];
		
			for(int i = 0; i < 10; i++){
				String key = "" + i;
				names[i] = prefs.getString(key, "");
			}
			
		return names;		
	}
	
	@Override
	public boolean checkScore(int score){
		
		SharedPreferences prefs = this.getSharedPreferences("highScores", Context.MODE_PRIVATE);
		int[] scores = new int[10];
		
			for(int i = 0; i < 10; i++){
				String key = "" + i;
				scores[i] = prefs.getInt(key, 0);
				
					if(score > scores[i])
						return true;
			}
			
		return false;			
	}
	
	
	
	
}