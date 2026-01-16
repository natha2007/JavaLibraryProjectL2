package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout; // je peux l'enlever mais je le laisse au cas ou 
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
		setLayout(new BorderLayout(10, 10));

		
		mainText = new JLabel("Gestion des retours", SwingConstants.CENTER);
		mainText.setFont(new Font("Arial", Font.BOLD, 24));
		add(mainText, BorderLayout.NORTH);

		
		JPanel panelGauche = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.fill = GridBagConstraints.HORIZONTAL;

		Dimension fieldSize = new Dimension(220, 22);

		// Nom
		c.gridx = 0; c.gridy = 0;
		panelGauche.add(new JLabel("Nom :"), c);
		c.gridx = 1;
		tfNom = new JTextField();
		tfNom.setPreferredSize(fieldSize);
		panelGauche.add(tfNom, c);

		// prenom
		c.gridx = 0; c.gridy++;
		panelGauche.add(new JLabel("Prénom :"), c);
		c.gridx = 1;
		tfPrenom = new JTextField();
		tfPrenom.setPreferredSize(fieldSize);
		panelGauche.add(tfPrenom, c);

		// abonneementId
		c.gridx = 0; c.gridy++;
		panelGauche.add(new JLabel("ID abonnement :"), c);
		c.gridx = 1;
		tfIdAbo = new JTextField();
		tfIdAbo.setPreferredSize(fieldSize);
		panelGauche.add(tfIdAbo, c);

		// Type objet
		c.gridx = 0; c.gridy++;
		panelGauche.add(new JLabel("Type objet :"), c);
		c.gridx = 1;
		tfTypeObjet = new JTextField();
		tfTypeObjet.setPreferredSize(fieldSize);
		panelGauche.add(tfTypeObjet, c);

		// panel droit 
		
		JPanel panelDroit = new JPanel(new GridBagLayout());
		GridBagConstraints d = new GridBagConstraints();
		d.insets = new Insets(5, 5, 5, 5);
		d.fill = GridBagConstraints.HORIZONTAL;

		// ref
		d.gridx = 0; d.gridy = 0;
		panelDroit.add(new JLabel("Référence :"), d);
		d.gridx = 1;
		tfReference = new JTextField();
		tfReference.setPreferredSize(fieldSize);
		panelDroit.add(tfReference, d);

		// Bouton retourner (à droite + plus grand)
		
		d.gridx = 0; d.gridy = 1; d.gridwidth = 2;
		btnRetourner = new JButton("Retourner");
		btnRetourner.setPreferredSize(new Dimension(200, 45));
		panelDroit.add(btnRetourner, d);

		add(panelGauche, BorderLayout.WEST);
		add(panelDroit, BorderLayout.EAST);

		// centree 
		labelResultat = new JLabel(" ", SwingConstants.CENTER);
		add(labelResultat, BorderLayout.CENTER);

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

	private void traiterRetour() {
		String nom = tfNom.getText();
		String prenom = tfPrenom.getText();
		String idAbo = tfIdAbo.getText();
		String typeObjet = tfTypeObjet.getText();
		String reference = tfReference.getText();

		if (!verifierDansBDD(nom, prenom, idAbo, typeObjet, reference)) {
			labelResultat.setText("Client ou objet introuvable");
			return;
		}

		String auteur = "Victor Hugo";
		String nomObjet = "Les Misérables";

		labelResultat.setText(
			"Le client " + nom + " " + prenom +
			" a retourné l'objet " + auteur + " " +
			nomObjet + " " + reference
		);
	}

	private boolean verifierDansBDD(String nom, String prenom, String idAbo,
	                                String typeObjet, String reference) {
		return !nom.isEmpty()
			&& !prenom.isEmpty()
			&& !idAbo.isEmpty()
			&& !reference.isEmpty();
	}
}