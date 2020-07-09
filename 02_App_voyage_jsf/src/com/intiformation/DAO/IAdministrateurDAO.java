package com.intiformation.DAO;

import com.intiformation.modeles.Administrateur;

public interface IAdministrateurDAO extends IGenerique<Administrateur> {
	
	// meth spé à administrateur
	public boolean isAdministrateurExists(String pIdentifiant, String pMdp);

}// end interface
