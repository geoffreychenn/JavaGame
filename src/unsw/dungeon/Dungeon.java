/**
 *
 */
package unsw.dungeon;
import java.util.ArrayList;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height, enemyCount, treasureCount;
	private Tile[][] matrix;
    private ArrayList<Entity> entities;
    private Player player;
    private Exit exit;
    private ArrayList<Portal> portals;
    private ArrayList<Switch> switches;
    private ArrayList<Enemy> enemies;
    private int turn;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.enemyCount = 0;
        this.treasureCount = 0;
		matrix = new Tile[width][height];
		initialiseMatrix();
        this.entities = new ArrayList<>();
        this.player = null;
        this.exit = null;
        this.portals = new ArrayList<>();
        this.switches = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.turn = 0;
    }

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public int getEnemyCount()
	{
		return enemyCount;
	}
	
	public int getTreasureCount()
	{
		return treasureCount;
	}

	public ArrayList<Entity> getEntities()
	{
		return entities;
	}
	
	public ArrayList<Portal> getPortals()
	{
		return portals;
	}
	
	public ArrayList<Switch> getSwitches()
	{
		return switches;
	}
	
	public ArrayList<Enemy> getEnemies()
	{
		return enemies;
	}

	public void setEntities(ArrayList<Entity> entities)
	{
		this.entities = entities;
	}

	public Player getPlayer()
	{
		return player;
	}
	
	public Exit getExit()
	{
		return exit;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	public void addEntity(Entity e)
	{
		if (e instanceof Enemy) 
		{
			enemyCount++;
			enemies.add((Enemy) e) ;
		}
		else if (e instanceof Treasure) treasureCount++;
		else if (e instanceof Player) player = (Player) e;
		else if (e instanceof Exit) exit = (Exit) e;
		else if (e instanceof Portal) portals.add((Portal) e);
		else if (e instanceof Switch) switches.add((Switch) e);
		entities.add(e);
		matrix[e.getX()][e.getY()].add(e);
	}
	
	public void removeEntity(Entity e)
	{
		entities.remove(e);
		matrix[e.getX()][e.getY()].remove(e);
	}
	
	public Tile[][] getMatrix() {
		return matrix;
	}
	
	/**
	 * initialises the matrix to be full of empty array lists
	 */
	public void initialiseMatrix() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(matrix[j][i] == null) matrix[j][i] = new Tile(j, i);
			}
		}
	}

	/**
	 * method updates the matrix when something moves in the game
	 * 
	 * @param entity		is the entity that is moving
	 * @param oldValue		its old x or y coordinate value
	 * @param isX			boolean to check if x or y cooordinate changes
	 */
	public void updateMatrix(Entity entity, int oldValue, Boolean isX) {
		//check if x or y coordinate needs to be changed and remove it from old position
		if (isX) matrix[oldValue][entity.getY()].remove(entity);
		else matrix[entity.getX()][oldValue].remove(entity);
		
		//put entity in new place in matrix
		matrix[entity.getX()][entity.getY()].add(entity);
	}
	
	public Tile getTile(int x, int y)
	{
		if (x < 0 || y < 0 || x > width - 1 || y > height - 1) return null;
		return matrix[x][y];
	}
	
    public boolean isPassableTile(Tile tile) 
    {
    	if (tile == null) return false;
    	return tile.isPassable();
    }
    
    public boolean checkMoveBackwardsBoulder(Tile tile) {
    	if(player.getBoulder() == null) return true;
    	for(Entity e : tile.getEntities()) {
    		if(e instanceof Portal) return false;
    	}
    	return true;
    }
    
    public int getTurn() {
    	return this.turn;
    }
    
    public void addTurn() {
    	this.turn++;
    }
}
