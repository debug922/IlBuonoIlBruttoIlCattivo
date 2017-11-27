package server;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Renegade {

	private HashMap<String, LinkedList<String>> map;
	private String posRenegade;
	
	public Renegade(){
		map = new HashMap<String, LinkedList<String>>();
		switch(Integer.valueOf(ThreadLocalRandom.current().nextInt(1, 6))){
			case 1:
				posRenegade = "Bodie";	break;
			case 2:
				posRenegade = "Calico";	break;
			case 3:
				posRenegade = "ElPaso";	break;
			case 4:
				posRenegade = "Jefferson";	break;
			case 5:
				posRenegade = "Tucson";	break;
			case 6:
				posRenegade = "CanyonDiablo"; break;
		};
		System.out.println("Pos. init Renegade :" + posRenegade);
		setMap();
	}
	
	private void setMap(){
		LinkedList<String> l = new LinkedList<String>();
		l.add("Calico");
		l.add("CanyonDiablo");	
		map.put("Bodie",l);
		l = new LinkedList<String>();
		l.add("Bodie");
		l.add("ElPaso");
		l.add("Tucson");
		map.put("Calico", l);
		//l.clear();
		l = new LinkedList<String>();
		l.add("Bodie");
		l.add("ElPaso");
		l.add("Tucson");
		map.put("CanyonDiablo", l);
		//l.clear();
		l = new LinkedList<String>();
		//System.out.println(map.get("Bodie").size());
		l.add("CanyonDiablo");
		l.add("Jefferson");
		l.add("Calico");
		map.put("ElPaso", l);
		//l.clear();
		l = new LinkedList<String>();
		l.add("ElPaso");
		l.add("Tucson");
		map.put("Jefferson", l);
		//l.clear();
		l = new LinkedList<String>();
		l.add("Jefferson");
		l.add("Calico");
		l.add("CanyonDiablo");
		map.put("Tucson", l);
	}
	
	public String getCurrentPos(){
		return posRenegade;
	}
	
	public String moveRenegade(){
		System.out.println("posizione before move:" + posRenegade + map.get(posRenegade).toString());
		posRenegade = map.get(posRenegade).get(ThreadLocalRandom.current().nextInt(0, map.get(posRenegade).size()-1));
		return posRenegade;
	}
	
	
	public int  theftRenegade(int punti){
		if(punti<=10)
			return ThreadLocalRandom.current().nextInt(1,2);
		
		return (int) (punti*0.1);
	}
}
