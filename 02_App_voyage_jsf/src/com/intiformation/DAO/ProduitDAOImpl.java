package com.intiformation.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.intiformation.modeles.LigneCommande;
import com.intiformation.modeles.Produit;

/**
 * Implémentation concrète de la DAO pour un produit
 * 
 * @author hannahlevardon
 *
 */
public class ProduitDAOImpl implements IProduitDAO {

	private PreparedStatement ps = null;
	private ResultSet rs = null;

	/**
	 * AJOUTER UN PRODUIT
	 */
	@Override
	public boolean add(Produit pProduit) {

		try {
			ps = this.connexion.prepareStatement(
					"INSERT into produits(nom_produit, description_produit, prix_produit, quantite_produit, selectionne_produit, image_produit ) "
							+ "VALUES (?,?,?,?,?,?)");

			ps.setString(1, pProduit.getNomProduit());
			ps.setString(2, pProduit.getDescriptionProduit());
			ps.setDouble(3, pProduit.getPrixProduit());
			ps.setInt(4, pProduit.getQuantitéProduit());
			ps.setBoolean(5, pProduit.isSelectionProduit());
			ps.setString(6, pProduit.getUrlImageProduit());

			int verifAjout = ps.executeUpdate();

			return verifAjout == 1;

		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode add() de ProduitDAOimpl ...");
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // end catch
		} // end finally

		return false;

	}// end add()

	/* ================================================== */

	/**
	 * MODIFIER UN PRODUIT
	 */
	@Override
	public boolean update(Produit pProduit) {

		try {
			ps = this.connexion.prepareStatement("update produits set nom_produit=? , description_produit=?, prix_produit=? ,"
					+ "quantite_produit= ?, selectionne_produit=?, image_produit= ? where id_produit= ?");
			
			ps.setString(1, pProduit.getNomProduit());
			ps.setString(2, pProduit.getDescriptionProduit());
			ps.setDouble(3, pProduit.getPrixProduit());
			ps.setInt(4, pProduit.getQuantitéProduit());
			ps.setBoolean(5, pProduit.isSelectionProduit());
			ps.setString(6, pProduit.getUrlImageProduit());
			ps.setInt(7, pProduit.getIdProduit());
			
			int verifUpdate = ps.executeUpdate();
			
			return verifUpdate == 1; 
		
			
		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode update() de ProduitDAOimpl ...");
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end catch
		}//end finally

		return false;

	}// end update()

	/* ================================================== */
	
