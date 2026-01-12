package gui;

import javax.swing.JLabel;
import javax.swing.*;

public class PageAbonnement extends JPanel {
	private Runnable conn;
	
	public PageAbonnement(Runnable conn) {
		this.conn = conn;
		JLabel test = new JLabel("abonnements");
		add(test);
	}
	
	
}
