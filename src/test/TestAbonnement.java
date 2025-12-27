package test;

import dao.*;
import metier.*;

public class TestAbonnement {

	public static void main(String[] args) {
		AbonnementDAO test1 = new AbonnementDAO();
		Abonnement ab = new Abonnement("premium",10f);
		
		test1.create(ab);
		//ok
		
		Abonnement ab2 = new Abonnement(1, "classique",2.5f);
		test1.update(ab2);
		//ok
		
		test1.delete(ab2);
		//ok
		
		Abonnement ab3 = test1.read(2);
		System.out.println(ab3);
		//ok
		
		//les classesDAO ne sont pas forcément finies complétement et peuvent être complétées 
		//en cas de besoin
	}

}
