
import java.net.InetAddress;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class RmiServidor extends UnicastRemoteObject implements InterfazMensajes , Runnable{
	
	private static GUIservidor ventana;//=new GUIservidor();
	private int estePuerto;
	private String estaIP;
	private Registry registro;
	public String posXposY = "0";
	int velocidad = 15;
	int Estado = 1;
	static int PosX = 100;
	int PosicionBarra1 = 240, PosicionBarra2 = 240;
	int cont = 0, listo = 0;
	int puntosJugador1,puntosJugador2;	
	boolean varJuego = true;
	int xA = 2, yA = 2;
	
	//Tama;o de la ventana
	public int ancho = 640, alto = 480;
	public int x = 305 , y = 235;
	
	public RmiServidor() throws RemoteException {
		

		try {
		// obtener la direccion de este host.
		estaIP = (InetAddress.getLocalHost()).toString();
		} catch (Exception e) {

		throw new RemoteException("No se puede obtener la direccion IP.");
		}
		estePuerto = 3232; // asignar el puerto que se registra
		ventana.anadirEntradas("Conexion establecida por...\nEsta direccion="
		+ estaIP + ", y puerto=" + estePuerto);

		try {
			// crear el registro y ligar el nombre y objeto.
			registro = LocateRegistry.createRegistry(estePuerto);
			registro.rebind("rmiServidor", this);
			} catch (RemoteException e) {
			throw e;
			}
	}
	
	public void run(){
	while(true){
		try {
			Thread.sleep(25);
			Bola();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	}
		//Metodo que mantiene actualizada la posicion de la bola
	public int [] Bola(){
		
		if(puntosJugador1==10||puntosJugador2==10){
    		listo = 0;
    		puntosJugador1 = 0;
    		puntosJugador2 = 0;
    	}
			//Vector de posiciones
			int PosicionesXY [] = new int [2];
			
			//Estados para saber hacia donde va la bola
			if (Estado==1){
				x = x + xA;
				y = y - yA;
			}
			//Arriva - Izquierda
            if (Estado==2){
            	x = x - xA;
            	y = y - yA;
			}
            //Abajo - Derecha
            if (Estado==3){
            	x = x + xA;
            	y = y + yA;
			}
            //Abajo - Izquierda
            if (Estado==4){
            	x = x - xA;
            	y = y + yA;
			}
            
          //Condiciones Que ejecutaran al metodo Decision - Cuando el Cuadro choque con una pared
            if (x>605||x<15||y<0||y>430||y<21){
            	Decision();
            }
            
            if(varJuego){
            	x = 305;
    			y = 235;
    			varJuego = false;
            }
			PosicionesXY[0] = x;
			PosicionesXY[1] = y;
			
			return PosicionesXY;
		}
		
		 void Decision(){
	        	
			 xA = (int) (Math.random()*2) + 2;
			 yA = (int) (Math.random()*2) + 2;
				
			   	if (Estado==1&&x>605) {
	           		if(y+15<PosicionBarra2||y>PosicionBarra2+60){
	           		puntosJugador1++;
	           		varJuego = true;
	           		}
	        		Estado = 2;
	        	}
	           	if (Estado==1&&y<0) {
	        		Estado = 3;
	        	}
	           	if (Estado==2&&x<15) {
	           		if(y+15<PosicionBarra1||y>PosicionBarra1+60){
	           			puntosJugador2++;
	           			varJuego = true;
	           		}
	        		Estado = 1;
	        	}
	        	if (Estado==2&&y<0) {
	        		Estado = 4;
	        	}    
	        	if (Estado==3&&x>605) {
	        		if(y+15<PosicionBarra2||y>PosicionBarra2+60){
	        			puntosJugador1++;
	        			varJuego = true;
	           		}
	        		Estado = 4;
	        	}
	        	//Jugador Uno
	        	if (Estado==3&&y>430) {
	        		Estado = 1;
	        	}    
	        	if (Estado==4&&x<15) {
	        		if(y+15<PosicionBarra1||y>PosicionBarra1+60){
	        			puntosJugador2++;
	        			varJuego = true;
	           		}
	        		Estado = 3;
	        	}
	        	if (Estado==4&&y>430) {
	        		Estado = 2;
	        	}
	        		
	        	
	}
		
    public int[] PosicionPelota() throws RemoteException {
		//ventana.anadirEntradas(""+Bola());
		return Bola();
	}
    
    public int PosicionBarra(int Jugador, int Posicion) throws RemoteException{
    	int posicion = 0;
    	
    	if(Jugador == 1){
    		    PosicionBarra1 = Posicion;
    			posicion = PosicionBarra2; 
    		}else{
    			PosicionBarra2 = Posicion;
    			posicion = PosicionBarra1;
    		}
    	
    	return posicion;
    }
    
    public int Jugador() throws RemoteException{
    	cont++;
    	return cont;
    }
    
    public int Estado()throws RemoteException{    	
    	return listo;
    }
    
    public void Listo()throws RemoteException{
    	listo++;    	
    }
    
    //Regresa los puntos del jugador 1
    public int PuntosJugUno() throws RemoteException{
    	return puntosJugador1;
    }
    
    //Regresa los puntos del jugador 2
    public int PuntosJugDos() throws RemoteException{
    	return puntosJugador2;
    }
    
    public static void main(String[] args) throws RemoteException {
	   
		JFrame.setDefaultLookAndFeelDecorated(true);
		ventana = new GUIservidor();
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
		Thread h = new Thread(new RmiServidor());
		h.setPriority(Thread.MAX_PRIORITY);
		h.start();
		} catch (Exception e) {
		e.printStackTrace();
		System.exit(1);
		}
	}

	

}
