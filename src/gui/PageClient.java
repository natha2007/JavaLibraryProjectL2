package gui;

import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.*;

import dao.*;
import metier.*;

public class PageClient extends JPanel {
	
	private CompteUtilisateur user;
	
	public void setUser(CompteUtilisateur user) {
		LocalDate today = LocalDate.now();
		
		this.user = user;
		ClientDAO cd = new ClientDAO();
		EmpruntDAO ed = new EmpruntDAO();
		Client cl = cd.read(user.getClientId());
		ArrayList<Emprunt> listeEmprunt = ed.getListeEmpruntsByClientId(user.getClientId());
		JLabel test2 = new JLabel("clientId : " + user.getClientId() + ", prenom : " + cl.getPrenom() + ", nom : " + cl.getNom());
		add(test2, BorderLayout.SOUTH);
		JPanel body = new JPanel(new GridLayout(5,1));
		add(body, BorderLayout.CENTER);
		for (Emprunt e : listeEmprunt) {
			LocalDate localDateFin = LocalDate.parse(e.getDateFin());
			JLabel test3 = new JLabel("Numéro emprunt : " + e.getEmpruntId()
									+ ", Date de l'emprunt : " + e.getDateDebut()
									+ ", Date d'expiration :" + e.getDateFin()
									+ ", Nombre de jours : " + ChronoUnit.DAYS.between(today, localDateFin)

									);
			body.add(test3);
		}
	}
	
	public PageClient(Runnable rb) {
		setLayout(new BorderLayout());
		JLabel test = new JLabel("Profil");
		add(test, BorderLayout.NORTH);
	
	}
}
