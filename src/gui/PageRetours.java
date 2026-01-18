package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.ClientDAO;
import dao.CompteDAO;
import dao.EmpruntDAO;
import dao.ObjetDAO;
import metier.Client;
import metier.Compte;
import metier.Emprunt;

public class PageRetours extends JPanel implements IPage {

    private CompteUtilisateur user;

    private ClientDAO clientDAO = new ClientDAO();
    private EmpruntDAO empruntDAO = new EmpruntDAO();
    private CompteDAO compteDAO = new CompteDAO();
    private ObjetDAO objetDAO = new ObjetDAO();

    private JLabel mainText;
    private JTextField tfPrenom, tfNom, tfIdentifiant, tfReference;
    private JButton btnRetourner;
    private JLabel labelResultat;
    private JComboBox tfTypeObjet;

    public PageRetours() {
        initialiserUI();
        majUI();
    }

    @Override
    public void setUser(CompteUtilisateur user) {
        this.user = user;
        majUI();
    }

    private void initialiserUI() {
        setLayout(new BorderLayout(10, 10));
        JPanel grid = new JPanel(new GridLayout(1,2));
        add(grid, BorderLayout.CENTER);

        // titre
        mainText = new JLabel("Gestion des retours", SwingConstants.CENTER);
        mainText.setFont(new Font("Arial", Font.BOLD, 24));
        add(mainText, BorderLayout.NORTH);

        // panel gauche
        JPanel panelGauche = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        

        Dimension fieldSize = new Dimension(600, 22);

        
        // Nom
        c.insets = new Insets(0, 50, 0, 0);
        ///c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; 
        c.gridy = 0;
        c.weightx = 1.0;
        
        panelGauche.add(new JLabel("Nom :"), c);
        c.insets = new Insets(0,50,100,0);
        c.gridx = 0; 
        c.gridy = 1;
        c.weightx = 1.0;
        tfNom = new JTextField(30);
        tfNom.setPreferredSize(fieldSize);
        panelGauche.add(tfNom, c);
       
		
        
        // Prenom
        c.insets = new Insets(0, 50, 0, 0);
        c.gridx = 0; c.gridy++;
        c.weightx = 1.0;
        panelGauche.add(new JLabel("Prénom :"), c);
        c.insets = new Insets(0,50,0,0);
        c.gridy++;
        c.weightx = 1.0;
        tfPrenom = new JTextField(30);
        tfPrenom.setPreferredSize(fieldSize);
        panelGauche.add(tfPrenom, c);
       
        // prenom
        
        //c.gridx = 0; c.gridy++;
        
        //c.gridx = 1;
       

        // type d'objet
        /*
        c.gridx = 0; c.gridy++;
        panelGauche.add(new JLabel("Type objet :"), c);
        c.gridx = 1;
        String[] listeTypeObjet = {"Livre","CD","DVD","JeuSociete","Ordinateur"};
		tfTypeObjet = new JComboBox(listeTypeObjet);
        tfTypeObjet.setPreferredSize(fieldSize);
        panelGauche.add(tfTypeObjet, c);
         */
        grid.add(panelGauche);

        // panel droit 
        JPanel panelDroit = new JPanel(new GridBagLayout());
        GridBagConstraints d = new GridBagConstraints();
       
       
        d.insets = new Insets(0, 0, 0, 50);
        
        // Identifiant
        d.gridx = 0; d.gridy = 0;
        d.weightx = 1.0;
        panelDroit.add(new JLabel("Identifiant :"), d);
        d.insets = new Insets(0,0,100,50);
        d.gridy = 1;
        d.weightx = 1.0;
        tfIdentifiant = new JTextField(30);
        tfIdentifiant.setPreferredSize(fieldSize);
        panelDroit.add(tfIdentifiant, d);
        
        // Référence
        d.insets = new Insets(0, 0, 0, 50);
        d.gridx = 0; d.gridy = 2;
        d.weightx = 1.0;
        panelDroit.add(new JLabel("Référence :"), d);
        d.insets = new Insets(0,0,0,50);
        d.gridy = 3;
        d.weightx = 1.0;
        tfReference = new JTextField(30);
        tfReference.setPreferredSize(fieldSize);
        panelDroit.add(tfReference, d);

        // Bouton Retourner
        JPanel foot = new JPanel(new GridBagLayout());
        add(foot, BorderLayout.SOUTH);

        GridBagConstraints e = new GridBagConstraints();
        
        btnRetourner = new JButton("Retourner");
        btnRetourner.setPreferredSize(new Dimension(200, 45));
        e.gridx = 0;
        e.gridy = 0;
        foot.add(btnRetourner, e);
        

        // Résultat au centre
        labelResultat = new JLabel(" ", SwingConstants.CENTER);
        e.gridx = 0;
        e.gridy = 1;
        foot.add(labelResultat, e);
        
        grid.add(panelDroit);

        btnRetourner.addActionListener(f -> gererErreurs());
    }

    private void majUI() {
        if (user == null) {
            mainText.setText("En attente de connexion");
            btnRetourner.setEnabled(false);
        } else {
            mainText.setText("Gestion des retours");
            btnRetourner.setEnabled(true);
        }
    }
    
    private void gererErreurs() {
    	try {
    		traiterRetour();
    	} catch (SaisieInvalideException s) {
    		labelResultat.setText(s.getMessage());
    	}
    }

    private void traiterRetour() throws SaisieInvalideException{
        String identifiant = tfIdentifiant.getText().trim();
        if (identifiant.isEmpty()) {
            throw new SaisieInvalideException("Veuillez entrer un identifiant");
        }

        String nom = tfNom.getText().trim();
        String prenom = tfPrenom.getText().trim();
        String reference = tfReference.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || reference.isEmpty()) {
            throw new SaisieInvalideException("Veuillez remplir tous les champs obligatoires");
        }

        // Récupérer le compte via identifiant
        Compte compte = compteDAO.read(identifiant);
        if (compte == null) {
            throw new SaisieInvalideException("Compte introuvable");

        }

        // Récupérer client via compteId
        Integer clientIdFromCompte = clientDAO.getClientFromCompte(compte.getCompteId());
        if (clientIdFromCompte == null) {
            throw new SaisieInvalideException("Client introuvable");
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
            throw new SaisieInvalideException("Aucun emprunt correspondant trouvé");
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
        
        objetDAO.updateDispo(aSupprimer.getObjet());
        System.out.println(aSupprimer.toString());
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