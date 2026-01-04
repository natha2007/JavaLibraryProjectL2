package test;

import dao.*;
import metier.*;

public class TestClient {

	public static void main(String[] args) {
		ClientDAO cld = new ClientDAO();
		AbonnementDAO abd = new AbonnementDAO();
		CompteDAO cd = new CompteDAO();
		Abonnement ab = new Abonnement("classique",2.5f);
		Compte c = new Compte("nathanael_mercier","mdp","2025-12-27","client");
		Client cl = new Client("Nathanaël","MERCIER",ab,c);
		
		abd.create(ab);
		cd.create(c);
		cld.create(cl);
		//ok
		
		
		Client c2 = cld.read(cl.getClientId());
		System.out.println(c2);
		//ok
		
		Client cl3 = new Client(4,"Stephane","Marchais",ab,c);
		cld.update(cl3);
		//ok
		
		cld.delete(cl3);
		//ok
		
		 
	}

}
