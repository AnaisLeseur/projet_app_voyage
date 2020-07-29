package com.intiformation.DAO;

import com.intiformation.modeles.Administrateur;

/**
 * <pre>
 * Interface DAO spécifique à un administrateur
 * Hérite de IGenerique
 * </pre> 
 * @author hannahlevardon
 */

public interface IAdministrateurDAO extends IGenerique<Administrateur> {
	
	/*__________ Méthodés spécifiques à l'adminitrateur __________*/ 
	
	/**
	 * Vérification de l'existence de l'administrateur dans bdd via ses identifiants
	 * et mot de passe
	 * @param pIdentifiant
	 * @param pMdp
	 * @return
	 */
	public boolean isAdministrateurExists(String pIdentifiant, String pMdp);

}// end interface
