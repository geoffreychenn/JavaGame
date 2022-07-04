package unsw.dungeon;

public class InvincibilityPotion extends Entity {

	private int duration;
	
	public InvincibilityPotion(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		setPassable(true);
        setCollider(new CollisionInvincibilityPotion());
		this.duration = 11;	//cos 10 just wont do
	}
	
	public int getDuration() {
		return duration;
	}

}
