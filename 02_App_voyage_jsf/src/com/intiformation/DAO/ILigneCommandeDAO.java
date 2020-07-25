package com.intiformation.DAO;

import java.util.List;

import com.intiformation.modeles.LigneCommande;


/**
 * Interface DAO spécifique aux 'Lignes de Commande'
 * hérite de IGenerique
 * 
 * @author vincent
 *
 */
public interface ILigneCommandeDAO extends IGenerique<LigneCommande>{
	
	// meth spé pour les lignes de commande
	
	
	/**
	 * permet de récupérer une ligne de commande par la combinaison des 2 PK (les params)
	 * 
	 * @param pIdCommande : id de la commande
	 * @param pIdProduit : id du produit
	 * 
	 * @return: l'objet LigneCommande (erreur : retourne une liste => liste d'un objet)
	 */
	public List<LigneCommande> getByDoubleId (Integer pIdCommande, Integer pIdProduit);
	
	
	/**
	 * permet de récuperer toutes les lignes de commande avec l'id de la commande
	 * 
	 * @param pIdCommande : id de la commande
	 * 
	 * @return: la liste des lignes de commandes associées à la commande
	 */
	public List<LigneCommande> getByIdCommande (Integer pIdCommande);
	
	
	
	/**
	 * meth spécifique permet de supprimer de la bdd une ligne de commande par la combinaison des 2 PK (les params)
	 * 
	 * @param pIdCommande : id de la commande
	 * @param pIdProduit : id du produit
	 * 
	 * @return true si suppression ok sinon false
	 */
	public boolean deleteDoubleId (Integer pIdCommande, Integer pIdProduit);
	
	
	/**
	 * Méthode pour récupérer la liste des lignes de commande utilisées lors de l'affichage du panier du client 
	 *    VIA l'ID du client et une vue (de la BDD) qui retourne d'autres listes : lignes de commande et commande et produits
	 *    
	 * @return List<LigneCommande> : la liste des lignes de commande des produits contenus dans le panier
	 */	
	public List<LigneCommande> findCommandePourCreaAffichage(Integer idClient);
	

}// end ILigneCommandeDAO
