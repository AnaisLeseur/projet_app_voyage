package com.intiformation.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.intiformation.DAO.AdministrateurDAOImpl;
import com.intiformation.DAO.ClientDAOImpl;
import com.intiformation.DAO.IAdministrateurDAO;
import com.intiformation.DAO.IClientDAO;
import com.intiformation.DAO.IProduitDAO;
import com.intiformation.DAO.ProduitDAOImpl;
import com.intiformation.modeles.Administrateur;
import com.intiformation.modeles.Client;
import com.intiformation.modeles.Produit;

/**
 * MB pour la gestion de l'authentification de l'administrateur ou du client
 * Permet de:
 * 		- commecter un client 
 * 		- connecter un administrateur 
 * 		- déconnecter un utilisateur (client ou admin)
 * 		- voir les infos du client 
 * 		- vérif de la connexion pour procéder au paiement 
 * 
 * @author vincent
 *
 */

@ManagedBean(name="AuthentificationAdminBean")
@SessionScoped
public class AuthentificationAdminBean implements Serializable {
	
	
	//  ----- Props ----
	
	private String adminIdentifiant;
	private String adminMotDePasse;
	private String clientIdentifiant;
	private String clientMotDePasse;

	private List<Produit> listePanier;
	
	private Administrateur administrateur;
	private Client client;
	
	// déclaration de la DAO
	private IAdministrateurDAO administrateurDAO;
	private IClientDAO clientDAO;
	
	IProduitDAO produitDAO;

	
	//  ----- Ctors ---- 
	
	// ctor vide pour l'instanciation du MB 
	public AuthentificationAdminBean() {
		administrateurDAO = new AdministrateurDAOImpl();
		clientDAO = new ClientDAOImpl();
		produitDAO = new ProduitDAOImpl();			
	}// end ctor 
	
	
	
	/* ============================================================================= */
	// ____________________ Méthodes ________________________________________________//
	/* ============================================================================= */
	
	
	/**
	 * methode qui permet de faire connecter l'admin à son espace et lui créer une session http
	 * la methode sera invoquée à la soumission du formulaire d'authentification admin
	 * 
	 * @return la page d'accueil-admin
	 */
	public String connecterAdministrateur() {
		
		// 1. déclaration du context de JSF (l'objet FacesContext)
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		// 2.vérif si admin existe dans la bdd
		if (administrateurDAO.isAdministrateurExists(adminIdentifiant, adminMotDePasse)) {
			
			// si vrai => admin existe dans bdd
			// -> création de la session
			HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(true);
			
			// -> sauvegarde du login de l'utilisateur dans la session
			session.setAttribute("user_login", adminIdentifiant);
			
			// -> navigation vers la page "accueil-admin.xhtml"
			return "accueil-admin.xhtml?faces-redirect=true";
			
			
		}else {
			// l'admin n'existe pas la bdd
			/**
			 * envoi d'un msg vers la vue via l'objet 'FacesMassage'
			 * et l'objet 'FacesContext'
			 */
			
			// definition du msg à envoyer via un objet de type 'FacesMassage'
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec de connexion :", " Identifiant ou MdP invalide ");
			
			// envoi du msg vers la vue via le context de JSF (l'objet 'FacesContext') et sa methode addMessage
			contextJSF.addMessage(null, message);
			
			// -> navigation vers la page du formulaire "authentification.xhtml"
			return "authentification.xhtml?faces-redirect=true";
			
			
		}// end else
	}// end connecterAdministrateur
	
	
	

	/* ============================================================================= */
	
	
	/**
	 * meth qui permet de faire deconnecter l'administrateur ou le client de son espace et de détruire la session
	 * la methode sera invoquée au click sur se deconnecter (dans le header de toutes les pages)
	 * 
	 * @return page d'accueil-client du site
	 */
	public String deconnecterUser() {
		
		// 1. récup du contexte de JSF 
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		// 2. récup de la session http de l'admin 
		HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);
		
		// récup des produits sélectionné 
		listePanier = produitDAO.getProduitSelectionnes(true);

		// remise à false de 'SelectionProduit' => le produit ne sera plus selectionné dans le panier
		for (Produit produit : listePanier) {
			produit.setSelectionProduit(false);	
			produitDAO.update(produit);	
		}// end for
		
		// 3. deconnexion => suppression de la session
		session.invalidate();
		
		// 4. message de deconnexion vers la vue 
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Deconnexion", " - vous êtes maintenant déconnecté");
		System.out.println("FacesMessage message =" + message);
		contextJSF.addMessage(null, message);
		
