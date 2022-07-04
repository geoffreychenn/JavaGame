package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;

public class MainMenuController
{
	@FXML
	AnchorPane mainPane;
	
	@FXML
	Button howToPlayBtn;
	
	@FXML
	Button startBtn;
	
	@FXML
	ImageView howToPlayDisplay;
	
	@FXML
	public void initialize()
	{
		howToPlayDisplay.setVisible(false);
		howToPlayDisplay.setImage(new Image("/howToPlay.jpg"));
		mainPane.setBackground(new Background(new BackgroundImage(new Image("/menuScreen.jpg"), null, null, null, null)));
	}
	
	@FXML
	public void handleHowToPlayBtn(ActionEvent event)
	{
		if (startBtn.isVisible())
		{
			startBtn.setVisible(false);
			howToPlayDisplay.setVisible(true);
		}
		else
		{
			startBtn.setVisible(true);
			howToPlayDisplay.setVisible(false);
		}
	}
	
	@FXML
	public void handleStartBtn(ActionEvent event) throws IOException
	{
		DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("level_01.json");
        DungeonController controller = dungeonLoader.loadController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
		DungeonApplication.getPrimaryStage().setScene(scene);
	}
	
}
