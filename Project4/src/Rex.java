/**
 * Rex is a type of animal that has the face of a goat.
 *
 */
public class Rex extends Animal
{
    static String imagePath = "Resources/Pictures/rex.png";

    public Rex(int xPos, int yPos)
    {
        super(xPos, yPos, imagePath);
    }
}
