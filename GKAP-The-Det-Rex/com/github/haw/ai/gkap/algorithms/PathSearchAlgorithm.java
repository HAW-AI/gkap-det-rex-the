package com.github.haw.ai.gkap.algorithms;

import java.util.Set;

import com.github.haw.ai.gkap.graph.AccessStats;
import com.github.haw.ai.gkap.graph.Graph;
import com.github.haw.ai.gkap.graph.Path;
import com.github.haw.ai.gkap.graph.Vertex;

public interface PathSearchAlgorithm<E,V> {
	public Path<V> path();
	public void resetPath();
	public boolean hasAugmentingPath();
	public void findAugmentingPath();
	public Set<Vertex<V>> visitedVertices();
	public AccessStats<E, V> stats();
}
