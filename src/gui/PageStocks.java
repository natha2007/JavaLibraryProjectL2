package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	private JTextField barreRecherche;
	private JLabel resultat;

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
		
		JPanel center = new JPanel(new GridLayout(1,2));
		add(center, BorderLayout.CENTER);
		
		scroll = new JScrollPane(table);
		JLabel test = new JLabel("test");
		
		center.add(scroll);
		JPanel partieDroite = new JPanel(new BorderLayout());
		
		center.add(partieDroite);
		
		JPanel recherche = new JPanel(new GridBagLayout());
		partieDroite.add(recherche, BorderLayout.NORTH);
		
		
		JLabel titreRecherche = new JLabel("Rechercher référence objet");
		barreRecherche = new JTextField(40);
		
		resultat = new JLabel("");
		partieDroite.add(resultat, BorderLayout.CENTER);
		
		BufferedImage loupeFic = null;
		try {
			loupeFic = ImageIO.read(new File("img/loupe.png"));
		} catch (IOException e) {
			System.out.println("Fichier introuvable");
			e.printStackTrace();
		}
		ImageIcon imageLoupe = new ImageIcon(loupeFic);
		Image img = imageLoupe.getImage().getScaledInstance(16, 12, Image.SCALE_SMOOTH);
		JButton loupe = new JButton(new ImageIcon(img));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		recherche.add(titreRecherche, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.9;
		recherche.add(barreRecherche, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 0.1;
		recherche.add(loupe, gbc);
		
		loupe.addActionListener(e -> {
			rechercherObjet();
		});
		
		
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
	
	
	private void rechercherObjet() {
		ObjetDAO od = new ObjetDAO();
		String recherche = barreRecherche.getText();
		Objet o = od.read(recherche);
		if (o == null) {
			resultat.setText("aucun objet trouvé");
		} else {
			String disponibilite = "";
			if (o.getDisponibilite() == 1) {
				disponibilite = "disponible";
			} else {
				disponibilite = "non disponible";
			}
			resultat.setText(o.getObjetId() + " " + o.getNom() + " " + o.getAuteur()
							+ " " + o.getPrix() + " " + o.getTypeObjet() + " " + disponibilite
							+ " " + o.getReference());
		}
	}
	
	
	
	
	
}
