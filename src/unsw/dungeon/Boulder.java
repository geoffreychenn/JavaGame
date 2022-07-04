package unsw.dungeon;

public class Boulder extends Entity {
	
	public enum Direction {
		UP, DOWN, LEFT, RIGHT;		//relative direction of player to boulder
	}
	
	private Direction attached;
	
	public Boulder(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		setPassable(false);
        setCollider(new CollisionNone());
        attached = null;
	}
	
	public void setAttached(Player player) {
		if(player == null) attached = null;
		else if(player.getX() == this.getX() && player.getY() + 1 == this.getY()) this.attached = Direction.UP;
		else if(player.getX() == this.getX() && player.getY() - 1 == this.getY()) this.attached = Direction.DOWN;
		else if(player.getX() - 1 == this.getX() && player.getY() == this.getY()) this.attached = Direction.RIGHT;
		else if(player.getX() + 1 == this.getX() && player.getY() == this.getY()) this.attached = Direction.LEFT;
	}
	
	public Direction getAttached() {
		return this.attached;
	}
	
	public void moveUp() {
		Dungeon dungeon = getDungeon();
		Tile tile = dungeon.getTile(getX(), getY() - 1);
		if(dungeon.isPassableTile(tile) && validTile(tile)) {
			y().set(getY() - 1);
			dungeon.updateMatrix(this, getY() + 1, false);
			this.setSound(new SoundBoulder());
			this.playSound();
			checkSwitchs(tile);
		}
	}
	
	public void moveDown() {
		Dungeon dungeon = getDungeon();
		Tile tile = dungeon.getTile(getX(), getY() + 1);
		if(dungeon.isPassableTile(tile) && validTile(tile)) {
			y().set(getY() + 1);
			dungeon.updateMatrix(this, getY() - 1, false);
			this.setSound(new SoundBoulder());
			this.playSound();
			checkSwitchs(tile);
		}
	}
	
	public void moveRight() {
		Dungeon dungeon = getDungeon();
		Tile tile = dungeon.getTile(getX() + 1, getY());
		if(dungeon.isPassableTile(tile) && validTile(tile)) {
			x().set(getX() + 1);
			dungeon.updateMatrix(this, getX() - 1, true);
			this.setSound(new SoundBoulder());
			this.playSound();
			checkSwitchs(tile);
		}
	}
	
	public void moveLeft() {
		Dungeon dungeon = getDungeon();
		Tile tile = dungeon.getTile(getX() - 1, getY());
		if(dungeon.isPassableTile(tile) && validTile(tile)) {
			x().set(getX() - 1);
			dungeon.updateMatrix(this, getX() + 1, true);
			this.setSound(new SoundBoulder());
			this.playSound();
			checkSwitchs(tile);
		}
	}
	
	public Boolean validTile(Tile tile) {
		for(Entity e : tile.getEntities()) {
			if(e instanceof Portal || e instanceof Exit || e instanceof Enemy) return false;
		}
		return true;
	}
	
	public void checkSwitchs(Tile tile) {
		for (Switch s : getDungeon().getSwitches()) {
			s.updateTile();
		}
	}
	
}
