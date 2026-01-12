package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import dao.BibliothecaireDAO;
import dao.ClientDAO;
import dao.CompteDAO;

//idées lambda : transformer un resultSet en collection Java grâce à un lambdaStream
//on peut mettre un abonnement bibliothécaire, vu que l'abonnement crée le compte

public class MainFrame extends JFrame{
	private CardLayout crd;
	private JPanel root;
	private PageClient pg;
	private BibliothecairePage bp;
	
	public MainFrame(String s) {
		super(s);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		crd = new CardLayout();
		root = new JPanel(crd);
		root.setPreferredSize(new Dimension(600,600));
		setContentPane(root);
		
		// Lambda permet de ne pas créer de dépendances entre les pages
		// et donc de, entre autres, pouvoir tester les classes seules.
		// Par exemple, au lieu de mettre en paramètre du constructeur
		// de ConnexionPage un MainFrame (couplage fort),
		// on lui passe un lambda représentant l’action à effectuer
		// quand la page veut changer d’écran.
		// La page ne sait pas qui fait le changement ni comment,
		// elle sait juste quand déclencher l’action.
		
		/*
		 * un lambda permet de passer une fonction en paramètre plutôt qu'une variable ?
		 */
		
		ConnexionPage connexion = new ConnexionPage(this::showLogiciel);
		bp = new BibliothecairePage(this::seDeconnecter); // Quand quelqu’un appellera run(), alors on exécutera showConnexion() sur cet obje
		pg = new PageClient(this::showConnexion); //== () -> this.showConnexion()

		
		root.add(connexion, "Connexion");
		root.add(bp, "Bibliothecaire");
		root.add(pg, "Client");
		
		pack();
		//setSize(600,600);
	}
	
	public void showLogiciel(CompteUtilisateur user) {
		if (user.getType().equals("bibliothecaire")) {
			BibliothecaireDAO bd = new BibliothecaireDAO();
			Integer bibliothecaireId = bd.readByCompteId(user.getCompteId()).getBibliothecaireId();
			user.setBibliothecaireId(bibliothecaireId);
			bp.setUser(user);
			crd.show(root, "Bibliothecaire");
		} else if (user.getType().equals("client")) {
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
	
	public void seDeconnecter() {
		bp.setUser(null);
		crd.show(root, "Connexion");
	}

	
}
