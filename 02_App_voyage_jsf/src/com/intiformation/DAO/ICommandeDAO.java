package com.intiformation.DAO;

import java.util.List;

import com.intiformation.modeles.Commande;

public interface ICommandeDAO extends IGenerique<Commande> {
	
	
	// meth spé pour les commandes
	
	
	public Commande findIdMax();
	
	public List<Commande> findCommandeDuClient(Integer idClient);
	
	public List<Commande> findCommandePourCreaAffichage(Integer idClient);
	

}// end ICommandeDAO
