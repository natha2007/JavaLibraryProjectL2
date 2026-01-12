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
	private JLabel test2;
	private Runnable rb;
	//private JLabel profil;
	private JLabel profilInfos;
	private JPanel body;
	private JTable table;
	private JScrollPane scroll;
	private DefaultTableModel tabRes;
	private ArrayList<Emprunt> listeEmprunt;
	private Client cl;
	private ClientDAO cd;
	private EmpruntDAO ed;
	private ObjetDAO od;
	private AbonnementDAO ad;
	
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
		
		String[] colonnes = {
				"Objet",
		        "Nom",
		        "Jours restants",
		        "Date de l'empreint",
		        "Date d'expiration",
		        "N° de l'emprunt",
		        "Référence"
		};
		
		//profil = new JLabel("Profil");
		//add(profil, BorderLayout.NORTH);
		
		profilInfos = new JLabel();
		add(profilInfos, BorderLayout.NORTH);
		
		body = new JPanel(new BorderLayout());
		add(body, BorderLayout.CENTER);
		
	
		
		tabRes = new DefaultTableModel(colonnes, 0);

		table = new JTable(tabRes);
	
		
//		add(test2, BorderLayout.NORTH);
		scroll = new JScrollPane(table);
		body.add(scroll, BorderLayout.CENTER);
		
		listeEmprunt = new ArrayList<Emprunt>();
		
		table.setRowHeight(40);
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		
	}
	
	private void majUI() {
		tabRes.setRowCount(0);
		
		if (user == null) {
			profilInfos.setText("En attente de connexion");
			return;
		}
		cl = cd.read(user.getClientId());
		profilInfos.setText("clientId : " + user.getClientId() + ", prenom : " + cl.getPrenom() + ", nom : " + cl.getNom());
		
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
			
			
	//		JPanel body = new JPanel(new GridLayout(10,1));
	//		add(body, BorderLayout.CENTER);
	}
	
	public PageClient(Runnable rb) {
		this.rb = rb;
		initialiserUI();
		majUI();
	}
}