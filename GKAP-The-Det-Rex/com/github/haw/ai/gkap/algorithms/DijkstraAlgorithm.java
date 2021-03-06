package com.github.haw.ai.gkap.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.haw.ai.gkap.graph.AccessStats;
import com.github.haw.ai.gkap.graph.Edge;
import com.github.haw.ai.gkap.graph.Graph;
import com.github.haw.ai.gkap.graph.Graphs;
import com.github.haw.ai.gkap.graph.Stats;
import com.github.haw.ai.gkap.graph.Vertex;

public class DijkstraAlgorithm<E,V> {

	private Graph<E,V> graph;
	private Map<Vertex<V>, Double> distances;
	private Map<Vertex<V>,Vertex<V>> predecessors;
	private Map<Vertex<V>, Boolean> ok;
	private Vertex<V> start;
	private Vertex<V> finish;
	
	private AccessStats<E, V> accessStats;
	private Stats<E, V> stats;
	
	private DijkstraAlgorithm(Graph<E,V> graph) {
		this.graph = graph;
		this.distances = new HashMap<Vertex<V>, Double>();
		this.predecessors = new HashMap<Vertex<V>,Vertex<V>>();
	}

	public static DijkstraAlgorithm valueOf(Graph graph) {
		return new DijkstraAlgorithm(graph);
	}
	
	public void runAlgorithm(Vertex<V> start, Vertex<V> finish) {
		if (start == null || finish == null || !graph.vertices().contains(start) || !graph.vertices().contains(finish)) {
			throw new IllegalArgumentException("Something went wrong with your start nodes.");
		}
		this.start = start;
		this.finish = finish;

		/*
		 * Edge contents must be numbers
		 */
		for (Edge<E, V> edge : graph.edges()) {
			if (!(edge.content() instanceof Integer || edge.content() instanceof Double)) {
				throw new IllegalArgumentException("All of your Edge contents have to be Numbers because they are the weight used to calculate the shortest path.");
			}
		}
		
		this.accessStats = new AccessStats<E, V>();
		
		Long startTime = System.currentTimeMillis();

		/*
		 * The setup stage
		 */
		setup(start);
		
		/*
		 * The iteration Stage
		 */
		Vertex<V> currentVertex = null;
		Set<Edge<E, V>> incidentEdges = null;
		while (ok.values().contains(false)) {
			
			/*
			 * Get the unvisited Vertex with the lowest distance..
			 */
			currentVertex = getUnvisitedVertexWithLowestDistance();
			/*
			 * ..and set this Vertex as Visited.
			 */
			ok.put(currentVertex, true);
			
			// get the possible Edges we could possibly walk along from the current Vertex
			incidentEdges = graph.incident(currentVertex, accessStats);
			for (Edge<E, V> e : incidentEdges) {
			    accessStats.increment(e);
			    
			    accessStats.increment(e, 2);
				// if the Distance is lower than the currently know distance:
				if ((Double) e.content() + distances.get(currentVertex) < distances.get(e.otherVertex(currentVertex))) {
				    accessStats.increment(e, 3);
					// set the new, shorter distance
					distances.put(e.otherVertex(currentVertex), ((Double) e.content() + distances.get(currentVertex)));
					// and set yourself as the predecessor
					predecessors.put(e.otherVertex(currentVertex), currentVertex);
				}
			}
		}
		
		Long endTime = System.currentTimeMillis();
		this.stats = new Stats<E, V>(this.accessStats, endTime-startTime);
	}
	
	public List<Vertex<V>> shortestPath() {
		List<Vertex<V>> result = new ArrayList<Vertex<V>>();
		
		Vertex<V> currentVertex = finish;
		Vertex<V> predecessor = predecessors.get(currentVertex);
		
		while (!(currentVertex == predecessor)) {
			result.add(currentVertex);
			currentVertex = predecessor;
			predecessor = predecessors.get(currentVertex); //fail
		}
		result.add(currentVertex);
		Collections.reverse(result);
		return result;
	}
	
	public String toString() {
		return "Distances: " + distances.toString() + "\nPredecessors: " + predecessors.toString() + "\nOk: " + ok.toString();
	}
	
	private Vertex<V> getUnvisitedVertexWithLowestDistance() {
		Vertex<V> result = null;
		
		Set<Vertex<V>> unvisited = new HashSet<Vertex<V>>();
		for (Vertex<V> vert : ok.keySet()) {
		    accessStats.increment(vert);
		    
			if (!ok.get(vert)) {
				unvisited.add(vert);
			}
		}
		
		for (Vertex<V> vert : unvisited) {
		    accessStats.increment(vert);
			if (result == null) {
				result = vert;
			}
			
			if (distances.get(vert) < distances.get(result)) {
				result = vert;
			}
		}

		return result;
	}

	/*
	 * This Method sets the distances map, the predecessors map and the ok map up
	 * with the required initial values so that the findShortestPath method can 
	 * start the second stage of the Dijkstra Algorithm.
	 */
	private void setup(Vertex<V> startVertex) {
		distances = new HashMap<Vertex<V>, Double>();
		predecessors = new HashMap<Vertex<V>, Vertex<V>>();
		ok = new HashMap<Vertex<V>, Boolean>();
		
		for (Vertex<V> vertex : graph.vertices()) {
		    accessStats.increment(vertex);
			distances.put(vertex, Double.POSITIVE_INFINITY);
			ok.put(vertex, false);
		}
		distances.put(startVertex, 0.0);
		predecessors.put(startVertex, startVertex);

		
		/*
		 * All the undirected Edges have to be replaced with two directed
		 * opposing Edges
		 */
		Set<Edge<E, V>> updatedEdges = new HashSet<Edge<E, V>>();
		for (Edge<E,V> edge : graph.edges()) {
		    accessStats.increment(edge);
			if (edge.isDirected()) {
				updatedEdges.add(edge);
			} else {
			    accessStats.increment(edge, 6);
				updatedEdges.add(Graphs.directedEdge(edge.left(), edge.right(), edge.content()));
				updatedEdges.add(Graphs.directedEdge(edge.right(), edge.left(), edge.content()));
			}
		}
		
		for (Vertex<V> v : graph.vertices()) accessStats.increment(v);
		graph = Graphs.graph(updatedEdges, graph.vertices());
	}
	
	public Stats<E, V> stats() {
	    return new Stats<E, V>(stats);
	}
}
