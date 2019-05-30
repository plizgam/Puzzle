package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ControllerPuzzle {

    @FXML
    GridPane myPane;
    static Image myImage;

    @FXML
    ImageView myImageView;



    public void initialize() {
        myImageView.setImage(myImage);

        myImageView.setPreserveRatio(true);
        myImageView.setSmooth(true);
        myImageView.setCache(true);
    }



    @FXML
    public void showMenu() throws Exception{
        Main.changeScene(FXMLLoader.load(getClass().getResource("Menu.fxml")));
    }











}
