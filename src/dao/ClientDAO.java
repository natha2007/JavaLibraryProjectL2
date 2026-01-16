// version fusionnée 


package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import metier.Abonnement;
import metier.Client;
import metier.Compte;

public class ClientDAO extends DAO<Client> {
    private ResultSet rs;

    @Override
    public Client create(Client cl) {
        String sql = "INSERT INTO client(nom, prenom, abonnementId, compteId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cl.getNom());
            ps.setString(2, cl.getPrenom());

            // abonnement nullable
            if (cl.getAbonnement() == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, cl.getAbonnement().getAbonnementId());
            }

            ps.setInt(4, cl.getCompte().getCompteId());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                cl.setClientId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL ClientDAO.create");
            e.printStackTrace();
        }
        return cl;
    }

    @Override
    public Client update(Client cl) {
        // Gestion abonnement nullable
        String aboVal;
        if (cl.getAbonnement() == null) {
            aboVal = "NULL";
        } else {
            aboVal = Integer.toString(cl.getAbonnement().getAbonnementId());
        }

        String requete = "UPDATE client SET "
                + "nom ='" + cl.getNom() + "', "
                + "prenom ='" + cl.getPrenom() + "', "
                + "abonnementId =" + aboVal + ", "
                + "compteId =" + cl.getCompte().getCompteId() + " "
                + "WHERE clientId =" + cl.getClientId();
        try {
            stmt.executeUpdate(requete);
        } catch (SQLException e) {
            System.out.println("Erreur SQL ClientDAO.update");
            e.printStackTrace();
        }
        return cl;
    }

    @Override
    public void delete(Client cl) {
        String requete = "DELETE FROM client WHERE clientId =" + cl.getClientId();
        try {
            stmt.executeUpdate(requete);
        } catch (SQLException e) {
            System.out.println("Erreur SQL ClientDAO.delete");
            e.printStackTrace();
        }
    }

    public Client read(Integer id) {
        Client cl = null;
        Abonnement ab = null;
        Compte c = null;
        AbonnementDAO ad = new AbonnementDAO();
        CompteDAO cd = new CompteDAO();
        String requete = "SELECT * FROM client WHERE clientId =" + id;
        try {
            rs = stmt.executeQuery(requete);
            if (rs.first()) {
                ab = ad.read(rs.getInt("abonnementId"));
                c = cd.read(rs.getInt("compteId"));
                cl = new Client(rs.getInt("clientId"), rs.getString("nom"), rs.getString("prenom"), ab, c);
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL ClientDAO.read");
            e.printStackTrace();
        }
        return cl;
    }

    public Integer getClientFromCompte(Integer compteId) {
        Integer clientId = null;
        String requete = "SELECT clientId FROM client WHERE compteId=" + compteId;
        try {
            rs = stmt.executeQuery(requete);
            if (rs.first()) {
                clientId = rs.getInt("clientId");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL ClientDAO.getClientFromCompte");
            e.printStackTrace();
        }
        return clientId;
    }
}


// version 100% bilal (je les écrit ici au cas ou qqc ne marche plus, on a une trace)

/*

package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import metier.Abonnement;
import metier.Client;
import metier.Compte;

public class ClientDAO extends DAO<Client> {
    private ResultSet rs;

    @Override
    public Client create(Client cl) {
        String sql = "INSERT INTO client(nom, prenom, abonnementId, compteId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cl.getNom());
            ps.setString(2, cl.getPrenom());

            // abonnement nullable
            if (cl.getAbonnement() == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, cl.getAbonnement().getAbonnementId());
            }

            ps.setInt(4, cl.getCompte().getCompteId());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                cl.setClientId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL ClientDAO.create");
            e.printStackTrace();
        }
        return cl;
    }

    @Override
    public Client update(Client cl) {
        String requete = "UPDATE client SET "
                + "nom ='" + cl.getNom() + "', "
                + "prenom ='" + cl.getPrenom() + "', "
                + "abonnementId =" + cl.getAbonnement().getAbonnementId() + ", "
                + "compteId =" + cl.getCompte().getCompteId() + " "
                + "WHERE clientId =" + cl.getClientId();
        try {
            stmt.executeUpdate(requete);
        } catch (SQLException e) {
            System.out.println("Erreur SQL ClientDAO.update");
            e.printStackTrace();
        }
        return cl;
    }

    @Override
    public void delete(Client cl) {
        String requete = "DELETE FROM client WHERE clientId =" + cl.getClientId();
        try {
            stmt.executeUpdate(requete);
        } catch (SQLException e) {
            System.out.println("Erreur SQL ClientDAO.delete");
            e.printStackTrace();
        }
    }

    public Client read(Integer id) {
        Client cl = null;
        Abonnement ab = null;
        Compte c = null;
        AbonnementDAO ad = new AbonnementDAO();
        CompteDAO cd = new CompteDAO();
        String requete = "SELECT * FROM client WHERE clientId =" + id;
        try {
            rs = stmt.executeQuery(requete);
            if (rs.first()) {
                ab = ad.read(rs.getInt("abonnementId"));
                c = cd.read(rs.getInt("compteId"));
                cl = new Client(rs.getInt("clientId"), rs.getString("nom"), rs.getString("prenom"), ab, c);
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL ClientDAO.read");
            e.printStackTrace();
        }
        return cl;
    }

    public Integer getClientFromCompte(Integer compteId) {
        Integer clientId = null;
        String requete = "SELECT clientId FROM client WHERE compteId=" + compteId;
        try {
            rs = stmt.executeQuery(requete);
            if (rs.first()) {
                clientId = rs.getInt("clientId");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL ClientDAO.getClientFromCompte");
            e.printStackTrace();
        }
        return clientId;
    }
}


*/



// votre version 

/*

package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import metier.Abonnement;
import metier.Client;
import metier.Compte;

public class ClientDAO extends DAO<Client> {
    private ResultSet rs;

    @Override
    public Client create(Client cl) {
        String sql = "INSERT INTO client(nom, prenom, abonnementId, compteId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cl.getNom());
            ps.setString(2, cl.getPrenom());

            // abonnement nullable
            if (cl.getAbonnement() == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, cl.getAbonnement().getAbonnementId());
            }

            ps.setInt(4, cl.getCompte().getCompteId());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                cl.setClientId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL ClientDAO.create");
            e.printStackTrace();
        }
        return cl;
    }

	        // abonnement null quand on le cré ou quand le modifie
	        if (cl.getAbonnement() == null) {
	            ps.setNull(3, Types.INTEGER);
	        } else {
	            ps.setInt(3, cl.getAbonnement().getAbonnementId());
	        }

    @Override
    public void delete(Client cl) {
        String requete = "DELETE FROM client WHERE clientId =" + cl.getClientId();
        try {
            stmt.executeUpdate(requete);
        } catch (SQLException e) {
            System.out.println("Erreur SQL ClientDAO.delete");
            e.printStackTrace();
        }
    }

    public Client read(Integer id) {
        Client cl = null;
        Abonnement ab = null;
        Compte c = null;
        AbonnementDAO ad = new AbonnementDAO();
        CompteDAO cd = new CompteDAO();
        String requete = "SELECT * FROM client WHERE clientId =" + id;
        try {
            rs = stmt.executeQuery(requete);
            if (rs.first()) {
                ab = ad.read(rs.getInt("abonnementId"));
                c = cd.read(rs.getInt("compteId"));
                cl = new Client(rs.getInt("clientId"), rs.getString("nom"), rs.getString("prenom"), ab, c);
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL ClientDAO.read");
            e.printStackTrace();
        }
        return cl;
    }

	        ResultSet rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	            cl.setClientId(rs.getInt(1));
	        }

	    } catch (SQLException e) {
	        System.out.println("Erreur SQL ClientDAO.create");
	        e.printStackTrace();
	    }

	    return cl;
	}


	@Override
	public Client update(Client cl) {
		Integer abonnementId = null;
		if (cl.getAbonnement() != null) {
			abonnementId = cl.getAbonnement().getAbonnementId();
		}
		String aboVal;
		if (abonnementId == null) {
			aboVal = "NULL";
		} else {
			aboVal =  abonnementId.toString();
		}
		String requete = "UPDATE client"
				+ " SET nom ='" + cl.getNom()
				+ "', prenom ='" + cl.getPrenom()
				+ "', abonnementId =" + aboVal
				+ ", compteId =" + cl.getCompte().getCompteId()
				+ " WHERE clientId =" + cl.getClientId();
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return cl;
	}

	@Override
	public void delete(Client cl) {
		String requete = "DELETE FROM client"
				+ " WHERE clientId =" + cl.getClientId();
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
	}
	
	public Client read(Integer id) {
		Client cl = null;
		Abonnement ab = null;
		Compte c = null;
		AbonnementDAO ad = new AbonnementDAO();
		CompteDAO cd = new CompteDAO();
		String requete = "SELECT *"
				+ " FROM client"
				+ " WHERE clientId =" + id;
		try {
			this.rs = stmt.executeQuery(requete);
			if (rs.first()) {
				ab = ad.read(rs.getInt(4));
				c = cd.read(rs.getInt(5));
				cl = new Client(rs.getInt(1),rs.getString(2),rs.getString(3), ab, c);
			}
		} catch (SQLException e) {
			System.out.println("erreur requête SQL");
			e.printStackTrace();
		}
		return cl;
	}
	
	public Integer getClientFromCompte(Integer compteId) {
		Integer clientId = null;
		String requete = "SELECT *"
				+ " FROM client"
				+ " WHERE compteId=" + compteId;
		try {
			rs = stmt.executeQuery(requete);
			if(rs.first()) {
				clientId = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Erreur requête SQL");
			e.printStackTrace();
		}
		return clientId;
	}

}



*/