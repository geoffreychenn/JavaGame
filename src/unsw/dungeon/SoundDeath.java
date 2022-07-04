package unsw.dungeon;

public class SoundDeath implements Sound {

	public SoundDeath() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void playSound() {
		String path = "sounds/other/roblox-death-sound_1.wav";
		SoundPlayer.playSound(path);
		
	}

}
