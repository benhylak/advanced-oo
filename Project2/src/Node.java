import java.util.*;
/**
 * Interface for a node that is part of a social network graph,
 * whereby each node has a unique name and also has neighbors.
 */
interface Node {
	/**
	 * @return the name of the node
	 */
	public String getName ();

	/**
	 * @return a collection of this node's neighbors. Each
	 * node in the collection can be Node or any subclass of Node.
	 */
	public Collection<? extends Node> getNeighbors ();
}
