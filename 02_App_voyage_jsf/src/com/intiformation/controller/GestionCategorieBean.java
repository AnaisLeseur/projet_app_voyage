package com.intiformation.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Part;
import org.primefaces.event.*;

import com.intiformation.DAO.CategorieDAOImpl;
import com.intiformation.DAO.ICategorieDAO;
import com.intiformation.modeles.Categorie;

/**
 * <pre>
 * ManagedBean pour la gestion des catégories tels que: 
 * Ajout, suppression et modification de catégories
 * Affichage de la liste des catégories contenues dans la database
 * Récupération d'une catégorie à partir de son id
 * </pre>
 * 
 * @author hannahlevardon
 *
 */
@ManagedBean(name = "GestionCategorieBean")
@SessionScoped
public class GestionCategorieBean implements Serializable {

	// _____________________ Propriétés ______________________ //

	private List<Categorie> listeCategories;
	private Categorie categorie;

	// Permet de faire appel aux méthodes de la DAO
	ICategorieDAO categorieDAO;

	// Permet de gérer l'upload d'image pour un catégories
	private Part uploadedFile;

	FacesContext contextJSF = FacesContext.getCurrentInstance();

	// _____________________ Constructeurs ______________________ //

	/**
	 * Constructeur vide
	 */
	public GestionCategorieBean() {

		categorieDAO = new CategorieDAOImpl();

	}// end constructeur

	// _____________________ Méthodes _____________________ //

	/**
	 * <pre>
	 * Méthode pour afficher la liste de toutes les catégories de la database
	 * Fait appel à la méthode getAll() de CategorieDAOImpl
	 * Invoquée dans différentes pages xhtml de l'application, notamment dans la <h:datatable> de 'accueil-admin-categorie' 
	 * &#64;return listeCategories : liste de toutes les catégories contenues dans la database
	 * </pre>
	 */
	public List<Categorie> findAllCategoriesBDD() {

		listeCategories = categorieDAO.getAll();

		return listeCategories;

	}// end findAllCategoriesBDD

