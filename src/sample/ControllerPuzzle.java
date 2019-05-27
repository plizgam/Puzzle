package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class ControllerPuzzle {
    @FXML
    public void showMenu() throws Exception{
        Main.changeScene(FXMLLoader.load(getClass().getResource("Menu.fxml")));
    }
}
