import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Class for a wall game object. A wall exists namely at the edges of the screen where nothing is drawn, but you'd
 * expect the ball to bounce back. (Could be made drawable by extending + implementing the drawable interface)
 */
public abstract class Wall extends GameObj
{
    final int wall_padding = 80;

    double minX;
    double minY;
    double size;

    BoundingBox wallBounds; //bounding box for wall

    /**
     * Default constructor for a wall
     *
     * @param minX minimum x coordinate (upper left corner)
     * @param minY minimum y coordinate (upper left corner)
     * @param size of the wall
     */
    public Wall(double minX, double minY, double size)
    {
        this.minX = minX;
        this.minY = minY;
        this.size = size;

        xVel = 0;
        yVel = 0;
    }

}
