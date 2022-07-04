package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

public class TestPlayerMovement {

	Player player1;
	Dungeon dungeon2x2;
	
	Player player2;
	Dungeon dungeon3x3;
	
	Wall wall;
	Boulder boulder;
	
	Door door;
	
	public TestPlayerMovement() {
		dungeon2x2 = new Dungeon(2, 2);
		player1 = new Player(0, 0, dungeon2x2);
		
		dungeon3x3 = new Dungeon(3, 3);
		player2 = new Player(0, 0, dungeon3x3);
		
		wall = new Wall(1, 1, dungeon3x3);
		boulder = new Boulder(1, 1, dungeon3x3);
		door = new Door(1, 1, dungeon3x3);
		
	}
	
	@Test
	public void testplayerMove() {
		
		//test edges, see if player1 can go out of bounds, (2x2 dungeon board)
		dungeon2x2.addEntity(player1);
		
		assertEquals(0, player1.getX());
		assertEquals(0, player1.getY());
		assert(dungeon2x2.getTile(0, 0).getEntities().contains(player1)); 	//make sure player1 is in the correct position on load
		
		player1.moveUp();					//check to see if player1 can move out of bounds to (0, -1)
		assertEquals(0, player1.getX());	//player1 remains at (0, 0)
		assertEquals(0, player1.getY());
		
		player1.moveRight();				//check to see if player1 can move to (1, 0), valid move
		assertEquals(1, player1.getX());	//player1 moves from (0, 0) to (1, 0)
		assertEquals(0, player1.getY());
		
		player1.moveRight();				//check to see if player1 can move out of bounds to (2, 0)
		assertEquals(1, player1.getX());	//player1 remains at (1, 0)
		assertEquals(0, player1.getY());
		
		player1.moveDown();					//check to see if player1 can move to (1, 1), valid move
		assertEquals(1, player1.getX());	//player1 moves from (1, 0) to (1, 1)
		assertEquals(1, player1.getY());
		
		player1.moveDown();					//check to see if player1 can move out of bounds to (1, 2)
		assertEquals(1, player1.getX());	//player1 remains at (1, 1)
		assertEquals(1, player1.getY());
		
		player1.moveLeft();					//check to see if player1 can move to (0, 1)
		assertEquals(0, player1.getX());	//player1 moves from (1, 1) to (0, 1)
		assertEquals(1, player1.getY());
		
		player1.moveLeft();					//check to see if player1 can move out of bounds to (-1, 1)
		assertEquals(0, player1.getX());	//player1 remains at (0, 1)
		assertEquals(1, player1.getY());
	}

	@Test
	public void testPlayerMoveWalls() {
		//test if the player can move through walls
		
		dungeon3x3.addEntity(player2);
		dungeon3x3.addEntity(wall); 	//create 3x3 dungeon with wall at (1, 1)
		assert(dungeon3x3.getTile(wall.getX(), wall.getY()).getEntities().contains(wall));
		
		player2.moveRight();				//move player above the wall
		assertEquals(1, player2.getX());	//ensure player is at (1, 0)
		assertEquals(0, player2.getY());
		
		player2.moveDown();					//player tries to move to wall
		assertEquals(1, player2.getX());	//player shouldnt move
		assertEquals(0, player2.getY());
		
		player2.moveRight();
		player2.moveDown();					//move player to right of wall
		assertEquals(2, player2.getX());	//player is at (2, 1)
		assertEquals(1, player2.getY());
		
		player2.moveLeft();					//player tries to move to wall
		assertEquals(2, player2.getX());	//player shouldnt move
		assertEquals(1, player2.getY());
		
		player2.moveDown();
		player2.moveLeft();					//move player below wall
		assertEquals(1, player2.getX());	//player is at (1, 2)
		assertEquals(2, player2.getY());
		
		player2.moveUp();					//player tries to move to wall
		assertEquals(1, player2.getX());	//player shouldnt move
		assertEquals(2, player2.getY());
		
		player2.moveLeft();
		player2.moveUp();					//move player to left of wall
		assertEquals(0, player2.getX());	//player is at (0, 1)
		assertEquals(1, player2.getY());
		
		player2.moveRight();				//player tries to move to wall
		assertEquals(0, player2.getX());	//player shouldnt move
		assertEquals(1, player2.getY());
	}
	
