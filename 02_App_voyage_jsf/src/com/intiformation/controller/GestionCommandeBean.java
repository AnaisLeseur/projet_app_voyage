package com.intiformation.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.intiformation.DAO.CommandeDAOImpl;
import com.intiformation.DAO.ICommandeDAO;
import com.intiformation.DAO.ILigneCommandeDAO;
import com.intiformation.DAO.IProduitDAO;
import com.intiformation.DAO.LigneCommandeDAOImpl;
import com.intiformation.DAO.ProduitDAOImpl;
import com.intiformation.modeles.Commande;
import com.intiformation.modeles.LigneCommande;
import com.intiformation.modeles.Produit;


/**
 * ManagedBean pour la gestion des commandes, utilisé pour : 
 * 		- récupérer les lignes de commandes pour toutes les commandes passées par tous les clients
 * 		- récupérer les lignes de commandes pour toutes les commandes passées par UN CLIENT 
 * 
 * @author vincent
 *
 */
@ManagedBean(name = "GestionCommandeBean")
@SessionScoped
public class GestionCommandeBean {

	// _____ Props ______//
	
	private List<Commande> listeAllCommandeBDD = new ArrayList<>();
	private List<Commande> listeAllCommandeBDD2 = new ArrayList<>();
	private List<LigneCommande> listeLigneCommandeAll = new ArrayList<>();
	private List<LigneCommande> listeLigneCommandeParCommande = new ArrayList<>();
	
	
	private List<Commande> listeCommandesCreaAffichage = new ArrayList<>();
	private List<LigneCommande> listeLignesCommandeCreaAffichage = new ArrayList<>();
	private List<Produit> listeProduitCommandeCreaAffichage = new ArrayList<>();
	

	ICommandeDAO commandeDAO;
	ILigneCommandeDAO lignecommandeDAO;
	IProduitDAO produitDAO;

	int idClient;

	
	// _____ Ctor ______//

	public GestionCommandeBean() {
		commandeDAO = new CommandeDAOImpl();
		lignecommandeDAO = new LigneCommandeDAOImpl();
		produitDAO = new ProduitDAOImpl();
	}// end ctor vide

	


	/* ============================================================================= */
	// ____________________ Méthodes ________________________________________________//
	/* ============================================================================= */
	
	
	/**
	 * methode pour récupérer la liste de toutes les commandes de la bdd
	 * 
	 * @return : la liste de toutes les commandes 
	 */
	public List<Commande> findAllCommandeBDD() {

		listeAllCommandeBDD = commandeDAO.getAll();

		return listeAllCommandeBDD;

	}// end findAllCommandeBDD
	
	
	
	/* ============================================================================= */

	
	/**
	 * methode pour récupérer la liste de toutes les lignes de commande pour CHAQUE commande de la bdd
	 * methode utilisée dans 'accueil-admin-commande' afin d'afficher toutes les commande effectuées par les clients 
	 * @return la liste des lignes de commande pour toutes les commandes
	 */
	public List<LigneCommande> findAllLigneCommandePourToutesCommandes() {

		// la liste des lignes de commande est remise à zéro
		listeLigneCommandeParCommande.clear();

		// récupération de toutes les commandes de la bdd dans une liste 
		listeAllCommandeBDD2 = commandeDAO.getAll();
		
		// taille de la liste de commande
		int taillelisteAllCommandeBDD = listeAllCommandeBDD2.size();
		int compteur = 0; 

		// pour chaque commande de la liste :
		for (Commande commande : listeAllCommandeBDD2) {
			
			if (compteur < taillelisteAllCommandeBDD) {
			
				// récupération de l'id de la commande 
				int idCommande = commande.getId_commande();
				
				// on récupère la liste des lignes de commande pour la commande en cours
				listeLigneCommandeAll = lignecommandeDAO.getByIdCommande(idCommande);

				// ajout des lignes de commande à la liste de toutes les lignes de commande
				listeLigneCommandeParCommande.addAll(listeLigneCommandeAll);
				
				continue; 
			}//end if
			
			// on incrémente le compteur pour passer à la commande suivante
			compteur ++; 

		} // end for
		
		return listeLigneCommandeParCommande;

	}// end findAllLigneCommandePourToutesCommandes
	
	

	
	/* ============================================================================= */

	/**
	 * methode pour récupérer la liste de toutes les lignes de commande pour CHAQUE commande D'UN CLIENT
	 * methode utilisée dans 'accueil-admin-client/affichage des commandes du client' afin d'afficher toutes les commande effectuées par ce client 
	 * @return la liste des lignes de commande pour toutes les commandes du client 
	 * @param event 
	 */
	public void findAllCommandeDuClient(ActionEvent event) {

		// récupération de l'id du client dont on veut récupérer les commandes et les lignes de commandes
		UIParameter uip = (UIParameter) event.getComponent().findComponent("clientID");
		idClient = (int) uip.getValue();
		
		// récupération de la session 
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);

		// on vide et on récupère la liste des commandes du client (via la DAO)
		listeCommandesCreaAffichage.clear();
		listeCommandesCreaAffichage = commandeDAO.findCommandePourCreaAffichage(idClient);
		

		// on vide et on récupère la liste des lignes de commande du client (via la DAO)
		listeLignesCommandeCreaAffichage.clear();
		listeLignesCommandeCreaAffichage = lignecommandeDAO.findCommandePourCreaAffichage(idClient);
		

		// on vide et on récupère la liste des produits dans le panier du client (via la DAO)
		listeProduitCommandeCreaAffichage.clear();
		listeProduitCommandeCreaAffichage = produitDAO.findCommandePourCreaAffichage(idClient);
		
	}// end findAllCommandeDuClient

	
	
	/* ============================================================================= */
	
	/**
	 * methode pour retourner la liste de toutes les commandes D'UN CLIENT afin de les afficher.
	 * methode utilisée dans 'accueil-admin-client/affichage des commandes du client' pour d'afficher toutes les commandes effectuées par ce client 
	 * @return la liste des commandes du client 
	 */
	public List<Commande> AfficherCommande() {

		return listeCommandesCreaAffichage;

	}// end AfficherCommande
	
	
	
	/* ============================================================================= */
	
	/**
	 * methode pour retourner la liste de toutes les lignes de commande des commandes D'UN CLIENT afin de les afficher.
	 * methode utilisée dans 'accueil-admin-client/affichage des commandes du client' pour d'afficher les infos des commandes effectuées par ce client 
	 * @return la liste des lignes de commande du client 
	 */
	public List<LigneCommande> AfficheLigneCommande() {

		return listeLignesCommandeCreaAffichage;

	}// end AfficheLigneCommande
	
	
	
	/* ============================================================================= */
	
	/**
	 * methode pour retourner la liste de tous les produits des commandes D'UN CLIENT afin de les afficher.
	 * methode utilisée dans 'accueil-admin-client/affichage des commandes du client' pour d'afficher les infos des commandes effectuées par ce client 
	 * @return la liste des produits achetés par le client 
	 */
	public List<Produit> AfficheProduit() {

		return listeProduitCommandeCreaAffichage;

	}// end AfficheProduit
	

}// end GestionCommandeBean
