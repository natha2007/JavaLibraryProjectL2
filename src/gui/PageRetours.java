package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PageRetours extends JPanel implements IPage {

	private CompteUtilisateur user;

	// UI
	private JLabel mainText;
	private JTextField tfPrenom, tfNom, tfIdAbo, tfTypeObjet, tfReference;
	private JButton btnRetourner;
	private JLabel labelResultat;

	public PageRetours() {
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
	 * Initialise l'interface graphique
	 */
	private void initialiserUI() {
		setLayout(new BorderLayout());

		// texte principal
		mainText = new JLabel();
		add(mainText, BorderLayout.NORTH);

		// partie gauche
		JPanel panelGauche = new JPanel(new GridLayout(5, 2, 5, 5));

		panelGauche.add(new JLabel("Nom :"));
		tfNom = new JTextField();
		panelGauche.add(tfNom);

		panelGauche.add(new JLabel("Prénom :"));
		tfPrenom = new JTextField();
		panelGauche.add(tfPrenom);

		panelGauche.add(new JLabel("ID abonnement :"));
		tfIdAbo = new JTextField();
		panelGauche.add(tfIdAbo);

		panelGauche.add(new JLabel("Type objet :"));
		tfTypeObjet = new JTextField();
		panelGauche.add(tfTypeObjet);

		

		add(panelGauche, BorderLayout.WEST);

		// partie droite
		JPanel panelDroit = new JPanel();
		labelResultat = new JLabel(" ");
		panelDroit.add(labelResultat);
		
		btnRetourner = new JButton("Retourner");
		panelGauche.add(btnRetourner);
		
		add(panelDroit, BorderLayout.CENTER);

		// bouton retour
		btnRetourner.addActionListener(e -> traiterRetour());
	}

	/**
	 * MAJ ui
	 */
	private void majUI() {
		if (user == null) {
			mainText.setText("En attente de connexion");
			btnRetourner.setEnabled(false);
		} else {
			mainText.setText("Gestion des retours");
			btnRetourner.setEnabled(true);
		}
	}

	/**
	 * retour
	 */
	private void traiterRetour() {
		String nom = tfNom.getText();
		String prenom = tfPrenom.getText();
		String idAbo = tfIdAbo.getText();
		String typeObjet = tfTypeObjet.getText();

		if (!verifierDansBDD(nom, prenom, idAbo, typeObjet)) {
			labelResultat.setText("Client ou objet introuvable");
			return;
		}

		// Données normalement récupérées depuis la BDD
		
		String auteur = "Victor Hugo";
		String nomObjet = "Les Misérables";
		String reference = "REF-123";

		labelResultat.setText(
			"Le client " + nom + " " + prenom +
			" a retourné l'objet " + auteur + " " +
			nomObjet + " " + reference
		);
	}

	/**
	 *  verification BDD
	 */
	private boolean verifierDansBDD(String nom, String prenom, String idAbo, String typeObjet) {
		return !nom.isEmpty() && !prenom.isEmpty() && !idAbo.isEmpty();
	}
}
