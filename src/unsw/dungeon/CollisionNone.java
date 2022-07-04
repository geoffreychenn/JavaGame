package unsw.dungeon;

public class CollisionNone implements Collision
{

	@Override
	public void collide(Entity entity, Dungeon dungeon)
	{
		return;
	}

}
