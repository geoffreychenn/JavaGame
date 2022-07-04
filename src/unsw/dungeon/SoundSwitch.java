package unsw.dungeon;

public class SoundSwitch implements Sound {

	public SoundSwitch() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void playSound() {
		String path = "sounds/RPGsounds_Kenney/switch.wav";
		SoundPlayer.playSound(path);
	}

}
