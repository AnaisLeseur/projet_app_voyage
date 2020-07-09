package com.intiformation.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.intiformation.modeles.Administrateur;

public class AdministrateurDAOImpl implements IAdministrateurDAO {
	
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	
	/**
	 * pour ajouter un administrateur
	 */
	@Override
	public boolean add(Administrateur t) {
		// TODO Auto-generated method stub
		return false;
	}

	
	/**
	 * pour modifier un administrateur
	 */
	@Override
	public boolean update(Administrateur t) {
		// TODO Auto-generated method stub
		return false;
	}

	
	/**
	 * pour supprimer un administrateur
	 */
	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	
	/**
	 * pour récup la liste des administrateurs
	 */
	@Override
	public List<Administrateur> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * pour récup un adlinistrateur par son Id
	 */
	@Override
	public Administrateur getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * vérif si l'administrateur existe dans bdd avec identifiant et mdp
	 */
	@Override
	public boolean isAdministrateurExists(String pIdentifiant, String pMdp) {
		try {
			
			ps = this.connexion.prepareStatement("select count(*) from users WHERE id_user= ? AND mot_de_passe_user=?");
			
			ps.setString(1, pIdentifiant);
			ps.setString(2, pMdp);
			
			rs = ps.executeQuery();
			
			rs.next();
			
			int verif = rs.getInt(1);
			
			return (verif == 1);
			
			
		} catch (SQLException e) {
			System.out.println("AdministrateurDAOImpl : erreur isAdministrateurExists()");
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				ps.close();
			}catch (Exception e) {
			}// end catch
		}// end finally
		
		return false;
	}// end isAdministrateurExists

} // end class AdministrateurDAOImpl
