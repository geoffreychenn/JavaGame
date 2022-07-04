package unsw.dungeon;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Enemy extends Entity
{
	private int delay;
	
	public Enemy(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
        setPassable(true);
        setCollider(new CollisionEnemy());
        setDelay(2);
    }
    
    public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void move()
    {
    	Dungeon dungeon = getDungeon();
    	Player player = dungeon.getPlayer();
    	if (player.getInvinvibilityDuration() == 0)
    	{
	    	int width = dungeon.getWidth(), height = dungeon.getHeight();
	    	ArrayDeque<Tile> q = new ArrayDeque<Tile>();
	    	
	    	boolean visited[][] = new boolean[width][height]; 
	    	Tile pred[][] = new Tile[width][height]; 
	    	for (int i = 0; i < width; i++)
	    	{
	    		for (int j = 0; j < height; j++) 
	    		{
	    			visited[i][j] = false;
	    			pred[i][j] = null;
	    		}
	    	}
	    	
	    	visited[getX()][getY()] = true;
	    	q.addLast(dungeon.getTile(getX(), getY()));
	    	if (bfs(q, visited, pred, player)) moveCloser(pred, dungeon, player);
    	}
    	else moveAway(dungeon.getTile(getX(), getY()), player,
    				  player.getX() - getX(), player.getY() - getY());
    }
    
    /**
     * Runs a breadth first search algorithm to find the shortest path to the player from
     * enemy's position.
     * @param q ArrayDeque<Tile> initialised with the start tile.
     * @param visited 2D boolean array with true for starting coordinates and false otherwise.
     * @param pred 2D Tile array initialised to being all null.
     * @param player Player class.
     * @return Returns true if a path was found and false otherwise.
     */
    private boolean bfs(ArrayDeque<Tile> q, boolean[][] visited, Tile[][] pred, Player player)
    {
    	while(!q.isEmpty())
    	{
    		Tile next = q.getFirst();
    		q.removeFirst();
    		for (Tile t : getNeighbours(next))
    		{
        		if (t != null && !visited[t.getX()][t.getY()])
    			{
    				visited[t.getX()][t.getY()] = true;
    				pred[t.getX()][t.getY()] = next;
    				q.addLast(t);
    				if (t.getEntities().contains(player)) return true;
    			}
    		}
    	}
		return false;
    }
    
    /**
     * Finds the vertical and horizontal neighbouring tiles.
     * @param tile Tile class to get the neighbours of.
     * @return Returns ArrayList<Tile> containing neighbours with 0 index being the tile below,
     * index 1 being the tile to the right, index 2 above, and 3 the left. Can contain null values
     * for positions if such neighbour does not exist or is impassable.
     */
    private ArrayList<Tile> getNeighbours(Tile tile)
    {
    	ArrayList<Tile> neighbours = new ArrayList<Tile>();
    	Dungeon dungeon = getDungeon();
    	
    	Tile n = dungeon.getTile(tile.getX(), tile.getY() + 1);	// Down (0)
    	if (dungeon.isPassableTile(n)) neighbours.add(n);	
    	else neighbours.add(null);
    	n = dungeon.getTile(tile.getX() + 1, tile.getY());	// Right (1)	
    	if (dungeon.isPassableTile(n)) neighbours.add(n);	
    	else neighbours.add(null);
    	n = dungeon.getTile(tile.getX(), tile.getY() - 1);	// Up (2)
    	if (dungeon.isPassableTile(n)) neighbours.add(n);	
    	else neighbours.add(null);
    	n = dungeon.getTile(tile.getX() - 1, tile.getY());	// Left (3)
    	if (dungeon.isPassableTile(n)) neighbours.add(n);	
    	else neighbours.add(null);
    	
    	return neighbours;
    }
    
    /**
     * Move closer to player by one tile based on the bfs predecessor 2D array.
     * @param pred 2D array of Tiles containing the predecessor tile of each coordinate.
     * @param dungeon Dungeon class 
     * @param player Player class
     */
    private void moveCloser(Tile[][] pred, Dungeon dungeon, Player player)
    {
    	Tile next = pred[player.getX()][player.getY()];
    	if (next == dungeon.getTile(getX(), getY()))	// If enemy next to player
    		next = dungeon.getTile(player.getX(), player.getY());
    	else
    	{
	    	while (true)
	    	{
	    		Tile temp = pred[next.getX()][next.getY()];
	    		if (temp == null || (temp.getX() == getX() && temp.getY() == getY())) break;
	    		next = temp;
	    	}
    	}
    	
    	if (enemyCheck(next)) return;
    	
    	if (next.getX() == getX())
    	{
    		if (next.getY() == getY() + 1) moveDown(player);
    		else moveUp(player);
    	}
    	else if (next.getY() == getY())
    	{
    		if (next.getX() == getX() + 1) moveRight(player);
    		else moveLeft(player);
    	}
    }
    
    // FAIR WARNING: I know this sucks but it's smart trust
    /**
     * Moves the enemy away from the player based on relative position.
     * @param enemy Enemy tile
     * @param player Player entity
     * @param relX Relative x position of player to enemy
     * @param relY Relative y position of player to enemy
     */
    private void moveAway(Tile enemy, Player player, int relX, int relY)
    {
    	ArrayList<Tile> neighbours = getNeighbours(enemy);
		Tile down = neighbours.get(0), right = neighbours.get(1), up = neighbours.get(2), left = neighbours.get(3);
		
		// In line with player
		
    	if (relY == 0)	// Horizontally in line
    	{
    		if (relX < 0) // Player to the left
    		{
    			if (right != null && !enemyCheck(right)) moveRight(player);	// Try to move right
    			else if (closerToTop(getY()))	// Prioritise moving down
    			{
    				if (down != null && !enemyCheck(down)) moveDown(player);			// Try to move down
    				else if (up != null && !enemyCheck(up)) moveUp(player);				// If can't down try up
    			}
    			else // Prioritise moving up
    			{
    				if (up != null && !enemyCheck(up)) moveUp(player);					// Try to move up
    				else if (down != null && !enemyCheck(down)) moveDown(player);		// If can't up try down
    			}
    		}
    		else	// Player to the right
    		{
    			if (left != null && !enemyCheck(left)) moveLeft(player);	// Try to move left
    			else if (closerToTop(getY()))	// Prioritise moving down
    			{
    				if (down != null && !enemyCheck(down)) moveDown(player);			// Try to move down
    				else if (up != null && !enemyCheck(up)) moveUp(player);				// If can't down try up
    			}
    			else // Prioritise moving up
    			{
    				if (up != null && !enemyCheck(up)) moveUp(player);					// Try to move up
    				else if (down != null && !enemyCheck(down)) moveDown(player);		// If can't up try down
    			}
    		}
    	}
    	else if (relX == 0)	// Vertically in line
    	{
    		if (relY < 0) // Player above
    		{
    			if (down != null && !enemyCheck(down)) moveDown(player);	// Try to move down
    			else if (closerToRight(getY()))	// Prioritise moving left
    			{
    				if (left != null && !enemyCheck(left)) moveLeft(player);			// Try to move left
    				else if (right != null && !enemyCheck(right)) moveRight(player);	// If can't left try right
    			}
    			else // Prioritise moving right
    			{
    				if (right != null && !enemyCheck(right)) moveRight(player);			// Try to move right
    				else if (left != null && !enemyCheck(left)) moveLeft(player);		// If can't right try left
    			}
    		}
    		else	// Player below
    		{
    			if (up != null && !enemyCheck(up)) moveUp(player);			// Try to move up
    			else if (closerToRight(getY()))	// Prioritise moving left
    			{
    				if (left != null && !enemyCheck(left)) moveLeft(player);			// Try to move left
    				else if (right != null && !enemyCheck(right)) moveRight(player);	// If can't left try right
    			}
    			else // Prioritise moving right
    			{
    				if (right != null && !enemyCheck(right)) moveRight(player);			// Try to move right
    				else if (left != null && !enemyCheck(left)) moveLeft(player);		// If can't right try left
    			}
    		}
    	}
    	
    	// Not in line with player
    	
    	else if (relX < 0)	// Player to the left
    	{
    		int furtherHorizontal = Math.abs(relX / relY);
    		if (relY < 0)	// Player above
    		{
    			if (furtherHorizontal > 0) // If player further away horizontally
    			{
    				if (down != null && !enemyCheck(down)) moveDown(player);			// Prioristise moving down
    				else if (right != null && !enemyCheck(right)) moveRight(player); 	// If can't down then right
    			}
    			else	// If player further away vertically 
    			{
    				if (right != null && !enemyCheck(right)) moveRight(player);			// Prioristise moving right
    				else if (down != null && !enemyCheck(down)) moveDown(player); 		// If can't right then down
    			}
    		}
    		else	// Player below
    		{
    			if (furtherHorizontal > 0) // If player further away horizontally
    			{
    				if (up != null && !enemyCheck(up)) moveUp(player);					// Prioristise moving up
    				else if (right != null && !enemyCheck(right)) moveRight(player); 	// If can't down then right
    			}
    			else	// If player further away vertically 
    			{
    				if (right != null && !enemyCheck(right)) moveRight(player);			// Prioristise moving right
    				else if (up != null && !enemyCheck(up)) moveUp(player); 		// If can't right then up
    			}
    		}
    	}
    	else	// Player to the right
    	{
    		int furtherHorizontal = Math.abs(relX / relY);
    		if (relY < 0)	// Player above
    		{
    			if (furtherHorizontal > 0) // If player further away horizontally
    			{
    				if (down != null && !enemyCheck(down)) moveDown(player);			// Prioristise moving down
    				else if (left != null && !enemyCheck(left)) moveLeft(player); 		// If can't down then left
    			}
    			else	// If player further away vertically 
    			{
    				if (left != null && !enemyCheck(left)) moveLeft(player);			// Prioristise moving left
    				else if (down != null && !enemyCheck(down)) moveDown(player); 		// If can't left then down
    			}
    		}
    		else	// Player below
    		{
    			if (furtherHorizontal > 0) // If player further away horizontally
    			{
    				if (up != null && !enemyCheck(up)) moveUp(player);					// Prioristise moving up
    				else if (left != null && !enemyCheck(left)) moveLeft(player); 		// If can't up then left
    			}
    			else	// If player further away vertically 
    			{
    				if (left != null && !enemyCheck(left)) moveLeft(player);			// Prioristise moving left
    				else if (up != null && !enemyCheck(up)) moveUp(player); 			// If can't right then up
    			}
    		}
    	}
    }
    
    /**
     * Enemies cannot move onto other enemies. Tile is checked for enemy entities.
     * @param tile Tile class to check.
     * @return Return true if enemy exists in the tile and false otherwise.
     */
    private boolean enemyCheck(Tile tile)
    {
    	for (Entity e : tile.getEntities()) 
    		if (e instanceof Enemy) return true;	
    	return false;
    }
    
    /**
     * Determines if y value is closer to the top of dungeon. 
     * @param y Coordinate in y direction
     * @return True if closer to the top of dungeon and false if closer to the bottom.
     */
    private boolean closerToTop(int y)
    {
    	int halfHeight = getDungeon().getHeight() / 2;
    	if (y <= halfHeight) return true;
    	else return false;
    }
    
    /**
     * Determines if x value is closer to the right of dungeon. 
     * @param x Coordinate in x direction
     * @return True if closer to the right of dungeon and false if closer to the left.
     */
    private boolean closerToRight(int x)
    {
    	int halfWidth = getDungeon().getWidth() / 2;
    	if (x >= halfWidth) return true;
    	else return false;
    }
    
    public void moveUp(Player player) 
    {
        y().set(getY() - 1);
        if (player.getX() == getX() && player.getY() == getY()) this.collide();
    }

    public void moveDown(Player player) 
    {
        y().set(getY() + 1);
        if (player.getX() == getX() && player.getY() == getY()) this.collide();
    }

    public void moveLeft(Player player) 
    {
        x().set(getX() - 1);
        if (player.getX() == getX() && player.getY() == getY()) this.collide();
    }

    public void moveRight(Player player) 
    {
        x().set(getX() + 1);
        if (player.getX() == getX() && player.getY() == getY()) this.collide();
    }
}
