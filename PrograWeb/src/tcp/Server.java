package tcp;
import java.net.*;
import java.io.*;
public class Server {
    public static void main(String args[]) {
        try{
	int PuertoServicio = 7896;
	ServerSocket escuchandoSocket = new ServerSocket(PuertoServicio);
	while (true) {
	          Socket socketCliente = escuchandoSocket.accept();
	          new Conexion(socketCliente);
	}
         } catch (IOException e) {System.out.println("Escuchando: "+e.getMessage());}
    }
}

// Esta figura continúa en la siguiente diapositiva
class Conexion extends Thread {
    DataInputStream entrada;
    DataOutputStream salida;
    Socket socketCliente;
     public Conexion (Socket unSocketCliente) {
       try {
socketCliente = unSocketCliente;
entrada = new DataInputStream(socketCliente.getInputStream( ));
salida = new DataOutputStream(socketCliente.getOutputStream( ));
this.start( );
        } catch(IOException e)
      {System.out.println("Conexion:"+e.getMessage());}
      }
      public void run( ){
         try {	      // un servidor eco
  String datos = entrada.readUTF();
  salida.writeUTF(datos);
  System.out.println(datos);
  socketCliente.close( );
         } catch(EOFException e) {System.out.println("EOF: "+e.getMessage( ));
         } catch(IOException e) {System.out.println("IO: "+e.getMessage( ));}
      }
}
