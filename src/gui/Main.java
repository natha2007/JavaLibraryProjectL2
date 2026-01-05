package gui;

import dao.CompteDAO;
import metier.Compte;

public class Main {
	public static void main(String[] args) {
		MainFrame mf = new MainFrame("Page de connexion");
		mf.setVisible(true);
		
		CompteDAO cd = new CompteDAO();
		Compte c = new Compte("MercierN",GestionMdp.hash("122007"),"2026-01-03","Client");
		//cd.create(c);
		
		
	}
}
