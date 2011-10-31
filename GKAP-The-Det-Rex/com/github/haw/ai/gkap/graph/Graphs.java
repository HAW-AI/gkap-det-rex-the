package com.github.haw.ai.gkap.graph;

public class Graphs {
    public static <E, V> Edge<E, V> directedEdge(Vertex<V> left, Vertex<V> right, E content) {
        return DirectedEdge.valueOf(left, right, content);
    }
    
    public static <E, V> Edge<E, V> undirectedEdge(Vertex<V> left, Vertex<V> right, E content) {
        return new UndirectedEdge<E, V>(left, right, content);
    }
    
    public static <V> Vertex<V> vertex(V content) {
    	return new VertexImpl<V>(content);
    }
}
