package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Controller {

    @FXML
    GridPane myPane;

    public void readImage(File file) throws Exception{


        FileInputStream imageStream = new FileInputStream(file.getPath());
        Image image = new Image(imageStream);
        myPane.add(new ImageView(image), 0, 0);
    }



    @FXML
    public void showMenu() throws Exception{
        Main.changeScene(FXMLLoader.load(getClass().getResource("Menu.fxml")));
    }

    @FXML
    public void showGame() throws Exception{
        FileChooser uploadPhoto = new FileChooser();
        uploadPhoto.setTitle("Wybierz zdjęcie do puzzli");
        uploadPhoto.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );


        File file = uploadPhoto.showOpenDialog(Main.myStage);
        if(file != null) {


            Main.changeScene(FXMLLoader.load(getClass().getResource("Puzzle.fxml")));
            readImage(file);


        }
    }

}

