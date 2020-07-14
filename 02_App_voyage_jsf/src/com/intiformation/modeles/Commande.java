package com.intiformation.modeles;

import java.sql.Date;

/**
 * modele de données pour une commande
 * mappé sur la table 'commandes' de la bdd 
 * 
 * @author vincent
 *
 */
public class Commande {
	
	// ---Props ---
	private int id_commande; 
	private Date date_commande;
	private int client_id;
	
	
	
	// ---Ctors ---
	// ctor vide
	public Commande() {
	}
	
	//ctor chargé complet
	public Commande(int id_commande, Date date_commande, int client_id) {
		this.id_commande = id_commande;
		this.date_commande = date_commande;
		this.client_id = client_id;
	}// end ctor chargé complet
	
	//ctor chargé sans id
	public Commande(Date date_commande, int client_id) {
		this.date_commande = date_commande;
		this.client_id = client_id;
	}// end ctor chargé sans id
	
	
	// ---meths ---
	// ---Getters/setters ---

	public int getId_commande() {
		return id_commande;
	}

	public void setId_commande(int id_commande) {
		this.id_commande = id_commande;
	}

	public Date getDate_commande() {
		return date_commande;
	}

	public void setDate_commande(Date date_commande) {
		this.date_commande = date_commande;
	}

	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	

}// end class commande 
