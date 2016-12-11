import javafx.scene.Node;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by benhylak on 12/10/16.
 */
public class Environment
{
    ArrayList<GameObj> gamePieces;

    ArrayList<GameStateChangedEvent.GameStateListener> stateListeners = new ArrayList<>();

    Ball ball;
    Wall bottomWall;
    Paddle paddle;
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

        gamePieces.add(paddle = new Paddle());
        gamePieces.add(ball = new Ball());

        ball.addAnimalHitListener(e -> {
           System.out.println("Hit");
        });

        ball.addWallCollisionListener(e->{
            System.out.println("collision");
        });
    }

    public void startGame()
    {
        if(state!=GameState.ACTIVE)
        {
            state = GameState.ACTIVE;

            notifyStateChange();
        }
    }

    public void runTimeStep(long deltaNanoTime)
    {
        ball.updatePosition(deltaNanoTime);

        ball.checkCollisions(gamePieces);
    }

    public void newGame()
    {
        if(state!=GameState.NEW)
        {
            state = GameState.NEW;
            notifyStateChange();
        }
    }

    private void notifyStateChange()
    {
       for(int i=0; i<stateListeners.size(); i++)
       {
           stateListeners.get(i).handleStateChange(new GameStateChangedEvent(this, state));
       }

//        for(GameStateChangedEvent.GameStateListener e : stateListeners)
//        {
//
//        }
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

        return drawings.toArray(new Node[drawings.size()]);
    }

    public void addStateChangeListener(GameStateChangedEvent.GameStateListener listener)
    {
        stateListeners.add(listener);
    }

    public String getMessage()
    {
        if (state == GameState.LOST)
        {
            return "Game Over\n";
        }
        else if (state == GameState.WON)
        {
            return "You won!\n";
        }
        else
        {
            return "";
        }
    }

    public void movePaddleTo(double x, double y)
    {
        paddle.moveTo(x, y);
    }

    public boolean isActive()
    {
        return state == GameState.ACTIVE;
    }

}
