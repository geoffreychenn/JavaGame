package unsw.dungeon;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Switch extends Entity
{
	private boolean active = false;
	private Tile tile;
	
	public Switch(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		setPassable(true);
		tile = dungeon.getTile(x, y);
        setCollider(new CollisionNone());
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void updateTile() {
		for (Entity e : tile.getEntities()) {
			if(e instanceof Boulder) {
				if (!active) {
					//delays sound playing
					try {
						Thread.sleep(180);
						this.setSound(new SoundSwitch());
						this.playSound();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					
					setActive(true);
				}
				return;
			}
		}
		setActive(false);
	}

}