	@Test
	public void testPlayerMoveBoulder() {
		//test if player can move through boulders, boulder does not move unless grabbed by player
		
		dungeon3x3.removeEntity(wall);
		assert(!dungeon3x3.getTile(1, 1).getEntities().contains(wall));
		dungeon3x3.addEntity(boulder);
		assert(dungeon3x3.getTile(1, 1).getEntities().contains(boulder));
		
		player2.moveUp();					//move player back to original position
		assertEquals(0, player2.getX());	//should be at (0, 0)
		assertEquals(0, player2.getY());
		
		player2.moveRight();				//move player above the boulder
		assertEquals(1, player2.getX());	//ensure player is at (1, 0)
		assertEquals(0, player2.getY());
		
		player2.moveDown();					//player tries to move to boulder
		assertEquals(1, player2.getX());	//player shouldnt move
		assertEquals(0, player2.getY());
		
		player2.moveRight();
		player2.moveDown();					//move player to right of boulder
		assertEquals(2, player2.getX());	//player is at (2, 1)
		assertEquals(1, player2.getY());
		
		player2.moveLeft();					//player tries to move to boulder
		assertEquals(2, player2.getX());	//player shouldnt move
		assertEquals(1, player2.getY());
		
		player2.moveDown();
		player2.moveLeft();					//move player below boulder
		assertEquals(1, player2.getX());	//player is at (1, 2)
		assertEquals(2, player2.getY());
		
		player2.moveUp();					//player tries to move to boulder
		assertEquals(1, player2.getX());	//player shouldnt move
		assertEquals(2, player2.getY());
		
		player2.moveLeft();
		player2.moveUp();					//move player to left of boulder
		assertEquals(0, player2.getX());	//player is at (0, 1)
		assertEquals(1, player2.getY());
		
		player2.moveRight();				//player tries to move to boulder
		assertEquals(0, player2.getX());	//player shouldnt move
		assertEquals(1, player2.getY());
		
		assertEquals(1, boulder.getX());	//boulder shouldnt have moved
		assertEquals(1, boulder.getY());

	}
	
	@Test
	public void testPlayerMoveDoor() {
		dungeon3x3.addEntity(player2);
		assert(!dungeon3x3.getTile(1, 1).getEntities().contains(wall));
		dungeon3x3.addEntity(door);
		assert(dungeon3x3.getTile(1, 1).getEntities().contains(door));
		
		assert(door.isPassable() == false); //make sure door starts locked
		
		player2.moveUp();					//move player back to original position
		assertEquals(0, player2.getX());	//should be at (0, 0)
		assertEquals(0, player2.getY());
		
		player2.moveRight();				//move player above the door
		assertEquals(1, player2.getX());	//ensure player is at (1, 0)
		assertEquals(0, player2.getY());
		
		player2.moveDown();					//player tries to move to door
		assertEquals(1, player2.getX());	//player shouldnt move
		assertEquals(0, player2.getY());
		
		player2.moveRight();
		player2.moveDown();					//move player to right of door
		assertEquals(2, player2.getX());	//player is at (2, 1)
		assertEquals(1, player2.getY());
		
		player2.moveLeft();					//player tries to move to door
		assertEquals(2, player2.getX());	//player shouldnt move
		assertEquals(1, player2.getY());
		
		player2.moveDown();
		player2.moveLeft();					//move player below door
		assertEquals(1, player2.getX());	//player is at (1, 2)
		assertEquals(2, player2.getY());
		
		player2.moveUp();					//player tries to move to door
		assertEquals(1, player2.getX());	//player shouldnt move
		assertEquals(2, player2.getY());
		
		player2.moveLeft();
		player2.moveUp();					//move player to left of door
		assertEquals(0, player2.getX());	//player is at (0, 1)
		assertEquals(1, player2.getY());
		
		player2.moveRight();				//player tries to move to door
		assertEquals(0, player2.getX());	//player shouldnt move
		assertEquals(1, player2.getY());
		
		assertEquals(1, boulder.getX());	//boulder shouldnt have moved
		assertEquals(1, boulder.getY());
	}
	
}
