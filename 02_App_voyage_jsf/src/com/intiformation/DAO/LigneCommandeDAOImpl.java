package com.intiformation.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.intiformation.modeles.Client;
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
			LigneCommande ligneCommmande = null;
			
			while (rs.next()) {
				ligneCommmande = new LigneCommande(rs.getInt(1), 
													rs.getInt(2), 
													rs.getInt(3), 
													rs.getDouble(4));
				
				listeligneCommandeBdd.add(ligneCommmande);
				
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
	public LigneCommande getByDoubleId(Integer pIdCommande, Integer pIdProduit) {
		try {
			
			ps = this.connexion.prepareStatement("select * from lignes_commandes where where commande_id=? and produit_id=?");
			
			ps.setInt(1, pIdCommande);
			ps.setInt(2, pIdProduit);
			
			rs = ps.executeQuery();
				
			LigneCommande ligneCommande = null;
			
			while (rs.next()) {
				ligneCommande = new LigneCommande(rs.getInt(1), 
												rs.getInt(2), 
												rs.getInt(3), 
												rs.getDouble(4)); 									
			}// end while
			
			return ligneCommande;
			
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
