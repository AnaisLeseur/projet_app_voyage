package com.intiformation.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.intiformation.DAO.ClientDAOImpl;
import com.intiformation.DAO.IClientDAO;
import com.intiformation.modeles.Client;

/**
 * <pre>
 * ManagedBean pour la gestion des clients tels que: 
 * Ajout, suppression et modification de client
 * Affichage de la liste des clients contenues dans la database
 * Récupération d'un client à partir de son id
 * </pre>
 * 
 * @author hannahlevardon
 *
 */

@ManagedBean(name = "GestionClientBean")
@SessionScoped
public class GestionClientBean implements Serializable {

	// _____________________ Propriétés ______________________ //
	
	private List<Client> listeClientsDAO;
	private Client client;

	// Déclaration de la couche DAO 
	private IClientDAO clientDAO;

	// _____________________ Constructeurs ______________________ //
	
	public GestionClientBean() {
		clientDAO = new ClientDAOImpl();
	}// end ctor

	// _____________________ Getter / Setter ______________________ //
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	// _____________________ Méthodes ______________________ //
	

	/**
	 * Méthode pour récupérer la liste des clients dans la database 
	 * Fait appel à la méthode getAll() de ClientDAOImpl
	 * Invoquée dans la <h:dataTable> de la page 'accueil-admin-client.xhtml'
	 * 
	 * @return listeClientsDAO : liste de tous les clients contenus dans la database
	 */
	public List<Client> findAllClientsBdd() {
		listeClientsDAO = clientDAO.getAll();
		return listeClientsDAO;
	}// end findAllClientsBdd

	
	/**
	 * Méthode pour supprimer un client dans la databse . 
	 * Fait appel à la méthode delete() de ClientDAOImpl()
	 * Invoquée au clic sur le <h:commandLink><h:outputText value="Supprimer ce client" /></h:commandLink>
	 * dans la page 'accueil-admin-client.xhtml'
	 * 
	 */
	public void supprimerClient(ActionEvent event) {

		// 1. récupération du paramètre passé dans le composant
		UIParameter uip = (UIParameter) event.getComponent().findComponent("deleteId");

		// 2. récup de la valeur du paramètre
		int clientId = (int) uip.getValue();

		// 3. suppression du client de la database

		// 3.1 récupération du context de JSF
		FacesContext contextJSF = FacesContext.getCurrentInstance();

		// 3.2 suppression du client
		if (clientDAO.delete(clientId)) {

			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "suppression du client",
					" - le client a été supprimé avec succès"));

		} else {
			
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					" la suppression du client à échouée", " - le client n'a pas été supprimé"));

		} // end else
	}// end supprimerClient()

	/**
	 * Méthode pour récupérer un client dans la database
	 * Fait appel à la méthode getById() de ClientDAOImpl()
	 * Invoquée au clic sur le <h:commandButton value="Modifier mes informations" dans la page 'compte-client.xhtml'
	 * 
	 * 
	 */
	public void selectionnerClient(ActionEvent event) {

		// 1. récupération du paramètre passé au composant via <f:param>
		UIParameter uip = (UIParameter) event.getComponent().findComponent("updateId");

		// 2. récupération de la valeur du paramètre (id du client à modifier)
		int clientId = (int) uip.getValue();

		// 3. récupération du client à modifier 
		Client clientAModifier = clientDAO.getById(clientId);

		// 4. affectation du client à modifier à la variable 'client'
		setClient(clientAModifier);

	}// end selectionnerClient

	/**
	 * Méthode pour modifier un client dans la database
	 * Fait appel à la méthode update() dans ClientDAOImpl 
	 * Invoquée au clic sur le <h:commandButton value="Valider les informations"> dans la page 'modif-infos-client.xhtml'
	 * @return: vers la page 'compte-client.xhtml'
	 */
	public String modifierClient() {

		/**
		 * la propriété 'client' encapsule les information du client à modifier dans la database
		 */
	
		// 1. Récupération du contexteJSF
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);
		
		// 2. Récupération du client via l'attribut 'client' enregistré dans AuthentificationAdminBean  
		Client client = (Client) session.getAttribute("client");

		// 3. Modification du client dans la database 
		if (clientDAO.update(client)) {
			
			FacesMessage messageOk = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification réussie",
					" - Le client a été correctement modifié");
			contextJSF.addMessage(null, messageOk);

		} else {
			FacesMessage messageNotOk = new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec de la modification",
					" - La modification du client à échouée");

			contextJSF.addMessage(null, messageNotOk);

		} // end else
		
		return "compte-client.xhtml?faces-redirect=true";
	}// end modifierClient

	/**
	 * Méthode pour initialiser un nouvel objet client vide. 
	 * Invoquée au clic sur le <h:commandLink action="editClient" <h:outputText value="Nouveau client ? C'est ici pour s'incrire" >
	 * Déclare une client 'vide'
	 */
	public void initialiserClient(ActionEvent event) {

		Client clientAdd = new Client();
		setClient(clientAdd);

	}// end initialiserClient

	/**
	 * Méthode pour ajouter un nouveau client dans la database. 
	 * Fait appel à la méthode add() dans ClientDAOImpl 
	 * Invoquée au clic sur le <h:commandButton value="Enregistrer-vous"> dans la page 'inscription.xhtml'
	 */
	public void ajouterClient() {
		// 1. Récupération du context de JSF
		FacesContext contextJSF = FacesContext.getCurrentInstance();

		// 2. Ajout du nouveau client 
		boolean verifAjout = clientDAO.add(client);

		// 3. Test du résultat
		if (verifAjout) {

			contextJSF.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Ajout du client", " - L'ajout est réussi "));

		} else {

			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ajout du client",
					" - L'ajout à échoué !"));

		} // end else
	}// end ajouterClient()

}// end class GestionClientBean
