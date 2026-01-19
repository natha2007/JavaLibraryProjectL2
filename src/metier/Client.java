package metier;


public class Client {
	private Integer clientId;
	private String nom;
	private String prenom;
	private Abonnement abonnement; 
	private Compte compte; 
	
	public Client(String nom, String prenom, Abonnement abonnement, Compte compte) {
		this.abonnement = abonnement;
		this.nom = nom;
		this.prenom = prenom;
		this.compte = compte;
	}
	
	public Client(Integer clientId, String nom, String prenom, Abonnement abonnement, Compte compte) {
		this.abonnement = abonnement;
		this.nom = nom;
		this.prenom = prenom;
		this.compte = compte;
		this.clientId = clientId;
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
	
	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	@Override
	public String toString() {
	    String abonnementId;
	    if (this.getAbonnement() == null || this.getAbonnement().getAbonnementId() == null) {
	        abonnementId = "NULL";
	    } else {
	        abonnementId = this.getAbonnement().getAbonnementId().toString();
	    }
	    return "Client [clientId=" + clientId + ", nom=" + nom + ", prenom=" + prenom + ", abonnementId=" + abonnementId + "]";
	}

	
}
