package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UI
{
	private AnchorPane dungeonPane;
	String fileName;
	private int moveCount, killCount, treasureCount, durability, duration;
	private boolean keyActive, won, lost;
	private long startTime;
	
	private ImageView sword;
	private Label durabilityLabel;
	private ImageView potion;
	private Label invincibilityLabel;
	private ImageView key;
	private Button objectivesButton;
	private Label objectivesLabel;
	
	public UI(String file)
	{
		fileName = file;
		moveCount = killCount = treasureCount = durability = duration = 0;
		keyActive = won = lost = false;
		startTime = System.nanoTime();
	}

	public int getMoveCount()
	{
		return moveCount;
	}

	public void updateMoveCount()
	{
		this.moveCount++;
	}
	
	public int getKillCount()
	{
		return killCount;
	}

	public void updateKillCount(int killCount)
	{
		this.killCount = killCount;
	}

	public int getTreasureCount()
	{
		return treasureCount;
	}

	public void updateTreasureCount(int treasureCount)
	{
		this.treasureCount = treasureCount;
	}

	public int getDurability()
	{
		return durability;
	}

	public void updateDurability(int amount)
	{
		durability = amount;
		if (durability > 0)
		{
			sword.toFront();
			durabilityLabel.setText(": " + durability); 
			durabilityLabel.setVisible(true);
		}
		else 
		{
			durabilityLabel.setVisible(false);
			sword.toBack();
		}
	}

	public int getDuration()
	{
		return duration;
	}

	public void updateDuration(int amount)
	{
		duration = amount;
		if (duration > 0)
		{
			potion.toFront();
			invincibilityLabel.setText(": " + duration); 
			invincibilityLabel.setVisible(true);
		}
		else 
		{
			invincibilityLabel.setVisible(false);
			potion.toBack();
		}
	}
	
	public boolean getKeyActive()
	{
		return keyActive;
	}
	
	public void updateKeyActive(boolean bool)
	{
		keyActive = bool;
		if (keyActive) key.toFront();
		else key.toBack();
	}
	
	public void setLost(boolean lost)
	{
		this.lost = lost;
		if (lost) loadEnd();
	}
	
	public boolean hasLost()
	{
		return lost;
	}
	
	public void setWon(boolean won)
	{
		this.won = won;
		if (won) loadEnd();
	}
	
	public boolean hasWon()
	{
		return won;
	}
	
	public void setDurabilityLabel(Label label)
	{
		durabilityLabel = label;
		durabilityLabel.setTextFill(Color.web("#ffffff"));
		durabilityLabel.setFont(new Font("Arial", 27));
		durabilityLabel.setVisible(false);
	}
	
	public void setInvincibilityLabel(Label label)
	{
		invincibilityLabel = label;
		invincibilityLabel.setTextFill(Color.web("#ffffff"));
		invincibilityLabel.setFont(new Font("Arial", 27));
		invincibilityLabel.setVisible(false);
	}
	
	public void setObjectivesLabel(Label label)
	{
		objectivesLabel = label;
		objectivesLabel.setTextFill(Color.web("#ffffff"));
		objectivesLabel.setVisible(false);
	}
	
	public void setObjectivesButton(Button button)
	{
		objectivesButton = button;
		objectivesButton.setOnAction(e -> showObjectives());
	}
	
	public void setSwordView(ImageView sword)
	{
		this.sword = sword;
		this.sword.toBack();
	}
	
	public void setPotionView(ImageView potion)
	{
		this.potion = potion;
		this.potion.toBack();
	}
	
	public void setKeyView(ImageView key)
	{
		this.key = key;
		this.key.toBack();
	}
	
	public void setDungeonPane(AnchorPane pane)
	{
		dungeonPane = pane;
	}
	
	private void showObjectives()
	{
		if (objectivesLabel.isVisible()) objectivesLabel.setVisible(false);
		else objectivesLabel.setVisible(true);
		dungeonPane.requestFocus();
	}
	
	@FXML
	private void loadEnd()
	{
        try
		{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("EndView.fxml"));
            loader.setController(new EndScreenController(fileName, won, killCount, moveCount, treasureCount, startTime));
			loader.load();
			
			Scene scene = new Scene(loader.getRoot());
			DungeonApplication.getPrimaryStage().setScene(scene);
		} 
        catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