	/**
	 *  SUPPRIMER UN PRODUIT
	 */
	@Override
	public boolean delete(Integer idProduit) {
		
		try {
			ps = this.connexion.prepareStatement("delete from produits where id_produit = ?");
			
			ps.setInt(1, idProduit);
			
			int verifDelete =  ps.executeUpdate();
			
			return verifDelete == 1; 
			
		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode delete() de ProduitDAOimpl ...");
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}//end catch
		}//end finally	
		return false;
		
	}//end delete()
	
	/* ================================================== */
	
	/**
	 * AFFICHER TOUS LES PRODUITS
	 */
	@Override
	public List<Produit> getAll() {
		
		try {
			ps = this.connexion.prepareStatement("select * from produits");
			
			rs = ps.executeQuery();
			
			List<Produit> listeProduitsDB = new ArrayList<>();
			Produit produit = null; 
			
			while (rs.next()) {
				produit = new Produit(rs.getInt(1), rs.getString(2), rs.getString(3), 
						rs.getDouble(4), rs.getInt(5), rs.getBoolean(6), rs.getString(7));
							
				listeProduitsDB.add(produit);
					
			}//end while
			
			return listeProduitsDB; 
			
		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode getAll() de ProduitDAOimpl ...");
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
	}//end getAll()
	
	
	/* ================================================== */
	
	/**
	 * AFFICHER PRODUIT PAR SON ID
	 */
	@Override
	public Produit getById(Integer idProduit) {
		
		try {
			ps = this.connexion.prepareStatement("select * from produits where id_produit = ?");
			
			ps.setInt(1, idProduit);
			
			rs = ps.executeQuery(); 
			
			Produit produit = null; 
			
			rs.next();
			
			produit = new Produit(rs.getInt(1), rs.getString(2), rs.getString(3), 
					rs.getDouble(4), rs.getInt(5), 
					rs.getBoolean(6), rs.getString(7));
			
			return produit;
			
		} catch (SQLException e) {
			
			System.out.println("... Erreur lors de la méthode getById() de ProduitDAOimpl ...");
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}//end catch
		}//end finally
		
		return null;
	}//end getById
	
	
	/* ================================================== */

	/**
	 * AFFICHER LA LISTE DES PRODUITS CONTENANT UN MOT-CLE
	 */
	@Override
	public List<Produit> getByKeyword(String pMotClé) {
		
		pMotClé = pMotClé.replace("%", "!%");
		
		try {
			ps = this.connexion.prepareStatement("select * from produits where nom_produit like ? or description_produit like ? ");
			
			ps.setString(1,"%" + pMotClé + "%" );
			ps.setString(2,"%" + pMotClé + "%" );
			
			rs = ps.executeQuery();
			
			List<Produit> listeProduitsDB = new ArrayList<>();
			Produit produit = null; 
			
			while (rs.next()) {
				produit = new Produit(rs.getInt(1), rs.getString(2), rs.getString(3), 
						rs.getDouble(4), rs.getInt(5), rs.getBoolean(6), rs.getString(7));
							
				listeProduitsDB.add(produit);
					
			}//end while
			
			return listeProduitsDB; 
			
		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode getByKeyword() de ProduitDAOimpl ...");
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
	} //end getByKeyword

	
	/* ================================================== */

	/**
	 * AFFICHER LA LISTE DES PRODUITS PAR CATEGORIES
	 */
	@Override
	public List<Produit> getByCategorie(Integer idCategorie) {
	
		try {
			ps = this.connexion.prepareStatement("select * from produit_categ1 where categorie_id = ? ");
			
			ps.setInt(1, idCategorie);
			
			rs = ps.executeQuery(); 
			
			Produit produitCateg = null; 
			List<Produit> listePorduitCateg = new ArrayList<>();
			
			while(rs.next()) {
				produitCateg = new Produit(rs.getInt(1), rs.getString(2), 
						rs.getString(3), rs.getDouble(4), rs.getInt(5), 
						rs.getBoolean(6), rs.getString(7), rs.getInt(8));
				
				listePorduitCateg.add(produitCateg);
			}//end while
			
			return listePorduitCateg; 
			
		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode getByCategorie() de ProduitDAOimpl ...");
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
	}// end getByCategorie

	
	/* ================================================== */
	
	
	@Override
	public List<Produit> getProduitSelectionnes(boolean selectionProduit) {
		
		try {
			ps = this.connexion.prepareStatement("select * from produits where selectionne_produit = ?");
			
			ps.setBoolean(1, selectionProduit);
		
			rs = ps.executeQuery();
			
			List<Produit> listeProduitsSelect = new ArrayList<>();
			Produit produit = null; 
			
			while (rs.next()) {
				produit = new Produit(rs.getInt(1), rs.getString(2), rs.getString(3), 
						rs.getDouble(4), rs.getInt(5), rs.getBoolean(6), rs.getString(7));
							
				listeProduitsSelect.add(produit);
					
			}//end while
			
			return listeProduitsSelect; 
			
		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode getProduitSelectionnes() de ProduitDAOimpl ...");
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
	}// end getProduitSelectionnes

	
	
	
	/**
	 * methode pour récupérer la liste des produits faite par un client via la vue (avec d'autres infos : lignes de commande, produits...) 
	 */
	@Override
	public List<Produit> findCommandePourCreaAffichage(Integer idClient) {
		try {
			ps = this.connexion.prepareStatement("select * from vieu_commande_client_clients where id_client=?");
			
			ps.setInt(1, idClient);
			
			rs = ps.executeQuery();
			
			List<Produit> listeProduitsCommandeDuClient = new ArrayList<>();
			Produit produit = null; 
			
			while (rs.next()) {
				produit = new Produit(rs.getInt(13), rs.getString(14), rs.getString(15), rs.getDouble(16), rs.getInt(17), rs.getBoolean(18), rs.getString(19));
							
						listeProduitsCommandeDuClient.add(produit);
					
			}//end while
			
			return listeProduitsCommandeDuClient; 
			
		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode findCommandePourCreaAffichage() de ProduitDAOimpl ...");
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

}// end classe
