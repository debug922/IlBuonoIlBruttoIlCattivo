package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listener implements ActionListener {
	Client frame;
	String nick,adr,portS,squadra;
	int port;
	Worker worker;
	public Listener(){
	
	}
	 public Listener(Client frame) {
		// TODO Auto-generated constructor stub
		 this.frame=frame;
	}
		private boolean checkSquadra(String s){
			if(!s.isEmpty()){
				s=s.toLowerCase();
				if(s.equals("bianchi") || s.equals("neri"))
					return false;
			}
			
			return true;
		}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Ciao, sono " + Thread.currentThread().getName()
				+ " ed ascolto");
		String command = e.getActionCommand();
	switch (command) {
			case "nickname":
			case "address":	
			case "port":	
			case "squadra":			
				frame.error.setText("Inserire valori");
			break;
			case "start":
				nick=frame.campoTesto.getText();
				adr=frame.address.getText();
				portS=frame.port.getText();
				squadra=frame.squadra.getText();
				port=Integer.parseInt(portS);
				if(port<1025 ||port>65535 || adr.isEmpty() || nick.isEmpty()  || checkSquadra(squadra)){
					frame.error.setText("error");
				}else{
					frame.error.setText("ok");
					frame.campoTesto.setEnabled(false);
					frame.port.setEnabled(false);
					frame.address.setEnabled(false);
					frame.start.setEnabled(false);
					frame.squadra.setEnabled(false);
				worker =new Worker(frame, adr,port,nick,squadra);
				worker.execute();	
				}
			break;

			case "posizione":
				//richiamare funzione di worker
				frame.posizione.setEnabled(false);
				String s=frame.posizione.getText();
				if(!s.equals("")){
					if (!worker.setPosizione(s)){
						frame.posizione.setEnabled(true);
						frame.error.setText("error posizione");
					}else
						frame.error.setText("");
				}else
					frame.posizione.setEnabled(true);	
			break;

		default:
			break;
		}				
	}
}