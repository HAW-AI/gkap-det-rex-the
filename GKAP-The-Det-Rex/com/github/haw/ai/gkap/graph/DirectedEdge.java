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
		return new DirectedEdge<E, V>(left, right, content);
	}

	private DirectedEdge(Vertex<V> left, Vertex<V> right, E content) {
		super(left, right, content);
	}

	public boolean isReachable(Vertex<V> from, Vertex<V> to) {
		// compare identity because the graph may contain many structurally
		// equal vertices
		return from == left() && to == right();
	}
	
	@Override
	public String toString() {
	    return "<" + left().toString() + ", " + right().toString() + ">";
	}

	// inheritted hashCode() and equals()
}
