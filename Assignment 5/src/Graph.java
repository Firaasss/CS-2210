//Firas Aboushamalah
//250 920 750
//this code provides all of the methods to implement when constructing the graph

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Graph implements GraphADT {

	private GraphNode [] graphBoard;
	private GraphEdge [][] edge;
	private int n;
	
	/* 
	 * Creates a graph with n nodes and no edges. This is the constructor for the class.
	 * The names of the nodes are 0,1,...,n−1. 
	 */
	public Graph(int n) {
		this.n = n;
		this.graphBoard = new GraphNode[n];  //Create graph with n nodes
		this.edge = new GraphEdge[n][n];
		int vertex = 0;
		while (vertex < n) {
			//creating the vertices on the graphboard from 0-n-1
			graphBoard[vertex] = new GraphNode(vertex);
			vertex++;
		}
		
	}
	
	/* 
	* Adds an edge connecting u and v and belonging to the specified bus line.
	* This method throws a GraphException if either node does not exist or if in the 
	* graph there is already an edge connecting the given nodes. 
	*/
	public void insertEdge(GraphNode u, GraphNode v, char busLine) throws GraphException {
		
		//IF THE NODE IS NOT IN GRAPH
		if (u.getName() < 0 || u.getName() > (n - 1)) {  
			throw new GraphException("The node does not exist.");
		}
		else if (v.getName() < 0 || v.getName() > (n - 1)) {
			throw new GraphException("The node does not exist.");
		}
		
		
		//IF THE EDGE DOES NOT EXIST (IS NULL)
		else if (edge[u.getName()][v.getName()] == null || edge[v.getName()][u.getName()] == null) {
			edge[u.getName()][v.getName()] = new GraphEdge(u, v, busLine);
			edge[v.getName()][u.getName()] = new GraphEdge(u, v, busLine);
		}
		else {  //THE EDGE IS PRESENT (NOT NULL)
			throw new GraphException("The edge already exists in the Graph.");
		}
	}


	/* 
	* Returns the node with the specified name. If no node with this name exists, the 
	* method should throw a GraphException.
	*/
	public GraphNode getNode(int name) throws GraphException {
		if (name < 0 || name > (n-1)) {  //if the node in graph with "name" nodes is not empty/exists, return it
			throw new GraphException("This vertex does not exist.");
		}
		else {
			return graphBoard[name];
		}
	}

	/* Returns a Java Iterator storing all the edges incident on node u.
	 * It returns null if u does not have any edges incident on it.
	 */
	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
		if (u.getName() < 0 || u.getName() > (n - 1)) {   //check if node exists
			throw new GraphException("The node does not exist.");
		}
		else {  //if node exists
			List<GraphEdge> listEdges = new ArrayList<GraphEdge>();  //create new array list to store the edges in
			int i;
			for (i = 0; i < n; i++) {
				if (edge[u.getName()][i] != null) {  //if the edge connected by u and iterator i is not empty
					listEdges.add(edge[u.getName()][i]);  //add it to the list
				}
			}
			if (listEdges.size() == 0) {  //if the array is empty return null, otherwise return the java iterator
				return null;
			}
			else {
				return listEdges.iterator();
			}
		}
		
	}

	/*
	 * Returns the edge connecting nodes u and v. This method throws a GraphException
	 * if there is no edge between u and v.
	 */
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		//check if the nodes u and v exist or not
		if (u.getName() < 0 || u.getName() > (n - 1)) {  
			throw new GraphException("The node does not exist.");
		}  
		
		else if (v.getName() < 0 || v.getName() > (n - 1)) {
			throw new GraphException("The node does not exist.");
		}
		
		//if u and v exist in the graph, check to see if there is an edge connecting the two nodes
		else {
			if (edge[u.getName()][v.getName()] == null) {  //if the edge is existent by vertices u and v
				throw new GraphException("There is no edge between u and v.");
			}
			else {
				return edge[u.getName()][v.getName()];
			}
		}
	}

	/*
	 * Returns true if nodes u and v are adjacent; it returns false otherwise.
	 */
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		//check if the node is in the graph
		if (u.getName() < 0 || u.getName() > (n - 1)) {  
			throw new GraphException("The node does not exist.");
		}  
		else if (v.getName() < 0 || v.getName() > (n - 1)) {
			throw new GraphException("The node does not exist.");
		}
		else {  //if there are nodes connected to an edge they are adjacent
			if(edge[u.getName()][v.getName()] != null) {
				return true;
			}
			else {  //if they are not connected by edge they are not adjacent
				return false;
			}
		}
	}

	
	
	
	
	
}
