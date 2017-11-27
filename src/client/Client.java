package client;

import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import com.mxgraph.swing.mxGraphComponent;
public class Client  extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 JTextField campoTesto;
	 JTextField address;
	 JTextField port;
	 JTextField error;
	 JTextField punti;
	 JTextField squadra;
	 JTextField posizione;
	 JButton start;
	 JPanel panel;
	 JPanel pannelloC;
	 JPanel Apannel;
	// JPanel panelG;
	 JPanel panelChat;
	// JTextArea areaTesto;
	 JTextArea chat;
	public  JPanel getPanel() {
		return panel;
	}
	@SuppressWarnings("deprecation")
	public  void setPanel(mxGraphComponent graphComponent) {
		if(this.panel.countComponents()==0)
			this.panel.add(graphComponent);

		
	}
		public Client(){
		super("Il Buono il Brutto e il Cattivo");
		System.out.println("Ciao, sono " + Thread.currentThread().getName()
				+ " e creo la GUI");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Listener listener= new Listener(this);
		posizione= new JTextField(15);
		posizione.addActionListener(listener);
		posizione.setActionCommand("posizione");
		posizione.setEnabled(false);
		squadra=new JTextField(10);
		squadra.addActionListener(listener);
		squadra.setActionCommand("squadra");
		squadra.setText("bianchi");
		punti=new JTextField(10);
		punti.addActionListener(listener);
		punti.setActionCommand("punti");
		punti.setEnabled(false);
		error=new JTextField(10);
		error.addActionListener(listener);
		error.setActionCommand("error");
		error.setEnabled(false);
		campoTesto= new JTextField(10);
		campoTesto.setActionCommand("nickname");
		campoTesto.setText("fuck");
		campoTesto.addActionListener(listener);
		address =new JTextField(10);
		address.setText("localhost");
		address.setActionCommand("address");
		address.addActionListener(listener);
		port=new JTextField(10);
		port.setActionCommand("port");
		port.setText("9000");
		port.addActionListener(listener);
		start= new JButton("start");
		start.setActionCommand("start");
		start.addActionListener(listener);
		start.setEnabled(true);
		JLabel label = new JLabel("Connetiti: ");
	//	areaTesto = new JTextArea(8,4);
	//	areaTesto.setEditable(false);
	//	JScrollPane scrollCentrale = new JScrollPane(areaTesto);
		Apannel= new JPanel();
		Apannel.setLayout(new GridLayout(3,2));
		//setPanel(new JPanel());
		panel= new JPanel();
		panel.setLayout(new GridLayout(1,1));
		//panelG= new JPanel();
		//panelG.setLayout(new GridLayout(2,1));
	//	panelG.add(new JLabel("giocatori:"));
	//	panelG.add(scrollCentrale);		
		pannelloC= new JPanel();
		pannelloC.setLayout(new GridLayout(11,1));
		pannelloC.add(label);
		pannelloC.add(campoTesto);
		pannelloC.add(squadra);
		pannelloC.add(address);
		pannelloC.add(port);
		pannelloC.add(start);
		pannelloC.add(error);
		pannelloC.add(new JLabel("punti:"));
		pannelloC.add(punti);
		pannelloC.add(new JLabel("posizione:"));
		pannelloC.add(posizione);
		Apannel.add(pannelloC);
	//	Apannel.add(panelG);
		chat = new JTextArea(11,10);
		chat.setEditable(false);
		JScrollPane scrollChat = new JScrollPane(chat);
		panelChat=new JPanel();
		panelChat.setLayout(new GridLayout(1,1));
		//panelChat.add(new JLabel("Notifiche"));
		panelChat.add(scrollChat);
		JSplitPane splitp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,Apannel,panelChat);
		//pannelloC.add(new JLabel("giocatori"));
		JSplitPane split= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,splitp,panel);
		Container frmContentPane = this.getContentPane();
		frmContentPane.add(split);
	//	frmContentPane.add(pannelloC);
		this.setSize(960, 640);
		//this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Runnable init = new Runnable() {
			public void run() {
				new Client();
			}
		};
			// creo la GUI nell’EDT
		SwingUtilities.invokeLater(init);
	}
}
