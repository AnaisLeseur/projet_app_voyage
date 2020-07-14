package com.intiformation.modeles;

public class ProduitCategorie {
	
	// _____ Props ______//
	private int categorie_id;
	private int produit_id; 
	
	
	// _____ Ctor ______//
	
	public ProduitCategorie() {
	}//end ctor vide
	
	
		
	public ProduitCategorie(int categorie_id, int produit_id) {
		super();
		this.categorie_id = categorie_id;
		this.produit_id = produit_id;

	}// end ctor chargé
	
	
	// _____ Getter / Setter ______//

	public int getProduit_id() {
		return produit_id;
	}

	public void setProduit_id(int produit_id) {
		this.produit_id = produit_id;
	}

	public int getCategorie_id() {
		return categorie_id;
	}

	public void setCategorie_id(int categorie_id) {
		this.categorie_id = categorie_id;
	}

}//end class