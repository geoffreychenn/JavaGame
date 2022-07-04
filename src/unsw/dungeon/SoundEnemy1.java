package unsw.dungeon;

import java.util.Random;

public class SoundEnemy1 implements Sound {

	public SoundEnemy1() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void playSound() {
		int i = new Random().nextInt(5) + 1;
		String path = "sounds/rpg_sound_pack/giant" + i + ".wav";
		SoundPlayer.playSound(path);
		
	}
}
