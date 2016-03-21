package udp;
import java.net.*;
import java.io.*;
import java.util.*;
public class Cliente implements Runnable{
 	Scanner in=new Scanner(System.in);
 	 static String mensaje;
    public static void main(String args[]){
    	new Cliente().run();
	}

 public void run(){

	 try {
		 System.out.println("Introduce Mensaje:");
		 DatagramSocket unSocket = new DatagramSocket();
		 while(true){
		InetAddress unHost = InetAddress.getByName(mensaje);
		mensaje= in.nextLine();
		int puertoServidor = 6789;
		DatagramPacket peticion =new DatagramPacket(mensaje.getBytes(), mensaje.length(), unHost, puertoServidor);
		unSocket.send(peticion);
		System.out.println("Tú: "+new String(peticion.getData()));
		 }//unSocket.close();
	  }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
	  }catch (IOException e){System.out.println("IO: " + e.getMessage());}


 }

 }

