package com.github.haw.ai.gkap.graph;

public interface Path<V> {
	public Vertex<V> predecessorVertexOf(Vertex<V> currentVertex);
	public Vertex<V> nextVertexAfter(Vertex<V> otherVertex);
	
}
