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

//idées lambda : transformer un resultSet en collection Java grâce à un lambdaStream
//on peut mettre un abonnement bibliothécaire, vu que l'abonnement crée le compte

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
		pg = new PageClient(this::seDeconnecter); //enlever 
		pcc = new PageCreationCompte(this::seDeconnecter);
		
		root.add(connexion, "Connexion");
		root.add(bp, "Bibliothecaire");
		root.add(pg, "Client");
		root.add(pcc, "creerCompte");
		
		pack();
		//setSize(600,600);
	}
	
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
	
	public void seDeconnecter() {
		bp.setUser(null);
		crd.show(root, "Connexion");
	}
	
}