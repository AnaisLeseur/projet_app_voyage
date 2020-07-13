package com.intiformation.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.intiformation.DAO.ILigneCommandeDAO;
import com.intiformation.DAO.LigneCommandeDAOImpl;
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
	
	
	
	public void updateQuantite(ActionEvent event) {
		
		
		
	}



}// end GestionLigneCommandeBean
