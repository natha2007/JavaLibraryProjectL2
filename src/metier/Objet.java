package metier;

public class Objet {
	private Integer objetId;
	private String nom;
	private String auteur;
	private Float prix;
	private String typeObjet;
	private int disponibilite;
	private String reference;
	
	public Objet(String nom, String auteur, Float prix, String typeObjet, int disponibilite, String reference) {
		this.nom=nom;
		this.auteur=auteur;
		this.prix=prix;
		this.typeObjet=typeObjet;
		this.disponibilite=disponibilite;
		this.reference=reference;
	}
	
	public Objet(Integer objetId, String nom, String auteur, Float prix, String typeObjet, int disponibilite, String reference) {
		this.objetId=objetId;
		this.nom=nom;
		this.auteur=auteur;
		this.prix=prix;
		this.typeObjet=typeObjet;
		this.disponibilite=disponibilite;
		this.reference=reference;
	}

	public Integer getObjetId() {
		return objetId;
	}

	public void setObjetId(Integer objetId) {
		this.objetId = objetId;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public Float getPrix() {
		return prix;
	}

	public void setPrix(Float prix) {
		this.prix = prix;
	}

	public String getTypeObjet() {
		return typeObjet;
	}

	public void setTypeObjet(String typeObjet) {
		this.typeObjet = typeObjet;
	}

	public int getDisponibilite() {
		return disponibilite;
	}

	public void setDisponibilite(int disponibilite) {
		this.disponibilite = disponibilite;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public String toString() {
		return "Objet [objetId=" + objetId + ", nom=" + nom + ", auteur=" + auteur + ", prix=" + prix + ", typeObjet="
				+ typeObjet + ", disponibilite=" + disponibilite + ", reference=" + reference + "]";
	}
}
