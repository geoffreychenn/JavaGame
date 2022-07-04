package unsw.dungeon;

import java.util.Random;

public class SoundTreasure implements Sound {

	public SoundTreasure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void playSound() {
		int i = new Random().nextInt(3) + 1;
		String path = "sounds/rpg_sound_pack/coin" + i + ".wav";
		SoundPlayer.playSound(path);
	}

}
