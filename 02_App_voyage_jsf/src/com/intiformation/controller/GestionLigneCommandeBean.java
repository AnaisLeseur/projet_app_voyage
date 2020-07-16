package com.intiformation.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.primefaces.expression.SearchExpressionConstants;

import com.intiformation.DAO.ILigneCommandeDAO;
import com.intiformation.DAO.LigneCommandeDAOImpl;
import com.intiformation.modeles.Categorie;
import com.intiformation.modeles.LigneCommande;
import com.intiformation.modeles.Produit;


@ManagedBean(name = "GestionLigneCommandeBean")
@SessionScoped
public class GestionLigneCommandeBean implements Serializable {
	
	
	// _____ Props ______//
	private List<LigneCommande> listeLigneCommande;
	private List<LigneCommande> listeLigneCommandeDoubleId;
	private List<Produit> listePanier;
	private LigneCommande ligneCommande;
	
	HttpSession session;
	
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
	
	
	// _____ Méthodes __//
	
	
	
	
	
	
	/**
	 * meth permet d'initialiser une ligne de commande
	 * appelée lors de l'ajout dans le panier 
	 */
	public List<LigneCommande> initLigneCommande (ActionEvent event) {
		
		UIParameter idProduit = (UIParameter) event.getComponent().findComponent("IdProduit");
		UIParameter prixProduit = (UIParameter) event.getComponent().findComponent("PrixProduit");
		int idProduitAdd = (int) idProduit.getValue();
		double prixProduitAdd = (double) prixProduit.getValue();
		
		LigneCommande ligneDeCommande = new LigneCommande(idProduitAdd, 1, prixProduitAdd);
		
		listeLigneCommande.add(ligneDeCommande);
		
		
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);
		session.setAttribute("listeLigneCommande", listeLigneCommande);
		
		
		return listeLigneCommande;
		
	}// end initLigneCommande
	
	
	public List<LigneCommande> ReturnListeLigneCommande(){
		return listeLigneCommande;
		
	}
	
	
	public LigneCommande ReturnLigneCommande(){
		
		
		
		return ligneCommande;
		
	}
	
	
	
	
	
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
//		UIParameter stockProduit = (UIParameter) event.getComponent().findComponent("StockProduit");
		UIParameter uip2 = (UIParameter) event.getComponent().findComponent("selectModifIdLignePanier");
		
		int quantiteAdd = (int) quantiteProduit.getValue();
		int idProduitAdd = (int) idProduit.getValue();
//		int stockProduitAdd = (int) stockProduit.getValue();
		int selectModifIdLignePanier = (int) uip2.getValue();
		
		System.out.println("int selectModifIdLignePanier =" + selectModifIdLignePanier);
		
		
		
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);
		listeLigneCommande = (List<LigneCommande>) session.getAttribute("listeLigneCommande");
		listePanier = (List<Produit>) session.getAttribute("listePanier");
		
//		quantiteVoyage = listePanier.get(selectModifIdLignePanier).getQuantitéProduit();
		prixParVoyage = listePanier.get(selectModifIdLignePanier).getPrixProduit();
		
		
		
		System.out.println("double prix = listePanier.get(selectModifIdLignePanier).getPrixProduit() =" + prixParVoyage);
//		System.out.println("double prix = listePanier.get(selectModifIdLignePanier).getQuantitéProduit(); =" + quantiteVoyage);
		
//		if (quantiteAdd < stockProduitAdd) {
			
		prixTotal = quantiteAdd * prixParVoyage;
		System.out.println("double prixTotal : " + prixTotal );
		
		ligneCommande = new LigneCommande(idProduitAdd, quantiteAdd, prixTotal);
		
		listeLigneCommande.set(selectModifIdLignePanier, ligneCommande);
		for (LigneCommande ligneCommande : listeLigneCommande) {
			System.out.println("listeLigneCommande.set(selectModifIdLignePanier, ligneCommande):" 
					+ ligneCommande.getProduit_id() + " ;ligneCommande.getPrix_ligne() " + ligneCommande.getPrix_ligne() );
			
			session.setAttribute("listeLigneCommande", listeLigneCommande);
			
		}//end for
		
/*		}else {
			contextJSF = FacesContext.getCurrentInstance();
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					" la quantité demandée est trop importante", " - saisir une quantité inférieure"));
			
			
			
		}// end else
*/		
		
		
	}// end meth
	
	
	
	
	public double ReturnPrixTotal() {
		return prixParVoyage;
	
	}
	
	
	public double PrixTotal() {
		
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);
		listeLigneCommande = (List<LigneCommande>) session.getAttribute("listeLigneCommande");

			double sommePanier = listeLigneCommande.stream().mapToDouble(ligne -> ligne.getPrix_ligne()).sum();
		
		return sommePanier;
		
		
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
