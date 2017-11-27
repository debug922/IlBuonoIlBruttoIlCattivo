package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;



public class ThreadClient implements Runnable  {
	
	private static  int userid;
	private final int  id;
	private Output out;
	private ObjectInputStream in;
	private Socket client;
	private Player player;
	private static Object sync,async,id1;
	private static int count,turno,dl,disconnected;
	private static ConcurrentHashMap<Player,String> hash;
	private static ConcurrentHashMap<String,Integer> sq;	
	private static boolean end,partita;
	private static Renegade renegade;
	private static String duello;
	private static Graph graph;
	static{
		disconnected=-1;
		partita=true;
		graph=new Graph();
		dl=-1;
		duello=new String();
		renegade= new Renegade();
		hash= new ConcurrentHashMap<Player,String>();		
		sync=new Object();
		async= new Object();
		id1=new Object();
		sq=new ConcurrentHashMap<String,Integer>();
		sq.put("bianchi", 0);
		sq.put("neri",0);
	}
	public void restart(){
		end=false;
		disconnected=-1;
		partita=true;
		graph=new Graph();
		dl=-1;
		duello=new String();
		renegade= new Renegade();
		hash= new ConcurrentHashMap<Player,String>();		
		sync=new Object();
		async= new Object();
		id1=new Object();
		sq=new ConcurrentHashMap<String,Integer>();
		sq.put("bianchi", 0);
		sq.put("neri",0);
		count=turno=userid=0;
	}
	public ThreadClient(Socket client) {
		// TODO Auto-generated constructor stub
		this.client=client;
		synchronized(id1){
			id=userid++;
		}
		player=new Player(id);
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try { 
			if(!client.equals(null)){
				client.setSoTimeout(40000);
				out= new Output(client.getOutputStream());
				//out= new ObjectOutputStream(client.getOutputStream());
				in= new ObjectInputStream(client.getInputStream());
				if(partita){
					connetti();
					turno();
					end();
				}else{
					occupato();	
				}	
				out.close();
				in.close();
			}
	         System.out.println("ho terminato l'esecuzione");
	         
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		/*	if(e instanceof SocketTimeoutException){
				System.out.println("okokokok");
				
			}*/
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	private void occupato() throws IOException{
		Update update =new Update();
		update.SetError();
		update.setMessage("partita in corso");
		out.writeObject(update);
		out.flush();
	}
	
	
	private void connetti() throws ClassNotFoundException, IOException{
		out.writeObject(new Update());
		String s= new String();
		for(int i=0;i<2;i++){
			s=(String)in.readObject();
			if(!s.isEmpty()){
				if(i==0)
					player.setNickname(s);
				else
					player.setSquadra(s);
			}
					
		}
		if(player.getSquadra().equals("bianchi"))
			add(player, "Bodie");
		else
			add(player,"Tucson");
		System.out.println("nickname di "+id+" è "+player.getNickname()+" sq= "+player.getSquadra()+"player.getId="+player.getId());
		countok();
		System.out.println("Ciao, sono " + Thread.currentThread().getName()
		+ " ed sono uscito dal waiting");
		Update update=new Update();
		update.setMappa(true);
		update.setCount(count);
		update.setUpdate(true);
		LinkedList<String> list =new LinkedList<String>();
		list=getAll();
		update.setList(list);
		out.writeObject(update);
		out.flush();
		System.out.println("ho inviato update = "+ Thread.currentThread().getName());
	}
	
	
	
	private  void countok() {
		synchronized (sync) {

			if(count==0)
				try {
					sync.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
	
	}
	
	private void setGraph(int count){
		//Integer.valueOf(ThreadLocalRandom.current().nextInt(1, 10))
		switch (count) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			graph.add("Bodie", "Calico",Integer.valueOf(ThreadLocalRandom.current().nextInt(1, 5)),Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10))
					,Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10)));
			graph.add("Calico", "Bodie",Integer.valueOf(ThreadLocalRandom.current().nextInt(1, 5)),Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10))
					,Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10)));
			graph.add("Calico", "ElPaso",Integer.valueOf(ThreadLocalRandom.current().nextInt(1, 5)),Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10))
					,Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10)));
			graph.add("ElPaso", "Calico",Integer.valueOf(ThreadLocalRandom.current().nextInt(1, 5)),Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10))
					,Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10)));
			graph.add("ElPaso", "Jefferson",Integer.valueOf(ThreadLocalRandom.current().nextInt(1, 5)),Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10))
					,Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10)));
			graph.add("Jefferson", "ElPaso",Integer.valueOf(ThreadLocalRandom.current().nextInt(1, 5)),Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10))
					,Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10))); 
			graph.add("Jefferson", "Tucson",Integer.valueOf(ThreadLocalRandom.current().nextInt(1, 5)),Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10))
					,Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10)));
			graph.add("Tucson", "Jefferson",Integer.valueOf(ThreadLocalRandom.current().nextInt(1, 5)),Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10))
					,Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10)));
			graph.add("CanyonDiablo", "Tucson",Integer.valueOf(ThreadLocalRandom.current().nextInt(1, 5)),Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10))
					,Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10)));
			graph.add("Tucson", "CanyonDiablo",Integer.valueOf(ThreadLocalRandom.current().nextInt(1, 5)),Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10))
					,Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10)));
			graph.add("Bodie", "CanyonDiablo",Integer.valueOf(ThreadLocalRandom.current().nextInt(1, 5)),Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10))
					,Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10)));
			graph.add("CanyonDiablo", "Bodie",Integer.valueOf(ThreadLocalRandom.current().nextInt(1, 5)),Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10))
					,Integer.valueOf(ThreadLocalRandom.current().nextInt(4, 10)));
			break;

		default:
			break;
		}
	}
	
	
	
	public  void set (int count1){
		synchronized(sync){
			partita=false;
			ThreadClient.count=count1;
			setGraph(count1);
			sync.notifyAll();
		}
	}

	private void turno() throws InterruptedException, IOException, ClassNotFoundException{
		boolean timeOut=false;
		String posizione;
		Integer punti=0;
		while(true){
			
			synchronized (async) {
				//if dl==-1 continue altrimenti non deve entrare  in turno ma stoppare il thread
				if(dl==-1){
					if(end){
						System.out.println("end"+Thread.currentThread().getName());
						async.notifyAll();
						break;
					}
				
					if(turno==count){
						turno=0;
					}
				
					if(turno==id){
						if(!timeOut){
							System.out.println("sono in turno==id"+Thread.currentThread().getName());
							LinkedList<String> list= new LinkedList<String>();
							Update update= new Update();
							update.setTurno(true);
							update.setUpdate(true);
							list=getAll();
							update.setList(list);
							update.setPunti(sq.get(player.getSquadra()));
							out.writeObject(update);	
							out.flush();
							System.out.println("ho inviato l'update di turno "+Thread.currentThread().getName());
							try{
								update=(Update)in.readObject();
						
							}catch(Exception e){
								if(e instanceof java.net.SocketTimeoutException){
									System.out.println("hhhhhhhhhhhhhhhhhhhhhhh");
									timeOut=true;
									continue;
								}
								else if(e instanceof java.net.SocketException){
									System.out.println("ooooooooooooooooooooooooooooooooooooooooooooo");
									disconnected=id;
									end=true;
									async.notifyAll();
									break;
								}
								else
									e.printStackTrace();						
							}
						//verifico posizione
							posizione=update.getPosizione();
							System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
							System.out.println("posizione="+update.getPosizione()+"  "+Thread.currentThread().getName());
							if(((punti=graph.isAdjacent(hash.get(player),posizione))==-1)){
								timeOut=true;
								System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
								continue;
							}
							System.out.println("subito dopo punti=graph.isAdjacent(hash.get(player),posizione))==-1)");
							punti=-punti;
							punti+=graph.pesoNodo(posizione);
							System.out.println("fanculo nodo"+graph.pesoNodo(update.getPosizione())+" arco="+graph.isAdjacent(hash.get(player),update.getPosizione())
								+"punti attuali="+punti+"putni sq="+sq.get(player.getSquadra())+"playerPunti="+player.getPunti());
							hash.replace(player, posizione);
							player.addPunti(punti);
						}else{
							System.out.println("else TimeOut ");
							posizione=hash.get(player);
							punti=-5;
							player.addPunti(punti);
						}
						timeOut=false;	
						Update u=new Update();	
						if(posizione.equals(renegade.moveRenegade())){
							int a=renegade.theftRenegade(player.getPunti());
							System.out.println("il rinnegato si trovava qui  ha inflitto -"+a);
							u.setRenegade("il rinnegato si trovava qui  ha inflitto -"+a);
							punti-=a;
							player.addPunti(-a);
						}
						System.out.println("renegade========================"+renegade.getCurrentPos());
						sq.replace(player.getSquadra(),sq.get(player.getSquadra())+punti);
						System.out.println("punti attuali="+punti+"putni sq="+sq.get(player.getSquadra())+"playerPunti="+player.getPunti());
						LinkedList<String> l=new LinkedList<String>();
						u.setUpdate(true);
						if((dl=anyoneHere(posizione))!=-1  ){
							u.setDuello(true);
							u.setWinner(new String(duello));
							System.out.println("1l1l1l1l1ll11ll11m1km1mn1k1nk11kmk1n1k1km1km1km1k1m1m1mkm1km1m11k1m1m1mm1kmk1mk1mk1m1mk1m1k");
							System.out.println("dl="+dl+" id="+id+"  "+Thread.currentThread().getName()+" "+player.getId());
						}
						
						l=getAll();
						u.setList(l);
						u.setPunti(sq.get(player.getSquadra()));
						//fa altro
						out.writeObject(u);
						out.flush();
						turno++;
						async.notifyAll();
					}
					else{
						System.out.println("else  waiting"+Thread.currentThread().getName());
						
						async.wait();
						update();				
						//invio lista
					}
						
			}else{
				if(id==dl){
					System.out.println("dlldldldldlldldldldldldldldlldldldldldldlldldldldldldlldldldldldldldldll");
					System.out.println("dl="+dl+" id="+id+"  "+Thread.currentThread().getName());
					LinkedList<String> list=new LinkedList<String>();
					Update update=new Update();
					update.setUpdate(true);
					list=getAll();
					update.setList(list);
					update.setPunti(sq.get(player.getSquadra()));
					update.setDuello(true);
					update.setWinner(new String(duello));
					out.writeObject(update);
					out.flush();
					dl=-1;
					async.notifyAll();
				}else
					async.wait();
				
				}		
			}
		}
	}
	
	private void update() throws IOException{
		System.out.println("sono uscito dal waiting id= "+id+" thread="+Thread.currentThread().getName());
		LinkedList<String> list=new LinkedList<String>();
		Update update=new Update();
		update.setUpdate(true);
		list=getAll();
		update.setList(list);
		update.setPunti(sq.get(player.getSquadra()));
		out.writeObject(update);
		out.flush();

	}
	
	
	private  LinkedList<String> getAll(){
		LinkedList<String>list= new LinkedList<String>();
		String s;
		Set<Player> key= hash.keySet();
		for(Player p:key){
			s=p.getSquadra()+": "+p.getNickname()+": "+hash.get(p)+" "+p.getPunti();
			list.add(s);
		}	
		return list;
	}
	
	public  void add(Player player,String nodo){
		hash.put(player, nodo);
	}

	private int anyoneHere(String posizione){
		String s=player.getSquadra();
		Set<Player> key= hash.keySet();
		for(Player p: key)
			if(posizione.equals(hash.get(p)) && !(s.equals(p.getSquadra()))  ){
				lancio(p);
				System.out.println("p.id="+p.getId()+"player.id="+player.getId());
				return p.getId();
			}
				
		
		return -1;		
	}
	
	public void endRound(){
		System.out.println("endRound	"+Thread.currentThread().getName());
		end=true;
	}
	
	private void  end() throws IOException{
		System.out.println("end		"+Thread.currentThread().getName());
		if(disconnected!=id){
			Update update = new Update();
			int puntiB=sq.get("bianchi"),puntiN=sq.get("neri");
		
			if(puntiB>puntiN)
				update.setRound(true," vittoria bianchi\n punti="+sq.get("bianchi")+"\n neri="+sq.get("neri")+"\n");
			else if(puntiB<puntiN)
				update.setRound(true," vittoria neri\n punti="+sq.get("bianchi")+"\n bianchi="+sq.get("neri")+"\n");
			else 
				update.setRound(true,"parità "+sq.get("bianchi")+"=="+sq.get("neri")+"\n");
			
			out.writeObject(update);
			out.flush();
		}
	}
	
	private void lancio(Player p){
		int[] dadi= new int[6];
		int n=0,count1=0,count2=0;
		for (int i=0; i<6;i++){
			dadi[i]=ThreadLocalRandom.current().nextInt(1,6);
			n+=dadi[i];
		}	
		insertionSort(dadi, 0, 3);
		insertionSort(dadi, 3,6);
		duello="esito duello:\n player: "+player.getNickname()+" ha: "+dadi[0]+","+dadi[1]+","+dadi[2]+
				 "\n player: "+p.getNickname()+" ha: "+dadi[3]+","+dadi[4]+","+dadi[5]+"\n";

		for(int i=0,j=3; i<3 && j<6;i++,j++){
			if(dadi[i]>dadi[j]){
				count1++;
				System.out.println("dadi[i]="+dadi[i]+"con i="+i+" dadi[j]="+dadi[j]+" con j="+j+"count="+count1);
			}
			if(dadi[i]<dadi[j]){
				count2++;
				System.out.println("dadi[i]="+dadi[i]+"con i="+i+" dadi[j]="+dadi[j]+" con j="+j+"count="+count2);
			}
		}
		System.out.println("count1="+count1+" count2="+count2);
		if(count1>count2)
			plusMinus(player, p, n);
		if(count1<count2)
			plusMinus(p, player, n);
		if(count1==count2){
			if(player.getPunti()>p.getPunti())
				plusMinus(player, p, n);

			else if(player.getPunti()<p.getPunti())
				plusMinus(p, player, n);
			
			else if(player.getPunti()==p.getPunti())
				duello+="parità\n";
			
		}
		
	}
	private void plusMinus(Player p1,Player p2,int n){
		p1.addPunti(n);
		p2.addPunti(-n);
		sq.replace(p1.getSquadra(),sq.get(p1.getSquadra())+n);
		sq.replace(p2.getSquadra(),sq.get(p2.getSquadra())-n);
		duello+="duello vinto da "+p1.getNickname()+" punti= "+n+"\n";
		System.out.print(duello);
	}
	
	
	private  void insertionSort(int[] array ,int min ,int max) {
	     int j;
	     for (int i = min+1; i <max; i++) {
	        int tmp = array[i];
	        for (j = i - 1; (j >= min) && (array[j] < tmp); j--) {
	            array[j + 1] = array[j];
	        }
	        array[j + 1] = tmp;  
	     }
	}
}
