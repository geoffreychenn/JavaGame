package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;
    private String fileName;

    public DungeonLoader(String fileName) throws FileNotFoundException {
    	this.fileName = fileName;
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + fileName)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        
        dungeon.getPlayer().setGoals(getGoals(dungeon, json.getJSONObject("goal-condition")));
        
        return dungeon;
    }
    
    public Goal getGoals(Dungeon dungeon, JSONObject json)
    {
    	String type = json.getString("goal");
    	JSONArray subGoals = null;
    	Goal toReturn = null;
    	switch (type)
    	{
	    	case "AND":
	    		AndGoal and = new AndGoal();
	    		subGoals = json.getJSONArray("subgoals");
	    		for (int i = 0; i < subGoals.length(); i++)
	    			and.addGoal(getGoals(dungeon, subGoals.getJSONObject(i)));
	    		toReturn = and;
	    		break;
	    	case "OR":
	    		OrGoal or = new OrGoal();
	    		subGoals = json.getJSONArray("subgoals");
	    		for (int i = 0; i < subGoals.length(); i++)
	    			or.addGoal(getGoals(dungeon, subGoals.getJSONObject(i)));
	    		toReturn = or;
	    		break;
	    	case "exit":
	    		toReturn = new ExitGoal(dungeon.getPlayer(), dungeon.getExit());
	    		break;
	    	case "enemies":
	    		toReturn = new EnemyGoal(dungeon.getPlayer(), dungeon.getEnemyCount());
	    		break;
	    	case "boulders":
	    		toReturn = new BoulderGoal(dungeon.getSwitches());
	    		break;
	    	case "treasure":
	    		toReturn = new TreasureGoal(dungeon.getPlayer(), dungeon.getTreasureCount());
	    		break;
    	}
    	return toReturn;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) 
    {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) 
        {
	        case "player":
	            Player player = new Player(x, y, dungeon);
	            onLoad(player);
	            entity = player;
	            break;
	        case "wall":
	            Wall wall = new Wall(x, y, dungeon);
	            onLoad(wall);
	            entity = wall;
	            break;
	        case "sword":
	            Sword sword = new Sword(x, y, dungeon);
	            onLoad(sword);
	            entity = sword;
	            break;
	        case "exit":
	        	Exit exit = new Exit(x, y, dungeon);
	        	onLoad(exit);
	        	entity = exit;
	        	break;
	        case "boulder":
	            Boulder boulder = new Boulder(x, y, dungeon);
	            onLoad(boulder);
	            entity = boulder;
	            break;
	        case "door":
	            Door door = new Door(x, y, dungeon);
	            onLoad(door);
	            entity = door;
	            break;
	        case "enemy":
	            Enemy enemy = new Enemy(x, y, dungeon);
	            onLoad(enemy);
	            entity = enemy;
	            break;
	        case "invincibility":
	        	InvincibilityPotion potion = new InvincibilityPotion(x, y, dungeon);
	        	onLoad(potion);
	        	entity = potion;
	        	break;
	        case "key":
	            Key key = new Key(x, y, dungeon);
	            onLoad(key);
	            entity = key;
	            break;
	        case "portal":
	            Portal portal = new Portal(x, y, dungeon);
	            onLoad(portal);
	            entity = portal;
	            break;
	        case "switch":
	            Switch floorSwitch = new Switch(x, y, dungeon);
	            onLoad(floorSwitch);
	            entity = floorSwitch;
	            break;
	        case "treasure":
	        	Treasure treasure = new Treasure(x, y, dungeon);
	        	onLoad(treasure);
	        	entity = treasure;
	        	break;
	        case "hound":
	        	EnemyHound hound = new EnemyHound(x, y, dungeon);
	        	onLoad(hound);
	        	entity = hound;
	        	break;
	        case "gnome":
	        	EnemyGnome gnome = new EnemyGnome(x, y, dungeon);
	        	onLoad(gnome);
	        	entity = gnome;
	        	break;
        }
        dungeon.addEntity(entity);
    }
    
    public String getFileName()
    {
    	return fileName;
    }

    public abstract void onLoad(Player player);

    public abstract void onLoad(Wall wall);
    
    public abstract void onLoad(Sword sword);
    
    public abstract void onLoad(Exit exit);
    
    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(Door door);
    
    public abstract void onLoad(Enemy enemy);
    
    public abstract void onLoad(InvincibilityPotion potion);

    public abstract void onLoad(Key key);
    
    public abstract void onLoad(Portal portal);
    
    public abstract void onLoad(Switch floorSwitch);
    
    public abstract void onLoad(Treasure treasure);
    
    public abstract void onLoad(EnemyHound enemy);
    
    public abstract void onLoad(EnemyGnome enemy);

}
