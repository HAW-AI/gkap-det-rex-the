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
	private List<Vertex<V>> inspectedVertices;
	private Map<Vertex<V>, Integer> flow;
	private Map<Vertex<V>, Boolean> direction;
	private Map<Vertex<V>, Vertex<V>> predecessor;
	private int maximumFlow;

	private FordFulkersonAlgorithm(Graph<E,V> graph, Vertex<V> source, Vertex<V> drain) {
		this.graph = graph;
		this.source = source;
		this.drain = drain;
	}

	public static <E,V> FordFulkersonAlgorithm<E,V> create(Graph<E,V> graph, Vertex<V> source, Vertex<V> drain) {
		if (!(graph.vertices().contains(source) && graph.vertices().contains(drain))) {
			throw new IllegalArgumentException("Source or Drain Vertex is not an element of the given Graph.");
		}
		return new FordFulkersonAlgorithm<E,V>(graph, source, drain);
	}
	
	public int maximumFlow() { return maximumFlow; }
	
	private void initializeMaps() {
		// Weise allen Kanten f(eij ) als einen (initialen) Wert zu, der die Nebenbedingungen erfüllt.

		inspectedVertices = new ArrayList<Vertex<V>>();
		flow = new HashMap<Vertex<V>, Integer>();
		for (Vertex<V> v : graph.vertices()) {
			flow.put(v, 0);
		}
		// Contains the direction in which you move across a vertex. It is false when backwards, and true when forwards.
		direction = new HashMap<Vertex<V>, Boolean>();
		predecessor = new HashMap<Vertex<V>, Vertex<V>>();
		
		// Markiere q(uelle/source) mit (undeﬁniert, ∞).
		predecessor.put(source, null);
		flow.put(source, Integer.MAX_VALUE);
		direction.put(source, null);
	}
	
	public void calculateMaximumFlow() {
		// 1. (Initialisierung)
		initializeMaps();
		int maximumFlow = 0;
		
		Set<Vertex<V>> uninspectedVertices = new HashSet<Vertex<V>>(graph.vertices());
		uninspectedVertices.removeAll(inspectedVertices);
		
		// 2. (Inspektion und Markierung)
		// (a) Falls alle markierten Ecken inspiziert wurden, gehe nach 4.
		while (uninspectedVertices.isEmpty()) {
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
				// vergrößernde Weg bis zur Ecke q rückwärts durchlaufen.
				Vertex<V> maximizeFlowCurrentVertex = drain;
				int flowincrease = flow.get(drain);
				if (predecessor.get(maximizeFlowCurrentVertex) != null) {
					Vertex<V> origin = predecessor.get(maximizeFlowCurrentVertex);
					while (predecessor.containsKey(origin)) {
						for (Edge<E,V> e : graph.edges()) {
							if (e.isReachable(origin, maximizeFlowCurrentVertex) && inspectedVertices.contains(predecessor)) {
								// Für jede Vorwärtskante wird f(eij ) um δs erhöht, und für jede 
								// Rückwärtskante wird f(eji) um δs vermindert.
								if (direction.get(maximizeFlowCurrentVertex)) {
									e.updateFlow(e.flow() + flowincrease);
								} else {
									e.updateFlow(e.flow() - flowincrease);
								}
							}
						}
					}
				}
				
				// Anschließend werden bei allen Ecken mit Ausnahme von q die Markierungen entfernt.
				initializeMaps();
				// Gehe zu 2.
			}
		}
		
		// 4. Es gibt keinen vergrößernden Weg. Der jetzige Wert von d ist optimal.
		// Ein Schnitt A(X, ¬X) mit c(X, ¬X) = d wird gebildet von genau denjenigen
		// Kanten, bei denen entweder die Anfangsecke oder die Endecke markiert ist.
		for (Edge<E,V> e : graph.incident(source)) {
			maximumFlow = e.left().equals(source) ? maximumFlow + e.flow() : maximumFlow - e.flow();
		}
	}
}
