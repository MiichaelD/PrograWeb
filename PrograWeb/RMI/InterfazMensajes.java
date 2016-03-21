import java.rmi.*;
public interface InterfazMensajes extends Remote{


	//Este es el metodo que implementará el servidor
	//void recibirMensaje(String texto) throws RemoteException;

	int[] PosicionPelota() throws RemoteException;
	int PosicionBarra(int Jugador, int Posicion) throws RemoteException;
	int Jugador() throws RemoteException;
	int Estado() throws RemoteException;
	void Listo() throws RemoteException;
	int PuntosJugUno() throws RemoteException;
	int PuntosJugDos() throws RemoteException;

}
