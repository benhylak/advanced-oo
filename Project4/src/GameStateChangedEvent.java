import javafx.event.EventType;
import java.util.EventObject;


/**
 * Created by benhylak on 12/10/16.
 */
public class GameStateChangedEvent extends EventObject
{
    Environment.GameState state;

    public GameStateChangedEvent(Object source, Environment.GameState state)
    {
        super(source);
        this.state = state;
    }

    public Environment.GameState getState()
    {
        return state;
    }

    public interface GameStateListener
    {
        public void handleStateChange();

    }
}

