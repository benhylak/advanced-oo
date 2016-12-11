import javafx.scene.Node;

import java.beans.EventHandler;
import java.util.ArrayList;

/**
 * Created by benhylak on 12/10/16.
 */
public class Environment
{
    ArrayList<GameObj> gamePieces;

    Ball b;
    Wall bottomWall;

    /**
     * Defines different states of the game.
     */
    public static enum GameState {
        WON, LOST, ACTIVE, NEW
    }

    GameState state;

    int width;
    int height;

    public Environment(int width, int height)
    {
        this.width = width;
        this.height = height;

        gamePieces = new ArrayList<GameObj>();

        gamePieces.add(new Paddle());
    }

    public Node[] getDrawings()
    {
        ArrayList<Node> drawings = new ArrayList<Node>();

        for(GameObj g : gamePieces)
        {
            if(g instanceof Drawable)
            {
                drawings.add(((Drawable) g).getDrawing());
            }
        }

        return drawings.toArray(new Node[]);
    }

    public void addStateChangeListener(GameStateChangedEvent.GameStateListener listener)
    {

    }

    public String getMessage()
    {
        if (state == GameState.LOST) {
            return "Game Over\n";
        }
        else if (state == GameState.WON) {
            return "You won!\n";
        }
        else {
            return "";
        }
    }

}
