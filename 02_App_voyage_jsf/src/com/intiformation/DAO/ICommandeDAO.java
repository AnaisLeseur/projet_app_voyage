package com.intiformation.DAO;

import java.util.List;

import com.intiformation.modeles.Commande;


/**
 * Interface DAO spécifique pour 'Commande'
 * hérite de IGenerique 
 * 
 * @author vincent
 *
 */
public interface ICommandeDAO extends IGenerique<Commande> {
	
	
	// meth spé pour les commandes
	
	/**
	 * Methodes spécifique à 'Commande' afin de récupérer les informations de la dernière commande effectuée
	 *  (utilisée pour créer les lignes de commande lors de la validation d'une commande)
	 * 
	 * @return : la dernière commande passée = enregistrée dans la bdd
	 */
	public Commande findIdMax();
	
	
	/**
	 * Methode spécifique à 'Commande' afin de récupérer les informations des toutes les commandes du client
	 * 
	 * @param idClient
	 * 
	 * @return la liste des commande du client
	 */
	public List<Commande> findCommandeDuClient(Integer idClient);
	
	
	/**
	 * Méthode pour récupérer la liste de commande utilisées lors de l'affichage du panier du client 
	 *    VIA l'ID du client et une vue (de la BDD) qui retourne d'autres listes : lignes de commande et commande et produits
	 *    
	 * @return List<Commande> : la liste des commandes des produits contenus dans le panier
	 * ERREUR : il n'y a qu'une commande associée à un panier : un return <Commande> aurait été suffisant 
	 */	
	public List<Commande> findCommandePourCreaAffichage(Integer idClient);
	

}// end ICommandeDAO
