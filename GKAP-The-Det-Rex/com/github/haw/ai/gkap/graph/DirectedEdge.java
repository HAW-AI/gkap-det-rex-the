package com.github.haw.ai.gkap.graph;

/**
 * @author Till Theis <till.theis@haw-hamburg.de>
 * @author Patrick Detlefsen <patrick.detlefsen@haw-hamburg.de>
 * @author Benjamin Rexin <benjamin.rexin@haw-hamburg.de>
 */
public class DirectedEdge<E, V> extends AbstractEdge<E, V> {
	/**
	 * create a directed edge which goes from 'left' to 'right'
	 */
	public static <E, V> Edge<E, V> valueOf(Vertex<V> left, Vertex<V> right, E content) {
        if (left == null || right == null || content == null) {
            throw new NullPointerException();
        }
	    
		return new DirectedEdge<E, V>(left, right, content);
	}

	private DirectedEdge(Vertex<V> left, Vertex<V> right, E content) {
		super(left, right, content);
	}

	public boolean isReachable(Vertex<V> from, Vertex<V> to) {
		return left().equals(from) && right().equals(to);
	}
	
	@Override
	public String toString() {
	    return "<" + left().toString() + ", " + right().toString() + ">";
	}



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content() == null) ? 0 : content().hashCode());
        result = prime * result + ((left() == null) ? 0 : left().hashCode());
        result = prime * result + ((right() == null) ? 0 : right().hashCode());
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
        if (left() == null) {
            if (other.left() != null)
                return false;
        } else if (!left().equals(other.left()))
            return false;
        if (right() == null) {
            if (other.right() != null)
                return false;
        } else if (!right().equals(other.right()))
            return false;
        return true;
    }
}
