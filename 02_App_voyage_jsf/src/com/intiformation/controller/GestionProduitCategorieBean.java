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

	// _____ Props ______//

	private Map<Integer, Boolean> listeCategorieSelectionnes = new HashMap<Integer, Boolean>();
	private List<Categorie> Listecategorie;
	
	ProduitCategorie produitCategorie; 

	IProduitCategorie produitCategorieDAO;

	// _____ Ctor ______//

	public GestionProduitCategorieBean() {
		produitCategorieDAO = new ProduitCategorieDAOImpl();
	}// end ctor

	// _____ Méthodes ______//
	public void creerLiaisonProduitCategorie(ActionEvent event) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		UIParameter uip = (UIParameter) event.getComponent().findComponent("ProduitID");
		int produitID = (int) uip.getValue();
		System.out.println("Id du produit selectionné: "+produitID);
		
		
		List<Integer> listeIdCategorieSelectionnees = listeCategorieSelectionnes.entrySet().stream()
				.filter(Entry::getValue).map(Entry::getKey).collect(Collectors.toList());

		for (Integer integer : listeIdCategorieSelectionnees) {
			System.out.println(integer);
			
			if(produitCategorieDAO.addVersion2(integer, produitID)) {
				
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Ajout Livre", "Ajout du livre effectué avec succès"));
			} else {
	
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"Ajout Livre", "Echec de l'Ajout du livre ..."));

			}// end else

		}//end for

		

		

	}// end creerLiaisonProduitCategorie

	// _____ Getter / Setter ______//

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
