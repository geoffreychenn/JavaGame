package unsw.dungeon;

import java.util.Random;

public class SoundSword implements Sound {

	public SoundSword() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void playSound() {
		int i = new Random().nextInt(5) + 1;
		String path = "sounds/rpg_sound_pack/sword-unsheathe" + i + ".wav";
		SoundPlayer.playSound(path);
	}

}
