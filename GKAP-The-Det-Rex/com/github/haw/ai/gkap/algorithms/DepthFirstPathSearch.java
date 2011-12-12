package com.github.haw.ai.gkap.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.github.haw.ai.gkap.graph.AccessStats;
import com.github.haw.ai.gkap.graph.Edge;
import com.github.haw.ai.gkap.graph.Graph;
import com.github.haw.ai.gkap.graph.Path;
import com.github.haw.ai.gkap.graph.PathImpl;
import com.github.haw.ai.gkap.graph.Vertex;

public class DepthFirstPathSearch<E,V> implements PathSearchAlgorithm<E,V> {

	private Path<V> path;
	private Graph<E,V> graph;
	private Vertex<V> source;
	private Vertex<V> target;
	private Map<Vertex<V>,Boolean> visitedVertices;
	private Stack<Vertex<V>> augmentingPath;
	private AccessStats<E, V> stats;
	
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
	public Set<Vertex<V>> visitedVertices() {
	    return visitedVertices.keySet();
	}

	@Override
	public void resetPath() {
		this.path = null;
	}

	@Override
	public boolean hasAugmentingPath() {
	    return path != null;
	}

	@Override
	public Path<V> path() {
		return path;
	}
	
	private class VertexInfo<W> {
	    public Vertex<W> pred;
	    public boolean isForward;
	    public VertexInfo(Vertex<W> pred, boolean isForward) {
	        this.pred = pred;
	        this.isForward = isForward;
	    }
	    @Override
	    public String toString() {
	        return "VertexInfo(" + pred + ", " + isForward + ")";
	    }
	}
	
	public void findAugmentingPath() {
		stats = new AccessStats<E, V>();
	    Map<Vertex<V>, VertexInfo<V>> preds;
	    preds = new HashMap<Vertex<V>, VertexInfo<V>>();
	    preds.put(source, new VertexInfo<V>(null, true));
	    visitedVertices.clear();
	    
		augmentingPath = new Stack<Vertex<V>>();
		augmentingPath.push(source);
		
		while (!augmentingPath.empty()) {
			Vertex<V> currentVertex = augmentingPath.pop();
			stats.increment(currentVertex);
			Iterator<Edge<E,V>> currentVertexEdgesIterator = graph.incident(currentVertex).iterator();
			// forwards
			while(currentVertexEdgesIterator.hasNext()) {
				Edge<E,V> e = currentVertexEdgesIterator.next();
				stats.increment(e);
				if (e.left() == currentVertex) {
					stats.increment(e);
					stats.increment(e.left());
					Vertex<V> nextVertexToBeAdded = e.right();
					stats.increment(e.right());
					if (!visitedVertices.keySet().contains(nextVertexToBeAdded) && e.capacity() > e.flow()) {
						visitedVertices.put(currentVertex, true);
						preds.put(nextVertexToBeAdded, new VertexInfo<V>(currentVertex, true));
						
						stats.increment(target);
						if (nextVertexToBeAdded == target) {
						    this.path = makePath(preds);
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
				stats.increment(e);
				if (e.right() == currentVertex) {
					stats.increment(e);
					stats.increment(e.right());
					Vertex<V> nextVertexToBeAdded = e.left();
					stats.increment(e.left());
					if (!visitedVertices.keySet().contains(nextVertexToBeAdded) && e.flow() > 0) {
						visitedVertices.put(currentVertex, false);
                        preds.put(nextVertexToBeAdded, new VertexInfo<V>(currentVertex, true));

						augmentingPath.push(nextVertexToBeAdded);
					}
				}
			}
			
		}

		// no path found
		this.path = null;
	}
	
	private Path<V> makePath(Map<Vertex<V>, VertexInfo<V>> preds) {
        List<Vertex<V>> pathList = new LinkedList<Vertex<V>>();
        for (Vertex<V> v = target; v != source; v = preds.get(v).pred) {
            pathList.add(v);
        }
        pathList.add(source);
        Collections.reverse(pathList);
        return new PathImpl<V>(pathList);
	}

	@Override
	public AccessStats<E, V> stats() {
		return new AccessStats<E, V>(stats);
	}
}
