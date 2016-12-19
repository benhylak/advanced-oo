import java.util.ArrayList;
import java.util.Collection;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
	public static final double INITIAL_VX = 0;
	/**
	 * The initial velocity of the ball in the y direction.
	 */
	public static final double INITIAL_VY = 1e-7;

	public double MAX_V = 5.5e-7;
	// Instance variables
	// (x,y) is the position of the center of the ball.
	private double x, y;
	private Circle circle;

	private ArrayList<HitEvent.HitListener<Wall>> wallHitListeners;
	private ArrayList<HitEvent.HitListener<Animal>> animalHitListeners;

	public static final double TOP_BOTTOM_MARGIN = 5;
	public static final double LEFT_RIGHT_MARGIN = 20;

	/**
	 * Gets the drawing for this ball
	 * @return circle representing the ball
	 */
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

		wallHitListeners = new ArrayList<>();
		animalHitListeners = new ArrayList<>();
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

	/**
	 * Checks collisions between the ball and a collection of game objects. Raises either a wall hit event or
	 * animal hit event if a wall or an animal is hit
	 * @param objs Game objects to check
	 */
	public void checkCollisions(Collection<GameObj> objs)
	{
		for(GameObj obj : objs)
		{
			if(obj != this && obj.isCollision(this))
			{
				if(obj instanceof Wall)
				{
					notifyWallHit((Wall)obj);
				}
				else if(obj instanceof Animal)
				{
					notifyAnimalHit((Animal)obj);
				}

				adjustForCollision(obj);
			}
		}
	}

	/**
	 * Determins the direction the object hit this ball from
	 *
	 * @param obj The object that hit the ball
	 * @return the direction the object contacted the ball from
	 */
	public HitDirection determineHitDirection(GameObj obj)
	{
		Bounds objBounds = obj.getBoundingBox();
		Bounds ballBounds = getBoundingBox();

		HitDirection hitDir = HitDirection.UNKNOWN;

		if(obj.getHitDirection() != HitDirection.UNKNOWN) //if the hit direction is known
		{
			hitDir =obj.getHitDirection();
		}
		else if(isYCollision(objBounds))
		{
			if(ballBounds.getMaxY() - objBounds.getMinY() <= TOP_BOTTOM_MARGIN) //within a margin of error of the top
			{
				hitDir = HitDirection.BELOW;
			}
			else if(objBounds.getMaxY() - ballBounds.getMinY() <= TOP_BOTTOM_MARGIN) //within a margin of error of the bottom
			{
				hitDir = HitDirection.ABOVE;
			}
			else hitDir = HitDirection.Y_UNK; //somewhere in the middle of the object :(

		}
		else if(isXCollision(objBounds))
		{
			if(objBounds.getMaxX() - ballBounds.getMinX() <= LEFT_RIGHT_MARGIN) //within a margin of error of the left
			{
				hitDir = HitDirection.LEFT;
			}
			else if(ballBounds.getMaxX() - objBounds.getMinX() <= LEFT_RIGHT_MARGIN) //.....you get the point
			{
				hitDir = HitDirection.RIGHT;
			}
			else hitDir = HitDirection.X_UNK; //somewhere in the middle of the object :(
		}

		return hitDir;
	}

	/**
	 * Adjusts the ball's course from a collision with obj. Accounts for direction hit was from and the objects velocity
	 * when it was hit. (Which allows for soft or hard hits..)
	 *
	 * @param obj Game object to adjust for collision with ball
	 */
	public void adjustForCollision(GameObj obj)
	{
		Bounds objBounds = obj.getBoundingBox();
		Bounds ballBounds = getBoundingBox();

		switch (determineHitDirection(obj))
		{
			case ABOVE: //ex: above wall
			{
				if(objBounds.contains(ballBounds)) //uh oh, we are stuck!
				{
					this.y = objBounds.getMaxY() - 1;
				}

				yVel =  1 * Math.abs(yVel);

				break;
			}

			case BELOW: //ex: bottom wall or top of paddle
			{
				if(objBounds.contains(ballBounds)) //uh oh, we are stuck!
				{
					this.y = objBounds.getMinY() - 1;
				}

				yVel = -1 * Math.abs(yVel); //lower collision

				break;
			}

			case LEFT: //object is to the LEFT of the ball
			{
				if(objBounds.contains(ballBounds)) //uh oh, we are stuck!
				{
					this.x = objBounds.getMaxX() - 1; //if the ball is inside the object, set a min distance
				}

				xVel =  Math.abs(xVel);

				break;
			}

			case RIGHT: //object is to the right of the ball
			{
				if(objBounds.contains(ballBounds)) //uh oh, we are stuck!
				{
					this.x = objBounds.getMinX() - 1; //if the ball is inside the object, set a min distance
				}

				xVel = -1 * Math.abs(xVel); //left collision

				break;
			}

			case Y_UNK: reverseVelocityY(); //if hit direction is unknown, just reverse vel
				break;

			case X_UNK: reverseVelocityX(); //if hit direction is unknown just reverse vel
				break;

		}


		if(this.xVel > MAX_V) this.xVel = MAX_V;
		if(this.yVel > MAX_V) this.yVel = MAX_V;

		this.xVel += obj.xVel; //add objects to velocity to our velocity
		this.yVel += obj.yVel;
	}

	/**
	 * Determins if collision was in y direction (up and down). For example: a paddle
	 * @param target
	 * @return
	 */
	public boolean isYCollision(Bounds target) //did we collide in Y direction?
	{
		Bounds upperYBound = new BoundingBox(target.getMinX(), target.getMaxY(), target.getWidth(), 0);
		Bounds lowerYBound = new BoundingBox(target.getMinX(), target.getMinY(),  target.getWidth(), 0);

		Bounds ballBounds = getBoundingBox();

		return(ballBounds.intersects(upperYBound) || ballBounds.intersects(lowerYBound));
	}

	/**
	 * Determins if collision was in x direction (side to side) For example: a left/right wall
	 *
	 * @param target
	 * @return whether collision was in x direction
	 */
	public boolean isXCollision(Bounds target)
	{
		Bounds rightXBound = new BoundingBox(target.getMaxX(), target.getMinY(), 0, target.getHeight());
		Bounds leftXBound = new BoundingBox(target.getMinX(), target.getMinY(), 0, target.getHeight());

		Bounds ballBounds = getBoundingBox();

		return(ballBounds.intersects(rightXBound) || ballBounds.intersects(leftXBound));
	}

	/**
	 * Reverses the ball's velocity in the x direction
	 */
	private void reverseVelocityX()
	{
		xVel *= -1;
	}

	/**
	 * Reverses the ball's velocity in the y direction
	 */
	private void reverseVelocityY()
	{
		yVel *= -1;
	}

	/**
	 * Adds a listener for a collision with this ball and a wall
	 * @param listener object that wants to listen
	 */
	public void addWallCollisionListener(HitEvent.HitListener<Wall> listener)
	{
		wallHitListeners.add(listener);
	}

	/**
	 * Adds a listener for a collision with this ball and an animal
	 * @param listener object that wants to hit
	 */
	public void addAnimalHitListener(HitEvent.HitListener<Animal> listener)
	{
		animalHitListeners.add(listener);
	}

	/**
	 * Notify wall hit listeners that a wall was hit
	 * @param wall that we hit
	 */
	public void notifyWallHit(Wall wall)
	{
		for(HitEvent.HitListener<Wall> l : wallHitListeners)
		{
			l.handleHit(new HitEvent<Wall>(wall, this));
		}
	}

	/**
	 * Notify animal hit listeners that an animal was hit
	 * @param a animal we hit
	 */
	public void notifyAnimalHit(Animal a)
	{
		for(HitEvent.HitListener l : animalHitListeners)
		{
			l.handleHit(new HitEvent<Animal>(a, this));
		}
	}

	/**
	 * Returns bounding box of this ball
	 * @return bounding box
	 */
	@Override
	public Bounds getBoundingBox()
	{
		return circle.getBoundsInParent();
	}
}
