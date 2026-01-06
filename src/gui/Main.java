package gui;

import dao.AbonnementDAO;
import metier.*;
import dao.ClientDAO;
import dao.CompteDAO;
import dao.EmpruntDAO;
import dao.ObjetDAO;
import metier.Abonnement;
import metier.Client;
import metier.Compte;

public class Main {
	public static void main(String[] args) {
		MainFrame mf = new MainFrame("Gestionnaire de bibliothèque");
		mf.setVisible(true);
		
		/*
		CompteDAO cd = new CompteDAO();
		//Compte c = new Compte("MercierN",GestionMdp.hash("122007"),"2026-01-03","Client");
		//cd.create(c);
		
		Compte c = cd.read(10);
		ClientDAO cld = new ClientDAO();
		AbonnementDAO ad = new AbonnementDAO();
		Abonnement ab = ad.read(2);
		
		Client cl = new Client("MERCIER","Nathanaël",ab,c);
		//cld.create(cl);
		*/
		
		EmpruntDAO ed = new EmpruntDAO();
		ClientDAO cld = new ClientDAO();
		ObjetDAO od = new ObjetDAO();
		Client cl = cld.read(8);
		Objet o1 = od.read(4);
		Objet o2 = od.read(5);
		Objet o3 = od.read(6);
		Objet o4 = od.read(7);
		Objet o5 = od.read(8);
				
	}
}
