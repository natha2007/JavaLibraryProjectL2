package gui;

import java.awt.*;

import javax.swing.*;


import dao.CompteDAO;

public class ConnexionPage extends JPanel {
	private JTextField champId = new JTextField(30);
	private JPasswordField champMdp = new JPasswordField(30);
	
	private final Runnable conn;
	
	public ConnexionPage(Runnable conn) {
		this.conn = conn;
		
		JPanel head = new JPanel(new GridBagLayout());
		setLayout(new BorderLayout());
		JLabel titre = new JLabel("Bienvenue");
		titre.setHorizontalAlignment(SwingConstants.CENTER);
		titre.setFont(new Font(titre.getFont().getName(), Font.BOLD, 40));
		add(head, BorderLayout.NORTH);
		head.add(titre);
		
		JPanel body = new JPanel(new GridBagLayout());
		add(body, BorderLayout.CENTER);
		
		GridBagConstraints ctr = new GridBagConstraints();
		
		
		ctr.insets = new Insets(100,0,0,200); //tlbr
		ctr.gridx = 0;
		ctr.gridy = 0;
		JLabel id = new JLabel("Identifiant");
		id.setFont(new Font(body.getFont().getName(), body.getFont().getStyle(), 25));
		body.add(id, ctr);
		
		ctr.insets = new Insets(0,100,25,100);
		ctr.gridy = 1;
		
		champId.setFont(new Font(body.getFont().getName(), body.getFont().getStyle(), 25));
		body.add(champId, ctr);
		
		ctr.insets = new Insets(25,0,0,200);
		ctr.gridy = 2;
		JLabel mdp = new JLabel("Mot de passe");
		mdp.setFont(new Font(body.getFont().getName(), body.getFont().getStyle(), 25));
		body.add(mdp, ctr);
		
		ctr.insets = new Insets(0,100,200,100);
		ctr.gridy = 3;
		
		champMdp.setFont(new Font(body.getFont().getName(), body.getFont().getStyle(), 25));
		body.add(champMdp, ctr);
	 	
		JButton connexionBtn = new JButton("Se connecter");
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(0,100,100,100);
		
		JPanel foot = new JPanel(new GridBagLayout());
		
		add(foot, BorderLayout.SOUTH);
		foot.add(connexionBtn, gbc);
		connexionBtn.addActionListener(e -> verifInfos());
		
	}
	
	public String getIdResult() {
		return champId.getText();
	}
	
	public char[] getMdpResult() {
		return champMdp.getPassword();
	}
	
	public String getMdpResultHash() {
		String pwd = "";
		for (int i = 0; i<this.getMdpResult().length; i++) {
			pwd += this.getMdpResult()[i];
		}
		return GestionMdp.hash(pwd);
	}
	
	public String getMdpAttendu() {
		CompteDAO cd = new CompteDAO();
		String mdpAttendu = "";
		if (cd.exists(getIdResult())) {
			mdpAttendu = cd.read(getIdResult()).getMdpHash();
		} else {
			System.out.println("utilisateur ou mot de passe incorrect #mdpAttendu");
		}
		return mdpAttendu;
	}
	
	public boolean verifInfos() {
		boolean verif = false;
		if (this.getMdpAttendu().equals(this.getMdpResultHash())) {
			verif = true;
			System.out.println("connexion réussie");
			System.out.println("mdp attendu : " + this.getMdpAttendu());
			System.out.println("mdp rentré : " + this.getMdpResultHash());
			conn.run();
			
		} else {
			System.out.println("utilisateur ou mot de passe incorrect");
			JOptionPane.showMessageDialog(this, 
										  "Identifiant ou mot de passe incorrect",
										  "Erreur de connexion",
										  JOptionPane.ERROR_MESSAGE);
		}
		return verif;
	}
	//vérifier qu'un nom d'utilisateur soit unique
}
