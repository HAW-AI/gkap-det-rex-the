package com.github.haw.ai.gkap.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.haw.ai.gkap.graph.Edge;
import com.github.haw.ai.gkap.graph.Graph;
import com.github.haw.ai.gkap.graph.Vertex;

public class FordFulkersonAlgorithm<E,V> {

	private Graph<E,V> graph;
	private Vertex<V> source;
	private Vertex<V> drain;

	private FordFulkersonAlgorithm(Graph<E,V> graph, Vertex<V> source, Vertex<V> drain) {
		this.graph = graph;
		this.source = source;
		this.drain = drain;
	}

	public static <E,V> FordFulkersonAlgorithm<E,V> create(Graph<E,V> graph, Vertex<V> source, Vertex<V> drain) {
		return new FordFulkersonAlgorithm<E,V>(graph, source, drain);
	}
	
	
	
	public int calculateMaximumFlow() {
		// 1. (Initialisierung)
		// Weise allen Kanten f(eij ) als einen (initialen) Wert zu, der die Nebenbedingungen
		// erfüllt. Markiere q(uelle/source) mit (undeﬁniert, ∞).
		Map<Vertex<V>, Integer> flow = new HashMap<Vertex<V>, Integer>();
		Map<Vertex<V>, Boolean> direction = new HashMap<Vertex<V>, Boolean>();
		Map<Vertex<V>, Vertex<V>> predecessor = new HashMap<Vertex<V>, Vertex<V>>();
		int maximumFlow = 0;
		List<Vertex<V>> inspectedVertices = new ArrayList<Vertex<V>>();
		Set<Vertex<V>> uninspectedVertices;
		
		// 2. (Inspektion und Markierung)
		// (a) Falls alle markierten Ecken inspiziert wurden, gehe nach 4.
		while (inspectedVertices.size() != graph.vertices().size()) {
			uninspectedVertices = new HashSet<Vertex<V>>(graph.vertices());
			uninspectedVertices.removeAll(inspectedVertices);
			// (b) Wähle eine beliebige markierte, aber noch nicht inspizierte Ecke vi
			// 	   und inspiziere sie wie folgt (Berechnung des Inkrements
			Vertex<V> currentVertex = uninspectedVertices.iterator().next();

			// (Vorwärtskante) Für jede Kante eij ∈ O(vi) mit unmarkierter
			for (Edge<E,V> e : graph.incident(currentVertex)) {
				// Ecke vj und f(eij ) < c(eij ) markiere vj mit (+vi, δj ),
				if (e.left().equals(currentVertex) && predecessor.containsKey(e.right()) && e.flow() < e.capacity()) {
					// wobei δj die kleinere der beiden Zahlen c(eij ) − f(eij ) und δi ist.
					direction.put(e.right(), true);
					flow.put(e.right(), Math.min(e.capacity() - e.flow(), flow.get(currentVertex)));
					predecessor.put(e.right(), currentVertex);
				}
			}

			// (Rückwärtskante) Für jede Kante eji ∈ I(vi) mit unmarkierter
			for (Edge<E,V> e : graph.incident(currentVertex)) {
				// Ecke vj und f(eji) > 0 markiere vj mit (−vi, δj ),
				if (e.right().equals(currentVertex) && predecessor.containsKey(e.left()) && e.flow() > 0) {
					// wobei δj die kleinere der beiden Zahlen f(eij) und δi ist.
					direction.put(e.left(), false);
					flow.put(e.left(), Math.min(e.flow(), flow.get(currentVertex)));
					predecessor.put(e.left(), currentVertex);
				}
			}

			inspectedVertices.add(currentVertex);

			// (c) Falls s(enke/drain) markiert ist, gehe zu 3., sonst zu 2.(a).
			if (inspectedVertices.contains(drain)) {
				// (Vergrößerung der Flußstärke)
				// Bei s beginnend läßt sich anhand der Markierungen der gefundene 
				// vergrößernde Weg bis zur Ecke q rückwärts durchlaufen. Für jede
				// Vorwärtskante wird f(eij ) um δs erhöht, und für jede 
				// Rückwärtskante wird f(eji) um δs vermindert. Anschließend werden
				// bei allen Ecken mit Ausnahme von q die Markierungen entfernt. 
				// Gehe zu 2.
				
				// TODO :)
			}
		}
		
		// 4. Es gibt keinen vergrößernden Weg. Der jetzige Wert von d ist optimal.
		// Ein Schnitt A(X, ¬X) mit c(X, ¬X) = d wird gebildet von genau denjenigen
		// Kanten, bei denen entweder die Anfangsecke oder die Endecke markiert ist.
		for (Edge<E,V> e : graph.incident(source)) {
			maximumFlow = e.left().equals(source) ? maximumFlow + e.flow() : maximumFlow - e.flow();
		}
		return maximumFlow;
	}
}
