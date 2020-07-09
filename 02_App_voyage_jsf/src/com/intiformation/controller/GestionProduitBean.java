package com.intiformation.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.intiformation.DAO.IProduitDAO;
import com.intiformation.DAO.ProduitDAOImpl;
import com.intiformation.modeles.Produit;

import net.bootsfaces.utils.FacesMessages;

import java.util.*;
import javax.faces.event.*;
import javax.faces.component.*;
import javax.faces.context.FacesContext;

@ManagedBean(name = "GestionProduitBean")
@SessionScoped
public class GestionProduitBean implements Serializable {

	// _____ Props ______//

	private List<Produit> listeProduits;
	private Produit produit;
	private String motCle; 

	IProduitDAO produitDAO;

	// _____ Ctor ______//

	public GestionProduitBean() {
		produitDAO = new ProduitDAOImpl();
	}// end ctor vide

	// _____ Méthodes ______//

	/**
	 * Récuperation de la liste des produits dans db via DAO Méthode utilisé par le
	 * composant <h:datatable> de accueil-client.xhtml pour afficher la liste des
	 * livres de la db
	 * 
	 * @return
	 */

	public List<Produit> findAllProduitsBDD() {

		listeProduits = produitDAO.getAll();

		return listeProduits;

	}// end findAllProduitsBDD

	public List<Produit> findProduitByMotCle(String motCle) {

		
		System.out.println("Mot clé :" + motCle);
		
		
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		listeProduits = produitDAO.getByKeyword(motCle);

		/*if (motCle != null) {

			listeProduits = produitDAO.getByKeyword(motCle);

			if (listeProduits.isEmpty()) {

				contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pas de voyage",
						": Aucun voyage correspondant à votre recherche..."));
			} else {
			
			*/
				return listeProduits;
			/*}

		} else {

			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pas de voyage",
					": vous avez pas rentré de requête"));

		} // end else
		*/
		

	}// end findProduitByMotClé

	/**
	 * Méthode pour supprimer un produit dans la db Invoquée au clic sur lien
	 * 'supprimer' dans admin-accueil au clic, l'évenement
	 * 'javax.faces.event.ActionEvent' se déclenche et encapsule toutes les infos
	 * concernant le composant
	 * 
	 * @param event
	 */
	public void supprimerProduit(ActionEvent event) {

		// 1. récup du param passé dans le composant au clic sur le lien 'supprimer'
		UIParameter uip = (UIParameter) event.getComponent().findComponent("deleteId");

		// 2. récup de la valeur du param
		int idProduit = (int) uip.getValue();

		// 3. suppression du produit dans la bdd via id

		// 3.1 récup du context de JSF
		FacesContext contextJSF = FacesContext.getCurrentInstance();

		// 3.2 suppression du livre
		if (produitDAO.delete(idProduit)) {

			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Suppression du produit",
					"- Le produit a été surpprimé avec succès"));

		} else {
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					" la suppression du produit à échouée", " - le produit n'a pas été supprimé"));

		} // end else
	}// end supprimerProduit

	// _____ Getter /setter ______//
	public List<Produit> getListeProduits() {
		return listeProduits;
	}

	public void setListeProduits(List<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public String getMotCle() {
		return motCle;
	}

	public void setMotCle(String motCle) {
		this.motCle = motCle;
	}
	

}// end classe
