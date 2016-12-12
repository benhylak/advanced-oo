import javafx.scene.Node;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

    Wall right_wall;

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

        Wall left_wall = new VerticalWall.LeftWall(width, height);
        Wall right_Wall = new VerticalWall.RightWall(width, height);

        Wall top_wall = new HorizontalWall.UpperWall(width, height);
        Wall bottom_wall = new HorizontalWall.LowerWall(width, height);

        gamePieces.addAll(Arrays.asList(left_wall, right_Wall, top_wall, bottom_wall)); //add environment walls

        this.right_wall = right_Wall;
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
        paddle.updateVelocity(deltaNanoTime);
        ball.updatePosition(deltaNanoTime);

        ball.checkCollisions(gamePieces);

//        if(ball.getBoundingBox().getMaxX() > width)
//        {
//            System.out.println(String.format("Ball coordinates: X: %f Y: %f", ball.getBoundingBox().getMaxX(), ball.getBoundingBox().getMaxY()));
//            System.out.println(String.format("Wall Min: X: %f Y: %f", right_wall.getBoundingBox().getMinX(), right_wall.getBoundingBox().getMinY()));
//
//
//
//        }
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
