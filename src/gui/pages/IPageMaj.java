package gui.pages;


public interface IPageMaj {
	
	/**
	 * mettre à jour l'UI. Utile quand l'ui affiche des informations dépendantes de l'utilisateur connecté
	 * ou quand elle a besoin de se "rafraichir"
	 */
	public void majUI();
	
	/**
	 * Initialise les éléments à mettre à jour. 
	 * Initialise et crée les éléments statiques.
	 */
	public void initialiserUI();
}
