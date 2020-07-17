package com.intiformation.DAO;

import com.intiformation.modeles.Client;

public interface IClientDAO extends IGenerique<Client> {
	
	// meth spé à client
	
	public boolean isClientExists(String pIdentifiant, String pMdp);
	
	public Client getByIdAndMdp(String pIdentifiant, String pMdp);

}// end interface
