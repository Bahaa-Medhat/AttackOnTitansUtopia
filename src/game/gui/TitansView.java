package game.gui;

import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class TitansView extends StackPane {
    private  StackPane stackPane;
    private int laneNumber;
    
    public TitansView(Titan titan, int laneNumber) {
        Image titanImage= getImageForTitan(titan);
        ImageView tView = new ImageView();
        tView.setImage(titanImage);
        tView.resize(50, 50);
        
        this.stackPane = new StackPane();
        this.stackPane.setPrefSize(50, 50);
        this.stackPane.getChildren().addAll(tView);
        this.getChildren().add(stackPane);
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
    
    public int getLaneNumber() {
		return laneNumber;
	}

}
