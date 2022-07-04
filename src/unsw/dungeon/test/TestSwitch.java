package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

public class TestSwitch 
{

	Dungeon dungeon;
	
	Switch floorSwitch;
	
	public TestSwitch() 
	{
		dungeon = new Dungeon(1, 2);
		floorSwitch = new Switch(0, 0, dungeon);
		dungeon.addEntity(floorSwitch);
	}

	@Test
	public void testNothingOnSwitch()
	{
		floorSwitch.updateTile();
		assertEquals(false, floorSwitch.isActive());
	}
	
	@Test
	public void testPlayerOnSwitch()
	{
		dungeon.addEntity(new Player(0, 0, dungeon));
		floorSwitch.updateTile();
		assertEquals(false, floorSwitch.isActive());
	}
	
	@Test
	public void testEnemyOnSwitch()
	{
		dungeon.addEntity(new Enemy(0, 0, dungeon));
		floorSwitch.updateTile();
		assertEquals(false, floorSwitch.isActive());
	}
	
	@Test
	public void testBoulderOnSwitch()
	{
		Boulder boulder = new Boulder(0, 0, dungeon);
		dungeon.addEntity(boulder);
		floorSwitch.updateTile();
		assertEquals(true, floorSwitch.isActive());
		
		boulder.moveDown();
		floorSwitch.updateTile();
		assertEquals(false, floorSwitch.isActive());
	}
}
