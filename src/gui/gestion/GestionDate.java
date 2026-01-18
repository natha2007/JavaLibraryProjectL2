package gui.gestion;

import java.sql.Date;
import java.time.LocalDate;

import dao.DateSystemeDAO;
import metier.DateSysteme;

public class GestionDate {
	private static LocalDate dateJour;
	private static DateSysteme dateSysteme;
	
	
	public static void majDate() {
		System.out.println("maj date appelé");
		DateSystemeDAO dsd = new DateSystemeDAO();
		dateJour = LocalDate.now();
		if (!dsd.exists(dateJour)) {
			dsd.create(new DateSysteme(dateJour));
		} else {
			dsd.delete(dsd.read());
			dateSysteme = new DateSysteme(dateJour);
			dsd.create(dateSysteme);
		}
	}
	
	public static LocalDate getDateJour() {
		return dateJour;
	}
	
	public static DateSysteme getDateSysteme() {
		return dateSysteme;
	}
	
	public static Date getDateFromLocalDate() {
		Date date = Date.valueOf(GestionDate.getDateJour());
		return date;
	}

	
}
