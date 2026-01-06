package gui;

public class CompteUtilisateur {
	private final Integer compteId;
	private final String type;
	private Integer clientId;
	private Integer bibliothecaireId;
	
	public CompteUtilisateur(Integer compteId, String type) {
		this.compteId = compteId;
		this.type = type;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getBibliothecaireId() {
		return bibliothecaireId;
	}

	public void setBibliothecaireId(Integer bibliothecaireId) {
		this.bibliothecaireId = bibliothecaireId;
	}

	public Integer getCompteId() {
		return compteId;
	}

	public String getType() {
		return type;
	}
	
	
}
