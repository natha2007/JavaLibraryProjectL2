package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.CompteDAO;
import dao.EmpruntDAO;
import dao.ClientDAO;
import dao.ObjetDAO;
import metier.Compte;
import metier.Client;
import metier.Emprunt;
import metier.Objet;

public class PageEmprunts extends JPanel implements IPage {
	
	private Runnable rb;
	private CompteUtilisateur user;
	private JLabel mainText;
	private JScrollPane scroll;
	private JTextField barreRecherche;
	private JTextField ideVal;
	private DefaultTableModel tabRes;
	private ArrayList<Objet> listeObjets;
	private JPanel body;
	private JLabel titreRecherche;
	private JLabel confirmation=new JLabel();
	private JPanel recherche;
	private Boolean checked;
	private CompteDAO cd =new CompteDAO();
	private ClientDAO cld =new ClientDAO();
	private ObjetDAO od = new ObjetDAO();
	private EmpruntDAO ed = new EmpruntDAO();
	private String identifiant;
	private Integer objetId;
	
	private int count = 0;

	public PageEmprunts(Runnable rb) {
		this.rb = rb;
		initialiserUI();
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
		setLayout(new BorderLayout());
		mainText = new JLabel();
		add(mainText, BorderLayout.NORTH);
		

        String[] colonnes = {
        	"Sélection",
            "objetId",
            "Nom",
            "auteur",
            "prix",
            "typeObjet",
            "disponibilité",
            "Référence"
        };
        
        tabRes = new DefaultTableModel(colonnes, 0) {

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // seule la case à cocher est éditable
            }
        };
        JTable table = new JTable(tabRes);
        
		tabRes.addTableModelListener(e -> {
		    int row = e.getFirstRow();
		    int col = e.getColumn();

		    if (col == 0) { // décoche un élément si un autre est coché
		        checked = (Boolean) tabRes.getValueAt(row, 0);

		        if (checked) {
		            for (int i = 0; i < tabRes.getRowCount(); i++) {
		                if (i != row) {
		                    tabRes.setValueAt(false, i, 0);
		                }
		            }
		        }
		    }
		});

		
		body = new JPanel(new GridLayout(2,1));
		add(body, BorderLayout.CENTER);
		
		JPanel haut = new JPanel();
		haut.setLayout(new BorderLayout());
		
		recherche = new JPanel(new GridLayout(1, 3));
		
		titreRecherche = new JLabel("Rechercher et sélectionnez la référence :");
		barreRecherche = new JTextField(40);
		barreRecherche.setMinimumSize(new Dimension(100,20));
		barreRecherche.setMaximumSize(new Dimension(200,20));
		//barreRecherche.setPreferredSize(new Dimension(50,20));
		recherche.add(titreRecherche);
		recherche.add(barreRecherche);		
		
		BufferedImage loupeFic = null;
		try {
			loupeFic = ImageIO.read(new File("img/loupe.png"));
		} catch (IOException e) {
			System.out.println("Fichier introuvable");
			e.printStackTrace();
		}
		ImageIcon imageLoupe = new ImageIcon(loupeFic);
		Image img = imageLoupe.getImage().getScaledInstance(16, 12, Image.SCALE_SMOOTH);
		JButton loupe = new JButton(new ImageIcon(img));
		
		recherche.add(loupe);
		haut.add(recherche, BorderLayout.NORTH);
		
		scroll = new JScrollPane(table);
		haut.add(scroll, BorderLayout.CENTER);
		
		body.add(haut);

		JPanel bas = new JPanel(new GridLayout(4,1,5,5));
		
		JLabel ideTitre=new JLabel("Saisissez l'identifiant du client :");
		ideVal = new JTextField(15);
		JButton valider = new JButton("Valider");
		
		JPanel ligne1 = new JPanel(new GridBagLayout());
		JPanel ligne2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel ligne3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel ligne4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		ligne1.add(ideTitre);
		ligne2.add(ideVal);
		ligne3.add(confirmation);
		ligne4.add(valider);
		
		bas.add(ligne1);
		bas.add(ligne2);
		bas.add(ligne3);
		bas.add(ligne4);
		
		
		body.add(bas);

		loupe.addActionListener(e -> {
			count++;
			if (count%2 == 0) {
				rechercherObjet();
				rb.run();
			} else {
				majUI();
				titreRecherche.setText("Rechercher puis sélectionnez l'objet");
			}
		});

		rechercherObjet();
		
