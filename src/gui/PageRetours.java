package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PageRetours extends JPanel {
	private Runnable rb;

	public PageRetours(Runnable rb) {
		this.rb = rb;
		JLabel test = new JLabel("Retours");
		add(test);
	}
	
	
}