		// 5. redirection vers la page 'accueil-client.xhtml'
		return "accueil-client.xhtml?faces-redirect=true";

	}// end deconnecterUser()
	
	
	
	/* ============================================================================= */
	
	/**
	 * methode qui permet de faire connecter le client à son espace personnel
	 * la methode sera invoquée à la soumission du formulaire d'authentification client
	 * 
	 * - mise en place de la session si elle n'existe pas deja 
	 * - récupération des données du client 
	 * 
	 * @return la page d'accueil-client
	 */
	public String connecterClient() {
		
		// 1. déclaration du context de JSF (l'objet FacesContext)
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		// 2.vérif si le client existe dans la bdd 
		if (clientDAO.isClientExists(clientIdentifiant, clientMotDePasse)) {
			
			// si vrai => client existe dans bdd
			// -> création ou récupération de la session
			HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);
			
			// si session n'existe pas => création de session
			if (session == null) {
				session = (HttpSession) contextJSF.getExternalContext().getSession(true);
			}// end if
			
			// -> sauvegarde du login du client dans la session
			session.setAttribute("user_login", clientIdentifiant);

			// récupération des infos du client dans la bdd via son id et son mdp
            client = clientDAO.getByIdAndMdp(clientIdentifiant, clientMotDePasse);
            
            // sauvegarde des infos du client via setAttribute
            session.setAttribute("client", client);
			
			// -> navigation vers la page "accueil-client.xhtml"
			return "accueil-client.xhtml?faces-redirect=true";
			
			
		}else {
			// le client n'existe pas la bdd
			
			// definition du msg à envoyer via un objet de type 'FacesMassage'
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec de connexion :", " Identifiant ou MdP invalide ");
			
			// envoi du msg vers la vue via le context de JSF (l'objet 'FacesContext') et sa methode addMessage
			contextJSF.addMessage(null, message);
			
			// -> navigation vers la page du formulaire "authentification.xhtml"
			return "authentication.xhtml?faces-redirect=true";

		}// end else
	}// end connecterClient
	
	
	/* ============================================================================= */
	
	/**
	 * methode qui permet de vérifier que client est connecté à son espace AVANT de payer sa commande
	 * la methode sera invoquée au click sur 'procéder au paiment' dans panier.xhtml
	 * 
	 * @return la page d'authentification client
	 * OU 
	 * @return la page avec la commande 
	 */
	public String Paiement() {
		
		// 1. déclaration du context de JSF (l'objet FacesContext)
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		// -> récupération de la session
		HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);
		
		// 2.vérif si le client est connecté
		if (session.getAttribute("user_login") == null) {

			// redirection vers la page d'autentification client
			return "authentication.xhtml?faces-redirect=true";
			
		} else {

			// redirection vers la page pour valider la commande
			return "proceder-paiement.xhtml?faces-redirect=true";

		}// end else
	}// end Paiement
	
	
	
	/* ============================================================================= */
	
	/**
	 * methode qui permet de vérifier que le client est connecté à son espace AVANT d'afficher ses infos
	 * la methode sera invoquée au click sur 'Mon Compte' dans le header
	 * 
	 * @return la page d'authentification client
	 * OU 
	 * @return la page compte-client.xhtml 
	 */
	public String VoirLesInfos() {
		
		// 1. déclaration du context de JSF (l'objet FacesContext)
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		// -> création de la session
		HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);
		
		
		// 2.vérif si le client est connecté
		if (session.getAttribute("user_login") == null) {
			
			// redirection vers la page d'autentification client
			return "authentication.xhtml?faces-redirect=true";
			
		} else {

			// redirection vers la page 'compte-client.xhtml' avec l'affichage des infos du client
			return "compte-client.xhtml?faces-redirect=true";
			
		}// end else
	}// end VoirLesInfos
	


	
	/* ============================================================================= */
	/* ============================================================================= */
	
	
	//  ----- Getters/setters ----
	public String getAdminIdentifiant() {
		return adminIdentifiant;
	}



	public void setAdminIdentifiant(String adminIdentifiant) {
		this.adminIdentifiant = adminIdentifiant;
	}



	public String getAdminMotDePasse() {
		return adminMotDePasse;
	}



	public void setAdminMotDePasse(String adminMotDePasse) {
		this.adminMotDePasse = adminMotDePasse;
	}



	public Administrateur getAdministrateur() {
		return administrateur;
	}



	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}



	public String getClientIdentifiant() {
		return clientIdentifiant;
	}



	public void setClientIdentifiant(String clientIdentifiant) {
		this.clientIdentifiant = clientIdentifiant;
	}



	public String getClientMotDePasse() {
		return clientMotDePasse;
	}



	public void setClientMotDePasse(String clientMotDePasse) {
		this.clientMotDePasse = clientMotDePasse;
	}



	public Client getClient() {
		return client;
	}



	public void setClient(Client client) {
		this.client = client;
	}


}// end class AuthentificationAdminBean
