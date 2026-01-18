package test;

import dao.*;
import gui.gestion.GestionDate;
import metier.*;

public class TestEmprunt {
	public static void main(String[] args) {
		EmpruntDAO ed = new EmpruntDAO();
		ObjetDAO od = new ObjetDAO();
		ClientDAO cd = new ClientDAO();
		CompteDAO cpd = new CompteDAO();
		AbonnementDAO ad = new AbonnementDAO();
		GestionDate.majDate();
		
		Objet o = od.read(5);
		
		Abonnement ab = ad.read(5);
		Compte c = new Compte("nathmrc","876557656757576...",GestionDate.getDateFromLocalDate(),"client");
		Client cl = cd.read(2);
		
		Emprunt e = new Emprunt("2026-01-04","2026-02-04",30f,cl,o);
		
		//test create
		
		//cpd.create(c);
		//ed.create(e);
		//e = ed.read(4);
		
		//System.out.println(e);
		
		//ok
		
		//e = ed.read(1);
		//e.setDureeMaximaleEmprunt(29);
		//e.setDateFin("2026-02-03");
		
		//ed.update(e);
		
		// ed.delete(e);
		
		//ok
	}
}