		valider.addActionListener(e -> validerEmprunt());
	}
	
	/**
	 * Crée les élements dynamiques (dépendant de l'utilisateur) ou qui doivent se mettre à jour au lancement de la page
	 */
	public void majUI() {
		if (user == null) {
			mainText.setText("En attente de connexion");
			return;
		} else {
			mainText.setText("Stock actuel de la bibliothèque");
			ObjetDAO od = new ObjetDAO();
			listeObjets = od.getListeObjet();
			tabRes.setRowCount(0);
			
			 for (Objet o : listeObjets) {
				 
				 if (o.getDisponibilite() == 1) {
					 	Object[] ligne = {
		        			false,
		        			o.getObjetId(),
		        			o.getNom(),
		        			o.getAuteur(),
		        			o.getPrix(),
		        			o.getTypeObjet(),
		        			o.getDisponibilite(),
		        			o.getReference()
		        	};
		        	
		        	tabRes.addRow(ligne);
		        }
			 }
		}
	}
	
	public void rechercherObjet() {
		ObjetDAO od = new ObjetDAO();
		String recherche = barreRecherche.getText();
		Objet o1 = od.read(recherche);
		if (o1 == null) {
			tabRes.setRowCount(0);
			titreRecherche.setText("Aucun objet trouvé");
		} else {
			listeObjets = od.getListeObjet(recherche);
			tabRes.setRowCount(0);
			
			 for (Objet o : listeObjets) {
				 if (o.getDisponibilite() == 1) {
		        	Object[] ligne = {
		        			false,
		        			o.getObjetId(),
		        			o.getNom(),
		        			o.getAuteur(),
		        			o.getPrix(),
		        			o.getTypeObjet(),
		        			o.getDisponibilite(),
		        			o.getReference()
		        	};
		        	
		        	tabRes.addRow(ligne);
		        }
		}
		}
	}
	
	private void validerEmprunt() {
		try {
			objetId = getObjetIdSelectionne();
			idValide();
			creerEmprunt(objetId);
			pageConfirmation();
		} catch (SaisieInvalideException s) {
			afficherErreur(s.getMessage());
		}
	}
	
	private void afficherErreur(String message) {
	    confirmation.setForeground(Color.RED);
	    confirmation.setText(message);
	}
	
	private void creerEmprunt(Integer objetId) {
		LocalDate today = LocalDate.now();
		LocalDate finEmprunt = today.plusDays(30);
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	String ajd = today.format(formatter);
    	String fe = finEmprunt.format(formatter);
    	Compte c=cd.readAvecIdentifiant(identifiant);
    	Integer compteId=c.getCompteId(); 
    	Client cl=cld.read(compteId);
    	Objet o=od.read(objetId);
    	Emprunt e=new Emprunt(ajd,fe,30f,cl,o);
    	ed.create(e);
    	od.updateDispo(o);
	}
	
	private int getObjetIdSelectionne() throws SaisieInvalideException {
	    for (int i = 0; i < tabRes.getRowCount(); i++) {
	        Boolean selected = (Boolean) tabRes.getValueAt(i, 0);
	        if (selected != null && selected) {
	            return (int) tabRes.getValueAt(i, 1); // colonne 1 = objetId
	        }
	    }
	    throw new SaisieInvalideException("Vous devez sélectionner un objet");
	}


	
	private void idValide() throws SaisieInvalideException{
		identifiant=ideVal.getText();
		if (identifiant.isEmpty()) {
			throw new SaisieInvalideException("Vous devez saisir un identifiant");
		}
		else if (!cd.exists(identifiant)) {
			throw new SaisieInvalideException("Identifiant invalide");
		}
		Compte c=cd.read(identifiant);
		if (("Bibliothécaire").equals(c.getTypeCompte())) {
			throw new SaisieInvalideException("Cet identifiant appartient a un bibliothécaire");
		};
	}
	
	private void pageConfirmation() {
        removeAll();
        setLayout(new BorderLayout());

        JLabel message = new JLabel(
            "Emprunt réalisé avec succès.",
            JLabel.CENTER
        );
        message.setFont(new Font("Arial", Font.BOLD, 16));

        JButton retour = new JButton("Retour");
        retour.addActionListener(e -> resetPage());

        JPanel centre = new JPanel(new BorderLayout());
        centre.add(message, BorderLayout.CENTER);

        JPanel bas = new JPanel();
        bas.add(retour);

        add(centre, BorderLayout.CENTER);
        add(bas, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
	
	public void resetPage() {
	    removeAll();
	    revalidate();
	    repaint();

	    initialiserUI();
	    majUI();
	    rb.run();
	}

}
