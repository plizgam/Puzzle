package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;

import java.io.FileWriter;
import java.io.IOException;
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

    static Image myImage;

    ImageView[][] pieces;
    ImageView cutImage;


    static int size;
    static String level;
    int widthCell , heightCell, x = 0 ,y = 0, GamingTime = 0;
    int[] EmptyCell = new int[2];
    boolean[][] usedPicture;
    boolean isWinner = false;

    public void initialize() {


        myButton.setGraphic(new ImageView("file:///returnIcon.png"));

        modifyGrid();

        widthCell = (int)(myImage.getWidth()/size);
        heightCell = (int)(myImage.getHeight()/size);


        pieces = new ImageView[size][size];
        usedPicture = new boolean[size][size];


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


        int m, n;


        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (!(i == size - 1 && j == size - 1)) {
                    do {
                        m = (int) (Math.random() * size);
                        n = (int) (Math.random() * size);

                    } while (usedPicture[m][n]);

                    usedPicture[m][n] = true;
                    myView = pieces[m][n];
                    myView.setPreserveRatio(true);
                    myView.setFitWidth(600/size);
                    myView.setFitHeight(600/size);
                    myPane.add(myView, j, i);

                }

            }
        }

        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if(!usedPicture[i][j])
                    cutImage = pieces[i][j];



        EmptyCell[0] = size -1;
        EmptyCell[1] = size -1;


        startTimer();
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
        if(myStatus.getText() != "You win!") {
            Node clickedNode = event.getPickResult().getIntersectedNode();
            if (clickedNode != myPane) {
                int colIndex = GridPane.getColumnIndex(clickedNode);
                int rowIndex = GridPane.getRowIndex(clickedNode);

                myPane.getChildren().remove(clickedNode);
                myPane.add(clickedNode, EmptyCell[0], EmptyCell[1]);
                EmptyCell[0] = colIndex;
                EmptyCell[1] = rowIndex;
                checkWinner();
            }
        }
    }


    public void checkWinner() {


        ImageView current;
        int matches = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                int colIndex = 0, rowIndex = 0;
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
            cutImage.setPreserveRatio(true);
            cutImage.setFitWidth(600/size);
            cutImage.setFitHeight(600/size);
            myPane.add(cutImage, EmptyCell[0], EmptyCell[1]);
            saveScore();
        }

    }


    public void startTimer() {



        TimerTask timeOfGame = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(!isWinner) {
                        myTimer.setText(String.format("%02d:%02d:%02d", (GamingTime / 3600), ((GamingTime % 3600) / 60), (GamingTime % 60)));
                        GamingTime++;
                    }
                });
            }
        };

        Timer time = new Timer();

        time.scheduleAtFixedRate(timeOfGame, 0, 1000);
    }


    public void saveScore(){
        try
        {
            String filename= "Scores.txt";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(level + "\t\t" + GamingTime + "\n");//appends the string to the file
            fw.close();
            fw.flush();
        }
        catch(IOException ex){}
    }




    public void showScores()throws Exception{
        Main.changeScene(FXMLLoader.load(getClass().getResource("Scores.fxml")));
    }

}


