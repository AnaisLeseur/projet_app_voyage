package com.intiformation.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.intiformation.DAO.ClientDAOImpl;
import com.intiformation.DAO.IClientDAO;
import com.intiformation.modeles.Client;

/**
 * MB pour le gestion des clients
 * 
 * @author vincent
 *
 */

@ManagedBean(name = "GestionClientBean")
@SessionScoped
public class GestionClientBean implements Serializable {

	// ---- Props ----
	private List<Client> listeClientsDAO;
	private Client client;

	private IClientDAO clientDAO;

	// ---- Ctors ----
	public GestionClientBean() {
		clientDAO = new ClientDAOImpl();
	}// end ctor

	// ---- Getters/setters ----
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	// ---- Meths ----
	/**
	 * permet de récup la liste des clients dans la bdd via la dao la meth est
	 * utilisée par le composant : pour afficher la liste des clients dans la bdd
	 * 
	 * @return
	 */
	public List<Client> findAllClientsBdd() {
		listeClientsDAO = clientDAO.getAll();
		return listeClientsDAO;
	}// end findAllClientsBdd

	/**
	 * meth permet de supprimer un livre dans la bdd . meth invoquée au click sur le
	 * lien 'supprimer' de la page 'accueil.xhtml'. au click, l'évenement
	 * 'javax.faces.event.ActionEvent' se déclenche. l'évenement encapsule ttes les
	 * infos concernant le composant.
	 * 
	 */
	public void supprimerClient(ActionEvent event) {

		// 1. récup du param passé dans le composant au click sur le lien 'supprimer'
		UIParameter uip = (UIParameter) event.getComponent().findComponent("deleteId");

		// 2. récup de la valeur du param
		int clientId = (int) uip.getValue();

		// 3. suppression du livre dans la bdd via id

		// 3.1 récup du context de JSF
		FacesContext contextJSF = FacesContext.getCurrentInstance();

		// 3.2 suppression du livre
		if (clientDAO.delete(clientId)) {

			// sup OK
			// envoi d'un msg vers la vue via context de JSF
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "suppression du client",
					" - le client a été supprimé avec succès"));

		} else {
			// sup PAS OK
			// envoi d'un msg vers la vue via context de JSF
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					" la suppression du client à échouée", " - le client n'a pas été supprimé"));

		} // end else
	}// end supprimerClient()

	/**
	 * meth qui permet de récup le client dans la bdd avant sa modification. meth
	 * invoquée au click sur le lien 'modification' au click, l'évenement
	 * 'javax.faces.event.ActionEvent' se déclenche. l'évenement encapsule ttes les
	 * infos concernant le composant.
	 */
	public void selectionnerClient(ActionEvent event) {

		// 1; récup du param passé au composant au click du lien "modification"
		UIParameter uip = (UIParameter) event.getComponent().findComponent("updateId");

		// 2. récup de la valeur du param (id du client à modifier)
		int clientId = (int) uip.getValue();

		// 3. récup du client à modifier via l'id dans la bdd
		Client clientAModifier = clientDAO.getById(clientId);

		// 4. affectation du client à modifier à la variable 'client' du MB
		setClient(clientAModifier);

	}// end selectionnerClient

	/**
	 * meth permet de modifier un client dans la bdd. meth invoquée au click sur le
	 * lien 'modifier' au click, l'évenement 'javax.faces.event.ActionEvent' se
	 * déclenche. l'évenement encapsule ttes les infos concernant le composant.
	 * 
	 */
	public void modifierClient(ActionEvent event) {

		/**
		 * la prop 'client' du MB encapsule les infos du client à modifier dans la bdd
		 */

		// 1; récup du param passé au composant au click du lien "modification"
		//UIParameter clientModif = (UIParameter) event.getComponent().findComponent("clientModif");
		
		//Client client = (Client) clientModif.getValue();
		
		// 1.récup du contexteJSF
		FacesContext contextJSF = FacesContext.getCurrentInstance();

		// 2. modification du client dans la bdd
		if (clientDAO.update(client)) {
			// modification ok

			// msg vers la vue
			FacesMessage messageOk = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification réussie",
					" - Le client a été correctement modifié");

			// envoi du msg
			contextJSF.addMessage(null, messageOk);

		} else {
			// modif pas OK

			// msg vers la vue
			FacesMessage messageNotOk = new FacesMessage(FacesMessage.SEVERITY_INFO, "Echec de la modification",
					" - La modification du client à échouée");

			// envoi du msg
			contextJSF.addMessage(null, messageNotOk);

		} // end else
	}// end modifierClient

	/**
	 * meth permet d'initialiser un nouveau objet client vide. meth invoquée au
	 * click sur le lien 'ajouter' ou 's'inscrire' au click, l'évenement
	 * 'javax.faces.event.ActionEvent' se déclenche. l'évenement encapsule ttes les
	 * infos concernant le composant.
	 */
	public void initialiserClient(ActionEvent event) {

		// instanciation d'un nouvel objet de type 'Client'
		Client clientAdd = new Client();

		// affectation du clientAdd à la prop 'client' du MB
		// cet objet va receptionner les infos du client envoyées à partir du formulaire
		// d'ajout
		setClient(clientAdd);

	}// end initialiserClient

	/**
	 * meth permet d'ajouter un nouveau client dans la bdd. meth invoquée au click
	 * sur le lien 'ajouter' ou 's'inscrire'
	 */
	public void ajouterClient() {
		// 1. récup du context de JSF
		FacesContext contextJSF = FacesContext.getCurrentInstance();

		// 2. ajout du nouveau client dans la bdd via DAO
		boolean verifAjout = clientDAO.add(client);

		// 3. test résultat
		if (verifAjout) {

			// Ajout OK
			// envoi d'un msg vers la vue
			contextJSF.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Ajout du client", " - L'ajout est réussi "));

		} else {
			// Ajout PAS OK

			// envoi d'un msg vers la vue
			FacesMessage messageAddNotOk = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ajout du client",
					" - L'ajout à échoué !");

			// envoi du msg
			contextJSF.addMessage(null, messageAddNotOk);

		} // end else
	}// end ajouterClient()

}// end class GestionClientBean
