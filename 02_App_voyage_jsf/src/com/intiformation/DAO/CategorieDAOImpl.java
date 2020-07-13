package com.intiformation.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.intiformation.modeles.Categorie;

import sun.security.x509.CertificateAlgorithmId;

/**
 * Implémentation concrète de la classe Categorie
 * 
 * @author hannahlevardon
 *
 */
public class CategorieDAOImpl implements ICategorieDAO {

	private PreparedStatement ps = null;
	private ResultSet rs = null;

	/**
	 * AJOUTER UNE CATEGORIE
	 */
	@Override
	public boolean add(Categorie pCategorie) {

		try {
			ps = this.connexion.prepareStatement(
					"INSERT into categories(id_categorie, nom_categorie, image_categorie, description_categorie ) "
							+ "VALUES (?,?,?,?)");

			ps.setInt(1, pCategorie.getId_categorie());
			ps.setString(2, pCategorie.getNom_categorie());
			ps.setString(3, pCategorie.getUrlImageCategorie());
			ps.setString(4, pCategorie.getDescription_categorie());

			int verifAjout = ps.executeUpdate();

			return verifAjout == 1;

		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode add() de CetgorieDAOimpl ...");
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
	 * MODIFIER UNE CATEGORIE
	 */
	@Override
	public boolean update(Categorie pCategorie) {

		try {
			ps = this.connexion.prepareStatement("update categories set nom_categorie=?, "
					+ "image_categorie=?, description_categorie=? where id_categorie=?  ");

			ps.setString(1, pCategorie.getNom_categorie());
			ps.setString(2, pCategorie.getUrlImageCategorie());
			ps.setString(3, pCategorie.getDescription_categorie());
			ps.setInt(4, pCategorie.getId_categorie());

			int verifUpdate = ps.executeUpdate();

			return verifUpdate == 1;

		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode update() de CategorieDAOimpl ...");
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
	}// end update()

	/* ================================================== */

	/**
	 * SUPPRIMER UNE CATEGORIE
	 */
	@Override
	public boolean delete(Integer idCategorie) {

		try {
			ps = this.connexion.prepareStatement("delete from categories where id_categorie=? ");

			ps.setInt(1, idCategorie);

			int verifDelete = ps.executeUpdate();

			return verifDelete == 1;

		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode delete() de CategorieDAOimpl ...");

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
	}// end delete

	/* ================================================== */

	/**
	 * AFFICHER LA LISTE DE TOUTES LES CATEGORIES
	 */
	@Override
	public List<Categorie> getAll() {

		try {
			ps = this.connexion.prepareStatement("select * from categories");

			rs = ps.executeQuery();

			List<Categorie> listeCategories = new ArrayList<>();
			Categorie categorie = null;

			while (rs.next()) {
				categorie = new Categorie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));

				listeCategories.add(categorie);

			} // end while

			return listeCategories;

		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode getAll() de CategorieDAOimpl ...");
			e.printStackTrace();
		} finally {

			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // end catch
		} // end finally

		return null;
	}// end getAll()

	/* ================================================== */

	/**
	 * AFFICHER CATEGORIE PAR SON ID
	 */
	@Override
	public Categorie getById(Integer idCategories) {

		try {
			ps = this.connexion.prepareStatement("select * from categories where id_categorie = ? ");

			ps.setInt(1, idCategories);

			rs = ps.executeQuery();

			Categorie categorie = null;

			rs.next();

			categorie = new Categorie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));

			return categorie;

		} catch (SQLException e) {

			System.out.println("... Erreur lors de la méthode getById() de CategorieDAOimpl ...");

			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // end catch
		} // end finally

		return null;
	}// end getById

}// end class
