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
INSERT INTO mission_note_de_frais.credentials (id, created_at, password, updated_at, user_id)
VALUES  (5, '2024-11-25 14:25:47.000000', '$2y$10$waPVWJNiZ9pjJ.V0kokCBuuho82lOBm/b9b9mWYhYsyVWFa5MGXjO', '2024-11-25 14:25:50.000000', 948162),
        (6, '2024-11-25 14:25:52.000000', '$2y$10$MUmM.ghfpAE.pJ4x9U3vQ.OziNG809Gw/Q6PLSSjvKRC5GppZtT56', '2024-11-25 14:25:54.000000', 492816),
        (7, '2024-11-25 14:25:55.000000', '$2y$10$hYKlgYYgGSWtqld2jmsnGO7DXX3nn9qtqtiNP8c8GGX4X.AMk0Nxi', '2024-11-25 14:25:56.000000', 627481),
        (8, '2024-11-25 14:25:58.000000', '$2y$10$JFqltMC4hZdb9aBQGqTBruYcIYEqD9gE29GLjsNFaJ7lsBPYXZ8d6', '2024-11-25 14:25:59.000000', 385209);

# Insert mission status data
INSERT INTO status (id, name, description)
VALUES (1, 'INITIALE', 'Une mission qui vient d''être créée'),
       (2, 'EN_ATTENTE_VALIDATION', 'Une mission en attends de validation par le manager'),
       (3, 'VALIDEE', 'Le manager a approuvé la mission'),
       (4, 'REJETEE', 'Le manager a refusé la mission'),
       (5, 'ANNULER', 'Une mission annulée par un(e) collaborateur(trice)');

# Insert user role data
INSERT INTO user_role (user_id, role)
VALUES (948162, 'COLLABORATOR'),
       (492816, 'COLLABORATOR'),
       (627481, 'MANAGER'),
       (385209, 'MANAGER'),
       (627481, 'ADMINISTRATOR');

# Insert expense report data
INSERT INTO expense_report (id)
VALUES (1),
       (2),
       (3);

# Insert expense type data
INSERT INTO expense_type (id, name)
VALUES (1, 'Transport'),
       (2, 'Hotel'),
       (3, 'Repas'),
       (4, 'Divers');

# Insert expense data
INSERT INTO expense (id, date, amount, tax, expense_report_id, type_id)
VALUES (1, '2024-11-23', 121.35, 20, 1, 2),
       (2, '2024-11-23', 2.5, 10, 1, 1),
       (3, '2024-11-24', 15.61, 20, 2, 3),
       (4, '2024-11-25', 99.56, 10, 2, 2),
       (5, '2024-11-25', 8.30, 0, 3, 1),
       (6, '2024-11-26', 10, 0, 3, 4);

