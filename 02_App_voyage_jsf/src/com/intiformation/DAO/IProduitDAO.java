package com.intiformation.DAO;

import com.intiformation.modeles.Produit;
import java.util.*;

/**
 * Interface DAO spécifique au 'produit'
 * hérite de IGenerique
 * 
 * @author hannahlevardon
 *
 */
public interface IProduitDAO extends IGenerique<Produit> {
	
	// Méthodes spécifiques au produit :
	
	
	/**
	 * Méthode pour afficher une liste de produit contenant un mot-clé
	 * @param pMotClé: doit être présent dans le produit pour l'afficher
	 * @return List<Produit> : liste des produit avec le pMotClé
	 */
	public List<Produit> getByKeyword(String pMotClé);
	
	
	
	/**
	 * Méthode pour afficher la liste des produits par catégorie
	 * @param idCategorie
	 * @return List<Produit> : liste des produits de la catégorie 
	 */
	public List<Produit> getByCategorie(Integer idCategorie);
	
	
	
	/**
	 * Méthode pour afficher la liste des produits sélectionnés dans le panier du client
	 * @return List<Produit> : la liste des produits avec 'selectionProduit=true'
	 */	
	public List<Produit> getProduitSelectionnes(boolean selectionProduit);
	
	
	
	/**
	 * Méthode pour récupérer la liste des produits sélectionnés dans le panier du client 
	 *    VIA l'ID du client et une vue (de la BDD) qui retourne d'autres listes : ligne de commande et commande
	 * 
	 * @return List<Produit> : la liste des produits du panier
	 */	
	public List<Produit> findCommandePourCreaAffichage(Integer idClient);
	

}// end interface
