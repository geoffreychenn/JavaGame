package unsw.dungeon;

public class EnemyGnome extends Enemy {

	public EnemyGnome(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		setDelay(3);
		setCollider(new CollisionGnome());
	}

}
