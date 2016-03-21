package rmi_juego;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;

public class Host extends UnicastRemoteObject implements InterfazJuego{
	private int Port;
	private String IP;
	private Registry registro;
	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="236,15"
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JPanel panel = null;
	private JButton jButton = null;


	public Host() throws RemoteException {
		try {// obtener la direccion de este host.
			IP = (InetAddress.getLocalHost()).toString();
		} catch (Exception e) {throw new RemoteException("No se puede obtener la direccion IP.");}
		Port = 3232; // asignar el puerto que se registra
		//("Conexion establecida por...\nEsta direccion="+ IP + ", y puerto=" + Port);

		try {
			// crear el registro y ligar el nombre y objeto.
			registro = LocateRegistry.createRegistry(Port);
			registro.rebind("InJu", this);//se registra este objeto con el nombre InJU (Interfaz Juego)
		} catch (RemoteException e) {throw e;}
	}

	public void recibirMensaje(String texto) throws RemoteException {

		}


	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		try {
			//Host h=
				new Host();
		} catch (Exception e) {e.printStackTrace();}
	}
}