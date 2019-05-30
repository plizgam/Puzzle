package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;


public class ControllerMenu {

    @FXML
    Button ButtonStart;



    public void showGame() throws Exception{
        FileChooser uploadPhoto = new FileChooser();
        uploadPhoto.setTitle("Wybierz zdjęcie do puzzli");
        uploadPhoto.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        //File file = uploadPhoto.showOpenDialog(Main.myStage);
        //if(file != null)

            //todo WYMIENIĆ SOURCE z file.toURI().toString()
            ControllerPuzzle.myImage = new Image("file:///C:\\Users\\Miłosz\\Desktop\\a.jpg");
            Main.changeScene(FXMLLoader.load(getClass().getResource("Puzzle.fxml")));

    }
}
