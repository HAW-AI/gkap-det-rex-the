package com.github.haw.ai.gkap.graph;

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
		super(left, right, content);
	}

	public boolean isReachable(Vertex<V> from, Vertex<V> to) {
		return (left().equals(from) && right().equals(to)) || (right().equals(from) && left().equals(to));
	}
	
	@Override
	public String toString() {
	    return "[" + left().toString() + ", " + right().toString() + "]";
	}

	// inheritted hashCode() and equals()
}
