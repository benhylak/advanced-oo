import java.util.List;

/**
 * Graph search engine that finds shortest paths between pairs of nodes
 */
interface GraphSearchEngine {
	/**
	 * Finds a shortest path between node s and node t
	 * @param s the source node
	 * @param t the target node
	 * @return a list of nodes (including s and t) along any shortest path from s to t
	 */
	public List<Node> findShortestPath (Node s, Node t);
}
