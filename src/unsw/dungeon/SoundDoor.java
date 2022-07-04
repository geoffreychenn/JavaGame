package unsw.dungeon;

import java.util.Random;

public class SoundDoor implements Sound {

	public SoundDoor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void playSound() {
		int i = new Random().nextInt(2) + 1;
		String path = "sounds/RPGsounds_Kenney/doorOpen_" + i + ".wav";
		SoundPlayer.playSound(path);
		
	}

}
