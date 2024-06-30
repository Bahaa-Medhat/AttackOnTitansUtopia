package game.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainWindowView extends Application {
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();

        Image logoImage = new Image(getClass().getResourceAsStream("/Media/Logo.jpg"));
        Image background = new Image(getClass().getResourceAsStream("/Media/Background.jpg"));
        BackgroundImage bgImage = new BackgroundImage(background, 
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
        Media media = new Media(getClass().getResource("/Media/intro.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
        });
        
        
        Label welcome = new Label("Welcome to Attack On Titans: Utopia");
        welcome.setFont(new Font("Goudy Stout", 32));
        welcome.setTextFill(Color.WHITE);
        welcome.setTranslateY(-100);
        
        Label mode = new Label("Select a game mode:");
        mode.setFont(new Font("Arial", 18));
        mode.setTextFill(Color.WHITE);
        mode.setTranslateY(-50);
        
        Button easy = new Button("Easy");
        easy.setStyle("-fx-background-color: lightgreen; -fx-text-fill: darkgreen; -fx-font-size: 24px; -fx-font-weight: bold;");
        easy.setTranslateX(-200);
        
        Button hard = new Button("Hard");
        hard.setStyle("-fx-background-color: lightcoral; -fx-text-fill: darkred; -fx-font-size: 24px; -fx-font-weight: bold;");
        hard.setTranslateX(200);
        
        
        Controller controller = new Controller(primaryStage, mediaPlayer);
        easy.setOnAction(e -> controller.handleEasyButtonAction());
        hard.setOnAction(e -> controller.handleHardButtonAction());
        
        root.getChildren().addAll(welcome, mode, easy, hard);
        root.setBackground(new Background(bgImage));
        
        Scene s = new Scene(root, 1536, 940);
        primaryStage.setScene(s);
        primaryStage.setTitle("Attack On Titans: Utopia");
        primaryStage.getIcons().add(logoImage);
        primaryStage.show();
    }

	public static void main(String[] args) {
		launch(args);
	}
}
