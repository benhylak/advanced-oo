import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by benhylak on 11/20/16.
 */
public class GraphParserTest
{
    static int total_actors = 4;
    static int tv_only_actors = 1;

    static String[] movie_actors_names = {"Bob", "Bill Joel", "John"};
    static String testFilePath = "actorsList.txt";

    @Test
    public void parseFileTest() throws Exception
    {
        GraphParser p = new GraphParser(testFilePath);

        IMDBActorsGraph actors = p.getActorGraph();

        assertEquals(total_actors - tv_only_actors, actors.getNodes().size());

        for(int i=0; i<actors.getNodes().size(); i++)
        {
            ActorNode a = ((ActorNode)actors.getNodes().toArray()[i]);

            assertEquals(movie_actors_names[i], a.getName());
        }
    }

}