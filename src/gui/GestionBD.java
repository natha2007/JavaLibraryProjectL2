package gui;
import metier.*;

import java.util.ArrayList;

import dao.*;

public class GestionBD {
	private ArrayList<Objet> listeObjets;
	
	public static void genererObjets() {
		ObjetDAO od = new ObjetDAO();
		Objet o = null;
		int randomTypeObjet = 0;
		for (int i=0;i<500;i++) {
			randomTypeObjet =  (int)(Math.random() * 4);
			String typeObjet = null;
			switch(randomTypeObjet) {
				case 0 :
					typeObjet = "Livre";
					break;
				case 1 :
					typeObjet = "CD";
					break;
				case 2 :
					typeObjet = "DVD";
					break;
				case 3 :
					typeObjet = "JeuSociete";
					break;
				case 4 :
					typeObjet = "Ordinateur";
					break;
			}
			o = new Objet(typeObjet+i,"auteur"+i,(float)Math.random()*10,typeObjet,1,i+"394745838"+i);
			od.create(o);
		}
	}
	
	
	/*
	public static void main(String[] args) {
		//GestionBD.genererObjets();
		ObjetDAO od = new ObjetDAO();
		
		
		for (int i = 1506; i < 2006; i++) {
			od.delete(i);
		}
		
	}
	*/
	
}
