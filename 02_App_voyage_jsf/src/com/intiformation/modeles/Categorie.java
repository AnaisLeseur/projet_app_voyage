package com.intiformation.modeles;


/**
 * modele de données pour une catégorie
 * mappé sur la table 'categories' de la bdd 
 * 
 * @author vincent
 *
 */
public class Categorie {
	
	// ---Props ---
	private int id_categorie;
	private String nom_categorie;
	private String description_categorie;
	private String urlImageCategorie;
	
	
	
	// ---Ctors ---
	
	// ctor vide
	public Categorie() {
	}// end ctor vide
	
	// ctor chargé complet
	public Categorie(int id_categorie, String nom_categorie, String urlImageProduit, String description_categorie) {
		this.id_categorie = id_categorie;
		this.nom_categorie = nom_categorie;
		this.description_categorie = description_categorie;
		this.urlImageCategorie = urlImageProduit;
	}// end ctor chargé complet
	
	// ctor chargé sans id
	public Categorie(String nom_categorie, String description_categorie, String urlImageProduit) {
		this.nom_categorie = nom_categorie;
		this.description_categorie = description_categorie;
		this.urlImageCategorie = urlImageProduit;
	}// end ctor chargé sans id

	
	// ---meths ---
	// ---Getters/setters ---
	
	
	public int getId_categorie() {
		return id_categorie;
	}

	public void setId_categorie(int id_categorie) {
		this.id_categorie = id_categorie;
	}

	public String getNom_categorie() {
		return nom_categorie;
	}

	public void setNom_categorie(String nom_categorie) {
		this.nom_categorie = nom_categorie;
	}

	public String getDescription_categorie() {
		return description_categorie;
	}

	public void setDescription_categorie(String description_categorie) {
		this.description_categorie = description_categorie;
	}

	public String getUrlImageCategorie() {
		return urlImageCategorie;
	}

	public void setUrlImageCategorie(String urlImageCategorie) {
		this.urlImageCategorie = urlImageCategorie;
	}

	
	

}// end Categorie
