package com.github.haw.ai.gkap.graph;

import java.util.Set;

/**
 * @author Till Theis <till.theis@haw-hamburg.de>
 * @author Patrick Detlefsen <patrick.detlefsen@haw-hamburg.de>
 * @author Benjamin Rexin <benjamin.rexin@haw-hamburg.de>
 */
public interface Graph<E,V> {

  /**
   * @param A Vertex of which the degree should be calculated
   * @return the degree of a given Vertex
   */
  public int degree(Vertex<V> vertex);

  /**
   * @param two vertices that may or may not be adjacent
   * @return true if the two given vertices are adjacent
   */
  public boolean isAdjacent(Vertex<V> vertex, Vertex<V> otherVertex);

  /**
   * @param A Vertex of which the Set of adjacent Vertices is calculated
   * @return the Set of Vertices that are adjacent to the given Vertex
   */
  public Set<Vertex<V>> adjacent(Vertex<V> vertex);

  /**
   * @param A Vertex and Edge which are checked for their incidence
   * @return true if the vertex is incident to the given edge
   */
  public boolean isIncident(Vertex<V> vertex, Edge<E,V> edge);

  /**
   * @param A Vertex whose Set of incident Edges will be calculated
   * @return the Set of Edges that are incident with the given Vertex
   */
  public Set<Edge<E,V>> incident(Vertex<V> vertex);
  
  /**
   * @return the Set of edges of this Graph
   */
  public Set<Edge<E, V>> edges();
  
  /**
   * @return the Set of Vertices of this Graph
   */
  public Set<Vertex<V>> vertices();
  
  
  // stats ...
  public Set<Vertex<V>> adjacent(Vertex<V> vertex, AccessStats<E, V> stats);
  public Set<Edge<E,V>> incident(Vertex<V> vertex, AccessStats<E, V> stats);
}
