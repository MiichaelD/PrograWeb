package rmi_ejemplo;
import java.rmi.*;
import java.rmi.registry.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
public class RmiCliente extends JFrame implements ActionListener {
		private JTextField cajaEnviar;
		private JButton botonEnviar;
		private JLabel estado;
		private InterfazMensajes rmiServidor;
		private Registry registro;
		private String direccionServidor = "localhost",puertoServidor = "3232";

		public RmiCliente() {
			super("Cliente RMI");
			direccionServidor=JOptionPane.showInputDialog("Nombre/IP del servidor:");
			try{if(direccionServidor.equals(""))
				direccionServidor="localhost";
			}catch(NullPointerException e){direccionServidor="localhost";};
			conectarseAlServidor();
			getContentPane().setLayout(new BorderLayout());
			cajaEnviar = new JTextField();
			cajaEnviar.addActionListener(this);
			botonEnviar = new JButton("Enviar");
			botonEnviar.addActionListener(this);
			estado = new JLabel("Estado...");

			getContentPane().add(cajaEnviar);
			getContentPane().add(botonEnviar, BorderLayout.EAST);
			getContentPane().add(estado, BorderLayout.SOUTH);

			setSize(300, 100);
			setVisible(true);
			}

		public void actionPerformed(ActionEvent e) {
			if (!cajaEnviar.getText().equals("")) {
			enviarMensaje(cajaEnviar.getText());
			cajaEnviar.setText("");
			}
		}

		private void conectarseAlServidor() {
			try {/*// obtener el registro localizandolo en esta direccion, con este puerto
				registro = LocateRegistry.getRegistry(direccionServidor,Integer.parseInt(puertoServidor));
				// creando el objeto remoto, buscandolo en el registro qe ya creamos
				rmiServidor = (InterfazMensajes) (registro.lookup("rmiServidor"));
				 *///Forma 2: Creamos objeto remoto buscando en el registro con los siguientes parametros:
				rmiServidor = (InterfazMensajes) (Naming.lookup("rmi://"+direccionServidor+":"+puertoServidor+"/rmiServidor"));
			}
			catch (Exception e) {e.printStackTrace();
				JOptionPane.showMessageDialog(null,"Debes iniciar servidor primero!" );
				System.exit(1);
			}
		}

		private void enviarMensaje(String mensaje) {
			estado.setText("Enviando " + mensaje + " a " + direccionServidor + ":"+ puertoServidor);
			try {// llamando el metodo remoto
				rmiServidor.recibirMensaje(mensaje);
				estado.setText("El mensaje se ha enviado!!!");
			} catch (RemoteException re) {re.printStackTrace();}
		}

		static public void main(String args[]) {
			JFrame.setDefaultLookAndFeelDecorated(true);
			RmiCliente ventana = new RmiCliente();
			ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

}
