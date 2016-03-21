
import java.rmi.*;
import java.rmi.registry.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;
public class RmiCliente extends JFrame implements ActionListener, Runnable {
		
	private static final long serialVersionUID = 1L;
		private JLabel Fondo = null;
		private JLabel Cuadro;
		private JButton Listo = null;
		private JLabel estado;
		private JLabel Barra = null;
		private JLabel BarraDos = null;
		private InterfazMensajes rmiServidor;
		private Registry registro;
		private String direccionServidor = "localhost";
		private String puertoServidor = "3232";
		int PosicionesXY [];
		int Jugador = 0;
		int PosY = 240, listo = 0;
		boolean banYa = false, banYb = false;
		
		public RmiCliente() {
			super("Cliente RMI");
			direccionServidor=JOptionPane.showInputDialog("Nombre/IP del servidor:");
			if(direccionServidor.equals(""))
				direccionServidor="localhost";
			conectarseAlServidor();
			getContentPane().setLayout(null);

			Fondo = new JLabel();
			Fondo.setBounds(new Rectangle(0, 0, 640, 480));
			Cuadro = new JLabel();
			ImageIcon icon = new ImageIcon("Punto.png");  
			Cuadro.setIcon(icon);
			
			Barra = new JLabel();
			ImageIcon iconD = new ImageIcon("Barra.png");  
			Barra.setIcon(iconD);
			
			BarraDos = new JLabel();
			ImageIcon iconF = new ImageIcon("Barra.png");  
			BarraDos.setIcon(iconF);
			
			Listo = new JButton();
			Listo.setText("Listo");
			Listo.setBounds(new Rectangle(270, 200, 100, 30));
			Listo.addActionListener(this);
		    
			estado = new JLabel();
			estado.setVisible(true);

			getContentPane().add(Listo,null);
			getContentPane().add(Fondo,null);
			getContentPane().add(Cuadro,null);
			getContentPane().add(Barra,null);
			getContentPane().add(BarraDos,null);
			getContentPane().add(estado, null);
			
			setSize(640, 480);
			setVisible(true);
			
			this.addKeyListener(new KeyAdapter() {
			    public void keyPressed(KeyEvent e) {
			    	if (e.getKeyCode()==38){
			    		banYa = true;
			    	}
			    	if (e.getKeyCode()==40){
			    		banYb = true;
			    	}			       	
			   }
			   
			    public void keyReleased(KeyEvent e) {
			    	if (e.getKeyCode()==38){
				   		banYa = false;
				   	}
				   	if (e.getKeyCode()==40){
				   		banYb = false;
				   	}
			    }
			    });
			  
			}

		private void conectarseAlServidor() {
			try {// obtener el registro
				registro = LocateRegistry.getRegistry(direccionServidor,Integer.parseInt(puertoServidor));
				// creando el objeto remoto
				rmiServidor = (InterfazMensajes) (registro.lookup("rmiServidor"));
			}
			catch (RemoteException e) {e.printStackTrace();}
			catch (NotBoundException e) {e.printStackTrace();}
		}

		private void enviarMensaje(String mensaje) {
			//estado.setText("Enviando " + mensaje + " a " + direccionServidor + ":"+ puertoServidor);
			try {// llamando el metodo remoto
				//rmiServidor.PosicionPelota("0","a");
				PosicionesXY = rmiServidor.PosicionPelota();
				
				//estado.setText("Recivimos: "+PosicionesXY[0]+" "+PosicionesXY[1]);
				
				//System.out.println(""+"Recivimos: "+PosicionesXY[0]+" "+PosicionesXY[1]);
			} catch (RemoteException re) {re.printStackTrace();}
		}

		public void run(){
			while(true){
				if(banYa){
					if(PosY>0)
			    		PosY = PosY - 10;
				}
				if(banYb){
					if(PosY<390)
			    		PosY = PosY + 10;
				}
				try{
					Thread.sleep(10);
				}catch(InterruptedException e) {
						e.printStackTrace();
				}
				try {
					if((listo=rmiServidor.Estado())==0)
						Listo.setVisible(true);
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
				try {
					if(listo>=2){
						Listo.setVisible(false);
						enviarMensaje("");
						Cuadro.setBounds(PosicionesXY[0], PosicionesXY[1], 15, 15);
						try {
							if(rmiServidor.PuntosJugUno()>=10){
								JOptionPane.showMessageDialog(null, "Perdio el jugador Dos\nSe iniciara de nuevo");
								//listo = 0;
								//Llamar a Nuevo
							}
							if(rmiServidor.PuntosJugDos()>=10){
								JOptionPane.showMessageDialog(null, "Perdio el jugador Uno\nSe iniciara de nuevo");
								//listo = 0;
								//Lamar a Nuevo
							}
							estado.setText("<html><font size=5><font color=BLUE>Jugador 1: </font>"+rmiServidor.PuntosJugUno()+" <font color=RED>Jugador 2: </font> "+rmiServidor.PuntosJugDos()+"</font></html>");
							estado.setBounds(5, 0, 300, 50);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				try {
					if(Jugador==1){
						Barra.setBounds(0, PosY, 15, 60);
						BarraDos.setBounds(615, rmiServidor.PosicionBarra(Jugador,PosY), 15, 60);
					}else{
						Barra.setBounds(615, PosY, 15, 60);
						BarraDos.setBounds(0, rmiServidor.PosicionBarra(Jugador,PosY), 15, 60);
					}
				} catch (RemoteException  e) {
					e.printStackTrace(); 
				} 
			}
		}
		
		public void Inicio(){
			try {
				Jugador = rmiServidor.Jugador();
			} catch (RemoteException re) {re.printStackTrace();}
		}
		
		static public void main(String args[]) throws RemoteException {
			JFrame.setDefaultLookAndFeelDecorated(true);
			RmiCliente ventana = new RmiCliente();
			ventana.Inicio();
		//	ventana.PosicionesXY[0] = 305;
			//ventana.PosicionesXY[1] = 225;
			Thread h = new Thread(ventana);
			h.start();
			ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
	     	if(e.getSource()==Listo){
	     		try {
	     			rmiServidor.Listo();
	     			Listo.setVisible(false);
	     		} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		}

		}
