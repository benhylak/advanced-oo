import java.util.EventObject;

/**
 * Created by benhylak on 12/11/16.
 */
public class AnimalHitEvent extends EventObject
{
    Animal source;
    Ball b;

    public AnimalHitEvent(Animal a, Ball b)
    {
        super(a);

        this.source = a;
        this.b = b;
    }

    public interface AnimalHitListener
    {
        public void handleAnimalHit(AnimalHitEvent e);
    }
}
