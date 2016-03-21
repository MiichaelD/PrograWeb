import javax.swing.*;
public class GUIservidor extends JFrame {

	private JTextArea areaTexto;

	public GUIservidor() {
		super("Servidor RMI");
		areaTexto = new JTextArea();
		areaTexto.setEditable(false);
		getContentPane().add(new JScrollPane(areaTexto));
		setSize(600, 400);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public void anadirEntradas(String texto) {
		areaTexto.append(texto + "\n");
	}
}