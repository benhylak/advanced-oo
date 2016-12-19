import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Created by benhylak on 12/10/16.
 */
public abstract class Wall extends GameObj
{
    final int wall_padding = 80;

    double minX;
    double minY;
    double size;

    BoundingBox wallBounds;

    public Wall(double minX, double minY, double size)
    {
        this.minX = minX;
        this.minY = minY;
        this.size = size;

        xVel = 0;
        yVel = 0;
    }

}
