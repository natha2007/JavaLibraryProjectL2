package test;

import java.sql.Date;

import dao.*;
import gui.gestion.GestionDate;
import metier.*;

public class TestCompte {
	public static void main(String[] args) {
		CompteDAO test1 = new CompteDAO();
		GestionDate.majDate();
		Date date = GestionDate.getDateFromLocalDate();
		Compte c = new Compte("Natha36","mdp",date,"client");
		
		test1.create(c);
		//ok 
		
		Compte c2 = new Compte(1, "Natha37", "mdp", date, "client");
		test1.update(c2);
		//ok
		
		test1.delete(c2);
		//ok
		
		Compte c3 = test1.read(1);
		System.out.println(c3);
		//ok
		
		Compte c4 = new Compte("Michel37","mdp",date,"bibliothécaire");
		test1.create(c4);
		//ok
	}
}
