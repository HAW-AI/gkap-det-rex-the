package com.github.haw.ai.gkap.graph;

import java.util.Set;

/**
 * @author Till Theis <till.theis@haw-hamburg.de>
 * @author Patrick Detlefsen <patrick.detlefsen@haw-hamburg.de>
 * @author Benjamin Rexin <benjamin.rexin@haw-hamburg.de>
 */
public interface Edge<E,V> {
    /**
     * @return the content associated with the edge
     */
    public E content();

    /**
     * @return the vertices connected by the edge
     */
    public Set<Vertex<V>> vertices();

    /**
     * @param  from the vertex to find the way from
     * @param  to   the vertex to find the way to
     * @return true if the vertex 'to' is reachable from the vertex 'from'
     *         through the edge - otherwise false
     */
    public boolean isReachable(Vertex<V> from, Vertex<V> to);
    
    public Vertex<V> left();
    public Vertex<V> right();
    
    public Vertex<V> otherVertex(Vertex<V> v);
}
