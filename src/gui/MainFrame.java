package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import dao.CompteDAO;

//idées lambda : transformer un resultSet en collection Java grâce à un lambdaStream
//on peut mettre un abonnement bibliothécaire, vu que l'abonnement crée le compte

public class MainFrame extends JFrame{
	private CardLayout crd;
	private JPanel root;
	
	public MainFrame(String s) {
		super(s);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		crd = new CardLayout();
		root = new JPanel(crd);
		setContentPane(root);
		
		ConnexionPage cp = new ConnexionPage(this::showLogiciel);
		MenuLogiciel ml = new MenuLogiciel();
		
		root.add(cp, "Connexion");
		root.add(ml, "Logiciel");
		
		setMinimumSize(new Dimension(900,600));
		pack();
		//setSize(600,600);
	}
	
	public void showLogiciel() {
		crd.show(root, "Logiciel");
	}
	
}
