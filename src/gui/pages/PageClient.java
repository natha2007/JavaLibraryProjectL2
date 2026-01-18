package gui.pages;

import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import dao.*;
import gui.gestion.CompteUtilisateur;
import gui.gestion.GestionDate;
import gui.gestion.GestionUIStyle;
import metier.*;

public class PageClient extends JPanel implements IPageMaj {

    private CompteUtilisateur user;

    private JTextArea profilInfos;
    private JTable table;
    private JScrollPane scroll;
    private DefaultTableModel tabRes;

    private ArrayList<Emprunt> listeEmprunt;

    private ClientDAO cd = new ClientDAO();
    private EmpruntDAO ed = new EmpruntDAO();
    private ObjetDAO od = new ObjetDAO();
    private AbonnementDAO ad = new AbonnementDAO();

    private Abonnement ab;
    private Client cl;
    private JLabel abonnementTxt;
    
	private final Color btnColor = GestionUIStyle.getButtonColor();
	private final Color bgColor = GestionUIStyle.getBgColor();
	private final Color txtColor = GestionUIStyle.getTextColor();

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

    @Override
    public void initialiserUI() {
        setLayout(new BorderLayout());
    	setBackground(bgColor);

        JPanel header = new JPanel(new GridLayout(2,1));
        header.setBackground(bgColor);
        add(header, BorderLayout.NORTH);

        JPanel headerPremLigne = new JPanel(new GridLayout(1,3));
        headerPremLigne.setBackground(bgColor);
        header.add(headerPremLigne);
        
        JPanel PremLigne1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        PremLigne1.setBackground(bgColor);
        headerPremLigne.add(PremLigne1);
        
        JPanel PremLigne3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        PremLigne3.setBackground(bgColor);
        headerPremLigne.add(Box.createGlue());
        headerPremLigne.add(PremLigne3);
        
        JLabel titreProfil = new JLabel("Profil");
        titreProfil.setFont(new Font("Serif", Font.BOLD, 20));
        titreProfil.setAlignmentX(Component.LEFT_ALIGNMENT);
        PremLigne1.add(titreProfil);
        
    	JLabel dateDuJour = new JLabel("pas de date a afficher");
		if (GestionDate.getDateJour() != null) {
			dateDuJour.setText(GestionDate.getDateJour().toString());
		}
		dateDuJour.setAlignmentX(RIGHT_ALIGNMENT);
		dateDuJour.setFont(new Font("Serif", Font.BOLD, 20));
		dateDuJour.setAlignmentX(Component.RIGHT_ALIGNMENT);
		PremLigne3.add(dateDuJour);
       
        profilInfos = new JTextArea();
        profilInfos.setFont(new Font("Arial", Font.PLAIN, 55));
        profilInfos.setAlignmentX(Component.LEFT_ALIGNMENT);
        profilInfos.setLineWrap(true);       
        profilInfos.setWrapStyleWord(true);
        profilInfos.setEditable(false);       // reste non éditable comme un JLabel
        profilInfos.setOpaque(false); 
        header.add(profilInfos);
        
        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(bgColor);
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
        table.setBackground(btnColor);
        table.setForeground(txtColor);
        table.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        JTableHeader headerT = table.getTableHeader();
        headerT.setBackground(btnColor);
        headerT.setForeground(txtColor);
        
        JLabel titreTable = new JLabel("Emprunts en cours");
        titreTable.setFont(new Font("Arial", Font.BOLD, 18));
        titreTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titreTable.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(bgColor);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        abonnementTxt = new JLabel();
        abonnementTxt.setFont(new Font("Arial", Font.PLAIN, 14));
        abonnementTxt.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        abonnementTxt.setAlignmentX(Component.LEFT_ALIGNMENT);

        topPanel.add(abonnementTxt);
        topPanel.add(titreTable);

        body.add(topPanel, BorderLayout.NORTH);

        scroll = new JScrollPane(table);
        body.add(scroll, BorderLayout.CENTER);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(bgColor);
		
        JPanel foot = new JPanel();
        foot.setBackground(bgColor);
        add(foot, BorderLayout.SOUTH);
        
        JButton deconnexion = new JButton("Se déconnecter");
        deconnexion.setBackground(btnColor);
        deconnexion.setForeground(txtColor);
        foot.add(deconnexion);
        deconnexion.addActionListener(e -> seDeconnecter(user));
    }
    
    public void seDeconnecter(CompteUtilisateur empty) {
    	rb.run();
    }

    @Override
    public void majUI() {
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
        LocalDate today = GestionDate.getDateJour();

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
