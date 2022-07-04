package unsw.dungeon;

public class SoundInvincibilityPotionKill implements Sound {

	public SoundInvincibilityPotionKill() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void playSound() {
		String path = "sounds/other/Spell_attack.wav";
		SoundPlayer.playSound(path);

	}

}
