package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

public class TestEnemy {

	Player player1;
	Player player2;
	Dungeon dungeon5x5;
	Dungeon dungeon7x1;
	Dungeon dungeon1x1;
	Enemy enemy1;
	Enemy enemy2;
	EnemyGoal goal;
	Door door1;
	
	public TestEnemy() {
		dungeon1x1 = new Dungeon(1, 1);
		dungeon5x5 = new Dungeon(5, 5);	
		dungeon7x1 = new Dungeon(7 ,1);
		player1 = new Player(0, 0, dungeon5x5);
		player2 = new Player(0, 0, dungeon1x1);
		enemy1 = new Enemy(4, 4, dungeon5x5);
		enemy2 = new Enemy(0, 0, dungeon7x1);
		goal = new EnemyGoal(player2, 5);
		door1 = new Door(1 ,4 ,dungeon5x5);
	}
	
	@Test
	public void testEnemyMovement() {
//		 0 1 2 3 4		//grid of the map used for testing
//		+-+-+-+-+-+		//W = wall
//	   0|P|B| | | |		//P = player
//		+-+-+-+-+-+		//B = boulder
//	   1| |L| |W| |		//L = door (locked)
//		+-+-+-+-+-+		//U = door (unlocked)
//	   2| |W| |W| |		//E = enemy
//		+-+-+-+-+-+
//	   3| |W| |W| |
//		+-+-+-+-+-+
//	   4| |U| |W|E|
//		+-+-+-+-+-+
		
		dungeon5x5.addEntity(player1);
		dungeon5x5.addEntity(enemy1);
		
		dungeon5x5.addEntity(new Wall(3, 4, dungeon5x5));				//enemy should navigate around walls and obstacles
		dungeon5x5.addEntity(new Wall(3, 3, dungeon5x5));				
		dungeon5x5.addEntity(new Wall(3, 2, dungeon5x5));
		dungeon5x5.addEntity(new Wall(3, 1, dungeon5x5));
		
		dungeon5x5.addEntity(new Boulder(1, 0, dungeon5x5));			//enemy should not walk into boulder
		dungeon5x5.addEntity(new Door(1, 1, dungeon5x5));				//enemy should not walk into locked door
		dungeon5x5.addEntity(new Wall(1, 2, dungeon5x5));
		dungeon5x5.addEntity(new Wall(1, 3, dungeon5x5));
		dungeon5x5.addEntity(door1); 									//enemt should walk through unlocked doors
		door1.unlock();
		
		assertEquals(4, enemy1.getX());		//enemy1 starts at (4, 4)
		assertEquals(4, enemy1.getY());
		enemy1.move();
		assertEquals(4, enemy1.getX());		//moves to (4, 3)
		assertEquals(3, enemy1.getY());
		enemy1.move();
		assertEquals(4, enemy1.getX());		//moves to (4, 2)
		assertEquals(2, enemy1.getY());
		enemy1.move();
		assertEquals(4, enemy1.getX());		//moves to (4, 1)
		assertEquals(1, enemy1.getY());
		enemy1.move();
		assertEquals(4, enemy1.getX());		//moves to (4, 0)
		assertEquals(0, enemy1.getY());
		enemy1.move();
		assertEquals(3, enemy1.getX());		//moves to (3, 0)
		assertEquals(0, enemy1.getY());
		enemy1.move();
		assertEquals(2, enemy1.getX());		//moves to (2, 0)
		assertEquals(0, enemy1.getY());
		enemy1.move();
		assertEquals(2, enemy1.getX());		//moves to (2, 1), navigates around boulder
		assertEquals(1, enemy1.getY());
		enemy1.move();
		assertEquals(2, enemy1.getX());		//moves to (2, 2), navigates around locked doors
		assertEquals(2, enemy1.getY());
		
		for(int i = 0; i < 7; i ++) {
			enemy1.move();
		}
		
		assertEquals(0, enemy1.getX());							//enemy walked through unlocked door
		assertEquals(1, enemy1.getY());							//enemy is 1 spot away from player
		assertEquals(true, player1.getAlive());
		assertEquals(0, player1.getDurability());				//enemy can kill player
		assertEquals(0, player1.getInvinvibilityDuration());
		enemy1.move();
		assertEquals(false, player1.getAlive());				//player is dead	
	}
	
	@Test
	public void testEnemyEntityCollide() {
		Enemy enemy = new Enemy(6, 0, dungeon7x1);
		Player player = new Player(6, 0, dungeon7x1);
		Portal portal = new Portal(5, 0, dungeon7x1);
		Sword sword = new Sword(4, 0, dungeon7x1);
		Key key = new Key(3, 0, dungeon7x1);
		InvincibilityPotion potion = new InvincibilityPotion(2, 0, dungeon7x1);
		Treasure treasure = new Treasure(1, 0, dungeon7x1);
		dungeon7x1.addEntity(enemy);
		dungeon7x1.addEntity(player);									
		dungeon7x1.addEntity(portal);		//enemy should not teleport with portals
		dungeon7x1.addEntity(sword);		//enemy should not pick up sword		
		dungeon7x1.addEntity(key);			//enemy should not pick up keys
		dungeon7x1.addEntity(potion);		//enemy should not pick up potion		
		dungeon7x1.addEntity(treasure);		//enemy should not pick up treasure
		dungeon7x1.addEntity(enemy2);
		
		for(int i = 0; i < dungeon7x1.getWidth() - 1; i++) {
			enemy2.move();
		}
		assertEquals(5, enemy2.getX());		//enemy does not move over another enemy
		assertEquals(0, enemy2.getY());		//in the real game the other enemy would move too
		
		assertEquals(true, dungeon7x1.getTile(1, 0).getEntities().contains(treasure));
		assertEquals(true, dungeon7x1.getTile(2, 0).getEntities().contains(potion));
		assertEquals(true, dungeon7x1.getTile(3, 0).getEntities().contains(key));
		assertEquals(true, dungeon7x1.getTile(4, 0).getEntities().contains(sword));
		assertEquals(true, dungeon7x1.getTile(5, 0).getEntities().contains(portal));		//all entities are still there
		
		assertEquals(0, player.getDurability());
		assertEquals(0, player.getInvinvibilityDuration());
		assertEquals(null, player.getKey());
		assertEquals(0, player.getTreasureCount());			//nothing changed with player
	}

}
