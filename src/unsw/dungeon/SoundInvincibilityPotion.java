package unsw.dungeon;

public class SoundInvincibilityPotion implements Sound {

	public SoundInvincibilityPotion() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void playSound() {
		String path = "sounds/rpg_sound_pack/bottle.wav";
		SoundPlayer.playSound(path);
	}
	
	

}
