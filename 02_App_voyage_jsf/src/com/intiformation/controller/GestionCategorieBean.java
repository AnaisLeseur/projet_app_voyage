package com.intiformation.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.intiformation.DAO.CategorieDAOImpl;
import com.intiformation.DAO.ICategorieDAO;
import com.intiformation.DAO.ProduitDAOImpl;
import com.intiformation.modeles.Categorie;

@ManagedBean(name="GestionCategorieBean")
@SessionScoped
public class GestionCategorieBean implements Serializable {
	
	
	// _____ Props ______//
	
	private List<Categorie> listeCategories; 
	private Categorie categorie; 
	
	ICategorieDAO categorieDAO;
	
	// _____ Ctor ______//
	
	public GestionCategorieBean() {
		
		categorieDAO = new CategorieDAOImpl();
		
	}//end ctor
	
	// _____ Méthodes ______//
	
	public List<Categorie> findAllCategoriesBDD(){
		
		listeCategories = categorieDAO.getAll();
		
		return listeCategories; 
		
	}//end findAllCategoriesBDD
	
	
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
		int idCategorie = (int) uip.getValue();

		// 3. suppression du produit dans la bdd via id

		// 3.1 récup du context de JSF
		FacesContext contextJSF = FacesContext.getCurrentInstance();

		// 3.2 suppression du livre
		if (categorieDAO.delete(idCategorie)) {

			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Suppression du produit",
					"- Le produit a été surpprimé avec succès"));

		} else {
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					" la suppression du produit à échouée", " - le produit n'a pas été supprimé"));

		} // end else
	}// end supprimerProduit
	
	

}//end class
