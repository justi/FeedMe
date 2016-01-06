-- // create_table_message
-- Migration SQL that makes the change goes here.
CREATE TABLE MESSAGE (
	MSG_ID SERIAL PRIMARY KEY,
	MSG_ID_USR_EXPEDITEUR INTEGER NOT NULL REFERENCES UTILISATEUR(USR_ID),
	MSG_ID_USR_DESTINATAIRE INTEGER NOT NULL REFERENCES UTILISATEUR(USR_ID),
	MSG_DATE DATE NOT NULL,
	MSG_LU BOOLEAN NOT NULL DEFAULT FALSE,
	MSG_OBJET VARCHAR(128) NOT NULL,
	MSG_TEXTE VARCHAR(1024) NOT NULL
);


-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE MESSAGE

