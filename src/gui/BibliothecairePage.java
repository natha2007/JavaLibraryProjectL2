package gui;

import java.awt.*;

import javax.swing.*;

public class BibliothecairePage extends JPanel {
	
	private Runnable rb;
	private CardLayout cl;
	private CompteUtilisateur user;
	private JPanel head;
	private JPanel body;
	private JPanel foot;
	private JLabel mainText;
	private JButton abonnements;
	private JButton emprunts;
	private JButton commandes;
	private JButton stocks;
	private JButton retours;
	private JLabel footText;
	private JButton accueil;
	
	private PageAbonnement pa;
	private PageEmprunts pe;
	private PageCommandes pc;
	private PageStocks ps;
	private PageRetours pr;
	private JLabel pb;
	
	public void setUser(CompteUtilisateur user) {
		this.user = user;
		majUI();
		
	}
	
	private void initialiserUI() {
		setLayout(new BorderLayout());
		
		cl = new CardLayout();
		
		head = new JPanel(new GridLayout(0,5,0,0)); //int rows, int cols, int hgap, int vgap
		head.setPreferredSize(new Dimension(0,50));
		
		body = new JPanel(cl);
		foot = new JPanel(new GridLayout(0,2,20,0));
		
		add(head, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		add(foot, BorderLayout.SOUTH);
		
		mainText = new JLabel();
		//body.add(mainText);
		
		abonnements = new JButton("Abonnements");
		emprunts = new JButton("Emprunts");
		commandes = new JButton("Commandes");
		stocks = new JButton("Stocks");
		retours = new JButton("Retours");

		head.add(abonnements);
		head.add(emprunts);
		head.add(commandes);
		head.add(stocks);
		head.add(retours);
		
		pa = new PageAbonnement(this::retournerAccueil);
		pe = new PageEmprunts(this::retournerAccueil);
		pc = new PageCommandes(this::retournerAccueil);
		ps = new PageStocks(this::retournerAccueil);
		pr = new PageRetours(this::retournerAccueil);
		pb = new JLabel("menu");
		
		body.add(pa,"Abonnements");
		body.add(pe,"Emprunts");
		body.add(pc,"Commandes");
		body.add(ps,"Stocks");
		body.add(pr,"Retours");
		body.add(pb,"Menu");

		abonnements.addActionListener(e -> showAbonnements());
		emprunts.addActionListener(e -> showEmprunts());
		
		footText = new JLabel("Gestionnaire bibliothèque - 2026 Tous droits réservés");
		accueil = new JButton("Accueil");
		
		accueil.addActionListener(e -> retournerAccueil());
		
		foot.add(footText);
		foot.add(accueil);
		
		cl.show(body, "Menu");
	}
	
	private void majUI() {
		if (user == null) {
			mainText.setText("En attente de connexion");
		} else {
			mainText.setText("salut je suis le logiciel bibliothèque");
			//user n'est pas utilisé pour l'instant mais pourra l'être
		}
		//accueil.addActionListener(e->conn.run());
		//abonnements.addActionListener(e->conn.run());
	}
	
	public BibliothecairePage(Runnable rb) {
		this.rb = rb;
		initialiserUI();
		majUI();
	}
	
	public void retournerAccueil() {
		cl.show(body, "Menu");
	}
	
	public void showAbonnements() {
		cl.show(body, "Abonnements");
	}
	
	public void showEmprunts() {
		cl.show(body, "Emprunts");
	}
	
	//Méthode de test
	/*
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Test PageBibliothecaire");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new BibliothecairePage(() -> {
            System.out.println("Switch de page ignoré");
        }));
        frame.setMinimumSize(new Dimension(900,600));
        frame.setVisible(true);
        
	}
	*/
}
