package unsw.dungeon;

public class Exit extends Entity{

	public Exit(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		setPassable(true);
        setCollider(new CollisionNone());
	}
	
	public Boolean checkPlayer() {
		Tile tile = getDungeon().getTile(getX(), getY());
		for (Entity e : tile.getEntities()) {
			if(e instanceof Player) return true;
		}
		return false;
	}

}
