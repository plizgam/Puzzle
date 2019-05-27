package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;

import java.io.File;

public class ControllerMenu {

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
        if(file != null)
        Main.changeScene(FXMLLoader.load(getClass().getResource("Puzzle.fxml")));
    }

}