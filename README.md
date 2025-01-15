# Shortest-Path-Framework

__Project Overview__ -

This project implements a graph data structure with support for nodes and weighted, directed edges. It includes basic operations for managing the graph, such as inserting/removing nodes and edges, checking for existence, and retrieving node and edge data. The `BaseGraph` class serves as the foundation, while the `DijkstraGraph` class extends it to implement Dijkstra's algorithm for finding the shortest path between nodes in the graph.

__Key Features__ -
- **Node and Edge Management**: 
  - Supports insertion and removal of nodes and edges.
  - Nodes are uniquely identified by their data.
  - Each edge has a weight and connects two nodes in a directed manner.
  
- **Graph Traversal**:
  - Dijkstra's algorithm is used to compute the shortest path between two nodes in terms of the total edge weight.
  
- **Edge and Node Retrieval**: 
  - Retrieve edges between specific nodes.
  - Check if a node or edge exists in the graph.
  
- **Graph Properties**: 
  - Get the count of nodes and edges in the graph.

__Technologies Used__ -
- **Java**: The primary programming language used for the implementation of the graph and algorithms.
- **JUnit**: For testing various functionalities, including insertion/removal of nodes and edges, and correctness of Dijkstra's algorithm.

__How It Works__ -

1. `BaseGraph` Class
- **Node Representation**: Each node contains data and maintains two lists of edges: one for edges leaving the node and one for edges entering the node.
- **Edge Representation**: Each edge contains data (weight or cost) and references to its predecessor and successor nodes.
- **Core Operations**:
   - Insert/Remove nodes and edges.
   - Check existence of nodes and edges.
   - Retrieve edge data between two nodes.
   - Get the count of nodes and edges in the graph.

2. `DijkstraGraph` Class
- **Shortest Path Calculation**: 
   - Implements Dijkstra's algorithm using a priority queue (`PriorityQueue`) to find the shortest path from a start node to an end node.
   - It maintains a `SearchNode` class that tracks the current node, its cost from the start, and its predecessor node on the shortest path.
- **Path Construction**:
   - The shortest path is built by tracing back from the end node to the start node using the predecessor references of `SearchNode` instances.
- **Edge Weight**: Dijkstra's algorithm accounts for edge weights while computing the shortest path by adding the weight of each edge encountered in the path traversal.

__Example Usage__ -
1. **Create Graph**: Initialize a new `DijkstraGraph` instance with node types and edge weight types.
2. **Insert Nodes and Edges**: Use `insertNode` and `insertEdge` methods to add nodes and directed edges to the graph.
3. **Compute Shortest Path**: Use `shortestPathData` to get the list of nodes along the shortest path between two nodes.
4. **Remove Nodes and Edges**: Use `removeNode` and `removeEdge` to remove nodes and edges from the graph.

__Testing__ -

The project includes JUnit tests to verify correct functionality, including:
- Insertion/removal of nodes and edges.
- Correctness of Dijkstra's shortest path algorithm.
