package com.github.haw.ai.gkap.graph;

import java.util.Set;

public class Graphs {
    public static <E, V> Edge<E, V> directedEdge(Vertex<V> left, Vertex<V> right, E content) {
        return DirectedEdge.valueOf(left, right, content);
    }
    
    public static <E,V> Edge<E,V> directedEdge(Vertex<V> left, Vertex<V> right, E content, int capacity, int flow) {
    	return DirectedEdge.valueOf(left, right, content, capacity, flow);
    }
    
    public static <E, V> Edge<E, V> undirectedEdge(Vertex<V> left, Vertex<V> right, E content) {
        return UndirectedEdge.valueOf(left, right, content);
    }
    
    public static <V> Vertex<V> vertex(V content) {
    	return VertexImpl.valueOf(content);
    }
    
    public static <E, V> Graph<E, V> graph(Set<Edge<E, V>> edges, Set<Vertex<V>> vertices) {
    	return GraphImpl.valueOf(edges, vertices);
    }
}
