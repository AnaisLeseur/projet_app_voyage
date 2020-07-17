package com.intiformation.DAO;

import java.util.List;

import com.intiformation.modeles.LigneCommande;

public interface ILigneCommandeDAO extends IGenerique<LigneCommande>{
	
	// meth spé pour les lignes de commande
	
	
	/**
	 * permet de récup une ligne de commande par la combinaison des 2 PK (les params)
	 * @param pIdCommande : id de la commande
	 * @param pIdProduit : id du produit
	 * @return: l'obj LigneCommande
	 */
	public List<LigneCommande> getByDoubleId (Integer pIdCommande, Integer pIdProduit);
	
	
	/**
	 * permet de récup une ligne de commande par id de la commande
	 * @param pIdCommande : id de la commande
	 * @return: l'obj LigneCommande
	 */
	public List<LigneCommande> getByIdCommande (Integer pIdCommande);
	
	
	
	/**
	 * meth spécifique permet de supprimer de la bdd une ligne de commande par la combinaison des 2 PK (les params)
	 * @param pIdCommande : id de la commande
	 * @param pIdProduit : id du produit
	 * @return true si suppression ok sinon false
	 */
	public boolean deleteDoubleId (Integer pIdCommande, Integer pIdProduit);
	
	
	
	

}// end ILigneCommandeDAO
