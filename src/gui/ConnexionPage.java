package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

//idées lambda : transformer un resultSet en collection Java grâce à un lambdaStream
//on peut mettre un abonnement bibliothécaire, vu que l'abonnement crée le compte

public class ConnexionPage extends JFrame{
	
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
		JTextField champId = new JTextField(30);
		champId.setFont(new Font(corps.getFont().getName(), corps.getFont().getStyle(), 25));
		corps.add(champId, ctr);
		
		ctr.insets = new Insets(25,0,0,200);
		ctr.gridy = 2;
		JLabel mdp = new JLabel("Mot de passe");
		mdp.setFont(new Font(corps.getFont().getName(), corps.getFont().getStyle(), 25));
		corps.add(mdp, ctr);
		
		ctr.insets = new Insets(0,100,200,100);
		ctr.gridy = 3;
		JPasswordField champMdp = new JPasswordField(30);
		champMdp.setFont(new Font(corps.getFont().getName(), corps.getFont().getStyle(), 25));
		corps.add(champMdp, ctr);
		
		pack();
		//setSize(600,600);
	}
	
}
