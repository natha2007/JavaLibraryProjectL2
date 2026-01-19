package gui.main;

import java.awt.Color;
import java.awt.Dimension;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import dao.AbonnementDAO;
import dao.BibliothecaireDAO;
import metier.*;
import dao.ClientDAO;
import dao.CompteDAO;
import dao.DateSystemeDAO;
import dao.EmpruntDAO;
import dao.ObjetDAO;
import gui.gestion.GestionBD;
import gui.gestion.GestionDate;
import metier.Abonnement;
import metier.Client;
import metier.Compte;

public class Main {
	public static void main(String[] args) {
		GestionDate.majDate();
		System.out.println(GestionDate.getDateJour());
		GestionBD.genererBibliothecaire("DoeJ", "Jd12", "DOE", "John");
		MainFrame mf = new MainFrame("Gestionnaire de bibliothèque");
		mf.setVisible(true);
		mf.setMinimumSize(new Dimension(1024, 600)); // limite basse réelle
		mf.setSize(1280, 720);  
		mf.pack();
	}
}
