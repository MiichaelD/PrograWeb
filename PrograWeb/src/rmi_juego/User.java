package rmi_juego;
import javax.swing.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.awt.*;
public class User extends JFrame{
	/**
	 *
	 */
	private static final long serialVersionUID = 8810235406133323505L;
	private InterfazJuego InJu;
	private Registry registro;
	private String Host = "localhost";
	private String Port = "3232";

	public User() {
		super("User ");
		Host=JOptionPane.showInputDialog("Nombre/IP del servidor");
		try{if(Host.equals(""))
			Host="localhost";
		}catch(NullPointerException e){Host="localhost";};
		conectarseAlServidor();
		getContentPane().setLayout(new BorderLayout());

		setSize(300, 100);
		setVisible(true);
		}

	private void conectarseAlServidor() {
		try {// obtener el registro
			registro = LocateRegistry.getRegistry(Host,Integer.parseInt(Port));
			// creando el objeto remoto
			InJu = (InterfazJuego) (registro.lookup("InJu"));
		}
		catch (Exception e) {e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Debes iniciar servidor primero!" );
			System.exit(1);
		}
	}



	static public void main(String args[]) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		User ventana = new User();
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


}
