package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.Abonnement;

public class AbonnementDAO extends DAO<Abonnement>{
	private ResultSet rs;

	@Override
	public Abonnement create(Abonnement ab) {
		String requete = "INSERT INTO abonnement(typeAbonnement, prix, nbEmpruntsMax)"
				+ " VALUES('" + ab.getTypeAbonnement() 
				+ "', '" + ab.getPrix() 
				+ "', '" + ab.getNbEmpruntsMax()
				+ "')";
		try {
			stmt.executeUpdate(requete, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				ab.setAbonnementId(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return ab;
	}
	
	@Override
	public Abonnement update(Abonnement ab) {
		String requete = "UPDATE abonnement"
				+ " SET typeAbonnement= '" + ab.getTypeAbonnement()
				+ "', prix= '" + ab.getPrix() 
				+ "', nbEmpruntsMax= '" + ab.getNbEmpruntsMax() + "'"
				+ "WHERE abonnementId= " + ab.getAbonnementId();
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return ab;
	}

	@Override
	public void delete(Abonnement ab) {
		String requete = "DELETE FROM abonnement"
				+ " WHERE abonnementId= " + ab.getAbonnementId();
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
	}
	
	public Abonnement read(Integer id) {
		Abonnement ab = null;
		String requete = "SELECT *"
				+ " FROM abonnement"
				+ " WHERE abonnementId= " + id;
		try {
			rs = stmt.executeQuery(requete);
			if (rs.first()) {
				ab = new Abonnement(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4));
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return ab;
	}
	
	public Abonnement read(String typeAbonnement) {
		Abonnement ab = null;
		String requete = "SELECT *"
				+ " FROM abonnement"
				+ " WHERE typeAbonnement= '" + typeAbonnement + "'";
		try {
			rs = stmt.executeQuery(requete);
			if (rs.first()) {
				ab = new Abonnement(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4));
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return ab;
	}

}
