import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Created by benhylak on 12/10/16.
 */
public abstract class GameObj
{
    double xVel;
    double yVel;

    enum  HitDirection {ABOVE, BELOW, LEFT, RIGHT, Y_UNK, X_UNK, UNKNOWN}; //which way can this object hit the ball? (Unknown if gameobj is moveable)

    public boolean isCollision(GameObj o)
    {
        return o.getBoundingBox().intersects(this.getBoundingBox());
    }

    public abstract Bounds getBoundingBox();

    public HitDirection getHitDirection()
    {
        return HitDirection.UNKNOWN;
    }
//    static class BoundingBox
//    {
//        static class BoundingPoint
//        {
//            public double x;
//            public double y;
//
//            public BoundingPoint(double x, double  y)
//            {
//                this.x = x;
//                this.y = y;
//            }
//
//            public BoundingPoint(BoundingPoint start, double xOffset, double yOffset)
//            {
//                this.x = start.x + xOffset;
//                this.y = start.y + yOffset;
//            }
//        }
//
//        BoundingPoint upperRight;
//        BoundingPoint lowerLeft;
//
//        public  BoundingBox(BoundingPoint upperRight, BoundingPoint lowerLeft)
//        {
//            this.upperRight = upperRight;
//            this.lowerLeft = lowerLeft;
//        }
//
//        public  boolean inBox(BoundingBox b)
//        {
//            return (xIntersect(b) && yIntersect(b)) ||
//                    (b.xIntersect(this) && yIntersect(this));
//        }
//
//        private boolean xIntersect(BoundingBox b)
//        {
//            return inRange(b.upperRight.x, lowerLeft.x, upperRight.x) ||
//                    inRange(b.lowerLeft.x, lowerLeft.x, upperRight.x) ;
//        }
//
//        private boolean yIntersect(BoundingBox b)
//        {
//            return inRange(b.upperRight.y, lowerLeft.y, upperRight.y) ||
//                    inRange(b.lowerLeft.y, lowerLeft.y, upperRight.y);
//        }
//
//        private boolean inRange(double target, double low, double high)
//        {
//            return (low<= target && target <= high);
//        }
    //}
}
