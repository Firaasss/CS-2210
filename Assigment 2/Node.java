//Author Firas Aboushamalah
//250-920-750
//This node class is assembled to allow multiple objects of Configuration 
//placed in the same bucket to prevent collisions.

public class Node<Configuration> {
	private Node<Configuration> next;
	private Node<Configuration> prev;
	private Configuration data;
	
	public Node(Configuration data) {
		this.next = null;
		this.prev = null;
		this.data = data;
	}
	public Configuration getData() {
		return data;
	}
	
	public void setNext(Node<Configuration> nextNode) {
		next = nextNode;
	}
	
	public Node<Configuration> getNext() {
		return next;
	}
	
	public void setPrev(Node<Configuration> prevNode) {
		prev = prevNode;
	}
	
	public Node<Configuration> getPrev() {
		return prev;
	}
	
	
	
}
