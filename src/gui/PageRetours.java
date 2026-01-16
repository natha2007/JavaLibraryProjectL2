package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.ClientDAO;
import dao.CompteDAO;
import dao.EmpruntDAO;
import metier.Client;
import metier.Compte;
import metier.Emprunt;

public class PageRetours extends JPanel implements IPage {

    private ClientDAO clientDAO = new ClientDAO();
    private EmpruntDAO empruntDAO = new EmpruntDAO();
    private CompteDAO compteDAO = new CompteDAO();

    private JLabel mainText;
    private JTextField tfPrenom, tfNom, tfIdentifiant, tfTypeObjet, tfReference;
    private JButton btnRetourner;
    private JLabel labelResultat;

    public PageRetours() {
        initialiserUI();
        majUI();
    }

    @Override
    public void setUser(CompteUtilisateur user) {
        majUI();
    }

    private void initialiserUI() {
        setLayout(new BorderLayout(10, 10));

        // titre
        mainText = new JLabel("Gestion des retours", SwingConstants.CENTER);
        mainText.setFont(new Font("Arial", Font.BOLD, 24));
        add(mainText, BorderLayout.NORTH);

        // panel gauche
        JPanel panelGauche = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        Dimension fieldSize = new Dimension(220, 22);

        // Identifiant
        c.gridx = 0; c.gridy = 0;
        panelGauche.add(new JLabel("Identifiant :"), c);
        c.gridx = 1;
        tfIdentifiant = new JTextField();
        tfIdentifiant.setPreferredSize(fieldSize);
        panelGauche.add(tfIdentifiant, c);

        // nom 
        c.gridx = 0; c.gridy++;
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

        // type d'objet
        c.gridx = 0; c.gridy++;
        panelGauche.add(new JLabel("Type objet :"), c);
        c.gridx = 1;
        tfTypeObjet = new JTextField();
        tfTypeObjet.setPreferredSize(fieldSize);
        panelGauche.add(tfTypeObjet, c);

        add(panelGauche, BorderLayout.WEST);

        // panel droit 
        JPanel panelDroit = new JPanel(new GridBagLayout());
        GridBagConstraints d = new GridBagConstraints();
        d.insets = new Insets(5, 5, 5, 5);
        d.fill = GridBagConstraints.HORIZONTAL;

        // Référence
        d.gridx = 0; d.gridy = 0;
        panelDroit.add(new JLabel("Référence :"), d);
        d.gridx = 1;
        tfReference = new JTextField();
        tfReference.setPreferredSize(fieldSize);
        panelDroit.add(tfReference, d);

        // Bouton Retourner
        d.gridx = 0; d.gridy = 1; d.gridwidth = 2;
        btnRetourner = new JButton("Retourner");
        btnRetourner.setPreferredSize(new Dimension(200, 45));
        panelDroit.add(btnRetourner, d);

        add(panelDroit, BorderLayout.EAST);

        // Résultat au centre
        labelResultat = new JLabel(" ", SwingConstants.CENTER);
        add(labelResultat, BorderLayout.CENTER);

        btnRetourner.addActionListener(e -> traiterRetour());
    }

    private void majUI() {
        btnRetourner.setEnabled(true);
    }

    private void traiterRetour() {
        String identifiant = tfIdentifiant.getText().trim();
        if (identifiant.isEmpty()) {
            labelResultat.setText("Veuillez entrer un identifiant");
            return;
        }

        String nom = tfNom.getText().trim();
        String prenom = tfPrenom.getText().trim();
        String typeObjet = tfTypeObjet.getText().trim();
        String reference = tfReference.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || reference.isEmpty()) {
            labelResultat.setText("Veuillez remplir tous les champs obligatoires");
            return;
        }

        // Récupérer le compte via identifiant
        Compte compte = compteDAO.read(identifiant);
        if (compte == null) {
            labelResultat.setText("Compte introuvable");
            return;
        }

        // Récupérer client via compteId
        Integer clientIdFromCompte = clientDAO.getClientFromCompte(compte.getCompteId());
        if (clientIdFromCompte == null) {
            labelResultat.setText("Client introuvable");
            return;
        }

        Client client = clientDAO.read(clientIdFromCompte);

        ArrayList<Emprunt> emprunts = empruntDAO.getListeEmpruntsByClientId(client.getClientId());
        ArrayList<Emprunt> correspondants = new ArrayList<>();
        for (Emprunt e : emprunts) {
            if (e.getObjet().getReference().equals(reference)) {
                correspondants.add(e);
            }
        }

        if (correspondants.isEmpty()) {
            labelResultat.setText("Aucun emprunt correspondant trouvé");
            return;
        }

        Emprunt aSupprimer;
        if (correspondants.size() == 1) {
            aSupprimer = correspondants.get(0);
        } else {
            String[] choix = new String[correspondants.size()];
            for (int i = 0; i < correspondants.size(); i++) {
                Emprunt e = correspondants.get(i);
                choix[i] = "Emprunt du " + e.getDateDebut() + " → " + e.getDateFin();
            }

            String selection = (String) JOptionPane.showInputDialog(
                this,
                "Plusieurs emprunts trouvés :",
                "Choisir l'emprunt à supprimer",
                JOptionPane.QUESTION_MESSAGE,
                null,
                choix,
                choix[0]
            );

            if (selection == null) return;

            int index = 0;
            for (int i = 0; i < choix.length; i++) {
                if (choix[i].equals(selection)) {
                    index = i;
                    break;
                }
            }
            aSupprimer = correspondants.get(index);
        }

        empruntDAO.delete(aSupprimer);

        labelResultat.setText(
            "Le client " + client.getNom() + " " + client.getPrenom() +
            " a retourné l'objet " +
            aSupprimer.getObjet().getAuteur() + " " +
            aSupprimer.getObjet().getNom() + " " +
            aSupprimer.getObjet().getReference()
        );
    }
}
