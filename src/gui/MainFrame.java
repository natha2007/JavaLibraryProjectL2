package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import dao.ClientDAO;
import dao.CompteDAO;

//idées lambda : transformer un resultSet en collection Java grâce à un lambdaStream
//on peut mettre un abonnement bibliothécaire, vu que l'abonnement crée le compte

public class MainFrame extends JFrame{
	private CardLayout crd;
	private JPanel root;
	private PageClient pg;
	
	public MainFrame(String s) {
		super(s);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(900,600));
		
		crd = new CardLayout();
		root = new JPanel(crd);
		setContentPane(root);
		
		ConnexionPage connexion = new ConnexionPage(this::showLogiciel);
		BibliothecairePage bbl = new BibliothecairePage(this::showConnexion);
		pg = new PageClient(this::showConnexion);
		
		root.add(connexion, "Connexion");
		root.add(bbl, "Bibliothecaire");
		root.add(pg, "Client");
		
		
		pack();
		//setSize(600,600);
	}
	
	public void showLogiciel(CompteUtilisateur user) {
		if (user.getType() == "bibliothécaire") {
			//BibliothecaireDAO bd = new BibliothecaireDAO();
			//Integer bibliothecaireId = bd.getBibliothecaireIdByCompteId(user.getCompteId());
			//user.setBibliothecaireId(bibliothecaireId);
			crd.show(root, "Bibliothecaire");
		} else {
			ClientDAO cd = new ClientDAO();
			Integer clientId = cd.getClientFromCompte(user.getCompteId());
			user.setClientId(clientId);
			pg.setUser(user);
			crd.show(root, "Client");
		}
		
	}
	
	public void showConnexion() {
		crd.show(root, "Connexion");
	}

	
}
