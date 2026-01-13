package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.ObjetDAO;
import metier.Objet;

public class PageStocks extends JPanel implements IPage {
	
	private CompteUtilisateur user;
	private JLabel mainText;
	private JScrollPane scroll;
	private JList liste;

	public PageStocks() {
		initialiserUI();
		majUI();
	}

	/**
	 * Récupère les infos de l'utilisateur connecté
	 * @param user utilisateur (client ou bibliothecaire)
	 */
	@Override
	public void setUser(CompteUtilisateur user) {
		this.user = user;
		majUI();
	}
	

	/**
	 * Initialise les éléments de l'interface "dynamiques" (dépendant de l'utilisateur)
	 * Et crée les éléments "statiques".
	 */
	private void initialiserUI() {
		setLayout(new BorderLayout());
		mainText = new JLabel();
		add(mainText, BorderLayout.NORTH);
		DefaultListModel<String> listModel = new DefaultListModel<>();
		liste = new JList(listModel);
		
		
		ObjetDAO od = new ObjetDAO();
		ArrayList<Objet> listeObjets = od.getListeObjet();
		

        String[] colonnes = {
            "objetId",
            "Nom",
            "auteur",
            "prix",
            "typeObjet",
            "disponibilité",
            "Référence"
        };
        
        DefaultTableModel tabRes = new DefaultTableModel(colonnes, 0);
        JTable table = new JTable(tabRes);
		
		/*
		for (Objet o : listeObjets) {
			listModel.addElement(o.getObjetId() + " | " + 
								o.getNom() + " | " + o.getAuteur()
								+ " | " + o.getPrix() + " | "  + o.getTypeObjet()
								 + " | " + o.getDisponibilite() + " | "
								+ o.getReference());
			listModel.addElement("-------------------------------------------------------"
					+ "------------------------------------------------------------------"
					+ "-----------------------------------------------------------------");
			/*
			for (int i = 0; i < 15; i++) {
				listModel.addElement("");
			}
			
			
		}
		*/
        
        for (Objet o : listeObjets) {
        	Object[] ligne = {
        			o.getObjetId(),
        			o.getNom(),
        			o.getAuteur(),
        			o.getPrix(),
        			o.getTypeObjet(),
        			o.getDisponibilite(),
        			o.getReference()
        	};
        	
        	tabRes.addRow(ligne);
        }
		
		scroll = new JScrollPane(table);
		JPanel center = new JPanel(new GridLayout(1,2));
		JLabel test = new JLabel("test");
		center.add(scroll);
		JPanel partieDroite = new JPanel(new BorderLayout());
		center.add(partieDroite);
		JTextField barreRecherche = new JTextField(50);
		partieDroite.add(barreRecherche, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		
	}
	
	/**
	 * Crée les élements dynamiques (dépendant de l'utilisateur)
	 */
	private void majUI() {
		if (user == null) {
			mainText.setText("En attente de connexion");
		} else {
			mainText.setText("Stock actuel de la bibliothèque");
			//compléter ici pour les choses qui nécéssitent les infos de l'utilisateur
		}
	}
	
	
	
	
	
}
