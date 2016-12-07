import java.io.*;
import java.util.regex.*;

/**
 * Class for converting a properly formatted text document with
 * actors and movies into two graphs of actors in movies where 
 * 1. Each actor node has neighbors of movie nodes
 * 2. Each movie node has neighbors of actor nodes
 * 
 * Requires
 * 1. Year of movie published to be 4 digits long (Hopefully not a problem)
 * 2. TV shows to signified with either
 * 		-Quotes around title 
 * 		-"(TV) after the year of the movie 
 * 
 * @author ben hylak
 *
 */
public class GraphParser
{
	IMDBActorsGraph actors;
	IMDBMoviesGraph movies;
	
	static String movieString  = "[\\s]+(?<MovieInfo>[^\\s\"].+?[^\"]\\s+\\(\\d{4}\\))\\s+(?!\\(TV\\))";
	static String nameLineString= "^(?<ActorName>\\S[^\\t]+)\\s+.+?\\(\\d{4}\\).+";
	
	Pattern nameLinePattern; 
	Pattern moviePattern;
	BufferedReader bufferedReader;

	public GraphParser()
	{
		nameLinePattern = Pattern.compile(nameLineString);
		moviePattern = Pattern.compile(movieString);

		actors = new IMDBActorsGraph();
		movies = new IMDBMoviesGraph();
	}

	/**
	 *
	 * @param filePath Path to text file for parsing
	 * @throws IOException
	 */
	public GraphParser(String filePath) throws IOException
	{
		this();
		parseFile(filePath);
	}

	/**
	 * Parses a text file and creates both an IMDBActorsGraph and a IMDBMoviesGraph, either of which can be accessed through
	 * respective functions. This function can be called multiple times on different files and the IMDBActorsGraph/IMDBMoviesGraph
	 * will be added to.
	 *
	 * @param path Path to text file for parsing
	 * @throws IOException
	 */
	public void parseFile(String path) throws IOException
	{
		bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1")); //read in file

		ActorNode curActor = null;
		Matcher lineMatcher;

		String curLine;

		while((curLine = bufferedReader.readLine()) != null) //next line is available
		{
			lineMatcher = nameLinePattern.matcher(curLine);

			if(!curLine.isEmpty() && lineMatcher.matches()) //if line has an actor
			{
				if(!isNullOrNoMovies(curActor))
				{
					actors.addNode(curActor);
				}

				curActor = new ActorNode(lineMatcher.group("ActorName"));
			}

			lineMatcher = moviePattern.matcher(curLine);

			if(curActor != null && !curLine.isEmpty() && lineMatcher.lookingAt()) //if line has a movie...
			{
				MovieNode m = movies.fetchMovie(lineMatcher.group("MovieInfo"));
				addRelation(curActor, m); //adds actor to movie and movie to actor)
			}
		}

		if(!isNullOrNoMovies(curActor))
		{
			actors.addNode(curActor);
		}
	}

	public boolean isNullOrNoMovies(ActorNode n)
	{
		return (n==null || !n.hasMovies());
	}
	/**
	 * Adds a relation inbetween ActorNode and MovieNode. (Movie is one of the movies the actor has been in and actor
	 * is one of the actors in the movie)
	 * @param a actor node
	 * @param m movie node
	 */
	private void addRelation(ActorNode a, MovieNode m)
	{
		m.addActor(a);
		a.addMovie(m);
	}

	/**
	 * Returns the graph of actors filled while parsing the text file. If no file has been parsed, graph will be empty
	 *
	 * @return Graph of Actors
	 */
	public IMDBActorsGraph getActorGraph()
	{
		return actors;
	}

	/**
	 * Returns the graph of movies filled while parsing the text file. If no file has been parsed, graph will be empty
	 *
	 * @returnGraph of Movies
	 */
	public IMDBMoviesGraph getMovieGraph()
	{
		return movies;
	}
}


