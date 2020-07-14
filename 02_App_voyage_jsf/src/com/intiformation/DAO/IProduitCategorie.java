package com.intiformation.DAO;

import com.intiformation.modeles.ProduitCategorie;

public interface IProduitCategorie extends IGenerique<ProduitCategorie>{
	/**
	 * Méthode pour trouver une ligne de commande a partir de l'id du produit et de la catégorie
	 * @param pIdCategorie
	 * @param PIdProduit
	 * @return
	 */
	public ProduitCategorie getByDoubleId(Integer pIdCategorie, Integer pIdProduit);
	
	public boolean deleteByDoubleId(Integer pIdCategorie, Integer pIdProduit);

}//end interface
