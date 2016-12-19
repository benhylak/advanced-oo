import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Type of game object that is drawable and has a picture. Once it is hit, an animal disappears and cannot be hit
 * again
 */

public abstract class Animal extends GameObj implements Drawable
{
    public String imagePath = "";
    protected String soundPath = "";

    Image picture;
    ImageView picNode;

    boolean hasBeenHit = false;

    /**
     * Animal constructor
     *
     * @param xPos x coordinate of the animal's location
     * @param yPos y coordinate of the animal's location
     * @param imagePath path to the animal's image
     */
    public Animal(int xPos, int yPos, String imagePath)
    {
        super();

        this.xPos = xPos;
        this.yPos = yPos;

        picture = new Image(imagePath);
        picNode = new ImageView(picture);

        picNode.resize(50, 50);

        picNode.setLayoutX(xPos);
        picNode.setLayoutY(yPos);
    }

    /**
     *
     * @return Node for drawing this animal's image
     */
    @Override
    public Node getDrawing()
    {
        return picNode;
    }

    /**
     *
     * @return the bounding box for this animal
     */
    @Override
    public Bounds getBoundingBox()
    {
        return picNode.boundsInParentProperty().get();
    }

    /**
     * Record that this animal has been hit.
     */
    public void hit()
    {
        getDrawing().setVisible(false);

        hasBeenHit = true;
    }

    /**
     * Detects whether this animal has collided with another object. If the animal has already been hit, it can not
     * be hit again because it will disappear
     * @param o object to test collision with
     * @return
     */
    @Override
    public boolean isCollision(GameObj o)
    {
        return (!hasBeenHit && super.isCollision(o));
    }
}

