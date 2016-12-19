import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Trump is a type of animal that has the face of a duck. (And matching color hair)
 */
public class Trump extends Animal
{
    static String imagePath = "Resources/Pictures/trump.png";

    public Trump(int xPos, int yPos)
    {
        super(xPos, yPos, imagePath);
    }
}

