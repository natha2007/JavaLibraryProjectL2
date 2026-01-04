package gui;

import dao.CompteDAO;
import metier.Compte;

public class Main {
	public static void main(String[] args) {
		ConnexionPage cp = new ConnexionPage("Page de connexion");
		cp.setVisible(true);
		
		CompteDAO cd = new CompteDAO();
		Compte c = new Compte("MercierN",GestionMdp.hash("122007"),"2026-01-03","Client");
		//cd.create(c);
		
		
	}
}
