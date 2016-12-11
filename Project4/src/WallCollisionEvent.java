import java.util.EventObject;

/**
 * Created by benhylak on 12/11/16.
 */
public class WallCollisionEvent extends EventObject
{
    Ball b;
    Wall w;

    public WallCollisionEvent(Wall source, Ball b)
    {
        super(source);

        this.b = b;
        this.w = source;
    }

    public Wall getWall()
    {
        return w;
    }

    public interface WallCollisionListener
    {
        public void handleCollision(WallCollisionEvent e);
    }
}
