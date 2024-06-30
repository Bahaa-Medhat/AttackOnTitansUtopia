package game.gui;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class WeaponView extends StackPane {
	private int weaponCode;
    private final Circle circle;
    private final Text text;
    private int count = 1;

    public WeaponView(int weaponCode) {
    	this.weaponCode = weaponCode;
        Color color = getColorForWeapon(weaponCode);
        String name = getNameForWeapon(weaponCode);

        this.circle = new Circle(15, color);
        this.text = new Text(name);

        this.getChildren().addAll(circle, text);
    }

    private Color getColorForWeapon(int weaponCode) {
        switch(weaponCode) {
        case 1: return Color.RED;
        case 2: return Color.GREEN;
        case 3: return Color.BLUE;
        default: return Color.YELLOW;
        }
    }

    String getNameForWeapon(int weaponCode) {
    	switch(weaponCode) {
        case 1: return "PC";
        case 2: return "SC";
        case 3: return "VSC";
        case 4: return "WT";
        default: return "X";
        }
    }

	public int getCount() {
		return count;
	}

	public void incrementCount() {
		this.count++;
	}

	public int getWeaponCode() {
		return weaponCode;
	}
}
