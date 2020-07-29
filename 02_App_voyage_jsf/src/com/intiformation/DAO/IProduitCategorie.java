package com.intiformation.DAO;

import com.intiformation.modeles.ProduitCategorie;

/**
 * Interface DAO spécifique au 'ProduitCategorie'
 * hérite de IGenerique
 * 
 * @author hannahlevardon
 * 
 */
public interface IProduitCategorie extends IGenerique<ProduitCategorie>{
	
	
	/**
	 * Méthode pour trouver le lien entre un produit et une catégorie à partir de 
	 * l'id du produit et de l'id de la catégorie
	 * 
	 * @param pIdCategorie
	 * @param PIdProduit
	 * 
	 * @return <ProduitCategorie> : l'unique lien entre un produit et une catégorie
	 */
	public ProduitCategorie getByDoubleId(Integer pIdCategorie, Integer pIdProduit);
	
	
	/**
	 * methode spécifique qui permet de supprimer de la bdd un lien 
	 * entre un produit et une catégorie par la combinaison des 2 PK (les params)
	 * 
	 * @param pIdCategorie : id de la categorie
	 * @param pIdProduit : id du produit
	 * 
	 * @return true si suppression ok sinon false
	 */
	public boolean deleteByDoubleId(Integer pIdCategorie, Integer pIdProduit);
	
	
	/**
	 * methode spécifique qui permet d'ajouter un lien entre un produit et une catégorie
	 * par la combinaison des 2 PK (les params)
	 * 
	 * @param IDcategorie : id de la categorie
	 * @param IdProduit : id du produit
	 * 
	 * @return true si ajout ok sinon false
	 */
	public boolean addVersion2(int IDcategorie,int IdProduit) ;
	
	
}//end interface
