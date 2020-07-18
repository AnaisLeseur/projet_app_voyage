package com.intiformation.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.intiformation.DAO.CommandeDAOImpl;
import com.intiformation.DAO.ICommandeDAO;
import com.intiformation.DAO.ILigneCommandeDAO;
import com.intiformation.DAO.IProduitDAO;
import com.intiformation.DAO.LigneCommandeDAOImpl;
import com.intiformation.DAO.ProduitDAOImpl;
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
	private List<LigneCommande> listeLigneCommandeAll = new ArrayList<>();
	private List<LigneCommande> listeLigneCommandeDuClient = new ArrayList<>();
	private List<LigneCommande> listeLigneCommandeParCommande = new ArrayList<>();
	
	private List<Produit> listeProduitCommande = new ArrayList<>();
	private Produit produit;
	
	
	ICommandeDAO commandeDAO;
	ILigneCommandeDAO lignecommandeDAO;
	IProduitDAO produitDAO;
	
	private LigneCommande ligneCommande;
	
	int idClient;

	
	
	// _____ Ctor ______//
	
	public GestionCommandeBean() {
		commandeDAO = new CommandeDAOImpl();
		lignecommandeDAO = new LigneCommandeDAOImpl();
		produitDAO = new ProduitDAOImpl();
	}// end ctor vide
	
	
	// _____ Methodes ______//
	
	public List<Commande> findAllCommandeBDD() {

		listeAllCommandeBDD = commandeDAO.getAll();
		

		return listeAllCommandeBDD;

	}// end findAllProduitsBDD
	
	
	public List<LigneCommande> findAllLigneCommandePourToutesCommandes() {


		listeAllCommandeBDD = commandeDAO.getAll();
		
		for (Commande commande : listeAllCommandeBDD) {
			
			int idCommande = commande.getId_commande();
			listeLigneCommandeAll = lignecommandeDAO.getByIdCommande(idCommande);

			System.out.println("listeLigneCommande =" + listeLigneCommandeAll);
			
			listeLigneCommandeParCommande.addAll(listeLigneCommandeAll);	
			
		}//  end for
		return listeLigneCommandeParCommande;

	}// end findAllLigneCommandePourToutesCommandes
	
	

	public void findAllCommandeDuClient(ActionEvent event) {

		
		UIParameter uip = (UIParameter) event.getComponent().findComponent("clientID");
		idClient = (int) uip.getValue();
		System.out.println("int idClient: " + idClient);
		
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);
		
		
		listeCommandesDuClient = commandeDAO.findCommandeDuClient(idClient);
		for (Commande commande : listeCommandesDuClient) {
			
			int idCommande = commande.getId_commande();
			System.out.println("int idCommande = " + idCommande);
			listeLigneCommande = lignecommandeDAO.getByIdCommande(idCommande);
			
			System.out.println("listeLigneCommande =" + listeLigneCommande);
			
			listeLigneCommandeDuClient.addAll(listeLigneCommande);	
			
		}//  end for
		
		
		session.setAttribute("listeLigneCommandeDuClient", listeLigneCommandeDuClient);
		
//		return listeLigneCommandeDuClient;
		
	}// end findAllCommandeDuClient
	
	
	public List<LigneCommande> AfficheCommandeDuClient() {

		return listeLigneCommandeDuClient;
		
	}// end findAllCommandeDuClient
	
	
	

}// end GestionCommandeBean
