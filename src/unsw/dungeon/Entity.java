package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private BooleanProperty passable, active;
    private Dungeon dungeon;
    private Collision collider;
    private Sound sound;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y, Dungeon dungeon) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.passable = new SimpleBooleanProperty(true);
        this.active = new SimpleBooleanProperty(true);
        this.dungeon = dungeon;
        setCollider(new CollisionNone());
        setSound(new SoundNone());
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }
    
    public BooleanProperty passable() {
    	return passable;
    }
    
    public boolean isPassable() {
    	return passable.get();
    }
    
    public void setPassable(boolean bool) {
    	passable.set(bool);
    }
    
    public Dungeon getDungeon() {
    	return dungeon;
    }
    
    public void setCollider(Collision collider) {
    	this.collider = collider;
    }
    
    public void collide() {
    	collider.collide(this, dungeon);
    }
   
    public BooleanProperty active() {
    	return active;
    }
    
    public void setActive(boolean bool) {
    	active.set(bool);
    }
    
    public boolean isActive() {
    	return active.get();
    }
    
    public void playSound() {
    	this.sound.playSound();
    }
    
    public void setSound(Sound sound) {
    	this.sound = sound;
    }
}
