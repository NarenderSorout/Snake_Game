import javax.swing.JFrame;

public class Interface extends JFrame{

	private static final long serialVersionUID = 1L;

	Interface() {
		HUD panel = new HUD();
		this.add(panel);
		this.setTitle("snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
