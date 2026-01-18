package gui.pages;

import java.awt.*;
import java.text.NumberFormat;

import javax.swing.*;

import dao.ObjetDAO;
import gui.gestion.CompteUtilisateur;
import gui.gestion.GestionUIStyle;
import gui.gestion.SaisieInvalideException;
import metier.Objet;

public class PageCommandes extends JPanel implements IPageMaj {
	private CompteUtilisateur user;
	private JLabel mainText;
	private JLabel nom;
	private JTextField champNom;
	private JLabel erreurNom;
	private JLabel auteur;
	private JTextField champAuteur;
	private JLabel prix;
	private JFormattedTextField champPrix;
	private JLabel typeObjet;
	private JComboBox champTypeObjet;
	private JLabel reference;
	private JTextField champReference;
	private JLabel confirmation;
	private JPanel suiteMsg;
	private final Color btnColor = GestionUIStyle.getButtonColor();
	private final Color bgColor = GestionUIStyle.getBgColor();
	private final Color txtColor = GestionUIStyle.getTextColor();
	
	public PageCommandes() {
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
		
		JPanel head = new JPanel(new FlowLayout());
		head.setPreferredSize(new Dimension(0, 50));
		head.setBackground(bgColor);
		add(head, BorderLayout.NORTH);
		
		mainText = new JLabel();
		//mainText.setPreferredSize(new Dimension(1000,150));
		mainText.setHorizontalAlignment(SwingConstants.CENTER);
		mainText.setVerticalAlignment(SwingConstants.CENTER);
		head.add(mainText);
		
		
		JPanel grid = new JPanel(new GridLayout(3,2));
		add(grid, BorderLayout.CENTER);
		grid.setBackground(bgColor);
		
		JPanel zoneNom = new JPanel();
		zoneNom.setBackground(bgColor);
		zoneNom.setLayout(new BoxLayout(zoneNom, BoxLayout.Y_AXIS));
		nom = new JLabel("nom/titre : ");
		champNom = new JTextField(30);
		erreurNom = new JLabel("");
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
		zoneNom.add(erreurNom);
		
		JPanel zoneAuteur= new JPanel();
		zoneAuteur.setBackground(bgColor);
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
		zonePrix.setBackground(bgColor);
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
		zoneObjet.setBackground(bgColor);
		zoneObjet.setLayout(new BoxLayout(zoneObjet, BoxLayout.Y_AXIS));
		typeObjet = new JLabel("typeObjet");
		String[] listeTypeObjet = {"Livre","CD","DVD","JeuSociete","Ordinateur"};
		champTypeObjet = new JComboBox(listeTypeObjet);
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
		zoneReference.setBackground(bgColor);
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
		commande.setBackground(btnColor);
		commande.setForeground(txtColor); 
		d = commande.getPreferredSize();
//        d.height = 25;
//        commande.setMaximumSize(d);
//        commande.setPreferredSize(d);
		commande.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel boutonLayout = new JPanel();
		boutonLayout.setLayout(new BoxLayout(boutonLayout, BoxLayout.Y_AXIS));
        confirmation = new JLabel("");
        confirmation.setAlignmentX(Component.CENTER_ALIGNMENT);
        //confirmation.setForeground(new Color(255,0,0,255));
        
        boutonLayout.setBackground(bgColor);
        boutonLayout.add(commande);
        suiteMsg = new JPanel();
        suiteMsg.setLayout(new BoxLayout(suiteMsg, BoxLayout.Y_AXIS));
        boutonLayout.add(confirmation);
        boutonLayout.add(suiteMsg);
		grid.add(boutonLayout);
		suiteMsg.add(confirmation);
		commande.addActionListener(e -> ajouterCommande());
		
	}
	
	/**
	 * Crée les élements dynamiques (dépendant de l'utilisateur)
	 */
	@Override
	public void majUI() {
		if (user == null) {
			mainText.setText("En attente de connexion");
		} else {
			mainText.setText("Remplissez le formulaire ci-dessous pour commander un nouvel objet dans la bibliothèque");
			//compléter ici pour les choses qui nécéssitent les infos de l'utilisateur
		}
	}
	
	private void ajouterCommande() {
		try {
			validerSaisie();
			creerCommande();
		} catch (SaisieInvalideException s) {
			afficherErreur(s.getMessage());
		}
	}
	
