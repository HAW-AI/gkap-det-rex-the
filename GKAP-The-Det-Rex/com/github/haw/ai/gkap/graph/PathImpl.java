package com.github.haw.ai.gkap.graph;

import java.util.List;

public class PathImpl<V> implements Path<V> {

	private List<Vertex<V>> vertexlist;

	public PathImpl(List<Vertex<V>> input) {
		this.vertexlist = input;
	}

	@Override
	public Vertex<V> predecessorVertexOf(Vertex<V> otherVertex) {
		return vertexlist.get(vertexlist.indexOf(otherVertex) - 1);
	}

	@Override
	public Vertex<V> nextVertexAfter(Vertex<V> otherVertex) {
		return vertexlist.get(vertexlist.indexOf(otherVertex) + 1);
	}
	
	public static <V> Path<Vertex<V>> create(List<Vertex<V>> input) {
		if (input == null){
			throw new IllegalArgumentException();
		}
		return new PathImpl(input);
	}
}
