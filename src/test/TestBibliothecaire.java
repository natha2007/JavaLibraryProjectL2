package test;

import java.sql.Date;

import dao.BibliothecaireDAO;
import dao.CompteDAO;
import gui.GestionDate;
import gui.GestionMdp;
import metier.Bibliothecaire;
import metier.Compte;

public class TestBibliothecaire {
	public static void main(String[] args) {
		
		//CREATE
		
		BibliothecaireDAO bd = new BibliothecaireDAO();
		Bibliothecaire b = null;
		Compte c = null;
		CompteDAO cd = new CompteDAO();
		GestionDate.majDate();
		
		if (!(cd.exists("Biblio1"))) {
			c = new Compte("Biblio1",GestionMdp.hash("biblio1mdp"),GestionDate.getDateFromLocalDate(),"bibliothecaire");
			cd.create(c);
			System.out.println("je suis appelé #cd.exists");
		} else {
			c = cd.read("Biblio1");
		}
		
		if (!(bd.exists(c.getCompteId()))) {
			b = new Bibliothecaire("REMBEAUX","Samuel",c);
			bd.create(b);
		} else {
			b = bd.readByCompteId(c.getCompteId());
		}
		System.out.println("CREATE : \n" + b.toString());
		
		//UPDATE
		
		Compte c2 = null;
		
		if (!(cd.exists("Biblio2"))) {
			c2 = new Compte("Biblio2",GestionMdp.hash("biblio2mdp"),GestionDate.getDateFromLocalDate(),"bibliothecaire");
			cd.create(c2);
		} else {
			c2 = cd.read("Biblio2");
		}
		
		Bibliothecaire b2 = new Bibliothecaire(b.getBibliothecaireId(),"BENNEJMA","Bilal",c2);
		bd.update(b2);
		System.out.println("\nUPDATE : \n" + bd.read(b.getBibliothecaireId()));
		b = new Bibliothecaire(b.getBibliothecaireId(),"REMBEAUX","Samuel",c);
		bd.update(b);
		
		//READ
		
		Bibliothecaire b3 = bd.read(b.getBibliothecaireId());
		System.out.println("\nREAD : \nDoit afficher Samuel REMBEAUX : " + b3);
		Bibliothecaire b4 = bd.readByCompteId(c.getCompteId());
		System.out.println("Doit afficher Samuel REMBEAUX : " + b4);
		
		//DELETE
		
		Bibliothecaire b5 = null;
		
		if (!(bd.exists(c2.getCompteId()))) {
			b5 = new Bibliothecaire("BER","Matteo",c2);
			bd.create(b5);
		} else {
			b5 = bd.readByCompteId(c2.getCompteId());
		}
		
		System.out.println("\nDELETE : \nAvant suppression : " + b5);
		bd.delete(b5);
		System.out.println("Après suppression : " + bd.readByCompteId(c2.getCompteId()) + " (doit être null)");
	}
}
