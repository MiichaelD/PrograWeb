package rmi_ejemplo;
import java.net.InetAddress;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import javax.swing.JFrame;
public class RmiServidor extends UnicastRemoteObject implements InterfazMensajes {
	private static GUIservidor ventana;
	private int estePuerto;
	private String estaIP;
	private Registry registro;

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
			// crear el registro y ligar el nombre y objeto en la maqina servidora.
			registro = LocateRegistry.createRegistry(estePuerto);
			registro.rebind("rmiServidor", this);
			} catch (Exception e) {e.printStackTrace();}
	}

	public void recibirMensaje(String texto) throws RemoteException {
		ventana.anadirEntradas(texto);
		}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		ventana = new GUIservidor();
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
		new RmiServidor();
		} catch (Exception e) {
		e.printStackTrace();
		System.exit(1);
		}
	}
}
