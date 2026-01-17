CREATE TABLE abonnement (
	abonnementId INT AUTO_INCREMENT NOT NULL,
	typeAbonnement VARCHAR(20) NOT NULL,
	prix DECIMAL(4,2) NOT NULL,
	nbEmpruntsMax INT NOT NULL,
	CONSTRAINT pkAbonnementId PRIMARY KEY(abonnementId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE compte(
	compteId INT AUTO_INCREMENT NOT NULL,
	identifiant VARCHAR(30) NOT NULL,
	mdpHash VARCHAR(100) NOT NULL,
	dateCreation DATE NOT NULL,
	typeCompte VARCHAR(15) NOT NULL, -- bibliothecaire ou client
	CONSTRAINT pkCompteId PRIMARY KEY(compteId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE client (
	clientId INT AUTO_INCREMENT NOT NULL,
	nom VARCHAR(80) NOT NULL,
	prenom VARCHAR(30) NOT NULL,
	abonnementId INT,
	compteId INT NOT NULL,
	CONSTRAINT pkClientId PRIMARY KEY (clientId),
	CONSTRAINT fkAbonnementId1 FOREIGN KEY (abonnementId)
	REFERENCES abonnement(abonnementId),
	CONSTRAINT fkCompteId1 FOREIGN KEY (compteId)
	REFERENCES compte(compteId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE objet(
	objetId INT AUTO_INCREMENT NOT NULL,
	nom VARCHAR(100) NOT NULL, -- titre pour un livre, modele pour un ordinateur, titre d'un CD...
	auteur VARCHAR(100), -- auteur du livre, du DVD, constructeur de l'ordinateur...
	prix DECIMAL(8,2),
	typeObjet VARCHAR(20) NOT NULL, -- JeuSociete, Ordinateur, Livre, CD, DVD
	disponibilite BOOLEAN NOT NULL DEFAULT 1,
	reference VARCHAR(100) NOT NULL, -- Pour un livre ISBN, pour les autres code barre ou qqch comme ça qui identifie dans le cas où on aurait deux objets de même nom
	CONSTRAINT pkObjetIdObjet PRIMARY KEY (objetId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE emprunt(
	empruntId INT AUTO_INCREMENT NOT NULL,
	dateDebut DATE NOT NULL,
	dateFin DATE NOT NULL,
	dureeMaximaleEmprunt DECIMAL(8,4), -- durée en jours, si on devait mettre des durées de moins d'1 jour, il faudrait choisir des heures multiples de 3 (3h = 0,125 jour)
	clientId INT NOT NULL,
	objetId INT NOT NULL,
	CONSTRAINT pkEmpruntId PRIMARY KEY (empruntId),
	CONSTRAINT fkObjetId1 FOREIGN KEY (objetId)
	REFERENCES objet(objetId),
	CONSTRAINT fkClientId1 FOREIGN KEY (clientId)
	REFERENCES client(clientId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE bibliothecaire(
	bibliothecaireId INT AUTO_INCREMENT NOT NULL,
	nom VARCHAR(80) NOT NULL,
	prenom VARCHAR(30) NOT NULL,
	compteId INT NOT NULL,
	CONSTRAINT pkBibliothecaireId PRIMARY KEY (bibliothecaireId),
	CONSTRAINT fkCompteId2 FOREIGN KEY(compteId)
	REFERENCES compte(compteId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE date_systeme (
	dateId INT AUTO_INCREMENT NOT NULL,
	dateDuJour DATETIME DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pkDateId PRIMARY KEY (dateId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- a chaque lancement de l'application pour que la date se mette à jour
-- UPDATE system_date
-- SET current_date = CURRENT_DATE
-- WHERE id = 1;

-- pouvoir changer la date manuellement pour pouvoir tester
