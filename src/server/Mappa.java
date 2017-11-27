package server;

import java.io.Serializable;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class Mappa   implements Serializable
{
	//java.io.NotSerializableException mxGraoh non serializable quindi non funziona un cazzo
	private mxGraph  graph ;

	//creazione mappa statica o dinamica idNodo==NomeNodo
	private static final long serialVersionUID = -2707712944901661771L;
	
	public 	Mappa(){
		graph=new mxGraph(); 
	}
	
	public  void setMappa(int x){
		
		switch(x){
		case 0:
			
		break;
		case 1:
		case 2:
		case 3:
		case 4:	
			Object parent = graph.getDefaultParent();
			graph.getModel().beginUpdate();
			try{
				Object bodie = graph.insertVertex(parent,null, "Bodie", 20, 75, 80,30,"ROUNDED;strokeColor=red;fillColor=green");
				Object calico = graph.insertVertex(parent,null, "Calico", 130, 20, 80,30);
				Object elPaso = graph.insertVertex(parent,null, "ElPaso", 260, 20,80, 30);
				Object canyonDiablo = graph.insertVertex(parent,null, "CanyonDiablo", 35, 170, 80, 30);
				Object tucson = graph.insertVertex(parent,null, "Tucson", 380, 170, 80, 30,"ROUNDED;strokeColor=red;fillColor=green");
				Object jefferson = graph.insertVertex(parent,null, "Jefferson", 400, 30, 80, 30);	
				graph.insertEdge(parent,null, "Edge1", bodie, calico);
				graph.insertEdge(parent,null, "Edge1", calico, bodie);
				graph.insertEdge(parent,null, "Edge2", calico, elPaso);
				graph.insertEdge(parent,null, "Edge2", elPaso, calico);
				//graph.insertEdge(parent, a.toString(), "Edge3", calico, tucson);
				graph.insertEdge(parent,null, "Edge3", tucson, calico);
				graph.insertEdge(parent,null, "Edge4", canyonDiablo, bodie);
				graph.insertEdge(parent,null, "Edge4", bodie, canyonDiablo);
				graph.insertEdge(parent,null, "Edge5", canyonDiablo, elPaso);
				graph.insertEdge(parent,null, "Edge5", elPaso, canyonDiablo);
				graph.insertEdge(parent,null, "Edge6", tucson, canyonDiablo);
				graph.insertEdge(parent,null, "Edge6", canyonDiablo, tucson);
				graph.insertEdge(parent,null, "Edge7", tucson, jefferson);
				graph.insertEdge(parent,null, "Edge7", jefferson, tucson);
				graph.insertEdge(parent,null, "Edge8", elPaso, jefferson);
				graph.insertEdge(parent,null, "Edge8", jefferson, elPaso);
			}
			finally
			{
				graph.getModel().endUpdate();
			}

		break;
		default:
		break;

		}
		graph.setCellsMovable(false);
		graph.setCellsEditable(false);
		graph.setCellsBendable(false);
		graph.setCellsResizable(false);
		graph.setEdgeLabelsMovable(false);
		graph.setCellsDisconnectable(false);
		graph.setAllowDanglingEdges(false);		
		
	}
		
	private mxCell isInVertex(String s){
		Object[] c = graph.getChildVertices(graph.getDefaultParent());
		for(int i=0; i<c.length; i++){
			mxCell n =(mxCell) c[i];
			if(n.isVertex() && n.getValue().toString().equals(s)){
				return n;
			}
		}
		return null;
	}	
	
	public boolean isAdjacent (String s1, String  s2){
		mxCell n1 = isInVertex(s1);
		mxCell n2 = isInVertex(s2);
		
		if(n1 != null && n2 != null){
			Object[] c = graph.getEdgesBetween(n1, n2);
			if(!c.equals(null) || c.length != 0){
				for(Object cc : c){
					mxCell v =(mxCell) cc;
					System.out.println("");
					if(v.isEdge()){
						return true;
					}
				}	
			}
		}
		return false; 
	}
	
	public mxGraph getGraph(){
		return graph;
	}
	
	public mxGraphComponent getmx(){
		return new mxGraphComponent(graph);	
	}
}