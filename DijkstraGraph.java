import java.util.PriorityQueue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

//import org.junit.jupiter.api.Test;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
//import static org.junit.Assert.assertTrue;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number> extends BaseGraph<NodeType, EdgeType>
		implements GraphADT<NodeType, EdgeType> {

	/**
	 * While searching for the shortest path between two nodes, a SearchNode
	 * contains data about one specific path between the start node and another node
	 * in the graph. The final node in this path is stored in it's node field. The
	 * total cost of this path is stored in its cost field. And the predecessor
	 * SearchNode within this path is referened by the predecessor field (this field
	 * is null within the SearchNode containing the starting node in it's node
	 * field).
	 *
	 * SearchNodes are Comparable and are sorted by cost so that the lowest cost
	 * SearchNode has the highest priority within a java.util.PriorityQueue.
	 */
	protected class SearchNode implements Comparable<SearchNode> {
		public Node node;
		public double cost;
		public SearchNode predecessor;

		public SearchNode(Node node, double cost, SearchNode predecessor) {
			this.node = node;
			this.cost = cost;
			this.predecessor = predecessor;
		}

		public int compareTo(SearchNode other) {
			if (cost > other.cost)
				return +1;
			if (cost < other.cost)
				return -1;
			return 0;
		}
	}

	/**
	 * This helper method creates a network of SearchNodes while computing the
	 * shortest path between the provided start and end locations. The SearchNode
	 * that is returned by this method is represents the end of the shortest path
	 * that is found: it's cost is the cost of that shortest path, and the nodes
	 * linked together through predecessor references represent all of the nodes
	 * along that shortest path (ordered from end to start).
	 *
	 * @param start the data item in the starting node for the path
	 * @param end   the data item in the destination node for the path
	 * @return SearchNode for the final end node within the shortest path
	 * @throws NoSuchElementException when no path from start to end is found or
	 *                                when either start or end data do not
	 *                                correspond to a graph node
	 */
	protected SearchNode computeShortestPath(NodeType start, NodeType end) throws NoSuchElementException {
		// TODO: implement in step 6
		Hashtable<NodeType, SearchNode> visited = null;
		if (!nodes.containsKey(start) || !nodes.containsKey(end)) {
			throw new NoSuchElementException("Invalid data entry, No such values exsist");
			// throws an error if the data is not found
		}
		visited = new Hashtable<>();// keeps the track of all the visited notes
		// create a minimum priority queue for the nodes transversing from the starter
		PriorityQueue<SearchNode> pq = new PriorityQueue<>();
		SearchNode path = new SearchNode(nodes.get(start), 0.0, null);
		pq.add(path);
		SearchNode sn = null;
		while (!pq.isEmpty()) {
			sn = pq.remove(); // the current search node which has the minimum edge
			if (!visited.containsKey(sn.node.data)) {
				visited.put(sn.node.data, sn);
				for (Edge edge : sn.node.edgesLeaving) {
					if (!visited.containsKey(edge.successor)) {
						SearchNode next = new SearchNode(edge.successor, sn.cost + edge.data.doubleValue(), sn);
						pq.add(next);
					}
				}
			}
		}
		if (visited.containsKey(end) == false)
			throw new NoSuchElementException("The particular end node is not reachable in the graph");
		return visited.get(end);
	}

	/**
	 * Returns the list of data values from nodes along the shortest path from the
	 * node with the provided start value through the node with the provided end
	 * value. This list of data values starts with the start value, ends with the
	 * end value, and contains intermediary values in the order they are encountered
	 * while traversing this shorteset path. This method uses Dijkstra's shortest
	 * path algorithm to find this solution.
	 *
	 * @param start the data item in the starting node for the path
	 * @param end   the data item in the destination node for the path
	 * @return list of data item from node along this shortest path
	 */
	public List<NodeType> shortestPathData(NodeType start, NodeType end) {
		SearchNode count = computeShortestPath(start, end);
		List<NodeType> temp = new ArrayList<>();
		while (count != null) {
			temp.add(count.node.data);
			count = count.predecessor;
		}
		List<NodeType> temp2 = new ArrayList<>();
		for (int i = temp.size() - 1; i >= 0; i--) {
			temp2.add(temp.get(i));
		}
		return temp2;
	}

	/**
	 * Returns the cost of the path (sum over edge weights) of the shortest path
	 * freom the node containing the start data to the node containing the end data.
	 * This method uses Dijkstra's shortest path algorithm to find this solution.
	 *
	 * @param start the data item in the starting node for the path
	 * @param end   the data item in the destination node for the path
	 * @return the cost of the shortest path between these nodes
	 */
	public double shortestPathCost(NodeType start, NodeType end) {
		SearchNode count = computeShortestPath(start, end);
		return count.cost;
	}

	// TODO: implement 3+ tests in step 8.
	/**
	 * This test uses an example from lecture and confirms that the results of the
	 * implementation matches the previously computed by hand value
	 */
	@Test
	public void test1() {
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
		// add nodes and edges to the graph
		graph.insertNode("A");
		graph.insertNode("B");
		graph.insertNode("D");
		graph.insertNode("E");
		graph.insertNode("F");
		graph.insertNode("G");
		graph.insertNode("H");
		graph.insertNode("I");
		graph.insertNode("L");
		graph.insertNode("M");

		graph.insertEdge("A", "H", 8);
		graph.insertEdge("A", "B", 1);
		graph.insertEdge("A", "M", 5);
		graph.insertEdge("H", "B", 6);
		graph.insertEdge("H", "I", 2);
		graph.insertEdge("B", "M", 3);
		graph.insertEdge("M", "E", 3);
		graph.insertEdge("M", "F", 4);
		graph.insertEdge("F", "G", 9);
		graph.insertEdge("G", "L", 7);
		graph.insertEdge("D", "G", 2);
		graph.insertEdge("D", "A", 7);
		graph.insertEdge("I", "H", 2);
		graph.insertEdge("I", "D", 1);
		graph.insertEdge("I", "L", 5);

		// find the shortest path between two nodes
		String start = "D";
		String end = "I";
		List<String> shortestPath = graph.shortestPathData(start, end);
		double shortestPathCost = graph.shortestPathCost(start, end);

		// test if the shortest path and shortest path costs are correct
		Assertions.assertEquals("[D, A, H, I]", shortestPath.toString());
		Assertions.assertEquals(17, shortestPathCost);
	}

	/**
	 * This test uses the same example but different start and end nodes to test the
	 * algorithm
	 */
	@Test
	public void test2() {
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
		// add nodes and edges to the graph
		graph.insertNode("A");
		graph.insertNode("B");
		graph.insertNode("D");
		graph.insertNode("E");
		graph.insertNode("F");
		graph.insertNode("G");
		graph.insertNode("H");
		graph.insertNode("I");
		graph.insertNode("L");
		graph.insertNode("M");

		graph.insertEdge("A", "H", 8);
		graph.insertEdge("A", "B", 1);
		graph.insertEdge("A", "M", 5);
		graph.insertEdge("H", "B", 6);
		graph.insertEdge("H", "I", 2);
		graph.insertEdge("B", "M", 3);
		graph.insertEdge("M", "E", 3);
		graph.insertEdge("M", "F", 4);
		graph.insertEdge("F", "G", 9);
		graph.insertEdge("G", "L", 7);
		graph.insertEdge("D", "G", 2);
		graph.insertEdge("D", "A", 7);
		graph.insertEdge("I", "H", 2);
		graph.insertEdge("I", "D", 1);
		graph.insertEdge("I", "L", 5);

		// find the shortest path between two nodes
		String start = "H";
		String end = "G";
		List<String> shortestPath = graph.shortestPathData(start, end);
		double shortestPathCost = graph.shortestPathCost(start, end);

		// test if the shortest path and shortest path costs are correct
		Assertions.assertEquals("[H, I, D, G]", shortestPath.toString());
		Assertions.assertEquals(5, shortestPathCost);
	}

	/**
	 * This test makes use of the same graph as above, however, tries to find a path
	 * when the nodes that you are searching for a path between exist in the graph,
	 * but there is no sequence of directed edges that connects them from the start
	 * to the end. An exception should be thrown as there is no path connecting the
	 * 2 nodes provided you follow the direction of the directed edges.
	 */
	@Test
	public void test3() {
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
		// add nodes and edges to the graph
		graph.insertNode("A");
		graph.insertNode("B");
		graph.insertNode("D");
		graph.insertNode("E");
		graph.insertNode("F");
		graph.insertNode("G");
		graph.insertNode("H");
		graph.insertNode("I");
		graph.insertNode("L");
		graph.insertNode("M");

		graph.insertEdge("A", "H", 8);
		graph.insertEdge("A", "B", 1);
		graph.insertEdge("A", "M", 5);
		graph.insertEdge("H", "B", 6);
		graph.insertEdge("H", "I", 2);
		graph.insertEdge("B", "M", 3);
		graph.insertEdge("M", "E", 3);
		graph.insertEdge("M", "F", 4);
		graph.insertEdge("F", "G", 9);
		graph.insertEdge("G", "L", 7);
		graph.insertEdge("D", "G", 2);
		graph.insertEdge("D", "A", 7);
		graph.insertEdge("I", "H", 2);
		graph.insertEdge("I", "D", 1);
		graph.insertEdge("I", "L", 5);

		String start = "E";
		String end = "M";

		Assertions.assertThrows(NoSuchElementException.class, () -> graph.shortestPathData(start, end));
		Assertions.assertThrows(NoSuchElementException.class, () -> graph.shortestPathCost(start, end));
	}

}
