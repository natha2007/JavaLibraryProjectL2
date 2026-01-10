package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.Bibliothecaire;
import metier.Compte;

public class BibliothecaireDAO extends DAO<Bibliothecaire> {
	private ResultSet rs;

	@Override
	public Bibliothecaire create(Bibliothecaire bbl) {
		String requete = "INSERT INTO bibliothecaire(nom, prenom, compteId)"
				+ " VALUES ('" + bbl.getNom() + "', '" + bbl.getPrenom() + "', " + 
				bbl.getCompte().getCompteId() + ")";
		try {
			stmt.executeUpdate(requete, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				bbl.setBibliothecaireId(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return bbl;
	}

	@Override
	public Bibliothecaire update(Bibliothecaire bbl) {
		String requete = "UPDATE bibliothecaire"
				+ " SET nom= '" + bbl.getNom()
				+ "', prenom= '" + bbl.getPrenom()
				+ "', compte= " + bbl.getCompte().getCompteId()
				+ " WHERE bibliothecaireId= " + bbl.getBibliothecaireId();
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return bbl;
	}

	@Override
	public void delete(Bibliothecaire bbl) {
		String requete = "DELETE FROM bibliothecaire"
				+ " WHERE bibliothecaireId=" + bbl.getBibliothecaireId();
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}	
	}
	
	public Bibliothecaire read(Integer id) {
		Bibliothecaire bbl = null;
		Compte c = null;
		CompteDAO cd = new CompteDAO();
		String requete = "SELECT *"
				+ " FROM bibliothecaire"
				+ " WHERE bibliothecaireId=" + id;
		try {
			this.rs = stmt.executeQuery(requete);
			if (rs.next()) {
				c = cd.read(rs.getInt(4));
				bbl = new Bibliothecaire(rs.getInt(1), rs.getString(2), rs.getString(3), c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erreur requête SQL");
		}
		return bbl;
	}
	
	/**
	 * Récupère un objet Bibliothécaire en fonction
	 * de son compteId
	 * @param compteId
	 * @return
	 */
	public Bibliothecaire readByCompteId(Integer compteId) {
		Bibliothecaire bbl = null;
		Compte c = null;
		CompteDAO cd = new CompteDAO();
		String requete = "SELECT *"
				+ " FROM bibliothecaire"
				+ " WHERE compteId=" + compteId;
		try {
			this.rs = stmt.executeQuery(requete);
			if (rs.next()) {
				c = cd.read(rs.getInt(4));
				bbl = new Bibliothecaire(rs.getInt(1), rs.getString(2), rs.getString(3), c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erreur requête SQL");
		}
		return bbl;
	}
	
	public boolean exists(Integer bibliothecaireId) {
		boolean verif = false;
		if (bibliothecaireId == null) {
			verif = false;
		}
		String requete = "SELECT * "
					+ " FROM bibliothecaire"
					+ " WHERE bibliothecaireId= " + bibliothecaireId;
		try {
			rs = stmt.executeQuery(requete);
			if (rs.first()) {
				verif = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erreur requête SQL");
		}
		return verif;
	}
	
	public boolean exists(String nom, String prenom) {
		boolean verif = false;
		String requete = "SELECT *"
				+ " FROM bibliothecaire"
				+ " WHERE nom='" + nom
				+ "' AND prenom='" + prenom + "'";
		try {
			rs = stmt.executeQuery(requete);
			if (rs.first()) {
				verif = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erreur requête SQL");
		}
		return verif;
	}
	
	/**
	 * Renvoie vrai si le bibliothecaire existe.
	 * On recherche le bibliothecaire par son compteId.
	 * Renvoie faux sinon.
	 * @param compteId 
	 * @return boolean
	 */
	public boolean existsByCompteId(Integer compteId) {
		boolean verif = false;
		String requete = "SELECT * "
					+ " FROM bibliothecaire"
					+ " WHERE compteId= " + compteId;
		try {
			rs = stmt.executeQuery(requete);
			if (rs.first()) {
				verif = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erreur requête SQL");
		}
		return verif;
	}
	
}
