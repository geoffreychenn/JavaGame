package unsw.dungeon;

public class CollisionPortal implements Collision {

	@Override
	public void collide(Entity entity, Dungeon dungeon) {
		Portal portal = (Portal) entity;
		Player player = dungeon.getPlayer();

		Portal p = portal.findPartner();
		if(p != null) {
			portal.setSound(new SoundPortal());
			player.x().set(p.getX());
			player.y().set(p.getY());
		}
		portal.playSound();
	}
	


}
