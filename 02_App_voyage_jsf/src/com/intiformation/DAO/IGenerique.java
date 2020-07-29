package com.intiformation.DAO;

import java.sql.Connection;
import java.util.List;

import com.intiformation.tool.ConnectionBDD_db_gestion_voyage;


/**
 * interface générique de base de la couche DAO. 
 * interface de base pour tous type de DAO.
 * l'interface déclare les methodes à exposer dans la DAO.
 * les methode déclarées seront communes à tous types de DAO.
 * interface de type <T> car type générique
 */
public interface IGenerique <T> {
	
	// déclarer = récup d'une connexion vers la bdd via l'utilitaire ConnectionBDD_db_gestion_voyage
	public Connection connexion = ConnectionBDD_db_gestion_voyage.getInstance();
	
	
	// déclaration des methodes à exposer dans la DAO
	
		/**
		 * methode qui permet d'ajouter dans la bdd tout type d'objet
		 * @param t: objet à ajouter
		 * @return true si ajout ok, sinon false
		 */
		public boolean add(T t);
		
		
		
		/**
		 * methode générale qui permet de modifier dans la bdd tout type d'objet
		 * @param t: objet à modifier 
		 * @return true si modification ok, sinon false
		 */
		public boolean update(T t);
		
		
		
		/**
		 *  methode générale qui permet de supprimer de la bdd tout type d'objet
		 * @param id : id (PK) de l'objet à supprimer
		 * @return true si suppression ok, sinon false
		 */
		public boolean delete(Integer id);
		
		
		
		/**
		 * methode générale qui permet de récupérer la liste de tous types d'objets de la bdd
		 * @return : la liste des objets
		 */
		public List<T> getAll();
		
		
		
		/**
		 * methode générale qui permet de récupérer tout type d'objet via son id (PK) dans la bdd
		 * @param id : id de l'objet à récupérer
		 * @return: l'objet
		 */
		public T getById (Integer id);
		

	}// end interface

