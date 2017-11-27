package server;

import java.io.Serializable;
import java.util.LinkedList;

public class Update implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2669195839920867214L;	
	private  int count, punti;
	private boolean mappa,duello,turno,update,fine,rinnegato,error,timeout;
	private String posizione,mess,winner,renegade;
	private LinkedList<String> list;
	public Update() {
		// TODO Auto-generated constructor stub
		list=new LinkedList<String>();
		renegade=mess=null;
	}
	public void setWinner(String winner){
		this.winner=winner;
	}
	
	public void setMessage(String mess){
		this.mess=mess;
	}
	public String getMessage(){
		return mess;
	}
	public boolean getRound(){
		return fine;
	}
	public String getWinner(){
		return winner;
	}
	public void  setRound(boolean fine,String winner){
		this.fine=fine;
		this.winner=winner;
	}
	public void setPosizione(String posizione){
		this.posizione=posizione;
		
	}
	public int getPunti(){
		return punti;
	}
	
	public String getPosizione(){
		return posizione;
	}
	
	public void setMappa(boolean mappa){
		this.mappa=mappa;
	}
	public boolean getMappa(){
		return mappa;
	}
	public  int getCount(){
		return count;
	}
	public  void setCount(int count){
		this.count=count;
	}	
	public  boolean getUpdate(){
		return update;
	}
	public void setList(LinkedList<String> list){
		this.list=list;
	}
	
	public  LinkedList<String> getList(){
		return list;
	}
	public void setUpdate(boolean a){
		update=a;
	}
	public void setTurno(boolean turno){
		this.turno=turno;
	}
	public boolean getTurno(){
		return turno;
	}
	public void setDuello(boolean duello){
		this.duello=duello;
	}
	
	public boolean getDuello(){
		return duello;
	}
	public void setPunti(int punti){
		this.punti=punti;
	}
	public void  setRenegade(String renegade){
		this.renegade=renegade;
		rinnegato=true;
	}
	public String getRenegade(){
		return renegade;
	}
	public boolean getRinnegato(){
		return rinnegato;
	}
	public void SetError(){
		error=true;
	}
	public boolean getErorr(){
		return error;
	}
	public void setTimeOut(){
		timeout=true;
	}
	public boolean getTimeOut(){
		return timeout;
	}
}
