package unsw.dungeon;

public class Sword extends Entity {

	private int durability;
	
    public Sword(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
        setPassable(true);
        this.durability = 5;
        setCollider(new CollisionSword());
    }
    
    public int getDurability() {
    	return durability;
    }

}
