import java.util.Collection;
import java.util.ArrayList;

public class ActorNode implements Node
{
	ArrayList<MovieNode> movies; //movies the actor has been in
	String name;

	public ActorNode(String name)
	{
		this.name = name;
		movies = new ArrayList<MovieNode>();
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public Collection<? extends Node> getNeighbors()
	{
		return movies;
	}

	public void addMovie(MovieNode n)
	{
		movies.add(n);
	}

	public int numberOfMovies()
    {
        return movies.size();
    }

    public boolean hasMovies()
	{
		return numberOfMovies() > 0;
	}
}
