package com.spaceshooter.game;

import com.spaceshooter.framework.Image;
import com.spaceshooter.framework.Music;
import com.spaceshooter.framework.Sound;

public class Assets {
	
	public static Image menu, menu_back, splash, background, highScores, pause, bombia, bombii, soundon, soundoff, musicon, musicoff, warning;
	public static Image trishot, laserlong, shipL1, shipL2, shipL3, shipR1, shipR2, shipR3, lineshooter;
	public static Image bomb, ship, ship_respawn, oneplus, lives, doppleganger, grunt, drone, laserguy, laser, elaser, mine, mSpike, lsLas;
	public static Image explosion1, explosion2, explosion3, explosion4, explosion5;
	public static Sound click, explode, shoot, playerDeath, powerup, LSshoot, LSlasCharge;
	public static Music theme, ingame, bossmusic;
	
	public static void load(SampleGame sampleGame) {
		// TODO Auto-generated method stub
		Assets.theme = sampleGame.getAudio().createMusic("menutheme.mp3");
		Assets.theme.setLooping(true);
		Assets.theme.setVolume(0.5f);
	}
	
}
