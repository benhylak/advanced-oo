import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Created by benhylak on 12/11/16.
 */
public class VerticalWall extends Wall
{
    public VerticalWall(double minX, double minY, double height)
    {
        super(minX, minY, height);

        wallBounds = new BoundingBox(this.minX, this.minY, 0, height);
    }

    @Override
    public Bounds getBoundingBox()
    {
        return wallBounds;
    }

    public static class LeftWall extends VerticalWall
    {
        final int wall_padding = 50;

        public LeftWall(double width, double height)
        {
            super(0, 0, height);
            wallBounds = new BoundingBox(this.minX-wall_padding, this.minY, wall_padding, height);
        }

        @Override
        public boolean isCollision(GameObj o)
        {
            return (super.isCollision(o) ||
                    o.getBoundingBox().getMinX() < wallBounds.getMaxX());
        }

        @Override
        public HitDirection getHitDirection()
        {
            return HitDirection.LEFT;
        }
    }

    public static class RightWall extends HorizontalWall
    {
        public RightWall(double width, double height)
        {
            super(width, 0, height);

            wallBounds = new BoundingBox(this.minX, this.minY, wall_padding, height);
        }

        @Override
        public boolean isCollision(GameObj o)
        {
            return (super.isCollision(o) ||
                    o.getBoundingBox().getMaxX() > wallBounds.getMinX());
        }

        @Override
        public HitDirection getHitDirection()
        {
            return HitDirection.RIGHT;
        }
    }
}
