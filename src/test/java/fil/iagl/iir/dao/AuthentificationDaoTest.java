package fil.iagl.iir.dao;

import org.apache.commons.lang3.RandomStringUtils;
import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import fil.iagl.iir.entite.Authentification;
import fil.iagl.iir.entite.Particulier;
import fil.iagl.iir.entite.Role;
import fil.iagl.iir.entite.Utilisateur;
import fil.iagl.iir.outils.SQLCODE;

public class AuthentificationDaoTest extends AbstractDaoTest {

  @Test
  public void getByUsernameTestSucces() throws Exception {
    Authentification<? extends Utilisateur> authentification = this.authentificationDao
      .getByUsername(USERNAME_TEST_USER);

    Assertions.assertThat(authentification).isNotNull();
    Assertions.assertThat(authentification.getUtilisateur().getMail()).isNotNull().isEqualTo(USERNAME_TEST_USER);
    Assertions.assertThat(authentification.getPassword()).isNotNull();
    Assertions.assertThat(authentification.getRole()).isNotNull().isEqualTo(Role.PARTICULIER);
  }

  @Test
  public void getByUsernameTestEchec() throws Exception {
    Assertions.assertThat(this.authentificationDao.getByUsername(null)).isNull();
    Assertions.assertThat(this.authentificationDao.getByUsername(RandomStringUtils.random(RANDOM_STRING_SIZE)))
      .isNull();
  }

  @Test
  public void sauvegarderTestSucces() throws Exception {

    Authentification<Particulier> authentification = this.createAuthentificationParticulier();

    this.authentificationDao.sauvegarder(authentification);

    Assertions.assertThat(this.authentificationDao.getByUsername(authentification.getUtilisateur().getMail()))
      .isNotNull();
  }

  @Test
  public void sauvegarderTestEchec_IdUtilisateurNull() throws Exception {
    Authentification<Particulier> authentification = this.createAuthentificationParticulier();
    authentification.getUtilisateur().setIdUtilisateur(null);

    try {
      this.authentificationDao.sauvegarder(authentification);
      Assertions.fail("Doit soulever une exception");
    } catch (DataIntegrityViolationException dive) {
      this.assertSQLCode(dive, SQLCODE.NOT_NULL_VIOLATION);
    }
  }

  @Test
  public void sauvegarderTestEchec_PasswordNull() throws Exception {
    Authentification<Particulier> authentification = this.createAuthentificationParticulier();
    authentification.setPassword(null);

    try {
      this.authentificationDao.sauvegarder(authentification);
      Assertions.fail("Doit soulever une exception");
    } catch (DataIntegrityViolationException dive) {
      this.assertSQLCode(dive, SQLCODE.NOT_NULL_VIOLATION);
    }
  }

  @Test
  public void sauvegarderTestEchec_RoleNull() throws Exception {
    Authentification<Particulier> authentification = this.createAuthentificationParticulier();
    authentification.setRole(null);

    try {
      this.authentificationDao.sauvegarder(authentification);
      Assertions.fail("Doit soulever une exception");
    } catch (DataIntegrityViolationException dive) {
      this.assertSQLCode(dive, SQLCODE.NOT_NULL_VIOLATION);
    }
  }

  @Test
  public void sauvegarderTestEchec_IdUtilisateurNonExistant() throws Exception {
    Authentification<Particulier> authentification = this.createAuthentificationParticulier();
    authentification.getUtilisateur().setIdUtilisateur(Integer.MAX_VALUE);

    try {
      this.authentificationDao.sauvegarder(authentification);
      Assertions.fail("Doit soulever une exception");
    } catch (DataIntegrityViolationException dive) {
      this.assertSQLCode(dive, SQLCODE.FOREIGN_KEY_VIOLATION);
    }
  }

  @Test
  public void sauvegarderTestEchec_UsernameUnique() throws Exception {
    Integer idUtilisateur = 1;
    Authentification<Particulier> authentification = this.createAuthentificationParticulier();
    authentification.getUtilisateur().setIdUtilisateur(idUtilisateur);

    try {
      this.authentificationDao.sauvegarder(authentification);
      Assertions.fail("Doit soulever une exception");
    } catch (DataIntegrityViolationException dive) {
      this.assertSQLCode(dive, SQLCODE.UNIQUE_VIOLATION);
    }
  }

}
