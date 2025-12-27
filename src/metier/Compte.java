package metier;

public class Compte {
	private Integer compteId;
	private String identifiant;
	private String mdpHash;
	private String dateCreation;
	private String typeCompte;
	//penser à vérifier que dateCreation est bien une date,
	//que la taille des String ne dépasse pas la limite max
	//des VARCHAR
	
	public Compte(String identifiant, String mdpHash, String dateCreation, String typeCompte) {
		this.identifiant = identifiant;
		this.mdpHash = mdpHash;
		this.dateCreation = dateCreation;
		this.typeCompte = typeCompte;
	}

	//penser à transformer le hash du mdp, où ? peut être dans l'UI

	public Compte(Integer compteId, String identifiant, String mdpHash, String dateCreation, String typeCompte) {
		this.identifiant = identifiant;
		this.mdpHash = mdpHash;
		this.dateCreation = dateCreation;
		this.typeCompte = typeCompte;
		this.compteId = compteId;
	}

	
	public Integer getCompteId() {
		return compteId;
	}

	public void setCompteId(Integer compteId) {
		this.compteId = compteId;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getMdpHash() {
		return mdpHash;
	}

	public void setMdpHash(String mdpHash) {
		this.mdpHash = mdpHash;
	}

	public String getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getTypeCompte() {
		return typeCompte;
	}

	public void setTypeCompte(String typeCompte) {
		this.typeCompte = typeCompte;
	}

	@Override
	public String toString() {
		return "Compte [compteId=" + compteId + ", identifiant=" + identifiant + ", mdpHash=" + mdpHash
				+ ", dateCreation=" + dateCreation + ", typeCompte=" + typeCompte + "]";
	}
	
}
