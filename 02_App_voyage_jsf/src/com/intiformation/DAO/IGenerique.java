package com.intiformation.DAO;

import java.sql.Connection;
import java.util.List;

import com.intiformation.tool.ConnectionBDD_db_gestion_voyage;


/**
 * interface générique de base de la couche DAO. 
 * interface de base pour tout type de DAO
 * l'interface déclare les meth à exposer dans la DAO.
 * 
 * les methode déclarées seront communes à tous types de DAO
 */
public interface IGenerique <T> {
	
	// déclarer = récup d'une connexion vers la bdd via l'utilitaire ConnectionBDD_db_gestion_voyage
	public Connection connexion = ConnectionBDD_db_gestion_voyage.getInstance();
	
	
	// déclaration des methodes à exposer dans la DAO
	
		/**
		 * permet d'ajouter dans la bdd, tout type d'obj
		 * @param t: obj à ajouter
		 * @return true si ajout ok sinon false
		 */
		public boolean add(T t);
		
		/**
		 * meth gle permet de modifier dans la bdd, tout type d'obj
		 * @param t: obj à modifier 
		 * @return true si modif ok sinon false
		 */
		public boolean update(T t);
		
		/**
		 * meth gle permet de supprimer de la bdd, tout type d'obj
		 * @param id : id (PK) de l'obj à supprimer
		 * @return true si suppression ok sinon false
		 */
		public boolean delete(Integer id);
		
		/**
		 * meth gle permet de récupérer la liste de tous types d'obj de la bdd
		 * @return : la liste des obj
		 */
		public List<T> getAll();
		
		/**
		 * permet de récup tt type d'obj via son id (PK) dans la bdd
		 * @param id : id de l'obj à récup
		 * @return: l'obj
		 */
		public T getById (Integer id);
		

	}// end interface