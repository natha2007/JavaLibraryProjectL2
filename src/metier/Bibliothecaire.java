package metier;

public class Bibliothecaire {
	private Integer bibliothecaireId;
	private String nom;
	private String prenom;
	private Compte compte;
	
	public Bibliothecaire(Integer bibliothecaireId, String nom, String prenom, Compte compte) {
		this.bibliothecaireId = bibliothecaireId;
		this.nom = nom;
		this.prenom = prenom;
		this.compte = compte;
	}

	public Bibliothecaire(String nom, String prenom, Compte compte) {
		this.nom = nom;
		this.prenom = prenom;
		this.compte = compte;
	}

	public Integer getBibliothecaireId() {
		return bibliothecaireId;
	}

	public void setBibliothecaireId(Integer bibliothecaireId) {
		this.bibliothecaireId = bibliothecaireId;
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
	
	public Compte getCompte() {
		return compte;
	}
	
	public void setCompteId(Compte compte) {
		this.compte = compte;
	}

	@Override
	public String toString() {
		return "Bibliothecaire [bibliothecaireId=" + bibliothecaireId + ", nom=" + nom + ", prenom=" + prenom + ", compteId=" + compte.getCompteId() + "]";
	}
	
	
}
