import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * GameObj is an object that is part of the game. Every object in the game must have a position, velocity and bounding box
 */
public abstract class GameObj
{
    double xVel; //x velocity
    double yVel; //y velocity

    double xPos; //x position
    double yPos; //y position

    public GameObj()
    {
        xVel = 0;
        yVel = 0;
    }

    enum  HitDirection {ABOVE, BELOW, LEFT, RIGHT, Y_UNK, X_UNK, UNKNOWN}; //which way can this object hit the ball? (Unknown if gameobj is moveable)

    /**
     * Determins if game object o is colliding with this object
     * @param o object to test collision with
     * @return whether object is colliding with this object
     */
    public boolean isCollision(GameObj o) //is this object colliding with o?
    {
        return o.getBoundingBox().intersects(this.getBoundingBox());
    }

    /**
     * Returns bounding box for this object
     * @return bounding box for this object
     */
    public abstract Bounds getBoundingBox(); //returns bounding box for object

    /**
     * Returns direction this object hit another from (some things are constant... like walls)
     * @return
     */
    public HitDirection getHitDirection()
    {
        return HitDirection.UNKNOWN; //default unknown hit direction. Only known for set objects like walls
    }
}
