package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import dao.BibliothecaireDAO;
import metier.Bibliothecaire;

public class BibliothecairePage extends JPanel {
	
	private Runnable rb;
	private CardLayout cl;
	private CompteUtilisateur user;
	
	private JPanel head;
	private JPanel body;
	private JPanel foot;
	
	// HEAD

	private JButton abonnements;
	private JButton emprunts;
	private JButton commandes;
	private JButton stocks;
	private JButton retours;
	
	// BODY
	
	private PageAbonnement pa;
	private PageEmprunts pe;
	private PageCommandes pc;
	private PageStocks ps;
	private PageRetours pr;
	private JPanel pb;
	private JLabel mainText;
	
	private JLabel title;
	private BibliothecaireDAO bd;
	
	// FOOT
	
	private JLabel footText;
	private JLabel dateDuJour;
	private JButton accueil;
	private JButton connexion;
	private JPanel footGrid;
	private JPanel footGrid2;
	
	public BibliothecairePage(Runnable rb) {
		this.rb = rb;
		initialiserUI();
		majUI();
	}
	
	/**
	 * Récupère les infos de l'utilisateur connecté
	 * @param user utilisateur (client ou bibliothecaire)
	 */
	public void setUser(CompteUtilisateur user) {
		this.user = user;
		pa.setUser(user);
		pe.setUser(user);
		pc.setUser(user);
		ps.setUser(user);
		pr.setUser(user);
		majUI();
	}
	
	/**
	 * Initialise les éléments de l'interface "dynamiques" (dépendant de l'utilisateur)
	 * Et crée les éléments "statiques".
	 */
	private void initialiserUI() {
		setLayout(new BorderLayout());
		
		initHead();
		initBody();
		initFoot();
		
		cl.show(body, "Menu");
	}
	
	/**
	 * Crée les élements dynamiques (dépendant de l'utilisateur)
	 */
	private void majUI() {
		if (user == null) {
			mainText.setText("En attente de connexion");
			title.setText("");
		} else {
			
			Bibliothecaire b = bd.readByCompteId(user.getCompteId());
			title.setText("Bienvenue " + b.getPrenom() + " " + b.getNom());
			title.setFont(new Font("Serif", Font.BOLD, 30));
			mainText.setText(
					"<html>"
					+ "<div style='width:500px;'>"
					+ "Ce gestionnaire de bibliothèque vous permet de gérer "
					+ "les emprunts des clients, les retours, leurs abonnements, ainsi que de "
					+ "gérer les commandes et les stocks "
					+ "</div>"
					+ "</html>");
			
			//compléter ici si on a besoin des infos de l'utilisateur
		}
	}
	
	/*
	 * Switch sur la "page" Accueil 
	 */
	public void retournerAccueil() {
		cl.show(body, "Menu");
	}
	
	/*
	 * Switch sur la "page" Abonnements
	 */
	public void showAbonnements() {
		cl.show(body, "Abonnements");
	}
	
	/*
	 * Switch sur la "page" Emprunts
	 */
	public void showEmprunts() {
		cl.show(body, "Emprunts");
	}
	
	/*
	 * Switch sur la "page" Commandes 
	 */
	public void showCommandes() {
		cl.show(body, "Commandes");
	}
	
	/*
	 * Switch sur la "page" Stocks
	 */
	public void showStocks() {
		ps.majUI();
		cl.show(body, "Stocks");
	}
	
	public void rechercherStock() {
		ps.rechercherObjet();
	}
	
	/*
	 * Switch sur la "page" Retours
	 */
	public void showRetours() {
		cl.show(body, "Retours");
	}
	
	/*
	 * Switch sur la "page" Connexion 
	 */
	public void retournerPageConnexion(CompteUtilisateur empty) {
		rb.run();
	}
	
