package com.intiformation.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.intiformation.DAO.ILigneCommandeDAO;
import com.intiformation.DAO.LigneCommandeDAOImpl;
import com.intiformation.modeles.Categorie;
import com.intiformation.modeles.LigneCommande;


@ManagedBean(name = "GestionLigneCommandeBean")
@SessionScoped
public class GestionLigneCommandeBean implements Serializable {
	
	
	// _____ Props ______//
	private List<LigneCommande> listeLigneCommande;
	private List<LigneCommande> listeLigneCommandeDoubleId;
	private LigneCommande ligneCommande;
	
	
	private Integer pIdCommande;
	private Integer pIdProduit;
	
	private int nbPersonne;
	

	private ILigneCommandeDAO ligneCommandeDAO;

	
	// _____ Ctor ______//
	public GestionLigneCommandeBean() {
		ligneCommandeDAO = new LigneCommandeDAOImpl();
	}// end ctor
	
	
	// _____ Méthodes __//
	
	
	/**
	 * meth permet d'initialiser une ligne de commande
	 * appelée lors de l'ajout dans le panier 
	 */
	public int initLigneCommande (ActionEvent event) {
		
		UIParameter idProduit = (UIParameter) event.getComponent().findComponent("IdProduit");
		UIParameter prixProduit = (UIParameter) event.getComponent().findComponent("PrixProduit");
		int idProduitAdd = (int) idProduit.getValue();
		double prixProduitAdd = (double) prixProduit.getValue();
		
		LigneCommande ligneDeCommande = new LigneCommande(idProduitAdd, 1, prixProduitAdd);
		
		listeLigneCommande.add(ligneCommande);
		
		
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);
		session.setAttribute("listeLigneCommande", listeLigneCommande);
		
		
		return 1;
		
	}// end initLigneCommande
	
	
	/**
	 * meth pour récup la liste des lignes de commande
	 */
	public List<LigneCommande> findAllLigneCommandeBDD(){
		
		listeLigneCommande = ligneCommandeDAO.getAll();
		
		return listeLigneCommande;
		
	}// end findAllLigneCommandeBDD
	
	
	
	/**
	 * meth pour récup la liste des lignes de commande by doubleId
	 */
	public List<LigneCommande> findLigneCommandeDoubleId(){

		listeLigneCommandeDoubleId = ligneCommandeDAO.getByDoubleId(pIdCommande, pIdProduit);
		
		return listeLigneCommandeDoubleId;
		
	}// end findAllLigneCommandeBDD
	
	
	
	
	public void updateQuantitePrix(ActionEvent event) {

		UIParameter quantiteProduit = (UIParameter) event.getComponent().findComponent("QuantiteProduit");
		UIParameter idProduit = (UIParameter) event.getComponent().findComponent("IdProduit");
		UIParameter prixProduit = (UIParameter) event.getComponent().findComponent("PrixProduit");
		int quantiteAdd = (int) quantiteProduit.getValue();
		int idProduitAdd = (int) idProduit.getValue();
		double prixProduitAdd = (double) prixProduit.getValue();
		
		double prixTotal = quantiteAdd * prixProduitAdd;
		System.out.println("double prixTotal : " + prixTotal );
		LigneCommande ligneCommande = new LigneCommande(idProduitAdd, quantiteAdd, prixTotal);

		
		
	}


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
