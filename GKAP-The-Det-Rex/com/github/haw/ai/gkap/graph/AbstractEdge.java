package com.github.haw.ai.gkap.graph;

/**
 * @author Till Theis <till.theis@haw-hamburg.de>
 * @author Patrick Detlefsen <patrick.detlefsen@haw-hamburg.de>
 * @author Benjamin Rexin <benjamin.rexin@haw-hamburg.de>
 */
abstract public class AbstractEdge<E,V> implements Edge<E,V>{
	abstract Edge<E,V> valueOf(Vertex<V> v1, Vertex<V> v2, E content);
}
