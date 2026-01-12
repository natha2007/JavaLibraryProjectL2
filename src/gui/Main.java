package gui;

import java.awt.Dimension;

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
		mf.setMinimumSize(new Dimension(900,600));
		
//		CompteDAO cd = new CompteDAO();
//		Compte c = new Compte("Mercier",GestionMdp.hash("122007"),"2026-01-03","client");
//		cd.create(c);
//		
//		Compte c2 = cd.read(14);
//		ClientDAO cld = new ClientDAO();
//		AbonnementDAO ad = new AbonnementDAO();
//		Abonnement ab = ad.read(2);
//		
//		Client cl = new Client("MERCIER","Nathanaël2",ab,c2);
//		cld.create(cl);
		
		
		
//		EmpruntDAO ed = new EmpruntDAO();
//		ClientDAO cld = new ClientDAO();
//		ObjetDAO od = new ObjetDAO();
//		Client cl = cld.read(1);
//		Objet o1 = od.read(1);
//		Objet o2 = od.read(2);
//		Objet o3 = od.read(3);
//		Objet o4 = od.read(4);
//		Objet o5 = od.read(5);
//		Emprunt e1 = new Emprunt("","",30f,cl,o1);
//		Emprunt e2 = new Emprunt("","",30f,cl,o2);
//		Emprunt e3 = new Emprunt("","",30f,cl,o3);
//		Emprunt e4 = new Emprunt("","",30f,cl,o4);
//		Emprunt e5 = new Emprunt("","",30f,cl,o5);
//		
//		ed.create(e1);
//		ed.create(e2);
//		ed.create(e3);
//		ed.create(e4);
//		ed.create(e5);
	
//		ObjetDAO od = new ObjetDAO();
//		
//		Objet o1 = new Objet("Les misérables Tome 1", "Victor Hugo", 12.90f, "Livre", 1, "1041426186");
//		Objet o2 = new Objet("Les misérables Tome 2", "Victor Hugo", 11.95f, "Livre", 1, "1041427158");
//		Objet o3 = new Objet("Les misérables Tome 3", "Victor Hugo", 13.99f, "Livre", 1, "2322205508");
//		Objet o4 = new Objet("Les misérables Tome 4", "Victor Hugo", 15.99f, "Livre", 1, "2322207039");
//		Objet o5 = new Objet("Les misérables Tome 5", "Victor Hugo", 14.50f, "Livre", 1, "2013752121");
//		
//		od.create(o1);
//		od.create(o2);
//		od.create(o3);
//		od.create(o4);
//		od.create(o5);
	}
}
