package com.intiformation.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.intiformation.modeles.Administrateur;

/**
 * <pre>
 * Implémentation concrète de la couche DAO pour un administrateur 
 * Implémente l'interface IAdministrateurDAO
 * </pre>
 * 
 * @author hannahlevardon
 *
 */
public class AdministrateurDAOImpl implements IAdministrateurDAO {

	private PreparedStatement ps = null;
	private ResultSet rs = null;

	/**
	 * Vérification de l'existence de l'administrateur dans bdd via ses identifiants
	 * et mot de passe
	 * 
	 * @param pIdentifiant:
	 *            identifiant à vérifier
	 * @param pMdp:
	 *            mot de passe à vérifier
	 * 
	 *            Utilisation de la méthode 'count()' de mysql pour déterminer le
	 *            nombre de lignes contenant le mot de passe et l'identitifiant
	 * 
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
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (Exception e) {
			} // end catch
		} // end finally

		return false;
	}// end isAdministrateurExists

	/* _____________ Méthodes non implémentées ___________ */

	/**
	 * Ajouter un administrateur
	 */
	@Override
	public boolean add(Administrateur t) {
		// TODO Auto-generated method stub
		return false;
	}// end add()

	/**
	 * Modifier un administrateur
	 */
	@Override
	public boolean update(Administrateur t) {
		// TODO Auto-generated method stub
		return false;
	}// end update()

	/**
	 * Supprimer un administrateur
	 */
	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}// end delete()

	/**
	 * Récupération de la liste des administrateurs
	 */
	@Override
	public List<Administrateur> getAll() {
		// TODO Auto-generated method stub
		return null;
	}// end getAll()

	/**
	 * Récupération d'un adlinistrateur par son Id
	 */
	@Override
	public Administrateur getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}// end getById()

}// end class AdministrateurDAOImpl
