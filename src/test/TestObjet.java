package test;

import dao.*;
import metier.*;

public class TestObjet {
	public static void main(String[] args) {
		ObjetDAO  objetdao=new ObjetDAO();
		Objet o1=new Objet("Les misérables", "Victor Hugo", 
				Float.valueOf(9), "Livre", 5, " 2070142221");
		objetdao.create(o1);

		o1.setNom("Les misérables Tome 1");
		objetdao.update(o1);
		
		//objetdao.delete(o1);
		System.out.println(o1.toString());
	}
}
