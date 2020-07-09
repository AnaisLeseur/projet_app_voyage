package com.intiformation.DAO;

import com.intiformation.modeles.Client;

public interface IClientDAO extends IGenerique<Client> {
	
	// meth spé à client
	
	public boolean isClientExists(String pMail, String pMdp);

}// end interface
