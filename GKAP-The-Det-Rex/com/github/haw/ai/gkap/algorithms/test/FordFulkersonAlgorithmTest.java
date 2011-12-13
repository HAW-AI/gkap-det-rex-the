package com.github.haw.ai.gkap.algorithms.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.github.haw.ai.gkap.algorithms.AbstractPathSearchAlgorithm;
import com.github.haw.ai.gkap.algorithms.BreadthFirstPathSearch;
import com.github.haw.ai.gkap.algorithms.DepthFirstPathSearch;
import com.github.haw.ai.gkap.algorithms.FordFulkersonAlgorithm;
import com.github.haw.ai.gkap.algorithms.PathSearchAlgorithm;
import com.github.haw.ai.gkap.graph.Edge;
import com.github.haw.ai.gkap.graph.Graph;
import com.github.haw.ai.gkap.graph.Graphs;
import com.github.haw.ai.gkap.graph.Vertex;

public class FordFulkersonAlgorithmTest {

	Graph<String,String> graph;
	Vertex<String> q, s;
	
	@Before
	public void setUp() throws Exception {
		Vertex<String> v2, v3, v4, v5;
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
		v5v3 = Graphs.directedEdge(v5, v3, "v5v3", 1, 0);
		v5s = Graphs.directedEdge(v5, s, "v5s", 4, 0);
		v3s = Graphs.directedEdge(v3, s, "v3s", 2, 0);
		
		List<Edge<String, String>> edgelist = Arrays.asList(qv2, qv4, v4v2, v2v3, v3v4, v4v5, v5v3, v5s, v3s);
		Set<Edge<String, String>> edges = new HashSet<Edge<String,String>>(edgelist);
		
		List<Vertex<String>> vertexlist = Arrays.asList(q, s, v2, v3, v4, v5);
		Set<Vertex<String>> vertices = new HashSet<Vertex<String>>(vertexlist);
		
		graph = Graphs.graph(edges, vertices);
	}

    @Test
    public void testCalculateMaximumFlowDepthFirst() {
        innerTestCalculateMaximumFlow(DepthFirstPathSearch.create(graph, q, s), "Ford-Fulkerson");
    }

    @Test
    public void testCalculateMaximumFlowBreadthFirst() {
        innerTestCalculateMaximumFlow(BreadthFirstPathSearch.create(graph, q, s), "Edmonds-Karp");
    }
    
    public void innerTestCalculateMaximumFlow(PathSearchAlgorithm<String, String> pathsearch, String title) {
        FordFulkersonAlgorithm<String,String> ffalgo =
            FordFulkersonAlgorithm.create(graph, q, s, pathsearch);
        
        assertEquals(true, ffalgo.foundMaximumFlow());
        assertEquals(4, ffalgo.maxFlow());
        
        System.out.println(title + "\n");
        System.out.println(ffalgo.stats());
    }

}
