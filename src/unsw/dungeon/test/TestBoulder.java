package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;
import unsw.dungeon.Boulder.Direction;

public class TestBoulder {

	Player player1;
	Boulder boulder1;
	Boulder boulder2;
	Dungeon dungeon5x5;
	Dungeon dungeon7x1;
	
	public TestBoulder() {
		dungeon5x5 = new Dungeon(5, 5);
		dungeon7x1 = new Dungeon(7, 1);
		player1 = new Player(0, 0, dungeon5x5);
		boulder1 = new Boulder(2, 2, dungeon5x5);
		boulder2 = new Boulder(0, 0, dungeon7x1);
	}
	
	@Test
	public void testBoulderMove() {			
		dungeon5x5.addEntity(player1);		//5x5 map with boulder in the middle
		dungeon5x5.addEntity(boulder1);		
		
		player1.attachBoulder(boulder1);			//player does not pickup boulder when not adjacent
		assertEquals(null, player1.getBoulder());
		
		player1.moveRight();
		player1.moveRight();
		player1.moveDown();
		assertEquals(null, player1.getBoulder());			//player does not have boulder
		assertEquals(null, boulder1.getAttached());
		
		//push and pull boulder to boundaries
		player1.attachBoulder(boulder1);
		assertEquals(Direction.UP, boulder1.getAttached());	//player has boulder
		assertEquals(2, boulder1.getX());					//boulder at (2, 2)
		assertEquals(2, boulder1.getY());					//player above at (2, 1)
		assertEquals(2, player1.getX());
		assertEquals(1, player1.getY());
		player1.moveDown();									//player can move axially
		assertEquals(2, boulder1.getX());					//boulder at (2, 3)
		assertEquals(3, boulder1.getY());					//player above at (2, 2)
		assertEquals(2, player1.getX());
		assertEquals(2, player1.getY());
		player1.moveDown();									
		assertEquals(2, boulder1.getX());					//boulder at (2, 4)
		assertEquals(4, boulder1.getY());					//player above at (2, 3)
		assertEquals(2, player1.getX());
		assertEquals(3, player1.getY());
		player1.moveDown();									//boulder blocked by boundary
		assertEquals(2, boulder1.getX());					//boulder at (2, 4)
		assertEquals(4, boulder1.getY());					//player above at (2, 3)
		assertEquals(2, player1.getX());
		assertEquals(3, player1.getY());
		player1.moveUp();									
		assertEquals(2, boulder1.getX());					//boulder at (2, 3)
		assertEquals(3, boulder1.getY());					//player above at (2, 2)
		assertEquals(2, player1.getX());
		assertEquals(2, player1.getY());
		player1.moveUp();									
		assertEquals(2, boulder1.getX());					//boulder at (2, 2)
		assertEquals(2, boulder1.getY());					//player above at (2, 1)
		assertEquals(2, player1.getX());
		assertEquals(1, player1.getY());
		player1.moveUp();									
		assertEquals(2, boulder1.getX());					//boulder at (2, 1)
		assertEquals(1, boulder1.getY());					//player above at (2, 0)
		assertEquals(2, player1.getX());
		assertEquals(0, player1.getY());
		player1.moveUp();									//player is blocked by boundary
		assertEquals(2, boulder1.getX());					//boulder at (2, 1)
		assertEquals(1, boulder1.getY());					//player above at (2, 0)
		assertEquals(2, player1.getX());
		assertEquals(0, player1.getY());
		player1.moveDown();									//put boulder back at original pos
		assertEquals(2, boulder1.getX());					//boulder at (2, 2)
		assertEquals(2, boulder1.getY());					//player above at (2, 1)
		assertEquals(2, player1.getX());
		assertEquals(1, player1.getY());
		
		player1.moveLeft();									//cannot move to side
		assertEquals(2, boulder1.getX());					//boulder at (2, 2)
		assertEquals(2, boulder1.getY());					//player above at (2, 1)
		assertEquals(2, player1.getX());
		assertEquals(1, player1.getY());
		
		player1.moveRight();								//cannot move to side
		assertEquals(2, boulder1.getX());					//boulder at (2, 2)
		assertEquals(2, boulder1.getY());					//player above at (2, 1)
		assertEquals(2, player1.getX());
		assertEquals(1, player1.getY());
		
		player1.releaseBoulder();
		assertEquals(null, player1.getBoulder());
		assertEquals(null, boulder1.getAttached());
		
		player1.moveRight();
		player1.moveDown();
		assertEquals(2, boulder1.getX());					//boulder at (2, 2)
		assertEquals(2, boulder1.getY());					//player on right at (3, 2)
		assertEquals(3, player1.getX());					
		assertEquals(2, player1.getY());	
		
		player1.attachBoulder(boulder1);
		assertEquals(Direction.RIGHT, boulder1.getAttached());	//player has boulder
		player1.moveLeft();
		assertEquals(1, boulder1.getX());					//boulder at (1, 2)
		assertEquals(2, boulder1.getY());					//player on right at (2, 2)
		assertEquals(2, player1.getX());					
		assertEquals(2, player1.getY());	
		
		player1.moveRight();
		assertEquals(2, boulder1.getX());					//boulder at (2, 2)
		assertEquals(2, boulder1.getY());					//player on right at (3, 2)
		assertEquals(3, player1.getX());					
		assertEquals(2, player1.getY());	
		
		player1.releaseBoulder();
		
		player1.moveDown();
		player1.moveLeft();
		
		assertEquals(2, boulder1.getX());					//boulder at (2, 2)
		assertEquals(2, boulder1.getY());					//player below at (2, 3)
		assertEquals(2, player1.getX());					
		assertEquals(3, player1.getY());	
		
		player1.attachBoulder(boulder1);
		assertEquals(Direction.DOWN, boulder1.getAttached());	//player has boulder
		player1.moveDown();
		assertEquals(2, boulder1.getX());					//boulder at (2, 3)
		assertEquals(3, boulder1.getY());					//player below at (2, 4)
		assertEquals(2, player1.getX());					
		assertEquals(4, player1.getY());
		
		player1.moveDown();									//player blocked by wall
		assertEquals(2, boulder1.getX());					//boulder at (2, 3)
		assertEquals(3, boulder1.getY());					//player below at (2, 4)
		assertEquals(2, player1.getX());					
		assertEquals(4, player1.getY());
		
		player1.moveUp();
		assertEquals(2, boulder1.getX());					//boulder at (2, 2)
		assertEquals(2, boulder1.getY());					//player below at (2, 3)
		assertEquals(2, player1.getX());					
		assertEquals(3, player1.getY());
		
		player1.releaseBoulder();
		
		player1.moveLeft();
		player1.moveUp();
		
		player1.attachBoulder(boulder1);
		assertEquals(Direction.LEFT, boulder1.getAttached());	//player has boulder
		
		player1.moveLeft();
		assertEquals(1, boulder1.getX());					//boulder at (1, 2)
		assertEquals(2, boulder1.getY());					//player on right at (0, 2)
		assertEquals(0, player1.getX());					
		assertEquals(2, player1.getY());	
		
		player1.moveLeft();									//player is blocked by wall
		assertEquals(1, boulder1.getX());					//boulder at (1, 2)
		assertEquals(2, boulder1.getY());					//player on right at (0, 2)
		assertEquals(0, player1.getX());					
		assertEquals(2, player1.getY());
		
		player1.moveRight();
		assertEquals(2, boulder1.getX());					//boulder at (2, 2)
		assertEquals(2, boulder1.getY());					//player on right at (1, 2)
		assertEquals(1, player1.getX());					
		assertEquals(2, player1.getY());	
		
		dungeon5x5.addEntity(new Boulder(3, 2, dungeon5x5));
		player1.moveRight();								//player cannot push boulder through boulder
		assertEquals(2, boulder1.getX());					//boulder at (2, 2)
		assertEquals(2, boulder1.getY());					//player on right at (1, 2)
		assertEquals(1, player1.getX());					
		assertEquals(2, player1.getY());	
		
		dungeon5x5.addEntity(new Wall(0, 2, dungeon5x5));
		player1.moveLeft();									//player blocked by wall
		assertEquals(2, boulder1.getX());					//boulder at (2, 2)
		assertEquals(2, boulder1.getY());					//player on right at (1, 2)
		assertEquals(1, player1.getX());					
		assertEquals(2, player1.getY());
		
	}
	
