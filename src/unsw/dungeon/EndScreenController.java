package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class EndScreenController
{
	@FXML
	AnchorPane endPane;
	
	@FXML
	Label msgLbl;
	
	@FXML
	Label killsLbl;
	
	@FXML
	Label treasureLbl;
	
	@FXML
	Label movesLbl;
	
	@FXML
	Label timeLbl;
	
	@FXML
	Button retryBtn;
	
	@FXML
	Button nextBtn;
	
	@FXML
	Button menuBtn;
	
	private double startTimeNano;
	private int killCount, moveCount, treasureCount;
	private boolean won;
	private String fileName;
	
	public EndScreenController(String fileName, boolean hasWon, int kills, int moves, int treasures, long startTime)
	{
		won = hasWon;
		killCount = kills;
		treasureCount = treasures;
		moveCount = moves;
		this.fileName = fileName;
		startTimeNano = startTime;
	}
	
	@FXML
	public void initialize()
	{
		if (won) 
		{
			if (fileName.contains("05")) 
			{
				msgLbl.setText("YOU WON!");
				nextBtn.setDisable(true);
				nextBtn.setVisible(false);
			}
			else if (fileName.contains("01")) msgLbl.setText("YOU BEAT THE FIRST LEVEL!"); 
			else if (fileName.contains("02")) msgLbl.setText("YOU BEAT THE SECOND LEVEL!"); 
			else if (fileName.contains("03")) msgLbl.setText("YOU BEAT THE THIRD LEVEL!"); 
			else if (fileName.contains("04")) msgLbl.setText("YOU BEAT THE FOURTH LEVEL!"); 
			retryBtn.setText("Play again");
		}
		else 
		{
			msgLbl.setText("YOU LOST!");
			retryBtn.setText("Try again");
			nextBtn.setDisable(true);
			nextBtn.setVisible(false);
		}
		
		killsLbl.setText("Kills: " + killCount);
		treasureLbl.setText("Treasures: " + treasureCount);
		movesLbl.setText("Moves: " + moveCount);
		timeLbl.setText("Time: " + time());
	}
	
	@FXML
	public void handleRetryBtn(ActionEvent event) throws IOException
	{
		loadDungeon(fileName);
	}
	
	@FXML
	public void handleNextBtn(ActionEvent event) throws IOException
	{
		if (fileName.contains("level_01")) loadDungeon("level_02.json");
		else if (fileName.contains("level_02")) loadDungeon("level_03.json");
		else if(fileName.contains("level_03")) loadDungeon("level_04.json");
		else if(fileName.contains("level_04")) loadDungeon("level_05.json");
	}
	
	@FXML
	public void handleMenuBtn(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        loader.setController(new MainMenuController());
		loader.load();
		
		Scene scene = new Scene(loader.getRoot());
		DungeonApplication.getPrimaryStage().setScene(scene);
	}
	
	private String time()
	{
		int sec = (int) ((System.nanoTime() - startTimeNano) / 1000000000);
		int min = sec / 60;
		sec -= min * 60;
		return min + " mins " + sec + " sec";
	}
	
	private void loadDungeon(String fileName) throws IOException
	{
		DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(fileName);
        DungeonController controller = dungeonLoader.loadController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
		DungeonApplication.getPrimaryStage().setScene(scene);
	}
}
