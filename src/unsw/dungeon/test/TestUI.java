package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

public class TestUI
{
	Dungeon dungeon;
	
	Player player;
	
	UI ui;
	
	public TestUI()
	{
		dungeon = new Dungeon(1, 1);
		player = new Player(0, 0, dungeon);
		ui = new UI(null);
		player.attachObserver(ui);
	}
	
	@Test
	public void testHasLost()
	{
		assertEquals(ui.hasLost(), false);
		player.setAlive(false);
		assertEquals(ui.hasLost(), true);
	}
	
	@Test
	public void testDurabilityCount()
	{
		assertEquals(ui.getDurability(), 0);
		player.setDurability(2);
		assertEquals(ui.getDurability(), 2);
	}
	
	@Test
	public void testTreasureCount()
	{
		assertEquals(ui.getTreasureCount(), 0);
		player.setTreasureCount(3);
		assertEquals(ui.getTreasureCount(), 3);
	}
	
	@Test
	public void testDurationCount()
	{
		assertEquals(ui.getDuration(), 0);
		player.setInvinvibilityDuration(4);
		assertEquals(ui.getDuration(), 4);
	}
	
	@Test
	public void testKillCount()
	{
		assertEquals(ui.getKillCount(), 0);
		player.setKillCount(1);
		assertEquals(ui.getKillCount(), 1);
	}
	
	@Test
	public void testHasWon()
	{
		assertEquals(ui.hasWon(), false);
		Goal g = new EnemyGoal(player, 1);
		player.setKillCount(1);
		player.setGoals(g);
		player.checkGoals();
		assertEquals(ui.hasWon(), true);
	}
}
