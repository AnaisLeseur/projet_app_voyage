package com.intiformation.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.intiformation.modeles.Commande;
import com.intiformation.modeles.LigneCommande;

public class LigneCommandeDAOImpl implements ILigneCommandeDAO {
	
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	
	
	/**
	 * pour ajouter une ligne de commande
	 */
	@Override
	public boolean add(LigneCommande pLigneCommande) {
		try {
			ps = this.connexion.prepareStatement("insert into lignes_commandes"
					+ "(commande_id, produit_id, quantite_ligne, prix_ligne)"
					+ "values (?, ?, ?, ?)");
			
			ps.setInt(1, pLigneCommande.getCommande_id());
			ps.setInt(2, pLigneCommande.getProduit_id());
			ps.setInt(3, pLigneCommande.getQuantite_ligne());
			ps.setDouble(4, pLigneCommande.getPrix_ligne());

			
			int verif = ps.executeUpdate();
				
			
			return (verif == 1);
			
			
		} catch (SQLException e) {
			System.out.println("LigneCommandeDAOImpl : erreur add()");
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			}catch (Exception e) {
			}// end catch
		}// end finally
		
		return false;
	}// end add

	
	
	/**
	 * pour modifier une ligne de commande
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


	
	/**
	 * pour récup la liste de toutes les lignes de commande
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


	/**
	 * pour supprimer une ligne de commande via ses 2 PK
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
	
	
	/**
	 * pour récup les ligne de commandes associées à une commande
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
	
	
	
	/**
	 * methode pour récupérer la liste des lignes de commande faites par un client via la vue (avec d'autres infos : lignes de commande, produits...) 
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
	}
	
	
	
// METH NON UTILISEES
	
	
	/**
	 * pour supprimer : ne peut pas utilisée car besoin de 2 id => Cf meth deleteDoubleId
	 */
	@Override
	public boolean delete(Integer pidLigneCommande) {
		return false;
	}// end delete
	
	
	/**
	 * pour récup : ne peut pas utilisée car besoin de 2 id => Cf meth getByDoubleId
	 */
	@Override
	public LigneCommande getById(Integer pidLigneCommande) {
		// TODO Auto-generated method stub
		return null;
	}// end getById








}// end class
