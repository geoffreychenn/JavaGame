package unsw.dungeon;

import unsw.dungeon.Boulder.Direction;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {
	
    private int weaponDurability;
    private int killCount;
    private int treasureCount;
    private int invincibilityDuration;
    private Key key;
    private Boolean alive;
    private Goal goals;
    private Boulder boulder;
    private UI observer;
    
    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        this.weaponDurability = 0;
        this.killCount = 0;
        this.treasureCount = 0;
        this.invincibilityDuration = 0;
        this.key = null;
        this.alive = true;
        this.boulder = null;
    }

    public boolean moveUp() {
    	Dungeon dungeon = getDungeon();
    	Boulder boulder = getBoulder();
    	if(boulder == null || boulder.getAttached() == Direction.DOWN || boulder.getAttached() == Direction.UP) {
	    	if (boulder != null && boulder.getAttached() == Direction.DOWN) boulder.moveUp();
	        Tile tile = dungeon.getTile(getX(), getY() - 1);
	        checkDoor(tile);
	        if (dungeon.isPassableTile(tile) && dungeon.checkMoveBackwardsBoulder(tile))
	    	{
	    		y().set(getY() - 1);
	    		collide(tile);
	    		decreaseInvincibilityDuration();
	    		if (boulder != null && boulder.getAttached() == Direction.UP) boulder.moveUp();
	    		checkGoals();
	    		updateMoveCount();
	    		return true;
	    	}
    	}
	    return false;
    }

    public boolean moveDown() {
    	Dungeon dungeon = getDungeon();
    	Boulder boulder = getBoulder();
    	if(boulder == null || boulder.getAttached() == Direction.DOWN || boulder.getAttached() == Direction.UP) {
	    	if (boulder != null && boulder.getAttached() == Direction.UP) boulder.moveDown();
	    	Tile tile = dungeon.getTile(getX(), getY() + 1);
	    	checkDoor(tile);
	    	if (dungeon.isPassableTile(tile) && dungeon.checkMoveBackwardsBoulder(tile))
	    	{
	    		y().set(getY() + 1);
	    		collide(tile);
	    		decreaseInvincibilityDuration();
	    		if (boulder != null && boulder.getAttached() == Direction.DOWN) boulder.moveDown();
	    		checkGoals();
	    		updateMoveCount();
	    		return true;
	    	}
    	}
    	return false;
    }

    public boolean moveLeft() {
    	Dungeon dungeon = getDungeon();
    	Boulder boulder = getBoulder();
    	if(boulder == null || boulder.getAttached() == Direction.LEFT || boulder.getAttached() == Direction.RIGHT) {
	    	if (boulder != null && boulder.getAttached() == Direction.RIGHT) boulder.moveLeft();
	    	Tile tile = dungeon.getTile(getX() - 1, getY());
	    	checkDoor(tile);
	    	if (dungeon.isPassableTile(tile) && dungeon.checkMoveBackwardsBoulder(tile))
	    	{
	    		x().set(getX() - 1);
	    		collide(tile);
	    		decreaseInvincibilityDuration();
	    		if (boulder != null && boulder.getAttached() == Direction.LEFT) boulder.moveLeft();
	    		checkGoals();
	    		updateMoveCount();
	    		return true;
	    	}
    	}
    	return false;
    }

    public boolean moveRight() {
    	Dungeon dungeon = getDungeon();
    	Boulder boulder = getBoulder();
    	if(boulder == null || boulder.getAttached() == Direction.LEFT || boulder.getAttached() == Direction.RIGHT) {
	    	if (boulder != null && boulder.getAttached() == Direction.LEFT) boulder.moveRight();
	    	Tile tile = dungeon.getTile(getX() + 1, getY());
	    	checkDoor(tile);
	    	if (dungeon.isPassableTile(tile) && dungeon.checkMoveBackwardsBoulder(tile))
	    	{
	    		x().set(getX() + 1);
	    		collide(tile);
	    		decreaseInvincibilityDuration();
	    		if (boulder != null && boulder.getAttached() == Direction.RIGHT) boulder.moveRight();
	    		checkGoals();
	    		updateMoveCount();
	    		return true;
	    	}
    	}
    	return false;
    }
    
    public void collide(Tile tile) {
		for (int i = 0; i < tile.getEntities().size(); i++) {
			Entity e = tile.getEntities().get(i);
			if(e.isActive()) {
				e.collide();
				if(!e.isActive()) i--;
			}
		}
    }
    
    public void checkDoor(Tile tile) {
    	if(tile == null) return;
    	for(Entity e : tile.getEntities()) {
    		if(e instanceof Door) e.collide();
     	}
    }
    
    public void toggleBoulder(Tile tile) {
    	for(Entity e : tile.getEntities()) {
    		if(e instanceof Boulder) {
    			if(boulder == null) { 
    				attachBoulder((Boulder) e);
    				return;
    			}
    			else if(boulder != null) {
    				if(e.equals(boulder)) {
    					releaseBoulder();
    					return;
    				}
    			}
    		}
    	}
    }
    
    public void attachBoulder(Boulder boulder) {
    	if (isAdjacent(boulder)) {
    		boulder.setAttached(this);
        	this.boulder = boulder;
    	}
    }
    
    public void releaseBoulder() {
    	boulder.setAttached(null);
    	this.boulder = null;
    }
    
    public Boulder getBoulder() {
    	return this.boulder;
    }
    
    public boolean isAdjacent(Entity entity) {
    	return (entity.getY() == getY() && (entity.getX() == getX() - 1 || entity.getX() == getX() + 1)) ||
    		   (entity.getX() == getX() && (entity.getY() == getY() - 1 || entity.getY() == getY() + 1));
    }
    
    public int getDurability() {
    	return weaponDurability;
    }
    
    public void setDurability(int n) {
    	this.weaponDurability = n;
		if (observer != null) observer.updateDurability(weaponDurability);
    }
    
    public void decrementDurability() {
    	if (weaponDurability > 0) {
    		this.weaponDurability--;
    		if (observer != null) observer.updateDurability(weaponDurability);
    	}
    }
    
    public int getTreasureCount() {
    	return treasureCount;
    }
    
    public void incrementTreasureCount() {
    	treasureCount++;
    	if (observer != null) observer.updateTreasureCount(treasureCount);
    }
    
    public void setTreasureCount(int n) {
    	this.treasureCount = n;
    	if (observer != null) observer.updateTreasureCount(treasureCount);
    }
    
    public Key getKey() {
    	return key;
    }
    
    public void addKey(Key key) {
    	this.key = key;
    	if (observer != null)
    	{
	    	if (key != null) observer.updateKeyActive(true);
	    	else observer.updateKeyActive(false);
    	}
    }
    
    public void dropKey(int x, int y)
    {
    	if (x == getX() && y == getY() && key != null)
    	{
    		key.x().set(x);
    		key.y().set(y);
	    	key.setActive(true);
	    	addKey(null);
    	}
    }
    
    public int getKeyId() {
    	return key.getId();
    }
    
    public int getKillCount() {
    	return killCount;
    }
    
    public void incrementKill() {
    	this.killCount++;
    	if (observer != null) observer.updateKillCount(killCount);
    }
    public void setKillCount(int n) {
    	this.killCount = n;
    	if (observer != null) observer.updateKillCount(killCount);
    }
    
    public Boolean getAlive() {
    	return alive;
    }
    
    public void setAlive(Boolean bool) {
    	this.alive = bool;
    	if (observer != null && alive == false) observer.setLost(true);
    }
    
    public void setInvinvibilityDuration(int n) {
    	this.invincibilityDuration = n;
    	if (observer != null) observer.updateDuration(invincibilityDuration);
    }
    
    public int getInvinvibilityDuration() {
    	return invincibilityDuration;
    }
    
    public void decreaseInvincibilityDuration() 
    {
    	if(invincibilityDuration > 0) 
    	{
    		this.invincibilityDuration--;
    		if (observer != null) observer.updateDuration(invincibilityDuration);
    	}
    }

	public Goal getGoals()
	{
		return goals;
	}

	public void setGoals(Goal goals)
	{
		this.goals = goals;
		
	}
	
	public void checkGoals()
	{
		if (observer != null)
		{
			if (goals.isSatisfied()) observer.setWon(true);
			else if (!alive) observer.setLost(true);
		}
	}
	
	public void updateMoveCount()
	{
		if (observer != null) observer.updateMoveCount();
		getDungeon().addTurn();
	}
    
	public void attachObserver(UI observer)
	{
		this.observer = observer;
	}
	
	public UI getObserver()
	{
		return observer;
	}
}
