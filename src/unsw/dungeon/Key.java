package unsw.dungeon;

public class Key extends Entity {

	private static int count = 0;	// Count of keys
	private int id;
	
	public Key(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		setPassable(true);
        setCollider(new CollisionKey());
		this.id = count;
		count++;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int n) {
		this.id = n;
	}

}
