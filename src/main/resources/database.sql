-- Création de la table users
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL
);

-- Insertion des données dans la table users
INSERT INTO users (id, email, first_name, last_name)
VALUES (385209, 'bob.brown@yopmail.com', 'Bob', 'Brown'),
       (492816, 'jane.smith@yopmail.com', 'Jane', 'Smith'),
       (627481, 'alice.jones@yopmail.com', 'Alice', 'Jones'),
       (948162, 'john.doe@yopmail.com', 'John', 'Doe');

-- Création de la table credentials
CREATE TABLE IF NOT EXISTS credentials (
    id INT PRIMARY KEY,
    created_at DATETIME NOT NULL,
    password VARCHAR(255) NOT NULL,
    updated_at DATETIME NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Insertion des données dans la table credentials
INSERT INTO credentials (id, created_at, password, updated_at, user_id)
VALUES (5, '2024-11-25 14:25:47.000000', '$2y$10$waPVWJNiZ9pjJ.V0kokCBuuho82lOBm/b9b9mWYhYsyVWFa5MGXjO', '2024-11-25 14:25:50.000000', 948162),
        (6, '2024-11-25 14:25:52.000000', '$2y$10$MUmM.ghfpAE.pJ4x9U3vQ.OziNG809Gw/Q6PLSSjvKRC5GppZtT56', '2024-11-25 14:25:54.000000', 492816),
        (7, '2024-11-25 14:25:55.000000', '$2y$10$hYKlgYYgGSWtqld2jmsnGO7DXX3nn9qtqtiNP8c8GGX4X.AMk0Nxi', '2024-11-25 14:25:56.000000', 627481),
        (8, '2024-11-25 14:25:58.000000', '$2y$10$JFqltMC4hZdb9aBQGqTBruYcIYEqD9gE29GLjsNFaJ7lsBPYXZ8d6', '2024-11-25 14:25:59.000000', 385209);

-- Création de la table status
CREATE TABLE IF NOT EXISTS status (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Insertion des données dans la table status
INSERT INTO status (id, name)
VALUES (1, 'INITIALE'),
       (2, 'EN_ATTENTE_VALIDATION'),
       (3, 'VALIDEE'),
       (4, 'REJETEE'),
       (5, 'ANNULEE');

-- Création de la table user_role
CREATE TABLE IF NOT EXISTS user_role (
    user_id INT,
    role VARCHAR(100) NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Insertion des données dans la table user_role
INSERT INTO user_role (user_id, role)
VALUES (948162, 'COLLABORATOR'),
       (492816, 'COLLABORATOR'),
       (627481, 'MANAGER'),
       (385209, 'MANAGER'),
       (627481, 'ADMINISTRATOR');

-- Création de la table mission_type
CREATE TABLE IF NOT EXISTS mission_type (
    id INT PRIMARY KEY,
    average_daily_rate DECIMAL(10,2),
    bonus_amount DECIMAL(10,2),
    bonus_percentage DECIMAL(5,2),
    end_date DATE,
    is_bonus BOOLEAN,
    is_charged BOOLEAN,
    label VARCHAR(100),
    start_date DATE
);

-- Insertion des données dans la table mission_type
INSERT INTO mission_type (id, average_daily_rate, bonus_amount, bonus_percentage, end_date, is_bonus, is_charged, label, start_date)
VALUES (1, 100, NULL, NULL, NULL, 0, 1, 'Maçonnerie', '2023-11-29'),
       (2, 350, NULL, NULL, NULL, 0, 1, 'Peinture', '2023-11-29'),
       (3, 400, NULL, 2.8, NULL, 1, 1, 'Logistique', '2023-11-29'),
       (4, NULL, NULL, NULL, NULL, 0, 0, 'Bénévolat', '2023-11-29'),
       (5, 100, NULL, NULL, '2024-11-29', 0, 1, 'Maçonnerie', '2023-11-29');


-- Création de la table missions
CREATE TABLE IF NOT EXISTS missions (
    id INT PRIMARY KEY,
    end_date DATE,
    end_town VARCHAR(100),
    start_date DATE,
    start_town VARCHAR(100),
    mission_type_id INT,
    status_id INT,
    FOREIGN KEY (mission_type_id) REFERENCES mission_type(id),
    FOREIGN KEY (status_id) REFERENCES status(id)
);

-- Insertion des données dans la table missions
INSERT INTO missions (id, end_date, end_town, start_date, start_town, mission_type_id, status_id)
VALUES (1, '2023-01-20', 'Lyon', '2023-01-15', 'Paris', 1, 1),
       (2, '2023-02-10', 'Marseille', '2023-02-05', 'Nice', 1, 2),
       (3, '2023-03-15', 'Bordeaux', '2023-03-10', 'Toulouse', 2, 1),
       (4, '2023-04-20', 'Strasbourg', '2023-04-15', 'Nancy', 3, 5),
       (5, '2023-05-25', 'Lille', '2023-05-20', 'Roubaix', 3, 4),
       (6, '2023-06-30', 'Nantes', '2023-06-25', 'Rennes', 4, 3);

-- Création de la table transport
CREATE TABLE IF NOT EXISTS transport (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Insertion des données dans la table transport
INSERT INTO transport (id, name)
VALUES (6, 'Avion'),
       (7, 'Bateau'),
       (1, 'Bus'),
       (4, 'Covoiturage'),
       (2, 'Train'),
       (5, 'Tram'),
       (3, 'Voiture');

-- Création de la table mission_transport
CREATE TABLE IF NOT EXISTS mission_transport (
    mission_id INT,
    transport_id INT,
    PRIMARY KEY (mission_id, transport_id),
    FOREIGN KEY (mission_id) REFERENCES missions(id),
    FOREIGN KEY (transport_id) REFERENCES transport(id)
);

-- Insertion des données dans la table mission_transport
INSERT INTO mission_transport (mission_id, transport_id)
VALUES (2, 1),
       (2, 2),
       (3, 2),
       (5, 2),
       (6, 2),
       (4, 5),
       (4, 6);

-- Création de la table expense_type
CREATE TABLE IF NOT EXISTS expense_type (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Insertion des données dans la table expense_type
INSERT INTO expense_type (id, name)
VALUES (1, 'Transport'),
       (2, 'Hotel'),
       (3, 'Repas'),
       (4, 'Divers');

-- Création de la table expense_report
CREATE TABLE IF NOT EXISTS expense_report (
    id INT PRIMARY KEY,
    mission_id INT,
    status_id INT,
    FOREIGN KEY (mission_id) REFERENCES missions(id),
    FOREIGN KEY (status_id) REFERENCES status(id)
);

-- Insertion des données dans la table expense_report
INSERT INTO expense_report (id, mission_id, status_id)
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 3, 2),
       (4, 4, 3),
       (5, 5, 4);

-- Création de la table expense
CREATE TABLE IF NOT EXISTS expense (
    id INT PRIMARY KEY,
    date DATE,
    amount DECIMAL(10,2),
    tax DECIMAL(10,2),
    expense_report_id INT,
    type_id INT,
    FOREIGN KEY (expense_report_id) REFERENCES expense_report(id),
    FOREIGN KEY (type_id) REFERENCES expense_type(id)
);

-- Insertion des données dans la table expense
INSERT INTO expense (id, date, amount, tax, expense_report_id, type_id)
VALUES (1, '2024-11-24', 249.90, 30, 2, 3),
       (2, '2024-11-25', 145.70, 25, 3, 1),
       (3, '2024-11-26', 59.00, 10, 4, 4),
       (4, '2024-11-27', 89.50, 15, 5, 2);
