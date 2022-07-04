package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

public class TestKey {

	Player player1;
	Dungeon dungeon3x3;
	Key key1;
	Key key2;
	Door door1;
	Door door2;
	
	public TestKey() {
		dungeon3x3 = new Dungeon(3, 3);
		player1 = new Player(0, 0, dungeon3x3);
		key1 = new Key(0, 1, dungeon3x3);
		key2 = new Key(0, 2, dungeon3x3);	
		door1 = new Door(1, 1, dungeon3x3);
		door2 = new Door(2, 1, dungeon3x3);
	}
	
	@Test
	public void testKey() {
		dungeon3x3.addEntity(player1);
		dungeon3x3.addEntity(key1);
		assertEquals(0, key1.getId());
		dungeon3x3.addEntity(key2);
		assertEquals(1, key2.getId());
		dungeon3x3.addEntity(door1);
		assertEquals(0, door1.getId());
		dungeon3x3.addEntity(door2);
		assertEquals(1, door2.getId());
		
		assertEquals(null, player1.getKey());	//player should not start with a key
		player1.moveDown();
		assertEquals(key1, player1.getKey());	//player has key1
		assertEquals(0, player1.getKeyId());
		
		player1.moveDown();
		assertEquals(key1, player1.getKey());	//player still has key1
		assertEquals(0, player1.getKeyId());
		assertEquals(true, dungeon3x3.getTile(0, 2).getEntities().contains(key2));	//tile has key2
		
		player1.moveRight();
		player1.moveRight();				//player moves to (2, 2), under door2
		player1.moveUp();					//tries to move through door2
		player1.moveUp();	
		assertEquals(2, player1.getX());
		assertEquals(2, player1.getY());
		
		player1.moveLeft();
		player1.moveUp();	
		assertEquals(1, player1.getX());	//player unlocks door1 with key1
		assertEquals(1, player1.getY());	//should be on same time as door (1, 1)
		
		player1.moveUp();
		player1.moveDown();
		assertEquals(1, player1.getX());	//player can walk freely through open door
		assertEquals(1, player1.getY());
		
	}

}
