package unsw.dungeon;

public class Treasure extends Entity
{

	public Treasure(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		setPassable(true);
        setCollider(new CollisionTreasure());
	}

}
