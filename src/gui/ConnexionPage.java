package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import dao.CompteDAO;

//idées lambda : transformer un resultSet en collection Java grâce à un lambdaStream
//on peut mettre un abonnement bibliothécaire, vu que l'abonnement crée le compte

public class ConnexionPage extends JFrame{
	private JTextField champId = new JTextField(30);
	private JPasswordField champMdp = new JPasswordField(30);
	
	public ConnexionPage(String s) {
		super(s);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		JLabel titre = new JLabel("Bienvenue");
		titre.setHorizontalAlignment(SwingConstants.CENTER);
		titre.setFont(new Font(titre.getFont().getName(), Font.BOLD, 40));
		getContentPane().add(titre, BorderLayout.NORTH);
		
		JPanel ecran = new JPanel();
		JPanel corps = new JPanel();
		ecran.setLayout(new GridBagLayout());
		corps.setLayout(new GridBagLayout());
		getContentPane().add(ecran, BorderLayout.CENTER);
		ecran.add(corps);
		
		GridBagConstraints ctr = new GridBagConstraints();
		
		ctr.insets = new Insets(100,0,0,200); //tlbr
		ctr.gridx = 0;
		ctr.gridy = 0;
		JLabel id = new JLabel("Identifiant");
		id.setFont(new Font(corps.getFont().getName(), corps.getFont().getStyle(), 25));
		corps.add(id, ctr);
		
		ctr.insets = new Insets(0,100,25,100);
		ctr.gridy = 1;
		
		champId.setFont(new Font(corps.getFont().getName(), corps.getFont().getStyle(), 25));
		corps.add(champId, ctr);
		
		ctr.insets = new Insets(25,0,0,200);
		ctr.gridy = 2;
		JLabel mdp = new JLabel("Mot de passe");
		mdp.setFont(new Font(corps.getFont().getName(), corps.getFont().getStyle(), 25));
		corps.add(mdp, ctr);
		
		ctr.insets = new Insets(0,100,200,100);
		ctr.gridy = 3;
		
		champMdp.setFont(new Font(corps.getFont().getName(), corps.getFont().getStyle(), 25));
		corps.add(champMdp, ctr);
		
		JButton connexionBtn = new JButton();
		JPanel pied = new JPanel();
		pied.setLayout(new GridBagLayout());
		getContentPane().add(pied, BorderLayout.SOUTH);
		pied.add(connexionBtn);
		connexionBtn.addActionListener(e -> verifInfos());
		
		pack();
		//setSize(600,600);
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
		} else {
			System.out.println("utilisateur ou mot de passe incorrect");
		}
		return verif;
	}
	//vérifier qu'un nom d'utilisateur soit unique
	
}
