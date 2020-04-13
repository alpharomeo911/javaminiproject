package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

        Pane root = new Pane();
        primaryStage.setTitle("JAVA FX MINI PROJECT");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("1.180170107093\tPrajapati Darshil\n2.180170107095\tPrajapati Sarvesh\n3.180170107113\t" +
                "Thakkar Aayush\n4.180170107121\tVaktapuriya Muzib");
        alert.setTitle("For your information,");
        alert.setHeaderText("This project was made by:-");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
        alert.show();
        primaryStage.setResizable(false);
        root.setStyle("-fx-background: #FDDC5C;");
        Image image = new Image("File:C:\\Users\\sarve\\IdeaProjects\\BASE\\src\\sample\\play.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(300);
        imageView.setFitWidth(820);
        Button sarvesh = new Button("Sudoku Solver/Generator");
        Button aayush = new Button( "Tic-Tac-Toe");
        Button darshil = new Button("Snake");
        Button muzib = new Button(  "T E T R I S");
        /** Style for the button.*/
        muzib.setPrefSize(250, 40);
        darshil.setPrefSize(250, 40);
        sarvesh.setPrefSize(250, 40);
        aayush.setPrefSize(250, 40);
        sarvesh.setLayoutX(150);
        sarvesh.setLayoutY(400);
        aayush.setLayoutX(150);
        aayush.setLayoutY(550);
        darshil.setLayoutX(450);
        darshil.setLayoutY(400);
        muzib.setLayoutX(450);
        muzib.setLayoutY(550);
        root.getChildren().addAll(imageView, sarvesh, aayush, darshil, muzib);
        sarvesh.setStyle("-fx-background-color: #475F94; -fx-text-fill: #FDDC5C;");
        darshil.setStyle("-fx-background-color: #475F94; -fx-text-fill: #FDDC5C;");
        muzib.setStyle("-fx-background-color: #475F94; -fx-text-fill: #FDDC5C;");
        aayush.setStyle("-fx-background-color: #475F94; -fx-text-fill: #FDDC5C;");
        sarvesh.setOnMouseEntered(event -> {sarvesh.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff;");});
        sarvesh.setOnMouseExited(event -> {sarvesh.setStyle("-fx-background-color: #475F94; -fx-text-fill: #FDDC5C;");});
        darshil.setOnMouseEntered(event -> {darshil.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff;");});
        darshil.setOnMouseExited(event -> {darshil.setStyle("-fx-background-color: #475F94; -fx-text-fill: #FDDC5C;");});
        muzib.setOnMouseEntered(event -> {muzib.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff;");});
        muzib.setOnMouseExited(event -> {muzib.setStyle("-fx-background-color: #475F94; -fx-text-fill: #FDDC5C;");});
        aayush.setOnMouseEntered(event -> {aayush.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff;");});
        aayush.setOnMouseExited(event -> {aayush.setStyle("-fx-background-color: #475F94; -fx-text-fill: #FDDC5C;");});
        sarvesh.setOnMouseClicked(event -> {
            Sudoku sudokuGame = new Sudoku();
            try {
                sudokuGame.start(primaryStage);
                primaryStage.setResizable(true);
            } catch(Exception e) {
                System.out.println("Error!");
            }
        });
        aayush.setOnMouseClicked(event -> {
            try {
                TicTacToe ticTacToeGame = new TicTacToe();
                ticTacToeGame.start(primaryStage);
                primaryStage.setResizable(true);
            } catch (Exception e) {
                System.out.println("Error!");
            }
        });
        darshil.setOnMouseClicked(event -> {
            try {
                Snake snake = new Snake();
                snake.start(primaryStage);
            } catch (Exception e) {
                System.out.println("Error!");
            }
        });
        muzib.setOnMouseClicked(event -> {
            try {
                Tetris tetris = new Tetris();
                tetris.start(primaryStage);

            } catch (Exception e) {
                System.out.println("");
            }
        });

    }




    public static void main(String[] args) {
        launch(args);
    }
}
