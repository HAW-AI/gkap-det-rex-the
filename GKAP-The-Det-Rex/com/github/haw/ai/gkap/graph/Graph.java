package com.github.haw.ai.gkap.graph;

/**
 * @author Till Theis <till.theis@haw-hamburg.de>
 * @author Patrick Detlefsen <patrick.detlefsen@haw-hamburg.de>
 * @author Benjamin Rexin <benjamin.rexin@haw-hamburg.de>
 */
public interface Graph<E,V> {

  /**
   * @return the degree of a given Vertex
   */
  public int degree(Vertex<V>);

  /**
   * @return the Set of Vertices that are adjacent to the given Vertex
   */
  public Set<Vertex<V>> adjacent(Vertex<V>);

  /**
   * @return the Set of Edges that are incident with the given Vertex
   */
  public Set<Edge<T>> incident(Vertex<V>);
}
