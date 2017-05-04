import java.util.HashMap;
public class GraphNode {
	
	private HashMap<Integer,GraphNode> outgoingEdges;
	
	private final int index;
	
	private boolean visited;
	
	public GraphNode(int index){
		
		this.index = index;
		
		this.visited = false;
		
		outgoingEdges = new HashMap<Integer,GraphNode>();
	}
	
	public void addOutgoingEdge(Integer key,GraphNode v){
		
		this.outgoingEdges.put(key,v);
	
	}

	public HashMap<Integer,GraphNode> getOutgoingEdges(){
		
		return outgoingEdges;
	
	}
	
	public int getIndex(){
		
		return index;
	
	}
	
	public boolean isVisited(){
		
		return visited;
	
	}
	
	public GraphNode setVisited(boolean visited){
		
		this.visited = visited;
		
		return this;
	
	}
	
	
	public boolean isExternal(){
		if(outgoingEdges.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	
}
