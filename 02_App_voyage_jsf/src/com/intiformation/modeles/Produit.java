package com.intiformation.modeles;


/**
 * Modèle pour une produit Mappé sur la table 'produits' dans la base de données
 * db_gestion_voyage
 * 
 * @author hannahlevardon
 *
 */
public class Produit {

	// _____ Props ______//

	private int idProduit;
	private String nomProduit;
	private String descriptionProduit;
	private double prixProduit;
	private int quantitéProduit;
	private boolean selectionProduit;
	private String urlImageProduit;

	// _____ Ctor ______//

	/**
	 * Ctor vide
	 */
	public Produit() {
	}// end ctor chargé

	public Produit(int idProduit, String nomProduit, String descriptionProduit, double prixProduit, int quantitéProduit,
			boolean selectionProduit, String urlImageProduit) {
		super();
		this.idProduit = idProduit;
		this.nomProduit = nomProduit;
		this.descriptionProduit = descriptionProduit;
		this.prixProduit = prixProduit;
		this.quantitéProduit = quantitéProduit;
		this.selectionProduit = selectionProduit;
		this.urlImageProduit = urlImageProduit;
	} // end ctor chargé

	public Produit(String nomProduit, String descriptionProduit, double prixProduit, int quantitéProduit,
			boolean selectionProduit, String urlImageProduit) {
		super();
		this.nomProduit = nomProduit;
		this.descriptionProduit = descriptionProduit;
		this.prixProduit = prixProduit;
		this.quantitéProduit = quantitéProduit;
		this.selectionProduit = selectionProduit;
		this.urlImageProduit = urlImageProduit;
	}// end ctor chargé sans id

	// _____ Getter / Setter ______//

	public int getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(int idProduit) {
		this.idProduit = idProduit;
	}

	public String getNomProduit() {
		return nomProduit;
	}

	public void setNomProduit(String nomProduit) {
		this.nomProduit = nomProduit;
	}

	public String getDescriptionProduit() {
		return descriptionProduit;
	}

	public void setDescriptionProduit(String descriptionProduit) {
		this.descriptionProduit = descriptionProduit;
	}

	public double getPrixProduit() {
		return prixProduit;
	}

	public void setPrixProduit(double prixProduit) {
		this.prixProduit = prixProduit;
	}

	public int getQuantitéProduit() {
		return quantitéProduit;
	}

	public void setQuantitéProduit(int quantitéProduit) {
		this.quantitéProduit = quantitéProduit;
	}

	public boolean isSelectionProduit() {
		return selectionProduit;
	}

	public void setSelectionProduit(boolean selectionProduit) {
		this.selectionProduit = selectionProduit;
	}

	public String getUrlImageProduit() {
		return urlImageProduit;
	}

	public void setUrlImageProduit(String urlImageProduit) {
		this.urlImageProduit = urlImageProduit;
	}

}// end classe
