package rmi_juego;
import java.rmi.*;
public interface InterfazJuego extends Remote{

	//Este es el metodo que implementará el servidor
	void recibirMensaje(String texto) throws RemoteException;

}
