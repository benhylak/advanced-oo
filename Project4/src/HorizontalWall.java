import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Created by benhylak on 12/11/16.
 */
public class HorizontalWall extends Wall
{
    /**
     *
     * @param minX minimum x value
     * @param minY minimum y value
     * @param width width of wall
     */
    public HorizontalWall(double minX, double minY, double width)
    {
        super(minX, minY, width);

        wallBounds = new BoundingBox(this.minX, this.minY, width, 4);
    }


    /**
     * Bounding box for wall
     * @return bounding box
     */
    @Override
    public Bounds getBoundingBox()
    {
        return wallBounds;
    }

    /**
     * Special type of horizontal wall on top
     */
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

    /**
     * Special type of lower ball on bottom
     */
    public static class LowerWall extends HorizontalWall
    {
        public LowerWall(double width, double height)
        {
            super(0, height, width);

            wallBounds = new BoundingBox(this.minX, this.minY, width, wall_padding);
        }

        /**
         *
         * @param o object to test collision with
         * @return if object is collided
         */
        @Override
        public boolean isCollision(GameObj o)
        {
            return (super.isCollision(o) ||
                    o.getBoundingBox().getMaxY() > wallBounds.getMinY());
        }

        /**
         *
         * @return hit dir
         */
        @Override
        public HitDirection getHitDirection()
        {
            return HitDirection.BELOW;
        }
    }
}