	@Test
	public void testBoulderCollideEntity() {
		Door door = new Door(4, 0, dungeon7x1);
		door.unlock();
		Portal portal = new Portal(6, 0, dungeon7x1);
		
		dungeon7x1.addEntity(boulder2);
		dungeon7x1.addEntity(new Treasure(1, 0, dungeon7x1));
		dungeon7x1.addEntity(new InvincibilityPotion(2, 0, dungeon7x1));
		dungeon7x1.addEntity(new Sword(3, 0, dungeon7x1));
		dungeon7x1.addEntity(door);
		dungeon7x1.addEntity(new Key(5, 0, dungeon7x1));
		dungeon7x1.addEntity(portal);
		
		boulder2.moveRight();
		assertEquals(1, boulder2.getX());
		boulder2.moveRight();
		assertEquals(2, boulder2.getX());
		boulder2.moveRight();
		assertEquals(3, boulder2.getX());
		boulder2.moveRight();
		assertEquals(4, boulder2.getX());
		boulder2.moveRight();
		assertEquals(5, boulder2.getX());
		boulder2.moveRight();
		assertEquals(5, boulder2.getX());
		
		dungeon7x1.addEntity(new Exit(4, 0, dungeon7x1));
		boulder2.moveLeft();
		assertEquals(5, boulder2.getX());
		
		dungeon7x1.removeEntity(portal);
		dungeon7x1.addEntity(new Enemy(6, 0, dungeon7x1));
		boulder2.moveRight();
		assertEquals(5, boulder2.getX());
		
	}
	
	
	

}
