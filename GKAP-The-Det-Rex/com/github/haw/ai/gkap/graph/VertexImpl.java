package com.github.haw.ai.gkap.graph;

/**
 * @author Till Theis <till.theis@haw-hamburg.de>
 * @author Patrick Detlefsen <patrick.detlefsen@haw-hamburg.de>
 * @author Benjamin Rexin <benjamin.rexin@haw-hamburg.de>
 */
public class VertexImpl<V> implements Vertex<V> {
	public Vertex<V> valueOf(V content) {
		return new VertexImpl<V>(content);
	}
	private VertexImpl(V content) {
		
	}
}
