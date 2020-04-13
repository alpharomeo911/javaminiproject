package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.scene.Group; 

public class TicTacToe extends Application{

    private boolean gameOver;
    private int nFilled;
    private char whoseTurn = 'X'; // 'X' or 'O'
    //private Image imageX = new Image("file:x.jpg");
    //private Image imageO = new Image("file:o.jpg");
    private Cell[][] cell =  new Cell[3][3];
    private Label statusLabel = new Label("X's turn to play");
    private Stage primaryStage;
    BorderPane bP = new BorderPane();


    void startGame(Stage stage, BorderPane bP)
   {
	gameOver = false;
	nFilled = 0;
	statusLabel = new Label("X's turn to play");
            GridPane pane = new GridPane(); 
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                pane.add(cell[i][j] = new Cell(), j, i);
        pane.setAlignment(Pos.CENTER);
        bP.setCenter(pane);
        bP.setTop(statusLabel);

        pane.setStyle("-fx-background-color:ORANGE; -fx-opacity:1;");

        Scene scene = new Scene(bP, 600, 600);
        stage.setTitle("TicTacToe");
        stage.setScene(scene);

        stage.show();
    }


    @Override
    public void start(Stage primaryStage) {
                this.primaryStage = primaryStage;
                this.bP = new BorderPane();
                startGame(this.primaryStage, this.bP);
    }

    public boolean isFull() {
        return nFilled >= 9; // > should never happen
    }

    public boolean hasWon(char tkn) {
        for (int i = 0; i < 3; i++)
            if (cell[i][0].getToken() == tkn &&
                cell[i][1].getToken() == tkn &&
                cell[i][2].getToken() == tkn)
                return true;
        for (int j = 0; j < 3; j++)
            if (cell[0][j].getToken() == tkn &&
                cell[1][j].getToken() == tkn &&
                cell[2][j].getToken() == tkn)
                return true;
        if (cell[0][0].getToken() == tkn &&
            cell[1][1].getToken() == tkn &&
            cell[2][2].getToken() == tkn)
            return true;
        if (cell[0][2].getToken() == tkn &&
            cell[1][1].getToken() == tkn &&
            cell[2][0].getToken() == tkn)
            return true;
        return false;
    }

    public class Cell extends Pane {

        private char token = ' ';   // one of blank, X, or O

        public Cell() {
            setStyle("-fx-border-color: black"); 
            setPrefSize(200, 200);
            setOnMouseClicked(e -> handleMouseClick());
        }

        public char getToken() {
            return token;
        }

	/** Set a new token */
    public void setToken(char c) {
      token = c;
      
      if (token == 'X') {
        Line line1 = new Line(10, 10, 
          this.getWidth() - 10, this.getHeight() - 10);
        line1.endXProperty().bind(this.widthProperty().subtract(10));
        line1.endYProperty().bind(this.heightProperty().subtract(10));
        line1.setStyle("-fx-fill: #000080; -fx-stroke: #000080; -fx-stroke-width: 5;");
        Line line2 = new Line(10, this.getHeight() - 10, 
          this.getWidth() - 10, 10);
        line2.startYProperty().bind(
          this.heightProperty().subtract(10));
        line2.endXProperty().bind(this.widthProperty().subtract(10));
        line2.setStyle("-fx-fill: #000080; -fx-stroke: #000080; -fx-stroke-width: 5;");
        
        // Add the lines to the pane
        this.getChildren().addAll(line1, line2); 
      }
      else if (token == 'O') {
        Ellipse ellipse = new Ellipse(this.getWidth() / 2, 
          this.getHeight() / 2, this.getWidth() / 2 - 10, 
          this.getHeight() / 2 - 10);
        ellipse.centerXProperty().bind(
          this.widthProperty().divide(2));
        ellipse.centerYProperty().bind(
            this.heightProperty().divide(2));
        ellipse.radiusXProperty().bind(
            this.widthProperty().divide(2).subtract(10));        
        ellipse.radiusYProperty().bind(
            this.heightProperty().divide(2).subtract(10));
          ellipse.setStyle("-fx-fill: ORANGE; -fx-stroke: #000080; -fx-stroke-width: 5;");
        
        getChildren().add(ellipse); // Add the ellipse to the pane
      }
	nFilled++;
    }

        private void handleMouseClick() {
            String s = "";
            if (token==' ' && !gameOver) {
                setToken(whoseTurn);
            if (hasWon(whoseTurn)) {
                    gameOver = true;
                    s = "Congratulations, " + whoseTurn + " wins the game!";
                    Button btnNewGame = new Button("Play Again");
                    btnNewGame.setOnAction( event ->
                            {
                                Stage pStage = new Stage();
                                primaryStage.close();
                                start(pStage);
                            });
		    Button exit = new Button("Exit");
		    exit.setOnAction(new EventHandler<ActionEvent>()
     		    {
         		@Override 
         		public void handle(ActionEvent e) 
			{
                Main main = new Main();
                try {
                    main.start(primaryStage);
                    primaryStage.setResizable(false);
                } catch (Exception x) {
                    System.out.println("Err!");
                }
            	    	}
        	    });
		    ToolBar toolBar = new ToolBar();
                    toolBar.getItems().addAll( new Separator(), btnNewGame, exit);

                    bP.setBottom(toolBar);

                    primaryStage.show();
                }
                else if (isFull()) {
                    gameOver = true;
                    s = "Draw!";
                    Button btnNewGame = new Button("Play Again");
                    btnNewGame.setOnAction( event ->
                            {
                                Stage pStage = new Stage();
                                primaryStage.close();
                                start(pStage);
                            });

		    Button exit = new Button("Exit");
		    exit.setOnAction(new EventHandler<ActionEvent>()
     		    {
         		@Override 
         		public void handle(ActionEvent e) 
			{
                Main main = new Main();
                try {
                    main.start(primaryStage);
                    primaryStage.setResizable(false);
                } catch (Exception x) {
                    System.out.println("Err!");
                }
            	    	}
        	    });

                    ToolBar toolBar = new ToolBar();
                    toolBar.getItems().addAll( new Separator(), btnNewGame, exit);

                    bP.setBottom(toolBar);

                    primaryStage.show();
                }
                else {
                    whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
                    s = whoseTurn + "'s turn";
                }
                statusLabel.setText(s);
            }

        }




    }

    public static void main(String[] args) {
        launch(args);
    }
}





/* public void drawX() {   
                if(token == ' ') {
                    ImageView imageV = new ImageView(imageX);
                    imageV.setFitHeight(100);
                    imageV.setFitWidth(100);

                    imageV.setTranslateX(50);
                    imageV.setTranslateY(50);

                getChildren().add(imageV);
                }
        }

        public void drawO() {
                if(token == ' ') {
                    ImageView imageV = new ImageView(imageO);
                    imageV.setFitHeight(100);
                    imageV.setFitWidth(100);

                    imageV.setTranslateX(50);
                    imageV.setTranslateY(50);

                getChildren().add(imageV);
                }
        }

        public void setToken(char c) {
            if (c == 'X')
                drawX();
            else 
                drawO();
            token = c;
            nFilled ++;
        }*/