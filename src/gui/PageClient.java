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
	
	public void setUser(CompteUtilisateur user) {
		this.user = user;
		
		LocalDate today = LocalDate.now();
		ClientDAO cd = new ClientDAO();
		EmpruntDAO ed = new EmpruntDAO();
		ObjetDAO od = new ObjetDAO();
		Client cl = cd.read(user.getClientId());
		AbonnementDAO ad = new AbonnementDAO();
		System.out.println(cl);
		ArrayList<Emprunt> listeEmprunt = ed.getListeEmpruntsByClientId(user.getClientId());
				
		
		JLabel test2 = new JLabel("clientId : " + user.getClientId() + ", prenom : " + cl.getPrenom() + ", nom : " + cl.getNom());
		add(test2, BorderLayout.NORTH);
		
		JPanel body = new JPanel(new BorderLayout());
		add(body, BorderLayout.CENTER);
		
//		JPanel body = new JPanel(new GridLayout(10,1));
//		add(body, BorderLayout.CENTER);
		
		String[] colonnes = {
				"Objet",
		        "Nom",
		        "Jours restants",
		        "Date de l'empreint",
		        "Date d'expiration",
		        "N° de l'emprunt",
		        "Référence"
		};
		
		DefaultTableModel tabRes = new DefaultTableModel(colonnes, 0);

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
		JTable table = new JTable(tabRes);
		table.setRowHeight(40);
		table.setFont(new Font("Arial", Font.PLAIN, 14));

		JScrollPane scroll = new JScrollPane(table);
		body.setLayout(new BorderLayout());
		body.add(scroll, BorderLayout.CENTER);
		
	}
	
	public PageClient(Runnable rb) {
		setLayout(new BorderLayout());
		JLabel test = new JLabel("Profil");
		add(test, BorderLayout.NORTH);
	
	}
}
