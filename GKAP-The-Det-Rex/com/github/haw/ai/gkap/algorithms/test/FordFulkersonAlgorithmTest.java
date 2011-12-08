package com.github.haw.ai.gkap.algorithms.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.github.haw.ai.gkap.algorithms.FordFulkersonAlgorithm;
import com.github.haw.ai.gkap.graph.Edge;
import com.github.haw.ai.gkap.graph.Graph;
import com.github.haw.ai.gkap.graph.Graphs;
import com.github.haw.ai.gkap.graph.Vertex;

public class FordFulkersonAlgorithmTest {

	FordFulkersonAlgorithm<String,String> ffalgo;
	@Before
	public void setUp() throws Exception {
		Vertex<String> q, s, v2, v3, v4, v5;
		q = Graphs.vertex("q");
		s = Graphs.vertex("s");
		v2 = Graphs.vertex("v2");
		v3 = Graphs.vertex("v3");
		v4 = Graphs.vertex("v4");
		v5 = Graphs.vertex("v5");
		
		Edge<String, String> qv2, qv4, v4v2, v2v3, v3v4, v4v5, v5v3, v5s, v3s;
		qv2 = Graphs.directedEdge(q, v2, "qv2", 3, 0);
		qv4 = Graphs.directedEdge(q, v4, "qv4", 2, 0);
		v4v2 = Graphs.directedEdge(v4, v2, "v4v2", 2, 0);
		v2v3 = Graphs.directedEdge(v2, v3, "v2v3", 2, 0);
		v3v4 = Graphs.directedEdge(v3, v4, "v3v4", 1, 0);
		v4v5 = Graphs.directedEdge(v4, v5, "v4v5", 2, 0);
		v5v3 = Graphs.directedEdge(v5, v3, "v5v3", 4, 0);
		v5s = Graphs.directedEdge(v5, s, "v5s", 4, 0);
		v3s = Graphs.directedEdge(v3, s, "v3s", 2, 0);
		
		List<Edge<String, String>> edgelist = Arrays.asList(qv2, qv4, v4v2, v2v3, v3v4, v4v5, v5v3, v5s, v3s);
		Set<Edge<String, String>> edges = new HashSet<Edge<String,String>>(edgelist);
		
		List<Vertex<String>> vertexlist = Arrays.asList(q, s, v2, v3, v4, v5);
		Set<Vertex<String>> vertices = new HashSet<Vertex<String>>(vertexlist);
		
		Graph<String,String> graph = Graphs.graph(edges, vertices);
		ffalgo = FordFulkersonAlgorithm.create(graph, q, s);
	}

	@Test
	public void testCalculateMaximumFlow() {
		ffalgo.calculateMaximumFlow();
		assertEquals(4, ffalgo.maximumFlow());
	}

}
