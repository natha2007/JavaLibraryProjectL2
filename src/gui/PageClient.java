package gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import dao.*;
import metier.*;

public class PageClient extends JPanel {

    private CompteUtilisateur user;
    private JPanel contenu;

    public PageClient(Runnable rb) {
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Bonjour je suis la page client");
        add(titre, BorderLayout.NORTH);

        contenu = new JPanel();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));

        add(contenu, BorderLayout.CENTER);
    }

    public void setUser(CompteUtilisateur user) {
        this.user = user;
        contenu.removeAll(); // IMPORTANT si setUser est rappelé

        ClientDAO cd = new ClientDAO();
        EmpruntDAO ed = new EmpruntDAO();
        Client cl = cd.read(user.getClientId());
        ArrayList<Emprunt> listeEmprunt =
                ed.getListeEmpruntsByClientId(user.getClientId());

        JLabel infoClient = new JLabel(
            "clientId : " + user.getClientId()
            + ", prenom : " + cl.getPrenom()
            + ", nom : " + cl.getNom()
        );
        contenu.add(infoClient);

        for (Emprunt e : listeEmprunt) {
            JLabel infoEmprunt = new JLabel(
                "empruntId : " + e.getEmpruntId()
                + ", date debut : " + e.getDateDebut()
                + ", date fin : " + e.getDateFin()
                + ", dureeMax : " + e.getDureeMaximaleEmprunt()
            );
            contenu.add(infoEmprunt);
        }

        contenu.revalidate();
        contenu.repaint();
    }
}
