package com.intiformation.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.intiformation.DAO.IProduitDAO;
import com.intiformation.DAO.ProduitDAOImpl;
import com.intiformation.modeles.Produit;
import java.util.*;

@ManagedBean(name="GestionProduitBean")
@SessionScoped
public class GestionProduitBean implements Serializable {
	
	// _____ Props ______//
	
	private List<Produit> listeProduits;
	private Produit produit;
	
	IProduitDAO produitDAO;
	
	
	// _____ Ctor ______//

	public GestionProduitBean() {
			produitDAO = new ProduitDAOImpl();
	}//end ctor vide


	
	// _____ Méthodes ______//
	/**
	 * Récuperation de la liste des produits dans db via DAO
	 * Méthode utilisé par le composant <h:datatable> de accueil-client.xhtml
	 * pour afficher la liste des livres de la db
	 * @return
	 */
	
	public List<Produit> findAllProduitsBDD(){
		
		listeProduits = produitDAO.getAll();
		
		return listeProduits;
		
	}//end findAllProduitsBDD
	
	
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


	
	

}//end classe
