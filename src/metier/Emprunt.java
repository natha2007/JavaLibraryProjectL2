package metier;

import java.sql.Date;

public class Emprunt {
	private Integer empruntId;
	private Date dateDebut;
	private Date dateFin;
	private Client client;
	private Objet objet;
	
	public Emprunt(Date dateDebut, Date dateFin, Client client, Objet objet) {
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.client = client;
		this.objet = objet;
	}
	
	public Emprunt(Integer empruntId, Date dateDebut, Date dateFin, Client client, Objet objet) {
		this.empruntId=empruntId;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.client = client;
		this.objet = objet;
	}

	public Integer getEmpruntId() {
		return empruntId;
	}

	public void setEmpruntId(Integer empruntId) {
		this.empruntId = empruntId;
	}
	
	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
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
				+ ", clientId=" + this.client.getClientId() 
				+ ", objetId=" + this.objet.getObjetId()
				+ "]";
	}
}
