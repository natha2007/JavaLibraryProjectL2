package gui.pages;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import javax.swing.*;

import dao.ClientDAO;
import dao.CompteDAO;
import gui.gestion.CompteUtilisateur;
import gui.gestion.GestionMdp;
import gui.gestion.GestionUIStyle;
import gui.gestion.SaisieInvalideException;
import metier.Compte;

public class ConnexionPage extends JPanel {

    private Consumer<CompteUtilisateur> conn;

    private JTextField champIdentifiant = new JTextField(30);
    private JPasswordField champMdp = new JPasswordField(30);
    private Integer IdClientActuel;
    
	private final Color btnColor = GestionUIStyle.getButtonColor();
	private final Color bgColor = GestionUIStyle.getBgColor();
	private final Color txtColor = GestionUIStyle.getTextColor();

    public ConnexionPage(Consumer<CompteUtilisateur> conn) {
        this.conn = conn;
        initialiserUI();
    }

    /**
     * Initialiser et créer éléments de la page
     */
    private void initialiserUI() {
        setLayout(new BorderLayout());

        JPanel head = new JPanel(new GridLayout(1, 3));
        JPanel body = new JPanel(new GridBagLayout());
        JPanel foot = new JPanel(new GridBagLayout());
        
        head.setBackground(bgColor);
        body.setBackground(bgColor);
        foot.setBackground(bgColor);

        add(head, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
        add(foot, BorderLayout.SOUTH);

        head.add(Box.createGlue()); 

        JLabel titre = new JLabel("Bienvenue");
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        titre.setFont(new Font(titre.getFont().getName(), Font.BOLD, 40));
        head.add(titre);

        JButton creerCompte = new JButton("Créer un compte");
        creerCompte.setBorderPainted(false);
        creerCompte.setContentAreaFilled(false);
        creerCompte.setFocusPainted(false);
        creerCompte.setOpaque(false);
        
        head.add(creerCompte);
        creerCompte.addActionListener(e -> conn.accept(null));

        creerCompte.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                creerCompte.setForeground(new Color(200, 200, 200, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                creerCompte.setForeground(new Color(100, 100, 100, 255));
            }
        });

        GridBagConstraints ctr = new GridBagConstraints();

        ctr.insets = new Insets(100, 100, 0, 0); //tlbr
        ctr.gridx = 0;
        ctr.gridy = 0;
        ctr.weightx = 1;
        ctr.anchor = GridBagConstraints.WEST; 
        ctr.fill = GridBagConstraints.NONE; 
        JLabel id = new JLabel("Identifiant");
        id.setFont(new Font(body.getFont().getName(), body.getFont().getStyle(), 25));
        body.add(id, ctr);

        ctr.insets = new Insets(0, 100, 50, 100);
        ctr.gridx = 0;
        ctr.gridy = 1;
        ctr.weightx = 1.0;                 
        ctr.fill = GridBagConstraints.HORIZONTAL;
        champIdentifiant.setFont(new Font(body.getFont().getName(), body.getFont().getStyle(), 25));
        body.add(champIdentifiant, ctr);

        ctr.insets = new Insets(25, 100, 0, 0);
        ctr.gridx = 0;
        ctr.gridy = 2;
        ctr.weightx = 1;
        ctr.anchor = GridBagConstraints.WEST; 
        ctr.fill = GridBagConstraints.NONE; 
        JLabel mdp = new JLabel("Mot de passe");
        mdp.setFont(new Font(body.getFont().getName(), body.getFont().getStyle(), 25));
        body.add(mdp, ctr);

        ctr.insets = new Insets(0, 100, 100, 100);
        ctr.gridx = 0;
        ctr.gridy = 3;
        ctr.weightx = 1.0;                 
        ctr.fill = GridBagConstraints.HORIZONTAL;
        champMdp.setFont(new Font(body.getFont().getName(), body.getFont().getStyle(), 25));
        body.add(champMdp, ctr);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 100, 50, 100);
        JButton connexionBtn = new JButton("Se connecter");
        connexionBtn.setBackground(btnColor);
        connexionBtn.setForeground(txtColor);
        foot.add(connexionBtn, gbc);
        connexionBtn.addActionListener(e -> gererErreurs());
    }

    /**
     * Obtenir la saisie de l'utilisateur dans le champ identifiant
     * @return saisie de l'utilisateur sous forme de String
     */
    private String getIdentifiant() {
        return champIdentifiant.getText();
    }

    /**
     * Récupérer la saisie du mot de passe par l'utilisateur
     * @return mot de passe saisie sous forme de tableau de char
     */
    private char[] getMdpResult() {
        return champMdp.getPassword();
    }

    /**
     * Obtenir le mot de passe attendu par rapport à l'utilisateur rentré
     * @return mot de passe attendu
     */
    private String getMdpAttendu() {
        CompteDAO cd = new CompteDAO();
        String mdpAttendu = "";
        if (cd.exists(getIdentifiant())) {
            mdpAttendu = cd.read(getIdentifiant()).getMdpHash();
        }
        return mdpAttendu;
    }
    
    /**
     * gérer l'affichage d'un message d'erreur en interceptant une
     * SaisieInvalideException dans la méthode verifInfos()
     */
    public void gererErreurs() {
    	try {
    		verifInfos();
    	} catch (SaisieInvalideException s) {
    		 JOptionPane.showMessageDialog(this,
                     s.getMessage(),
                     "Erreur de connexion",
                     JOptionPane.ERROR_MESSAGE);
    	}
    }

    /**
     * vérifie si le mot de passe et l'identifiant correspondent
     * à ce qui se trouve en base de données
     * @throws SaisieInvalideException
     */
    private void verifInfos() throws SaisieInvalideException {
        if (this.getMdpAttendu().equals(GestionMdp.getMdpResultHash(getMdpResult()))) {
            CompteDAO cd = new CompteDAO();
            Compte c = cd.read(getIdentifiant());
            if (c != null) {
                CompteUtilisateur user = new CompteUtilisateur(c.getCompteId(), c.getTypeCompte());
                conn.accept(user);
            }
        } else {
            throw new SaisieInvalideException("Identifiant ou mot de passe incorrect");
        }
    }
}
