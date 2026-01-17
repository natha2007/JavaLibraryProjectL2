package gui;

import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.*;
import metier.*;

public class PageClient extends JPanel {

    private CompteUtilisateur user;

    private JTextArea profilInfos;
    private JPanel body;
    private JTable table;
    private JScrollPane scroll;
    private DefaultTableModel tabRes;

    private ArrayList<Emprunt> listeEmprunt;

    private ClientDAO cd;
    private EmpruntDAO ed;
    private ObjetDAO od;
    private AbonnementDAO ad;

    private Abonnement ab;
    private Client cl;
    private JLabel abonnementTxt;

    private Runnable rb;

    public PageClient(Runnable rb) {
        this.rb = rb;
        initialiserUI();
        majUI();
    }

    public void setUser(CompteUtilisateur user) {
        this.user = user;
        majUI();
    }

    
    private void initialiserUI() {
    	
    	
        setLayout(new BorderLayout());

        cd = new ClientDAO();
        ed = new EmpruntDAO();
        od = new ObjetDAO();
        ad = new AbonnementDAO();

        JPanel header = new JPanel(new GridLayout(2,1));
        //header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        //header.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(header, BorderLayout.NORTH);

        JPanel headerPremLigne = new JPanel(new GridLayout(1,3));
        JPanel PremLigne1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel PremLigne3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel titreProfil = new JLabel("Profil");
    	JLabel dateDuJour = new JLabel("pas de date a afficher");
		if (GestionDate.getDateJour() != null) {
			dateDuJour.setText(GestionDate.getDateJour().toString());
		}
		dateDuJour.setAlignmentX(RIGHT_ALIGNMENT);
		dateDuJour.setFont(new Font("Serif", Font.BOLD, 20));
		dateDuJour.setAlignmentX(Component.RIGHT_ALIGNMENT);
        titreProfil.setFont(new Font("Serif", Font.BOLD, 20));
        titreProfil.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        header.add(headerPremLigne);
        headerPremLigne.add(PremLigne1);
        headerPremLigne.add(Box.createGlue());
        headerPremLigne.add(PremLigne3);
        PremLigne1.add(titreProfil);
        PremLigne3.add(dateDuJour);
        //header.add(Box.createHorizontalStrut(30));
        //header.add(Box.createVerticalStrut(8));
        profilInfos = new JTextArea();
        profilInfos.setFont(new Font("Arial", Font.PLAIN, 55));
        profilInfos.setAlignmentX(Component.LEFT_ALIGNMENT);
        profilInfos.setLineWrap(true);       
        profilInfos.setWrapStyleWord(true);
        profilInfos.setEditable(false);       // reste non éditable comme un JLabel
        profilInfos.setOpaque(false); 
        header.add(profilInfos);
        

        body = new JPanel(new BorderLayout());
        add(body, BorderLayout.CENTER);

        String[] colonnes = {
            "Objet",
            "Nom",
            "Jours restants",
            "Date début",
            "Date fin",
            "N° emprunt",
            "Référence"
        };
        
        tabRes = new DefaultTableModel(colonnes, 0);
        table = new JTable(tabRes);

        table.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel titreTable = new JLabel("Emprunts en cours");
        titreTable.setFont(new Font("Arial", Font.BOLD, 18));
        titreTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        abonnementTxt = new JLabel();
        abonnementTxt.setFont(new Font("Arial", Font.PLAIN, 14));
        abonnementTxt.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        abonnementTxt.setAlignmentX(Component.LEFT_ALIGNMENT);

        titreTable.setAlignmentX(Component.LEFT_ALIGNMENT);

        topPanel.add(abonnementTxt);
        topPanel.add(titreTable);

        body.add(topPanel, BorderLayout.NORTH);

        scroll = new JScrollPane(table);
        body.add(scroll, BorderLayout.CENTER);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        
        JPanel foot = new JPanel();
        add(foot, BorderLayout.SOUTH);
        JButton deconnexion = new JButton("Se déconnecter");
        foot.add(deconnexion);
        deconnexion.addActionListener(e -> seDeconnecter(user));
    }
    
    public void seDeconnecter(CompteUtilisateur empty) {
    	rb.run();
    }

    private void majUI() {
        tabRes.setRowCount(0);

        if (user == null) {
            profilInfos.setText("");
            return;
        }

        cl = cd.read(user.getClientId());
        profilInfos.setText("Bonjour " + cl.getPrenom() + " " + cl.getNom());
        ab=cl.getAbonnement();
        
       if (ab == null) {
    	   abonnementTxt.setText("Vous n'avez aucun abonnement actif (aucun emprunt possible)");
       } else {
    	   abonnementTxt.setText("Vous bénéficiez du forfait " + ab.getTypeAbonnement() 
           + " à " + ab.getPrix() + "€. Vous pouvez faire jusqu'à " + ab.getNbEmpruntsMax() + " emprunts simultanément.");
       }
        listeEmprunt = ed.getListeEmpruntsByClientId(user.getClientId());
        LocalDate today = LocalDate.now();

        for (Emprunt e : listeEmprunt) {
            LocalDate localDateFin = LocalDate.parse(e.getDateFin());
            Objet objet = od.read(e.getObjet().getObjetId());

            Object[] ligne = {
                objet.getTypeObjet(),
                objet.getNom(),
                ChronoUnit.DAYS.between(today, localDateFin),
                e.getDateDebut(),
                e.getDateFin(),
                e.getEmpruntId(),
                objet.getReference()
            };

            tabRes.addRow(ligne);
        }
    }
}
