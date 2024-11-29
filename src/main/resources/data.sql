# This file contains the SQL script to populate the database with some data
# It can be executed by the Spring Boot application when it starts by setting
# the following property in the application.properties file:
# spring.sql.init.mode=always

# Checkout into the database
use mission_note_de_frais;

# Insert user data
INSERT INTO users (id, email, first_name, last_name)
VALUES (385209, 'bob.brown@yopmail.com', 'Bob', 'Brown'),
       (492816, 'jane.smith@yopmail.com', 'Jane', 'Smith'),
       (627481, 'alice.jones@yopmail.com', 'Alice', 'Jones'),
       (948162, 'john.doe@yopmail.com', 'John', 'Doe');

# Insert credential data
INSERT INTO credentials (id, created_at, password, updated_at, user_id)
VALUES  (5, '2024-11-25 14:25:47.000000', '$2y$10$waPVWJNiZ9pjJ.V0kokCBuuho82lOBm/b9b9mWYhYsyVWFa5MGXjO', '2024-11-25 14:25:50.000000', 948162),
        (6, '2024-11-25 14:25:52.000000', '$2y$10$MUmM.ghfpAE.pJ4x9U3vQ.OziNG809Gw/Q6PLSSjvKRC5GppZtT56', '2024-11-25 14:25:54.000000', 492816),
        (7, '2024-11-25 14:25:55.000000', '$2y$10$hYKlgYYgGSWtqld2jmsnGO7DXX3nn9qtqtiNP8c8GGX4X.AMk0Nxi', '2024-11-25 14:25:56.000000', 627481),
        (8, '2024-11-25 14:25:58.000000', '$2y$10$JFqltMC4hZdb9aBQGqTBruYcIYEqD9gE29GLjsNFaJ7lsBPYXZ8d6', '2024-11-25 14:25:59.000000', 385209);

# Insert mission status data
INSERT INTO status (id, name)
VALUES (1, 'INITIALE'),
       (2, 'EN_ATTENTE_VALIDATION'),
       (3, 'VALIDEE'),
       (4, 'REJETEE'),
       (5, 'ANNULEE');

# Insert user role data
INSERT INTO user_role (user_id, role)
VALUES (948162, 'COLLABORATOR'),
       (492816, 'COLLABORATOR'),
       (627481, 'MANAGER'),
       (385209, 'MANAGER'),
       (627481, 'ADMINISTRATOR');

# Insert mission data
INSERT INTO missions (id, end_date, end_town, start_date, start_town, mission_type_id, status_id)
VALUES (1, '2023-01-20', 'Lyon', '2023-01-15', 'Paris', 1, 1),
       (2, '2023-02-10', 'Marseille', '2023-02-05', 'Nice', 1, 2),
       (3, '2023-03-15', 'Bordeaux', '2023-03-10', 'Toulouse', 2, 1),
       (4, '2023-04-20', 'Strasbourg', '2023-04-15', 'Nancy', 3, 5),
       (5, '2023-05-25', 'Lille', '2023-05-20', 'Roubaix', 3, 4),
       (6, '2023-06-30', 'Nantes', '2023-06-25', 'Rennes', 0, 3);
     
# Insert missionType data 
INSERT INTO mission_type (id, average_daily_rate, bonus_amount, bonus_percentage, end_date, is_bonus, is_charged, label, start_date) 
VALUES (1, 100, NULL, NULL, NULL, b'0', b'1', 'Maçonnerie', '2024-11-29'),
	   (2, 350, NULL, NULL, NULL, b'0', b'1', 'Peinture', '2024-11-29'),
	   (3, 400, NULL, 2,8, NULL, b'1', b'1', 'Logistique', '2024-11-29'),
	   (4, NULL, NULL, NULL, NULL, b'0', b'0', 'Bénévolat', '2024-11-29');

# Insert transport data
INSERT INTO transport (id, name)
VALUES (6, 'Avion'),
       (7, 'Bateau'),
       (1, 'Bus'),
       (4, 'Covoiturage'),
       (2, 'Train'),
       (5, 'Tram'),
       (3, 'Voiture');

# Insert mission_transport data
INSERT INTO mission_transport (mission_id, transport_id)
VALUES (2, 1),
       (2, 2),
       (3, 2),
       (5, 2),
       (6, 2),
       (4, 5),
       (4, 6);

# Insert expense type data
INSERT INTO expense_type (id, name)
VALUES (1, 'Transport'),
       (2, 'Hotel'),
       (3, 'Repas'),
       (4, 'Divers');

# Insert expense report data
INSERT INTO expense_report (id, mission_id, status_id)
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 3, 2),
       (4, 4, 3),
       (5, 5, 4);

# Insert expense data
INSERT INTO expense (id, date, amount, tax, expense_report_id, type_id)
VALUES (1, '2024-11-23', 121.35, 20, 1, 2),
       (2, '2024-11-23', 2.5, 10, 1, 1),
       (3, '2024-11-24', 15.61, 20, 2, 3),
       (4, '2024-11-25', 99.56, 10, 2, 2),
       (5, '2024-11-25', 8.30, 0, 3, 1),
       (6, '2024-11-26', 10, 0, 3, 4);



