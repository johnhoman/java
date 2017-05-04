import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class DirectedGraph {

	
	private HashMap<Integer,GraphNode> nodes;
	//DONE
	public DirectedGraph(String str){
		
		HashMap<Integer,ArrayList<Integer>> edgePairs;
		Matcher m;
		Pattern p;
		Entry<Integer,GraphNode> entry;
		
		
		nodes = new HashMap<Integer,GraphNode>();
		
		String[] edges = str.split("\\]\\[");
		
		edgePairs = new HashMap<Integer,ArrayList<Integer>>(edges.length);

		p = Pattern.compile("\\d+");
		
		int u = -1,v = -1;
		for (String s:edges){
			
			m = p.matcher(s);
						
			if (m.find()){
				u = Integer.parseInt(m.group());
			}
			if (m.find()){
				v = Integer.parseInt(m.group());
			}
			if (u != -1 && v != -1){
				// How can I find out if the node already exists				
				if (!nodes.containsKey(u)){
					nodes.put(u,new GraphNode(u));
				}
				if (!nodes.containsKey(v))
					nodes.put(v,new GraphNode(v));
				
				
				if (edgePairs.containsKey(u)){
					
					edgePairs.get(u).add(v);
					
				}
				else{
					
					edgePairs.put(u, new ArrayList<Integer>());
					edgePairs.get(u).add(v);
					
				}
				
			}
		}
		
		ArrayList<Integer> alist;
				
		for (Iterator<Entry<Integer,GraphNode>> it = nodes.entrySet().iterator(); 
				it.hasNext();){
			entry = it.next();
			
			if (edgePairs.containsKey(entry.getKey()))
				
				alist = edgePairs.get(entry.getKey());
			
			else
				
				continue;
			
			for (Integer i:alist){
				entry.getValue().addOutgoingEdge(i,nodes.get(i));				
			}
								
		}
		
		
		
			
	}
	//DONE
	public ArrayList<GraphNode> getIncomingEdges(GraphNode v){
		
		Iterator<Entry<Integer, GraphNode>> iter;
		
		Iterator<Entry<Integer, GraphNode>> iterout;
		
		Entry<Integer,GraphNode> entry;
		
		Entry<Integer,GraphNode> entryOutgoing;
		
		HashMap<Integer,GraphNode> map = new HashMap<Integer,GraphNode>();
		
		ArrayList<GraphNode> list = new ArrayList<GraphNode>();
		
		iter = nodes.entrySet().iterator();
		
		while(iter.hasNext()){
			
			entry = iter.next();
			
			iterout = entry.getValue().getOutgoingEdges().entrySet().iterator();
			
			while(iterout.hasNext()){
				
				entryOutgoing = iterout.next();
				
				if (entryOutgoing.getValue() == v){
					
					map.put(entry.getKey(), entry.getValue());
					list.add(entry.getValue());
				}
			}
		}
		return list;
	}
	//DONE
	public GraphNode get(int key){
		
		return nodes.get(key);
		
	}
	//DONE
	public boolean isDirectedAcyclicGraph(){
		
		Iterator<Entry<Integer,GraphNode>> iter = nodes.entrySet().iterator();
		
		Entry<Integer,GraphNode> entry;
		
		LinkedList<GraphNode> path;
		
		while (iter.hasNext()){
			
			entry = iter.next();
			
			path = findCycle(entry.getValue());
			
			if (path.size() != 0){
				
				return false;
				
			}
			
		}
		
		return true;
		
	}
	//DONE
	public ArrayList<GraphNode> getRoots(){
		
		HashMap<Integer,GraphNode> roots = this.deepCopy();
				
		ArrayList<GraphNode> list = new ArrayList<GraphNode>();
		
		for (Iterator<GraphNode>iter = nodes.values().iterator();iter.hasNext();){			
							
			for (Iterator<GraphNode> it = iter.next().getOutgoingEdges().values().iterator();
					it.hasNext();){
			
				roots.remove(it.next().getIndex());	
			}
		}
		
		for (Iterator<GraphNode> iter = roots.values().iterator(); iter.hasNext();){
			
			list.add(iter.next());
			
		}
		
		return list;
	}
	//DONE
	public int edgeCount(){
		
		Iterator<Map.Entry<Integer, GraphNode>> iter;
		
		int count = 0;
		
		iter = nodes.entrySet().iterator();
		
		while(iter.hasNext()){
			
			count += iter.next().getValue().getOutgoingEdges().size();
		}
		
		return count;
		
	}
	//DONE
	public void printAsAdjacencyMatrix(){
		
		Iterator<Entry<Integer, GraphNode>> iter;
		
		Iterator<Entry<Integer, GraphNode>> iterout;
		
		Entry<Integer,GraphNode> entry;
		
		Entry<Integer,GraphNode> entryout;
		
		int max = (int)Collections.max(nodes.keySet()) + 1;
		
		int [][] matrix = new int[max][max];
		
		iter = nodes.entrySet().iterator();
				
		while(iter.hasNext()){
			
			entry = iter.next();
			
			iterout = entry.getValue().getOutgoingEdges().entrySet().iterator();
			
			while(iterout.hasNext()){
				
				entryout = iterout.next();
				
				matrix[entry.getValue().getIndex()][entryout.getKey()] = 1;
				
			}
			
		}
		int column = 0,row = 0;
		System.out.format("     ");
		while(++column < max){
			System.out.format("%2d ",column);
		}
		System.out.println();
		System.out.print("     ");
		for (int k = 1;k < max;k++){
			System.out.format("%s"," ^ ");
		}
		System.out.println();
		for (int k = 1;k < max;k++){
			System.out.format("%2d > ",++row);
			for (int kk = 1;kk < max; kk++){
				System.out.format("%2d ",matrix[k][kk]);
			}
			System.out.println();
		}
		
		
	}
	//NOT IMPLEMENTED
	public LinkedList<GraphNode> findCycle(GraphNode v){
		
		GraphNode graphNode = v;
		
		GraphNode w;
		
		Stack<GraphNode> stack = new Stack<GraphNode>();
		
		LinkedList<GraphNode> list = new LinkedList<GraphNode>();
		
		stack.push(graphNode.setVisited(true));
		
		while(!stack.isEmpty()){
			
			graphNode = stack.pop();
			
			for (Iterator<GraphNode> iter = graphNode.getOutgoingEdges().values().iterator(); iter.hasNext();){
				
				w = iter.next();
				
				if (w == v){
					
					stack.push(graphNode);
					stack.push(w);
					
					while(!stack.isEmpty()){
						
						list.addFirst(stack.pop());
					}
					break;
					
				}
				
				if (!w.isVisited() && !w.isExternal()){
						
					stack.push(graphNode);
					
					stack.push(w.setVisited(true));
					
					break;

				}				
			}		
		}
		
		for (Iterator<GraphNode> iter = nodes.values().iterator();iter.hasNext();){
			
			iter.next().setVisited(false);
			
		}
		
		return list;
	}
	
	public HashMap<Integer,GraphNode> deepCopy(){
		
		Iterator<Entry<Integer, GraphNode>> it;
		Iterator<Entry<Integer, GraphNode>> itout;
		
		Entry<Integer,GraphNode> entry;
		Entry<Integer,GraphNode> entry2;
		
		HashMap<Integer,GraphNode> out;
		HashMap<Integer,GraphNode> map;
		
		GraphNode graphNode;
		
		map = new HashMap<Integer,GraphNode>(nodes.size());
		
		it = nodes.entrySet().iterator();
		
		while(it.hasNext()){
			
			entry = it.next();
			map.put(entry.getKey(),new GraphNode(entry.getKey()));
			
		}

		it = map.entrySet().iterator();
		
		while(it.hasNext()){
			
			entry = it.next();
			
			graphNode = entry.getValue();
			
			out = nodes.get(entry.getKey()).getOutgoingEdges();
			
			itout = out.entrySet().iterator();
			
			while(itout.hasNext()){
				
				entry2 = itout.next();
				
				entry.getValue().addOutgoingEdge(entry2.getKey(), map.get(entry2.getKey()));
				
			}
		}
		return map;
	}

	public void printPathToSelf(LinkedList<GraphNode> l){
		GraphNode graphNode;
		for (Iterator<GraphNode> iter = l.iterator();iter.hasNext();){
			
			graphNode = iter.next();
			
			if (iter.hasNext())
				
				System.out.format("v%d->",graphNode.getIndex());
			
			else
				
				System.out.println(graphNode.getIndex());
			
		}
		
	}
	
	public void printRoots(ArrayList<GraphNode> l){
		
		System.out.println("Graph Roots");
		System.out.println("-----------");
		for (Iterator<GraphNode> iter = l.iterator();iter.hasNext();){
			
			System.out.format("v%d\n",iter.next().getIndex());
			
		}
		
		System.out.println();
		
	}
	
	public HashMap<Integer,GraphNode> getNodes(){
		return nodes;
	}
	
	
	public static void main(String[]args){
		
		String graphA =   "[v15,v1]"
						+ "[v15,v2]"
						+ "[v6,v2]"
						+ "[v6,v3]"
						+ "[v4,v1]"
						+ "[v5,v1]"
						+ "[v5,v2]"  
						+ "[v10,v6]"
						+ "[v7,v3]"
						+ "[v8,v4]"
						+ "[v9,v4]"
						+ "[v9,v5]"
						+ "[v10,v5]" 
						+ "[v10,v7]"
						+ "[v11,v7]"
						+ "[v12,v8]"
						+ "[v12,v9]"
						+ "[v12,v10]"
						+ "[v13,v10]"   
						+ "[v14,v12]"
						+ "[v14,v13]"
						+ "[v13,v11]";
		
		String graphB = "[v1,v4][v5,v1][v2,v5][v6,v2][v3,v6][v10,v6][v7,v3]"    + 
						"[v10,v5][v10,v7][v4,v8][v4,v9][v9,v5][v9,v10]" + 
						"[v8,v9][v9,v11][v10,v11][v11,v7]";
		/**
		 * Test case A
		 * */
		
		System.out.println("\n\nTest case A:");
		System.out.println("-------------------------------------------\n\n");
		DirectedGraph directedGraph = new DirectedGraph(graphA);
		
		System.out.format("isDAG: %b\n\n",directedGraph.isDirectedAcyclicGraph());
				
		directedGraph.printRoots(directedGraph.getRoots());
		
		System.out.format("Edge Count: %d\n\n", directedGraph.edgeCount());
		
		System.out.println("Adjacency Matrix");
		System.out.println("----------------");
		
		directedGraph.printAsAdjacencyMatrix();
		
		for (Iterator<GraphNode> iter = directedGraph.getNodes().values().iterator();
				iter.hasNext();){
			
			GraphNode v = iter.next();
						
			System.out.format("Cycle v%d: ",v.getIndex());
			directedGraph.printPathToSelf(directedGraph.findCycle(v));
			System.out.println();

			
		}
		//System.out.println(directedGraph.getIncomingEdges(graphNode));
		//directedGraph.printAsAdjacencyMatrix();

		//directedGraph.printPathToSelf(directedGraph.findCycle(directedGraph.get(3)));
		
		System.out.println("\n\nTest case B:");
		System.out.println("--------------------------------------------\n\n");
	
	
		directedGraph = new DirectedGraph(graphB);
		
		System.out.format("isDAG: %b\n\n",directedGraph.isDirectedAcyclicGraph());
				
		directedGraph.printRoots(directedGraph.getRoots());
		
		System.out.format("Edge Count: %d\n\n", directedGraph.edgeCount());
		
		System.out.println("Adjacency Matrix");
		System.out.println("----------------");
		
		directedGraph.printAsAdjacencyMatrix();
		System.out.println();
		for (Iterator<GraphNode> iter = directedGraph.getNodes().values().iterator();
				iter.hasNext();){
			
			GraphNode v = iter.next();
						
			System.out.format("Cycle v%d: ",v.getIndex());
			directedGraph.printPathToSelf(directedGraph.findCycle(v));
			System.out.println();
	
			
		}
	}
	
}


