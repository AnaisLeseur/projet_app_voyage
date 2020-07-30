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
	 *            
	 * @return true si un seul client avec l'identifiant et le mot de passe existe, 
	 * false si plusieurs clients qui possèdent les memes identifiant et mot de passe ou si aucun client n'est trouvé         
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
	 * @return : client possédant l'identifiand et le mot de passe
	 */
	public Client getByIdAndMdp(String pIdentifiant, String pMdp);

}// end interface
