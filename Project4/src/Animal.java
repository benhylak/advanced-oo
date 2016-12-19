import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by benhylak on 12/11/16.
 */

public abstract class Animal extends GameObj implements Drawable
{
    public String imagePath = "";
    protected String soundPath = "";

    Image picture;
    ImageView picNode;

    int xPos;
    int yPos;

    boolean hasBeenHit = false;

    public Animal()
    {
        xVel = 0;
        yVel = 0;
    }

    public Animal(int xPos, int yPos, String imagePath)
    {
        this.xPos = xPos;
        this.yPos = yPos;

        picture = new Image(imagePath);
        picNode = new ImageView(picture);

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

