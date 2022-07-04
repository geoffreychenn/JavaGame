package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

public class TestExit {

	Player player;
	Dungeon dungeon2x1;
	Exit exit;
	
	public TestExit() {
		dungeon2x1 = new Dungeon(2, 1);
		player = new Player(0, 0, dungeon2x1);
		exit = new Exit(1, 0, dungeon2x1);
	}
	
	@Test
	public void testNoGoals() {
		dungeon2x1.addEntity(player);
		dungeon2x1.addEntity(exit);
		assertEquals(false, exit.checkPlayer());
		
		player.moveRight();
		dungeon2x1.updateMatrix(player, 0, true);
		assertEquals(true, exit.checkPlayer());
	}

}
