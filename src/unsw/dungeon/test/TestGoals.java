package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

public class TestGoals
{
	Dungeon dungeon;
	
	Player player;
	
	Goal goal;
	
	public TestGoals()
	{
		dungeon = new Dungeon(1, 3);
		player = new Player(0, 0, dungeon);
	}
	
	@Test
	public void testEnemyGoal()
	{
		goal = new EnemyGoal(player, 5);
		player.setGoals(goal);
		assertEquals(0, player.getKillCount());
		assertEquals(false, goal.isSatisfied());		
		
		player.setKillCount(5);
		assertEquals(true, goal.isSatisfied());
	}
	
	@Test
	public void testTreasureGoal()
	{
		goal = new TreasureGoal(player, 5);
		player.setGoals(goal);
		assertEquals(0, player.getTreasureCount());		
		assertEquals(false, goal.isSatisfied());			
		
		player.setTreasureCount(5);
		assertEquals(true, goal.isSatisfied());					
	}
	
	@Test
	public void testExitGoal()
	{
		Exit exit = new Exit(0, 1, dungeon);
		goal = new ExitGoal(player, exit);
		player.setGoals(goal);
		assertEquals(false, goal.isSatisfied());
		
		player.y().set(1);
		assertEquals(true, goal.isSatisfied());
	}
	
	@Test
	public void testBoulderGoal()
	{
		dungeon.addEntity(new Switch(0, 1, dungeon));
		dungeon.addEntity(new Switch(0, 2, dungeon));
		ArrayList<Switch> switches = dungeon.getSwitches();
		goal = new BoulderGoal(switches);
		player.setGoals(goal);
		assertEquals(false, goal.isSatisfied());
		
		dungeon.addEntity(new Boulder(0, 1, dungeon));
		for (Switch s : switches) s.updateTile();
		assertEquals(false, goal.isSatisfied());
		
		dungeon.addEntity(new Boulder(0, 2, dungeon));
		for (Switch s : switches) s.updateTile();
		assertEquals(true, goal.isSatisfied());
	}
	

	@Test
	public void testAndGoal()
	{
		goal = new AndGoal();
		((CompositeGoal) goal).addGoal(new EnemyGoal(player, 5));
		((CompositeGoal) goal).addGoal(new TreasureGoal(player, 5));
		player.setGoals(goal);
		assertEquals(false, goal.isSatisfied());	
		
		player.setKillCount(5);
		assertEquals(false, goal.isSatisfied());

		player.setTreasureCount(5);
		assertEquals(true, goal.isSatisfied());	
	}
	
	@Test
	public void testOrGoal()
	{
		goal = new OrGoal();
		((CompositeGoal) goal).addGoal(new EnemyGoal(player, 5));
		((CompositeGoal) goal).addGoal(new TreasureGoal(player, 5));
		player.setGoals(goal);
		assertEquals(false, goal.isSatisfied());	
		
		player.setKillCount(5);
		assertEquals(true, goal.isSatisfied());

		player.setTreasureCount(5);
		assertEquals(true, goal.isSatisfied());	
	}
}