	private void creerCommande() {
		String nom = champNom.getText();
		String auteur = champAuteur.getText();
		Number n = (Number) champPrix.getValue();
		float prix = n.floatValue();
		String typeObjet = champTypeObjet.getSelectedItem().toString();
		String reference = champReference.getText();
		ObjetDAO od = new ObjetDAO();
		
		Objet o = new Objet(nom, auteur, prix, typeObjet,1,reference);
		od.create(o);
		champNom.setText(null);
		champAuteur.setText(null);
		champPrix.setText(null);
		champReference.setText(null);
		confirmation.setText("vous avez bien commandé le " + typeObjet + " : " + nom);
	}
	
	private void afficherErreur(String message) {
	    confirmation.setForeground(Color.RED);
	    if (message.length() < 89) {
	    	confirmation.setText(message);
	    } else {
	    	String newMessage = "";
	    	String newMessage2 = "";
	    	for (int i = 0; i < (message.length()/2)-1;i++) {
	    		newMessage += message.charAt(i);
	    	}
	    	for (int i = (message.length()/2)-1; i < message.length();i++) {
	    		newMessage2 += message.charAt(i);
	    	}
	    	confirmation.setText(newMessage);
	    	JLabel nm2 = new JLabel(newMessage2);
	    	nm2.setForeground(Color.red);
	    	nm2.setAlignmentX(Component.CENTER_ALIGNMENT);
	    	suiteMsg.add(nm2);
	    }
	    
	}
	
	private void validerSaisie() throws SaisieInvalideException {
		validerNom();
		validerPrix();
		validerAuteur();
		validerReference();
	}
	
	private void validerNom() throws SaisieInvalideException {
		String t = champNom.getText();
		if (t.isEmpty()) {
			throw new SaisieInvalideException("Vous devez saisir un nom");
		}
		if (t.contains("'") || t.contains("\"") || t.contains(";") || t.contains("/") || t.contains("--") || t.contains("*")) {
			throw new SaisieInvalideException("Caractères invalides");
		}
	}
	
	private void validerPrix() throws SaisieInvalideException {
		Number n = (Number) champPrix.getValue();
		if (n == null || n.floatValue() <= 0) {
			throw new SaisieInvalideException("Le prix doit être supérieur ou égal à 0");
		}
	}
	
	private void validerAuteur() throws SaisieInvalideException {
		String t = champAuteur.getText();
		if (t.isEmpty()) {
			throw new SaisieInvalideException("Vous devez saisir un auteur");
		}
		if (t.contains("'") || t.contains("\"") || t.contains(";") || t.contains("/") || t.contains("--") || t.contains("*")) {
			throw new SaisieInvalideException("Caractères invalides");
		}
	}
	
	/*
	private void validerTypeObjet() throws SaisieInvalideException {
		String t = champTypeObjet.getText();
		if (t.isEmpty()) {
			throw new SaisieInvalideException("Vous devez saisir un type d'objet");
		}
		if (t.contains("'") || t.contains("\"") || t.contains(";") || t.contains("/") || t.contains("--") || t.contains("*")) {
			throw new SaisieInvalideException("Caractères invalides");
		}
	}
	*/
	
	private void validerReference() throws SaisieInvalideException {
		ObjetDAO od = new ObjetDAO();
		String t = champReference.getText();
		String nom = champNom.getText();
		Number n = (Number) champPrix.getValue();
		float prix = Math.round(n.floatValue() * 100) / 100f;
		String auteur = champAuteur.getText();
		Objet o = od.read(t);
		
		if (champReference.getText().isEmpty()) {
			throw new SaisieInvalideException("Vous devez saisir un champ référence");
		}
		if (t.contains("'") || t.contains("\"") || t.contains(";") || t.contains("/") || t.contains("--") || t.contains("*")) {
			throw new SaisieInvalideException("Caractères invalides");
		}
		if (o != null && (!(o.getNom().equals(nom)) || !(o.getAuteur().equals(auteur)) || (Math.round(o.getPrix() * 100) / 100f) != prix )){
			System.out.println("différent");
			throw new SaisieInvalideException("" + (Math.round(o.getPrix() * 100) / 100f) + " " + prix);
		}
	}
}
