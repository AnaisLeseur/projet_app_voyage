package com.intiformation.modeles;


/**
 * modele de données pour une ligne de commande
 * mappé sur la table 'lignes_commandes' de la bdd 
 * 
 * @author vincent
 *
 */
public class LigneCommande {
	
	// ---Props ---
	private int commande_id;
	private int produit_id;
	private int quantite_ligne;
	private double prix_ligne;
	private int panier_id;

	
	// ---Ctors ---
	// ctor vide
	public LigneCommande() {
	}
	
	//ctor chargé complet
	public LigneCommande(int commande_id, int produit_id, int quantite_ligne, double prix_ligne, int panier_id) {
		this.commande_id = commande_id;
		this.produit_id = produit_id;
		this.quantite_ligne = quantite_ligne;
		this.prix_ligne = prix_ligne;
		this.panier_id = panier_id;
	}//end ctor chargé complet
	
	//ctor chargé sans id
	public LigneCommande(int produit_id, int quantite_ligne, double prix_ligne, int panier_id) {
		this.produit_id = produit_id;
		this.quantite_ligne = quantite_ligne;
		this.prix_ligne = prix_ligne;
		this.panier_id = panier_id;
	}//end ctor chargé sans id

	
	
	// ---meths ---
	// ---Getters/setters ---
	
	
	public int getCommande_id() {
		return commande_id;
	}

	public void setCommande_id(int commande_id) {
		this.commande_id = commande_id;
	}

	public int getProduit_id() {
		return produit_id;
	}

	public void setProduit_id(int produit_id) {
		this.produit_id = produit_id;
	}

	public int getQuantite_ligne() {
		return quantite_ligne;
	}

	public void setQuantite_ligne(int quantite_ligne) {
		this.quantite_ligne = quantite_ligne;
	}

	public double getPrix_ligne() {
		return prix_ligne;
	}

	public void setPrix_ligne(double prix_ligne) {
		this.prix_ligne = prix_ligne;
	}

	public int getPanier_id() {
		return panier_id;
	}

	public void setPanier_id(int panier_id) {
		this.panier_id = panier_id;
	}
	

}// end LigneCommande
