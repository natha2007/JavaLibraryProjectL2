package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PageStocks extends JPanel {
	private Runnable rb;

	public PageStocks(Runnable rb) {
		this.rb = rb;
		JLabel test = new JLabel("Stocks");
		add(test);
	}
	
	
}
