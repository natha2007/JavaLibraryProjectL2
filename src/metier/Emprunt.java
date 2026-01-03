package metier;


public class Emprunt {
	private Integer empruntId;
	private String dateDebut;
	private String dateFin;
	private float dureeMaximaleEmprunt;
	private Client client;
	private Objet objet;
	
	public Emprunt(String dateDebut, String dateFin, float dureeMaximaleEmprunt, Client client, Objet objet) {
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.dureeMaximaleEmprunt = dureeMaximaleEmprunt;
		this.client = client;
		this.objet = objet;
	}
	
	public Emprunt(Integer empruntId, String dateDebut, String dateFin, float dureeMaximaleEmprunt, Client client, Objet objet) {
		this.empruntId=empruntId;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.dureeMaximaleEmprunt = dureeMaximaleEmprunt;
		this.client = client;
		this.objet = objet;
	}

	public Integer getEmpruntId() {
		return empruntId;
	}

	public void setEmpruntId(Integer empruntId) {
		this.empruntId = empruntId;
	}
	
	public String getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getDateFin() {
		return dateFin;
	}

	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}

	public float getDureeMaximaleEmprunt() {
		return dureeMaximaleEmprunt;
	}

	public void setDureeMaximaleEmprunt(float dureeMaximaleEmprunt) {
		this.dureeMaximaleEmprunt = dureeMaximaleEmprunt;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Objet getObjet() {
		return objet;
	}

	public void setObjet(Objet objet) {
		this.objet = objet;
	}

	@Override
	public String toString() {
		return "Emprunt [empruntId=" + empruntId + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin
				+ ", dureeMaximaleEmprunt=" + dureeMaximaleEmprunt 
				+ ", clientId=" + this.client.getClientId() 
				+ ", objetId=" + this.objet.getObjetId()
				+ "]";
	}
}
