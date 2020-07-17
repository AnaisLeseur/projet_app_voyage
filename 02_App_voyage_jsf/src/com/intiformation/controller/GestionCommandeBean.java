package com.intiformation.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

import com.intiformation.DAO.CommandeDAOImpl;
import com.intiformation.DAO.ICommandeDAO;
import com.intiformation.DAO.ILigneCommandeDAO;
import com.intiformation.DAO.LigneCommandeDAOImpl;
import com.intiformation.modeles.Commande;
import com.intiformation.modeles.LigneCommande;
import com.intiformation.modeles.Produit;

@ManagedBean(name = "GestionCommandeBean")
@SessionScoped
public class GestionCommandeBean {
	
	
	// _____ Props ______//
	private List<Commande> listeCommandesDuClient = new ArrayList<>();
	private List<Commande> listeAllCommandeBDD = new ArrayList<>();
	private List<LigneCommande> listeLigneCommande = new ArrayList<>();
	private List<LigneCommande> listeLigneCommandeDuClient = new ArrayList<>();
	
	ICommandeDAO commandeDAO;
	ILigneCommandeDAO lignecommandeDAO;
	
	private LigneCommande ligneCommande;

	
	
	// _____ Ctor ______//
	
	public GestionCommandeBean() {
		commandeDAO = new CommandeDAOImpl();
		lignecommandeDAO = new LigneCommandeDAOImpl();
	}// end ctor vide
	
	
	// _____ Methodes ______//
	
	public List<Commande> findAllCommandeBDD() {

		listeAllCommandeBDD = commandeDAO.getAll();
		

		return listeAllCommandeBDD;

	}// end findAllProduitsBDD
	
	
	public List<LigneCommande> findAllCommandeDuClient(ActionEvent event) {

		
		UIParameter uip = (UIParameter) event.getComponent().findComponent("clientID");
		int idClient = (int) uip.getValue();
		System.out.println("int idClient: " + idClient);
		
		listeCommandesDuClient = commandeDAO.findCommandeDuClient(idClient);
		for (Commande commande : listeCommandesDuClient) {
			
			int idCommande = commande.getId_commande();
			System.out.println("int idCommande = " + idCommande);
			listeLigneCommande = lignecommandeDAO.getByIdCommande(idCommande);
			
			System.out.println("listeLigneCommande =" + listeLigneCommande);
			
			listeLigneCommandeDuClient.addAll(listeLigneCommande);	
			
		}//  end for
		return listeLigneCommandeDuClient;
		
	}// end findAllCommandeDuClient
	

}// end GestionCommandeBean
