package gui;

import java.awt.AWTEvent;

import java.awt.Toolkit;
import java.awt.AWTEvent;

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
		
		
		CompteDAO cd = new CompteDAO();
		//Compte c2 = new Compte("MercierN",GestionMdp.hash("122007"),"2026-01-03","Client");
		//cd.create(c2);
		
		Compte c = cd.read(1);
		ClientDAO cld = new ClientDAO();
		AbonnementDAO ad = new AbonnementDAO();
		Abonnement ab = new Abonnement("premium",2f);
		
		//ad.create(ab);
		
		Client cl = new Client("MERCIER","Nathanaël",ab,c);
		//cld.create(cl);
		
		
		EmpruntDAO ed = new EmpruntDAO();
		ClientDAO cld2 = new ClientDAO();
		ObjetDAO od = new ObjetDAO();
		Client cl2 = cld.read(1);
		Objet o1 = od.read(1);
		Objet o2 = od.read(2);
		Objet o3 = od.read(3);
		Objet o4 = od.read(4);
		Objet o5 = od.read(5);
		
		Emprunt e1 = new Emprunt("2026-01-06","2026-02-06",30f,cl2,o1);
		Emprunt e2 = new Emprunt("2026-01-06","2026-02-06",30f,cl2,o2);
		Emprunt e3 = new Emprunt("2026-01-06","2026-02-06",30f,cl2,o3);
		Emprunt e4 = new Emprunt("2026-01-06","2026-02-06",30f,cl2,o4);
		Emprunt e5= new Emprunt("2026-01-06","2026-02-06",30f,cl2,o5);
		
		
		ed.create(e1);		
		ed.create(e2);		
		ed.create(e3);		
		ed.create(e4);		
		ed.create(e5);	
		System.out.print(o1);
		
	}
}
