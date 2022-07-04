package unsw.dungeon;

public class SoundPortal implements Sound {

	public SoundPortal() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void playSound() {
		String path = "sounds/other/Spell_01.wav";
		SoundPlayer.playSound(path);
		
	}

}
