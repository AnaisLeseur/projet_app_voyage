package com.intiformation.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.intiformation.DAO.IProduitCategorie;
import com.intiformation.DAO.ProduitCategorieDAOImpl;
import com.intiformation.modeles.Categorie;
import com.intiformation.modeles.ProduitCategorie;

@ManagedBean(name = "GestionProduitCategorieBean")
@SessionScoped
public class GestionProduitCategorieBean implements Serializable {

	// _________________ Props __________________//

	private Map<Integer, Boolean> listeCategorieSelectionnes = new HashMap<Integer, Boolean>();
	private List<Categorie> Listecategorie;
	
	ProduitCategorie produitCategorie; 

	IProduitCategorie produitCategorieDAO;

	// ___________________ Ctor ____________________//

	public GestionProduitCategorieBean() {
		produitCategorieDAO = new ProduitCategorieDAOImpl();
	}// end ctor

	// _________________ Méthodes __________________//
	
	/**
	 * Méthode pour créer un lien entre le produit et la catégorie via la table d'association produit-catgorie
	 * 
	 * @param event
	 */
	public void creerLiaisonProduitCategorie(ActionEvent event) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		// Récupération de l'ID du produit envoyé via le f:param
		UIParameter uip = (UIParameter) event.getComponent().findComponent("ProduitID");
		int produitID = (int) uip.getValue();
		
		
		// Récupération des ID des catégories sous forme de liste, selectionnées via les booleancheckbox cochées en true
		List<Integer> listeIdCategorieSelectionnees = listeCategorieSelectionnes.entrySet().stream()
				.filter(Entry::getValue).map(Entry::getKey).collect(Collectors.toList());

		// Boucle for pour chaque ID de catégorie à lier au produit
		for (Integer integer : listeIdCategorieSelectionnees) {
			System.out.println(integer);
			
			// Creation d'une ligne dans la table produit-catégorie
			if(produitCategorieDAO.addVersion2(integer, produitID)) {
				
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Ajout Livre", "Ajout du livre effectué avec succès"));
			} else {
	
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"Ajout Livre", "Echec de l'Ajout du livre ..."));

			}// end else
		}//end for
	}// end creerLiaisonProduitCategorie
	

	// __________________ Getter / Setter __________________ //

	public Map<Integer, Boolean> getListeCategorieSelectionnes() {
		return listeCategorieSelectionnes;
	}

	public void setListeCategorieSelectionnes(Map<Integer, Boolean> listeCategorieSelectionnes) {
		this.listeCategorieSelectionnes = listeCategorieSelectionnes;
	}

	public List<Categorie> getListecategorie() {
		return Listecategorie;
	}

	public void setListecategorie(List<Categorie> listecategorie) {
		Listecategorie = listecategorie;
	}

}// end class
