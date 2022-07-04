package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

public class TestSword {

	Sword sword1;
	Sword sword2;
	Dungeon dungeon3x1;
	Dungeon dungeon4x1;
	Player player1;
	Player player2;
	
	
	public TestSword() {
		dungeon3x1 = new Dungeon(3, 1);
		dungeon4x1 = new Dungeon(4, 1);
		player1 = new Player(0, 0, dungeon3x1);
		player2 = new Player(0, 0, dungeon4x1);
		sword1 = new Sword(1, 0, dungeon3x1);
		sword2 = new Sword(2, 0, dungeon3x1);
		
	}

	@Test
	public void testPickupSword() {
		dungeon3x1.addEntity(player1);
		dungeon3x1.addEntity(sword1);
		dungeon3x1.addEntity(sword2);
		
		assertEquals(0, player1.getDurability()); 						//player does not have weapon equipped
		
		player1.moveRight();
		assertEquals(sword1.getDurability(), player1.getDurability());	//player picks up a weapon at full dirability
		
		player1.setDurability(3);
		player1.moveRight();
		assertEquals(3, player1.getDurability());						//player doesnt pick up a new sword when it has a sword equipped
	
		player1.moveLeft();
		player1.setDurability(0);										//player depletes sword			
		player1.moveRight();
		assertEquals(sword1.getDurability(), player1.getDurability());	//player can pick up new sword
	}
	
	@Test
	public void testSwordEnemy() {
		dungeon4x1.addEntity(player2);
		dungeon4x1.addEntity(new Enemy(1, 0, dungeon4x1));
		dungeon4x1.addEntity(new Enemy(2, 0, dungeon4x1));
		dungeon4x1.addEntity(new Enemy(3, 0, dungeon4x1));	//map with 3 enemies in a line

		player2.setDurability(2);							//give player 2 sword durability
		player2.moveRight();
		assertEquals(1, player2.getDurability());			//decrease sword durability on hitting an enemy
		assertEquals(true, player2.getAlive());				//player is still alive
		assertEquals(1, player2.getKillCount());			//increments kill count
		
		player2.moveRight();
		assertEquals(0, player2.getDurability());			//decrease sword durability on hitting an enemy
		assertEquals(true, player2.getAlive());				//player is still alive
		assertEquals(2, player2.getKillCount());			//increments kill count
		
		player2.moveRight();
		assertEquals(0, player2.getDurability());			//player has no durability and hits an enemy
		assertEquals(false, player2.getAlive());			//player is dead
		assertEquals(2, player2.getKillCount());			//does not increments kill count
		
	}

}
