package game.gui;

import game.engine.Battle;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Controller {
    private static String difficulty;
    private Stage primaryStage;
    private MediaPlayer mediaPlayer;
    private MediaPlayer videoPlayer;
    private Battle battle;

    public Controller(Stage primaryStage, MediaPlayer mediaPlayer) {
        this.primaryStage = primaryStage;
        this.mediaPlayer = mediaPlayer;
    }

    public Controller(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Controller(Stage primaryStage, Battle battle) {
        this.primaryStage = primaryStage;
        this.battle = battle;
    }

    public void handleEasyButtonAction() {
        difficulty = "easy";
        loadMainWindow2View();
    }

    public void handleHardButtonAction() {
        difficulty = "hard";
        loadMainWindow2View();
    }

    private void loadMainWindow2View() {
        try {
            mediaPlayer.stop();
            MainWindow2View mainWindow2View = new MainWindow2View();
            mainWindow2View.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDifficulty() {
        return difficulty;
    }

    public void handleDescriptionButtonAction() {
        try {
            mediaPlayer.stop();
            GameDescriptionView descriptionView = new GameDescriptionView();
            descriptionView.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handlePlayButtonAction() {
        try {
            mediaPlayer.stop();
            DisplayView displayView = new DisplayView();
            displayView.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMainWindowView() {
        try {
            MainWindowView mainWindow = new MainWindowView();
            mainWindow.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleNextButtonAction() {
        loadMainWindow2View();
    }

    public void loadGameOverDisplay() {
        try {
            GameOverDisplay gameOver = new GameOverDisplay(battle);
            gameOver.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
