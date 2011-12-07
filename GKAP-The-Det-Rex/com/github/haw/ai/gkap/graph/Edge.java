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
    
    /**
     * @return the Vertex on the "left" side.
     */
    public Vertex<V> left();

    /**
     * @return the Vertex on the "left" side.
     */
    public Vertex<V> right();
    
    /**
     * @param v the Vertex on of the sides of the Edge
     * @return the other Vertex that is connected with v via the Edge
     */
    public Vertex<V> otherVertex(Vertex<V> v);
    
    /**
     * @return true if the Edge is directed, otherwise false
     */
    public boolean isDirected();

    /**
     * @return the capacity of this edge
     */
    public int capacity();

    /**
     * @return the flow of this Edge
     */
    public int flow();
}
