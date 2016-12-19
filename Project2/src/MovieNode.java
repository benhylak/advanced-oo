import java.util.ArrayList;
import java.util.Collection;

public class MovieNode implements Node
{
	ArrayList<ActorNode> actors; //actors that are in the movie
    String name;

	public MovieNode(String name)
	{
		this.name = name;
		actors = new ArrayList<ActorNode>();
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public Collection<? extends Node> getNeighbors() {
		return actors;
	}

	public void addActor(ActorNode n)
	{
		actors.add(n);
	}

}
