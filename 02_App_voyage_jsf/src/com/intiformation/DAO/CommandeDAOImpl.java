package com.intiformation.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.intiformation.modeles.Commande;
import com.intiformation.modeles.Produit;


public class CommandeDAOImpl implements ICommandeDAO{

	
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	
	
	/**
	 * pour ajouter une commande
	 */
	@Override
	public boolean add(Commande pCommande) {
		try {
			ps = this.connexion.prepareStatement("insert into commandes"
					+ "(date_commande, client_id) values (?, ?)");
			
			ps.setDate(1, pCommande.getDate_commande());
			ps.setInt(2, pCommande.getClient_id());
			
			int verif = ps.executeUpdate();
				
			return (verif == 1);
			
			
		} catch (SQLException e) {
			System.out.println("CommandeDAOImpl : erreur add()");
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
	 * pour modifier une commande
	 */
	@Override
	public boolean update(Commande pCommande) {
		try {
			ps = this.connexion.prepareStatement("update commandes set "
					+ "date_commande=?, client_id=? where id_commande=?") ;

			ps.setDate(1, pCommande.getDate_commande());
			ps.setInt(2, pCommande.getClient_id());
			ps.setInt(3, pCommande.getId_commande());
			
			int verif = ps.executeUpdate();
				
			return (verif == 1);

		} catch (SQLException e) {
			System.out.println("CommandeDAOImpl : erreur update()");
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
	 * pour supprimer une commande
	 */
	@Override
	public boolean delete(Integer idCommande) {
		try {
			ps = this.connexion.prepareStatement("delete from commandes where id_commande = ?");
			
			ps.setInt(1, idCommande);
			
			int verifDelete =  ps.executeUpdate();
			
			return verifDelete == 1; 
			
		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode delete() de CommandeDAOImpl ...");
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}//end catch
		}//end finally	
		return false;
	}// end delete

	
	
	/**
	 * pour récup la liste de toutes les commandes
	 */
	@Override
	public List<Commande> getAll() {
		try {
			ps = this.connexion.prepareStatement("select * from commandes");
			
			rs = ps.executeQuery();
			
			List<Commande> listeCommandesDB = new ArrayList<>();
			Commande commande = null; 
			
			while (rs.next()) {
				commande = new Commande(rs.getInt(1), rs.getDate(2), rs.getInt(3));
							
				listeCommandesDB.add(commande);
					
			}//end while
			
			return listeCommandesDB; 
			
		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode getAll() de CommandeDAOImpl ...");
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
	}// end getAll

	
	/**
	 * pour récup une commande via son Id
	 */
	@Override
	public Commande getById(Integer idCommande) {
		try {
			ps = this.connexion.prepareStatement("select * from commandes where id_commande = ?");
			
			ps.setInt(1, idCommande);
			
			rs = ps.executeQuery(); 
			
			Commande commande = null; 
			
			rs.next();
			
			commande = new Commande(rs.getInt(1), rs.getDate(2), rs.getInt(3));
			
			return commande;
			
		} catch (SQLException e) {
			
			System.out.println("... Erreur lors de la méthode getById() de CommandeDAOImpl ...");
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
	}// end getById
	
	

}// end CommandeDAOImpl
