package com.intiformation.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.intiformation.modeles.LigneCommande;

/**
 * Implémentation concrète de la DAO pour 'lignes_commandes'.
 * 'lignes_commandes' : table d'association entre un produit et une commande.
 * classe qui implemente l'interface ILigneCommandeDAO
 * 
 * @author vincent
 *
 */
public class LigneCommandeDAOImpl implements ILigneCommandeDAO {
	
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	
	/* ================================================== */	
	
	/**
	 * AJOUTER UNE LIGNE DE COMMANDE 
	 * @param pLigneCommande : ligne de commande à ajouter
	 * @return boolean: si ajout ok ou non 
	 */
	@Override
	public boolean add(LigneCommande pLigneCommande) {
		try {
			
			// prepared statement + requete SQL
			ps = this.connexion.prepareStatement("insert into lignes_commandes"
					+ "(commande_id, produit_id, quantite_ligne, prix_ligne)"
					+ "values (?, ?, ?, ?)");
			
			// passage de params
			ps.setInt(1, pLigneCommande.getCommande_id());
			ps.setInt(2, pLigneCommande.getProduit_id());
			ps.setInt(3, pLigneCommande.getQuantite_ligne());
			ps.setDouble(4, pLigneCommande.getPrix_ligne());
			
			// executeUpdate
			int verif = ps.executeUpdate();
			
			return (verif == 1);			
			
		} catch (SQLException e) {
			System.out.println("LigneCommandeDAOImpl : erreur add()");
			e.printStackTrace();
		}finally {
			try {
				
				// fermeture des ressources
				ps.close();
				
			}catch (Exception e) {
			}// end catch
		}// end finally
		
		return false;
	}// end add


	/* ================================================== */
	
	/**
	 * MODIFIER UNE LIGNE DE COMMANDE 
	 * @param pLigneCommande : ligne de commande à modifier
	 * @return boolean: si modification ok ou non 
	 */
	@Override
	public boolean update(LigneCommande pLigneCommande) {
		try {
			ps = this.connexion.prepareStatement("update lignes_commandes set "
					+ "quantite_ligne=?, prix_ligne=? where commande_id=? and produit_id=?") ;

			ps.setInt(1, pLigneCommande.getQuantite_ligne());
			ps.setDouble(2, pLigneCommande.getPrix_ligne());
			ps.setInt(3, pLigneCommande.getCommande_id());
			ps.setInt(4, pLigneCommande.getProduit_id());
			
			int verif = ps.executeUpdate();
				
			return (verif == 1);

		} catch (SQLException e) {
			System.out.println("LigneCommandeDAOImpl : erreur update()");
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			}catch (Exception e) {
			}// end catch
		}// end finally
		return false;
	}// end update

	
	
	/* ================================================== */
	
	/**
	 * RECUPERER LA LISTE DE TOUTES LES LIGNES DE COMMANDES 
	 * @return List<LigneCommande> listeligneCommandeBdd : la liste de toutes les lignes de commandes 
	 * 
	 */
	@Override
	public List<LigneCommande> getAll() {
		try {
			
			ps = this.connexion.prepareStatement("select * from lignes_commandes");
			
			rs = ps.executeQuery();
			
			List<LigneCommande> listeligneCommandeBdd = new ArrayList<>();
			LigneCommande ligneCommande = null;
			
			while (rs.next()) {
				ligneCommande = new LigneCommande(rs.getInt(1), 
													rs.getInt(2), 
													rs.getInt(3), 
													rs.getDouble(4));
				
				listeligneCommandeBdd.add(ligneCommande);
				
			}// end while
			
			return listeligneCommandeBdd;
			
			
		} catch (SQLException e) {
			System.out.println("Dans LigneCommandeDAOImpl : erreur getAll()");
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				ps.close();
			}catch (Exception e) {
				// TODO: handle exception
			}// end catch
		}// end finally
		return null;
	}// end getAll


	
	/* ================================================== */

	/**
	 * RECUPERER UNE LIGNE DE COMMANDE
	 * @param pIdCommande : id de la commande 
	 * @param pIdProduit : id du produit
	 * 
	 * @return List<LigneCommande> listeligneCommandeByDbleId : liste des lignes de commande avec pIdCommande et pIdProduit
	 */
	
