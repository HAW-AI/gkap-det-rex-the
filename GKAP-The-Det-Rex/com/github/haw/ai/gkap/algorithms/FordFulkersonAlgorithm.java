package com.github.haw.ai.gkap.algorithms;

import com.github.haw.ai.gkap.graph.Edge;
import com.github.haw.ai.gkap.graph.Graph;
import com.github.haw.ai.gkap.graph.Vertex;

public class FordFulkersonAlgorithm<E,V> {

	private Vertex<V> source;
	private PathSearchAlgorithm<E,V> pathSearchAlgorithm;
	private Graph<E, V> graph;
	private Vertex<V> target;
	
	private FordFulkersonAlgorithm(Graph<E,V> graph, Vertex<V> source, Vertex<V> target, PathSearchAlgorithm<E,V> pathSearchAlgorithm) {
		this.graph = graph;
		this.source = source;
		this.target = target;
		this.pathSearchAlgorithm = pathSearchAlgorithm;
	}
	
	public boolean foundMaximumFlow() {
		boolean augmented = false;
		pathSearchAlgorithm.findAugmentingPath();
		while (pathSearchAlgorithm.hasAugmentingPath()) {
			processPath();
			augmented = true;
			pathSearchAlgorithm.findAugmentingPath();
		}
		return augmented;
	}

	private void processPath() {
		Vertex<V> currentVertex = target;
		int cadidateFlow = Integer.MAX_VALUE;
		while (currentVertex != source) {
			Vertex<V> predecessor = pathSearchAlgorithm.path().predecessorVertexOf(currentVertex);
			int possibleSmallerCandidateFlow = 0;
			for (Edge<E,V> e : graph.edges()) {
				if (e.left() == predecessor && e.right() == currentVertex) {
					possibleSmallerCandidateFlow = e.capacity() - e.flow();
				}
				if (e.right() == predecessor && e.left() == currentVertex) {
					possibleSmallerCandidateFlow = e.flow();
				}
			}
			cadidateFlow = possibleSmallerCandidateFlow < cadidateFlow ? possibleSmallerCandidateFlow : cadidateFlow;
			currentVertex = predecessor;
		}
		
		currentVertex = target;
		while (currentVertex != source) {
			Vertex<V> predecessor = pathSearchAlgorithm.path().predecessorVertexOf(currentVertex);
			for (Edge<E,V> e : graph.edges()) {
				if (e.left() == predecessor && e.right() == currentVertex) {
					e.updateFlow(e.flow() + cadidateFlow);
				}
				if (e.right() == predecessor && e.left() == currentVertex) {
					e.updateFlow(e.flow() - cadidateFlow);
				}
			}
			currentVertex = predecessor;
		}
		// RESET PATH!!
		pathSearchAlgorithm.resetPath();
	}

	public static <E,V> FordFulkersonAlgorithm<E, V> create(Graph<E,V> graph, Vertex<V> source, Vertex<V> target, PathSearchAlgorithm<E,V> pathSearchAlgorithm) {
		if (graph == null || source == null || target == null || pathSearchAlgorithm == null) {
			throw new IllegalArgumentException();
		}
		return new FordFulkersonAlgorithm<E, V>(graph, source, target, pathSearchAlgorithm);
	}

	public Graph<E,V> graph() {
		return graph;
	}

}
