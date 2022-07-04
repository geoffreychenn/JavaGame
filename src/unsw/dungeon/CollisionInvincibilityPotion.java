package unsw.dungeon;

public class CollisionInvincibilityPotion implements Collision {

	@Override
	public void collide(Entity entity, Dungeon dungeon) {
		InvincibilityPotion potion = (InvincibilityPotion) entity;
		Player player = dungeon.getPlayer();
		potion.setSound(new SoundInvincibilityPotion());
		potion.playSound();
		player.setInvinvibilityDuration(potion.getDuration());
		entity.setActive(false);
	}

}
