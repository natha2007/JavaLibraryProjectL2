package gui.pages;

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
import gui.gestion.GestionDate;
import gui.gestion.GestionMdp;
import gui.gestion.GestionUIStyle;
import gui.gestion.SaisieInvalideException;
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
   	private ClientDAO cld=new ClientDAO();
   	private Compte c;
   	
	private final Color btnColor = GestionUIStyle.getButtonColor();
	private final Color bgColor = GestionUIStyle.getBgColor();
	private final Color txtColor = GestionUIStyle.getTextColor();
   	
   	private JLabel confirmation = new JLabel("");
   	
    public PageCreationCompte(Runnable rb) {
        this.rb = rb;
        initialiserUI();
    }
    
    /**
     * Cette fonction permet de créer un panel avec un JLabel et une zone
     * @param texte
     * @param champ
     * @return
     */
    private JPanel champAvecLabel(String texte, JTextField champ) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(bgColor);

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

    /**
	 * Initialise et crée les élements de l'UI 
	 */
    public void initialiserUI() {
    	setBackground(bgColor);
        setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        header.setBackground(bgColor);
        add(header, BorderLayout.NORTH);

        JLabel titreCreaCmt = new JLabel("Créez votre compte client");
        titreCreaCmt.setFont(new Font("Arial", Font.BOLD, 14));
        titreCreaCmt.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(titreCreaCmt);

        JPanel body = new JPanel(new GridLayout(3, 2, 15, 15));
        body.setBackground(bgColor);
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
        
        JPanel footer = new JPanel();
        footer.setBackground(bgColor);
        add(footer, BorderLayout.SOUTH);
        
        JButton retour=new JButton("Retour");
        retour.setBackground(btnColor);
        retour.setForeground(txtColor);
        footer.add(retour);
        retour.addActionListener(e->rb.run());
        
        JButton valider=new JButton("Valider");
        valider.setBackground(btnColor);
        valider.setForeground(txtColor);
        
        footer.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        footer.add(valider);
        valider.addActionListener(e -> validerProfil() );
  
    }
    
    /**
     * vérifie les éléments de saisie pour valider la création de profil
     */
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
	
    /**
     * Créer le compte en base de données
     */
    private void creerCompte() {
    	String identifiant=txtIde.getText();
    	char[] mdp1=txtMdp.getPassword();
       	// char[] mdp2=txtConfirm.getPassword();
       	String mdp1s=GestionMdp.getMdpResultHash(mdp1);
       	c=new Compte(identifiant, mdp1s, GestionDate.getDateFromLocalDate(), "Client");      	
       	cd.create(c);
    }

    /**
     * Crée le client en base de données
     */
    private void creerClient() {
      	String nom=txtNom.getText();
       	String prenom=txtPrenom.getText();
       	
       	Client cl = new Client(nom, prenom, null, c);
       	cld.create(cl);
    }
    
    /**
     * Affiche la "page" de confirmation, qui apparait quand les champs de saisie ont été validés
     * et que le client et le compte associé ont été créés
     */
    private void pageConfirmation() {
        removeAll();
        setLayout(new BorderLayout());

        JLabel message = new JLabel(
            "Compte créé avec succès.",
            JLabel.CENTER
        );
        message.setFont(new Font("Arial", Font.BOLD, 16));

        JButton retour = new JButton("Retour");
        retour.setBackground(btnColor);
        retour.setForeground(txtColor);
        
        retour.addActionListener(e -> resetPage());

        JPanel centre = new JPanel(new BorderLayout());
        centre.setBackground(bgColor);
        centre.add(message, BorderLayout.CENTER);

        JPanel bas = new JPanel();
        bas.setBackground(bgColor);
        bas.add(retour);

        add(centre, BorderLayout.CENTER);
        add(bas, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    /**
     * ferme la page et affiche la page de connexion
     */
    public void resetPage() {
        removeAll();
        revalidate();
        repaint();
        initialiserUI();
        rb.run();
    }

    /**
     * Attribue le message d'erreur au JLabel correspondant
     * @param message message d'erreur
     */
	private void afficherErreur(String message) {
	    confirmation.setForeground(Color.RED);
	    confirmation.setText(message);
	}
	
	/**
	 * vérifie que toutes les saisies sont justes
	 * @throws SaisieInvalideException
	 */
	private void validerSaisie() throws SaisieInvalideException {
		validerNom();
		validerPrenom();
		validerIdentifiant();
		validerMDP();
	}
	
	/**
	 * vérifie que la saisie du champ "nom" est valide
	 * @throws SaisieInvalideException
	 */
	private void validerNom() throws SaisieInvalideException {
		String t = txtNom.getText();
		if (t.isEmpty()) {
			throw new SaisieInvalideException("Vous devez saisir un nom");
		}
		if (!t.matches("[\\p{L} \\-]+")) {
			throw new SaisieInvalideException("Nom invalide");
		}
	}
	
	/**
	 * vérifie que la saisie du champ "Prénom" est valide
	 * @throws SaisieInvalideException
	 */
	private void validerPrenom() throws SaisieInvalideException {
		String t = txtPrenom.getText();
		if (t.isEmpty()) {
			throw new SaisieInvalideException("Vous devez saisir un prenom");
		}
		if (!t.matches("[\\p{L} \\-]+")) {
			throw new SaisieInvalideException("Prenom invalide");
		}
	}
	
	/**
	 * vérifie que la saisie du champ "identifiant" est valide
	 * @throws SaisieInvalideException
	 */
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
	
	/**
	 * vérifie que la saisie du mot de passe est valide
	 * @throws SaisieInvalideException
	 */
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