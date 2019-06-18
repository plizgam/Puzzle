package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class ControllerPuzzle {

    @FXML
    GridPane myPane;
    @FXML
    Label myStatus;
    @FXML
    Button myButton;
    @FXML
    Label myTimer;
    @FXML
    CheckBox myCheck;


    static Image myImage;

    ImageView[][] pieces;
    ImageView cutImage;


    static int size;
    static String level;
    int widthCell , heightCell, x = 0 ,y = 0, GamingTime = 0, colIndex, rowIndex;
    int[] EmptyCell = new int[2];
    boolean isWinner = false;

    public void initialize() {

        modifyGrid();

        widthCell = (int)(myImage.getWidth()/size);
        heightCell = (int)(myImage.getHeight()/size);


        pieces = new ImageView[size][size];


        PixelReader reader = myImage.getPixelReader();
        WritableImage divideImage;
        ImageView myView;



        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                divideImage = new WritableImage(reader, x, y, widthCell, heightCell);
                pieces[i][j] = new ImageView(divideImage);
                x +=widthCell;
            }
            x = 0;
            y +=heightCell;
        }


        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (!(i == size - 1 && j == size - 1)) {
                    myView = pieces[i][j];
                    myView.setFitWidth(600 / size);
                    myView.setFitHeight(400 / size);
                    myPane.add(myView, j, i);
                }
            }
        }




        cutImage = pieces[size-1][size-1];
        EmptyCell[0] = size -1;
        EmptyCell[1] = size -1;

        randomPuzzles();
        startTimer();
    }



    public void randomPuzzles(){

        int m=0, n=0;

        for(int i = 0; i < 40000; i++) {
                switch ((int)(Math.random() * 4)) {
                    case 0:
                        m = EmptyCell[0] + 1;
                        n = EmptyCell[1];
                        break;
                    case 1:
                        m = EmptyCell[0] - 1;
                        n = EmptyCell[1];
                        break;
                    case 2:
                        m = EmptyCell[0];
                        n = EmptyCell[1] - 1;
                        break;
                    case 3:
                        m = EmptyCell[0];
                        n = EmptyCell[1] + 1;
                        break;
                }

            if(m >= 0 && n >= 0 && m < size && n < size){
                ImageView current = pieces[m][n];
                if(myPane.getColumnIndex(current)!= null && myPane.getRowIndex(current) != null) {
                    colIndex = myPane.getColumnIndex(current);
                    rowIndex = myPane.getRowIndex(current);
                    if (((EmptyCell[1] == rowIndex + 1 && EmptyCell[0] == colIndex) ||
                            (EmptyCell[0] == colIndex + 1 && EmptyCell[1] == rowIndex)
                            || (EmptyCell[1] == rowIndex - 1 && EmptyCell[0] == colIndex) ||
                            (EmptyCell[0] == colIndex - 1 && EmptyCell[1] == rowIndex))){
                        changePosition(current);
                    }
                }
            }
        }
    }


    public void modifyGrid(){
        for(int i = 0; i < size; i++) {
            myPane.addColumn(i);
            myPane.addRow(i);
        }
    }


    @FXML
    public void showMenu() throws Exception{
        Main.changeScene(FXMLLoader.load(getClass().getResource("Menu.fxml")));
    }


    @FXML
    public void clickImage(javafx.scene.input.MouseEvent event) {

        if(!isWinner) {
            Node clickedNode = event.getPickResult().getIntersectedNode();
            if (clickedNode != myPane) {
                colIndex = GridPane.getColumnIndex(clickedNode);
                rowIndex = GridPane.getRowIndex(clickedNode);

                if(checkMode()) {
                    if (((EmptyCell[1] == rowIndex + 1 && EmptyCell[0] == colIndex) ||
                            (EmptyCell[0] == colIndex + 1 && EmptyCell[1] == rowIndex)
                            || (EmptyCell[1] == rowIndex - 1 && EmptyCell[0] == colIndex) ||
                            (EmptyCell[0] == colIndex - 1 && EmptyCell[1] == rowIndex))) {
                       // playSound("click.mp3");
                        changePosition(clickedNode);
                    }
                }else
                    changePosition(clickedNode);


            }
        }
    }


    public void changePosition(Node clickedNode){
        myPane.getChildren().remove(clickedNode);
        myPane.add(clickedNode, EmptyCell[0], EmptyCell[1]);
        EmptyCell[0] = colIndex;
        EmptyCell[1] = rowIndex;
        checkWinner();
    }

    public void checkWinner() {


        ImageView current;
        int matches = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                int colIndex, rowIndex;
                current = pieces[i][j];

                for (Node node : myPane.getChildren()) {

                    colIndex = myPane.getColumnIndex(node);
                    rowIndex = myPane.getRowIndex(node);

                    if(node == current && i == rowIndex && j == colIndex){
                        matches++;
                    }
                }
                }
            }

        if (matches == size * size - 1) {
            myStatus.setText("You win!");
            isWinner = true;
            cutImage.setFitWidth(600/size);
            cutImage.setFitHeight(400/size);
            myPane.add(cutImage, EmptyCell[0], EmptyCell[1]);
            saveScore();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playSound("winner.mp3");
        }

    }


    public void startTimer() {



        TimerTask timeOfGame = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(!isWinner) {
                        GamingTime++;
                        myTimer.setText(String.format("%02d:%02d:%02d", (GamingTime / 3600), ((GamingTime % 3600) / 60), (GamingTime % 60)));
                    }
                });
            }
        };

        Timer time = new Timer();

        time.scheduleAtFixedRate(timeOfGame, 0, 1000);
    }


    public void playSound(String url) {

            File file = new File(url);
            System.out.println(file.toURI().toString());
            Media media = new Media(file.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();

    }


    public void saveScore(){
        try
        {
            String filename= "Scores.txt";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(level + "\t\t" + String.format("%02d:%02d:%02d", (GamingTime / 3600), ((GamingTime % 3600) / 60), (GamingTime % 60)) + "\n");//appends the string to the file
            fw.close();
            fw.flush();
        }
        catch(IOException ex){}
    }


    public boolean checkMode(){
        if(myCheck.isSelected())
            return true;
        else
            return false;
    }

    public void showScores()throws Exception{
        Main.changeScene(FXMLLoader.load(getClass().getResource("Scores.fxml")));
    }

}
