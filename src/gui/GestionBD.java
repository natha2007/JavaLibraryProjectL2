package gui;
import metier.*;

import java.util.ArrayList;

import dao.*;

public class GestionBD {
	private ArrayList<Objet> listeObjets;
	
	public static void genererObjets() {
		ObjetDAO od = new ObjetDAO();
		Objet o = null;
		int randomTypeObjet =  (int)Math.random() * 4;
		String typeObjet = null;
		switch(randomTypeObjet) {
		 
		case 0 :
			typeObjet = "Livre";
		case 1 :
			typeObjet = "CD";
		case 2 :
			typeObjet = "DVD";
		case 3 :
			typeObjet = "JeuSociete";
		case 4 :
			typeObjet = "Ordinateur";
		}
		for (int i=0;i<500;i++) {
			o = new Objet(typeObjet+i,"auteur"+i,(float)Math.random()*10,typeObjet,1,i+"394745838"+i);
			od.create(o);
		}
	}
}
