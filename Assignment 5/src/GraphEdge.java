//Firas Aboushamalah
//250 920 750
//this code implements the methods to stabilize the edges of the graph

public class GraphEdge {

	//instance variables for endpoints and bus type
	private GraphNode u, v;
	private char busLine;
	
	public GraphEdge(GraphNode u, GraphNode v, char busLine) {
	 this.u = u;
	 this.v = v;
	 this.busLine = busLine;
	}
	
	public GraphNode firstEndpoint() {
		return u;
	}
	
	public GraphNode secondEndpoint() {
		return v;
	}
	
	public char getBusLine() {
		return busLine;
	}
	
}
