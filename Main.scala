object Main {
  def main(args : Array[String]) : Unit = {
    val v = new VertexImpl(1)
    val w = new VertexImpl(2)
    val e = new EdgeImpl(v, w, "EDGE")
    val g = new GraphImpl(List(v,w), List(e))
    
    println(g)
  }
}

trait Graph[V, E]
trait Vertex[V]
trait Edge[E, V]

case class GraphImpl[V, E](vertices : List[Vertex[V]], edges : List[Edge[E, V]]) extends Graph[V, E]
case class VertexImpl[V](content : V) extends Vertex[V] 
case class EdgeImpl[E, V](left : Vertex[V], right : Vertex[V], content : E) extends Edge[E, V] 