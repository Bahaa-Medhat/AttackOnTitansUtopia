package game.gui;

import game.engine.Battle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameOverDisplay extends Application{
	Battle battle;
	
	public GameOverDisplay(Battle battle) {
		this.battle = battle;
	}
	
	public void start(Stage primaryStage) throws Exception {
		Image logoImage = new Image(getClass().getResourceAsStream("/Media/Logo.jpg"));
		Image background = new Image(getClass().getResourceAsStream("/Media/Background.jpg"));
        BackgroundImage bgImage = new BackgroundImage(background, 
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
        
		StackPane root = new StackPane();
		
		Label gameOver = new Label("Game Over!");
        gameOver.setFont(new Font("Goudy Stout", 32));
        gameOver.setTextFill(Color.WHITE);
        gameOver.setTranslateY(-100);
		
		Label score = new Label("Score: " + battle.getScore());
        score.setFont(new Font("Arial", 32));
        score.setTextFill(Color.WHITE);


		Button back = new Button("Return to Menu");
        back.setStyle("-fx-background-color: lightgreen; -fx-text-fill: darkgreen; -fx-font-size: 24px; -fx-font-weight: bold;");
        back.setTranslateY(100);
        back.setOnAction(e ->{
        	Controller controller = new Controller(primaryStage);
        	controller.loadMainWindowView();
        });
		
		
		root.getChildren().addAll(gameOver, score, back);
        root.setBackground(new Background(bgImage));

		Scene scene = new Scene(root, 1536, 960);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Attack On Titans: Utopia");
		primaryStage.getIcons().add(logoImage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
