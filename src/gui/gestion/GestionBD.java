package gui.gestion;
import metier.*;

import java.util.ArrayList;

import dao.*;

public class GestionBD {
	private ArrayList<Objet> listeObjets;
	
	/**
	 * Génère des objets aléatoires en BD pour des tests
	 * @param nombre nombre d'objet à générer
	 */
	public static void genererObjets(int nombre) {
		ObjetDAO od = new ObjetDAO();
		Objet o = null;
		int randomTypeObjet = 0;
		for (int i=0;i<nombre;i++) {
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
	
	/**
	 * Permet de générer un bibliothécaire (pas possible par le logiciel)
	 * @param identifiant
	 * @param mdp
	 * @param nom
	 * @param prenom
	 */
	public static void genererBibliothecaire(String identifiant, String mdp, String nom, String prenom) {
		CompteDAO cd = new CompteDAO();
		BibliothecaireDAO bd = new BibliothecaireDAO();
		Compte c = new Compte(identifiant,GestionMdp.hash(mdp),GestionDate.getDateFromLocalDate(),"Bibliothécaire");
		Bibliothecaire b = new Bibliothecaire(nom, prenom, c);
		if (!cd.exists(identifiant)) {
			cd.create(c);
			bd.create(b);
		} 
	}
	
}
