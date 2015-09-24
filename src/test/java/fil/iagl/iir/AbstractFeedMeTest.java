package fil.iagl.iir;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import fil.iagl.iir.dao.authentification.AuthentificationDao;
import fil.iagl.iir.entite.Adresse;
import fil.iagl.iir.entite.Authentification;
import fil.iagl.iir.entite.Offre;
import fil.iagl.iir.entite.Pays;
import fil.iagl.iir.entite.Reservation;
import fil.iagl.iir.entite.Role;
import fil.iagl.iir.entite.TypeCuisine;
import fil.iagl.iir.entite.Utilisateur;
import fil.iagl.iir.entite.Ville;
import fil.iagl.iir.outils.FeedMeAuthentificationToken;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FeedMeConfiguration.class)
@WebAppConfiguration
@Transactional
@ActiveProfiles("test")
public abstract class AbstractFeedMeTest {

	protected static final int RANDOM_STRING_SIZE = 60;

	protected static final String USERNAME_TEST_USER = "toto.toto@gmail.com";

	private static Boolean hasBeenReset = Boolean.FALSE;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private AuthentificationDao authentificationDao;

	@Before
	public void setUp() {
		if (!hasBeenReset) {
			Resource r = new ClassPathResource("test_script.sql");
			ScriptRunner runner = null;
			try {
				File reset = r.getFile();
				runner = new ScriptRunner(dataSource.getConnection());
				runner.runScript(new FileReader(reset));
			} catch (IOException | SQLException e) {
				throw new RuntimeException(e);
			} finally {
				runner.closeConnection();
				hasBeenReset = Boolean.TRUE;
			}
		}

		this.fausseConnection(USERNAME_TEST_USER, Optional.empty());
	}

	private void fausseConnection(String username, Optional<Role> role) {
		Authentification auth = authentificationDao.getByUsername(username);
		if (role.isPresent()) {
			auth.setRole(role.get());
		}
		FeedMeAuthentificationToken authToken = new FeedMeAuthentificationToken(auth);
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

	protected void changerRole(Role role) {
		fausseConnection(USERNAME_TEST_USER, Optional.of(role));
	}

	protected Utilisateur createUtilisateur() {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setIdUtilisateur(1);
		utilisateur.setMail("toto.toto@gmail.com");
		utilisateur.setNom("toto");
		return utilisateur;
	}

	protected Reservation createReservation() {
		Integer idOffre = 1;

		Utilisateur convive = new Utilisateur();

		Offre offre = new Offre();
		offre.setId(idOffre);

		LocalDate dateReservation = LocalDate.now();

		Reservation reservation = new Reservation();
		reservation.setConvive(convive);
		reservation.setDateReservation(dateReservation);
		reservation.setOffre(offre);

		return reservation;
	}

	protected Offre createOffre() {
		String titre = "MonTitre";
		Integer prix = 999;
		Integer nombrePersonne = 5;
		Integer dureeMinute = 120;
		LocalDateTime dateRepas = LocalDateTime.of(2015, 2, 1, 19, 45, 00);
		String menu = "DescriptionDuMenu";
		Boolean animaux = Boolean.FALSE;

		Integer idAdresse = 1;
		Integer idVille = 1;
		Integer idPays = 1;

		Integer idTypeCuisine = 3;

		Integer idUtilisateur = 1;

		Pays pays = new Pays();
		pays.setId(idPays);

		Ville ville = new Ville();
		ville.setId(idVille);
		ville.setPays(pays);

		Adresse adresse = new Adresse();
		adresse.setId(idAdresse);
		adresse.setVille(ville);

		TypeCuisine typeCuisine = new TypeCuisine();
		typeCuisine.setId(idTypeCuisine);

		Utilisateur hote = new Utilisateur();
		hote.setIdUtilisateur(idUtilisateur);

		Offre offre = new Offre();

		offre.setTitre(titre);
		offre.setPrix(prix);
		offre.setNombrePersonne(nombrePersonne);
		offre.setDureeMinute(dureeMinute);
		offre.setDateRepas(dateRepas);
		offre.setMenu(menu);
		offre.setAnimaux(animaux);
		offre.setAdresse(adresse);
		offre.setTypeCuisine(typeCuisine);
		offre.setHote(hote);

		return offre;
	}

	protected Authentification createAuthentification() {
		Integer idUtilisateur = 2;
		String mail = "foo.bar@gmail.com";
		String nom = "foo";

		String password = RandomStringUtils.random(RANDOM_STRING_SIZE);
		Role role = Role.PARTICULIER;

		Utilisateur utilisateur = new Utilisateur();

		utilisateur.setIdUtilisateur(idUtilisateur);
		utilisateur.setMail(mail);
		utilisateur.setNom(nom);

		Authentification authentification = new Authentification();
		authentification.setUtilisateur(utilisateur);
		authentification.setPassword(password);
		authentification.setRole(role);

		return authentification;
	}

}
