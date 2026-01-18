package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import metier.Emprunt;
import metier.Client;
import metier.Objet;

public class EmpruntDAO extends DAO<Emprunt> {
	private ResultSet rs;
	
	@Override
	public Emprunt create(Emprunt e1) {
		LocalDate today = LocalDate.now();
		LocalDate oneMonthLater = today.plusDays(30);

		String requete = "INSERT INTO emprunt(dateDebut, dateFin, clientId, objetId)"
				+ "VALUES('" + today + "', '" + oneMonthLater
				+ "', '" + e1.getClient().getClientId() 
				+ "', '" + e1.getObjet().getObjetId() 
				+ "')";
		try {
			stmt.executeUpdate(requete, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				e1.setEmpruntId(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return e1;
	}
	
	@Override
	public Emprunt update(Emprunt e1) {
		String requete = "UPDATE emprunt"
				+ " SET dateDebut= '" + e1.getDateDebut()
				+ "', dateFin= '" + e1.getDateFin()
				+ ", clientId= " + e1.getClient().getClientId() 
				+ ", objetId= " + e1.getObjet().getObjetId() 
				+ " WHERE empruntId= " + e1.getEmpruntId();
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return e1;
	}
	
	@Override
	public void delete(Emprunt e1) {
		String requete = "DELETE FROM emprunt WHERE empruntId =" + e1.getEmpruntId();
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
	}
	
	public int countAbonnement(Client c1) {
	    int count = 0;
	    String requete = "SELECT COUNT(*) FROM emprunt WHERE clientId = " + c1.getClientId();

	    try {
	        ResultSet rs = stmt.executeQuery(requete);
	        if (rs.next()) {
	            count = rs.getInt(1); // COUNT(*)
	        }
	    } catch (SQLException e) {
	        System.out.println("erreur requête SQL");
	        e.printStackTrace();
	    }

	    return count;
	}

	
	public Emprunt read(Integer id) {
		Emprunt e1 = null;
		Client c1 = null;
		Objet o1 = null;
		ClientDAO cd = new ClientDAO();
		ObjetDAO od = new ObjetDAO();
		String requete = "SELECT * FROM emprunt WHERE empruntId =" + id;
		try {
			this.rs = stmt.executeQuery(requete);
			if (rs.first()) {
				c1 = cd.read(rs.getInt(5));
				o1 = od.read(rs.getInt(6));
				e1 = new Emprunt(rs.getInt(1),rs.getString(2),rs.getString(3), c1, o1);
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return e1;
	}
	
	public ArrayList<Emprunt> getListeEmpruntsByClientId(Integer clientId){
		ArrayList<Emprunt> liste = new ArrayList<Emprunt>();
		Client c1 = null;
		Objet o1 = null;
		ClientDAO cd = new ClientDAO();
		ObjetDAO od = new ObjetDAO();
		String requete = "SELECT * FROM emprunt WHERE clientId= " + clientId;
		try {
			this.rs = stmt.executeQuery(requete);
			while (rs.next()) {
				c1 = cd.read(rs.getInt(4));
				o1 = od.read(rs.getInt(5));
				Emprunt e = new Emprunt(rs.getInt(1),rs.getString(2),rs.getString(3),c1,o1);
				liste.add(e);
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return liste;
	}
}
