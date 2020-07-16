package com.intiformation.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.*;

import com.intiformation.modeles.ProduitCategorie;

public class ProduitCategorieDAOImpl implements IProduitCategorie {

	private PreparedStatement ps = null;
	private ResultSet rs = null;

	/**
	 * AJOUTER UNE LIAISON PRODUIT-CATEGORIE
	 */
	@Override
	public boolean add(ProduitCategorie pPC) {

		try {
			ps = this.connexion
					.prepareStatement("insert into produits_categories(categorie_id, produit_id) " + "values(?, ?)");

			ps.setInt(1, pPC.getCategorie_id());
			ps.setInt(2, pPC.getProduit_id());

			int verifAjout = ps.executeUpdate();

			return verifAjout == 1;

		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode add() de ProduitCategorieDAOImpl ...");
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // end catch
		} // end finally
		return false;
	}// end add

	/**
	 * MODIFIER UNE LIAISON PRODUIT-CATEGORIE
	 */
	@Override
	public boolean update(ProduitCategorie pPC) {

		try {
			ps = this.connexion.prepareStatement("update produits_categories set categorie_id = ?, produit_id = ? "
					+ "where categorie_id= ? and produit_id = ?");

			ps.setInt(1, pPC.getCategorie_id());
			ps.setInt(2, pPC.getProduit_id());
			ps.setInt(3, pPC.getCategorie_id());
			ps.setInt(4, pPC.getProduit_id());

			int verifUpdate = ps.executeUpdate();

			return verifUpdate == 1;

		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode update() de ProduitCategorieDAOImpl ...");
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			} // end catch
		} // end finally

		return false;
	}// end update

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * AFFICHER LA LISTE DE TOUTES LES LIAISONS PRODUIT-CATEGORIE
	 */
	@Override
	public List<ProduitCategorie> getAll() {
		
		try {
			ps = this.connexion.prepareStatement("select * from produits_categories");
			
			rs = ps.executeQuery();
			
			List<ProduitCategorie> listeProdCateg = new ArrayList<>();
			ProduitCategorie prodCategorie = null;

			while (rs.next()) {
				
				prodCategorie = new ProduitCategorie(rs.getInt(1), rs.getInt(2));
				
				listeProdCateg.add(prodCategorie);
				
			}//end while
			
			return listeProdCateg; 
					
		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode getAll() de ProduitCategorieDAOImpl ...");
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}//end catch
		}//end finally
		return null;
	}//end getAll 

	@Override
	public ProduitCategorie getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * TROUVER UNE LIAISON PRODUIT-CATEGORIE
	 */
	@Override
	public ProduitCategorie getByDoubleId(Integer pIdCategorie, Integer pIdProduit) {
		
		try {
			ps = this.connexion.prepareStatement("select * from produits_categories where categorie_id=? and produit_id=?");
			
			ps.setInt(1, pIdCategorie);
			ps.setInt(2, pIdProduit);
			
			rs = ps.executeQuery();
			
			ProduitCategorie prodCategorie = null;
			
			rs.next(); 
			prodCategorie = new ProduitCategorie(rs.getInt(1), rs.getInt(2));
			
			return prodCategorie; 
			
		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode getByDoubleID() de ProduitCategorieDAOImpl ...");
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
	}// end getByDoubleId 

	/**
	 * SUPPRIMER UNE LIAISON PRODUIT-CATEGORIE
	 */
	@Override
	public boolean deleteByDoubleId(Integer pIdCategorie, Integer pIdProduit) {

		try {
			ps = this.connexion
					.prepareStatement("delete from produits_categories where categorie_id = ? and produit_id = ? ");

			ps.setInt(1, pIdCategorie);
			ps.setInt(2, pIdProduit);

			int verifDelete = ps.executeUpdate();

			return verifDelete == 1;

		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode deleteByDoubleId() de ProduitCategorieDAOImpl ...");

			e.printStackTrace();
		} finally {

			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // end catch

		} // end finally

		return false;
	}// end deleteByDoubleId

	

	/**
	 * AJOUTER UNE LIAISON PRODUIT-CATEGORIE VERSION 2
	 */
	@Override
	public boolean addVersion2(int IDcategorie,int IdProduit) {

		try {
			ps = this.connexion
					.prepareStatement("insert into produits_categories(categorie_id, produit_id) " + "values(?, ?)");

			ps.setInt(1, IDcategorie);
			ps.setInt(2, IdProduit);

			int verifAjout = ps.executeUpdate();

			return verifAjout == 1;

		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode addversion2() de ProduitCategorieDAOImpl ...");
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // end catch
		} // end finally
		return false;
	}// end add

}// end classe
