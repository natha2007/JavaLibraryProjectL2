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
		
//		
//		CompteDAO cd = new CompteDAO();
//		AbonnementDAO ad = new AbonnementDAO();
//		ClientDAO cld = new ClientDAO();
//		ObjetDAO od = new ObjetDAO();
//		EmpruntDAO ed = new EmpruntDAO();
//		
//		Compte c = new Compte("MercierN", GestionMdp.hash("122007"),"2026-01-03","Client");
//		cd.create(c);
//		Abonnement ab = new Abonnement("Etudiant", 7.50f, 10);
//		ad.create(ab);
//		Client cl = new Client("MERCIER","Nathanaël",ab,c);
//		cld.create(cl);
//
//		
//		Objet o1 = new Objet("Les misérables Tome 1", "Victor Hugo", 12.90f, "Livre", 1, "1041426186");
//		Objet o2 = new Objet("Les misérables Tome 2", "Victor Hugo", 11.95f, "Livre", 1, "1041427158");
//		Objet o3 = new Objet("Les misérables Tome 3", "Victor Hugo", 13.99f, "Livre", 1, "2322205508");
//		Objet o4 = new Objet("Les misérables Tome 4", "Victor Hugo", 15.99f, "Livre", 1, "2322207039");
//		Objet o5 = new Objet("Les misérables Tome 5", "Victor Hugo", 14.50f, "Livre", 1, "2013752121");
//		
//		Emprunt e1 = new Emprunt("","",30f,cl,o1);
//		Emprunt e2 = new Emprunt("","",30f,cl,o2);
//		Emprunt e3 = new Emprunt("","",30f,cl,o3);
//		Emprunt e4 = new Emprunt("","",30f,cl,o4);
//		Emprunt e5 = new Emprunt("","",30f,cl,o5);
//		
//		od.create(o1);
//		od.create(o2);
//		od.create(o3);
//		od.create(o4);
//		od.create(o5);
//		
//		
//		ed.create(e1);
//		ed.create(e2);
//		ed.create(e3);//		
//		CompteDAO cd = new CompteDAO();
//		AbonnementDAO ad = new AbonnementDAO();
//		ClientDAO cld = new ClientDAO();
//		ObjetDAO od = new ObjetDAO();
//		EmpruntDAO ed = new EmpruntDAO();
//		
//		Compte c = new Compte("MercierN", GestionMdp.hash("122007"),"2026-01-03","Client");
//		cd.create(c);
//		Abonnement ab = new Abonnement("Etudiant", 7.50f, 10);
//		ad.create(ab);
//		Client cl = new Client("MERCIER","Nathanaël",ab,c);
//		cld.create(cl);
//
//		
//		Objet o1 = new Objet("Les misérables Tome 1", "Victor Hugo", 12.90f, "Livre", 1, "1041426186");
//		Objet o2 = new Objet("Les misérables Tome 2", "Victor Hugo", 11.95f, "Livre", 1, "1041427158");
//		Objet o3 = new Objet("Les misérables Tome 3", "Victor Hugo", 13.99f, "Livre", 1, "2322205508");
//		Objet o4 = new Objet("Les misérables Tome 4", "Victor Hugo", 15.99f, "Livre", 1, "2322207039");
//		Objet o5 = new Objet("Les misérables Tome 5", "Victor Hugo", 14.50f, "Livre", 1, "2013752121");
//		
//		Emprunt e1 = new Emprunt("","",30f,cl,o1);
//		Emprunt e2 = new Emprunt("","",30f,cl,o2);
//		Emprunt e3 = new Emprunt("","",30f,cl,o3);
//		Emprunt e4 = new Emprunt("","",30f,cl,o4);
//		Emprunt e5 = new Emprunt("","",30f,cl,o5);
//		
//		od.create(o1);
//		od.create(o2);
//		od.create(o3);
//		od.create(o4);
//		od.create(o5);
//		
//		
//		ed.create(e1);
//		ed.create(e2);
//		ed.create(e3);
//		ed.create(e4);
//		ed.create(e5);
//		ed.create(e4);
//		ed.create(e5);
	}
}
