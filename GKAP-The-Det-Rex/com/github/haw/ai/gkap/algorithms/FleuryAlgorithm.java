package com.github.haw.ai.gkap.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import com.github.haw.ai.gkap.graph.Edge;
import com.github.haw.ai.gkap.graph.Graph;
import com.github.haw.ai.gkap.graph.Vertex;

public class FleuryAlgorithm<E,V> {
	private Graph<E, V> graph;
	private List<Edge<E, V>> marked;
	
	/**
	 * 1. W�hle einen beliebigen Knoten als aktuellen Knoten.
	 * 2. W�hle unter den unmarkierten, mit dem aktuellen Knoten inzidenten Kanten 
	 * 	    eine beliebige Kante aus. Dabei sind zuerst Kanten zu w�hlen, die im 
	 *      unmarkierten Graphen keine Br�ckenkanten sind.
	 * 3. Markiere die gew�hlte Kante und f�ge sie der Kantenfolge hinzu.
	 * 4. W�hle den anderen Knoten der gew�hlten Kante als neuen aktuellen Knoten.
	 * 5. Wenn noch unmarkierte Kanten existieren, dann gehe zu Schritt 2.
	 */
	
	public FleuryAlgorithm(Graph<E,V> graph) {
		this.graph = graph;
	}
	public void runAlgorithm() {
		this.marked = new ArrayList<Edge<E,V>>();
		Set<Edge<E,V>> unbridged = null;
		Set<Edge<E,V>> incidented = null;
		Edge<E,V> edge = null;
		
		if (graph.vertices().isEmpty()) return; 
		
		// 1. W�hle einen beliebigen Knoten als aktuellen Knoten.
		Vertex<V> current = graph.vertices().iterator().next();
		
		// 5. Wenn noch unmarkierte Kanten existieren, dann gehe zu Schritt 2.
		while (marked.size() < graph.vertices().size()) {
			// 2. W�hle unter den unmarkierten, mit dem aktuellen Knoten inzidenten 
			// Kanten eine beliebige Kante aus. Dabei sind zuerst Kanten zu w�hlen, 
			// die im unmarkierten Graphen keine Br�ckenkanten sind.
			incidented = graph.incident(current);
			incidented.removeAll(marked);
			unbridged = unbridged(incidented);
			if (unbridged.size() > 0) {
				edge = unbridged.iterator().next();
			}
			else {
				edge = incidented.iterator().next();
			}
			// 3. Markiere die gew�hlte Kante und f�ge sie der Kantenfolge hinzu.
			marked.add(edge);
			// 4. W�hle den anderen Knoten der gew�hlten Kante als neuen aktuellen Knoten.
			current = edge.otherVertex(current);
		} 
	}
	
	public List<Edge<E, V>> path() {
		return this.marked;
	}
	
	private Set<Edge<E,V>> unbridged(Set<Edge<E,V>> edges) {
		Set<Edge<E,V>> result = new HashSet<Edge<E,V>>();
		for (Edge<E,V> e : edges) {
			if (e.isBridge(graph)) result.add(e);
		}
		return result;
	}
}
