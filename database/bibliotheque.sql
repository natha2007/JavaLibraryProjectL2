CREATE TABLE Client (
	clientId INT AUTO_INCREMENT NOT NULL,
	nom VARCHAR(80) NOT NULL,
	prenom VARCHAR(30) NOT NULL,
	abonnementId INT NOT NULL,
	compteId INT NOT NULL
)

CREATE TABLE Abonnement (
	abonnementId INT AUTO_INCREMENT NOT NULL,
	type VARCHAR(20),
	prix DECIMAL(4,2) NOT NULL
)

CREATE TABLE Session(
	//je dois finir le fichier sql
)
