package fil.iagl.iir.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import fil.iagl.iir.entite.Message;
import fil.iagl.iir.outils.FeedMeException;

public class MessageServiceTest extends AbstractServiceTest {

  @Mock
  private Message message;

  @Test
  public void sauvegarderTestSucces() throws Exception {
    // Etant donné un message remplis
    Mockito.when(message.getTexte()).thenReturn("Mon texte");
    Mockito.when(message.getObjet()).thenReturn("Mon object");

    // Lorsque j'appelle le service
    this.messageService.sauvegarder(message);

    // On verifie que la DAO associé est bien appelé
    Mockito.verify(messageDao, Mockito.times(1)).sauvegarder(message);
  }

  @Test(expected = FeedMeException.class)
  public void sauvegarderTestEchec_MessageNull() throws Exception {
    // Lorsque j'appelle le service avec une valeur "null"
    this.messageService.sauvegarder(null);

    // Je verifie que la DAO n'est jamais appelée et que le service renvoi une exception
    Mockito.verify(messageDao, Mockito.never()).sauvegarder(message);
  }

  @Test(expected = FeedMeException.class)
  public void sauvegarderTestEchec_ObjetNull() throws Exception {
    // Etant donné un message contenant un texte "null"
    Mockito.when(message.getObjet()).thenReturn(null);
    Mockito.when(message.getTexte()).thenReturn("Mon texte");

    // Lorsque j'appelle le service
    this.messageService.sauvegarder(message);

    // Je verifie que la DAO n'est jamais appelée et que le service renvoi une exception
    Mockito.verify(messageDao, Mockito.never()).sauvegarder(message);
  }

  @Test(expected = FeedMeException.class)
  public void sauvegarderTestEchec_TexteNull() throws Exception {
    // Etant donné un message contenant un objet "null"
    Mockito.when(message.getTexte()).thenReturn(null);
    Mockito.when(message.getObjet()).thenReturn("Mon object");

    // Lorsque j'appelle le service
    this.messageService.sauvegarder(message);

    // Je verifie que la DAO n'est jamais appelée et que le service renvoi une exception
    Mockito.verify(messageDao, Mockito.never()).sauvegarder(message);
  }

  @Test
  public void supprimerTestSucces() throws Exception {
    // Lorsque j'appelle le service avec un id non null
    this.messageService.supprimer(Mockito.anyInt());
    // Je verifie que la DAO associé est bien appelé
    Mockito.verify(messageDao, Mockito.times(1)).supprimer(Mockito.anyInt());
  }

  @Test(expected = FeedMeException.class)
  public void supprimerTestEchec_IdNull() throws Exception {
    // Lorsque j'appelle le service avec un id null
    this.messageService.supprimer(null);
    // Je verifie que la DAO associé n'est jamais appelé
    Mockito.verify(messageDao, Mockito.never()).supprimer(null);
  }

  @Test
  public void getAllNonLuParIdTestSucces() throws Exception {
    // Lorsque j'appelle le service avec un id non null
    this.messageService.getAllNonLuParId(Mockito.anyInt());
    // Je verifie que la DAO associé est bien appelé
    Mockito.verify(messageDao, Mockito.times(1)).getAllNonLuParId(Mockito.anyInt());
  }

  @Test
  public void getAllTestSucces() throws Exception {
    // Lorsque j'appelle le service avec un id non null
    this.messageService.getAll(Mockito.anyInt());
    // Je vérifie que la DAO associée est bien appelée
    Mockito.verify(messageDao, Mockito.times(1)).getAll(Mockito.anyInt());
  }
  
  @Test
  public void marquerCommeLuTestSucces() throws Exception {
	  // Lorsque j'appelle le service avec un id non null
	  messageService.marquerCommeLu(Mockito.anyInt());
	  
	  // Je vérifie que la DAO associée est bien appelée
	  Mockito.verify(messageDao,Mockito.times(1)).marquerCommeLu(Mockito.anyInt());
  }

  @Test
  public void marquerCommeLuTestEchecIdMsgNull() throws Exception {
	  Integer idMsg = null;
	  
	  // Lorsque j'appelle le service avec un id null
	  try {
		  // Je vérifie que j'ai bien une erreur retournée
		  messageService.marquerCommeLu(idMsg);
		  fail("Exception attendue");
	  } catch (FeedMeException fme) {
		  // OK
	  }
	  // Et que la DAO n'a jamais été appelée
	  Mockito.verify(messageDao,Mockito.never()).marquerCommeLu(idMsg);
  }
}
