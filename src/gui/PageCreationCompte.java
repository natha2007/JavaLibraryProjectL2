package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import dao.AbonnementDAO;
import dao.ClientDAO;
import dao.CompteDAO;
import dao.ObjetDAO;
import metier.Abonnement;
import metier.Compte;
import metier.Objet;
import metier.Client;


public class PageCreationCompte extends JPanel {

    private Runnable rb;
    private JTextField txtNom;
   	private JTextField txtPrenom;
   	private JTextField txtIde;
   	
   	private JPasswordField txtMdp;
   	private JPasswordField txtConfirm;
   	private CompteDAO cd = new CompteDAO();
   	private Compte c;
   	private ClientDAO cld=new ClientDAO();
   	private JPanel footer;
   	private JPanel header;
   	private JPanel body;
   	
   	private JLabel confirmation=new JLabel("");
   	
    public PageCreationCompte(Runnable rb) {
        this.rb = rb;
        initialiserUI();
    }

    
    //Cette fonction permet de créer un panel avec un JLabel et une zone
    private JPanel champAvecLabel(String texte, JTextField champ) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(texte);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        champ.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension d = champ.getPreferredSize();
        d.height = 25;
        champ.setMaximumSize(d);
        champ.setPreferredSize(d);

        p.add(label);
        p.add(Box.createVerticalStrut(5));
        p.add(champ);

        return p;
    }

    
    public void initialiserUI() {

        setLayout(new BorderLayout());

        header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(header, BorderLayout.NORTH);

        JLabel titreCreaCmt = new JLabel("Créez votre compte client");
        titreCreaCmt.setFont(new Font("Arial", Font.BOLD, 14));
        titreCreaCmt.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(titreCreaCmt);

        body = new JPanel(new GridLayout(3, 2, 15, 15));
        body.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(body, BorderLayout.CENTER);

        txtNom = new JTextField(12);
        txtPrenom = new JTextField(12);
        txtIde = new JTextField(12);
        txtMdp = new JPasswordField(12);
        txtConfirm = new JPasswordField(12);

        body.add(champAvecLabel("Nom", txtNom));
        body.add(champAvecLabel("Prénom", txtPrenom));
        body.add(champAvecLabel("Identifiant", txtIde));
        body.add(champAvecLabel("Mot de passe", txtMdp));
        body.add(champAvecLabel("Confirmation", txtConfirm));
        body.add(confirmation);

//        JPanel basDroite = new JPanel();
//        basDroite.setLayout(new BoxLayout(basDroite, BoxLayout.Y_AXIS));
//        basDroite.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        basDroite.add(champAvecLabel("Mot de passe", txtMdp));
//        basDroite.add(Box.createVerticalStrut(10));
//        basDroite.add(champAvecLabel("Confirmation", txtConfirm));
//
//        body.add(basDroite);
        
        footer = new JPanel();
        //footer.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        add(footer, BorderLayout.SOUTH);
        
        JButton retour=new JButton("Retour");
        footer.add(retour);
        retour.addActionListener(e->rb.run());
        
        JButton valider=new JButton("Valider");
        footer.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        footer.add(valider);
        valider.addActionListener(e -> validerProfil() );
  
    }
    
    private void validerProfil() {
		try {
			validerSaisie();
			creerCompte();
			creerClient();
			pageConfirmation();
		} catch (SaisieInvalideException s) {
			afficherErreur(s.getMessage());
		}
	}
	
    private void creerCompte() {
    	String identifiant=txtIde.getText();
    	char[] mdp1=txtMdp.getPassword();
       	// char[] mdp2=txtConfirm.getPassword();
       	String mdp1s=GestionMdp.getMdpResultHash(mdp1);
       	c=new Compte(identifiant, mdp1s, GestionDate.getDateFromLocalDate(), "Client");      	
       	cd.create(c);
    }

    private void creerClient() {
      	String nom=txtNom.getText();
       	String prenom=txtPrenom.getText();
       	
       	Client cl = new Client(nom, prenom, null, c);
       	cld.create(cl);
    }
    
    private void pageConfirmation() {
        removeAll();
        setLayout(new BorderLayout());

        JLabel message = new JLabel(
            "Compte créé avec succès.",
            JLabel.CENTER
        );
        message.setFont(new Font("Arial", Font.BOLD, 16));

        JButton retour = new JButton("Retour");
        retour.addActionListener(e -> resetPage());

        JPanel centre = new JPanel(new BorderLayout());
        centre.add(message, BorderLayout.CENTER);

        JPanel bas = new JPanel();
        bas.add(retour);

        add(centre, BorderLayout.CENTER);
        add(bas, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    
    public void resetPage() {
        removeAll();
        revalidate();
        repaint();

        initialiserUI();
        rb.run();
    }

    
	private void afficherErreur(String message) {
	    confirmation.setForeground(Color.RED);
	    confirmation.setText(message);
	}
	
	private void validerSaisie() throws SaisieInvalideException {
		validerNom();
		validerPrenom();
		validerIdentifiant();
		validerMDP();
	}
	
	private void validerNom() throws SaisieInvalideException {
		String t = txtNom.getText();
		if (t.isEmpty()) {
			throw new SaisieInvalideException("Vous devez saisir un nom");
		}
		if (!t.matches("[\\p{L} \\-]+")) {
			throw new SaisieInvalideException("Nom invalide");
		}
	}
	
	private void validerPrenom() throws SaisieInvalideException {
		String t = txtPrenom.getText();
		if (t.isEmpty()) {
			throw new SaisieInvalideException("Vous devez saisir un prenom");
		}
		if (!t.matches("[\\p{L} \\-]+")) {
			throw new SaisieInvalideException("Prenom invalide");
		}
	}
	
	private void validerIdentifiant() throws SaisieInvalideException {
		String t = txtIde.getText();
		if (cd.exists(t)) {
			throw new SaisieInvalideException("L'identifiant est déjà affecté");
		}
		
		if (t.isEmpty()) {
			throw new SaisieInvalideException("Vous devez saisir un identifiant");
		}
		
		if (t.contains("'") || t.contains("\"") || t.contains(";") || t.contains("/") || t.contains("--") || t.contains("*")) {
			throw new SaisieInvalideException("Caractères invalides (pas de '\\;/) ");
		}
	}
	
	private void validerMDP() throws SaisieInvalideException {
		String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s])\\S{12,}$";
		String mdpTxt = new String(txtMdp.getPassword());
		String verifTxt = new String(txtConfirm.getPassword());

		// String confirmTxt = new String(txtConfirm.getPassword());
		
		if (mdpTxt.isEmpty()) {
			throw new SaisieInvalideException("Vous devez saisir un mot de passe");
		}
		
		if (!(mdpTxt.equals(verifTxt))) {
			throw new SaisieInvalideException("Les deux mots de passe sont différents.");
		}
		
		if (!(mdpTxt.matches(regex))) {
			throw new SaisieInvalideException(
				"<html>" +
				"Mot de passe invalide<br>" +
				"• 12 caractères minimum<br>" +
				"• 1 majuscule<br>" +
				"• 1 chiffre<br>" +
				"• 1 caractère spécial" +
				"</html>"
				);
		}
	}	
}