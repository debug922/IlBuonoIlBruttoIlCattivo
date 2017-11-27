package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import server.Mappa;
import server.Update;

public class Worker extends SwingWorker<Update,Update>{
	private Client frame;
	private String address,nickname,squadra,posizione;
	private Socket socket;
	private int port;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Mappa mappa ;
	private boolean aspetta,timeOut,continua;
	private Timer t;
	
	public Worker(Client frame, String address,int port,String nickname,String squadra) {
		// TODO Auto-generated constructor stub
		this.frame=frame;
		this.address=address;
		this.port=port;
		this.nickname=nickname;
		this.squadra=squadra;
		mappa=new Mappa();
		posizione=new String();
	}
	@Override
	protected Update doInBackground() throws Exception {
		System.out.println("Ciao, sono " + Thread.currentThread().getName()
				+ " ed lavoro in background");
		if(squadra.equals("bianchi"))
			posizione="Bodie";
		else
			posizione="Tucson";
		
		try
		{	
			
		// open a socket connection
			socket=new Socket();
			socket.connect(new InetSocketAddress(address,port),30000);
			out = new ObjectOutputStream(socket.getOutputStream());
			in= new ObjectInputStream(socket.getInputStream());

		}catch (ConnectException e) {
			if (e instanceof java.net.ConnectException){
				Update update = new Update();
				update.SetError();
				update.setMessage("Connection refused");
				publish(update);
			}else
			// TODO: handle exception
			e.printStackTrace();
			
		}catch(Exception e){
			if(e instanceof java.net.SocketTimeoutException  ){
				Update update = new Update();
				update.SetError();
				update.setMessage("connect timed out");
				publish(update);
				
			}else
				e.printStackTrace();
	
		}
		Update u=(Update)in.readObject();
		if(u.getErorr()){
			publish(u);
		}else{
		
		out.writeObject(nickname);
		out.writeObject(squadra);
		out.flush();		
		int i=0;
		boolean round=false;
		do{
			Update update = new Update();
			System.out.println("i++="+ ++i);
			if(aspetta){
				synchronized (this) {
					System.out.println("aspetto posizione");
					wait();
					System.out.println("sono uscito dal wait");
					aspetta=false;
					if(!timeOut){
						System.out.println("sto Inviando");
						update.setPosizione(posizione);
						out.writeObject(update);
						out.flush();
					}else
						timeOut=false;
				}
			}
			System.out.println("sto leggendo");
			update=(Update)in.readObject();
			round=update.getRound();
			if(update.getTurno()){
				aspetta=true;
				System.out.println("setto aspetta=true");
			}
			if(round){
				System.out.println("  		  "+update.getWinner());
		//		break;
			}	
			publish(update);
			System.out.println("fuckBackground");	
		}while(!round);
		}
		
		System.out.println("sono uscito dal while ");
		return null;
	}

	protected void process(List<Update> list) {
		System.out.println("fuck");
	
		for (Update u: list){
			if(u.getRound()){
				frame.chat.append(u.getWinner());
				frame.campoTesto.setEnabled(true);
				frame.port.setEnabled(true);
				frame.address.setEnabled(true);
				frame.squadra.setEnabled(true);
				frame.posizione.setEnabled(false);
				frame.posizione.setText("");
				frame.punti.setText("");
				Timer t1= new Timer();
				t1.schedule(new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						System.out.println("Sono nel run timer1");
							SwingUtilities.invokeLater(new Runnable() {
								
								@Override
								public void run() {
									frame.chat.setText("");
									frame.start.setEnabled(true);
									// TODO Auto-generated method stub
									
								}
							});
					}
				}, 35000);

				
				
				
			}
			if(u.getErorr()){
				frame.error.setText(u.getMessage());
				frame.campoTesto.setEnabled(true);
				frame.port.setEnabled(true);
				frame.address.setEnabled(true);
				frame.start.setEnabled(true);
				frame.squadra.setEnabled(true);
			}
			
			if(u.getMappa()){
				int count=u.getCount();
				System.out.println("count=="+count);
				mappa.setMappa(count);
				frame.setPanel(mappa.getmx());
				frame.revalidate();
				frame.repaint();
			}
			if(u.getTurno()){
				aspetta=true;
				System.out.println("sono getTurno");
				frame.posizione.setEnabled(true);
				continua=true;
				t= new Timer();
				t.schedule(new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						System.out.println("Sono nel run timer1");
						if(continua){
							timeOut=true;
							SwingUtilities.invokeLater(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									frame.posizione.setEnabled(false);

								}
							});
							setPosizione(null);
						}
					}
				}, 35000);
				
			}

			if(u.getUpdate()){
				frame.chat.setText("");
				if(u.getTimeOut())
					frame.chat.append(u.getMessage());
					
				if(u.getDuello()){
					//frame.chat.setText("");
					System.out.println("okokokokokokokokokokokookokkokokokokokokokokookokokokok");
					System.out.println(u.getWinner());
					frame.chat.append(u.getWinner());
				
				}
				if(u.getRinnegato()){
					frame.chat.append(u.getRenegade());
					System.out.println("fuckRenegade");
				}	
				System.out.println("getUpdate");
	//			frame.areaTesto.setText("");
				LinkedList<String>listU=new LinkedList<String>();
				listU=u.getList();
				System.out.println("fuck    u.getPunti()");
				frame.punti.setText(Integer.toString(u.getPunti()));
				for(String m: listU){
					frame.chat.append(m+"\n");
					System.out.print("m=========="+m);
				}
			}		
		}
	System.out.println("Ciao, sono " + Thread.currentThread().getName()
			+ " e non faccio quello che mi è stato chiesto");
	}
	
	
	
	public boolean setPosizione(String posizione){
		synchronized (this) {
					
			System.out.println("sono in setPosizione");
			if(timeOut){
				continua=false;
				notify();
				return true;
			}
			System.out.println(this.posizione+" isAdjacent "+posizione);
			if(mappa.isAdjacent(this.posizione, posizione)){
				t.cancel();
				continua=false;
				this.posizione=posizione;
				notify();
				return true;
			}else{
				System.out.println("error");
			}
		}
		return false;
	}
	
	@Override
	protected void done() {	
	}
	
}
