package rmi_ejemplo;
import java.rmi.*;
public interface InterfazMensajes extends Remote{


	//Este es el metodo que implementar� el servidor
	void recibirMensaje(String texto) throws RemoteException;

}
