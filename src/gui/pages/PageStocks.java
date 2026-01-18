package gui.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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
import javax.swing.table.JTableHeader;

import dao.ObjetDAO;
import gui.gestion.CompteUtilisateur;
import gui.gestion.GestionUIStyle;
import metier.Objet;

public class PageStocks extends JPanel implements IPageMaj {
	
	private Runnable rb;
	private CompteUtilisateur user;
	private JLabel mainText;
	private JScrollPane scroll;
	private JList liste;
	private JTextField barreRecherche;

	private DefaultTableModel tabRes;
	private ArrayList<Objet> listeObjets;
	private JPanel center;
	private JLabel titreRecherche;
	
	private final Color btnColor = GestionUIStyle.getButtonColor();
	private final Color bgColor = GestionUIStyle.getBgColor();
	private final Color txtColor = GestionUIStyle.getTextColor();
	
	private int count = 0;

	public PageStocks(Runnable rb) {
		this.rb = rb;
		initialiserUI();
		majUI();
	}

	/**
	 * Récupère les infos de l'utilisateur connecté
	 * @param user utilisateur (client ou bibliothecaire)
	 */
	
	public void setUser(CompteUtilisateur user) {
		this.user = user;
		majUI();
	}
	

	/**
	 * Initialise les éléments de l'interface "dynamiques" (dépendant de l'utilisateur)
	 * Et crée les éléments "statiques".
	 */
	@Override
	public void initialiserUI() {
		setLayout(new BorderLayout());
		setBackground(bgColor);
		
		mainText = new JLabel();
		add(mainText, BorderLayout.NORTH);
		DefaultListModel<String> listModel = new DefaultListModel<>();
		liste = new JList(listModel);
		
		
		ObjetDAO od = new ObjetDAO();
		listeObjets = od.getListeObjet();
		

        String[] colonnes = {
            "objetId",
            "Nom",
            "auteur",
            "prix",
            "typeObjet",
            "disponibilité",
            "Référence"
        };
        
        tabRes = new DefaultTableModel(colonnes, 0);
        JTable table = new JTable(tabRes);
        table.setBackground(btnColor);
        table.setForeground(txtColor);
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(btnColor);
        header.setForeground(txtColor);
		
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
        
       
		
		center = new JPanel(new BorderLayout());
		center.setBackground(bgColor);
		add(center, BorderLayout.CENTER);
		
		 
		
		scroll = new JScrollPane(table);
		scroll.getViewport().setBackground(bgColor);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		
		
		JLabel test = new JLabel("test");
		
		center.add(scroll, BorderLayout.CENTER);
		JPanel nord = new JPanel(new GridLayout(1,3));
		nord.setBackground(bgColor);
		
		center.add(nord, BorderLayout.NORTH);
		
		//JPanel recherche = new JPanel(new GridBagLayout());
		
		
		
		titreRecherche = new JLabel("Rechercher référence objet");
		barreRecherche = new JTextField(40);
		barreRecherche.setMinimumSize(new Dimension(100,20));
		barreRecherche.setMaximumSize(new Dimension(200,20));
		//barreRecherche.setPreferredSize(new Dimension(50,20));
		nord.add(titreRecherche);
		nord.add(barreRecherche);		
		
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
		loupe.setBackground(btnColor);
		
		nord.add(loupe);
		
		/*
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
		*/
		loupe.addActionListener(e -> {
			count++;
			if (count%2 == 0) {
				rb.run();
			} else {
				majUI();
				titreRecherche.setText("Rechercher référence objet");
			}
		});
		
		rechercherObjet();
	}
	
	/**
	 * Crée les élements dynamiques (dépendant de l'utilisateur) ou qui doivent se mettre à jour au lancement de la page
	 */
	@Override
	public void majUI() {
		if (user == null) {
			mainText.setText("En attente de connexion");
		} else {
			mainText.setText("Stock actuel de la bibliothèque");
			//compléter ici pour les choses qui nécéssitent les infos de l'utilisateur
			ObjetDAO od = new ObjetDAO();
			listeObjets = od.getListeObjet();
			tabRes.setRowCount(0);
			
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
		}
	}
	
	
	public void rechercherObjet() {
		ObjetDAO od = new ObjetDAO();
		String recherche = barreRecherche.getText();
		Objet o1 = od.read(recherche);
		if (o1 == null) {
			tabRes.setRowCount(0);
			titreRecherche.setText("Aucun objet trouvé");
		} else {
			listeObjets = od.getListeObjet(recherche);
			tabRes.setRowCount(0);
			
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
		}
	}
	
	
	
	
	
}
