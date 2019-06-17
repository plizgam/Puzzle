package sample;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class ControllerScores {


    @FXML
    TextArea ScoresArea;


    public void initialize() throws Exception{

        StringBuffer sb = new StringBuffer();
        File file = new File("Scores.txt");
        Scanner sc = new Scanner(file);


        while (sc.hasNextLine())
            sb.append(sc.nextLine() + "\n");




        ScoresArea.setText(sb.toString());
    }


    public void showPuzzle(){
        try {
            Main.changeScene(FXMLLoader.load(getClass().getResource("Puzzle.fxml")));
        } catch (IOException e){}
    }
}