	/**
	 * <pre>
	 * Méthode pour supprimer une catégorie dans la database
	 * Fait appel à la méthode delete() de CategorieDAOImpl
	 * Invoquée dans 'accueil-admin-categorie', au clic sur le <h:commandLink> Supprimer une catégorie de la datatable
	 * </pre>
	 * 
	 * @param event
	 * 
	 */
	public void supprimerCategorie(ActionEvent event) {

		// 1. Récupération du paramètre passé par <f:param>
		UIParameter uip = (UIParameter) event.getComponent().findComponent("suppID");

		// 2. Récupération de la valeur du paramètre
		int idCategorie = (int) uip.getValue();

		// 3. Suppression de la catégorie dans la database via son id

		// 3.1 Récupération du context de JSF
		FacesContext contextJSF = FacesContext.getCurrentInstance();

		// 3.2 Suppression du livre
		if (categorieDAO.delete(idCategorie)) {

			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Suppression de la catégorie",
					"- La catégorie a été surpprimée avec succès"));

		} else {
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					" la suppression de la catégorie à échouée", " - la catégorie n'a pas été supprimée"));

		} // end else

	}// end supprimerCategorie()

	/**
	 * <pre>
	* Méthode pour initialiser une catégorie dans la database
	* Invoquée dans 'accueil-admin-categorie', au clic sur le <h:commandLink> "Ajouter une catégorie" 
	* Déclare une catégorie 'vide'
	 * </pre>
	 * 
	 * @param event
	 *
	 */
	public void initCategorie(ActionEvent event) {
		setCategorie(new Categorie());
	}// end initCategorie

	/**
	 * <pre>
	 * Méthode pour récupérer une catégorie dans la database via son Id
	 * Fait appel à la méthode getById de CategorieDAOImpl
	 * Invoquée dans plusieurs pages xhtml, lorsque des infos sur la catégorie sont requises (Id, nom, description...)
	 * </pre>
	 * 
	 * @param event
	 *
	 */
	public void recupCategorie(ActionEvent event) {

		// 1. récup du paramètre passé dans le composant 'modifId'
		UIParameter uip = (UIParameter) event.getComponent().findComponent("modifId");

		// 2. Récupération de la valeur du paramètre
		int idCategorie = (int) uip.getValue();

		// 3. Récupération de la catégorie via son Id
		Categorie categorie = categorieDAO.getById(idCategorie);

		// 4. Enregistrement des informations dans la variable 'categorie'
		setCategorie(categorie);

	}// end recupCategorie

	/**
	 * <pre>
	 * Méthode pour ajouter ou modifier une catégorie
	 * Fait appel aux méthodes add() ou update() de CategorieDAOImpl
	 * Invoquée par <h:commandButton  value=
	 "Enregistrer la catégorie"> dans la page edit-categorie.xhtml
	 * </pre>
	 * 
	 * @param event
	 * 
	 */
	public void saveCategorie(ActionEvent event) {

		FacesContext contextJSF = FacesContext.getCurrentInstance();

		// -------------------------------------------
		// cas : ajout
		// -------------------------------------------
		// Cas lorsque la méthode initCategorie() est invoquée avant : existence d'une
		// catégorie avec idCategorie = 0
		if (categorie.getId_categorie() == 0) {

			try {

				
				// 1. Ajout du chemin absolu à la propriété UrlImageCategorie
				categorie.setUrlImageCategorie(uploadedFile.getSubmittedFileName());

				// 2. Ajout de la catégorie à la database
				if (categorieDAO.add(categorie)) {
					contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ajout de la catégorie",
							"- la catégorie a bien été ajoutée avec succès"));

				} else {
					contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Echec lors de l'ajout de la catégorie", "- la catégorie n'a pas été ajoutée"));

				} // end else

				// ----------------------------------------------
				// ajout de la photo dans le dossier images
				// -----------------------------------------------

				InputStream imageContent = uploadedFile.getInputStream();

				// 3. Récupération de l'image uplaodée dans 'edit-catégorie.xhtml'
				
				FacesContext fContext = FacesContext.getCurrentInstance();
				
				String pathImg = fContext.getExternalContext().getInitParameter("file-upload");
				
				String filePath = fContext.getExternalContext().getRealPath(pathImg);
				
				File targetFile = new File(filePath, uploadedFile.getSubmittedFileName());

				// 4. Enregistrement de l'image dans le dossier image
				OutputStream outStream = new FileOutputStream(targetFile);
				byte[] buf = new byte[1024];
				int len;

				while ((len = imageContent.read(buf)) > 0) {
					outStream.write(buf, 0, len);
				} // end while

				
				outStream.close();

			} catch (IOException e) {
				Logger.getLogger(GestionProduitBean.class.getName()).log(Level.SEVERE, null, e);
				e.printStackTrace();
			} // end catch

		} // end if ajout

		// -------------------------------------------
		// cas : modif
		// -------------------------------------------


		if (categorie.getId_categorie() != 0) {

			if (uploadedFile != null) {

				// 1. Ajout du chemin absolu à la propriété UrlImageCategorie
				String fileNameToUpdate = uploadedFile.getSubmittedFileName();

				// 2. Vérification que le chemin n'est pas null et sans caractère
				if (!"".equals(fileNameToUpdate) && fileNameToUpdate != null) {

					// 3. affectation du nouveau nom à la propriété urlImage de la catégorie
					categorie.setUrlImageCategorie(fileNameToUpdate);

				} // end if equals
			} // end ifUploadedFile

			if (categorieDAO.update(categorie)) {

				System.out.println("Modif réussie");
				/*
				 * contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				 * "Modification de la catégorie",
				 * "- La catégorie a été modifiée avec succès"));
				 */

			} else {
				/*
				 * contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
				 * " la modification de la catégorie a échoué",
				 * " - la catégorie n'a pas été modifiée"));
				 */
				
			} // end else pour msg ajout
		} // end if modif
	}// end saveCategorie

	/* _____________________________________________________ */
	/* _____________ Méthodes en developpement _____________ */
	/* _____________________________________________________ */

	/**
	 * Méthode pour afficher la liste des items (catégories) non selectionnés
	 * Non invoquée pour l'instant
	 * @param event
	 */
	public void onItemUnselect(UnselectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage();

		// Affichage d'un message donnant la liste des items nont selectionnés
		msg.setSummary("Item unselected: " + event.getObject().toString());
		msg.setSeverity(FacesMessage.SEVERITY_INFO);

		context.addMessage(null, msg);
	}// end onItemUnselect

	// _____ Getter / Setter ______//

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public List<Categorie> getListeCategories() {
		return listeCategories;
	}

	public void setListeCategories(List<Categorie> listeCategories) {
		this.listeCategories = listeCategories;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

}// end class
