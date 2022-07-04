package unsw.dungeon;

public class SoundBoulder implements Sound {

	public SoundBoulder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void playSound() {
		String path = "sounds/other/sfx_push_boulder.wav";
		SoundPlayer.playSound(path);
		
	}

}