	/**
	 * Initialisation de la partie "head" (supérieure) de la page
	 */
	private void initHead() {
		head = new JPanel(new GridLayout(0,5,0,0)); //int rows, int cols, int hgap, int vgap
		head.setPreferredSize(new Dimension(0,50));
		
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
		
		abonnements.addActionListener(e -> showAbonnements());
		emprunts.addActionListener(e -> showEmprunts());
		commandes.addActionListener(e -> showCommandes());
		stocks.addActionListener(e -> showStocks());
		retours.addActionListener(e -> showRetours());
		
		add(head, BorderLayout.NORTH);
	}
	
	/**
	 * Initialisation de la partie "body" (centrale) de la page
	 */
	private void initBody() {
		cl = new CardLayout();
		body = new JPanel(cl);
		
		bd = new BibliothecaireDAO();
		
		pa = new PageAbonnement();
		pe = new PageEmprunts(this::rechercherStock);
		pc = new PageCommandes();
		ps = new PageStocks(this::rechercherStock);
		pr = new PageRetours();
		
		pb = new JPanel(new BorderLayout());
		
//		GridBagConstraints gbc = new GridBagConstraints();
//		
//		gbc.insets = new Insets(20,0,0,0);
//		gbc.gridx = 0;
//		gbc.gridy = 0;
//		gbc.weightx = 1;
		title = new JLabel();
		title.setHorizontalAlignment(SwingConstants.CENTER);
		pb.add(title, BorderLayout.NORTH);
		
//		gbc.insets = new Insets(10,0,0,0);
//		gbc.gridx = 0;
//		gbc.gridy = 1;
//		gbc.weightx = 1;
		BufferedImage bibliFic = null;
		try {
			bibliFic = ImageIO.read(new File("img/bibliotheque.jpg"));
		} catch (IOException e) {
			System.out.println("Fichier introuvable");
			e.printStackTrace();
		}
		ImageIcon bibli = new ImageIcon(bibliFic);
		pb.add(new JLabel(bibli), BorderLayout.CENTER);
		
//		gbc.insets = new Insets(10,0,0,0);
//		gbc.gridx = 0;
//		gbc.gridy = 2;
//		gbc.weightx = 1;
		mainText = new JLabel();
		mainText.setHorizontalAlignment(SwingConstants.CENTER);
		pb.add(mainText, BorderLayout.SOUTH);
		
		body.add(pa,"Abonnements");
		body.add(pe,"Emprunts");
		body.add(pc,"Commandes");
		body.add(ps,"Stocks");
		body.add(pr,"Retours");
		body.add(pb,"Menu");
		
		add(body, BorderLayout.CENTER);
	}
	
	/**
	 * Initialisation de la partie "foot" (inférieure) de la page
	 */
	private void initFoot() {
		foot = new JPanel(new GridLayout(2,1,0,0));

		footText = new JLabel("Gestionnaire bibliothèque - 2026");
		footText.setHorizontalAlignment(getWidth());
		footText.setFont(new Font("Serif", Font.BOLD, 20));
		
		
		accueil = new JButton("Accueil");
		connexion = new JButton("Se déconnecter");
		
		accueil.addActionListener(e -> retournerAccueil());
		connexion.addActionListener(e -> retournerPageConnexion(user));
		
		footGrid = new JPanel(new GridLayout(1,2));
		footGrid2 = new JPanel(new GridLayout(1,2));
		JPanel footGrid3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel footGrid4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		String message = "";
		dateDuJour = new JLabel("pas de date a afficher");
		if (GestionDate.getDateJour() != null) {
			dateDuJour.setText(GestionDate.getDateJour().toString());
		}
		dateDuJour.setAlignmentX(RIGHT_ALIGNMENT);
		dateDuJour.setFont(new Font("Serif", Font.BOLD, 20));
		
		footGrid.add(accueil);
		footGrid.add(connexion);
		
		
		/*
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		*/
		
		footGrid2.add(Box.createGlue());
		footGrid2.add(footGrid3);
		footGrid3.add(dateDuJour);
		
		foot.add(footGrid2);
		foot.add(footGrid);
		
		add(foot, BorderLayout.SOUTH);
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