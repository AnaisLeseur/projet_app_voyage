package com.intiformation.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.intiformation.DAO.ILigneCommandeDAO;
import com.intiformation.DAO.LigneCommandeDAOImpl;
import com.intiformation.modeles.LigneCommande;
import com.intiformation.modeles.Produit;

/**
 * ManagedBean pour la gestion des lignes de commande, utilisé pour : 
 * 		- ajouter / modifier / supprimer une ligne de commande de la bdd via la DAO 
 * 		- initialiser une ligne de commande
 * 		- récupérer toutes les lignes de commande de la bdd / ou récupérer 1 ligne via ses 2 PK
 * 		- mise à jour de la ligne de commande avec la quantité selectionné (dans le panier) + MaJ du prix ( = prix initial pour 1 * quantité)
 * 		- Calcul du prix par voyage = prix du voyage pour 1 personne * quantité selectionnée = prix du voyage 
 * 		- Calcul du prix total du panier = somme de tous les prix par voyage du panier = prix total du panier 
 * 
 * 
 * @author vincent
 *
 */
@ManagedBean(name = "GestionLigneCommandeBean")
@SessionScoped
public class GestionLigneCommandeBean implements Serializable {
	
	
	// _____ Props ______//
	private List<LigneCommande> listeLigneCommande;
	private List<LigneCommande> listeLigneCommandeDoubleId;
	private List<Produit> listePanier;
	private LigneCommande ligneCommande;
	
	double prixParVoyage;
	int quantiteVoyage;
	
	private Integer pIdCommande;
	private Integer pIdProduit;
	
	private int nbPersonne;
	private 	double prixTotal;
	
	private ILigneCommandeDAO ligneCommandeDAO;

	
	// _____ Ctor ______//
	public GestionLigneCommandeBean() {
		ligneCommandeDAO = new LigneCommandeDAOImpl();
	}// end ctor
	

	
	/* ============================================================================= */
	// ____________________ Méthodes ________________________________________________//
	/* ============================================================================= */
	
	
	/**
	 * methode qui permet d'initialiser une ligne de commande avec l'id du produit selectionné et le prix de base (pour quantité = 1)
	 * appelée lors de l'ajout dans le panier
	 */
	public List<LigneCommande> initLigneCommande (ActionEvent event) {
		
		// récupération des params : id du produit et prix initial
		UIParameter idProduit = (UIParameter) event.getComponent().findComponent("IdProduit");
		UIParameter prixProduit = (UIParameter) event.getComponent().findComponent("PrixProduit");
		
		int idProduitAdd = (int) idProduit.getValue();
		double prixProduitAdd = (double) prixProduit.getValue();
		
		// initialisation de la ligne (SANS l'id de la commande qui n'existe pas encore) 
		// AVEC id du produit; quantité = 1 et prix du voyage pour 1 personne
		LigneCommande ligneDeCommande = new LigneCommande(idProduitAdd, 1, prixProduitAdd);
		
		// ajout de cette ligne de commande à la liste des lignes de commande qui composent le panier 
		listeLigneCommande.add(ligneDeCommande);
		
		// setAttribute pour la liste des lignes de commande : listeLigneCommande
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);
		session.setAttribute("listeLigneCommande", listeLigneCommande);

