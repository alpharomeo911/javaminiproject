package sample;

/**
 *
 * Sudoku Solver and generator javafx
 *
 * @author Sarvesh Prajapati - 180170107095
 *
 * Use of recursice backtracking algorith to solve sudoku puzzle.
 *
 * Version 3 - Code works, needs work with UI and some exceptions needs to be handled and some
 * messages are to be displayed. Thats all.
 *
 *
 * */

import com.sun.deploy.util.StringUtils;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import static java.util.Arrays.asList;

public class Sudoku extends Application {

    public static final int MAX_CELLS = 9;
    public ArrayList<Integer> generator = new ArrayList(asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    TextField[][] sudokuCells;
    int[][] numbersArray;
    Alert alert = new Alert(Alert.AlertType.NONE);

    @Override
    public void start(Stage primaryStage) throws Exception{

//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        GridPane root = new GridPane();
        sudokuCells = new TextField[MAX_CELLS][MAX_CELLS];
        numbersArray = new int[MAX_CELLS][MAX_CELLS];

        for(int i = 0; i < MAX_CELLS; i++) {
            for(int j = 0; j < MAX_CELLS; j++) {
                sudokuCells[i][j] = new TextField();
                sudokuCells[i][j].setPrefWidth(30);
                root.getChildren().add(sudokuCells[i][j]);
                root.setRowIndex(sudokuCells[i][j], i);
                root.setColumnIndex(sudokuCells[i][j], j);
            }
        }

        Button clear = new Button("CLEAR");
        Button exit = new Button("EXIT");
        Button solver = new Button("SOLVE");
        Button check = new Button("CHECK");
        Button generate = new Button("Generate");

        root.add(generate, 1, 13);
        root.add(check, 6, 13);
        root.add(solver, 1, 15);
        root.add(clear, 6, 15);
        root.add(exit, 4, 17);
        GridPane.setColumnSpan(generate, 3);
        GridPane.setColumnSpan(exit, 3);
        GridPane.setColumnSpan(clear, 3);
        GridPane.setColumnSpan(solver, 3);
        GridPane.setColumnSpan(check, 3);

        root.setVgap(10);
        root.setHgap(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        primaryStage.setTitle("Sudoku Solver/ Generator");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();

        /** Extras*/
        //Horizontal Line
        Line line1 = new Line();
        line1.setStartX(0);
        line1.setEndX(349);
        line1.setEndY(0);
        line1.setStartY(0);
        root.add(line1, 0,2);
        GridPane.setColumnSpan(line1, 10);
        GridPane.setMargin(line1, new Insets(45,0,0,0));
        Line line2 = new Line();
        line2.setStartX(0);
        line2.setEndX(349);
        line2.setEndY(0);
        line2.setStartY(0);
        root.add(line2, 0,5);
        GridPane.setColumnSpan(line2, 10);
        GridPane.setMargin(line2, new Insets(45,0,0,0));
        //Vertical Line
        Line line3 = new Line();
        line3.setStartX(0);
        line3.setEndX(0);
        line3.setEndY(370);
        line3.setStartY(-30);
        root.add(line3, 2,0);
        GridPane.setRowSpan(line3, 10);
        GridPane.setColumnSpan(line3, 10);
        GridPane.setMargin(line3, new Insets(0,0,0,35));
        Line line4 = new Line();
        line4.setStartX(0);
        line4.setEndX(0);
        line4.setEndY(370);
        line4.setStartY(-30);
        root.add(line4, 5,0);
        GridPane.setRowSpan(line4, 10);
        GridPane.setColumnSpan(line4, 10);
        GridPane.setMargin(line4, new Insets(0,0,0,35));
        line1.setStrokeWidth(3);
        line2.setStrokeWidth(3);
        line3.setStrokeWidth(3);
        line4.setStrokeWidth(3);

        /** On Click for TextFields*/
        for(int i = 0; i < MAX_CELLS; i++) {
            for(int j = 0; j < MAX_CELLS; j++) {
                sudokuCells[i][j].setOnMouseClicked(event -> {
                    for(int x = 0; x < MAX_CELLS; x++) {
                        for(int y = 0; y < MAX_CELLS; y++) {
                                sudokuCells[x][y].setStyle("-fx-background-colour: white; -fx-text-inner-color: black;");
                        }
                    }
                });
            }
        }

        for(int i = 0; i < MAX_CELLS; i++) {
            for(int j = 0; j < MAX_CELLS; j++) {
                final int I = i;
                final int J = j;
                sudokuCells[i][j].setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case UP: try {
                            sudokuCells[I-1][J].requestFocus();
                        }catch(Exception e) {
                            System.out.println("");
                        }
                        break;
                        case DOWN: try {
                            sudokuCells[I + 1][J].requestFocus();
                        }catch(Exception e) {
                            System.out.println("");
                        }
                        break;
                        case LEFT:try {
                            sudokuCells[I][J - 1].requestFocus();
                        }catch (Exception e) {
                            System.out.println("");
                        }
                        break;
                        case RIGHT:try {
                            sudokuCells[I][J+1].requestFocus();
                        }catch(Exception e) {
                            System.out.println("");
                        }
                        break;
                        default:System.out.println("");
                    }
                });
            }
        }


