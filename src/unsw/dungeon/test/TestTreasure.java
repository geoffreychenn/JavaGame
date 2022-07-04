package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

public class TestTreasure {

	Dungeon dungeon3x1;
	Player player1;
	TreasureGoal goal;
	
	public TestTreasure() {
		dungeon3x1 = new Dungeon(3, 1);
		player1 = new Player(0, 0, dungeon3x1);
		goal = new TreasureGoal(player1, 5);
	}

	@Test
	public void testPickUp() {
		dungeon3x1.addEntity(player1);
		dungeon3x1.addEntity(new Treasure(1, 0, dungeon3x1));
		dungeon3x1.addEntity(new Treasure(2, 0, dungeon3x1));
		
		assertEquals(0, player1.getTreasureCount());
		
		player1.moveRight();
		assertEquals(1, player1.getTreasureCount());
		
		player1.moveRight();
		assertEquals(2, player1.getTreasureCount());		//player picks up treasure
	}
}
