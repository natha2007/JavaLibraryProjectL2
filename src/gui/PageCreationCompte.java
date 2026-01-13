package gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PageCreationCompte extends JPanel  {
	
	public PageCreationCompte() {
		setLayout(new BorderLayout());
		JLabel text = new JLabel("test");
		add(text,BorderLayout.CENTER);
	}
}
