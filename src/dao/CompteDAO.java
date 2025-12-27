package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.Compte;

public class CompteDAO extends DAO<Compte> {
	private ResultSet rs;

	@Override
	public Compte create(Compte c) {
		String requete = "INSERT INTO compte(identifiant, mdpHash, dateCreation, typeCompte)"
				+ "VALUES('" + c.getIdentifiant() + "', '" + c.getMdpHash() 
				+ "', '" + c.getDateCreation() + "', '" + c.getTypeCompte() 
				+ "')";
		try {
			stmt.executeUpdate(requete, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				c.setCompteId(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public Compte update(Compte c) {
		String requete = "UPDATE compte"
				+ " SET identifiant= '" + c.getIdentifiant()
				+ "', mdpHash= '" + c.getMdpHash()
				+ "', dateCreation= '" + c.getDateCreation()
				+ "', typeCompte= '" + c.getTypeCompte()
				+ "' WHERE compteId= " + c.getCompteId();
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public void delete(Compte c) {
		String requete = "DELETE FROM compte"
				+ " WHERE compteId= " + c.getCompteId();
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
	}
	
	public Compte read(Integer id) {
		Compte c = null;
		String requete = "SELECT *"
				+ " FROM compte"
				+ " WHERE compteId= " + id;
		try {
			rs = stmt.executeQuery(requete);
			if (rs.first()) {
				c = new Compte(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				//index commencent à 1
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return c;
	}
	
	
}
