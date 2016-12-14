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

    @Override
    public Node getDrawing()
    {
        return picNode;
    }

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

    @Override
    public boolean isCollision(GameObj o)
    {
        return (!hasBeenHit && super.isCollision(o));
    }
}

