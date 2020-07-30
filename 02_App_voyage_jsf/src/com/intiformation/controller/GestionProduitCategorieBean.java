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

/**
 * <pre>
 * ManagedBean pour la gestion des liens entre produits-catégories
 * </pre>
 * 
 * @author hannahlevardon
 */
@ManagedBean(name = "GestionProduitCategorieBean")
@SessionScoped
public class GestionProduitCategorieBean implements Serializable {


	// _____________________ Propriétés ______________________ //
	
	private Map<Integer, Boolean> listeCategorieSelectionnes = new HashMap<Integer, Boolean>();
	private List<Categorie> Listecategorie;

	ProduitCategorie produitCategorie;

	IProduitCategorie produitCategorieDAO;

	// _____________________ Constructeurs ______________________ //
	
	public GestionProduitCategorieBean() {
		produitCategorieDAO = new ProduitCategorieDAOImpl();
	}// end ctor

	// _________________ Méthodes __________________//

	/**
	 * <pre>
	 * Méthode pour créer un lien entre le produit et la catégorie via la table d'association produit-catgorie
	 * Fait appel à la méthode addVersion2() de ProduitCategorieDAOImpl
	 * Invoquée au clic sur <h:commandButton value=
	"Lier ce voyage aux catégories selectionnées"> dans la page 'set-categorie.xhtml'
	 * </pre>
	 * 
	 * @param event
	 */
	public void creerLiaisonProduitCategorie(ActionEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();

		// 1. Récupération de l'ID du produit envoyé via le f:param
		UIParameter uip = (UIParameter) event.getComponent().findComponent("ProduitID");
		int produitID = (int) uip.getValue();

		// 2. Récupération des ID des catégories sous forme de liste, selectionnées via les
		// booleancheckbox cochées en true
		List<Integer> listeIdCategorieSelectionnees = listeCategorieSelectionnes.entrySet().stream()
				.filter(Entry::getValue).map(Entry::getKey).collect(Collectors.toList());

		// 3. Boucle for pour chaque ID de catégorie à lier au produit
		for (Integer integer : listeIdCategorieSelectionnees) {
			System.out.println(integer);

			// 4. Creation d'une ligne dans la table produit-catégorie
			if (produitCategorieDAO.addVersion2(integer, produitID)) {

				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Liaison réussie",
						"Le voyage a bien été liée aux catégories sélectionnées"));
			} else {

				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec de la liaison",
						"Le voyage n'a pas été lié aux catégories sélectionnées"));

			} // end else
		} // end for
	}// end creerLiaisonProduitCategorie

	/* _____________________________________________________ */
	/* _____________ Méthodes en developpement _____________ */
	/* _____________________________________________________ */

	// Nécessité d'ajouter des méthodes pour modifier et supprimer les liaisons

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
