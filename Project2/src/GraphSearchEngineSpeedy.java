import java.util.*;

/*
Speedy (?) version of the Graph Search Engine. Uses Dynamic programming/Bellman optimization where the path cost is simply
distance... Could be expanded though to account for real path costs.
 */

public class GraphSearchEngineSpeedy implements GraphSearchEngine
{
    //Maps nodes to distances of target

    HashMap<Node, Integer> nodeDists;

    public GraphSearchEngineSpeedy()
    {
        nodeDists = new HashMap<Node, Integer>();
    }

    /**
     * Finds the shortest path from source to target... It first gets the minimum distance to target for each node
     * After, starting at node s, it adds the neighbor with minimum distance to the target, t, to the shortest path
     * until t is reached
     *
     * @param s the source node
     * @param t the target node
     * @return List, in order, of path from source to target
     */
    @Override
    public List<Node> findShortestPath(Node s, Node t)
    {
        nodeDists.clear(); //clear previously calculated distances

        recordNodeDistances(s, t); //record node distances to target

        LinkedList<Node> shortestPath = new LinkedList<Node>();

        shortestPath.addLast(s);

        boolean reachedEnd = false;

        if(s==t) reachedEnd = true; // "That was easy"

        while(!reachedEnd)
        {
            //get the neighbors from the most recent step on the path
            Collection<? extends Node> neighbors = shortestPath.getLast().getNeighbors();

            //minimum cost from neighbors
            Node minNode = null;

            for(Node n: neighbors)
            {
                if(minNode == null || nodeDists.get(n) < nodeDists.get(minNode))
                {
                    minNode = n;

                    if(nodeDists.get(n) == 0)
                    {
                        reachedEnd = true;
                        break;
                    }
                }
            }

            if(nodeDists.get(minNode) == Integer.MAX_VALUE) return null; //no path
            else shortestPath.addLast(minNode);
        }

        return shortestPath;
    }

    /**
     * This function takes a starting node, "start", and records how far each node is from the start
     *
     * @param start The node to record distances from
     * @param end The node we are trying to traverse to
     *
     */

    public void recordNodeDistances(Node start, Node end)
    {
        HashSet<Node> inStack = new HashSet<Node>();
        LinkedList<Node> nodeStack = new LinkedList<Node>();

        nodeStack.push(start);

        Node curNode;

        while(!nodeStack.isEmpty())
        {
           //shortest distance from end to start = shortest distance of neighbor to start
            curNode = nodeStack.peekFirst();
            Collection<? extends Node> neighbors = curNode.getNeighbors();

            if(curNode == end) //if we've reached the end
            {
                nodeDists.put(curNode, 0); //the distance to the end is 0 (obviously)

                inStack.remove(curNode); //remove from our store of nodes that are in the stack
                nodeStack.removeFirst();
            }
            else
            {
                boolean readyForEval = true; //is node ready to have the min distance calculated?

                for (Node n : neighbors)
                {
                    if (!nodeDists.containsKey(n) && !inStack.contains(n))
                    {
                        nodeStack.addFirst(n);   //in order to calculate the min distance to target any neighbors not in
                        inStack.add(n);          //the stack must have already been calculated. Neighbors still in the stack
                                                 //are to be considered closer to the source and, thus, "behind" curNode
                        readyForEval = false;
                    }
                }

                if (readyForEval)
                {
                    int minDist = getMinDist(curNode); //get the minimum distance from this node to the target

                    nodeDists.put(curNode, minDist); //if unreachable, distance is maxValue
                    nodeStack.removeFirst();
                    inStack.remove(curNode);
                }
            }
        }
    }

    /**
     * Get the minimum distance from the node to target.. (Minimum distance among neighbors + 1)
     *
     * @param n Node to find minimum distance to target
     * @return min distance from node to target
     */
    public int getMinDist(Node n)
    {
        int minNeighborDist = Integer.MAX_VALUE - 1;

        Collection<? extends Node> neighbors = n.getNeighbors();

        for (Node x : neighbors)
        {
            if (nodeDists.containsKey(x) && nodeDists.get(x) < minNeighborDist)
            {
                minNeighborDist = nodeDists.get(x);
            }
        }

        return minNeighborDist+1;
    }
}
