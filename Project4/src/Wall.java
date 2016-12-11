/**
 * Created by benhylak on 12/10/16.
 */
public abstract class Wall extends GameObj
{
    @Override
    public boolean isCollision(GameObj obj)
    {
        return false;
    }
}
