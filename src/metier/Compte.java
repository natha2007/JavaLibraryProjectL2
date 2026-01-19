package metier;

import java.sql.Date;

public class Compte {
	private Integer compteId;
	private String identifiant;
	private String mdpHash;
	private Date dateCreation;
	private String typeCompte;
	
	public Compte(String identifiant, String mdpHash, Date dateCreation, String typeCompte) {
		this.identifiant = identifiant;
		this.mdpHash = mdpHash;
		this.dateCreation = dateCreation;
		this.typeCompte = typeCompte;
	}

	public Compte(Integer compteId, String identifiant, String mdpHash, Date dateCreation, String typeCompte) {
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

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
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
