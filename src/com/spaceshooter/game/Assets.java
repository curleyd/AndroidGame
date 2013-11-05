package com.spaceshooter.game;

import com.spaceshooter.framework.Image;
import com.spaceshooter.framework.Music;
import com.spaceshooter.framework.Sound;

public class Assets {
	
	public static Image menu, menu_back, splash, background, grunt;
	public static Image trishot, pause, laserlong, shipL1, shipL2, shipL3, shipR1, shipR2, shipR3, doppleganger;
	public static Image bomb, ship, ship_respawn, oneplus, lives, bombia, bombii, drone, laserguy, laser, elaser;
	public static Sound click;
	public static Music theme;
	
	public static void load(SampleGame sampleGame) {
		// TODO Auto-generated method stub
		Assets.theme = sampleGame.getAudio().createMusic("menutheme.mp3");
		Assets.theme.setLooping(true);
		Assets.theme.setVolume(0.85f);
		Assets.theme.play();
	}
	
}
