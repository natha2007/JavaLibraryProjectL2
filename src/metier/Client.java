package metier;

import dao.DAO;

public class Client {
	private int clientId;
	private String nom;
	private String prenom;
	private Abonnement abonnement; //clé étrangère abonnementId
	
	public Client(String nom, String prenom, Abonnement abonnement) {
		this.abonnement = abonnement;
		this.nom = nom;
		this.prenom = prenom;
	}
	
	public int getClientId() {
		return clientId;
	}
	
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public Abonnement getAbonnement() {
		return abonnement;
	}
	
	public void setAbonnement(Abonnement abonnement) {
		this.abonnement = abonnement;
	}
	
	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", nom=" + nom + ", prenom=" + prenom + "abonnementId=" + this.abonnement.getAbonnementId() + "]";
	}
	

	
	
	
}
