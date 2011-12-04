package com.github.haw.ai.gkap.algorithms.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.github.haw.ai.gkap.algorithms.DijkstraAlgorithm;
import com.github.haw.ai.gkap.graph.Edge;
import com.github.haw.ai.gkap.graph.Graph;
import com.github.haw.ai.gkap.graph.GraphImpl;
import com.github.haw.ai.gkap.graph.Graphs;
import com.github.haw.ai.gkap.graph.Vertex;

public class DijkstraAlgorithmTest<E, V> {
	
	Vertex<String> v1 = Graphs.vertex("v1");
	Vertex<String> v2 = Graphs.vertex("v2");
	Vertex<String> v3 = Graphs.vertex("v3");
	Vertex<String> v4 = Graphs.vertex("v4");
	Vertex<String> v5 = Graphs.vertex("v5");
	Vertex<String> v6 = Graphs.vertex("v6");
	
	Edge<Double, String> e1 = Graphs.undirectedEdge(v1, v2, 1.0);
	Edge<Double, String> e2 = Graphs.undirectedEdge(v1, v6, 3.0);
	Edge<Double, String> e3 = Graphs.undirectedEdge(v2, v6, 2.0);
	Edge<Double, String> e4 = Graphs.undirectedEdge(v2, v3, 5.0);
	Edge<Double, String> e5 = Graphs.undirectedEdge(v2, v5, 3.0);
	Edge<Double, String> e6 = Graphs.undirectedEdge(v6, v3, 2.0);
	Edge<Double, String> e7 = Graphs.undirectedEdge(v3, v5, 2.0);
	Edge<Double, String> e8 = Graphs.undirectedEdge(v6, v5, 1.0);
	Edge<Double, String> e9 = Graphs.undirectedEdge(v3, v4, 1.0);
	Edge<Double, String> e10 = Graphs.undirectedEdge(v5, v4, 3.0);
	Set<Edge<E, V>> edges = new HashSet<Edge<E, V>>();
	Set<Vertex<V>> vertices = new HashSet<Vertex<V>>();
	Graph graph = null;
	
	Vertex<String> a = Graphs.vertex("a");
	Vertex<String> b = Graphs.vertex("b");
	Vertex<String> c = Graphs.vertex("c");
	Vertex<String> d = Graphs.vertex("d");
	Vertex<String> e = Graphs.vertex("e");
	Vertex<String> f = Graphs.vertex("f");
	
	Edge<Double, String> ab = Graphs.undirectedEdge(a,b,4.0);
	Edge<Double, String> ae = Graphs.undirectedEdge(a,e,4.0);
	Edge<Double, String> ac = Graphs.undirectedEdge(a,c,2.0);
	Edge<Double, String> bc = Graphs.undirectedEdge(b,c,1.0);
	Edge<Double, String> ce = Graphs.undirectedEdge(c,e,5.0);
	Edge<Double, String> cd = Graphs.undirectedEdge(c,d,3.0);
	Edge<Double, String> ef = Graphs.undirectedEdge(e,f,1.0);
	Edge<Double, String> df = Graphs.undirectedEdge(d,f,2.0);

	@Test
	public void testRunAlgorithm() {
		edges = new HashSet<Edge<E, V>>((Collection<? extends Edge<E, V>>) Arrays.asList(e1,e2,e3,e4,e5,e6,e7,e8,e9,e10));
		vertices = new HashSet<Vertex<V>>((Collection<? extends Vertex<V>>) Arrays.asList(v1,v2,v3,v4,v5,v6));
		graph = GraphImpl.valueOf(edges, vertices);
		DijkstraAlgorithm result = DijkstraAlgorithm.valueOf(graph);
		result.runAlgorithm(v1, v4);
		List expectedResult = Arrays.asList(v1,v6,v3,v4);
		
		assertEquals(expectedResult,result.shortestPath());
	}
	
	@Test
	public void testRunAlgorithmSecondGraph() {
		edges = new HashSet<Edge<E, V>>((Collection<? extends Edge<E, V>>) Arrays.asList(ab,ae,ac,bc,ce,cd,ef,df));
		vertices = new HashSet<Vertex<V>>((Collection<? extends Vertex<V>>) Arrays.asList(a,b,c,d,e,f));
		graph = GraphImpl.valueOf(edges, vertices);
		DijkstraAlgorithm result = DijkstraAlgorithm.valueOf(graph);
		result.runAlgorithm(a, d);
		List expectedResult = Arrays.asList(a,c,d);
		
		assertEquals(expectedResult,result.shortestPath());
		
		List expectedFalseResult = Arrays.asList(a,c,e);
		assertNotSame(expectedFalseResult, result.shortestPath());

	}

}
