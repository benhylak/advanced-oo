/**
 * Pruitt is a type of animal that has the face of a horse. (And doesn't believe in climate change)
 */
public class Pruitt extends Animal
{
    static String imagePath = "Resources/Pictures/horse.jpg";

    public Pruitt(int xPos, int yPos)
    {
        super(xPos, yPos, imagePath);
    }
}
