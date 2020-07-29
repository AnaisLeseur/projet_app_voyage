package com.intiformation.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.intiformation.modeles.Commande;
import com.intiformation.modeles.Produit;

/**
 * <pre>
 * Implémentation concrète de la couche DAO pour une commande 
 * Implémente l'interface ICommandeDAO
 * </pre>
 * 
 * @author hannahlevardon
 *
 */
public class CommandeDAOImpl implements ICommandeDAO {

	private PreparedStatement ps = null;
	private ResultSet rs = null;

	/**
	 * Ajouter une commande
	 * 
	 * @param pCommande:
	 *            commande à ajouter à la database
	 */
	@Override
	public boolean add(Commande pCommande) {
		try {
			ps = this.connexion.prepareStatement("insert into commandes" + "(date_commande, client_id) values (?, ?)");

			ps.setDate(1, pCommande.getDate_commande());
			ps.setInt(2, pCommande.getClient_id());

			int verif = ps.executeUpdate();

			return (verif == 1);

		} catch (SQLException e) {
			System.out.println("CommandeDAOImpl : erreur add()");
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
	 * Modifier une commande
	 * 
	 * @param pCommande:
	 *            commande à modifier à la database
	 */
	@Override
	public boolean update(Commande pCommande) {
		try {
			ps = this.connexion
					.prepareStatement("update commandes set " + "date_commande=?, client_id=? where id_commande=?");

			ps.setDate(1, pCommande.getDate_commande());
			ps.setInt(2, pCommande.getClient_id());
			ps.setInt(3, pCommande.getId_commande());

			int verif = ps.executeUpdate();

			return (verif == 1);

		} catch (SQLException e) {
			System.out.println("CommandeDAOImpl : erreur update()");
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			} // end catch
		} // end finally
		return false;
	}// end update

	/**
	 * Supprimer une commande
	 * 
	 * @param pCommande:
	 *            id de la commande à supprimer de la database
	 */
	@Override
	public boolean delete(Integer idCommande) {
		try {
			ps = this.connexion.prepareStatement("delete from commandes where id_commande = ?");

			ps.setInt(1, idCommande);

			int verifDelete = ps.executeUpdate();

			return verifDelete == 1;

		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode delete() de CommandeDAOImpl ...");
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // end catch
		} // end finally
		return false;
	}// end delete()

	/**
	 * Récupération de la liste de toutes les commandes dans la database
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

			} // end while

			return listeCommandesDB;

		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode getAll() de CommandeDAOImpl ...");
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // end catch
		} // end finally
		return null;
	}// end getAll()

	/**
	 * Récupération d'une commande via son Id
	 * 
	 * @param pCommande:
	 *            id de la commande à récupérer à la database
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
			} // end catch
		} // end finally

		return null;
	}// end getById()
	

	/**
	 * Récupération de la dernière commande enregistrée dans la database
	 * 
	 * @param pCommande:
	 *            id de la commande à récupérer à la database
	 */
	@Override
	public Commande findIdMax() {
		try {
			
			// Récupération du dernier ID ajouté à la database, correspondant à la dernière commande effectuée (auto-incrémentation)
			
			ps = this.connexion.prepareStatement("SELECT max(id_commande) FROM commandes;");

			rs = ps.executeQuery();

			int idCommande = 0;

			rs.next();

			idCommande = rs.getInt(1);
			System.out.println("idCommande: " + idCommande);

			// Récupération des informations de la commande correspondant à cet id
			ps = this.connexion.prepareStatement("select * from commandes where id_commande = ?");

			ps.setInt(1, idCommande);

			rs = ps.executeQuery();

			Commande commande = null;

			rs.next();

			commande = new Commande(rs.getInt(1), rs.getDate(2), rs.getInt(3));

			return commande;

		} catch (SQLException e) {

			System.out.println("... Erreur lors de la méthode findIdMax() de CommandeDAOImpl ...");
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
	}// end findIdMax()
	

	/**
	 * Récupération de la la liste des commandes effectuées par un client
	 * 
	 * @param idClient : ID du client 
	 */
	@Override
	public List<Commande> findCommandeDuClient(Integer idClient) {
		try {
			ps = this.connexion.prepareStatement("select * from commandes where client_id= ?");

			ps.setInt(1, idClient);

			rs = ps.executeQuery();

			List<Commande> listeCommandesDuClient = new ArrayList<>();
			Commande commande = null;

			while (rs.next()) {
				commande = new Commande(rs.getInt(1), rs.getDate(2), rs.getInt(3));

				listeCommandesDuClient.add(commande);

			} // end while

			return listeCommandesDuClient;

		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode findCommandeDuClient() de CommandeDAOImpl ...");
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // end catch
		} // end finally
		return null;
	}// end findCommandeDuClient()

	
	
	/**
	 * Récupération de la liste des commandes faites par un client via la vue vieu_commande_client_clients (voir Mysql)	 * 
	 * (avec d'autres infos : lignes de commande, produits...)
	 * 
	 * @param idClient : ID du client	  
	 */
	@Override
	public List<Commande> findCommandePourCreaAffichage(Integer idClient) {
		try {
			ps = this.connexion.prepareStatement("select * from vieu_commande_client_clients where id_client=?");

			ps.setInt(1, idClient);

			rs = ps.executeQuery();

			List<Commande> listeCommandesDuClient = new ArrayList<>();
			Commande commande = null;

			while (rs.next()) {
				commande = new Commande(rs.getInt(10), rs.getDate(11), rs.getInt(12));

				listeCommandesDuClient.add(commande);

			} // end while

			return listeCommandesDuClient;

		} catch (SQLException e) {
			System.out.println("... Erreur lors de la méthode findCommandePourCreaAffichage() de CommandeDAOImpl ...");
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // end catch
		} // end finally
		return null;
	}// end findCommandePourCreaAffichage

}// end CommandeDAOImpl
