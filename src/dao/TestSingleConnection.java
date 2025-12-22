package dao;

import java.sql.Connection;
import java.sql.SQLException;

public class TestSingleConnection {
	public static void main(String[] args) {
		try {
			Connection cnTest = SingleConnection.getInstance();
			
			 if (cnTest != null) {
	                System.out.println("Connexion réussie !");
	            } else {
	                System.out.println("La connexion est null");
	            }
			 
			 if (cnTest.isClosed()) {
				 System.out.println("la connexion est fermée");
			 } else {
				 System.out.println("la connexion est ouverte");
			 }
			 
			 SingleConnection.close();
			 
			 if (cnTest.isClosed()) {
				 System.out.println("la connexion est fermée");
			 } else {
				 System.out.println("la connexion est ouverte");
			 }
			 
		} catch (SQLException e) {
			System.out.println("erreur de connexion à la base de données");
			e.printStackTrace();
		}
	}
}
