package udp;

import java.net.*;

public class Chat{
	static DatagramPacket datagrama;
	static DatagramSocket unSocket;
	static 	int puertoServidor = 555;
	static InetAddress unHost;
	public static void main(String[] args) throws Exception {

		Thread cliente=new Thread(new Cliente());
		if(args.length>0)Cliente.mensaje=args[0];
		else Cliente.mensaje="192.168.1.18";
		Thread servidor=new Thread(new Servidor());

		servidor.start();
		cliente.start();


	}

}
