package latice.application;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.GameManager;

public class LaticeApplicationConsole extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		GameManager.createGame();

		try {
			Parent content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LaticeForm.fxml")));
			Scene scene = new Scene(content, 1600, 900);
			Image img = new Image("/images/OceanBackground.png",1650,950,true,true);
			BackgroundImage bImg = new BackgroundImage(img,
                     BackgroundRepeat.NO_REPEAT,
                     BackgroundRepeat.NO_REPEAT,
                     BackgroundPosition.DEFAULT,
                     BackgroundSize.DEFAULT);
			Background bGround = new Background(bImg);
			((BorderPane) content).setBackground(bGround);
			primaryStage.setScene(scene);
		    primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
