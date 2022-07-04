package unsw.dungeon;

import java.util.Random;

public class SoundSwordSwing implements Sound {

	public SoundSwordSwing() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void playSound() {
		int i = new Random().nextInt(3) + 1;
		String path = "sounds/rpg_sound_pack/swing" + i + ".wav";
		SoundPlayer.playSound(path);
		
	}

}
