package unsw.dungeon;

public class EnemyHound extends Enemy {

	public EnemyHound(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		setDelay(1);
		setCollider(new CollisionHound());
	}

}
