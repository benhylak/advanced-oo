import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Created by benhylak on 12/11/16.
 */
public class HorizontalWall extends Wall
{
    public HorizontalWall(double minX, double minY, double width)
    {
        super(minX, minY, width);

        wallBounds = new BoundingBox(this.minX, this.minY, width, 4);
    }


    @Override
    public Bounds getBoundingBox()
    {
        return wallBounds;
    }

    public static class UpperWall extends HorizontalWall
    {
        public UpperWall(double width, double height)
        {
            super(0, 0, width);
            wallBounds = new BoundingBox(this.minX, this.minY-wall_padding, width, wall_padding);
        }

        @Override
        public boolean isCollision(GameObj o)
        {
            return (super.isCollision(o) ||
                    o.getBoundingBox().getMinY() < wallBounds.getMaxY());
        }

        @Override
        public HitDirection getHitDirection()
        {
            return HitDirection.ABOVE;
        }
    }

    public static class LowerWall extends HorizontalWall
    {
        public LowerWall(double width, double height)
        {
            super(0, height, width);

            wallBounds = new BoundingBox(this.minX, this.minY, width, wall_padding);
        }

        @Override
        public boolean isCollision(GameObj o)
        {
            return (super.isCollision(o) ||
                    o.getBoundingBox().getMaxY() > wallBounds.getMinY());
        }

        @Override
        public HitDirection getHitDirection()
        {
            return HitDirection.BELOW;
        }
    }
}
