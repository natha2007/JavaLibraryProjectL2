package dao;

import java.sql.Connection;
import java.sql.SQLException;

public class TestSingleConnection {
	public static void main(String[] args) {
		try {
			Connection cnTest = SingleConnection.getInstance();
		} catch (SQLException e) {
			System.out.println("erreur de connexion à la base de données");
			e.printStackTrace();
		}
	}
}
