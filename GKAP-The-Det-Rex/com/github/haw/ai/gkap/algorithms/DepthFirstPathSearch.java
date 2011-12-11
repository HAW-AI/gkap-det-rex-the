package com.github.haw.ai.gkap.algorithms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.github.haw.ai.gkap.graph.Edge;
import com.github.haw.ai.gkap.graph.Graph;
import com.github.haw.ai.gkap.graph.Path;
import com.github.haw.ai.gkap.graph.PathImpl;
import com.github.haw.ai.gkap.graph.Vertex;

public class DepthFirstPathSearch<E,V> implements PathSearchAlgorithm<E,V> {

	private Path<Vertex<V>> path;
	private Graph<E,V> graph;
	private Vertex<V> source;
	private Vertex<V> target;
	private Map<Vertex<V>,Boolean> visitedVertices;
	private Stack<Vertex<V>> augmentingPath;
	
	private DepthFirstPathSearch(Graph<E,V> graph, Vertex<V> source, Vertex<V> target) {
		this.graph = graph;
		this.source = source;
		this.target = target;
		this.visitedVertices = new HashMap<Vertex<V>,Boolean>();
	}
	
	public static <E,V> DepthFirstPathSearch<E,V> create(Graph<E,V> graph, Vertex<V> source, Vertex<V> target) {
		if (graph == null || source == null || target == null) {
			throw new IllegalArgumentException();
		}
		return new DepthFirstPathSearch<E, V>(graph, source, target);
	}

	@Override
	public void resetPath() {
		this.path = null;
	}

	@Override
	public boolean hasAugmentingPath() {
		if (path == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Path<Vertex<V>> path() {
		return path;
	}
	
	public void findAugmentingPath() {
		augmentingPath = new Stack<Vertex<V>>();
		augmentingPath.push(source);
		while (!augmentingPath.empty()) {
			Vertex<V> currentVertex = augmentingPath.pop();
			Iterator<Edge<E,V>> currentVertexEdgesIterator = graph.incident(currentVertex).iterator();
			// forwards
			while(currentVertexEdgesIterator.hasNext()) {
				Edge<E,V> e = currentVertexEdgesIterator.next();
				if (e.left() == currentVertex) {
					Vertex<V> nextVertexToBeAdded = e.right();
					if (!visitedVertices.keySet().contains(nextVertexToBeAdded) && e.capacity() > e.flow()) {
						visitedVertices.put(currentVertex, true);
						
						if (nextVertexToBeAdded == target) {
							return;
						}
						augmentingPath.push(nextVertexToBeAdded);
					}
				}
			}
			
			// backwards
			currentVertexEdgesIterator = graph.incident(currentVertex).iterator();
			while(currentVertexEdgesIterator.hasNext()) {
				Edge<E,V> e = currentVertexEdgesIterator.next();
				if (e.right() == currentVertex) {
					Vertex<V> nextVertexToBeAdded = e.left();
					if (!visitedVertices.keySet().contains(nextVertexToBeAdded) && e.flow() > 0) {
						visitedVertices.put(currentVertex, false);
						augmentingPath.push(nextVertexToBeAdded);
					}
				}
			}
			
		}
		path = PathImpl.create(augmentingPath);
	}
}
