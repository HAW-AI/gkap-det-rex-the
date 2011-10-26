# Graphentheoretische Konzepte und Algorithmen

## ADT

###Type Graph
####Literals
+ emptyGraph
+ vollständigGraph(beim erzeugen des vollstä. 
Graphen muss ein default wert übergeben werden)
+ zugrundeliegenderUngerichteterGraph
+ gerichteterGraph
+ matrizen

####Operations
**Graph**: List<Vertex> x List<Edge> -/-> Graph

**ausgabe** -- (graphviz, toString)

####Axioms:

###Type Vertex

####Literals
+ **grad**:
+ **adjazent**:
+ **inzident**:
+ **content**:

####Operations
**Vertex**: T ---> Vertex<T>

####Axioms:
Vertex<T>.content == T

###Type Edge
####Literals
+ **content**:

####Operations
**Edge**: Vertex<T> x U ---> Edge<T,U>;
Vertext<T> x Vertex<T> x U ---> Edge<T,U>

####Axioms
Vertex<T> x U ---> Edge<T,U>.content ---> U

Vertex<T> x Vertex<T> x U ---> Edge<T,U>.content ---> U