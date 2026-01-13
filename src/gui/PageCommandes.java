package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PageCommandes extends JPanel implements IPage {
	private CompteUtilisateur user;
	private JLabel mainText;

	public PageCommandes() {
		initialiserUI();
		majUI();
	}

	/**
	 * Récupère les infos de l'utilisateur connecté
	 * @param user utilisateur (client ou bibliothecaire)
	 */
	@Override
	public void setUser(CompteUtilisateur user) {
		this.user = user;
		majUI();
	}
	

	/**
	 * Initialise les éléments de l'interface "dynamiques" (dépendant de l'utilisateur)
	 * Et crée les éléments "statiques".
	 */
	private void initialiserUI() {
		mainText = new JLabel();
		add(mainText);
	}
	
	/**
	 * Crée les élements dynamiques (dépendant de l'utilisateur)
	 */
	private void majUI() {
		if (user == null) {
			mainText.setText("En attente de connexion");
		} else {
			mainText.setText("Commandes ici");
			//compléter ici pour les choses qui nécéssitent les infos de l'utilisateur
		}
	}
}