        /** Defining Controls for button*/


        //Regex ?!
        for(int i = 0; i < MAX_CELLS; i++) {
            for(int j = 0; j < MAX_CELLS; j++) {
                int R = i;
                int C = j;
                sudokuCells[R][C].textProperty().addListener(((observable, oldValue, newValue) -> {
                    if(newValue.length() > 1){
                        sudokuCells[R][C].setText(oldValue);
                    }
                    if(!newValue.matches("^[1-9]+$")) {
                        sudokuCells[R][C].setText("");
                    }
                }));
            }
        }
        // Clear Button
        clear.setOnAction(event -> {
            for(int i = 0; i < MAX_CELLS; i++) {
                for(int j = 0; j < MAX_CELLS; j++) {
                    sudokuCells[i][j].setText("");
                    numbersArray[i][j] = 0;
                    sudokuCells[i][j].setEditable(true);
                    sudokuCells[i][j].setDisable(false);
                    sudokuCells[i][j].setStyle("-fx-background-colour: white; -fx-text-inner-color: black;");
                    generate.setDisable(false);
                }
            }
        });
        //Exit Button
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Main main = new Main();
                try {
                    main.start(primaryStage);
                    primaryStage.setResizable(false);
                } catch (Exception e) {
                    System.out.println("Err!");
                }

            }
        });

        //Generate Button //Searching for a new method.
        /**
         *
         * Generate sudoku puzzle
         *
         */
        generate.setOnAction(event -> {
            for(int i = 0; i < MAX_CELLS; i++) {
                for(int j = 0; j < MAX_CELLS; j++) {
                    sudokuCells[i][j].setText("");
                    numbersArray[i][j] = 0;
                    sudokuCells[i][j].setEditable(true);
                    sudokuCells[i][j].setDisable(false);
                    sudokuCells[i][j].setStyle("-fx-background-colour: white; -fx-text-inner-color: black;");
                }
            }
            generate.setDisable(true);
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    Random rand = new Random();
                    do {
                        Collections.shuffle(generator);
                        int temp = rand.nextInt(9) + 1;
                        for(int i = 0; i < MAX_CELLS; i++) {
                            for(int j = 0; j < MAX_CELLS; j++) {
                                numbersArray[i][j] = 0;
                            }
                        }
                        for(int i = temp; i < temp + 1; i++) {
                            for(int j = 0; j < MAX_CELLS; j++) {
                                try {
                                    numbersArray[i][j] = generator.get(j);
                                } catch (Exception e) {
                                    generate.setDisable(false);
                                    generate.fire();
                                }
                            }
                        }
                    } while (!solve());

                    //Deleting random numbers , ideally sudoku puzzles have 23-35 hints given, so lets do that.
                    int del = rand.nextInt(59);
                    while (!(del > 46 && del < 59)) {
                        del = rand.nextInt(59);
                    }
                    for(int i = 0; i < del; i++) {
                        int row = rand.nextInt(9);
                        int col = rand.nextInt(9);
                        while(numbersArray[row][col] == 0) {
                            row = rand.nextInt(9);
                            col = rand.nextInt(9);
                        }
                        numbersArray[row][col] = 0;
                    }
                    for(int i = 0; i < MAX_CELLS; i++) {
                        for(int j = 0; j < MAX_CELLS; j++) {
                            if(numbersArray[i][j] == 0) {
                                try {
                                    sudokuCells[i][j].setText("");
                                } catch (Exception e) {
                                    System.out.println("");
                                }
                            }
                            else {
                                sudokuCells[i][j].setText(String.valueOf(numbersArray[i][j]));
                                sudokuCells[i][j].setEditable(false);
                            }
                        }
                    }
                }
            });
            t1.start();
            if(t1.getState().equals(Thread.State.TERMINATED)) {
                generate.setDisable(false);
            }
        });

        //Solve
        solver.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for(int i = 0; i < MAX_CELLS; i++) {
                    for(int j = 0; j < MAX_CELLS; j++) {
                        if(!sudokuCells[i][j].getText().equals("")) {
                            numbersArray[i][j] = Integer.parseInt(sudokuCells[i][j].getText());
                        } else {
                            numbersArray[i][j] =0;
                        }
                    }
                }
                if(solve()) {
                    for(int i = 0; i < MAX_CELLS; i++) {
                        for(int j = 0; j < MAX_CELLS; j++) {
                            sudokuCells[i][j].setText(String.valueOf(numbersArray[i][j]));
                            sudokuCells[i][j].setEditable(false);
                            sudokuCells[i][j].setStyle("-fx-background-color: green; -fx-text-inner-color: white; -fx-font-weight: bold;");
                        }
                    }
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Solution Found!");
                    alert.show();
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("No Solutions Found for this puzzle.");
                    alert.show();
                }

            }
        });
        //For check button

        check.setOnMouseClicked(event -> {
            for(int i = 0; i < MAX_CELLS; i++) {
                for(int j = 0; j < MAX_CELLS; j++) {
                    numbersArray[i][j] = 0;
                }
            }
            for(int i = 0; i < MAX_CELLS; i++) {
                for(int j = 0; j < MAX_CELLS; j++) {
                    if(!sudokuCells[i][j].getText().equals("")) {
                        numbersArray[i][j] = Integer.parseInt(sudokuCells[i][j].getText());
                    }
                }
            }
            for(int i = 0; i < MAX_CELLS; i++) {
                for(int j = 0; j < MAX_CELLS; j++) {
                    if((isInRow1(i, numbersArray[i][j]) && isInCol1(j, numbersArray[i][j]) && isInBox1(i, j, numbersArray[i][j]))) {
                        sudokuCells[i][j].setStyle("-fx-background-color: green; -fx-text-inner-color: white; -fx-font-weight: bold;");
                    } else {
                        sudokuCells[i][j].setStyle("-fx-background-color: red; -fx-font-weight: bold; -fx-text-inner-color: white;");
                    }
                }
            }
        });
    }

    /** Methods for backtracking sudoku solver. There are multiple in here!*/

    public boolean isInRow(int row, int number) {
        for(int i = 0; i < MAX_CELLS; i++) {
            if(numbersArray[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    public boolean isInCol(int col, int number) {
        for(int i = 0; i < MAX_CELLS; i++) {
            if(numbersArray[i][col] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean isInBox(int row, int col, int number) {
        int r = row - row % 3;
        int c = col - col % 3;

        for(int i = r; i < r + 3; i++) {
            for(int j = c; j < c + 3; j++) {
                if(numbersArray[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOk(int row, int col, int number) {
        return !isInRow(row, number) && !isInBox(row, col, number) && !isInCol(col, number);
    }

    public boolean isInRow1(int row, int number) {
        int count = 0;
        for(int i = 0; i < MAX_CELLS; i++) {
            if(numbersArray[row][i] == number) {
                count++;
            }
        }
        if(count > 1) {
            return false;
        }
        return true;
    }
    public boolean isInCol1(int col, int number) {
        int count = 0;
        for(int i = 0; i < MAX_CELLS; i++) {
            if(numbersArray[i][col] == number) {
                count++;
            }
        }
        if(count > 1) {
            return false;
        }
        return true;
    }

    private boolean isInBox1(int row, int col, int number) {
        int count = 0;
        int r = row - row % 3;
        int c = col - col % 3;

        for(int i = r; i < r + 3; i++) {
            for(int j = c; j < c + 3; j++) {
                if(numbersArray[i][j] == number) {
                    count++;
                }
            }
        }
        if(count > 1) {
            return false;
        }
        return true;
    }

    public boolean solve() {
        for(int row = 0; row < MAX_CELLS; row++) {
            for(int col = 0; col < MAX_CELLS; col++) {
                if(numbersArray[row][col] == 0) {
                    for(int number = 1; number <= 9; number++) {
                        if(isOk(row, col, number)) {
                            numbersArray[row][col] = number;
                        if(solve()) {
                            return true;
                        } else  {
                            numbersArray[row][col] = 0;
                        }
                        }

                    }
                    return  false;
                }
            }
        }
        return true;
    }

    /** Methods for button.*/
    //Just for Testing
    public boolean validatorForGenerator() {

        for(int x = 0; x < MAX_CELLS; x++) {
            for(int i = 0; i < MAX_CELLS; i++) {
                for(int j = 0; j < MAX_CELLS; j++) {
                    if(numbersArray[x][j] == numbersArray[i][j] && x != i) {
                        return false;
                    }
                    if(numbersArray[j][x] == numbersArray[j][i] && x != i) {
                        return false;
                    }
                }
            }
        }
        ArrayList<Integer> temp = new ArrayList(asList(1,2,3,4,5,6,7,8,9));
        for(int i = 0; i < MAX_CELLS; i++) {

            if(i % 3 == 0) {
                temp = new ArrayList(asList(1,2,3,4,5,6,7,8,9));
            }
            for(int j = 0; j < 3; j++) {
                temp.remove(Integer.valueOf(numbersArray[i][j]));
            }
            if(i == 2 || i == 5 || i == 8) {
                if(!temp.isEmpty()) {
                    return false;
                }
            }
        }
        temp = new ArrayList(asList(1,2,3,4,5,6,7,8,9));
        for(int i = 0; i < MAX_CELLS; i++) {
            if(i % 3 == 0) {
                temp = new ArrayList(asList(1,2,3,4,5,6,7,8,9));
            }
            for(int j = 3; j < 6; j++) {
                temp.remove(Integer.valueOf(numbersArray[i][j]));
            }
            if(i == 2 || i == 5 || i == 8) {
                if(!temp.isEmpty()) {
                    return false;
                }
            }
        }
        temp = new ArrayList(asList(1,2,3,4,5,6,7,8,9));
        for(int i = 0; i < MAX_CELLS; i++) {

            if(i % 3 == 0) {
                temp = new ArrayList(asList(1,2,3,4,5,6,7,8,9));
            }
            for(int j = 6; j < 9; j++) {
                temp.remove(Integer.valueOf(numbersArray[i][j]));
            }
            if(i == 2 || i == 5 || i == 8) {
                if(!temp.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }


    public static void main(String[] args) {

        launch(args);
    }
}
