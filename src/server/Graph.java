package server;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Graph {
  HashMap<Object,Set<Arco>> nodi;
  HashMap<Object,Object> pesoNodi;
  int nArchi;
  
  public Graph() {
	pesoNodi= new HashMap<Object,Object>(); 
    nodi = new HashMap<Object,Set<Arco>>();
    nArchi = 0;
  }
  public int nodesNumber() {
    return nodi.size();
  }
  public int edgesNumber() {
    return nArchi;
  }
  
  public void add(Object x,Object value) {
    if (!nodi.containsKey(x)) {
      Set<Arco> lista = new HashSet<Arco>();
      nodi.put(x,lista);
      pesoNodi.put(x, value);
    }
  }
  public boolean add(Object x, Object y, Object value,Object valuex,Object valuey) {
 
    if (!nodi.containsKey(x)){
    	add(x,valuex);
    }
    if (!nodi.containsKey(y)){
    	add(y,valuey);
    }
      
    Arco a = new Arco(x,y,value);
    if ( ((nodi.get(x) ).add(a)) && ((nodi.get(y) ).add(a)) ){
    	 nArchi++;
    	 return true;
    }
    return false;
  }
    public Set<Arco> getEdgeSet() {
    Set<Arco> setArchi = new HashSet<Arco>();
    Iterator<Set<Arco>> hashSetI = nodi.values().iterator();
    while (hashSetI.hasNext())
      setArchi.addAll(hashSetI.next());

    return setArchi;
  }

  public Set<Arco> getEdgeSet(Object nodo) {
    if (nodi.containsKey(nodo)) //se il nodo e' presente nel grafo
      return nodi.get(nodo);
    else
      return null;
  }
  public Set<Object> getNodeSet() {
    return nodi.keySet();
  }

  public String toString() {
    StringBuffer out = new StringBuffer();
    Object nodo;
    Arco a;
    Iterator<Arco> arcoI;
    Iterator<Object> nodoI = nodi.keySet().iterator();
    while (nodoI.hasNext()) {
      arcoI = nodi.get( nodo = nodoI.next() ).iterator();
      out.append("Nodo " + nodo.toString() + ": ");
      while (arcoI.hasNext()) {
        a = (Arco)arcoI.next();
        //out.append( ((a.x == nodo ) ? a.y.toString() : a.x.toString()) + "("+a.value.toString()+"), ");
        out.append(a.toString()+", ");
      }
      out.append("\n");
    }
    return out.toString();
  }
  public Integer isAdjacent (Object x,Object y){
	  Arco arco=new Arco(x,y,0);
	  Set<Arco> a= getEdgeSet(x);
	  if(!a.equals(null))
	  	for(Arco i:a){
	  		 if(i.equals(arco))
	  		//if(y.equals(i.getNode2()))
	  			return (Integer) i.getValue();
	  }
	  return -1;
  }
  
  public Integer pesoNodo(Object x){
	  return (Integer) (pesoNodi.get(x));
  }
}
