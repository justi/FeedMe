package fil.iagl.iir.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fil.iagl.iir.dao.image.ImageDao;
import fil.iagl.iir.dao.particulier.ParticulierDao;
import fil.iagl.iir.dao.utilisateur.UtilisateurDao;
import fil.iagl.iir.entite.Particulier;
import fil.iagl.iir.entite.Utilisateur;
import fil.iagl.iir.outils.FeedMeException;
import fil.iagl.iir.service.UtilisateurService;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

  @Autowired
  private UtilisateurDao utilisateurDao;

  @Autowired
  private ParticulierDao particulierDao;

  @Autowired
  private ImageDao imageDao;

  /*
   * (non-Javadoc)
   * 
   * @see fil.iagl.iir.service.UtilisateurService#getById(java.lang.Integer)
   */
  @Override
  public Utilisateur getById(Integer id) {
    if (id == null) {
      throw new FeedMeException("Parametre null");
    }
    return utilisateurDao.getById(id);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fil.iagl.iir.service.UtilisateurService#getParticulierByUtilisisateurId(java.lang.Integer)
   */
  @Override
  public Particulier getParticulierByUtilisisateurId(Integer id) {
    if (id == null) {
      throw new FeedMeException("parametre null");
    }
    return particulierDao.getParticulierByUtilisateurId(id);
  }

  @Override
  public void modifierProfil(Particulier particulier) {
    if (particulier == null) {
      throw new FeedMeException("Parametre Particulier nul");
    }
    if (particulier.getImage() != null)
      imageDao.sauvegarder(particulier.getImage());
    particulierDao.modifier(particulier);
  }

  @Override
  public List<Particulier> getAllPremium() {
    return this.particulierDao.getAllPremium();
  }

}
