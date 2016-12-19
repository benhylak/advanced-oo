import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by benhylak on 12/12/16.
 */
public class Trump extends Animal
{
    static String imagePath = "Resources/Pictures/duck.jpg";

    public Trump(int xPos, int yPos)
    {
        super(xPos, yPos, imagePath);
    }
}

