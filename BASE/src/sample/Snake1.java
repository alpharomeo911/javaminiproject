package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Snake1 extends Application {
	// variable
	int speed = 5;
	int foodcolor = 0;
	int width = 20;
	int height = 20;
	int foodX = 0;
	int foodY = 0;
	int cornersize = 25;
	List<Corner> snake = new ArrayList<>();
	Dir direction = Dir.left;
	boolean gameOver = false;
	Random rand = new Random();

	public enum Dir {
		left, right, up, down
	}

	public static class Corner {
		int x;
		int y;

		public Corner(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}

	public void start(Stage primaryStage) {
		try {
			newFood();

			VBox root = new VBox();
			Canvas c = new Canvas(width * cornersize, height * cornersize);
			GraphicsContext gc = c.getGraphicsContext2D();
			root.getChildren().add(c);

			new AnimationTimer() {
				long lastTick = 0;

				public void handle(long now) {
					if (lastTick == 0 && !gameOver) {
						lastTick = now;
						tick(gc, primaryStage, root);
						return;
					}

					if (now - lastTick > 1000000000 / speed && !gameOver) {
						lastTick = now;
						tick(gc, primaryStage, root);
						if(gameOver) {
							Alert alert = new Alert(Alert.AlertType.WARNING);
							alert.setTitle("Game Over!");
							alert.setHeaderText("Your final score is: ");
							alert.setContentText(" " + (speed - 6)); // Score to be added
							alert.show();
							try{
								Thread.sleep(3000);
							} catch(Exception e) {
								System.out.println("Whoops");
							}
							alert.close();
							Snake snake = new Snake();
							snake.start(primaryStage);
						}
					}
				}

			}.start();
			root.setAlignment(Pos.CENTER);
			Scene scene = new Scene(root, width * cornersize, height * cornersize);
			root.setStyle("-fx-background-color: #000000");

			// control
			scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
				if (key.getCode() == KeyCode.W || key.getCode() == KeyCode.UP && direction != Dir.down) {
					direction = Dir.up;
				}
				if (key.getCode() == KeyCode.A || key.getCode() == KeyCode.LEFT && direction != Dir.right) {
					direction = Dir.left;
				}
				if (key.getCode() == KeyCode.S || key.getCode() == KeyCode.DOWN && direction != Dir.up) {
					direction = Dir.down;
				}
				if (key.getCode() == KeyCode.D || key.getCode() == KeyCode.RIGHT && direction != Dir.left) {
					direction = Dir.right;
				}

			});

			// add start snake parts
			snake.add(new Corner(width / 2, height / 2));
			snake.add(new Corner(width / 2, height / 2));
			snake.add(new Corner(width / 2, height / 2));

			primaryStage.setScene(scene);
			primaryStage.setTitle("SNAKE GAME");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// tick
	public void tick(GraphicsContext gc, Stage primaryStage, VBox root) {
		if (gameOver) {
			gc.setFill(Color.DARKRED);
			gc.setFont(new Font("", 50));
			gc.fillText("GAME OVER", 100, 250);
			return;
		}

		for (int i = snake.size() - 1; i >= 1; i--) {
			snake.get(i).x = snake.get(i - 1).x;
			snake.get(i).y = snake.get(i - 1).y;
		}

		switch (direction) {
			case up:
				snake.get(0).y--;
				if (snake.get(0).y < 0) {
					gameOver = true;
				}
				break;
			case down:
				snake.get(0).y++;
				if (snake.get(0).y >= height) {
					gameOver = true;
				}
				break;
			case left:
				snake.get(0).x--;
				if (snake.get(0).x < 0) {
					gameOver = true;
				}
				break;
			case right:
				snake.get(0).x++;
				if (snake.get(0).x >= width)	 {
					gameOver = true;
				}
				break;

		}

		// eat food
		if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
			snake.add(new Corner(-1, -1));
			newFood();
		}

		// self destroy
		for (int i = 1; i < snake.size(); i++) {
			if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
				gameOver = true;
			}
		}

		// fill
		// background
		gc.setFill(Color.DARKGREEN);
		gc.fillRect(0, 0, width * cornersize, height * cornersize);

		// score
		gc.setFill(Color.PEACHPUFF);
		gc.setFont(new Font("", 30));
		gc.fillText("Score: " + (speed - 6), 10, 30);

		// random foodcolor
		Color cc = Color.WHITE;

		switch (foodcolor) {
			case 0:
				cc = Color.MAGENTA;
				break;
			case 1:
				cc = Color.LIGHTBLUE;
				break;
			case 2:
				cc = Color.YELLOW;
				break;
			case 3:
				cc = Color.PINK;
				break;
			case 4:
				cc = Color.ORANGE;
				break;
			case 5:
				cc = Color.DARKRED;
				break;
			case 6:
				cc = Color.LIGHTGOLDENRODYELLOW;
				break;
		}
		gc.setFill(cc);
		gc.fillOval(foodX * cornersize, foodY * cornersize, cornersize, cornersize);

		// snake
		for (Corner c : snake) {
			gc.setFill(Color.BLACK);
			gc.fillRect(c.x * cornersize, c.y * cornersize, cornersize - 1, cornersize - 1);
			gc.setFill(Color.BLACK);
			gc.fillRect(c.x * cornersize, c.y * cornersize, cornersize - 2, cornersize - 2);

		}
	}

	// food
	public void newFood() {
		start: while (true) {
			foodX = rand.nextInt(width);
			foodY = rand.nextInt(height);

			for (Corner c : snake) {
				if (c.x == foodX && c.y == foodY) {
					continue start;
				}
			}
			foodcolor = rand.nextInt(5);
			speed++;
			break;

		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}