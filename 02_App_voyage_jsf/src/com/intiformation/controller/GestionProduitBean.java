package com.intiformation.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.intiformation.DAO.CommandeDAOImpl;
import com.intiformation.DAO.ICommandeDAO;
import com.intiformation.DAO.ILigneCommandeDAO;
import com.intiformation.DAO.IProduitCategorie;
import com.intiformation.DAO.IProduitDAO;
import com.intiformation.DAO.LigneCommandeDAOImpl;
import com.intiformation.DAO.ProduitCategorieDAOImpl;
import com.intiformation.DAO.ProduitDAOImpl;
import com.intiformation.modeles.Client;
import com.intiformation.modeles.Commande;
import com.intiformation.modeles.LigneCommande;
import com.intiformation.modeles.Produit;
import com.intiformation.modeles.ProduitCategorie;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.event.*;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import javax.faces.component.*;
import javax.faces.context.FacesContext;

/**
 * ManagedBean pour la gestion des Produits, utilisé pour : 
 * 		- ajouter / modifier / supprimer un Produit de la bdd via la DAO 
 * 		- ajouter / supprimer un produit du panier 
 * 		- liste des produits de la bdd / liste des produits par categéries
 * 		- rechercher et récupérer un produit par mot clé  
 * 
 * @author vincent
 *
 */
@ManagedBean(name = "GestionProduitBean")
@SessionScoped
public class GestionProduitBean implements Serializable {

	// _____ Props ______//

	private Produit produit;
	private ProduitCategorie produitCateg;

	private String motCle;
	private String nomCategorie;
	private boolean selectionProduit;
	private int nbPersonne = 1;
	private int idClient;

	private List<Produit> listeProduits;
	private List<Produit> listePanier = new ArrayList<>();
	private List<Produit> listeMonde = new ArrayList<>();
	private List<Produit> listeProduitCateg;
	private List<LigneCommande> listeLigneCommande = new ArrayList<>();

	HttpSession session;

	// file upload de l'API servlet
	private Part uploadedFile;

	IProduitDAO produitDAO;
	IProduitCategorie produitCategDAO;
	ICommandeDAO commandeDAO;
	ILigneCommandeDAO lignecommandeDAO;

	GestionLigneCommandeBean gestionLigneCommandeBean;
	Commande commandeRecup;

	// _____ Ctor ______//

	public GestionProduitBean() {
		produitDAO = new ProduitDAOImpl();
		produitCategDAO = new ProduitCategorieDAOImpl();
		gestionLigneCommandeBean = new GestionLigneCommandeBean();
		commandeDAO = new CommandeDAOImpl();
		lignecommandeDAO = new LigneCommandeDAOImpl();
	}// end ctor 

	
	/* ============================================================================= */
	// ____________________ Méthodes ________________________________________________//
	/* ============================================================================= */
	
	/**
	 * Récuperation de la liste de tous les produits dans bdd via la DAO. Méthode utilisée par le
	 * composant <h:datatable> de 'accueil-client.xhtml' pour afficher la liste des voyages.
	 * et utilisée dans 'accueil-admin.xhtml' pour afficher la liste de tous les voyages
	 * 
	 * @return
	 */
	public List<Produit> findAllProduitsBDD() {

		listeProduits = produitDAO.getAll();

		return listeProduits;
	}// end findAllProduitsBDD
	

	/* ============================================================================= */
	
	
	/**
	 * Méthode appelée dans la barre de recherche du header lors de la recherche par mot clé 
	 * @return la page d'affichage de la liste des produit trouvés pour ce mot clé
	 */
	public String findProduitByMotCleRedirect() {

		return "recherche-produit-motcle.xhtml?faces-redirect=true";

	}// end findProduitByMotCleRedirect
	
	
	
	/* ============================================================================= */
	
	/**
	 * Récupération de la liste de produits, via la DAO, dont le nom ou la description contiennent le mot clé.
	 * Méthode appelée pour l'affichage de la page 'recherche-produit-motcle.xhtml'
	 * @param motCle: paramètre de type String qui a été saisi dans la barre de recherche
	 * @return la liste des voyages ayant le mot clé 
	 */
	public List<Produit> findProduitByMotCle(String motCle) {

		listeProduits = produitDAO.getByKeyword(motCle);

		return listeProduits;

	}// end findProduitByMotClé
	
	

	/* ================= Metode à revoir ============================================ */

