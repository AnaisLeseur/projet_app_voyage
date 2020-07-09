package com.intiformation.modeles;


/**
 * modele de données pour un client
 * mappé sur la table de la bdd
 * @author vincent
 *
 */
public class Client {
	
	// ---Props ---
	private int id_client;
	private String identifiant_client;
	private String mot_de_passe_client;
	private String nom_client;
	private String prenom_client;
	private String adresse_client;
	private String email_client;
	private String tel_client;
	private boolean activated_client;

	
	// ---Ctors ---
	public Client() {
	}// end ctor vide
	
	public Client(int id_client, String identifiant_client, String mot_de_passe_client, String nom_client,
			String prenom_client, String adresse_client, String email_client, String tel_client,
			boolean activated_client) {
		this.id_client = id_client;
		this.identifiant_client = identifiant_client;
		this.mot_de_passe_client = mot_de_passe_client;
		this.nom_client = nom_client;
		this.prenom_client = prenom_client;
		this.adresse_client = adresse_client;
		this.email_client = email_client;
		this.tel_client = tel_client;
		this.activated_client = activated_client;
	}
	public Client(String identifiant_client, String mot_de_passe_client, String nom_client,
			String prenom_client, String adresse_client, String email_client, String tel_client,
			boolean activated_client) {
		this.identifiant_client = identifiant_client;
		this.mot_de_passe_client = mot_de_passe_client;
		this.nom_client = nom_client;
		this.prenom_client = prenom_client;
		this.adresse_client = adresse_client;
		this.email_client = email_client;
		this.tel_client = tel_client;
		this.activated_client = activated_client;
	}
	
	
	
	// ---meths ---
	// ---Getters/setters ---
	

	public int getId_client() {
		return id_client;
	}

	public void setId_client(int id_client) {
		this.id_client = id_client;
	}

	public String getIdentifiant_client() {
		return identifiant_client;
	}

	public void setIdentifiant_client(String identifiant_client) {
		this.identifiant_client = identifiant_client;
	}

	public String getMot_de_passe_client() {
		return mot_de_passe_client;
	}

	public void setMot_de_passe_client(String mot_de_passe_client) {
		this.mot_de_passe_client = mot_de_passe_client;
	}

	public String getNom_client() {
		return nom_client;
	}

	public void setNom_client(String nom_client) {
		this.nom_client = nom_client;
	}

	public String getPrenom_client() {
		return prenom_client;
	}

	public void setPrenom_client(String prenom_client) {
		this.prenom_client = prenom_client;
	}

	public String getAdresse_client() {
		return adresse_client;
	}

	public void setAdresse_client(String adresse_client) {
		this.adresse_client = adresse_client;
	}

	public String getEmail_client() {
		return email_client;
	}

	public void setEmail_client(String email_client) {
		this.email_client = email_client;
	}

	public String getTel_client() {
		return tel_client;
	}

	public void setTel_client(String tel_client) {
		this.tel_client = tel_client;
	}

	public boolean isActivated_client() {
		return activated_client;
	}

	public void setActivated_client(boolean activated_client) {
		this.activated_client = activated_client;
	}
	

	
	

}// end class client
