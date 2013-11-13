package com.spaceshooter.framework;

public interface Game {

		public Audio getAudio();
		
		public Input getInput();
		
		public FileIO getFileIO();
		
		public Graphics getGraphics();
		
		public void setScreen(Screen screen);
		
		public Screen getInitScreen();
		
		public void setHighScores(int score);
		
		public boolean checkScore(int score);
		
		public int[] getHighScores();

		public String[] getNHighScores();
		
		public void setAudio();
}
