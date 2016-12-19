/**
 * Pruitt is a type of animal that has the face of a horse. (And doesn't believe in climate change)
 */
public class Pruitt extends Animal
{
    static String imagePath = "Resources/Pictures/Pruitt.png"; //path to image

    /**
     * Constructor for Pruitt animal
     *
     * @param xPos x position
     * @param yPos y position
     */
    public Pruitt(int xPos, int yPos)
    {
        super(xPos, yPos, imagePath);
    }
}
