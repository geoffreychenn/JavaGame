package unsw.dungeon;


public class CollisionTreasure implements Collision {

	@Override
	public void collide(Entity entity, Dungeon dungeon) {
		entity.setSound(new SoundTreasure());
		entity.playSound();
		dungeon.getPlayer().incrementTreasureCount();
		entity.setActive(false);
	}
	
}
