import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class IMDBMoviesGraph implements Graph
{
	HashMap<String, MovieNode> nodeRecord;

	public IMDBMoviesGraph()
	{
		nodeRecord = new HashMap<String, MovieNode>();
	}

	public IMDBMoviesGraph(String actorsPath, String actressesPath) throws IOException
	{
		GraphParser p = new GraphParser();

		p.parseFile(actorsPath);
		p.parseFile(actressesPath);

		this.nodeRecord	= p.getMovieGraph().nodeRecord;
	}

    @Override
    public Collection<? extends Node> getNodes()
    {
        return nodeRecord.values();
    }

    @Override
	public MovieNode getNodeByName(String name) 
	{
		return nodeRecord.get(name); 
	}

	public MovieNode fetchMovie(String movieName)
	{
		if(!nodeRecord.containsKey(movieName))
		{
			nodeRecord.put(movieName, new MovieNode(movieName));
		}

		return nodeRecord.get(movieName);
	}
}
