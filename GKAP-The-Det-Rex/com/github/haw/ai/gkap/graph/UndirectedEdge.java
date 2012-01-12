package com.github.haw.ai.gkap.graph;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @author Till Theis <till.theis@haw-hamburg.de>
 * @author Patrick Detlefsen <patrick.detlefsen@haw-hamburg.de>
 * @author Benjamin Rexin <benjamin.rexin@haw-hamburg.de>
 */
public class UndirectedEdge<E, V> extends AbstractEdge<E, V> {
    /**
     * create a directed edge which goes from 'left' to 'right'
     */
    public static <E, V> Edge<E, V> valueOf(Vertex<V> left, Vertex<V> right, E content) {
        if (left == null || right == null || content == null) {
            throw new NullPointerException();
        }
        
        return new UndirectedEdge<E, V>(left, right, content);
    }
    
	private UndirectedEdge(Vertex<V> left, Vertex<V> right, E content) {
		super(left, right, content, 0, 0);
	}

	public static <E, V> Edge<E, V> valueOf(Vertex<V> left, Vertex<V> right, E content, int capacity, int flow) {
        if (left == null || right == null || content == null) {
            throw new NullPointerException();
        }
        
        return new UndirectedEdge<E, V>(left, right, content, capacity, flow);
    }
    
	private UndirectedEdge(Vertex<V> left, Vertex<V> right, E content, int capacity, int flow) {
		super(left, right, content, capacity, flow);
	}

	public boolean isReachable(Vertex<V> from, Vertex<V> to) {
		return (left().equals(from) && right().equals(to)) || (right().equals(from) && left().equals(to));
	}
	
	@Override
	public String toString() {
	    return content() + "(" + left() + "-" + right() + ")";
	}



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content() == null) ? 0 : content().hashCode());
        result = prime * result + ((vertices() == null) ? 0 : vertices().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractEdge other = (AbstractEdge) obj;
        if (content() == null) {
            if (other.content() != null)
                return false;
        } else if (!content().equals(other.content()))
            return false;
        if (vertices() == null) {
            if (other.vertices() != null)
                return false;
        } else if (!vertices().equals(other.vertices()))
            return false;
        return true;
    }
    
    public boolean isBridge(Graph<E, V> g) {
    	boolean result = true;

    	// defensive copy
    	Set<Edge<E, V>> edgesWithoutSelf = g.edges();
    	edgesWithoutSelf.remove(this);

    	Graph<E, V> graph = Graphs.graph(edgesWithoutSelf, g.vertices());

    	Set<Vertex<V>> unvisitedVertices = graph.vertices();
    	Set<Vertex<V>> visitedVertices = new HashSet<Vertex<V>>();
    	Stack<Vertex<V>> stack = new Stack<Vertex<V>>();

    	if (unvisitedVertices.iterator().hasNext()) {
        	stack.push(unvisitedVertices.iterator().next());
    	}

		while(!stack.isEmpty()) {
    		Vertex<V> currentVertex = stack.peek();
    		Vertex<V> unvisitedChildVertex = getUnvisitedChildVertex(graph, visitedVertices, currentVertex);

    		if(unvisitedChildVertex != null) {
    			visitedVertices.add(unvisitedChildVertex);
    			unvisitedVertices.remove(unvisitedChildVertex);
    			stack.push(unvisitedChildVertex);
    		} else {
    			stack.pop();
    		}
    	}
		
    	if (visitedVertices.equals(graph.vertices())) {
    		result = false;
    	}

    	return result;
    }

    // needed for isBridge()
    private Vertex<V> getUnvisitedChildVertex(Graph<E, V> graph, Set<Vertex<V>> visitedVertices, Vertex<V> currentVertex) {
    	Set<Vertex<V>> unvisitedChildVertices = graph.adjacent(currentVertex);
    	unvisitedChildVertices.removeAll(visitedVertices);
    	if (unvisitedChildVertices.iterator().hasNext()) {
    		return unvisitedChildVertices.iterator().next();
    	} else {
    		return null;
    	}    	
    }
}
