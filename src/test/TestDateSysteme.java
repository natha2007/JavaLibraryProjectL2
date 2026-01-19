package test;

import java.sql.Timestamp;
import java.time.LocalDate;

import dao.DateSystemeDAO;
import gui.gestion.GestionDate;
import metier.DateSysteme;

public class TestDateSysteme {

	public static void main(String[] args) {
		
		DateSystemeDAO dateDAO = new DateSystemeDAO();

		// 1️ CREATE
		System.out.println(" CREATE ");
		LocalDate now = GestionDate.getDateJour();
		DateSysteme ds = new DateSysteme(now);
		dateDAO.create(ds);
		System.out.println(ds);

		// 2️ READ
		System.out.println("\n READ ");
		DateSysteme dsRead = dateDAO.read(ds.getDateId());
		System.out.println(dsRead);

		// 3️ UPDATE
		
		System.out.println("\n UPDATE ");
		dsRead.setDateDuJour(GestionDate.getDateJour().plusDays(5));
		dateDAO.update(dsRead);
		System.out.println(dateDAO.read(dsRead.getDateId()));

		// 4️ DELETE 
		System.out.println("\n DELETE ");
		//dateDAO.delete(dsRead);
		
		System.out.println("Ca suprimé");
		
	}
} 