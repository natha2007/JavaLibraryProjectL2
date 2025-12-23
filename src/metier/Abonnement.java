package metier;

public class Abonnement {
	private Integer abonnementId; //se mettra à null et non pas à 0 (cas de int), plus clair sur le fait que l'objet est pas encore en base, il faut que AbonnementDAO ensuite appelle setId
	private String typeAbonnement;
	private float prix;
	
	public Abonnement(String typeAbonnement, float prix) {
		this.typeAbonnement = typeAbonnement;
		this.prix = prix;
		/*abonnementId sera géré dans la méthode create
		* de AbonnementDAO, il suffira de faire un 
		* INSERT INTO simple, sans préciser l'id
		* et il s'incrémentera automatiquement.
		* Comme les id de toutes les tables sont auto_increment
		* on fera pareil pour toutes les classes
		*/
	}
	
	public int getAbonnementId() {
		return abonnementId;
	}
	
	public void setAbonnementId(int abonnementId) {
		this.abonnementId = abonnementId;
	}
	
	public String getTypeAbonnement() {
		return typeAbonnement;
	}
	
	public void setTypeAbonnement(String typeAbonnement) {
		this.typeAbonnement = typeAbonnement;
	}
	
	public float getPrix() {
		return prix;
	}
	
	public void setPrix(float prix) {
		this.prix = prix;
	}

	@Override
	public String toString() {
		return "Abonnement [abonnementId=" + abonnementId + ", typeAbonnement=" + typeAbonnement + ", prix=" + prix
				+ "]";
	}
	

}
