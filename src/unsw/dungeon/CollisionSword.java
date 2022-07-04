package unsw.dungeon;

public class CollisionSword implements Collision {
	
	@Override
	public void collide(Entity entity, Dungeon dungeon) {
		Sword sword = (Sword) entity;
		Player player = dungeon.getPlayer();
		sword.setSound(new SoundNone());
		if(player.getDurability() == 0) {
			sword.setSound(new SoundSword());
			sword.playSound();
			player.setDurability(sword.getDurability());
			sword.setActive(false);
		}	
	}


}
