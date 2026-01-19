package gui.main;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import dao.BibliothecaireDAO;
import dao.ClientDAO;
import dao.CompteDAO;
import gui.gestion.CompteUtilisateur;
import gui.pages.BibliothecairePage;
import gui.pages.ConnexionPage;
import gui.pages.PageClient;
import gui.pages.PageCreationCompte;


public class MainFrame extends JFrame{
	private CardLayout crd;
	private JPanel root;
	private PageClient pg;
	private BibliothecairePage bp;
	private PageCreationCompte pcc;
	
	public MainFrame(String s) {
		super(s);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		crd = new CardLayout();
		root = new JPanel(crd);
		root.setPreferredSize(new Dimension(900,600));
		setContentPane(root);
		
		ConnexionPage connexion = new ConnexionPage(this::showLogiciel);
		bp = new BibliothecairePage(this::seDeconnecter);
		pg = new PageClient(this::seDeconnecter); 
		pcc = new PageCreationCompte(this::seDeconnecter);
		
		root.add(connexion, "Connexion");
		root.add(bp, "Bibliothecaire");
		root.add(pg, "Client");
		root.add(pcc, "creerCompte");
		
		pack();
	}
	
	/**
	 * Affiche la partie client, ou bibliothécaire selon le typeCompte
	 * de l'utilisateur 
	 * @param user
	 */
	public void showLogiciel(CompteUtilisateur user) {
		if (user == null) {
			crd.show(root, "creerCompte");
		} else if (user.getType().equals("Bibliothécaire")) {
			BibliothecaireDAO bd = new BibliothecaireDAO();
			Integer bibliothecaireId = bd.readByCompteId(user.getCompteId()).getBibliothecaireId();
			user.setBibliothecaireId(bibliothecaireId);
			bp.setUser(user);
			crd.show(root, "Bibliothecaire");
		} else if (user.getType().equals("Client")) {
			ClientDAO cd = new ClientDAO();
			Integer clientId = cd.getClientFromCompte(user.getCompteId());
			user.setClientId(clientId);
			pg.setUser(user);
			crd.show(root, "Client");
		}
	}
	
	/**
	 * Permet de se déconnecter depuis la partie Bibliothécaire
	 */
	public void seDeconnecter() {
		bp.setUser(null);
		crd.show(root, "Connexion");
	}
	
}