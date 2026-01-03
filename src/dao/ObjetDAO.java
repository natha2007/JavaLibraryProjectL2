package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.Objet;


public class ObjetDAO extends DAO<Objet>{
private ResultSet rs;
	
	@Override
	public Objet create(Objet o1) {
		String requete = "INSERT INTO objet(nom, auteur, prix, typeObjet, quantite, reference)"
				+ "VALUES('" + o1.getNom() + "', '" + o1.getAuteur() 
				+ "', '" + o1.getPrix() + "', '" + o1.getTypeObjet() + "', '" + o1.getQuantite() 
				+ "', '" + o1.getReference() + "')";
		try {
			stmt.executeUpdate(requete, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				o1.setObjetId(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return o1;
	}
	
	@Override
	public Objet update(Objet o1) {
		String requete = "UPDATE objet "
				+ "SET nom= '" + o1.getNom()
				+ "', auteur= '" + o1.getAuteur()
				+ "', prix= '" + o1.getPrix()
				+ "', typeObjet= '" + o1.getTypeObjet()
				+ "', quantite= '" + o1.getQuantite()
				+ "' WHERE objetId= " + o1.getObjetId();
		try {
			System.out.println(requete);
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return o1;
	}
	
	@Override
	public void delete(Objet o1) {
		String requete = "DELETE FROM objet"
				+ " WHERE objetId =" + o1.getObjetId();
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace(); 
		}
	}
	
	public Objet read(Integer id) {
		Objet o1 = null;
		String requete = "SELECT *"
				+ " FROM emprunt"
				+ " WHERE empruntId =" + id;
		try {
			this.rs = stmt.executeQuery(requete);
			if (rs.first()) {
				o1 = new Objet(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getInt(6), rs.getString(7));
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return o1;
	}
}
