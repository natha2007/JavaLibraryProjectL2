package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PageEmprunts extends JPanel {
	private Runnable rb;

	public PageEmprunts(Runnable rb) {
		this.rb = rb;
		JLabel test = new JLabel("Emprunts");
		add(test);
	}
	
}
