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
	public static final double INITIAL_VX = 0;
	/**
	 * The initial velocity of the ball in the y direction.
	 */
	public static final double INITIAL_VY = 1e-7;

	public static final double MAX_V = 7e-7;
	// Instance variables
	// (x,y) is the position of the center of the ball.
	private double x, y;
	private Circle circle;

	private ArrayList<WallCollisionEvent.WallCollisionListener> collisionListeners;
	private ArrayList<AnimalHitEvent.AnimalHitListener> hitListeners;

	public static final double TOP_BOTTOM_MARGIN = 5;
	public static final double LEFT_RIGHT_MARGIN = 20;

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

	public void checkCollisions(Collection<GameObj> objs)
	{
		for(GameObj obj : objs)
		{
			if(obj != this && obj.isCollision(this))
			{
				if(obj instanceof Wall)
				{
					notifyWallCollision((Wall)obj);
				}
				else if(obj instanceof Animal)
				{
					notifyAnimalHit((Animal)obj);
				}

				if(!(obj instanceof Ball))
				{
					adjustForCollision(obj);
				}
			}
		}
	}

	public void adjustForCollision(GameObj obj)
	{
		Bounds objBounds = obj.getBoundingBox();
		Bounds ballBounds = getBoundingBox();

		HitDirection hitDir = HitDirection.UNKNOWN;

		if(obj.getHitDirection() != HitDirection.UNKNOWN)
		{
			hitDir = obj.getHitDirection();
		}
		else
		{
			if(isYCollision(objBounds))
			{
				if(ballBounds.getMaxY() - objBounds.getMinY() <= TOP_BOTTOM_MARGIN)
				{
					hitDir = HitDirection.BELOW;
				}
				else if(objBounds.getMaxY() - ballBounds.getMinY() <= TOP_BOTTOM_MARGIN)
				{
					hitDir = HitDirection.ABOVE;
				}
				else hitDir = HitDirection.Y_UNK;

			}
			else if(isXCollision(objBounds))
			{
				if(objBounds.getMaxX() - ballBounds.getMinX() <= LEFT_RIGHT_MARGIN)
				{
					hitDir = HitDirection.LEFT;
				}
				else if(ballBounds.getMaxX() - objBounds.getMinX() <= LEFT_RIGHT_MARGIN)
				{
					hitDir = HitDirection.RIGHT;
				}
				else hitDir = HitDirection.X_UNK;
			}
		}

		switch (hitDir)
		{
			case ABOVE:
			{
				if(objBounds.contains(ballBounds)) //uh oh, we are stuck!
				{
					this.y = objBounds.getMaxY() - 3;
				}

				yVel =  1 * Math.abs(yVel);

				break;
			}

			case BELOW:
			{
				if(objBounds.contains(ballBounds)) //uh oh, we are stuck!
				{
					this.y = objBounds.getMinY() + 3;
				}

				yVel = -1 * Math.abs(yVel); //lower collision

				break;
			}

			case LEFT:
			{
				if(objBounds.contains(ballBounds)) //uh oh, we are stuck!
				{
					this.x = objBounds.getMaxX() + 3;
				}

				xVel =  Math.abs(xVel);

				break;
			}

			case RIGHT:
			{
				if(objBounds.contains(ballBounds)) //uh oh, we are stuck!
				{
					this.x = objBounds.getMinX() - 3;
				}

				xVel = -1 * Math.abs(xVel); //left collision

				break;
			}

			case Y_UNK: reverseVelocityY();
				break;

			case X_UNK: reverseVelocityX();
				break;

		}

		if(xVel>MAX_V) xVel = MAX_V;
		if(yVel > MAX_V) yVel =MAX_V;

		if(Math.abs(xVel+obj.xVel) > MAX_V || Math.abs(yVel + obj.yVel) > MAX_V)
		{
			xVel -= Math.max(obj.xVel, obj.yVel);
			yVel -= Math.max(obj.xVel, obj.yVel);
		}

		this.xVel += obj.xVel;
		this.yVel += obj.yVel;
	}

	public boolean isYCollision(Bounds target)
	{
		Bounds upperYBound = new BoundingBox(target.getMinX(), target.getMaxY(), target.getWidth(), 0);
		Bounds lowerYBound = new BoundingBox(target.getMinX(), target.getMinY(),  target.getWidth(), 0);

		Bounds ballBounds = getBoundingBox();

		return(ballBounds.intersects(upperYBound) || ballBounds.intersects(lowerYBound));
	}

	public boolean isXCollision(Bounds target)
	{
		Bounds rightXBound = new BoundingBox(target.getMaxX(), target.getMinY(), 0, target.getHeight());
		Bounds leftXBound = new BoundingBox(target.getMinX(), target.getMinY(), 0, target.getHeight());

		Bounds ballBounds = getBoundingBox();

		return(ballBounds.intersects(rightXBound) || ballBounds.intersects(leftXBound));
	}

	private void reverseVelocityX()
	{
		xVel *= -1;
	}

	private void reverseVelocityY()
	{
		yVel *= -1;
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
