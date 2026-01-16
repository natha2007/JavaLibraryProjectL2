package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.DateSysteme;

public class DateSystemeDAO extends DAO<DateSysteme> {

	private ResultSet rs;

	@Override
	public DateSysteme create(DateSysteme ds) {
		String requete = "INSERT INTO date_systeme(dateDuJour, dateManuelle) "
				+ "VALUES('" + ds.getDateDuJour() + "', " + ds.isDateManuelle() + ")";
		try {
			stmt.executeUpdate(requete, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				ds.setDateId(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return ds;
	}

	@Override
	public DateSysteme update(DateSysteme ds) {
		String requete = "UPDATE date_systeme "
				+ "SET dateDuJour = '" + ds.getDateDuJour() + "', "
				+ "dateManuelle = " + ds.isDateManuelle()
				+ " WHERE dateId = " + ds.getDateId();
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return ds;
	}

	@Override
	public void delete(DateSysteme ds) {
		String requete = "DELETE FROM date_systeme "
				+ "WHERE dateId = " + ds.getDateId();
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
	}

	public DateSysteme read(Integer id) {
		DateSysteme ds = null;
		String requete = "SELECT * FROM date_systeme WHERE dateId = " + id;
		try {
			rs = stmt.executeQuery(requete);
			if (rs.first()) {
				ds = new DateSysteme(
						rs.getInt("dateId"),
						rs.getTimestamp("dateDuJour"),
						rs.getBoolean("dateManuelle")
				);
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return ds;
	}
}




