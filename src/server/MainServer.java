package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

 class MainServer {
	private final int countMax=10;
	private Socket connection;
	private ExecutorService pool;
	private ServerSocket ss;
	private int count; //numero di giocatori attuali
//	private boolean ready;
	private Ascolto ascolto;
	private Timer tCount;
	
	public MainServer(){
		pool = Executors.newFixedThreadPool(15);
	//	ready=true;
		ascolto=new Ascolto();
	}

	private class  Ascolto implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
				
				while(true){
					System.out.println("sono in ascolto");
					ready();
					try {
						connection=ss.accept();
						}
							
					 catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					count++;
				ThreadClient tc= new ThreadClient(connection);
				pool.execute(tc);
			}
		}
	}
	public void listen(int port ) throws InterruptedException{
		if(port<1024 || port>65535){
			System.out.println(port);
			throw new IllegalStateException();
		}
		try {
			ss=new ServerSocket(port);
			System.out.println("server in ascolto "+port);
			new Thread(ascolto).start();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		while(true){
			synchronized(this){
				//while(ready)
					this.wait();
					System.out.println("	wait mainserver");
				}
			System.out.println("uscito dal while in mainserver");
			
			
			Timer t= new Timer();
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
				
					(new ThreadClient(null)).endRound();
					System.out.println("endTimer  "+Thread.currentThread().getName());
					wakeup();
				}
			}, 180000);

			synchronized(this){
				this.wait();
				System.out.println("	wait1 mainserver");
			}
			Timer t1= new Timer();
			t1.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					count=0;
					(new ThreadClient(null)).restart();
					System.out.println("endTimerRestart "+Thread.currentThread().getName());
					//wakeup();
				}
			}, 45000);
		}	
	}
	
	
	private void wakeup(){
		
		synchronized(this){
			notify();
			System.out.println("sono nel notify del mainserver");
			
		}
	}
	private void ready(){
		if(count==countMax){
			tCount.cancel();
			wakeup();
			(new ThreadClient(null)).set(count);
		}
		if(count==1){
			//start timer
			
			tCount= new Timer();
			tCount.schedule(new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("Sono nel run timer1");
				//	ready=false;
					wakeup();
					(new ThreadClient(null)).set(count);
				}
			}, 10000);
			
			//creo l oggetto mappa ed lo passo al ThreadClient	
		}	
	}	
	public static void main(String[] args) throws InterruptedException {
		MainServer m= new MainServer();
		m.listen(9000);
		
	}
}
