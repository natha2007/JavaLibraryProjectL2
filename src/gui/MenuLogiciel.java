package gui;

import java.awt.*;

import javax.swing.*;

public class MenuLogiciel extends JPanel {
	
	public MenuLogiciel() {
		setLayout(new BorderLayout());
		
		JPanel head = new JPanel(new GridBagLayout());
		JPanel body = new JPanel(new GridBagLayout());
		JPanel foot = new JPanel(new GridBagLayout());
		
		add(head, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		add(foot, BorderLayout.SOUTH);
		
		JLabel text = new JLabel("salut je suis le logiciel bibliothèque de merde");
		body.add(text);
	}
}
