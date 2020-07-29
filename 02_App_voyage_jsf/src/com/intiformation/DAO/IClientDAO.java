package com.intiformation.DAO;

import com.intiformation.modeles.Client;

/**
 * <pre>
 * Interface DAO spécifique à un une un client
 * Hérite de IGenerique
 * </pre> 
 * @author hannahlevardon
 */
public interface IClientDAO extends IGenerique<Client> {
	
	/*__________ Méthodés spécifiques à une client __________*/ 
	
	
	/**
	 * Vérification de l'existence d'un client dans la database via ses identifiant
	 * et mot de passe
	 * 
	 * @param pIdentifiant:
	 *            identifiant à vérifier
	 * @param pMdp:
	 *            mot de passe à vérifier
	 */
	public boolean isClientExists(String pIdentifiant, String pMdp);
	
	
	/**
	 * Récupération d'un client dans la database via ses identifiant
	 * et mot de passe
	 * 
	 * @param pIdentifiant:
	 *            identifiant à vérifier
	 * @param pMdp:
	 *            mot de passe à vérifier
	 */
	public Client getByIdAndMdp(String pIdentifiant, String pMdp);

}// end interface
