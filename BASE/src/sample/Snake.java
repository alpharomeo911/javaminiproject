package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Snake extends Application {
    public void start(Stage primaryStage) {

        Pane pane = new Pane();
        Image image = new Image("file:///C:/Users/sarve/IdeaProjects/BASE/src/sample/snake.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(415);
        imageView.setFitWidth(415);
        Button playAgain = new Button("Play");
        Button exit = new Button("Exit");
        exit.setPrefWidth(120);
        playAgain.setPrefWidth(120);
        playAgain.setLayoutX(100);
        playAgain.setLayoutY(130);
        exit.setLayoutX(70);
        exit.setLayoutY(245);
        pane.getChildren().addAll(imageView ,playAgain, exit);
        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Snake");
        primaryStage.setResizable(false);

        /** Button On Click Here */
        exit.setOnMouseClicked(event -> {
            Main main = new Main();
            try {
                main.start(primaryStage);
            } catch (Exception e) {
                System.out.println("Whoops!");
            }
        });

        playAgain.setOnMouseClicked(event -> {
            Snake1 snake1 = new Snake1();
            snake1.start(primaryStage);
        });

            }

            public static void main(String[] args) {
        launch(args);
        }
        }