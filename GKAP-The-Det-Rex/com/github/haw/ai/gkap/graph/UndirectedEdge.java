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
}
