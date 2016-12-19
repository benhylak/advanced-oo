import javafx.scene.Node;

import java.lang.reflect.Array;
import java.util.*;


/**
 * Environment encapsulates the different objects that a game involves and provides a set way to update them all.
 * Environment responds to events from ball in order to update/track score and animal hits
 */
public class Environment
{
    HashSet<GameObj> gamePieces;
    HashSet<Node> envDrawings;

    ArrayList<Animal> animals;

    ArrayList<GameStateChangedEvent.GameStateListener> stateListeners = new ArrayList<>();

    Ball ball;
    Paddle paddle;

    int currentLives;
    final static int MAX_LIVES = 5;

    /**
     * Defines different states of the game.
     */
    public static enum GameState
    {
        WON, LOST, ACTIVE, NEW
    }

    GameState state;

    int width;
    int height;

    final int num_of_animals = 16;

    int animals_left;

    ArrayList walls = new ArrayList(4);
    /**
     * Constructor for Environment
     *
     * @param width width of environment
     * @param height width of environment
     */
    public Environment(int width, int height)
    {
        this.width = width;
        this.height = height;

        make_walls(); //makes the walls, adds them to walls[]
    }

    /**
     * Constructs new walls for the environment.
     */
    private void make_walls()
    {
        Wall left_wall = new VerticalWall.LeftWall(width, height);
        Wall right_Wall = new VerticalWall.RightWall(width, height);

        Wall top_wall = new HorizontalWall.UpperWall(width, height);
        Wall bottom_wall = new HorizontalWall.LowerWall(width, height);

        walls.add(left_wall);
        walls.add(right_Wall);
        walls.add(top_wall);
        walls.add(bottom_wall);
    }

    private void loadDrawings()
    {
        envDrawings.clear();

        for(GameObj g : gamePieces) //get all of the drawings from the environment and put it into gamepieces
        {
            if(g instanceof Drawable)
            {
                envDrawings.add(((Drawable) g).getDrawing());
            }
        }

        envDrawings.add(ball.getDrawing());
    }

    /**
     * Gets the drawings for game objects in our environment
     *
     * @return the nodes for all game objects that are drawable in our environment
     */
    public Collection<Node> getDrawings()
    {
        return envDrawings;
    }

    /**
     * Starts the game (Changes the game state to active)
     */
    public void startGame()
    {
        changeState(GameState.ACTIVE);
    }

    /**
     * Runs one time step of the environment and updates everything that needs updating
     * @param deltaNanoTime
     */
    public void runTimeStep(long deltaNanoTime)
    {
        paddle.updateVelocity(deltaNanoTime);
        ball.updatePosition(deltaNanoTime);

        ball.checkCollisions(gamePieces); //identifies if the ball has collided with anything, fires event if so
    }

    /**
     * Creates a new game and updates the state accordingly
     */
    public void newGame()
    {
        reset_game();

        changeState(GameState.NEW); //change state
    }

    /**
     * Resets the game. (Should be called with new game)
     * Clears all of the old game objects, reconstructs
     */
    public void reset_game()
    {
        animals_left = num_of_animals;
        currentLives = MAX_LIVES;

        envDrawings = new HashSet<>();
        gamePieces = new HashSet<GameObj>();

        make_ball();
        make_animals();

        gamePieces.add(paddle = new Paddle());
        gamePieces.add(ball);
        gamePieces.addAll(walls);
        gamePieces.addAll(animals);

        loadDrawings();
    }

    /**
     * Makes/places our animals. Alternates inbetween our three types of animals.
     */
    public void make_animals()
    {
        animals = new ArrayList<>();

        int counter = 0;

        for(int i = 25; i < height * 1/2; i += 70) //*~~~~~~ magic numbers ~~~~~~~~~*//
        {
            for(int j=25; j< width; j+= width/4) //*~~~~~~~~~ more magic numbers ~~~~~~~*//
            {
                counter++;
                Animal a;

                switch(counter%4)
                {
                    case 1:
                        a = new Trump(j, i);
                        break;
                    case 2:
                        a = new Pruitt(j, i);
                        break;
                    case 3:
                        a = new Carson(j,i);
                        break;
                    default:
                        a = new Rex(j, i);
                        break;
                }

                animals.add(a);
            }
        }
    }
    /**
     * Makes a ball object with appropriate listeners
     */
    public void make_ball()
    {
        ball = new Ball();

        //make ball faster
        ball.addAnimalHitListener(e ->
        {
            ball.MAX_V += .3e-7; //increase max velocity
            ball.xVel += .3e-7; //increase y
            ball.yVel += .3e-7; //increase x
        });

        //remove animal
        ball.addAnimalHitListener(e ->
        {
            Animal a = (Animal)e.getSource();

            a.hit();

            if(--animals_left  <= 0) { //if there aren't any more animals left
                changeState(GameState.LOST);
            }
        });

        ball.addWallCollisionListener(e ->
        {
            if (e.getSource() instanceof HorizontalWall.LowerWall) //if the wall is a lower wall
            {
                currentLives--; // you lost a life
            }

            if (currentLives <= 0)
            {
                changeState(GameState.LOST);
            }
        });
    }

    /**
     * Changes the state of the environment and fires off event to notify listeners
     * @param nextState The state to change to
     */
    public void changeState(GameState nextState)
    {
        if(nextState != state) //not a duplicate state
        {
            state = nextState;
            notifyStateChange(); //notifies listeners
        }
    }

    /**
     * Notifies listeners of state change
     */
    private void notifyStateChange()
    {
       for(int i=0; i<stateListeners.size(); i++)
       {
           stateListeners.get(i).handleStateChange(new GameStateChangedEvent(this, state));
       }
    }

    /**
     * Adds a listener to the state change event
     * @param listener for our state change event
     */
    public void addStateChangeListener(GameStateChangedEvent.GameStateListener listener)
    {
        stateListeners.add(listener);
    }

    /**
     * Gets message describing game
     * @return message describing game state
     */
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

    /**
     * Moves paddle of environment to a specific location
     * @param x
     * @param y
     */
    public void movePaddleTo(double x, double y)
    {
        paddle.moveTo(x, y);
    }

    /**
     * Whether or not the environment is in an active game
     * @return whether or not the environment is in an active game
     */
    public boolean isActive()
    {
        return state == GameState.ACTIVE;
    }

}
