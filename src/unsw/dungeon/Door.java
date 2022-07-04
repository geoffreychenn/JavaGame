package unsw.dungeon;

public class Door extends Entity {

	private static int count = 0; // Count of doors
	private int id;
	
	public Door(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		setPassable(false);
        setCollider(new CollisionDoor());
		this.id = count;
		count++;
	}
	
	public void unlock() {
		setPassable(true);
		this.getDungeon().getTile(getX(), getY()).setPassable(true);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int n) {
		this.id = n;
	}
	

}
