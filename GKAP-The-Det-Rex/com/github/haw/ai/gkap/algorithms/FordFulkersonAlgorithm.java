package com.github.haw.ai.gkap.algorithms;

import java.util.Set;

import com.github.haw.ai.gkap.graph.AccessStats;
import com.github.haw.ai.gkap.graph.Edge;
import com.github.haw.ai.gkap.graph.Graph;
import com.github.haw.ai.gkap.graph.Stats;
import com.github.haw.ai.gkap.graph.Vertex;

public class FordFulkersonAlgorithm<E,V> {

	private Vertex<V> source;
	private PathSearchAlgorithm<E,V> pathSearchAlgorithm;
	private Graph<E, V> graph;
	private Vertex<V> target;
	private Stats<E, V> stats;
	
	private FordFulkersonAlgorithm(Graph<E,V> graph, Vertex<V> source, Vertex<V> target, PathSearchAlgorithm<E,V> pathSearchAlgorithm) {
		this.graph = graph;
		this.source = source;
		this.target = target;
		this.pathSearchAlgorithm = pathSearchAlgorithm;
	}
	
	public int maxFlow() {
	    // foundMaximumFlow() must have been called before
	    Set<Vertex<V>> left = pathSearchAlgorithm.visitedVertices();
	    Set<Vertex<V>> right = graph.vertices();
	    right.removeAll(left);
	    
	    int flow = 0;
	    
	    // cut
	    for (Vertex<V> v : left) {
	        for (Edge<E, V> e : graph.incident(v)) {
	            if (left.contains(e.left()) && right.contains(e.right())) {
	                // forward edge
	                flow += e.flow();
	            } else if (right.contains(e.left()) && left.contains(e.right())) {
	                // backward edge
	                flow -= e.flow();
	            }
	        }
	    }
	    
	    return flow;
	}
	
	public boolean foundMaximumFlow() {
		Long startTime = System.currentTimeMillis();
		boolean augmented = false;
		pathSearchAlgorithm.findAugmentingPath();
		while (pathSearchAlgorithm.hasAugmentingPath()) {
			processPath();
			augmented = true;
			pathSearchAlgorithm.findAugmentingPath();
		}
		Long endTime = System.currentTimeMillis();
		this.stats = new Stats<E, V>(this.pathSearchAlgorithm.stats(), endTime-startTime);
		return augmented;
	}

	private void processPath() {
		Vertex<V> currentVertex = target;
		int candidateFlow = Integer.MAX_VALUE;
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
			candidateFlow = possibleSmallerCandidateFlow < candidateFlow ? possibleSmallerCandidateFlow : candidateFlow;
			currentVertex = predecessor;
		}
		
		currentVertex = target;
		while (currentVertex != source) {
			Vertex<V> predecessor = pathSearchAlgorithm.path().predecessorVertexOf(currentVertex);
			for (Edge<E,V> e : graph.edges()) {
				if (e.left() == predecessor && e.right() == currentVertex) {
					e.updateFlow(e.flow() + candidateFlow);
				}
				if (e.right() == predecessor && e.left() == currentVertex) {
					e.updateFlow(e.flow() - candidateFlow);
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
	
	public Stats<E, V> stats() {
		return new Stats<E, V>(this.stats);
	}

}
