package server;

public class Arco  {
	
 private Object node1, node2;
 private Object value;

 public Arco() {
   node1 = node2 = null;
   value = null;
 }
 public Arco(Object x1, Object y1, Object v) {
   node1 = x1;
   node2 = y1;
   value = v;
 }
 public Object getNode1() {
	 return node1; 
 }
 
 public Object getNode2() { 
	 return node2; 
 }
 public Object getValue() { 
	 return value; 
 }
 	public boolean equals(Object a) {
 		if (a instanceof Arco) {
 			Arco arc = (Arco) a;
			return (node1.equals(arc.node1) && node2.equals(arc.node2));		
 		}
 	return false;
 	}

 	public int hashCode() {
	 return node1.hashCode()+node2.hashCode()+value.hashCode();
 	}

 	public String toString() {
 		return "<"+node1.toString()+", "+node2.toString()+"; "+value.toString()+">";
 	}
}