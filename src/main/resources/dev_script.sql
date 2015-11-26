-- ---------------------------
-- Table VILLE
-- ---------------------------
INSERT INTO VILLE(VIL_NOM, VIL_CP, VIL_PAY_ID) VALUES
('Lille','59650',1);

INSERT INTO VILLE(VIL_NOM, VIL_CP, VIL_PAY_ID) VALUES
('Reims','51100',1);

-- ---------------------------
-- Table ADRESSE
-- ---------------------------
INSERT INTO ADRESSE(adr_voie, adr_vil_id) VALUES
('45 Rue des sacres',1);
INSERT INTO ADRESSE(adr_voie, adr_vil_id) VALUES
('21 Avenue des champs',2);

-- ---------------------------
-- Table UTILISATEUR
-- ---------------------------
INSERT INTO UTILISATEUR ( usr_nom, usr_mail, usr_premium, usr_description, usr_adresse_visible) VALUES 
( 'Me', 'feed.me@univ-lille1.fr', true, 'La cuisine est ma passion !', true);

-- ---------------------------
-- Table PARTICULIER
-- ---------------------------
INSERT INTO PARTICULIER ( prt_prenom, prt_date_naissance, prt_usr_id) VALUES 
( 'Feed', '2015-01-31', 1); -- ID 1

-- ---------------------------
-- Table AUTHENTIFICATION
-- ---------------------------
INSERT INTO AUTHENTIFICATION ( aut_usr_id, aut_password, aut_rol_id ) VALUES
(1, '$2a$10$L00YE3VqyxIvn43zvWbxkut6WNqKAp0WA0XRZGMriRzIRV04qxkqi', 1); -- MDP : feedme

-- ---------------------------
-- Table OFFRE
-- ---------------------------
INSERT INTO OFFRE(OFF_DATE_CREATION,OFF_TITRE,OFF_PRIX, OFF_NOMBRE_PERSONNE,OFF_DUREE_MINUTE, OFF_DATE_REPAS, OFF_NOTE, OFF_AGE_MIN, OFF_AGE_MAX, OFF_ANIMAUX, OFF_ADR_ID, OFF_TYP_ID, OFF_USR_ID,OFF_PLAT,OFF_DESSERT,OFF_ENTREE,OFF_BOISSON, off_premium) VALUES
('2012-10-13','Tomates nordique',12,5,123,'2012-10-19 03:14:07','Des tomates délicieuses',10,45,'true',1,1,1,'Tomates Farcies, riz à la crème fraiche et au vin blanc.','Poires belle Hélène','Salade composée.','', true);

INSERT INTO OFFRE(OFF_DATE_CREATION,OFF_TITRE,OFF_PRIX, OFF_NOMBRE_PERSONNE,OFF_DUREE_MINUTE, OFF_DATE_REPAS, OFF_NOTE, OFF_AGE_MIN, OFF_AGE_MAX, OFF_ANIMAUX, OFF_ADR_ID, OFF_TYP_ID, OFF_USR_ID,OFF_PLAT,OFF_DESSERT,OFF_ENTREE,OFF_BOISSON, off_premium) VALUES
('2012-10-13','Poulet subsaharien',19,4,168,'2012-10-19 03:14:07','Attention les arachides sont un allergène',18,29,'true',2,1,1,'Poulet aux arachides, riz, carottes, piment.','Kakis et autres fruits exotiques','Buffet froid','', true);
