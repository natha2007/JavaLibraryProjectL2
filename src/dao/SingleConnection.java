package dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.cj.jdbc.MysqlDataSource;

public class SingleConnection {
	private static Connection connect;
	
	/**
	 * Crée une nouvelle connexion 
	 * @param dbname nom de la base de données
	 * @param servername nom du server
	 * @param login identifiant
	 * @param password mot de passe
	 * @throws SQLException
	 */
	private SingleConnection(String dbname, String servername, String login, String password) throws SQLException{
		String url="jdbc:mysql://"+servername+":3306/"+dbname+"?serverTimezone=UTC";
		MysqlDataSource mysqlDS = new MysqlDataSource();
		mysqlDS.setURL(url);
		mysqlDS.setUser(login);
		mysqlDS.setPassword(password);
		
		try {
			connect = mysqlDS.getConnection();
		} catch (SQLException e) {
			System.out.println("erreur de connexion à la base de données");
			e.printStackTrace();
		}
	}
	
	/**
	 * récupère l'instance de connexion et la créé si elle est null
	 * @return l'instance de connexion
	 * @throws SQLException
	 */
	public static Connection getInstance() throws SQLException {
		if (connect == null) {
			new SingleConnection("bibliotheque","127.0.0.1","root","") ;
		}
		return connect;
	}
	
	//étant donné qu'on a qu'une seule base de donnée, j'ai trouvé inutile de faire
	//une deuxième méthode getInstance avec des paramètres
	
	/**
	 * ferme la connexion
	 */
	public static void close() {
		try {
			connect.close();
		} catch (SQLException e) {
			System.out.println("problème de fermeture de la connexion");
			e.printStackTrace();
		}
	}
}
