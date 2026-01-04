package test;

import dao.*;
import metier.*;

public class TestCompte {
	public static void main(String[] args) {
		CompteDAO test1 = new CompteDAO();
		Compte c = new Compte("Natha36","mdp","2025-12-24","client");
		//attention à utiliser le constructeur sans l'id pour un create
		
		test1.create(c);
		//ok 
		
		Compte c2 = new Compte(1, "Natha37", "mdp", "2025-12-24", "client");
		test1.update(c2);
		//ok
		
		test1.delete(c2);
		//ok
		
		Compte c3 = test1.read(1);
		System.out.println(c3);
		//ok
		
		Compte c4 = new Compte("Michel37","mdp","20","bibliothécaire");
		test1.create(c4);
		
		//ok
		
		/*
		 * Il faudra dans la partie UI créer des méthodes particulières,
		 * par exemple on fera la méthode creerCompte() qui utilisera
		 * la méthode create() de CompteDAO, et c'est dans cette méthode
		 * creerCompte(), qu'on pourra gérer les exceptions (par exemple
		 * vérifier qu'une date est bien sous la forme d'une date, qu'une
		 * String dépasse pas 20 caractères pour un VARCHAR(20)...)
		 */
	}
}
