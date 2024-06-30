package game.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import game.engine.lanes.Lane;
import java.util.HashMap;

public class LaneView extends HBox {
	private final Lane lane;
	private final Rectangle wall;
	private final Text dangerLevelText;
	private final Text weaponText;
	private int laneNumber;
	private final HashMap<Integer, WeaponView> weaponViews = new HashMap<>();

	public LaneView(Lane lane, int laneNumber) {
		this.lane = lane;
		this.setPadding(new Insets(5));
		this.setSpacing(10);

		this.wall = new Rectangle(50, 100);
		this.wall.setFill(Color.GRAY);
		this.wall.setStroke(Color.BLACK);
		this.wall.setStrokeWidth(2);

		this.dangerLevelText = new Text(
				"Danger Level: " + lane.getDangerLevel() + " - Wall Health: " + lane.getLaneWall().getCurrentHealth());
		this.dangerLevelText.setFont(new Font("Goudy Stout", 12));
		this.dangerLevelText.setFill(Color.RED);

		this.weaponText = new Text("Weapons:");
		this.weaponText.setFont(new Font("Goudy Stout", 12));
		this.weaponText.setTranslateY(100);
		this.weaponText.setTranslateX(-50);

		this.getChildren().addAll(this.wall, this.dangerLevelText, this.weaponText);

		this.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THICK)));
	}

	public void addWeapon(int weaponCode) {
		if (!weaponViews.containsKey(weaponCode)) {
			WeaponView weaponView = new WeaponView(weaponCode);
			weaponViews.put(weaponCode, weaponView);
			weaponView.setPadding(new Insets(100, 0, 0, 0));
			this.getChildren().add(weaponView);
		} else {
			WeaponView weaponView = weaponViews.get(weaponCode);
			weaponView.incrementCount();
			((Text) weaponView.getChildren().get(1)).setText("x" + weaponView.getCount());
		}
	}
	

	public void updateDangerLevel() {
		this.dangerLevelText.setText("Danger Level: " + lane.getDangerLevel());
	}

	public int getLaneNumber() {
		return laneNumber;
	}
}
