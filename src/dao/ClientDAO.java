package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.Abonnement;
import metier.Client;
import metier.Compte;

public class ClientDAO extends DAO<Client>{
	private ResultSet rs;

	@Override
	public Client create(Client cl) {
		String requete = "INSERT INTO client(nom, prenom, abonnementId, compteId)"
				+ " VALUES('" + cl.getNom() + "', '" + cl.getPrenom() + "', " + cl.getAbonnement().getAbonnementId()
				+ ", " + cl.getCompte().getCompteId() + ")";
		try {
			stmt.executeUpdate(requete, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				cl.setClientId(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return cl;
	}

	@Override
	public Client update(Client cl) {
		String requete = "UPDATE client"
				+ " SET nom ='" + cl.getNom()
				+ "', prenom ='" + cl.getPrenom()
				+ "', abonnementId =" + cl.getAbonnement().getAbonnementId()
				+ ", compteId =" + cl.getCompte().getCompteId()
				+ " WHERE clientId =" + cl.getClientId();
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return cl;
	}

	@Override
	public void delete(Client cl) {
		String requete = "DELETE FROM client"
				+ " WHERE clientId =" + cl.getClientId();
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
	}
	
	public Client read(Integer id) {
		Client cl = null;
		Abonnement ab = null;
		Compte c = null;
		AbonnementDAO ad = new AbonnementDAO();
		CompteDAO cd = new CompteDAO();
		String requete = "SELECT *"
				+ " FROM client"
				+ " WHERE clientId =" + id;
		try {
			this.rs = stmt.executeQuery(requete);
			if (rs.first()) {
				ab = ad.read(rs.getInt(4));
				c = cd.read(rs.getInt(5));
				cl = new Client(rs.getInt(1),rs.getString(2),rs.getString(3), ab, c);
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return cl;
	}
	
	public Integer getClientFromCompte(Integer compteId) {
		Integer clientId = null;
		String requete = "SELECT *"
				+ " FROM client"
				+ " WHERE compteId=" + compteId;
		try {
			rs = stmt.executeQuery(requete);
			if(rs.first()) {
				clientId = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Erreur requête SQL");
			e.printStackTrace();
		}
		return clientId;
	}

}
