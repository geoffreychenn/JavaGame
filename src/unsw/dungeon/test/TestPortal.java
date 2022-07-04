package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

public class TestPortal {

	Player player1;
	Dungeon dungeon3x3;
	Portal portal1;
	Portal portal2;
	Portal portal3;
	Portal portal4;
	
	public TestPortal() {
		dungeon3x3 = new Dungeon(3, 3);
		player1 = new Player(1, 1, dungeon3x3);
		portal1 = new Portal(0, 0, dungeon3x3);
		portal2 = new Portal(2, 0, dungeon3x3);
		portal3 = new Portal(0, 2, dungeon3x3);
		portal4 = new Portal(2, 2, dungeon3x3);
	}
	
	@Test
	public void testPortal() {
		dungeon3x3.addEntity(player1);
		dungeon3x3.addEntity(portal1);
		assertEquals(0, portal1.getId());
		dungeon3x3.addEntity(portal2);
		assertEquals(0, portal2.getId());
		dungeon3x3.addEntity(portal3);
		assertEquals(1, portal3.getId());
		dungeon3x3.addEntity(portal4);
		assertEquals(1, portal4.getId());
		
		player1.moveUp();					//player starts at (1, 1)
		assertEquals(1, player1.getX());	//moves up to (1, 0)
		assertEquals(0, player1.getY());
		
		player1.moveRight();				//moves right to (2, 0)
		assertEquals(0, player1.getX());	//gets teleported to (0, 0)
		assertEquals(0, player1.getY());
		
		player1.moveDown();
		assertEquals(0, player1.getX());	//moves down to (0, 1)
		assertEquals(1, player1.getY());
		
		player1.moveDown();					//moves to (0, 2)
		assertEquals(2, player1.getX());	//gets teleported to (2, 2)
		assertEquals(2, player1.getY());
		
	}

}
