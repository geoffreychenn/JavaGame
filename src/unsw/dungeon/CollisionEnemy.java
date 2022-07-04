package unsw.dungeon;

public class CollisionEnemy implements Collision {

	@Override
	public void collide(Entity entity, Dungeon dungeon) {
		Player player = dungeon.getPlayer();
		if(player.getInvinvibilityDuration() > 0) {
			entity.setSound(new SoundInvincibilityPotionKill());
			entity.playSound();
			entity.setSound(new SoundEnemy1());
			entity.playSound();
			player.incrementKill();
			entity.setActive(false);
		}
		else if(player.getDurability() > 0) {
			entity.setSound(new SoundSwordSwing());
			entity.playSound();
			entity.setSound(new SoundEnemy1());
			entity.playSound();
			player.decrementDurability();
			player.incrementKill();
			entity.setActive(false);
		}
		else {
			entity.setSound(new SoundDeath());
			entity.playSound();
			player.setAlive(false);
		}
	}

}
