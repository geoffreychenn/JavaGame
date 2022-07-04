package unsw.dungeon;

import java.util.ArrayList;

public class Tile
{
	private int x;
	private int y;
	private boolean passable;
	private ArrayList<Entity> entities;
	
	public Tile(int x, int y)
	{
		super();
		this.x = x;
		this.y = y;
		this.passable = true;
		this.entities = new ArrayList<Entity>();
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public boolean isPassable()
	{
		return passable;
	}

	public void setPassable(boolean passable)
	{
		this.passable = passable;
	}

	public ArrayList<Entity> getEntities()
	{
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities)
	{
		this.entities = entities;
	}
	
	public void add(Entity entity)
	{
		if (!entity.isPassable()) setPassable(false);
		entities.add(entity);
	}
	
	public void remove(Entity entity)
	{
		if (!entity.isPassable()) setPassable(true);
		entities.remove(entity);
	}
}
