package com.intiformation.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Driver;

/**
 * utilitaire (= on aura des meth static) qui permet de récup une connexion vers la bdd 'db_gestion_voyage'
 * 		via l'API JDBc
 *
 */
public class ConnectionBDD_db_gestion_voyage {
	
	// Props  (pour factoriser le code: info de connexion à la bdd)
		private static final String URL_BDD = "jdbc:mysql://localhost:3306/db_gestion_voyage";
		private static final String USER_BDD = "root";
		private static final String USER_PASSWORD = "root";
		
	// prop : la connexion à retourner dans la meth 
	private static Connection connection;
	

	
	// CTORS de la class 
	
	// ctor en private pour interdire l'instantiation (création) d'obj a partir de cette classe

	private ConnectionBDD_db_gestion_voyage() {	
	}// end ctor
	
	
	
	
	// METHS de la class
	/**
	 * meth qui permet de récup et retourner une connexion vers la bdd via l'api jdbc
	 * @return la connexion physique vers la bdd
	 */
	public static Connection getInstance() {
		// vérif de la connexion (vérif de l'obj connexion: si créé ou non)
		if (connection == null) {
			// if = vrai --> l'obj connexion n'est pas encore crée => création via try/catch
			
			try { // sinon problème SQL exception
			
		// 1: enregistrement (chargement) du pilote jdbc de MySQL dans le DriverManager
		// -----------------------------------------------------------------------------
			// 1.1: instanciation (création) d'un obj du pilote 
	
			Driver piloteJdbc = new Driver();
			
			// 1.2 : enregistrement du pilote dans le java.sql.DriverMannager (gestionnaire de pilote)
			DriverManager.registerDriver(piloteJdbc); 
			
			
		// 2: récup d'une connexion vers la bdd via la meth stactic getConnection du DriverManager
		// ----------------------------------------------------------------------------------------
			
			/**<pre>
			 * la meth getConnection(url, user, password) prend en arguments: 
			 * 				- url de la bdd
			 * 						format: jdbc:mysql://<adresse de mysql>:<port de mysql>/<nom de la bdd>
			 * 						ici : 	jdbc:mysql://    localhost     :     3306      /db_gestion_voyage
			 * 				- nom utilisateur (identifiant) de la bdd : root 
			 * 				- MdP de la bdd : root 
			 * 
			 * </pre>
			 */
			
		// 2.1 arguments en dur 
		//	DriverManager.getConnection("jdbc:mysql://localhost:3306/db_gestion_voyage", "root", "root");
			
		// factorisation du code avec les constantes 
		 connection = DriverManager.getConnection(URL_BDD, USER_BDD, USER_PASSWORD);
			
			
		} catch (SQLException e) {
			System.out.println(" Erreur lors de la creation de la connexion vers la BDD");
			e.printStackTrace();
			
			
		}// end catch
		
	}// end if
		
		// if = false --> l'obj connexion est déjà crée => on le retourne	
		return connection;
		
	}// end getInstance
}// end class
