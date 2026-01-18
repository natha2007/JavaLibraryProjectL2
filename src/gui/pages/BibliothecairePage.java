package gui.pages;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import dao.BibliothecaireDAO;
import gui.gestion.CompteUtilisateur;
import gui.gestion.GestionDate;
import gui.gestion.GestionUIStyle;
import metier.Bibliothecaire;

public class BibliothecairePage extends JPanel implements IPageMaj {
	
	private Runnable rb; 
	
	private final Color btnColor = GestionUIStyle.getButtonColor();
	private final Color bgColor = GestionUIStyle.getBgColor();
	private final Color txtColor = GestionUIStyle.getTextColor();
	
	private CardLayout cl; 
	private CompteUtilisateur user;
	
	//private JPanel head;
	private JPanel body;
	//private JPanel foot;
	
	// HEAD

	
	
	// BODY
	
	private PageAbonnement pa;
	private PageEmprunts pe;
	private PageCommandes pc;
	private PageStocks ps;
	private PageRetours pr;
	private JPanel pb;
	private JLabel mainText;
	
	private JLabel title;
	private BibliothecaireDAO bd = new BibliothecaireDAO();
	
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
	 * Initialise les éléments de l'interface "dynamiques" (dépendant de l'utilisateur
	 * ou nécessitant d'être "rafraichis")
	 * Et crée les éléments "statiques".
	 */
	@Override
	public void initialiserUI() {
		setLayout(new BorderLayout());
		initHead();
		initBody();
		initFoot();
		cl.show(body, "Menu");
	}
	
	/**
	 * Crée les élements dynamiques (dépendant de l'utilisateur ou nécessitant d'être "rafraichis")
	 */
	@Override
	public void majUI() {
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
		pe.majUI();
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
	
	/*
	 * Mettre à jour le tableau de stock dans la page stock
	 */
	public void rechercherStock() {
		ps.rechercherObjet();
	}
	
	/**
	 * Mettre à jour le tableau de stocks disponibles dans la page Emprunt
	 */
	public void rechercherEmprunts() {
		pe.rechercherObjet();
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
		JPanel head = new JPanel(new GridLayout(0,5,0,0)); //int rows, int cols, int hgap, int vgap
		head.setPreferredSize(new Dimension(0,50));
		
		JButton abonnements = new JButton("Abonnements");
		abonnements.setBackground(btnColor);
		abonnements.setForeground(txtColor);
		
		JButton emprunts = new JButton("Emprunts");
		emprunts.setBackground(btnColor);
		emprunts.setForeground(txtColor);
		
		JButton commandes = new JButton("Commandes");
		commandes.setBackground(btnColor);
		commandes.setForeground(txtColor);

		JButton stocks = new JButton("Stocks");
		stocks.setBackground(btnColor);
		stocks.setForeground(txtColor);

		JButton retours = new JButton("Retours");
		retours.setBackground(btnColor);
		retours.setForeground(txtColor);

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
		
		pa = new PageAbonnement();
		pe = new PageEmprunts(this::rechercherStock);
		pc = new PageCommandes();
		ps = new PageStocks(this::rechercherStock);
		pr = new PageRetours();
		
		pb = new JPanel(new BorderLayout());
		pb.setBackground(bgColor);
		
		title = new JLabel();
		title.setHorizontalAlignment(SwingConstants.CENTER);
		pb.add(title, BorderLayout.NORTH);
		
		BufferedImage bibliFic = null;
		try {
			bibliFic = ImageIO.read(new File("img/bibliotheque.jpg"));
		} catch (IOException e) {
			System.out.println("Fichier introuvable");
			e.printStackTrace();
		}
		ImageIcon bibli = new ImageIcon(bibliFic);
		pb.add(new JLabel(bibli), BorderLayout.CENTER);
		
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
		JPanel foot = new JPanel(new GridLayout(2,1,0,0));

		JLabel footText = new JLabel("Gestionnaire bibliothèque - 2026");
		footText.setHorizontalAlignment(getWidth());
		footText.setFont(new Font("Serif", Font.BOLD, 20));
		footText.setForeground(txtColor);
		
		JButton accueil = new JButton("Accueil");
		accueil.setBackground(btnColor);
		accueil.setForeground(txtColor);
		
		JButton connexion = new JButton("Se déconnecter");
		connexion.setBackground(btnColor);
		connexion.setForeground(txtColor);
		
		accueil.addActionListener(e -> retournerAccueil());
		connexion.addActionListener(e -> retournerPageConnexion(user));
		
		JPanel footGrid = new JPanel(new GridLayout(1,2));
		footGrid.setBackground(bgColor);
		
		JPanel footGrid2 = new JPanel(new GridLayout(1,2));
		footGrid2.setBackground(bgColor);
		
		JPanel footGrid3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		footGrid3.setBackground(bgColor);
		
		footGrid.add(Box.createGlue());
		footGrid.add(footGrid3);
		
		footGrid2.add(accueil);
		footGrid2.add(connexion);
		
		String message = "";
		dateDuJour = new JLabel("pas de date a afficher");
		if (GestionDate.getDateJour() != null) {
			dateDuJour.setText(GestionDate.getDateJour().toString());
		}
		dateDuJour.setAlignmentX(RIGHT_ALIGNMENT);
		dateDuJour.setFont(new Font("Serif", Font.BOLD, 20));
		footGrid3.add(dateDuJour);
		
		foot.add(footGrid);
		foot.add(footGrid2);
		
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