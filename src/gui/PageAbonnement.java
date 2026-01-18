package gui;

import javax.swing.JLabel;

import dao.AbonnementDAO;
import dao.ClientDAO;
import dao.CompteDAO;
import dao.EmpruntDAO;
import metier.*;

import java.awt.*;

import javax.swing.*;

public class PageAbonnement extends JPanel implements IPageMaj {
	
	private CompteUtilisateur user;
	
	private JLabel mainText;
	private JTextField champNom;
	private JTextField champPrenom;
	private JTextField champCompte;
	private JTextField champNom1;
	private JTextField champPrenom1;
	private JTextField champCompte1;
	private JComboBox choixAbonnement;
	private JLabel confirmationCreation;
	private JLabel confirmationSuppression;
	private final ClientDAO cld = new ClientDAO();
	private final CompteDAO cd = new CompteDAO();
	private final AbonnementDAO abd = new AbonnementDAO();
	private final Color btnColor = GestionUIStyle.getButtonColor();
	private final Color bgColor = GestionUIStyle.getBgColor();
	private final Color txtColor = GestionUIStyle.getTextColor();

	
	public PageAbonnement() {
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
		mainText.setHorizontalAlignment(SwingConstants.CENTER);
		add(mainText, BorderLayout.NORTH);
		
		JPanel grid = new JPanel(new GridLayout(1,2));
		JPanel gauche = new JPanel(new GridBagLayout());
		JPanel droite = new JPanel(new GridBagLayout());
		JScrollPane scrollGauche = new JScrollPane(gauche);
		JScrollPane scrollDroite = new JScrollPane(droite);
		gauche.setBackground(bgColor);
		droite.setBackground(bgColor);
		scrollGauche.setBackground(bgColor);
		scrollDroite.setBackground(bgColor);
		scrollGauche.setBorder(BorderFactory.createEmptyBorder());
		scrollDroite.setBorder(BorderFactory.createEmptyBorder());
		grid.add(scrollGauche);
		grid.add(scrollDroite);
		add(grid, BorderLayout.CENTER);
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(0,0,35,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.2;
		JLabel titreGauche = new JLabel("Ajouter/modifier un abonnement :");
		gauche.add(titreGauche, gbc);
		
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridy = 1;
		gbc.weightx = 1;
		JLabel nom = new JLabel("Nom du client");
		gauche.add(nom, gbc);

		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(0,0,50,0);
		champNom = new JTextField(30);
		champNom.setMinimumSize(new Dimension(600,20));
		champNom.setPreferredSize(new Dimension(600,20));
		gauche.add(champNom, gbc);
		
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridy = 3;
		gbc.weightx = 1.0;
		JLabel prenom = new JLabel("Prénom du client");
		gauche.add(prenom, gbc);
		
		
		gbc.insets = new Insets(0,0,50,0);
		gbc.gridy = 4;
		gbc.weightx = 1.0;
		champPrenom = new JTextField(30);
		champPrenom.setMinimumSize(new Dimension(600,20));
		champPrenom.setPreferredSize(new Dimension(600,20));
		gauche.add(champPrenom, gbc);
		
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridy = 5;
		gbc.weightx = 1.0;
		JLabel compte = new JLabel("Identifiant du compte client");
		gauche.add(compte, gbc);
		
		
		gbc.insets = new Insets(0,0,25,0);
		gbc.gridy = 6;
		gbc.weightx = 1.0;
		champCompte = new JTextField(30);
		champCompte.setMinimumSize(new Dimension(600,20));
		champCompte.setPreferredSize(new Dimension(600,20));
		gauche.add(champCompte, gbc);
		
		
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridy = 7;
		gbc.weightx = 1;
		JLabel abonnement = new JLabel("Abonnement");
		gauche.add(abonnement, gbc);
		
		gbc.insets = new Insets(0,0,35,0);
		gbc.gridy = 8;
		gbc.weightx = 1;
		String[] listeAbonnement = {"Classique","Etudiant","Premium"};
		choixAbonnement = new JComboBox(listeAbonnement);
		gauche.add(choixAbonnement, gbc);
		
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridy = 9;
		gbc.weightx = 1;
		JButton ajouter = new JButton("Ajouter");
		ajouter.setBackground(btnColor);
		ajouter.setForeground(txtColor);
		gauche.add(ajouter, gbc);
		
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridy = 10;
		confirmationCreation = new JLabel("");
		gauche.add(confirmationCreation, gbc);
		
		ajouter.addActionListener(e -> ajouterAbonnement());
		
		
		// DROITE
		
		GridBagConstraints gbc1 = new GridBagConstraints();
		
		gbc1.insets = new Insets(0,0,75,0);
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.weightx = 0.2;
		JLabel titreDroite = new JLabel("Supprimer un abonnement : ");
		droite.add(titreDroite, gbc1);
		
		gbc1.insets = new Insets(0,0,0,0);
		gbc1.gridy = 1;
		gbc1.weightx = 1;
		JLabel nom1 = new JLabel("Nom du client");
		droite.add(nom1, gbc1);

		gbc1.gridy = 2;
		gbc1.weightx = 1.0;
		gbc1.insets = new Insets(0,0,50,0);
		champNom1 = new JTextField(30);
		champNom1.setMinimumSize(new Dimension(600,20));
		champNom1.setPreferredSize(new Dimension(600,20));
		droite.add(champNom1, gbc1);
		
		gbc1.insets = new Insets(0,0,0,0);
		gbc1.gridy = 3;
		gbc1.weightx = 1.0;
		JLabel prenom1 = new JLabel("Prénom du client");
		droite.add(prenom1, gbc1);
		
		
		gbc1.insets = new Insets(0,0,50,0);
		gbc1.gridy = 4;
		gbc1.weightx = 1.0;
		champPrenom1 = new JTextField(30);
		champPrenom1.setMinimumSize(new Dimension(600,20));
		champPrenom1.setPreferredSize(new Dimension(600,20));
		droite.add(champPrenom1, gbc1);
		
		gbc1.insets = new Insets(0,0,0,0);
		gbc1.gridy = 5;
		gbc1.weightx = 1.0;
		JLabel compte1 = new JLabel("Identifiant du compte client");
		droite.add(compte1, gbc1);
		
		
		gbc1.insets = new Insets(0,0,50,0);
		gbc1.gridy = 6;
		gbc1.weightx = 1.0;
		champCompte1 = new JTextField(30);
		champCompte1.setMinimumSize(new Dimension(600,20));
		champCompte1.setPreferredSize(new Dimension(600,20));
		droite.add(champCompte1, gbc1);
		
		
		gbc1.insets = new Insets(0,0,0,0);
		gbc1.gridy = 7;
		gbc1.weightx = 1;
		JLabel abonnement1 = new JLabel("Abonnement");
		droite.add(abonnement1, gbc1);
		
		gbc1.insets = new Insets(0,0,0,0);
		gbc1.gridy = 8;
		gbc1.weightx = 1;
		JButton supprimer = new JButton("Supprimer");
		supprimer.setBackground(btnColor);
		supprimer.setForeground(txtColor);
		droite.add(supprimer, gbc1);
		
		gbc1.insets = new Insets(0,0,0,0);
		gbc1.gridy = 9;
		confirmationSuppression = new JLabel("");
		droite.add(confirmationSuppression, gbc);
		
		supprimer.addActionListener(e -> supprimerAbonnement());
		
	}
	
	/**
	 * Crée les élements dynamiques (dépendant de l'utilisateur)
	 */
	@Override
	public void majUI() {
		if (user == null) {
			mainText.setText("En attente de connexion");
			mainText.setFont(new Font("Arial", Font.BOLD, 24));
		} else {
			mainText.setText("Gestion abonnements des clients");
			mainText.setFont(new Font("Arial", Font.BOLD, 24));
			//compléter ici pour les choses qui nécéssitent les infos de l'utilisateur
		}
	}
	
	private void ajouterAbonnement() {
		try {
			validerNom();
			validerPrenom();
			validerCompteClient();
		} catch (SaisieInvalideException e) {
			afficherErreurCreation(e.getMessage());
		}
		
	}
	
	private void supprimerAbonnement() {
		try {
			validerNomSupp();
			validerPrenomSupp();
			validerCompteClientSupp();
		} catch (SaisieInvalideException e) {
			afficherErreurSuppression(e.getMessage());
		}
	}
	
	private void afficherErreurCreation(String message) {
		confirmationCreation.setForeground(Color.RED);
		confirmationCreation.setText(message);
	}
	
	private void afficherErreurSuppression(String message) {
		confirmationSuppression.setForeground(Color.RED);
		confirmationSuppression.setText(message);
	}
	
	private void validerNom() throws SaisieInvalideException {
		confirmationSuppression.setText(null);
		String t = champNom.getText();
		if (t.isEmpty()) {
			throw new SaisieInvalideException("Vous devez saisir un nom");
		}
		if (t.contains("'") || t.contains("\"") || t.contains(";") || t.contains("/") || t.contains("--") || t.contains("*")) {
			throw new SaisieInvalideException("Caractères invalides");
		}
	}
	
	private void validerPrenom() throws SaisieInvalideException {
		String t = champPrenom.getText();
		if (t.isEmpty()) {
			throw new SaisieInvalideException("Vous devez saisir un prénom");
		}
		if (t.contains("'") || t.contains("\"") || t.contains(";") || t.contains("/") || t.contains("--") || t.contains("*")) {
			throw new SaisieInvalideException("Caractères invalides");
		}
	}
	
	private void validerCompteClient() throws SaisieInvalideException {
		String nom = champNom.getText();
		String prenom = champPrenom.getText();
		String compte = champCompte.getText();
		Compte c = cd.read(compte);
		if (c != null) {
			Integer id = cld.getClientFromCompte(c.getCompteId());
			Client cl = cld.read(id);
			String abonnement = choixAbonnement.getSelectedItem().toString();
			Abonnement ab = abd.read(abonnement);
			System.out.println(ab);
			if (cl == null) {
				throw new SaisieInvalideException("Le compte client n'existe pas");
			}
			if (cl != null && (!(cl.getNom().equals(nom)) || !(cl.getPrenom().equals(prenom)))) {
				throw new SaisieInvalideException("Le client ne correspond pas au compte");
			}
			if (cl != null && cl.getNom().equals(nom) && cl.getPrenom().equals(prenom)) {
				cl.setAbonnement(ab);
				cld.update(cl);
				String texteAbonnement;
				if (cl.getAbonnement() != null) {
				    texteAbonnement = cl.getAbonnement().getTypeAbonnement();
				} else {
				    texteAbonnement = "Aucun abonnement";
				}

				confirmationCreation.setText(
				    "Le client " + cl.getNom() + " " + cl.getPrenom()
				    + " a désormais l'abonnement " + texteAbonnement);
				confirmationCreation.setForeground(Color.black);
				champNom.setText(null);
				champPrenom.setText(null);
				champCompte.setText(null);
			}
			cl = cld.read(id);
			System.out.println(cl);
		} else {
			throw new SaisieInvalideException("Le compte n'existe pas");
		}
	}
	
	private void validerNomSupp() throws SaisieInvalideException {
		confirmationCreation.setText("");
		String t = champNom1.getText();
		if (t.isEmpty()) {
			throw new SaisieInvalideException("Vous devez saisir un nom");
		}
		if (t.contains("'") || t.contains("\"") || t.contains(";") || t.contains("/") || t.contains("--") || t.contains("*")) {
			throw new SaisieInvalideException("Caractères invalides");
		}
	}
	
	private void validerPrenomSupp() throws SaisieInvalideException {
		String t = champPrenom1.getText();
		if (t.isEmpty()) {
			throw new SaisieInvalideException("Vous devez saisir un prénom");
		}
		if (t.contains("'") || t.contains("\"") || t.contains(";") || t.contains("/") || t.contains("--") || t.contains("*")) {
			throw new SaisieInvalideException("Caractères invalides");
		}
	}
	
	private void validerCompteClientSupp() throws SaisieInvalideException {
		String nom = champNom1.getText();
		String Prenom = champPrenom1.getText();
		String compte = champCompte1.getText();
		Compte c = cd.read(compte);
		if (c != null) {
			Integer id = cld.getClientFromCompte(c.getCompteId());
			Client cl = cld.read(id);
			if (cl == null) {
				throw new SaisieInvalideException("Le compte client n'existe pas");
			}
			if (cl != null && (!cl.getNom().equals(nom) || !cl.getPrenom().equals(Prenom))) {
				throw new SaisieInvalideException("Le client ne correspond pas au compte");
			}
			if (cl != null && cl.getNom().equals(nom) && cl.getPrenom().equals(Prenom)) {
				cl.setAbonnement(null);
				cld.update(cl);
				confirmationSuppression.setText("L'abonnement de " + cl.getNom() + " " + cl.getPrenom() + " a bien été supprimé");
				confirmationSuppression.setForeground(Color.black);
				champNom1.setText(null);
				champPrenom1.setText(null);
				champCompte1.setText(null);
			}
			verifierRetourEmpruntsSupp(c);
			cl = cld.read(id);
			System.out.println(cl);
		} else {
			throw new SaisieInvalideException("Le compte n'existe pas");
		}
	}
	
	private void verifierRetourEmpruntsSupp(Compte c) throws SaisieInvalideException {
		EmpruntDAO ed = new EmpruntDAO();
		//faire des DAO généraux pour pas en recréer tout le temps
		if (!(ed.getListeEmpruntsByClientId(cld.getClientFromCompte(c.getCompteId())).isEmpty())) {
			throw new SaisieInvalideException("Le client doit retourner tous ses emprunts avant de supprimer son abonnement");
		}
	}
	
}
