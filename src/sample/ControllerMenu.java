package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;


public class ControllerMenu {

    @FXML
    Button ButtonStart;

    @FXML
    ChoiceBox Levels;


    public void initialize()
    {
        Levels.setItems(FXCollections.observableArrayList("Easy\t\t\t3x3", "Medium\t\t4x4", "Hard\t\t\t5x5", "Expert\t\t6x6"));
    }


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
            ControllerPuzzle.myImage = new Image(file.toURI().toString());
            ControllerPuzzle.size = Levels.getSelectionModel().getSelectedIndex() + 3;
            ControllerPuzzle.level = Levels.getSelectionModel().getSelectedItem().toString();
            Main.changeScene(FXMLLoader.load(getClass().getResource("Puzzle.fxml")));
        }
    }
}
