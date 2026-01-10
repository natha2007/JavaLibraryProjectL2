package test;

import dao.BibliothecaireDAO;
import dao.CompteDAO;
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
		
		
		if (!(cd.exists("Biblio1"))) {
			c = new Compte("Biblio1",GestionMdp.hash("biblio1mdp"),"2026-01-10","bibliothecaire");
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
		
	}
}
