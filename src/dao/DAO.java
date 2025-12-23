package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DAO<T> {
	protected Connection connect;
	protected Statement stmt;
	
	public void open() {
		try {
			connect = SingleConnection.getInstance();
			stmt = connect.createStatement();
		} catch (SQLException e) {
			System.out.println("erreur ouverture DAO");
			e.printStackTrace();
		}
	}
	
	public abstract T create(T obj);
	
	public abstract T update(T obj);
	
	public abstract void delete(T obj);
	
	public void close() {
		SingleConnection.close();
	}
}
