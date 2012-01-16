package com.github.haw.ai.gkap.algorithms.test;

import static com.github.haw.ai.gkap.algorithms.FleuryAlgorithm.fleuryPath;
import static com.github.haw.ai.gkap.graph.Graphs.undirectedEdge;
import static com.github.haw.ai.gkap.graph.Graphs.vertex;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.github.haw.ai.gkap.graph.Edge;
import com.github.haw.ai.gkap.graph.Graph;
import com.github.haw.ai.gkap.graph.Graphs;
import com.github.haw.ai.gkap.graph.Vertex;

public class FleuryAlgorithmTest {

	Vertex<String> vertex1, vertex2, vertex3, vertex4, vertex5;
    Edge<Integer, String> edge1, edge2, edge3, edge4, edge5, edge6, edge7, edge8;
    Graph<Integer, String> graph;

	@Before
	public void setUp() throws Exception {
        vertex1 = vertex("vertex1");
        vertex2 = vertex("vertex2");
        vertex3 = vertex("vertex3");
        vertex4 = vertex("vertex4");
        vertex5 = vertex("vertex5");

        edge1 = undirectedEdge(vertex1, vertex2, 1);
        edge2 = undirectedEdge(vertex1, vertex3, 1);
        edge3 = undirectedEdge(vertex2, vertex3, 1);
        edge4 = undirectedEdge(vertex2, vertex4, 1);
        edge5 = undirectedEdge(vertex3, vertex5, 1);
        edge6 = undirectedEdge(vertex2, vertex5, 1);
        edge7 = undirectedEdge(vertex4, vertex3, 1);
        edge8 = undirectedEdge(vertex4, vertex5, 1);

        Set<Edge<Integer,String>> edges = new HashSet<Edge<Integer,String>>(Arrays.asList(edge1, edge2, edge3, edge4, edge5, edge6, edge7, edge8));
        Set<Vertex<String>> vertices = new HashSet<Vertex<String>>(Arrays.asList(vertex1, vertex2, vertex3, vertex4, vertex5));

        graph = Graphs.graph(edges, vertices);
	}

	@Test
	public void testFleuryAlgorithm() {
        List<Vertex<String>> path1 = fleuryPath(graph);
        assertTrue(path1.isEmpty());
	}
}