	/**
	 * Méthode qui permet de récupérer les produits sélectionnés via le bouton "choisir ce voyage"
	 * appelée via la page 'details-produit.xhtml"
	 * @param event : récupération de l'id du produit sélectionné
	 */
	public void selectionnerProduit(ActionEvent event) {

		// parametres à récupérer
		UIParameter uip = (UIParameter) event.getComponent().findComponent("selectID");
		UIParameter uip2 = (UIParameter) event.getComponent().findComponent("selectIsDispo");

		int idProduit = (int) uip.getValue();
		boolean isDispo = (boolean) uip2.getValue();

		// récupération des informations du produit sélectionné (via la DAO) 
		Produit produitASelectionner = produitDAO.getById(idProduit);

		// update du selection en true = le produit est sélectionné dans le panier
		isDispo = true;

		produitASelectionner.setSelectionProduit(isDispo);

		// mise à jour du produit dans la BDD 
		produitDAO.update(produitASelectionner);


// A REFAIRE (ne fonctionne pas correctement: tous les cas se retrouvent dans le ELSE):
// IF : produitASelectionner already exist in listePanier => on ne l'ajoute pas à la listePanier
// ELSE : (le produitASelectionner n'est pas encore dans listePanier) => ajout à la listePanier

		boolean test = listePanier.contains(produitASelectionner);

		if (test) {
			System.out.println("if (listePanier.contains(produitASelectionner)): Vrai => NE l'ajoute pas ");

		} else {
			System.out.println("if (listePanier.contains(produitASelectionner)): FAUX => ajoute ");
			
			// MaJ de la listePanier = liste des produits dont la selection = true 
			listePanier = produitDAO.getProduitSelectionnes(isDispo);
			
			// création d'une ligne de commande pour le produit sélectionné
			LigneCommande ligneCommande = new LigneCommande(	produitASelectionner.getIdProduit(), 
															1,
															produitASelectionner.getPrixProduit());
			// on ajoute la ligne de commande créée à la liste des lignes de commande pour ce panier 
			listeLigneCommande.add(ligneCommande);

		}// end else

		
		// MaJ des différents Attributs de session : 'listePanier' et 'listeLigneCommande' en fonction de l'existance ou non de la session
		FacesContext contextJSF = FacesContext.getCurrentInstance();

		if (session == null) {
			// IF = Vrai => la session n'existe pas encore => on la créer et on 'setAttribute'
			
			HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(true);

			session.setAttribute("listePanier", listePanier);
			session.setAttribute("listeLigneCommande", listeLigneCommande);

		} else {
			
			// la session existe deja; on la récupère et on 'setAttribute'

			HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);

			session.setAttribute("listePanier", listePanier);
			session.setAttribute("listeLigneCommande", listeLigneCommande);

		} // end else
	}// end selectionnerProduit
	



	/* ============================================================================= */

	/**
	 * Méthode qui permet de récupérer la liste des produits sélectionnés via le bouton "choisir ce voyage"
	 * appelée lors de l'affichage du panier 
	 */
	public List<Produit> ListeProduitsSelectionnes() {

		listePanier = produitDAO.getProduitSelectionnes(true);

		return listePanier;

	}// end ListeProduitsSelectionnes()
	


	/* ============================================================================= */
	
	/**
	 * Méthode qui permet de récupérer la liste des lignes de commande
	 * appelée lors de l'affichage du panier 
	 */
	public List<LigneCommande> ListeLigneCommande() {

		return listeLigneCommande;

	}// end ListeLigneCommande()



	/* ============================================================================= */

	/**
	 * Méthode pour supprimer un produit DU PANIER Invoquée au clic sur lien
	 * 'retirer du panier' dans PANIER
	 * @param event
	 * @return 'listePanier' : la nouvelle liste des produits contenus dans le panier 
	 */
	public List<Produit> supprimerProduitduPanier(ActionEvent event) {

		// 1. récup des params passés dans le composant au clic sur le lien 'retirer du panier'
		UIParameter uip = (UIParameter) event.getComponent().findComponent("selectSuppIdPanier");
		UIParameter uip2 = (UIParameter) event.getComponent().findComponent("selectSuppIdLignePanier");

		// 2. récup des valeurs des params
		int idProduitSuppDuPanier = (int) uip.getValue();
		int selectSuppIdLignePanier = (int) uip2.getValue();


		// 3. récup des infos du produit à retirer du panier
		Produit produitARetirerDuPanier = produitDAO.getById(idProduitSuppDuPanier);

		// update de la props du produit selectionné à false
		produitARetirerDuPanier.setSelectionProduit(false);

		// récup du context de JSF
		FacesContext contextJSF = FacesContext.getCurrentInstance();

		// 3.2 suppression du produit du panier 
		if (produitDAO.update(produitARetirerDuPanier)) {

			contextJSF.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Le produit a été supprimé du panier", ""));

		} else {
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					" la suppression du produit à échouée", " - le produit n'a pas été supprimé du panier"));

		} // end else

		// récup de la nouvelle liste des produits sélectionnés dans le panier
		listePanier = produitDAO.getProduitSelectionnes(true);

		// MAJ de la liste des ligne de commande présente pour faire le panier
		listeLigneCommande.remove(selectSuppIdLignePanier);

		return listePanier;
	}// end supprimerProduit



	/* ============================================================================= */

	/**
	 * Méthode pour supprimer un produit dans la bdd. Invoquée au clic sur lien
	 * 'supprimer' dans 'admin-accueil' au clic, l'évenement
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

		// 3.1 récup du context de JSF
		FacesContext contextJSF = FacesContext.getCurrentInstance();

		// 3.2 suppression du produit dans la bdd via id
		if (produitDAO.delete(idProduitSupp)) {

			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Suppression du produit",
					"- Le produit a été surpprimé avec succès"));

		} else {
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					" la suppression du produit à échouée", " - le produit n'a pas été supprimé"));

		} // end else
	}// end supprimerProduit
	
	
	
	/* ============================================================================= */
	
	/**
	 * methode qui permet d'initialiser un produit. Appelée lors de l'ajout du produit dans 'accueil-admin'
	 * @param event
	 */
	public void initVoyage(ActionEvent event) {
		setProduit(new Produit());
	}// end initVoyage
	
	
	
	/* ============================================================================= */

	/**
	 * recupération d'un produit via son id, par la methode getById de la DAO
	 * @param event
	 */
	public void recupProduit(ActionEvent event) {

		// on récupère l'id du produit passé en paramètre 
		UIParameter cp = (UIParameter) event.getComponent().findComponent("modifId");
		int id = (int) cp.getValue();

		// récupération des infos du produit via son id 
		Produit produit = produitDAO.getById(id);

		setProduit(produit);

	}// end recupProduit
	
	

	/* ============================================================================= */
	
	
	/**
	 * methode pour l'ajout ou la modification d'un voyage dans la bdd 
	 * methode utilisée dans les pages 'admin' 
	 * @param event
	 */
	public void saveVoyage(ActionEvent event) {

		// -------------------------------------------
		// cas : ajout
		// -------------------------------------------
		if (produit.getIdProduit() == 0) {

			try {
				// traitement du fileUpload : recup du nom de l'image
				String fileName = uploadedFile.getSubmittedFileName();

				// affectation du nom a la prop urlImage du voyage
				produit.setUrlImageProduit(fileName);
				FacesContext contextJSFajout = FacesContext.getCurrentInstance();

				// ajout du voyage dans la bdd + message
				if (produitDAO.add(produit)) {

					contextJSFajout.addMessage(null, new FacesMessage("Ajout du produit",
							"- Le produit a été ajouté avec succès"));

				} else {
					contextJSFajout.addMessage(null, new FacesMessage("l'ajout du produit a échoué", " - le produit n'a pas été ajouté"));
				} // end else pour msg ajout

				// ----------------------------------------------
				// ajout de la photo dans le dossier images (resources)
				// -----------------------------------------------

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
				} // end while

				outStream.close();

			} catch (IOException ex) {
				Logger.getLogger(GestionProduitBean.class.getName()).log(Level.SEVERE, null, ex);
			} // end catch

		} // end if ajout

		// -------------------------------------------
		// cas : modif
		// -------------------------------------------
		if (produit.getIdProduit() != 0) {

			// ----------------------------------------------
			// creation liaison catégorie - produit
			// -----------------------------------------------

			int IdNewProduit = produit.getIdProduit();
			System.out.println("Id du produit crée :" + IdNewProduit);

			if (uploadedFile != null) {

				String fileNameToUpdate = uploadedFile.getSubmittedFileName();

				if (!"".equals(fileNameToUpdate) && fileNameToUpdate != null) {

					// affectation du nouveau nom à la prop urlImage du voyage
					produit.setUrlImageProduit(fileNameToUpdate);
					
				} // end if equals
			} // end if uploadedFile != null

			FacesContext contextJSFmodif = FacesContext.getCurrentInstance();

			if (produitDAO.update(produit)) {

				contextJSFmodif.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Modification du produit",
						"- Le produit a été modifié avec succès"));

			} else {
				contextJSFmodif.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						" la modification du produit a échouée", " - le produit n'a pas été modifié"));
			} // end else pour msg ajout
		} // end if modif
	}// end saveBook()
	
	
	
	/* ============================================================================= */
	
	
	/**
	 * methode pour récupérer les produits d'une catégorie et récupérer le nom de la catégorie selectionnée
	 * @param event
	 */
	public void findProduitParCategorie(ActionEvent event) {

		// récupération de l'id de la catégorie sélectionnée
		UIParameter cp = (UIParameter) event.getComponent().findComponent("CategorieID");
		int idCategorie = (int) cp.getValue();

		// récupération du nom de la catégorie sélectionnée
		UIParameter cpNom = (UIParameter) event.getComponent().findComponent("CategorieNom");
		nomCategorie = (String) cpNom.getValue();

		// attribut de session avec le nom de la catégorie sélectionnée (pour afficher ce mot dans le titre de la page 'produit-par-categorie.xhtml'
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("nomCategorie", nomCategorie);

		// on récupère tous les produits appartenant à la catégorie dans la liste 'listeProduitCateg'
		listeProduitCateg = produitDAO.getByCategorie(idCategorie);

	}// end findProduitParCategorie
	
	
	
	/* ============================================================================= */
	
	/**
	 * methode pour récupérer les produits de la categorie monde pour l'affichage dans la page d'accueil-client
	 * @param event
	 */
	public List<Produit> AfficherlisteProduitMonde() {
		
		// id de la catégorie monde =2
		int i = 2;

		// récup des voyages dans une liste
		listeMonde = produitDAO.getByCategorie(i);

		return listeMonde;
	}// end AfficherlisteProduitMonde
	
	
	
	
	/* ============================================================================= */
	
	/**
	 * methode pour récupérer la liste des produits de 'listeProduitCateg'
	 * @param event
	 */
	public List<Produit> AfficherListeProduitParCateg() {

		return listeProduitCateg;

	}// end AfficherListeProduitParCateg
	
	
	
	
	/* ============================================================================= */
	
	/**
	 * methode invoquée après la validation de la commande 
	 * methode qui permet de :
	 * 		- créer une commande (avec une id et une date du jour)
	 * 		- créer les lignes de commandes finales dans la bdd (avec le prix, la quantité, id de la commande)
	 * 		- remettre tous les produits qui etaient dans le panier avec un selection = false 
	 * 		- remettre l'attribut 'listePanier' à null afin de vider le panier 
	 * 
	 * @param event
	 * @return 'validation-commande.xhtml' : page pour dire "merci pour votre commande"
	 */

	public String ApresPaiement() {

		FacesContext contextJSF = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);

		LocalDate date = LocalDate.now();
		Date dateDuJour = Date.valueOf(date);

		System.out.println("Date dateDuJour : " + dateDuJour);

		Client client = (Client) session.getAttribute("client");
		idClient = client.getId_client();

		Commande commande = new Commande(dateDuJour, idClient);

		commandeDAO.add(commande);

		commandeRecup = new Commande();
		commandeRecup = commandeDAO.findIdMax();

		int commandeIdRecup = commandeRecup.getId_commande();


		for (LigneCommande ligneCommande : listeLigneCommande) {

			int produitRecup = ligneCommande.getProduit_id();
			int quantiteRecup = ligneCommande.getQuantite_ligne();
			double PrixRecup = ligneCommande.getPrix_ligne();

			LigneCommande ligneCreation = new LigneCommande(commandeIdRecup, produitRecup, quantiteRecup, PrixRecup);

			lignecommandeDAO.add(ligneCreation);

		} // end for

		listePanier = produitDAO.getProduitSelectionnes(true);

		for (Produit produit : listePanier) {
			produit.setSelectionProduit(false);
			produitDAO.update(produit);

		} // end for

		session.setAttribute("listePanier", null);

		return "validation-commande.xhtml?faces-redirect=true";

	}// end ApresPaiement
	
	

	
	
	/* ============================================================================= */
	/* ============================================================================= */
	
	
	// _____ Getter /setter ______//

	public String getNomCategorie() {
		return nomCategorie;
	}

	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}

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

	public int getNbPersonne() {
		return nbPersonne;
	}

	public void setNbPersonne(int nbPersonne) {
		this.nbPersonne = nbPersonne;
	}

	public ProduitCategorie getProduitCateg() {
		return produitCateg;
	}

	public void setProduitCateg(ProduitCategorie produitCateg) {
		this.produitCateg = produitCateg;
	}

}// end classe
