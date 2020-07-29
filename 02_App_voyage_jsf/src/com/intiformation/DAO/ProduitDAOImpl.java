package com.intiformation.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.intiformation.modeles.Produit;

/**
 * Implémentation concrète de la DAO pour un produit
 * classe qui implemente l'interface IProduitDAO
 * 
 * @author hannahlevardon
 *
 */
public class ProduitDAOImpl implements IProduitDAO {

	private PreparedStatement ps = null;
	private ResultSet rs = null;

	/**
	 * AJOUTER UN PRODUIT 
	 * @param pProduit : le produit à ajouter
	 * @return boolean: si ajout ok ou non 
	 */
	@Override
	public boolean add(Produit pProduit) {

		try {
			
			// prepared statement + requete SQL
			ps = this.connexion.prepareStatement(
					"INSERT into produits(nom_produit, description_produit, prix_produit, quantite_produit, selectionne_produit, image_produit ) "
							+ "VALUES (?,?,?,?,?,?)");

			// passage de params
			ps.setString(1, pProduit.getNomProduit());
			ps.setString(2, pProduit.getDescriptionProduit());
			ps.setDouble(3, pProduit.getPrixProduit());
			ps.setInt(4, pProduit.getQuantitéProduit());
			ps.setBoolean(5, pProduit.isSelectionProduit());
			ps.setString(6, pProduit.getUrlImageProduit());

			// executeUpdate
			int verifAjout = ps.executeUpdate();

			return verifAjout == 1;

		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode add() de ProduitDAOimpl ...");
			e.printStackTrace();
		} finally {
			try {
				
				// fermeture des ressources
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
	 * @param pProduit : le produit à modifier
	 * @return boolean: si modification ok ou non 
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
	 * @param idProduit : l'id du produit à supprimer de la bdd
	 * @return boolean: si ajout ok ou non 
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
	 * RECUPERER LA LISTE DE TOUS LES PRODUITS
	 * @return List<Produit> listeProduitsDB : liste de tous les produits de la bdd
	 * 
	 */
	@Override
	public List<Produit> getAll() {
		
		try {
			ps = this.connexion.prepareStatement("select * from produits");
			
			rs = ps.executeQuery();
			
			// on créé la liste 'listeProduitsDB' qui va récupérer tous les produits de la bdd
			List<Produit> listeProduitsDB = new ArrayList<>();
			Produit produit = null; 
			
			while (rs.next()) {
				produit = new Produit(rs.getInt(1), rs.getString(2), rs.getString(3), 
						rs.getDouble(4), rs.getInt(5), rs.getBoolean(6), rs.getString(7));
				
				// pour chaque produit de la bdd => ajout à la liste 'listeProduitsDB'
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
	 * RECUPERER UN PRODUIT PAR SON ID
	 * @param idProduit : l'id du produit à récupérer dans la bdd
	 * @return produit : le produit récupéré 
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
	 * RECUPERER LA LISTE DES PRODUITS CONTENANT UN MOT-CLE
	 * @param pMotClé : le mot clé saisi dans la barre de recherche 
	 * @return List<Produit> 'listeProduitsDB' : la liste des produits dont le titre ou la description contient 'pMotClé'
	 */
	@Override
	public List<Produit> getByKeyword(String pMotClé) {
		
		//récupération de 'pMotClé' sous forme correcte 
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
	 * RECUPERER LA LISTE DES PRODUITS PAR CATEGORIE
	 * @param idCategorie : id de la catégorie 
	 * @return List<Produit> listePorduitCateg : liste des produits de la catégorie avec id = 'idCategorie'
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
	
	/**
	 * RECUPERER LA LISTE DES PRODUITS SELECTIONNES (ajoutés au panier)
	 * @param boolean selectionProduit : true si le produit a été ajouté au panier 
	 * @return List<Produit> listeProduitsSelect : liste des produits selectionnés = ajoutés au panier
	 */
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

	
	/* ================================================== */
	
	/**
	 * RECUPERER LA LISTE DES PRODUITS SELECTIONNES PAR LE CLIENT (ajoutés au panier)
	 * via une vue de la bdd
	 * ( cette vue permet aussi de récupérer d'autres infos pour afficher un récapitulatif complet avant le paiement: lignes de commande, produits...) 
	 * 
	 * @param idClient : id du client => la récupération des informations se fait a partir de l'id du client
	 * @return List<Produit> listeProduitsCommandeDuClient : liste des produits que le client à ajouté à son panier 
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
