import java.util.ArrayList;
import java.util.Collection;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Class that implements a ball with a position and velocity.
 */
public class Ball extends GameObj implements Drawable
{
	// Constants
	/**
	 * The radius of the ball.
	 */
	public static final int BALL_RADIUS = 8;
	/**
	 * The initial velocity of the ball in the x direction.
	 */
	public static final double INITIAL_VX = 1e-7;
	/**
	 * The initial velocity of the ball in the y direction.
	 */
	public static final double INITIAL_VY = 1e-7;

	// Instance variables
	// (x,y) is the position of the center of the ball.
	private double x, y;
	private Circle circle;

	private ArrayList<WallCollisionEvent.WallCollisionListener> collisionListeners;
	private ArrayList<AnimalHitEvent.AnimalHitListener> hitListeners;


	public Node getDrawing()
	{
		return getCircle();
	}

	/**
	 * @return the Circle object that represents the ball on the game board.
	 */
	public Circle getCircle () {
		return circle;
	}

	/**
	 * Constructs a new Ball object at the centroid of the game board
	 * with a default velocity that points down and right.
	 */
	public Ball ()
	{
		x = GameImpl.WIDTH/2;
		y = GameImpl.HEIGHT/2;
		xVel = INITIAL_VX;
		yVel = INITIAL_VY;

		circle = new Circle(BALL_RADIUS, BALL_RADIUS, BALL_RADIUS);
		circle.setLayoutX(x - BALL_RADIUS);
		circle.setLayoutY(y - BALL_RADIUS);
		circle.setFill(Color.BLACK);

		collisionListeners = new ArrayList<>();
		hitListeners = new ArrayList<>();
	}

	/**
	 * Updates the position of the ball, given its current position and velocity,
	 * based on the specified elapsed time since the last update.
	 * @param deltaNanoTime the number of nanoseconds that have transpired since the last update
	 */
	public void updatePosition (long deltaNanoTime)
	{
		double dx = xVel * deltaNanoTime;
		double dy = yVel * deltaNanoTime;

		x += dx;
		y += dy;

		circle.setTranslateX(x - (circle.getLayoutX() + BALL_RADIUS));
		circle.setTranslateY(y - (circle.getLayoutY() + BALL_RADIUS));
	}

//	private BoundingBox.BoundingPoint getLowerLeft()
//	{
//		return new BoundingBox.BoundingPoint(circle.getTranslateX() + circle.getLayoutX(),
//				circle.getTranslateY() + circle.getLayoutY());
//	}

	public void checkCollisions(Collection<GameObj> objs)
	{
		for(GameObj obj : objs)
		{
			if(obj.isCollision(this))
			{
				if(obj instanceof Wall)
				{
					notifyWallCollision((Wall)obj);
				}
				else if(obj instanceof Animal)
				{
					notifyAnimalHit((Animal)obj);
				}
				else if(!(obj instanceof Ball))
				{
					System.out.println("Collided with Paddle");
				}
			}
		}
	}

	public void addWallCollisionListener(WallCollisionEvent.WallCollisionListener listener)
	{
		collisionListeners.add(listener);
	}

	public void addAnimalHitListener(AnimalHitEvent.AnimalHitListener listener)
	{
		hitListeners.add(listener);
	}


	public void notifyWallCollision(Wall wall)
	{
		for(WallCollisionEvent.WallCollisionListener l : collisionListeners)
		{
			l.handleCollision(new WallCollisionEvent(wall, this));
		}
	}

	public void notifyAnimalHit(Animal a)
	{
		for(AnimalHitEvent.AnimalHitListener l : hitListeners)
		{
			l.handleAnimalHit(new AnimalHitEvent(a, this));
		}
	}

	@Override
	public Bounds getBoundingBox()
	{
		//BoundingBox.BoundingPoint lowerLeft = getLowerLeft();

		//BoundingBox.BoundingPoint upperRight = new BoundingBox.BoundingPoint(lowerLeft, BALL_RADIUS, BALL_RADIUS);

		return circle.getBoundsInParent();
	}
}
