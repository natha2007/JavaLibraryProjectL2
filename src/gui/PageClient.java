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

    private JLabel profilInfos;
    private JPanel body;
    private JTable table;
    private JScrollPane scroll;
    private DefaultTableModel tabRes;

    private ArrayList<Emprunt> listeEmprunt;

    private ClientDAO cd;
    private EmpruntDAO ed;
    private ObjetDAO od;
    private AbonnementDAO ad;

    private Client cl;

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

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(header, BorderLayout.NORTH);

        JLabel titreProfil = new JLabel("Profil");
        titreProfil.setFont(new Font("Arial", Font.BOLD, 14));
        titreProfil.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(titreProfil);
        header.add(Box.createVerticalStrut(8));
        profilInfos = new JLabel();
        profilInfos.setFont(new Font("Arial", Font.PLAIN, 60));
        profilInfos.setAlignmentX(Component.LEFT_ALIGNMENT);
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

        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel titreTable = new JLabel("Emprunts en cours");
        titreTable.setFont(new Font("Arial", Font.BOLD, 18));
        titreTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        body.add(titreTable, BorderLayout.NORTH);
        
        scroll = new JScrollPane(table);
        body.add(scroll, BorderLayout.CENTER);
    }

    private void majUI() {
        tabRes.setRowCount(0);

        if (user == null) {
            profilInfos.setText("");
            return;
        }

        cl = cd.read(user.getClientId());
        profilInfos.setText("Bonjour " + cl.getPrenom() + " " + cl.getNom());

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
