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
	
	private Runnable rb;
	private CompteUtilisateur user;
	private JLabel profil;
	private JLabel profilInfos;
	private JPanel body;
	private JTable table;
	private JScrollPane scroll;
	
	public void setUser(CompteUtilisateur user) {
		this.user = user;
		majUI();
	}
	
	/**
	 * Initialiser les éléments statiques de l'ui.
	 * Donc soit créer les composants qui ne vont pas bouger
	 * soit juste initialiser les éléments qui accueilleront 
	 * du contenu différent selon l'user.
	 */
	private void initialiserUI() {
		setLayout(new BorderLayout());
		
		profil = new JLabel("Profil");
		add(profil, BorderLayout.NORTH);
		
		profilInfos = new JLabel();
		add(profilInfos, BorderLayout.NORTH);
		
		body = new JPanel(new BorderLayout());
		add(body, BorderLayout.CENTER);
		
//		body = new JPanel(new GridLayout(10,1));
//		add(body, BorderLayout.CENTER);
	}
	
	/**
	 * définit ce qui peut être mis à jour,
	 * c'est à dire ce qui dépend de l'utilisateur
	 * (user)
	 */
	private void majUI() {
		LocalDate today = LocalDate.now();
		ClientDAO cd = new ClientDAO();
		EmpruntDAO ed = new EmpruntDAO();
		if (user == null) {
			profilInfos.setText("attente de connexion");
		} else {
			Client cl = cd.read(user.getClientId());
			ArrayList<Emprunt> listeEmprunt = ed.getListeEmpruntsByClientId(user.getClientId());
			profilInfos.setText("clientId : " + user.getClientId() + ", prenom : " + cl.getPrenom() + ", nom : " + cl.getNom());
			
			String[] colonnes = {
			        "N° de l'emprunt",
			        "Date de l'empreint",
			        "Date d'expiration",
			        "Jours restants"
			};
			DefaultTableModel tabRes = new DefaultTableModel(colonnes, 0);
	
			for (Emprunt e : listeEmprunt) {
				LocalDate localDateFin = LocalDate.parse(e.getDateFin());
	
				Object[] ligne = {
					e.getEmpruntId(),
					e.getDateDebut(),
					e.getDateFin(),
					ChronoUnit.DAYS.between(today, localDateFin)
				};
	
				tabRes.addRow(ligne);
	//			LocalDate localDateFin = LocalDate.parse(e.getDateFin());
	//			JLabel test3 = new JLabel("Numéro emprunt : " + e.getEmpruntId()
	//									+ ", Date de l'emprunt : " + e.getDateDebut()
	//									+ ", Date d'expiration :" + e.getDateFin()
	//									+ ", Nombre de jours restants : " + ChronoUnit.DAYS.between(today, localDateFin)
	//
	//									);
	//			body.add(test3);
			}
			
			table = new JTable(tabRes);
			table.setRowHeight(25);
			table.setFont(new Font("Arial", Font.PLAIN, 14));
			scroll = new JScrollPane(table);
			body.setLayout(new BorderLayout());
			body.add(scroll, BorderLayout.CENTER);
		}
	}
	
	public PageClient(Runnable rb) {
		this.rb = rb;
		initialiserUI();
		majUI();
	}
}
