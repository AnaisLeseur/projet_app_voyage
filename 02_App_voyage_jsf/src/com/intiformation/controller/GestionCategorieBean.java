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

@ManagedBean(name = "GestionCategorieBean")
@SessionScoped
public class GestionCategorieBean implements Serializable {

	// _____ Props ______//

	private List<Categorie> listeCategories;
	private Map<Integer, Boolean> listeCategorieSelectionnes = new HashMap<Integer, Boolean>();
	private Categorie categorie;

	ICategorieDAO categorieDAO;

	private Part uploadedFile;
	

	FacesContext contextJSF = FacesContext.getCurrentInstance();

	// _____ Ctor ______//

	public GestionCategorieBean() {

		categorieDAO = new CategorieDAOImpl();
		
		

	}// end ctor

	// _____ Méthodes ______//
	/**
	 * Méthode pour afficher la liste de toutes les catégories
	 * 
	 * @return
	 */
	public List<Categorie> findAllCategoriesBDD() {

		listeCategories = categorieDAO.getAll();

		return listeCategories;

	}// end findAllCategoriesBDD
	
	

	/**
	 * Méthode pour supprimer un produit dans la db Invoquée au clic sur lien
	 * 'supprimer' dans admin-accueil au clic, l'évenement
	 * 'javax.faces.event.ActionEvent' se déclenche et encapsule toutes les infos
	 * concernant le composant
	 * 
	 * @param event
	 */
	public void supprimerCategorie(ActionEvent event) {

		// 1. récup du param passé dans le composant au clic sur le lien 'supprimer'
		UIParameter uip = (UIParameter) event.getComponent().findComponent("suppID");

		// 2. récup de la valeur du param
		int idCategorie = (int) uip.getValue();

		// 3. suppression de la catégorie dans la bdd via id
		
		// 3.1 récup du context de JSF
		FacesContext contextJSF = FacesContext.getCurrentInstance();

		// 3.2 suppression du livre
		if (categorieDAO.delete(idCategorie)) {

			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Suppression de la catégorie",
					"- La catégorie a été surpprimée avec succès"));

		} else {
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					" la suppression de la catégorie à échouée", " - la catégorie n'a pas été supprimée"));

		} // end else
		
		
	}// end supprimerCategorie

	/**
	 * Initialisation d'une catégorie Appelée lors de l'ajout d'une catégorie
	 * 
	 * @param event
	 */
	public void initCategorie(ActionEvent event) {
		setCategorie(new Categorie());
	}// end initCategorie

	public void recupCategorie(ActionEvent event) {

		UIParameter uip = (UIParameter) event.getComponent().findComponent("modifId");

		int idCategorie = (int) uip.getValue();

		Categorie categorie = categorieDAO.getById(idCategorie);

		setCategorie(categorie);

	}// end recupCategorie

	public void saveCategorie(ActionEvent event) {

	 	FacesContext contextJSF = FacesContext.getCurrentInstance();
		// -------------------------------------------
		// cas : ajout
		// -------------------------------------------

		if (categorie.getId_categorie() == 0) {

			try {
				String fileName = uploadedFile.getSubmittedFileName();

				categorie.setUrlImageCategorie(fileName);

				
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

				FacesContext fContext = FacesContext.getCurrentInstance();
				String pathImg = fContext.getExternalContext().getInitParameter("file-upload");

				String filePath = fContext.getExternalContext().getRealPath(pathImg);

				File targetFile = new File(filePath, fileName);

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

				String fileNameToUpdate = uploadedFile.getSubmittedFileName();

				if (!"".equals(fileNameToUpdate) && fileNameToUpdate != null) {

					// affectation du nouveau nom à la prop urlImage du voyage
					categorie.setUrlImageCategorie(fileNameToUpdate);
				} // end if equals
			} // end ifUploadedFile
			
			if (categorieDAO.update(categorie)) {
				
				System.out.println("Modif réussie");
				/*
	   			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification de la catégorie",
    					"- La catégorie a été modifiée avec succès"));
	   			*/

            } else {
            	/*
    			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
    					" la modification de la catégorie a échoué", " - la catégorie n'a pas été modifiée"));
    			*/
            }// end else pour msg ajout			
			
		} // end if modif

	}// end saveCategorie
	
	
	
	public void onItemUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
         
        FacesMessage msg = new FacesMessage();
        msg.setSummary("Item unselected: " + event.getObject().toString());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
         
        context.addMessage(null, msg);
    }//end  onItemUnselect
	

      
       

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



	public Map<Integer, Boolean> getListeCategorieSelectionnes() {
		return listeCategorieSelectionnes;
	}

	public void setListeCategorieSelectionnes(Map<Integer, Boolean> listeCategorieSelectionnes) {
		this.listeCategorieSelectionnes = listeCategorieSelectionnes;
	}

}// end class
