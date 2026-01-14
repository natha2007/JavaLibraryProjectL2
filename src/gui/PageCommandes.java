package gui;

import java.awt.*;
import java.text.NumberFormat;

import javax.swing.*;

import dao.ObjetDAO;
import metier.Objet;

public class PageCommandes extends JPanel implements IPage {
	private CompteUtilisateur user;
	private JLabel mainText;
	private JLabel nom;
	private JTextField champNom;
	private JLabel auteur;
	private JTextField champAuteur;
	private JLabel prix;
	private JFormattedTextField champPrix;
	private JLabel typeObjet;
	private JTextField champTypeObjet;
	private JLabel reference;
	private JTextField champReference;
	private JLabel confirmation;
	
	public PageCommandes() {
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
		
		JPanel head = new JPanel(new FlowLayout());
		head.setPreferredSize(new Dimension(0, 150));
		add(head, BorderLayout.NORTH);
		
		mainText = new JLabel();
		mainText.setPreferredSize(new Dimension(1000,150));
		mainText.setHorizontalAlignment(SwingConstants.CENTER);
		mainText.setVerticalAlignment(SwingConstants.CENTER);
		head.add(mainText);
		
		
		JPanel grid = new JPanel(new GridLayout(3,2));
		add(grid, BorderLayout.CENTER);
		
		JPanel zoneNom = new JPanel();
		zoneNom.setLayout(new BoxLayout(zoneNom, BoxLayout.Y_AXIS));
		nom = new JLabel("nom/titre : ");
		champNom = new JTextField(30);
		Dimension d = champNom.getPreferredSize();
        d.height = 25;
        champNom.setMaximumSize(d);
        champNom.setPreferredSize(d);
        nom.setAlignmentX(Component.CENTER_ALIGNMENT);
        champNom.setAlignmentX(Component.CENTER_ALIGNMENT);
        nom.setAlignmentY(Component.CENTER_ALIGNMENT);
        champNom.setAlignmentY(Component.CENTER_ALIGNMENT);
		grid.add(zoneNom);
		zoneNom.add(nom);
		zoneNom.add(champNom);
		
		JPanel zoneAuteur= new JPanel();
		zoneAuteur.setLayout(new BoxLayout(zoneAuteur, BoxLayout.Y_AXIS));
		auteur = new JLabel("auteur/constructeur : ");
		champAuteur = new JTextField(30);
		auteur.setAlignmentX(Component.CENTER_ALIGNMENT);
	    champAuteur.setAlignmentX(Component.CENTER_ALIGNMENT);
	    auteur.setAlignmentY(Component.CENTER_ALIGNMENT);
	    champAuteur.setAlignmentY(Component.CENTER_ALIGNMENT);
		d = champAuteur.getPreferredSize();
        d.height = 25;
        champAuteur.setMaximumSize(d);
        champAuteur.setPreferredSize(d);
		grid.add(zoneAuteur);
		zoneAuteur.add(auteur);
		zoneAuteur.add(champAuteur);
		
		JPanel zonePrix = new JPanel();
		zonePrix.setLayout(new BoxLayout(zonePrix, BoxLayout.Y_AXIS));
		
		prix = new JLabel("prix : ");
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setGroupingUsed(false);
		champPrix = new JFormattedTextField(format);
		champPrix.setColumns(30);
		prix.setAlignmentX(Component.CENTER_ALIGNMENT);
	    champPrix.setAlignmentX(Component.CENTER_ALIGNMENT);
	    prix.setAlignmentY(Component.CENTER_ALIGNMENT);
	    champPrix.setAlignmentY(Component.CENTER_ALIGNMENT);
		d = champPrix.getPreferredSize();
        d.height = 25;
        champPrix.setMaximumSize(d);
        champPrix.setPreferredSize(d);
		grid.add(zonePrix);
		zonePrix.add(prix);
		zonePrix.add(champPrix);
		
		JPanel zoneObjet = new JPanel();
		zoneObjet.setLayout(new BoxLayout(zoneObjet, BoxLayout.Y_AXIS));
		typeObjet = new JLabel("typeObjet");
		champTypeObjet = new JTextField(30);
		typeObjet.setAlignmentX(Component.CENTER_ALIGNMENT);
	    champTypeObjet.setAlignmentX(Component.CENTER_ALIGNMENT);
	    typeObjet.setAlignmentY(Component.CENTER_ALIGNMENT);
	    champTypeObjet.setAlignmentY(Component.CENTER_ALIGNMENT);
		d = champTypeObjet.getPreferredSize();
        d.height = 25;
        champTypeObjet.setMaximumSize(d);
        champTypeObjet.setPreferredSize(d);
		grid.add(zoneObjet);
		zoneObjet.add(typeObjet);
		zoneObjet.add(champTypeObjet);
		
		JPanel zoneReference = new JPanel();
		zoneReference.setLayout(new BoxLayout(zoneReference, BoxLayout.Y_AXIS));
		reference = new JLabel("reference");
		champReference = new JTextField(30);
		reference.setAlignmentX(Component.CENTER_ALIGNMENT);
	    champReference.setAlignmentX(Component.CENTER_ALIGNMENT);
	    reference.setAlignmentY(Component.CENTER_ALIGNMENT);
	    champReference.setAlignmentY(Component.CENTER_ALIGNMENT);
		d = champReference.getPreferredSize();
        d.height = 25;
        champReference.setMaximumSize(d);
        champReference.setPreferredSize(d);
		grid.add(zoneReference);
		zoneReference.add(reference);
		zoneReference.add(champReference);
		
		JButton commande = new JButton("Commander");
		d = commande.getPreferredSize();
//        d.height = 25;
//        commande.setMaximumSize(d);
//        commande.setPreferredSize(d);
        JPanel boutonLayout = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        confirmation = new JLabel("");
        
        add(confirmation, BorderLayout.SOUTH);
        boutonLayout.add(commande);
		grid.add(boutonLayout);
		
		commande.addActionListener(e -> ajouterCommande());
		
	}
	
	/**
	 * Crée les élements dynamiques (dépendant de l'utilisateur)
	 */
	private void majUI() {
		if (user == null) {
			mainText.setText("En attente de connexion");
		} else {
			mainText.setText("Remplissez le formulaire ci-dessous pour commander un nouvel objet dans la bibliothèque");
			//compléter ici pour les choses qui nécéssitent les infos de l'utilisateur
		}
	}
	
	private void ajouterCommande() {
		String nom = champNom.getText();
		String auteur = champAuteur.getText();
		Number n = (Number) champPrix.getValue();
		float prix;
		if (n != null) {
			prix = n.floatValue();
		} else {
			prix = 0f;
		}
		String typeObjet = champTypeObjet.getText();
		String reference = champReference.getText();
		ObjetDAO od = new ObjetDAO();
		Objet o = new Objet(nom, auteur, prix, typeObjet,1,reference);
		od.create(o);
		confirmation.setText("vous avez bien commandé le livre : " + od.read(reference).getNom());
		champNom.setText(null);
		champAuteur.setText(null);
		champPrix.setText(null);
		champTypeObjet.setText(null);
		champReference.setText(null);
	}
}
