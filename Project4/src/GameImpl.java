import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import javafx.event.*;
import java.util.*;

public class GameImpl extends Pane implements Game
{


	// Constants
	/**
	 * The width of the game board.
	 */
	public static final int WIDTH = 400;
	/**
	 * The height of the game board.
	 */
	public static final int HEIGHT = 600;

	// Instance variables
	private Ball ball;
	private Paddle paddle;
	Environment env;

	Label startLabel;

	/**
	 * Constructs a new GameImpl.
	 */
	public GameImpl()
	{
		setStyle("-fx-background-color: white;");
		env = new Environment(WIDTH, HEIGHT);

		env.addStateChangeListener(e ->
		{
			if(e.getState() == Environment.GameState.ACTIVE)
			{
				run();
			}
			else
			{
				getChildren().clear();  // remove all components from the game
				getChildren().addAll(env.getDrawings());

				startLabel = new Label(env.getMessage() + "Click mouse to start");

				startLabel.setLayoutX(WIDTH / 2 - 50);
				startLabel.setLayoutY(HEIGHT / 2 + 100);

				getChildren().add(startLabel);
			}
		});

		setOnMouseMoved(event ->
				{
					if (env.isActive())
					{
						env.movePaddleTo(event.getX(), event.getY());
					}
				}
		);

		setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e)
			{
				//GameImpl.this.setOnMouseClicked(null);
				// As soon as the mouse is clicked, remove the startLabel from the game board
				getChildren().remove(startLabel);
				env.startGame();
			}
		});

		env.newGame();
	}

	public String getName()
	{
		return "Zutopia";
	}

	public Pane getPane()
	{
		return this;
	}


	/**
	 * Begins the game-play by creating and starting an AnimationTimer.
	 */
	public void run()
	{
		// Instantiate and start an AnimationTimer to update the component of the game.
		AnimationTimer a = new AnimationTimer()
		{
			private long lastNanoTime = -1;

			public void handle(long currentNanoTime)
			{
				if (lastNanoTime >= 0)
				{
					env.runTimeStep(currentNanoTime-lastNanoTime);
				}
				// Keep track of how much time actually transpired since the last clock-tick.
				lastNanoTime = currentNanoTime;
			}
		};

		env.addStateChangeListener(e -> {
			if(e.getState() != Environment.GameState.ACTIVE)
			{
				a.stop();
			}
		});

		a.start();
	}
}

	/**
	 * Updates the state of the game at each timestep. In particular, this method should
	 * move the ball, check if the ball collided with any of the animals, walls, or the paddle, etc.
	 * @param deltaNanoTime how much time (in nanoseconds) has transpired since the last update
	 * @return the current game state
	 */

