package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import metier.Emprunt;
import metier.Client;
import metier.Objet;


public class EmpruntDAO extends DAO<Emprunt> {
	private ResultSet rs;
	
	@Override
	public Emprunt create(Emprunt e1) {
		String requete = "INSERT INTO emprunt(dateDebut, dateFin, dureeMaximaleEmprunt, clientId, objetId)"
				+ "VALUES('" + e1.getDateDebut() + "', '" + e1.getDateFin() 
				+ "', '" + e1.getDureeMaximaleEmprunt() 
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
				+ "', dureeMaximaleEmprunt= " + e1.getDureeMaximaleEmprunt()
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
		String requete = "DELETE FROM emprunt"
				+ " WHERE empruntId =" + e1.getEmpruntId();
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
	}
	
	public Emprunt read(Integer id) {
		Emprunt e1 = null;
		Client c1 = null;
		Objet o1 = null;
		ClientDAO cd = new ClientDAO();
		ObjetDAO od = new ObjetDAO();
		String requete = "SELECT *"
				+ " FROM emprunt"
				+ " WHERE empruntId =" + id;
		try {
			this.rs = stmt.executeQuery(requete);
			if (rs.first()) {
				c1 = cd.read(rs.getInt(5));
				o1 = od.read(rs.getInt(6));
				e1 = new Emprunt(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getFloat(4), c1, o1);
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
		String requete = "SELECT *"
						+ " FROM emprunt"
						+ " WHERE clientId= " + clientId;
		try {
			this.rs = stmt.executeQuery(requete);
			while (rs.next()) {
				c1 = cd.read(rs.getInt(5));
				o1 = od.read(rs.getInt(6));
				Emprunt e = new Emprunt(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getFloat(4),c1,o1);
				liste.add(e);
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return liste;
	}
}
