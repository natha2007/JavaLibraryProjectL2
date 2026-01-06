package gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import dao.*;
import metier.*;

public class PageClient extends JPanel {
	
	private CompteUtilisateur user;
	
	public void setUser(CompteUtilisateur user) {
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
			JLabel test3 = new JLabel("empruntId : " + e.getEmpruntId()
									+ ", date debut : " + e.getDateDebut()
									+ ", date fin :" + e.getDateFin()
									+ ", dureeMaximaleEmprunt : " + e.getDureeMaximaleEmprunt()
									);
			body.add(test3);
		}
	}
	
	public PageClient(Runnable rb) {
		setLayout(new BorderLayout());
		JLabel test = new JLabel("Bonjour je suis la page client");
		add(test, BorderLayout.NORTH);
	
	}
}
