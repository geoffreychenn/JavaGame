package unsw.dungeon;

public class CollisionGnome implements Collision {

	public CollisionGnome() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collide(Entity entity, Dungeon dungeon) {
		Player player = dungeon.getPlayer();
		if(player.getInvinvibilityDuration() > 0) {
			entity.setSound(new SoundInvincibilityPotionKill());
			entity.playSound();
			entity.setSound(new SoundEnemy3());
			entity.playSound();
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
