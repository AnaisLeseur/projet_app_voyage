package com.intiformation.modeles;

/**
 * modele de données pour un administrateur
 * mappé sur la table 'users' de la bdd
 * @author vincent
 *
 */
public class Administrateur {
	
	// ---Props ---
	private String id_user;
	private String mot_de_passe_user;
	private String nom_user;
	private boolean activated_user;
	
	
	
	// ---Ctors ---
	public Administrateur() {
	}// end ctor vide
	
	public Administrateur(String id_user, String mot_de_passe_user, String nom_user, boolean activated_user) {
		this.id_user = id_user;
		this.mot_de_passe_user = mot_de_passe_user;
		this.nom_user = nom_user;
		this.activated_user = activated_user;
	}
	
	public Administrateur(String id_user, String mot_de_passe_user, String nom_user) {
		this.id_user = id_user;
		this.mot_de_passe_user = mot_de_passe_user;
		this.nom_user = nom_user;
	}



	// ---meths ---
	// ---Getters/setters ---

	public String getId_user() {
		return id_user;
	}



	public void setId_user(String id_user) {
		this.id_user = id_user;
	}



	public String getMot_de_passe_user() {
		return mot_de_passe_user;
	}



	public void setMot_de_passe_user(String mot_de_passe_user) {
		this.mot_de_passe_user = mot_de_passe_user;
	}



	public String getNom_user() {
		return nom_user;
	}



	public void setNom_user(String nom_user) {
		this.nom_user = nom_user;
	}



	public boolean isActivated_user() {
		return activated_user;
	}



	public void setActivated_user(boolean activated_user) {
		this.activated_user = activated_user;
	}
	

}// end Administrateur
