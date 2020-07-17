package com.intiformation.DAO;

import com.intiformation.modeles.Commande;

public interface ICommandeDAO extends IGenerique<Commande> {
	
	
	// meth spé pour les commandes
	
	
	public Commande findIdMax();
	
	
	

}// end ICommandeDAO
