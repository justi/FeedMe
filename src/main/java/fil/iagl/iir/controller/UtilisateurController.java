package fil.iagl.iir.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fil.iagl.iir.entite.AuthentificationParticulier;
import fil.iagl.iir.entite.Particulier;
import fil.iagl.iir.entite.Utilisateur;
import fil.iagl.iir.service.AuthentificationService;
import fil.iagl.iir.service.UtilisateurService;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {

	@Autowired
	private UtilisateurService utilisateurService;

	@Autowired
	private AuthentificationService authentificationService;

	@RequestMapping(value = "/particulier/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Utilisateur afficherProfil(@PathVariable("id") Integer id) {
		return utilisateurService.getById(id);
	}

	@RequestMapping(value = "/particulier", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public Particulier inscription(@RequestBody AuthentificationParticulier auth) {
		authentificationService.inscription(auth);
		return auth.getUtilisateur();
	}

	/*
	 * Example de controller REST
	 *
	 */

	/*
	 * 
	 * 
	 * Coté Front ( en jQuery, trouver l'équivalent en Angular ) :
	 * 
	 * data = { "nom" : "toto", "mail" : "monemail@toto.fr" };
	 * 
	 * $.ajax({ type: "PUT", url: "/utilisateur/test", contentType:
	 * 'application/json', mimeType: 'application/json', data:
	 * JSON.stringify(data) });
	 * 
	 * 
	 * Coté Spring :
	 * 
	 * @RequestMapping(value = "/test", method = RequestMethod.PUT) public void
	 * test(@RequestBody Utilisateur utilisateur) { // Faire le traitement }
	 * 
	 */

}
