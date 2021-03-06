package com.github.haw.ai.gkap.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.haw.ai.gkap.graph.AccessStats;
import com.github.haw.ai.gkap.graph.Edge;
import com.github.haw.ai.gkap.graph.Graph;
import com.github.haw.ai.gkap.graph.Matrix;
import com.github.haw.ai.gkap.graph.Stats;
import com.github.haw.ai.gkap.graph.Vertex;

import static com.github.haw.ai.gkap.graph.Graphs.*;

public class FloydWarshallAlgorithm<E,V> {
	private final Graph<E,V> graph;
	private Map<Integer, Vertex<V>> map;
	private Matrix edgeCost;
	private Matrix next;
	private Matrix floyd;
	private Matrix warshall;
	
	private AccessStats<E, V> accessStats;
	private Stats<E, V> stats;
	
	private FloydWarshallAlgorithm(Graph<E, V> graph) {
		super();
		this.graph = graph;
		this.map = new HashMap<Integer, Vertex<V>>();
	}
	public static FloydWarshallAlgorithm valueOf(Graph graph) {
		return new FloydWarshallAlgorithm(graph);
	}
	public void runAlgorithm() {
		int c = 0;
		for (Vertex<V> v : graph.vertices()) {
		    // ignore access because it's not relevant to the algo
			this.map.put(c, v);
			c++;
		}

        this.accessStats = new AccessStats<E, V>();
        Long startTime = System.currentTimeMillis();
		
//		Algorithmus von Floyd
//
//		(1) F�r alle i,j : d[i,j] = w[i,j]
//		(2) F�r k = 1 bis n
//		(3)    F�r alle Paare i,j
//		(4)        d[i,j] = min (d[i,j],d[i,k] + d[k,j])
		edgeCost = edgeCostMatrix();
		next = edgeCost.clone();
		next.clear();
		floyd = edgeCost.clone();
		for (int k = 1; k < floyd.getHeight(); k++) {
		    accessStats.increment(this.map.get(k));
			for (int i = 0; i < floyd.getHeight(); i++) {
	            accessStats.increment(this.map.get(i));
				for (int j = 0; j < floyd.getWidth(); j++) {
		            accessStats.increment(this.map.get(j));
                    accessStats.increment(directedEdge(this.map.get(i), this.map.get(k), (E)edgeCost.get(i, k)));
                    accessStats.increment(directedEdge(this.map.get(k), this.map.get(j), (E)edgeCost.get(k, j)));
                    accessStats.increment(directedEdge(this.map.get(i), this.map.get(j), (E)edgeCost.get(i, j)));
					if (floyd.get(i, k)+floyd.get(k, j) < floyd.get(i, j)) {
	                    accessStats.increment(directedEdge(this.map.get(i), this.map.get(k), (E)edgeCost.get(i, k)));
	                    accessStats.increment(directedEdge(this.map.get(k), this.map.get(j), (E)edgeCost.get(k, j)));
						floyd.set(i, j, floyd.get(i, k)+floyd.get(k, j));
						next.set(i, j, (double) k);
					}
				}
			}
		}
		
//		Algorithmus von Warshall
//
//		(1) F�r k = 1 bis n
//		(2)   F�r i = 1 bis n
//		(3)     Falls d[i,k] = 1
//		(4)       F�r j = 1 bis n
//		(5)         Falls d[k,j] = 1 : d[i,j] = 1
		warshall = floyd.clone();
		for (int k = 1; k < warshall.getHeight(); k++) {
            accessStats.increment(this.map.get(k));
			for (int i = 1; i < warshall.getHeight(); i++) {
	            accessStats.increment(this.map.get(i));
                accessStats.increment(directedEdge(this.map.get(i), this.map.get(k), (E)edgeCost.get(i, k)));
				if (warshall.get(i, k) == 1) {
					for (int j = 1; j < warshall.getWidth(); j++) {
		                accessStats.increment(this.map.get(j));
	                    accessStats.increment(directedEdge(this.map.get(k), this.map.get(j), (E)edgeCost.get(k, j)));
						if (warshall.get(k, j) == 1) {
							warshall.set(i, j, 1.0);
						}
					}
				}
			}
		}
        
        Long endTime = System.currentTimeMillis();
        this.stats = new Stats<E, V>(this.accessStats, endTime-startTime);
	}

	public List<Vertex<V>> shortestPath(Vertex<V> start, Vertex<V> finish) {
		if (start == null || finish == null || !graph.vertices().contains(start) || !graph.vertices().contains(finish)) {
			throw new IllegalArgumentException("Something went wrong with your start nodes.");
		}
		List<Vertex<V>> result = new ArrayList<Vertex<V>>();
		int v = 0, t = 0;
		
		for (Map.Entry<Integer, Vertex<V>> e : map.entrySet()) {
			if (e.getValue().equals(start)) {
				v = e.getKey();
			}
			if (e.getValue().equals(finish)) {
				t = e.getKey();
			}
		}
		List<Integer> steps = path(v, t);
		result.add(start);
		for (Integer i : steps) { 
			result.add(map.get(i));
		}
		result.add(finish);
		
		System.out.println(floyd);
		System.out.println(warshall);
		return result;
	}
	
	private List<Integer> path(int s, int e) {
		List<Integer> result = new ArrayList<Integer>(); 
//		Weg rekonstuieren
//		 10    if path[i][j] equals infinity then
//		 11      return "no path";
//		 12    int intermediate := next[i][j];
//		 13    if intermediate equals 'null' then
//		 14      return " ";   /* there is an edge from i to j, with no vertices between */
//		 15    else
//		 16      return GetPath(i,intermediate) + intermediate + GetPath(intermediate,j);
		if (!Double.isInfinite(floyd.get(s, e))) {
			Double cur = next.get(s, e);
//			System.out.println(String.format("%d -> %d = %f (%s -> %s)", s, e, cur, map.get(s), map.get(e)));
			if (cur != 0) {
				result.addAll(path(s,cur.intValue()));
				result.add(cur.intValue());
				result.addAll(path(cur.intValue(),e));
			}
		}
		return result;
	}
	
	private Matrix edgeCostMatrix() {
		int n = graph.vertices().size(), i=0,j=0;
		Double low = Double.POSITIVE_INFINITY, content;
		Matrix result = new Matrix(n,n);
		Set<Edge<E,V>> edges;
		for (Vertex<V> v : graph.vertices()) {
            accessStats.increment(v);
			for (Vertex<V> t : graph.vertices()) {
	            accessStats.increment(t);

				if (graph.isAdjacent(v, t, accessStats)) {
					edges = graph.incident(v);
					edges.retainAll(graph.incident(t, accessStats));
					for (Edge<E, V> e : edges) {
					    accessStats.increment(e, 2);
						content = (Double) e.content();
						if (content < low) {
							low = content;
						}
					}
					result.set(i, j, low);
					low = Double.POSITIVE_INFINITY;
				}
				else {
					result.set(i, j, Double.POSITIVE_INFINITY);
				}
				j++;
			}
			j = 0;
			i++;
		}
		return result;
	}
    
    public Stats<E, V> stats() {
        return new Stats<E, V>(stats);
    }
}
