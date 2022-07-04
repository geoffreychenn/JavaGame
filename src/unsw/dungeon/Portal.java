package unsw.dungeon;

public class Portal extends Entity {

	private static int count = 0;	// Count of total portals
	private int id;
	
	public Portal(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		setPassable(true);
        setCollider(new CollisionPortal());
		id = count / 2;	// Will always be a pair of portals with same id for even count
		count++;
	}
	
	public Portal findPartner() {
		for(Portal p : this.getDungeon().getPortals()) {
			if(p.equals(this)) continue;
			if(p.getId() == this.getId()) return p;
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
