package udp;
import java.net.*;
import java.io.*;
public class Servidor implements Runnable{
	public static void main(String args[]){
		new Servidor().run();
	 }


	public void run(){
		 byte[] bufer;
		 DatagramSocket unSocket;
		try{
		      unSocket = new DatagramSocket(6789);
		      bufer = new byte[1000];
	 	      while(true){
	 	          DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);
	  	          unSocket.receive(peticion);
	    	      System.out.println("Otro: "+new String(peticion.getData()));
			}
		    }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		    }catch (IOException e) {System.out.println("IO: " + e.getMessage());}


	}



}
