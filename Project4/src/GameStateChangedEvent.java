import javafx.event.EventType;
import java.util.EventObject;


/**
 * Event representing a change in game state
 */
public class GameStateChangedEvent extends EventObject
{
    Environment.GameState state;

    public GameStateChangedEvent(Object source, Environment.GameState state)
    {
        super(source);
        this.state = state;
    }

    /**
     *
     * @return state of game event just changed
     */
    public Environment.GameState getState()
    {
        return state;
    }

    public interface GameStateListener
    {
        /**
         * function for how listener handles change
         * @param e
         */
        public void handleStateChange(GameStateChangedEvent e);

    }
}

