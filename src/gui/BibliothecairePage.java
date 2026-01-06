package gui;

import java.awt.*;

import javax.swing.*;

public class BibliothecairePage extends JPanel {
	private CardLayout cl;
	
	public BibliothecairePage(Runnable rb) {
		
		setLayout(new BorderLayout());
		
		JPanel head = new JPanel(new GridLayout(0,5,0,0)); //int rows, int cols, int hgap, int vgap
		head.setPreferredSize(new Dimension(0,50));
		
		JPanel body = new JPanel(new GridBagLayout());
		JPanel foot = new JPanel(new GridLayout(0,2,20,0));
		
		add(head, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		add(foot, BorderLayout.SOUTH);
		
		JLabel text = new JLabel("salut je suis le logiciel bibliothèque de merde");
		body.add(text);
		
		JButton abonnements = new JButton("Abonnements");
		JButton emprunts = new JButton("Emprunts");
		JButton commandes = new JButton("Commandes");
		JButton stocks = new JButton("Stocks");
		JButton retours = new JButton("Retours");

		
		head.add(abonnements);
		head.add(emprunts);
		head.add(commandes);
		head.add(stocks);
		head.add(retours);
		
		JLabel footText = new JLabel("Gestionnaire bibliothèque - 2026 Tous droits réservés");
		JButton accueil = new JButton("Accueil");
		
		foot.add(footText);
		foot.add(accueil);
		
		//accueil.addActionListener(e->conn.run());
		
		//abonnements.addActionListener(e->conn.run());
	}
	
	//Méthode de test
	/*
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Test PageClient");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new MenuLogiciel());
        frame.setMinimumSize(new Dimension(900,600));
        frame.setVisible(true);
        
	}
	*/
}
