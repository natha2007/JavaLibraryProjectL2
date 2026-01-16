package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
import metier.Abonnement;
import metier.Compte;
import metier.Client;


public class PageCreationCompte extends JPanel {

    private Runnable rb;

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
    	AbonnementDAO ad=new AbonnementDAO();
    	CompteDAO cd = new CompteDAO();
    	ClientDAO cld = new ClientDAO();
    	
    	LocalDate today = LocalDate.now();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	String ajd = today.format(formatter);

    	GestionMdp gmdp = null;
        setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(header, BorderLayout.NORTH);

        JLabel titreCreaCmt = new JLabel("Création compte client");
        titreCreaCmt.setFont(new Font("Arial", Font.BOLD, 14));
        titreCreaCmt.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(titreCreaCmt);

        JPanel body = new JPanel(new GridLayout(3, 2, 15, 15));
        body.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(body, BorderLayout.CENTER);

        JTextField txtNom = new JTextField(12);
        JTextField txtPrenom = new JTextField(12);
        JTextField txtIde = new JTextField(12);
        JPasswordField txtMdp = new JPasswordField(12);
        JPasswordField txtConfirm = new JPasswordField(12);

        body.add(champAvecLabel("Nom", txtNom));
        body.add(champAvecLabel("Prénom", txtPrenom));
        body.add(champAvecLabel("Identifiant", txtIde));
        body.add(champAvecLabel("Mot de passe", txtMdp));
        body.add(champAvecLabel("Confirmation", txtConfirm));

//        JPanel basDroite = new JPanel();
//        basDroite.setLayout(new BoxLayout(basDroite, BoxLayout.Y_AXIS));
//        basDroite.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        basDroite.add(champAvecLabel("Mot de passe", txtMdp));
//        basDroite.add(Box.createVerticalStrut(10));
//        basDroite.add(champAvecLabel("Confirmation", txtConfirm));
//
//        body.add(basDroite);
        
        JPanel footer = new JPanel();
        //footer.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        add(footer, BorderLayout.SOUTH);
        
        JButton valider=new JButton("Valider");
        footer.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        footer.add(valider);
        valider.addActionListener(e -> {        	
        	String nom=txtNom.getText();
        	String prenom=txtPrenom.getText();
        	String identifiant=txtIde.getText();
        	
        	char[] mdp1=txtMdp.getPassword();
        	char[] mdp2=txtConfirm.getPassword();
        	
        	String mdp1s=gmdp.getMdpResultHash(mdp1);
        	
        	Compte c=new Compte(identifiant, mdp1s, ajd, "Client");
        	
        	cd.create(c);
        	Client cl = new Client(nom, prenom, null, c);
        	
        	cld.create(cl);
        	
        });
  
    }
}
