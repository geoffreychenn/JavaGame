package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

public class TestInvincibilityPotion {

	Player player1;
	Player player2;
	Player player3;
	Dungeon dungeon4x1;
	Dungeon dungeon3x1;
	Dungeon dungeon5x1;
	InvincibilityPotion potion1;
	InvincibilityPotion potion2;
	InvincibilityPotion potion3;
	Enemy enemy;
	
	public TestInvincibilityPotion() {
		dungeon4x1 = new Dungeon(4, 1);
		dungeon3x1 = new Dungeon(3, 1);
		dungeon5x1 = new Dungeon(5, 1);
		player1 = new Player(0, 0, dungeon4x1);
		player2 = new Player(0, 0, dungeon3x1);
		player3 = new Player(1, 0, dungeon5x1);
		enemy = new Enemy(0, 0, dungeon5x1);
		potion1 = new InvincibilityPotion(1, 0, dungeon4x1);
		potion2 = new InvincibilityPotion(2, 0, dungeon4x1);
		potion3 = new InvincibilityPotion(4, 0, dungeon5x1);
	}

	@Test
	public void testPotionPickUp() {
		dungeon4x1.addEntity(player1);
		dungeon4x1.addEntity(potion1);
		dungeon4x1.addEntity(potion2);
		
		assertEquals(0, player1.getInvinvibilityDuration());								//player starts off with 0 duration
		
		player1.moveRight();
		assertEquals(potion1.getDuration() - 1, player1.getInvinvibilityDuration());		//player gets potion
		
		player1.moveLeft();
		assertEquals(potion1.getDuration() - 2, player1.getInvinvibilityDuration());		//potion duration decreases after moving
		
		player1.moveUp();
		assertEquals(potion1.getDuration() - 2, player1.getInvinvibilityDuration());		//potion duration doesnt decrease if player doesn not move
	
		player1.moveRight();
		player1.moveRight();
		assertEquals(potion2.getDuration() - 1, player1.getInvinvibilityDuration());		//potion refils when walking over a new one even with one already equipped
	
	}
	
	@Test 
	public void testPotionEnemy() {
		dungeon3x1.addEntity(player2);
		player2.setInvinvibilityDuration(2);					//give player some invincibility
		dungeon3x1.addEntity(new Enemy(2, 0, dungeon3x1));		//put enemy at end of level
		player2.moveRight();
		
		assertEquals(1, player2.getInvinvibilityDuration());	//invincibility decrements as moves
		assertEquals(true, player2.getAlive());					//ensure player is alive
				
		player2.moveRight();
		assertEquals(0, player2.getInvinvibilityDuration());	//player moves and it runs out
		assertEquals(true, player2.getAlive());					//player collides with enemy and kills enemy
		assertEquals(1, player2.getKillCount());				//increments kill counter
		
		dungeon3x1.addEntity(new Enemy(1, 0, dungeon3x1));
		
		player2.moveLeft();
		assertEquals(0, player2.getInvinvibilityDuration());	//player moves with no more invincibility
		assertEquals(false, player2.getAlive());				//player dies when it makes contact with enemy again
		assertEquals(1, player2.getKillCount());				//does not incrememnt kill counter
		
	}
	
	@Test
	public void testEnemy() {
		dungeon5x1.addEntity(player3);
		dungeon5x1.addEntity(enemy);
		dungeon5x1.addEntity(potion3);
		
		assertEquals(0, enemy.getX());
		assertEquals(1, player3.getX());
		assertEquals(4, 0, potion3.getX());
			
		player3.moveRight();							//enemy moves towards player when not invincible
		dungeon5x1.updateMatrix(player3, 1, true);
		enemy.move();
		dungeon5x1.updateMatrix(enemy, 0, true);
		assertEquals(1, enemy.getX());
		assertEquals(2, player3.getX());
		
		player3.moveRight();
		dungeon5x1.updateMatrix(player3, 2, true);
		
		enemy.move();
		dungeon5x1.updateMatrix(enemy, 1, true);
		assertEquals(2, enemy.getX());
		assertEquals(3, player3.getX());
		
		player3.moveRight();							//enemy navigates away from player while invincible
		dungeon5x1.updateMatrix(player3, 3, true);
		enemy.move();
		dungeon5x1.updateMatrix(enemy, 2, true);
		assertEquals(1, enemy.getX());
		assertEquals(4, player3.getX());
		
		player3.moveLeft();							
		dungeon5x1.updateMatrix(player3, 4, true);
		enemy.move();
		dungeon5x1.updateMatrix(enemy, 1, true);
		assertEquals(0, enemy.getX());
		assertEquals(3, player3.getX());
		
	}
	
}
