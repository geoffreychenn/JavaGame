package unsw.dungeon;

public class CollisionDoor implements Collision {

	@Override
	public void collide(Entity entity, Dungeon dungeon) {
		Door door = (Door) entity;
		
		if(door.isPassable() == false) {
			Player player = dungeon.getPlayer();
			if(player.getKey() != null) {
				if(player.getKeyId() == door.getId()) {
					door.setSound(new SoundDoor());
					door.playSound();
					door.unlock();
					player.addKey(null);
				}
			}
		}
		
	}

}
