package game.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameDescriptionView extends Application{

	public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();

        Image logoImage = new Image(getClass().getResourceAsStream("/Media/Logo.jpg"));
        Image background = new Image(getClass().getResourceAsStream("/Media/Background.jpg"));
        BackgroundImage bgImage = new BackgroundImage(background, 
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
        Media video = new Media(getClass().getResource("/Media/gameDescription.mp4").toExternalForm());
        MediaPlayer videoPlayer = new MediaPlayer(video);
        MediaView mediaView = new MediaView(videoPlayer);
        videoPlayer.setAutoPlay(true);
        mediaView.setFitWidth(1400); 
        mediaView.setPreserveRatio(true);
        
        
        Label description = new Label("Game Description");
        description.setFont(new Font("Goudy Stout", 36));
        description.setTextFill(Color.BLACK);
        BorderPane.setAlignment(description, Pos.TOP_CENTER);

        
        Button next = new Button("Next");
        next.setStyle("-fx-background-color: white; -fx-text-fill: red; -fx-font-size: 24px; -fx-font-weight: bold;");
        BorderPane.setAlignment(next, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(next, new Insets(10));
        
        Controller controller = new Controller(primaryStage, videoPlayer);
        next.setOnAction(e -> controller.handleNextButtonAction());
        
        root.setTop(description);
        root.setCenter(mediaView);
        root.setBottom(next);
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
