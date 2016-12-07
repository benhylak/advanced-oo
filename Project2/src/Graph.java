import java.util.*;

/**
 * An interface for a graph that contains inter-connected nodes, each of
 * which has a unique name.
 */
interface Graph {
	/**
	 * Gets all the nodes in the graph.
	 * @return a collection of all the nodes in the graph.
	 */
	public Collection<? extends Node> getNodes ();

	/**
	 * Returns the Node having the specified name.
	 * @param name the name of the requested Node
	 * @return the node associated with the specified name or null
	 * if no such node exists.
	 */
	public Node getNodeByName (String name);
}
