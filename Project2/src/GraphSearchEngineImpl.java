import java.util.*;

/**
 * Created by benhylak on 11/20/16.
 */
public class GraphSearchEngineImpl implements GraphSearchEngine
{
    HashMap<Node, Integer> nodeDistMap;

    public GraphSearchEngineImpl()
    {
        nodeDistMap = new HashMap<Node, Integer>();
    }

    /**
     * Finds the shortest path in between two nodes.
     *
     * @param s the source node
     * @param t the target node
     * @return List of nodes, in order, of the shortest path from s to t
     */
    @Override
    public List<Node> findShortestPath(Node s, Node t)
    {
        LinkedList<Node> path = new LinkedList<Node>(); //will hold shortest path

        path.push(t); //Add end to stack - F-I-L-O

        int tDist = recordNodeDistances(s, t); //store node distances in nodeDistMap, return distance from s,t
        int distLeft = tDist; //distance left = start distance

        if(tDist < 0) return null; //error, no path found

        while(distLeft > 0)
        {
            distLeft--;

            Node n = getNeighborWithDist(path.peekFirst(), distLeft); //find neighbor of last added node
                                                                     // with matching distance
            if(n!=null)
            {
               path.addFirst(n); //add node to front of path
            }
            else return null; //error in path (this probably could never happen)
        }

        return path;
    }

    /**
     * Gets the neighbor to inputNode that is dist away from the start node
     *
     * @param inputNode input node
     * @param dist neighbor's distance to start
     *
     * @return neighbor to inputNode that is dist away from the start node
     */
    private Node getNeighborWithDist(Node inputNode, int dist)
    {
        for(Node n : inputNode.getNeighbors())
        {
            if(nodeDistMap.get(n) == dist)
            {
                return n;
            }
        }

        return null; //no match found
    }

    /**
     * This function takes a starting node, "start", and records how far each node is from the start
     * @param start The node to record distances from
     * @param end The node we are trying to traverse to
     *
     * @return The distance from the end to start
     */
    public int recordNodeDistances(Node start, Node end)
    {
        HashSet<Node> visited = new HashSet<Node>();

        LinkedList<Node> curQueue = new LinkedList<Node>(); //holds nodes in current radius of BFS
        LinkedList<Node> nextQueue = new LinkedList<Node>(); //holds nodes in next radius of bfs

        curQueue.push(start);

        int end_dist = -1;
        int dist = 0;

        while(!curQueue.isEmpty())
        {
            Node next = curQueue.removeFirst();

            if(!visited.contains(next))
            {
                nodeDistMap.put(next, dist);

                Collection<? extends Node> neighbors = next.getNeighbors();

                for(Node n : neighbors)
                {
                   nextQueue.push(n);
                }

                if(next==end) end_dist = dist;

                visited.add(next);
            }

            if(curQueue.isEmpty()) //nodes in current radius have been explored
            {
                curQueue = nextQueue; //give it nodes in next radius
                nextQueue = new LinkedList<Node>(); //make a new next queue

                dist++; //increase the radius we are on
            }
        }

        return end_dist;
    }
}
