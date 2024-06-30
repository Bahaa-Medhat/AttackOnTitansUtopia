package game.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import game.engine.weapons.factory.WeaponFactory;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DisplayView extends Application {

	private Stage primaryStage;
	private Battle battle;
	private WeaponFactory weaponFactory;
	private int resources;
	private ArrayList<Lane> originalLanes = new ArrayList<>();
	private HBox status;
	private AnchorPane[] lanesView;
	private AnchorPane[] laneTitans;
	private VBox lanesContainer;
	private int widthLane;
	private int heightLane;
	private HashMap<Integer, WeaponView> weaponViews = new HashMap<>();
	private int[][] weaponCounter;

	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		Image logoImage = new Image(getClass().getResourceAsStream("/Media/Logo.jpg"));
		Image background = new Image(getClass().getResourceAsStream("/Media/warBackground.jpg"));
		BackgroundImage bgImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Media media = new Media(getClass().getResource("/Media/game.mp3").toExternalForm());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setOnEndOfMedia(() -> {
		    mediaPlayer.seek(Duration.ZERO);
		    mediaPlayer.play();
		});

		String difficulty = Controller.getDifficulty();
		int numberOfLanes = (difficulty.equals("easy")) ? 3 : 5;
		int resourcesPerLane = (difficulty.equals("easy")) ? 250 : 125;
		widthLane = 750;
		heightLane = (difficulty.equals("easy")) ? 230 : 134;
		laneTitans = new AnchorPane[numberOfLanes];
		lanesView = new AnchorPane[numberOfLanes];
		weaponCounter = new int[numberOfLanes][4];

		battle = new Battle(1, 0, widthLane - 100, numberOfLanes, resourcesPerLane);
		resources = battle.getResourcesGathered();

		BorderPane window = new BorderPane();
		window.setPrefSize(1536, 960);

		status = displayStatus();

		lanesContainer = new VBox(10);
		lanesContainer.setFillWidth(true);
		lanesContainer.setMaxSize(widthLane, heightLane);
		for (int j = 0; j < lanesView.length; j++) {
			lanesView[j] = new AnchorPane();
			lanesView[j].setMaxWidth(widthLane);
			lanesView[j].setStyle("-fx-border-color: black; -fx-border-width: 10px; -fx-background-color: white;");
			
			Label danger = createLabel("  D-Level: " + battle.getOriginalLanes().get(j).getDangerLevel() + " Health: "
					+ battle.getOriginalLanes().get(j).getLaneWall().getCurrentHealth(), 10);
			
			Rectangle wall = new Rectangle(50, 150);
			wall.setFill(Color.GRAY);
			wall.setStroke(Color.BLACK);
			wall.setStrokeWidth(2);
			AnchorPane.setLeftAnchor(wall, 0.0);
			AnchorPane.setBottomAnchor(danger, 0.0);
			lanesView[j].getChildren().addAll(wall, danger);
			VBox.setVgrow(lanesView[j], Priority.ALWAYS);
			lanesContainer.getChildren().add(lanesView[j]);
		}

		for (int i = 0; i < laneTitans.length; i++) {
			laneTitans[i] = new AnchorPane();
			laneTitans[i].setMaxSize(widthLane - 100, heightLane);
			lanesView[i].getChildren().add(laneTitans[i]);
		}

		VBox weaponShop = displayWeaponShop();

		window.setTop(status);
		window.setRight(weaponShop);
		window.setCenter(lanesContainer);
		window.setBackground(new Background(bgImage));

		originalLanes.addAll(battle.getOriginalLanes());

		Scene scene = new Scene(window, 1536, 960);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Attack On Titans: Utopia");
		primaryStage.getIcons().add(logoImage);
	}

	private HBox displayStatus() {
		HBox status = new HBox(75);
		status.setPrefSize(1152, 30);

		Label score = createLabel("Score: " + battle.getScore(), 18);
		Label turn = createLabel("Number of Turns: " + battle.getNumberOfTurns(), 18);
		Label resourcesLabel = createLabel("Resources: " + resources, 18);
		Label phase = createLabel("Phase: " + battle.getBattlePhase(), 18);

		score.setTextFill(Color.WHITE);
		turn.setTextFill(Color.WHITE);
		resourcesLabel.setTextFill(Color.WHITE);
		phase.setTextFill(Color.WHITE);

		Button passTurn = new Button("Pass Turn");
		passTurn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
		passTurn.setOnAction(event -> {
			battle.passTurn();
			score.setText("Score: " + battle.getScore());
			turn.setText("Number of Turns: " + battle.getNumberOfTurns());
			resourcesLabel.setText("Resources: " + battle.getResourcesGathered());
			phase.setText("Phase: " + battle.getBattlePhase());
			displayTitans();
			updateWallLabel();
		});
		status.getChildren().addAll(score, turn, resourcesLabel, phase, passTurn);

		return status;
	}

	private VBox displayWeaponShop() {
		weaponFactory = battle.getWeaponFactory();
		VBox shop = new VBox(10);
		shop.setPadding(new Insets(20));
		shop.setPrefSize(384, 960);
		shop.setStyle("-fx-background-color: #f0f0f0;");

		Label weaponShopLabel = createLabel("Weapon Shop:", 16);
		weaponShopLabel.setTextFill(Color.DARKRED);
		shop.getChildren().add(weaponShopLabel);

		weaponFactory.getWeaponShop().forEach((weaponCode, weaponRegistry) -> {
			String type = "";
			switch (weaponCode) {
			case 1:
				type = "Piercing Cannon";
				break;
			case 2:
				type = "Sniper Cannon";
				break;
			case 3:
				type = "Volley Spread Cannon";
				break;
			default:
				type = "Wall Trap";
			}

			String weaponInfo = weaponRegistry.getName() + " - Price: " + weaponRegistry.getPrice() + " - Damage: "
					+ weaponRegistry.getDamage() + "\n" + " - Type: " + type;

			Label weaponLabel = createLabel(weaponInfo, 7);
			ChoiceBox<String> laneSelector = new ChoiceBox<>();
			laneSelector.getItems().addAll("Lane 1", "Lane 2", "Lane 3");
			if (battle.getOriginalLanes().size() == 5)
				laneSelector.getItems().addAll("Lane 4", "Lane 5");
			laneSelector.setValue("Lane 1");
			laneSelector.setPrefWidth(100);

			Button buyButton = createBuyButton(weaponCode);

			HBox buyBar = new HBox(10);
			buyBar.getChildren().addAll(buyButton, laneSelector);

			VBox itemBox = new VBox(weaponLabel, buyBar);
			itemBox.setStyle("-fx-padding: 10px; -fx-border-color: #ccc; -fx-border-width: 1px;");

			Image weaponImage = getImageForWeapon(weaponRegistry.getName());
			ImageView imageView = createImageView(weaponImage, 125, 125);

			Label symbolLabel = createLabel("Symbol:", 16);
			symbolLabel.setTextFill(Color.DARKRED);
			WeaponView symbol = new WeaponView(weaponCode);
			symbol.setTranslateX(-50);

			HBox symbolBox = new HBox(10);
			symbolBox.getChildren().addAll(imageView, symbolLabel, symbol);

			shop.getChildren().addAll(itemBox, symbolBox);
		});

		return shop;
	}

	private void displayTitans() {
		for (AnchorPane a : laneTitans) {
			a.getChildren().clear();
		}
		int i = 0;
		if (!battle.isGameOver()) {
			for (Lane lane : battle.getOriginalLanes()) {
				if (!lane.isLaneLost()) {
					for (Titan titan : lane.getTitans()) {
						StackPane t = new StackPane();
						t.setMaxSize(100, 100);
						Image img = getImageForTitan(titan);
						ImageView tView = new ImageView();
						tView.setImage(img);
						tView.setFitHeight(100);
						tView.setFitWidth(100);
						t.getChildren().add(tView);
						t.setLayoutX(titan.getDistance());
						
						Label health = createLabel("Health " + titan.getCurrentHealth(), 5);
						health.setTranslateY(50);
						t.getChildren().add(health);
						
						laneTitans[i].getChildren().add(t);
					}
				}
				i++;
			}
		} else {
			Controller controller = new Controller(primaryStage, battle);
			controller.loadGameOverDisplay();
		}
	}

	private Image getImageForTitan(Titan titan) {
		if (titan instanceof AbnormalTitan) {
			return new Image(getClass().getResourceAsStream("/Media/abnormal_titan.png"));
		} else if (titan instanceof ArmoredTitan) {
			return new Image(getClass().getResourceAsStream("/Media/armored_titan.png"));
		} else if (titan instanceof ColossalTitan) {
			return new Image(getClass().getResourceAsStream("/Media/colossal_titan.png"));
		}
		return new Image(getClass().getResourceAsStream("/Media/pure_titan.png"));

	}

	private Button createBuyButton(int weaponCode) {
		Button buyButton = new Button("Buy");
		buyButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
		buyButton.setOnAction(event -> {
			try {
				int selectedLaneIndex = ((ChoiceBox<String>) ((HBox) buyButton.getParent()).getChildren().get(1))
						.getSelectionModel().getSelectedIndex();
				ArrayList<Lane> lanesList = new ArrayList<>(battle.getLanes());
				Lane selectedLane = lanesList.get(selectedLaneIndex);
				battle.purchaseWeapon(weaponCode, selectedLane);
				((Label) status.getChildren().get(0)).setText("Score: " + battle.getScore());
				((Label) status.getChildren().get(1)).setText("Number of Turns: " + battle.getNumberOfTurns());
				((Label) status.getChildren().get(2)).setText("Resources: " + battle.getResourcesGathered());
				((Label) status.getChildren().get(3)).setText("Phase: " + battle.getBattlePhase());
				addWeapon(weaponCode, selectedLaneIndex);
				displayTitans();
				updateWallLabel();
			} catch (InvalidLaneException e) {
				showAlert("Invalid Lane", "Please select a valid lane.");
			} catch (InsufficientResourcesException e) {
				showAlert("Insufficient Resources", "You do not have enough resources to buy this weapon.");
			} catch (IndexOutOfBoundsException e) {
				showAlert("Invalid Lane", "Lane already defeated!");
			}
		});
		return buyButton;
	}

	private void updateWallLabel() {
		for (int j = 0; j < lanesView.length; j++) {
			AnchorPane lanesView = (AnchorPane) lanesContainer.getChildren().get(j);
			Label danger = (Label) lanesView.getChildren().get(1);
			danger.setText("  D-Level: " + battle.getOriginalLanes().get(j).getDangerLevel() + " Health: "
					+ battle.getOriginalLanes().get(j).getLaneWall().getCurrentHealth());
		}
	}

	private void addWeapon(int weaponCode, int index) {
		weaponCounter[index][weaponCode - 1] += 1;
		AnchorPane laneAnchor = lanesView[index];
		if (weaponCounter[index][weaponCode - 1] == 1) {
			WeaponView weapon = new WeaponView(weaponCode);
			weapon.setLayoutY((weaponCode - 1) * 33);
			laneAnchor.getChildren().add(weapon);
		} else {
			for (Node n : laneAnchor.getChildren()) {
				if (n instanceof WeaponView) {
					WeaponView w = (WeaponView) n;
					if (w.getWeaponCode() == weaponCode) {
						Text t = (Text) w.getChildren().get(1);
						t.setText("x" + weaponCounter[index][weaponCode - 1]);
					}
				}
			}
		}
	}

	private Label createLabel(String text, int fontSize) {
		Label label = new Label(text);
		label.setFont(new Font("Goudy Stout", fontSize));
		return label;
	}

	private ImageView createImageView(Image image, int width, int height) {
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
		return imageView;
	}

	private Image getImageForWeapon(String name) {
		String imagePath = "";

		switch (name) {
		case "Anti Titan Shell":
			imagePath = "/Media/pc.png";
			break;
		case "Long Range Spear":
			imagePath = "/Media/sc.png";
			break;
		case "Wall Spread Cannon":
			imagePath = "/Media/vsc.png";
			break;
		case "Proximity Trap":
			imagePath = "/Media/wt.png";
			break;
		default:
			System.out.println("Image not found for weapon: " + name);
		}

		return new Image(getClass().getResourceAsStream(imagePath));
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
