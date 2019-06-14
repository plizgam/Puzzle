package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage myStage;


    @Override
    public void start(Stage primaryStage) throws Exception{
        myStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        primaryStage.setTitle("Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        centerScene();
    }

    public static void changeScene(Parent p){
        myStage.setScene(new Scene(p));
        centerScene();
    }

    public static void centerScene(){
        Rectangle2D p = Screen.getPrimary().getVisualBounds();
        myStage.setX((p.getWidth() - myStage.getWidth()) / 2);
        myStage.setY((p.getHeight() - myStage.getHeight()) / 2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
