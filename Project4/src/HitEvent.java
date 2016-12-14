import java.util.EventObject;

/**
 * Created by benhylak on 12/11/16.
 */
public class HitEvent<T> extends EventObject
{
    T source;
    Ball b;

    public HitEvent(T a, Ball b)
    {
        super(a);

        this.source = a;
        this.b = b;
    }

    public interface HitListener<U>
    {
        public void handleHit(HitEvent<U> e);
    }
}

