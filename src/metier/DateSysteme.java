package metier;

import java.time.LocalDate;

public class DateSysteme {

	private Integer dateId; 
	private LocalDate dateDuJour;
	private boolean dateManuelle;

	
	public DateSysteme(LocalDate dateDuJour, boolean dateManuelle) {
		this.dateDuJour = dateDuJour;
		this.dateManuelle = dateManuelle;
		
		/*
		 * dateId sera géré dans la méthode create
		 * de DateSystemeDAO. mais enft jsp trop si ca marche bien ici, car je vois pas d'increment 
		 * avec le test
		 * L'id s'incrémente automatiquement en base.)
		 */
	}

	public DateSysteme(Integer dateId, LocalDate dateDuJour, boolean dateManuelle) {
		this.dateId = dateId;
		this.dateDuJour = dateDuJour;
		this.dateManuelle = dateManuelle;
	}

	public Integer getDateId() {
		return dateId;
	}

	public void setDateId(Integer dateId) {
		this.dateId = dateId;
	}

	public LocalDate getDateDuJour() {
		return dateDuJour;
	}

	public void setDateDuJour(LocalDate dateDuJour) {
		this.dateDuJour = dateDuJour;
	}

	public boolean isDateManuelle() {
		return dateManuelle;
	}

	public void setDateManuelle(boolean dateManuelle) {
		this.dateManuelle = dateManuelle;
	}

	@Override
	public String toString() {
		return "DateSysteme [dateId=" + dateId + ", dateDuJour=" + dateDuJour
				+ ", dateManuelle=" + dateManuelle + "]";
	}
}