		return listeLigneCommande;
		
	}// end initLigneCommande
	

	
	/* ============================================================================= */
	
	/**
	 * methode pour récupérer la liste de toutes les lignes de commande de la bdd
	 */
	public List<LigneCommande> findAllLigneCommandeBDD(){
		
		listeLigneCommande = ligneCommandeDAO.getAll();
		
		return listeLigneCommande;
		
	}// end findAllLigneCommandeBDD
	

	
	/* ============================================================================= */
	
	/**
	 * methode pour récupérer la liste des lignes de commande by doubleId : via les 2 PK : id commande + id produit
	 */
	// ERREUR : le retour aurait du etre une ligne de commande unique issue d'une commande et pour 1 produit ! 
	
	public List<LigneCommande> findLigneCommandeDoubleId(){

		listeLigneCommandeDoubleId = ligneCommandeDAO.getByDoubleId(pIdCommande, pIdProduit);
		
		return listeLigneCommandeDoubleId;
		
	}// end findAllLigneCommandeBDD
	
	
	
	/* ============================================================================= */
	
	/**
	 * methode utilisée pour mettre à jour la ligne de commande lors de la modification de la quantité choisie dans le panier 
	 * 
	 * @param event
	 */
	public void updateQuantitePrix(ActionEvent event) {

		// récupération des paramètres passés : quantité choisie avec le +/- ; l'id du produit et la position dans la liste de la ligne à modifier 
		UIParameter quantiteProduit = (UIParameter) event.getComponent().findComponent("QuantiteProduit");
		UIParameter idProduit = (UIParameter) event.getComponent().findComponent("IdProduit");
//		UIParameter stockProduit = (UIParameter) event.getComponent().findComponent("StockProduit");
		UIParameter uip2 = (UIParameter) event.getComponent().findComponent("selectModifIdLignePanier");
		
		int quantiteAdd = (int) quantiteProduit.getValue();
		int idProduitAdd = (int) idProduit.getValue();
//		int stockProduitAdd = (int) stockProduit.getValue();
		int selectModifIdLignePanier = (int) uip2.getValue();
		
		// récupération des Attributs ds session : listeLigneCommande et listePanier
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);
		listeLigneCommande = (List<LigneCommande>) session.getAttribute("listeLigneCommande");
		listePanier = (List<Produit>) session.getAttribute("listePanier");
		
		// on récupère le prix du voyage sélectionné dans la liste des voyages du panier 
		prixParVoyage = listePanier.get(selectModifIdLignePanier).getPrixProduit();

		// calcule du prix total du voyage => prix pour 1 personne * quantité demandée
		prixTotal = quantiteAdd * prixParVoyage;
		
		// Création d'un ligne de commande avec le prix total du voyage et la quantité demandée 
		ligneCommande = new LigneCommande(idProduitAdd, quantiteAdd, prixTotal);
		
		// Modification de la liste des lignes de commande qui composent le panier: 
		// MAJ de la ligne modifiée (selectModifIdLignePanier) avec l'insertion de la nouvvelle ligne au meme index
		listeLigneCommande.set(selectModifIdLignePanier, ligneCommande);

		// setAttribute de la nouvelle liste des ligne de commande listeLigneCommande
		session.setAttribute("listeLigneCommande", listeLigneCommande);	
		
	}// end updateQuantitePrix
	
	
	
	/* ============================================================================= */
	
	/**
	 * Methode qui permet de calculer la somme du panier au total en prenant en compte les quantités demandées pour chaque ligne de commande
	 * @return le montant total du panier
	 */
	public double PrixTotal() {
		
		// récupération de la liste des lignes de commande
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);
		listeLigneCommande = (List<LigneCommande>) session.getAttribute("listeLigneCommande");

		// faire la somme du prix 'Prix_ligne()' = prix par voyage pour chaque ligne de la liste => prix total du panier
		double sommePanier = listeLigneCommande.stream().mapToDouble(ligne -> ligne.getPrix_ligne()).sum();
		
		return sommePanier;

	}// end PrixTotal
	
	
	
	
	/* ============================================================================= */
	/* ============================================================================= */
	
	
	// _____ Getter /setter ______//
	

	public int getNbPersonne() {
		return nbPersonne;
	}


	public void setNbPersonne(int nbPersonne) {
		this.nbPersonne = nbPersonne;
	}


	public LigneCommande getLigneCommande() {
		return ligneCommande;
	}


	public void setLigneCommande(LigneCommande ligneCommande) {
		this.ligneCommande = ligneCommande;
	}



}// end GestionLigneCommandeBean
