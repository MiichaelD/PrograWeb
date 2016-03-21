package tcp;
import java.net.*;
import java.io.*;
public class Client {
	public static void main (String args[]) {
	// los argumentos proporcionan el mensaje y el nombre del host destino
	   try{
	       int puertoServicio = 7896;
	   Socket	s = new Socket("10.10.13.68", puertoServicio);
	   DataInputStream entrada = new DataInputStream(s.getInputStream());
	   DataOutputStream salida = new DataOutputStream( s.getOutputStream());
	    salida.writeUTF("PUTO"); // UTF es una codificación de String, ver Sec. 4.3
	    String datos = entrada.readUTF();
	    System.out.println("Recibido:" + datos);
	    s.close( );
       	    }catch (UnknownHostException e){
		System.out.println("Sock:"+e.getMessage());
	    }catch (EOFException e){System.out.println("EOF:"+e.getMessage());
    	    }catch (IOException e){System.out.println("IO:"+e.getMessage());}
	 }
            }
