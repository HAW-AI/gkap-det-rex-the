package com.github.haw.ai.gkap.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Till Theis <till.theis@haw-hamburg.de>
 * @author Patrick Detlefsen <patrick.detlefsen@haw-hamburg.de>
 * @author Benjamin Rexin <benjamin.rexin@haw-hamburg.de>
 */
public class GraphImpl<E, V> implements Graph<E, V> {
	public static <E, V> Graph<E, V> valueOf(Set<Edge<E, V>> edges,
			Set<Vertex<V>> vertices) {
		return new GraphImpl<E, V>(edges, vertices);
	}

	private Set<Edge<E, V>> edges;
	private Set<Vertex<V>> vertices;

	private GraphImpl(Set<Edge<E, V>> edges, Set<Vertex<V>> vertices) {
		if (edges == null || vertices == null || edges.contains(null) || vertices.contains(null)) {
			throw new NullPointerException();
		}
		this.edges = edges;
		this.vertices = vertices;
	}

	@Override
	public int degree(Vertex<V> vertex) {
		List<Edge<E,V>> list = new ArrayList<Edge<E,V>>();
		for (Edge<E,V> edge : edges) {
			if (isIncident(vertex, edge)) {
				list.add(edge);
			}
		}
		return list.size();
	}

	@Override
	public boolean isAdjacent(Vertex<V> vertex, Vertex<V> otherVertex) {
		for (Edge<E, V> e : edges) {
			if (e.vertices().contains(vertex)
					&& e.vertices().contains(otherVertex)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isIncident(Vertex<V> vertex, Edge<E, V> edge) {
		if (edge.vertices().contains(vertex)) {
			return true;
		}
		return false;
	}

	@Override
	public Set<Vertex<V>> adjacent(Vertex<V> vertex) {
		Set<Vertex<V>> result = new HashSet<Vertex<V>>();
		for (Vertex<V> v : vertices) {
			if (isAdjacent(v, vertex)) {
				result.add(v);
			}
		}

		return result;
	}

	@Override
	public Set<Edge<E, V>> incident(Vertex<V> vertex) {
		Set<Edge<E, V>> result = new HashSet<Edge<E, V>>();
		for (Edge<E, V> edge : edges) {
			if (isIncident(vertex, edge)) {
				result.add(edge);
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
	    if (vertices.size() == 0) {
	        return "EmptyGraph";
	    } else {
	        return toString(vertices.iterator().next(), new HashSet<Vertex<V>>(), 0);
	    }
	}

	public String toString(Vertex<V> root, Set<Vertex<V>> visited, int depth) {
	    if (visited.contains(root)) {
	        return "";
	    }
	    
	    Set<Edge<E, V>> adjacent = adjacenceMap().get(root);
	    Set<Vertex<V>> newVisited = new HashSet<Vertex<V>>(visited);
	    newVisited.add(root);
	    String s = "";
	    String indentation = "";
	    
	    for (int i = 0; i < depth; ++i) {
	        indentation += " ";
	    }
	    
	    for (Edge<E,V> edge : adjacent) {
	        if (edge.vertices().size() == 1) {
	            // loop
	            s += indentation + root + "-" + edge + "\n";
	        } else {
	            s += indentation + root + "-" + edge + " " + toString(edge.otherVertex(root), newVisited, depth+1) + "\n";
	        }
	    }
	    
	    return s;
	}

	public Map<Vertex<V>, Set<Edge<E, V>>> adjacenceMap() {
	    // caching
	    Map<Vertex<V>, Set<Edge<E, V>>> result = new HashMap<Vertex<V>, Set<Edge<E, V>>>();
	    for (Edge<E, V> edge : edges) {
	        Set<Vertex<V>> vertices = edge.vertices();
	        if (vertices.size() == 1) {
	            // loop
	            checkAndSet(result, edge.left(), new HashSet<Edge<E,V>>());
	            result.get(edge.left()).add(edge);
	        } else {
	            if (edge.isReachable(edge.left(), edge.right())) {
	                checkAndSet(result, edge.left(), new HashSet<Edge<E,V>>());
	                result.get(edge.left()).add(edge);
	            }
	            
	            if (edge.isReachable(edge.right(), edge.left())) {
	                checkAndSet(result, edge.right(), new HashSet<Edge<E,V>>());
	                result.get(edge.right()).add(edge);
	            }
	        }
	    }
	    
	    return result;
	}
	
	private <K, Val> void checkAndSet(Map<K, Val> map, K key, Val defaultValue) {
	    if (!map.containsKey(key)) {
	        map.put(key, defaultValue);
	    }
	}
}
