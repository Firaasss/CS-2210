import java.io.*;
import java.util.*;

public class BusLines {
    
	private Graph graph;
	private int start, end, W, H, K, n;
	private List<GraphNode> allNodes= new ArrayList<>();
    
	
	public BusLines (String inputFile) throws MapException, GraphException
	{
		//Creates the variables w, l , node, str, holder.
		int node = 0;
		String str;
		char holder;
		try
		{
			//Creates a BufferedReader, that will read from the text file.
			BufferedReader graphInput = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
			StringTokenizer firstLine = new StringTokenizer(graphInput.readLine());
			Integer.parseInt(firstLine.nextToken()); //skip the first element in the line which is unwanted
			W = Integer.parseInt(firstLine.nextToken());
			H = Integer.parseInt(firstLine.nextToken());
			K = Integer.parseInt(firstLine.nextToken());
			n = (W * H);
			
			graph = new Graph(n);
			
			for (int i = 0; i < H; i++) {
				str = graphInput.readLine();
				
				for (int j = 0; j < W; j++) {
					holder = str.charAt(j);
					//START: Checks if holder is equal to 'S', if it is, increment the value of node and set the value of the intense value start to node.
 					if (holder == 'S') {
 						start = node++;
 					}
					//DESTINATION: Checks if holder is equal to 'D', if it is, increment the value of node and set the value of the intense value end to node.
					else if (holder == 'D') {
						end=node++;
					}
					//INTERSECTION OR DEAD END: Checks if holder is equal to '.', if it is, increment the value of node.
					else if (holder == '.') {
						node++;
					}
					//HOUSE: Checks if holder is equal to '(space)', if it is, continue
					else if (holder == ' ') {
						continue;
					}
 					//STREET BELONGING TO BUS LINE: Checks if holder is equal to 'v', if it is, it inserts an edge into graph, of a "vertical wall" type.
					else if (Character.isLetter(holder)) {
						GraphNode node1 = graph.getNode(node - 1);
						GraphNode node2 = graph.getNode(node);
						graph.insertEdge(node1, node2, holder);
					}
				}
			}
			//Closes the inputStream.
			graphInput.close();
			}
		//throws an exception if the file is not found.
		catch (IOException e) {
		throw new GraphException("The file does not exist!");
		}
	}
			
			
	
	/*
	 * Returns the graph representing the map. This method throws a 
	 * MapException if the graph could not be created.
	 */
	public Graph getGraph() throws MapException{
		//checks if the graph is initialized, if not, throws a MapException.
		if (graph == null) throw new MapException ("The graph is not defined!");
		else {
		return graph;
		}
	}
	
	/*
	* Returns a Java Iterator containing the nodes along the path from the starting point to the destination,
	* if such a path exists. If the path does not exist, this method returns the value null. 
	*/
	public Iterator<GraphNode> trip() throws GraphException {
	    GraphNode startNode = new GraphNode(start); //Creates the starting node.
	    allNodes.add(startNode); //Adds the starting node to the list of nodes that will be the solution.
	    startNode.setMark(true); //Marks the starting node as true, meaning it has been visited.
	    pathForEveryNode(graph.incidentEdges(startNode)); //Uses the custom method to find where to go next.
	    return allNodes.iterator(); //Returns the iterator that stores the solution nodes.
	}

	private List<GraphNode> pathForEveryNode (Iterator<GraphEdge> incidentEdges) throws GraphException {
	    //This checks if the current node has incident edges or not, if not, then null is returned.
	    if (incidentEdges == null) {
	        return null;
	    } 
	    else {
	        //If the current node does have incident nodes, check the first edge first.
	        while (incidentEdges.hasNext()) {
	            tryAgain:{ 
	        			GraphEdge nextEdge = incidentEdges.next(); //The is the first edge to be checked.
	                GraphNode endNode = nextEdge.secondEndpoint(); //This is the second end point of the current edge.
	                int type = nextEdge.getBusLine();  //get the next edge's bus type
	                allNodes.add(endNode);  //add the node to the list of solution nodes
	                endNode.setMark(true);  //set the endnode to visited
	                tryAgain2:{GraphEdge Snode = graph.incidentEdges(endNode).next();
	                if (type != Snode.getBusLine()) {
	                		if (K >= 1) {  //if number of bus changes is allowed, go to break
	                			K--;
	                			allNodes.add(Snode.secondEndpoint());
	                			Snode.secondEndpoint().setMark(true);
	                		}
	                		else if (K == 0 && graph.incidentEdges(endNode).hasNext()) {  //if the counter to k is reached and there is still more incident edges go to break
	                			break tryAgain2;
	                		}
	                		else if (K == 0 && !graph.incidentEdges(endNode).hasNext()) {  //if all of the counters are fulfilled then remove the node as we have hit a wall
	                			allNodes.remove(endNode);  
	                			endNode.setMark(false);
	                			break tryAgain;
	                		}
	                }
            		    else { //if the type IS EQUAL to the second node's bus type then set it to visited and add it to the solution nodes
            		    	allNodes.add(Snode.secondEndpoint());  
                		Snode.secondEndpoint().setMark(true);
            		    }

	                if (Snode.secondEndpoint().getName() == end) {  //if we have reached the destination then return the solution node list
	                		return allNodes;
	                }
	                
	                else if (pathForEveryNode(PathsForOne(graph.incidentEdges(Snode.secondEndpoint()))) == null) {
	                		allNodes.remove(Snode.secondEndpoint());
	                		Snode.secondEndpoint().setMark(false);
	                		allNodes.remove(endNode);
	                		endNode.setMark(false);
	                		if (K > 1) {
	                			K++;
	                		}
	                }

	                boolean endLocInArray = false;
	                for (int i = 0; i < allNodes.size(); i++) {
	                		if (allNodes.get(i).getName() == end) {
	                			endLocInArray = true;
	                			break;
	                		}
	                }
	                
	                if (endLocInArray == true) {
	                	return allNodes;
	                }
	                }
	        } 
	    }
	    return null;
	    }
	}
	
	private Iterator<GraphEdge> PathsForOne(Iterator<GraphEdge> PossibilitiesIterator) {
	    List<GraphEdge> possiblePaths = new ArrayList<GraphEdge>(); //Initializes the array list that will store the edges.
	    //This loop goes through all the incident edges connecting to the current node.
	    while (PossibilitiesIterator.hasNext()) {
	        GraphEdge currentEdge = PossibilitiesIterator.next(); //The first incident edge.
	        //This checks if the second end point (next intersection) is marked or not, if not, then add it to the array list.
	        if (currentEdge.secondEndpoint().getMark() == false) {
	        	possiblePaths.add(currentEdge);
	        }
	    }
	    //Return the iterator is the array list is not empty, otherwise return null.
	    if (!possiblePaths.isEmpty()) {
	        return possiblePaths.iterator();
	    } else {
	        return null;
	    }
	}
	
}
