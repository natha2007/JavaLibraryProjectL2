package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DAO<T> {
	protected Connection connect;
	protected Statement stmt;
	
	public DAO() {
		open();
	}
	
	/**
	 * Ouvre la connexion et crée un statement
	 */
	public void open() {
		try {
			connect = SingleConnection.getInstance();
			stmt = connect.createStatement(Statement.RETURN_GENERATED_KEYS,
				    						ResultSet.TYPE_FORWARD_ONLY);
		} catch (SQLException e) {
			System.out.println("erreur ouverture DAO");
			e.printStackTrace();
		}
	}
	
	/*
	 * méthode abstraite : ajouter un objet à la BD
	 */
	public abstract T create(T obj);
	
	/**
	 * méthode abstraite : maj d'un objet de la BD
	 * @param obj l'objet à mettre à jour
	 * @return l'objet mis à jour
	 */
	public abstract T update(T obj);
	
	/**
	 * méthode abstraite : supprimer un élément de la BD
	 * @param obj élément à supprimer
	 */
	public abstract void delete(T obj);
	
	/**
	 * ferme la connexion
	 */
	public void close() {
		SingleConnection.close();
	}
}
