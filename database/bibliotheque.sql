CREATE TABLE Abonnement (
	abonnementId INT AUTO_INCREMENT NOT NULL,
	typeAbonnement VARCHAR(20),
	prix DECIMAL(4,2) NOT NULL,
	CONSTRAINT pkAbonnementId PRIMARY KEY(abonnementId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Compte(
	compteId INT AUTO_INCREMENT NOT NULL,
	identifiant VARCHAR(30) NOT NULL,
	mdpHash VARCHAR(100) NOT NULL,
	dateCreation DATE NOT NULL,
	typeCompte VARCHAR(15) NOT NULL, -- bibliothecaire ou client
	CONSTRAINT pkCompteId PRIMARY KEY(compteId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Client (
	clientId INT AUTO_INCREMENT NOT NULL,
	nom VARCHAR(80) NOT NULL,
	prenom VARCHAR(30) NOT NULL,
	abonnementId INT NOT NULL,
	compteId INT NOT NULL,
	CONSTRAINT pkClientId PRIMARY KEY (clientId),
	CONSTRAINT fkAbonnementId1 FOREIGN KEY (abonnementId)
	REFERENCES Abonnement(abonnementId),
	CONSTRAINT fkCompteId1 FOREIGN KEY (compteId)
	REFERENCES Compte(compteId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Objet(
	objetId INT AUTO_INCREMENT NOT NULL,
	nom VARCHAR(100) NOT NULL, -- titre pour un livre, modele pour un ordinateur, titre d'un CD...
	auteur VARCHAR(100), -- auteur du livre, du DVD, constructeur de l'ordinateur
	prix DECIMAL(4,2),
	typeObjet VARCHAR(10) NOT NULL, -- JeuSociete, Ordinateur, Livre, CD, DVD
	quantite INT NOT NULL DEFAULT 1,
	reference VARCHAR(100) NOT NULL, -- Pour un livre ISBN, pour les autres code barre ou qqch comme ça qui identifie dans le cas où on aurait deux objets de même nom
	CONSTRAINT pkObjetIdObjet PRIMARY KEY (objetId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Emprunt(
	objetId INT NOT NULL,
	dateDebut DATE NOT NULL,
	dateFin DATE,
	dureeMaximaleEmprunt DECIMAL(8,4), -- durée en jours, si on devait mettre des durées de moins d'1 jour, il faudrait choisir des heures multiples de 3 (3h = 0,125 jour)
	clientId INT NOT NULL,
	CONSTRAINT pkObjetIdEmprunt PRIMARY KEY (objetId),
	CONSTRAINT fkObjetId1 FOREIGN KEY (objetId)
	REFERENCES Objet(objetId),
	CONSTRAINT fkClientId1 FOREIGN KEY (clientId)
	REFERENCES Client(clientId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Bibliothecaire(
	bibliothecaireId INT AUTO_INCREMENT NOT NULL,
	nom VARCHAR(80) NOT NULL,
	prenom VARCHAR(30) NOT NULL,
	compteId INT NOT NULL,
	CONSTRAINT pkBibliothecaireId PRIMARY KEY (bibliothecaireId),
	CONSTRAINT fkCompteId2 FOREIGN KEY(compteId)
	REFERENCES Compte(compteId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE JeuSociete(
	jeuSocieteId INT AUTO_INCREMENT NOT NULL,
	nom VARCHAR(100) NOT NULL,
	auteur VARCHAR(100),
	dateAchat DATE NOT NULL,
	reference VARCHAR(100) NOT NULL,
	objetId INT NOT NULL,
	CONSTRAINT pkJeuSocieteId PRIMARY KEY(jeuSocieteId),
	CONSTRAINT fkObjetId2 FOREIGN KEY (objetId)
	REFERENCES Objet(objetId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE CD(
	cdId INT AUTO_INCREMENT NOT NULL,
	titre VARCHAR(100) NOT NULL,
	auteur VARCHAR(100),
	dateAchat DATE NOT NULL,
	reference VARCHAR(100) NOT NULL,
	genre VARCHAR(50), -- Rock, Pop, Mathématiques, espagnol...
	objetId INT NOT NULL,
	CONSTRAINT pkCdId PRIMARY KEY(cdId),
	CONSTRAINT fkObjetId3 FOREIGN KEY (objetId)
	REFERENCES Objet(objetId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Livre(
	livreId INT AUTO_INCREMENT NOT NULL,
	titre VARCHAR(100) NOT NULL,
	auteur VARCHAR(100),
	dateAchat DATE NOT NULL,
	referenceISBN VARCHAR(100) NOT NULL,
	genre VARCHAR(50), -- Roman historique, société, Policier, magazine, BD, manga...
	objetId INT NOT NULL,
	CONSTRAINT pkLivreId PRIMARY KEY(livreId),
	CONSTRAINT fkObjetId4 FOREIGN KEY (objetId)
	REFERENCES Objet(objetId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE DVD(
	dvdId INT AUTO_INCREMENT NOT NULL,
	titre VARCHAR(100) NOT NULL,
	auteur VARCHAR(100),
	dateAchat DATE NOT NULL,
	reference VARCHAR(100) NOT NULL,
	genre VARCHAR(50), -- Thriller, comédie, géopolitique...
	objetId INT NOT NULL,
	CONSTRAINT pkDvdId PRIMARY KEY(dvdId),
	CONSTRAINT fkObjetId5 FOREIGN KEY (objetId)
	REFERENCES Objet(objetId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE ORDINATEUR(
	ordinateurId INT AUTO_INCREMENT NOT NULL,
	titre VARCHAR(100) NOT NULL,
	auteur VARCHAR(100),
	dateAchat DATE NOT NULL,
	reference VARCHAR(100) NOT NULL,
	genre VARCHAR(50), -- Thriller, comédie, géopolitique...
	objetId INT NOT NULL,
	CONSTRAINT pkOrdinateurId PRIMARY KEY(ordinateurId),
	CONSTRAINT fkObjetId6 FOREIGN KEY (objetId)
	REFERENCES Objet(objetId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE DateSysteme (
	dateId INT AUTO_INCREMENT NOT NULL,
	dateDuJour DATE DEFAULT CURDATE(),
	dateManuelle BOOLEAN DEFAULT FALSE, -- permet de changer la date manuellement si true
	CONSTRAINT pkDateId PRIMARY KEY (dateId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- On fait pas de table catégorie finalement--

-- a chaque lancement de l'application pour que la date se mette à jour
-- UPDATE system_date
-- SET current_date = CURRENT_DATE
-- WHERE id = 1;

-- pouvoir changer la date manuellement pour pouvoir tester--
