import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Created by benhylak on 12/11/16.
 */
public class VerticalWall extends Wall
{
    /**
     * Vertical wall constructor
     * @param minX min x location
     * @param minY min y location
     * @param height height of wall
     */
    public VerticalWall(double minX, double minY, double height)
    {
        super(minX, minY, height);

        wallBounds = new BoundingBox(this.minX, this.minY, 0, height);
    }

    /**
     *
     * @return bounding box for wall
     */
    @Override
    public Bounds getBoundingBox()
    {
        return wallBounds;
    }

    /**
     * Special type of vertical wall that is on the left
     */
    public static class LeftWall extends VerticalWall
    {
        final int wall_padding = 50;

        /**
         *
         * @param width of wall
         * @param height of wall
         */
        public LeftWall(double width, double height)
        {
            super(0, 0, height);
            wallBounds = new BoundingBox(this.minX-wall_padding, this.minY, wall_padding, height);
        }

        /**
         *
         * @param o object to test collision with
         * @return whether object collided
         */
        @Override
        public boolean isCollision(GameObj o)
        {
            return (super.isCollision(o) ||
                    o.getBoundingBox().getMinX() < wallBounds.getMaxX());
        }

        /**
         *
         * @return direction of hit
         */
        @Override
        public HitDirection getHitDirection()
        {
            return HitDirection.LEFT;
        }
    }

    /**
     * Special type of vertical wall on the right
     */
    public static class RightWall extends HorizontalWall
    {
        public RightWall(double width, double height)
        {
            super(width, 0, height);

            wallBounds = new BoundingBox(this.minX, this.minY, wall_padding, height);
        }

        /**
         * Tests collision
         * @return if collision
         */
        @Override
        public boolean isCollision(GameObj o)
        {
            return (super.isCollision(o) ||
                    o.getBoundingBox().getMaxX() > wallBounds.getMinX());
        }

        /**
         * Gets hit direction
         * @return if there was a hit
         */
        @Override
        public HitDirection getHitDirection()
        {
            return HitDirection.RIGHT;
        }
    }
}
