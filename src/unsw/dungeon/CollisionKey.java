package unsw.dungeon;

public class CollisionKey implements Collision {

	@Override
	public void collide(Entity entity, Dungeon dungeon) {
		Key key = (Key) entity;
		Player player = dungeon.getPlayer();
		Sound sound = new SoundNone();
		
		if(player.getKey() == null) {
			sound = new SoundKey();
			player.addKey(key);
			key.setActive(false);
		}
		
		key.setSound(sound);
		key.playSound();
	}

}
