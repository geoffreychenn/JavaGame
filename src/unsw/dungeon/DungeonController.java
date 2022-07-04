package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

	@FXML
    private GridPane squares;
	
	@FXML
	private AnchorPane pane;

    private List<ImageView> initialEntities;

    private Player player;
    
    private UI ui;

    private Dungeon dungeon;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.ui = player.getObserver();
        this.initialEntities = new ArrayList<>(initialEntities);
    }
    
    @FXML
    public void initialize() {
        Image ground = new Image("/dirt_0_new.png");
        
        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }
        
        ui.setDungeonPane(pane);
        
        ImageView sword = new ImageView("/greatsword_1_new.png");
        squares.add(sword, durabilitySwordX(), dungeonBottomY());
        ui.setSwordView(sword);
        
        ImageView potion = new ImageView("/brilliant_blue_new.png");
        squares.add(potion, durationPotionX(), dungeonBottomY());
        ui.setPotionView(potion);
        
        ImageView key = new ImageView("/key.png");
        squares.add(key, activeKeyX(), dungeonBottomY());
        ui.setKeyView(key);

        Label duraLabel = new Label();
        duraLabel.setLayoutX(32 * durabilitySwordX() + 32);
        duraLabel.setLayoutY(32 * dungeonBottomY());
        pane.getChildren().add(duraLabel);
        ui.setDurabilityLabel(duraLabel);
        
        Label invincLabel = new Label();
        invincLabel.setLayoutX(32 * durationPotionX() + 32);
        invincLabel.setLayoutY(32 * dungeonBottomY());
        pane.getChildren().add(invincLabel);
        ui.setInvincibilityLabel(invincLabel);
        
        Button objectivesBtn = new Button("Objectives");
        AnchorPane.setRightAnchor(objectivesBtn, 32.0);
        pane.getChildren().add(objectivesBtn);
        ui.setObjectivesButton(objectivesBtn);
        
        Label text = new Label(player.getGoals().toString());
        AnchorPane.setRightAnchor(text, 0.0);
        AnchorPane.setTopAnchor(text, 32.0);
        pane.getChildren().add(text);
        ui.setObjectivesLabel(text);

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case W:
        case UP:
        	if (player.moveUp()) handleEnemyMovement();
            break;
        case S:
        case DOWN:
        	if (player.moveDown()) handleEnemyMovement();
            break;
        case A:
        case LEFT:
        	if (player.moveLeft()) handleEnemyMovement();
            break;
        case D:
        case RIGHT:
        	if (player.moveRight()) handleEnemyMovement();
            break;
        default:
            break;
        }
    }
    
    @FXML
    public void handleMouseClick(MouseEvent event) {
    	int x = (int) event.getX()/32;
    	int y = (int) event.getY()/32;
    	player.toggleBoulder(dungeon.getTile(x, y));
    	player.dropKey(x, y);
    }
    
    private void handleEnemyMovement()
    {
		ArrayList<Enemy> enemies = dungeon.getEnemies();
		for (int i = 0; i < enemies.size(); i++) 
		{
			Enemy e = enemies.get(i);
			if (e.isActive()) 
			{
				if (dungeon.getTurn() % e.getDelay() == 0) e.move();
			}
			else 
			{
				enemies.remove(e);
				i--;
			}
		}
    }
    
    public int durabilitySwordX()
    {
    	return 2 * dungeon.getWidth() / 3;
    }
    
    public int durationPotionX()
    {
    	return dungeon.getWidth() / 3 - 1;
    }
    
    public int activeKeyX()
    {
    	return dungeon.getWidth() / 2;
    }
    
    public int dungeonBottomY()
    {
    	return dungeon.getHeight() - 1;
    }
}

