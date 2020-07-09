package com.intiformation.DAO;

import com.intiformation.modeles.Produit;
import java.util.*;

/**
 * Interface DAO spécifique au produit
 * hérite de IGenerique
 * @author hannahlevardon
 *
 */
public interface IProduitDAO extends IGenerique<Produit> {
	
	// Méthodes spécifiques au produit :
	
	/**
	 * Méthode pour afficher une liste de produit contenant un mot-clé
	 * @param pMotClé: doit être présent dans le produit pour l'afficher
	 * @return
	 */
	public List<Produit> getByKeyword(String pMotClé);
	
	/**
	 * Méthode pour afficher la liste des produits par catégorie
	 * @param idCategorie
	 * @return
	 */
	public List<Produit> getByCategorie(Integer idCategorie);
	
	/**
	 * Méthode pour afficher la liste des produits sélectionnés
	 * @return
	 */	
	public List<Produit> getProduitSelectionnes(boolean selectionProduit);
	
	

}// end interface
