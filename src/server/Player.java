package server;

public class Player {
	
	private final int  id;
	private String nickname;
	private String squadra;
	private int punti;

	public Player(int id, String nickname, String squadra ){
		this.punti=0;
		this.id=id;
		this.nickname=nickname;
		this.squadra=squadra;
	}
	
	
	public Player(int id){
		this.id=id;
		
	}
	
	public void setSquadra(String squadra){
		this.squadra=squadra;
		
	}
	
	public String getSquadra(){
		return squadra;
	}
	public void  setNickname(String nickname){
		this.nickname=nickname;
	}
	
	public String getNickname(){
	
		return nickname;
	}
	
	public int getId(){
		
	return id;	
		
	}
	
	public void addPunti(int punti1){
		punti+=punti1;
		
	}
	
	public int getPunti(){
		
		return punti;
		
	}
	
}