// ERREUR sur le return : devrait être <LigneCommande> car il n'y a qu'une ligne de commande avec pIdCommande et pIdProduit
// ICI return List<LigneCommande> listeligneCommandeByDbleId : composée d'1 seul element <LigneCommande>
	
	@Override
	public List<LigneCommande> getByDoubleId(Integer pIdCommande, Integer pIdProduit) {
		try {
			
			ps = this.connexion.prepareStatement("select * from lignes_commandes where where commande_id=? and produit_id=?");
			
			ps.setInt(1, pIdCommande);
			ps.setInt(2, pIdProduit);
			
			rs = ps.executeQuery();
			
			List<LigneCommande> listeligneCommandeByDbleId = new ArrayList<>();
			LigneCommande ligneCommande = null;
				
			while (rs.next()) {
				ligneCommande = new LigneCommande(rs.getInt(1), 
												rs.getInt(2), 
												rs.getInt(3), 
												rs.getDouble(4)); 		
				
				listeligneCommandeByDbleId .add(ligneCommande);
			}// end while
			
			return listeligneCommandeByDbleId ;
			
		} catch (SQLException e) {
			System.out.println("LigneCommandeDAOImpl : erreur getByDoubleId()");
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			}catch (Exception e) {
				// TODO: handle exception
			}// end catch
		}// end finally
		return null;
	}// end getByDoubleId
	
	
	/* ================================================== */

	/**
	 * SUPPRIMER UNE LIGNE DE COMMANDE via ses 2 PK
	 * 
	 * @param pIdCommande : id de la commande
	 * @param pIdProduit : id du produit
	 * 
	 * @return boolean: si la suppression ok ou non 
	 */
	@Override
	public boolean deleteDoubleId(Integer pIdCommande, Integer pIdProduit) {
		try {
			
			ps = this.connexion.prepareStatement("delete From lignes_commandes where where commande_id=? and produit_id=?");
			
			ps.setInt(1, pIdCommande);
			ps.setInt(2, pIdProduit);
			
			int verif = ps.executeUpdate();
				
			return (verif == 1);

		} catch (SQLException e) {
			System.out.println("LigneCommandeDAOImpl : erreur delete()");
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			}catch (Exception e) {
				// TODO: handle exception
			}// end catch
		}// end finally
		return false;
	}// end deleteDoubleId
	
	
	
	/* ================================================== */
	
	/**
	 * RECUPERER LA LISTE DES LIGNES DE COMMANDE ASSOCIEES A UNE COMMANDE
	 * @param pIdCommande : id de la commande 
	 * 
	 * @return List<LigneCommande> listeligneCommandeByIdCommande : liste des lignes de commande avec pIdCommande
	 * 
	 */
	@Override
	public List<LigneCommande> getByIdCommande(Integer pIdCommande) {
		try {
			
			ps = this.connexion.prepareStatement("select * from lignes_commandes where commande_id=?");
			
			ps.setInt(1, pIdCommande);

			rs = ps.executeQuery();
			
			List<LigneCommande> listeligneCommandeByIdCommande = new ArrayList<>();
			LigneCommande ligneCommande = null;
				
			while (rs.next()) {
				ligneCommande = new LigneCommande(rs.getInt(1), 
												rs.getInt(2), 
												rs.getInt(3), 
												rs.getDouble(4)); 		
				
				listeligneCommandeByIdCommande .add(ligneCommande);
			}// end while
			
			return listeligneCommandeByIdCommande ;
			
		} catch (SQLException e) {
			System.out.println("LigneCommandeDAOImpl : erreur getByIdCommande()");
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			}catch (Exception e) {
				// TODO: handle exception
			}// end catch
		}// end finally
		return null;
	}// end getByIdCommande
	
	
	/* ================================================== */
	
	/**
	 * RECUPERER LA LISTE DES LIGNES DE COMMANDE "SELECTIONNES" PAR LE CLIENT ( via les produits ajoutés au panier)
	 *  		via une vue de la bdd
	 * ( cette vue permet aussi de récupérer d'autres infos pour afficher un récapitulatif complet avant le paiement: lignes de commande, produits...) 
	 * 
	 * @param idClient : id du client => la récupération des informations se fait a partir de l'id du client
	 * 
	 * @return List<LigneCommande> listeLignesCommandeDuClient : liste des lignes de commande que le client à dans son panier via les produits ajoutés
	 */
	@Override
	public List<LigneCommande> findCommandePourCreaAffichage(Integer idClient) {
		try {
			ps = this.connexion.prepareStatement("select * from vieu_commande_client_clients where id_client=?");
			
			ps.setInt(1, idClient);
			
			rs = ps.executeQuery();
			
			List<LigneCommande> listeLignesCommandeDuClient = new ArrayList<>();
			LigneCommande lignecommande = null; 
			
			while (rs.next()) {
				lignecommande = new LigneCommande(rs.getInt(20), rs.getInt(21), rs.getInt(22), rs.getDouble(23));
							
				listeLignesCommandeDuClient.add(lignecommande);
					
			}//end while
			
			return listeLignesCommandeDuClient; 
			
		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode findCommandePourCreaAffichage() de LigneCommandeDAOImpl ...");
			e.printStackTrace();
		}finally {		
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end catch
		}//end finally
		return null;
	}// end findCommandePourCreaAffichage
	
	
	
	/* ================================================== */
	
// METH NON UTILISEES
	
	
	/**
	 * pour supprimer une ligne de commande : ne peut pas utilisée car besoin de 2 id => Cf meth deleteDoubleId
	 */
	@Override
	public boolean delete(Integer pidLigneCommande) {
		return false;
	}// end delete
	
	
	/**
	 * pour récupérer une ligne de commande : ne peut pas utilisée car besoin de 2 id => Cf meth getByDoubleId
	 */
	@Override
	public LigneCommande getById(Integer pidLigneCommande) {
		// TODO Auto-generated method stub
		return null;
	}// end getById


}// end class
