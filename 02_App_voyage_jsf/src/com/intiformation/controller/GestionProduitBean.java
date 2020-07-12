package com.intiformation.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.intiformation.DAO.IProduitDAO;
import com.intiformation.DAO.ProduitDAOImpl;
import com.intiformation.modeles.Produit;

import net.bootsfaces.utils.FacesMessages;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.event.*;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.faces.component.*;
import javax.faces.context.FacesContext;

@ManagedBean(name = "GestionProduitBean")
@SessionScoped
public class GestionProduitBean implements Serializable {

	// _____ Props ______//

	private List<Produit> listeProduits;
	private Produit produit;
	private String motCle;
	private boolean selectionProduit;
	private List<Produit> listePanier;
	private ActionEvent event;
	HttpSession session;
	
	
	// file upload de l'API servlet
    private Part uploadedFile;

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

		/*
		 * =============================================================================
		 * ===
		 */

	}// end findAllProduitsBDD

	public List<Produit> findProduitByMotCle(String motCle) {

		System.out.println("Mot clé :" + motCle);

		FacesContext contextJSF = FacesContext.getCurrentInstance();

		listeProduits = produitDAO.getByKeyword(motCle);

		/*
		 * if (motCle != null) {
		 * 
		 * listeProduits = produitDAO.getByKeyword(motCle);
		 * 
		 * if (listeProduits.isEmpty()) {
		 * 
		 * contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
		 * "Pas de voyage", ": Aucun voyage correspondant à votre recherche...")); }
		 * else {
		 * 
		 */
		return listeProduits;
		/*
		 * }
		 * 
		 * } else {
		 * 
		 * contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
		 * "Pas de voyage", ": vous avez pas rentré de requête"));
		 * 
		 * } // end else
		 */

	}// end findProduitByMotClé

	/*
	 * =============================================================================
	 * ===
	 */

	public void selectionnerProduit(ActionEvent event) {

		UIParameter uip = (UIParameter) event.getComponent().findComponent("selectID");
		UIParameter uip2 = (UIParameter) event.getComponent().findComponent("selectIsDispo");

		int idProduit = (int) uip.getValue();
		boolean isDispo = (boolean) uip2.getValue();
		
		//selection du produit
		Produit produitASelectionner = produitDAO.getById(idProduit);

		
		//update du selection en true
		isDispo = true;

		//
		produitASelectionner.setSelectionProduit(isDispo);

		//
		produitDAO.update(produitASelectionner);

		listePanier = produitDAO.getProduitSelectionnes(isDispo);
		
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		if (session == null) {
			
			
			HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(true);
			
			session.setAttribute("listePanier", listePanier);
		}else {
			HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);
			
			session.setAttribute("listePanier", listePanier);
			
		}

		

	}// end selectionnerProduit
	
	/*
	 * ================================================================================
	 */

	public List<Produit> ListeProduitsSelectionnes() {

		
		listePanier.forEach(e->System.out.println(e.getNomProduit()));
		
		return listePanier;
		
		
	}//end ListeProduitsSelectionnes()

	/*
	 * ================================================================================
	 */

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
		UIParameter uip = (UIParameter) event.getComponent().findComponent("suppID");

		// 2. récup de la valeur du param
		int idProduitSupp = (int) uip.getValue();

		// 3. suppression du produit dans la bdd via id

		// 3.1 récup du context de JSF
		FacesContext contextJSF = FacesContext.getCurrentInstance();

		// 3.2 suppression du livre
		if (produitDAO.delete(idProduitSupp)) {

			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Suppression du produit",
					"- Le produit a été surpprimé avec succès"));

		} else {
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					" la suppression du produit à échouée", " - le produit n'a pas été supprimé"));

		} // end else
	}// end supprimerProduit
	
	
	
	
	/**
     * permet d'initialiser un produit 
     * methode appelée lors de l'ajout du produit
     *
     * @param event
     */
    public void initVoyage(ActionEvent event) {
        setProduit(new Produit());
    }// end initVoyage
    
    /**
     * recup d'un produit 
     *
     * @param event
     */
    public void recupProduit(ActionEvent event) {

        UIParameter cp = (UIParameter) event.getComponent().findComponent("modifId");
        int id = (int) cp.getValue();

        Produit produit = produitDAO.getById(id);

        setProduit(produit);
       
    }// end recupProduit


	
	
    
    public void saveVoyage(ActionEvent event) {

        //-------------------------------------------
        // cas : ajout 
        //-------------------------------------------
        if (produit.getIdProduit() == 0) {

            try {
                // traitement du fileUpload : recup du nom de l'image
                String fileName = uploadedFile.getSubmittedFileName();
                
                // affectation du nom a la prop urlImage du voyage
                produit.setUrlImageProduit(fileName);
                
                // ajout du voyage dans la bdd
                produitDAO.add(produit);

                //----------------------------------------------
                // ajout de la photo dans le dossier images
                //-----------------------------------------------
                
                /*++++++++++++++++++++++++++++++++ version 1 ++++++++++++++++++*/
                
                // recup du contenu de l'image
                InputStream imageContent = uploadedFile.getInputStream();

                // recup de la valeur du param d'initialisation context-param de web.xml
                FacesContext fContext = FacesContext.getCurrentInstance();
                String pathTmp = fContext.getExternalContext().getInitParameter("file-upload");
                
                String filePath = fContext.getExternalContext().getRealPath(pathTmp);

                // creation du fichier image (conteneur de l'image) 
                File targetFile = new File(filePath, fileName);

                // instanciation du flux de sortie vers le fichier image
                OutputStream outStream = new FileOutputStream(targetFile);
                byte[] buf = new byte[1024];
                int len;

                while ((len = imageContent.read(buf)) > 0) {
                    outStream.write(buf, 0, len);
                }
                
                outStream.close();

            } catch (IOException ex) {
                Logger.getLogger(GestionProduitBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //-------------------------------------------
        // cas : modif 
        //-------------------------------------------
        if (produit.getIdProduit() != 0) {

            if (uploadedFile != null) {

                String fileNameToUpdate = uploadedFile.getSubmittedFileName();

                if (!"".equals(fileNameToUpdate) && fileNameToUpdate != null) {

                    // affectation du nouveau nom à la prop urlImage du voyage 
                    produit.setUrlImageProduit(fileNameToUpdate);
                }// end if equals
            }// end if uploadedFile != null

            produitDAO.update(produit);
        }// end if modif 

    }//end saveBook()
	
	
	

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

	public boolean isSelectionProduit() {
		return selectionProduit;
	}

	public void setSelectionProduit(boolean selectionProduit) {
		this.selectionProduit = selectionProduit;
	}

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

}// end classe
