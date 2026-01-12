package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PageCommandes extends JPanel {
	private Runnable rb;

	public PageCommandes(Runnable rb) {
		this.rb = rb;
		JLabel test = new JLabel("Commandes");
		add(test);
	}
}
