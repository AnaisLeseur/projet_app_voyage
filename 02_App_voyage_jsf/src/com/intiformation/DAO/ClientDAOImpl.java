package com.intiformation.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.intiformation.modeles.Client;

/**
 * <pre>
 * Implémentation concrète de la couche DAO pour un client 
 * Implémente l'interface IClientDAO
 * </pre>
 * 
 * @author hannahlevardon
 *
 */
public class ClientDAOImpl implements IClientDAO {

	private PreparedStatement ps = null;
	private ResultSet rs = null;

	/**
	 * Ajouter un client
	 * 
	 * @param pClient: client à ajouter à la database
	 * @return boolean: true si ajout validé, sinon false
	 */
	@Override
	public boolean add(Client pClient) {
		try {

			ps = this.connexion.prepareStatement("insert into clients"
					+ "(identifiant_client, mot_de_passe_client, nom_client, prenom_client, adresse_client, email_client, tel_client, activated_client) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?)");

			ps.setString(1, pClient.getIdentifiant_client());
			ps.setString(2, pClient.getMot_de_passe_client());
			ps.setString(3, pClient.getNom_client());
			ps.setString(4, pClient.getPrenom_client());
			ps.setString(5, pClient.getAdresse_client());
			ps.setString(6, pClient.getEmail_client());
			ps.setString(7, pClient.getTel_client());
			ps.setBoolean(8, pClient.isActivated_client());

			int verif = ps.executeUpdate();

			return (verif == 1);

		} catch (SQLException e) {
			System.out.println("ClientDAOImpl : erreur add()");
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			} // end catch
		} // end finally

		return false;
	}// end add()

	/**
	 * Modifier un client
	 * 
	 * @param pClient:
	 *            client à modifier dans la database
	 * @return boolean: true si modification validée, sinon false
	 * 
	 */
	@Override
	public boolean update(Client pClient) {
		try {

			ps = this.connexion.prepareStatement(" update clients set identifiant_client=?, mot_de_passe_client=?, "
					+ "nom_client=?, prenom_client=?, adresse_client=?, email_client=?, "
					+ "tel_client=?, activated_client=? where id_client=?");

			ps.setString(1, pClient.getIdentifiant_client());
			ps.setString(2, pClient.getMot_de_passe_client());
			ps.setString(3, pClient.getNom_client());
			ps.setString(4, pClient.getPrenom_client());
			ps.setString(5, pClient.getAdresse_client());
			ps.setString(6, pClient.getEmail_client());
			ps.setString(7, pClient.getTel_client());
			ps.setBoolean(8, pClient.isActivated_client());
			ps.setInt(9, pClient.getId_client());

			int verif = ps.executeUpdate();

			return (verif == 1);

		} catch (SQLException e) {
			System.out.println("ClientDAOImpl : erreur update()");
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				// TODO: handle exception
			} // end catch
		} // end finally
		return false;
	}// end update()

	/**
	 * Supprimer un client
	 * 
	 * @param pIdClient:
	 *            Id du client à supprimer de la database
	 * @return boolean: true si suppression validée, sinon false
	 */
	@Override
	public boolean delete(Integer pIdClient) {

		try {

			ps = this.connexion.prepareStatement("delete From clients where id_client=?");

			ps.setInt(1, pIdClient);

			int verif = ps.executeUpdate();

			return (verif == 1);

		} catch (SQLException e) {
			System.out.println("ClientDAOImpl : erreur delete()");
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			} // end catch
		} // end finally
		return false;
	}// end delete()

	/**
	 * Récupération de la liste des clients dans la database
	 * @return listeClientsBDD: liste de toutes les clients de la database
	 * 
	 */
	@Override
	public List<Client> getAll() {
		try {

			ps = this.connexion.prepareStatement("select * from clients");

			rs = ps.executeQuery();

			List<Client> listeClientsBDD = new ArrayList<>();
			Client client = null;

			while (rs.next()) {
				client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getBoolean(9));

				listeClientsBDD.add(client);

			} // end while

			return listeClientsBDD;

		} catch (SQLException e) {
			System.out.println("Dans ClientDAOImpl : erreur getAll()");
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (Exception e) {
				// TODO: handle exception
			} // end catch
		} // end finally
		return null;
	}// end getAll()

	/**
	 * Récupération d'un client dans la databse via son Id
	 * 
	 * @param pIdClient:
	 *            Id du client à retrouver dans la database
	 * @return: le client recherché
	 */
	@Override
	public Client getById(Integer pidClient) {

		try {

			ps = this.connexion.prepareStatement("select * from clients WHERE id_client= ?");

			ps.setInt(1, pidClient);

			rs = ps.executeQuery();

			Client client = null;

			while (rs.next()) {
				client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getBoolean(9));

			} // end while

			return client;

		} catch (SQLException e) {
			System.out.println("ClientDAOImpl : erreur getById()");
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (Exception e) {
				// TODO: handle exception
			} // end catch
		} // end finally

		return null;
	}// end geById ()

	/**
	 * Vérification de l'existence d'un client dans la database via ses identifiant
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
	 * @return true si un seul client avec l'identifiant et le mot de passe existe, 
	 * false si plusieurs clients qui possèdent les memes identifiant et mot de passe ou si aucun client n'est trouvé
	 * 
	 */
	@Override
	public boolean isClientExists(String pIdentifiant, String pMdp) {
		try {

			ps = this.connexion.prepareStatement(
					"select count(*) from clients WHERE identifiant_client= ? AND mot_de_passe_client=?");

			ps.setString(1, pIdentifiant);
			ps.setString(2, pMdp);

			rs = ps.executeQuery();

			rs.next();

			int verif = rs.getInt(1);

			return (verif == 1);

		} catch (SQLException e) {
			System.out.println("ClientDAOImpl : erreur isClientExists()");
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (Exception e) {
			} // end catch
		} // end finally

		return false;
	}// end isClientExists()

	/**
	 * Récupération d'un client dans la database via ses identifiant et mot de passe
	 * 
	 * @param pIdentifiant:
	 *            identifiant à vérifier
	 * @param pMdp:
	 *            mot de passe à vérifier
	 *      
	 * @return: le client ayant pIdentifiant et pMdp
	 * 
	 */
	@Override
	public Client getByIdAndMdp(String pIdentifiant, String pMdp) {
		try {

			ps = this.connexion
					.prepareStatement("select * from clients WHERE identifiant_client= ? AND mot_de_passe_client=?");

			ps.setString(1, pIdentifiant);
			ps.setString(2, pMdp);

			rs = ps.executeQuery();

			Client client = null;

			while (rs.next()) {
				client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getBoolean(9));

			} // end while

			return client;

		} catch (SQLException e) {
			System.out.println("ClientDAOImpl : erreur getByIdAndMdp()");
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (Exception e) {
				// TODO: handle exception
			} // end catch
		} // end finally

		return null;
	}// end getByIdAndMdp

}// end clientDAOImpl
