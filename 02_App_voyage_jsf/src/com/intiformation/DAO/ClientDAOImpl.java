package com.intiformation.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.intiformation.modeles.Client;

public class ClientDAOImpl implements IClientDAO {
	
	
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	
	
	/**
	 * pour ajouter un client
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
		}finally {
			try {
				ps.close();
			}catch (Exception e) {
			}// end catch
		}// end finally
		
		return false;
	}// end add

	
	
	/**
	 * pour modifier un client
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
		}finally {
			try {
				ps.close();
			}catch (Exception e) {
				// TODO: handle exception
			}// end catch
		}// end finally
		return false;
	}

	
	/**
	 * pour supprimer un client
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
		}finally {
			try {
				ps.close();
			}catch (Exception e) {
				// TODO: handle exception
			}// end catch
		}// end finally
		return false;
	}// end delete


	
	/**
	 * pour récup la liste des clients
	 */
	@Override
	public List<Client> getAll() {
		try {
			
			ps = this.connexion.prepareStatement("select * from clients");
			
			rs = ps.executeQuery();
			
			List<Client> listeClientsBDD = new ArrayList<>();
			Client client = null;
			
			while (rs.next()) {
				client = new Client(	rs.getInt(1), 
									rs.getString(2), 
									rs.getString(3), 
									rs.getString(4), 
									rs.getString(5), 
									rs.getString(6), 
									rs.getString(7),
									rs.getString(8), 
									rs.getBoolean(9));
				
				listeClientsBDD.add(client);
				
			}// end while
			
			return listeClientsBDD;
			
			
		} catch (SQLException e) {
			System.out.println("Dans ClientDAOImpl : erreur getAll()");
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
	
	
	/**
	 * pour récup un client via son Id
	 */
	@Override
	public Client getById(Integer pidClient) {
			
		try {
			
			ps = this.connexion.prepareStatement("select * from clients WHERE id_client= ?");
			
			ps.setInt(1, pidClient);
			
			rs = ps.executeQuery();
			
			Client client = null;
			
			while (rs.next()) {
				client = new Client(	rs.getInt(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5), 
						rs.getString(6), 
						rs.getString(7),
						rs.getString(8), 
						rs.getBoolean(9));
				
			}// end while
			
			return client;
			
			
		} catch (SQLException e) {
			System.out.println("ClientDAOImpl : erreur getById()");
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
	}// end geById


	/**
	 * vérif si le client existe dans bdd avec identifiant et mdp
	 */
	@Override
	public boolean isClientExists(String pIdentifiant, String pMdp) {
		try {
			
			ps = this.connexion.prepareStatement("select count(*) from clients WHERE identifiant_client= ? AND mot_de_passe_client=?");
			
			ps.setString(1, pIdentifiant);
			ps.setString(2, pMdp);
			
			rs = ps.executeQuery();
			
			rs.next();
			
			int verif = rs.getInt(1);
			
			return (verif == 1);
			
			
		} catch (SQLException e) {
			System.out.println("ClientDAOImpl : erreur isClientExists()");
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				ps.close();
			}catch (Exception e) {
			}// end catch
		}// end finally
		
		return false;
	}

	@Override
    public Client getByIdAndMdp(String pIdentifiant, String pMdp) {
            try {

            ps = this.connexion.prepareStatement("select * from clients WHERE identifiant_client= ? AND mot_de_passe_client=?");

            ps.setString(1, pIdentifiant);
            ps.setString(2, pMdp);

            rs = ps.executeQuery();

            Client client = null;

            while (rs.next()) {
                client = new Client(    rs.getInt(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getString(5), 
                        rs.getString(6), 
                        rs.getString(7),
                        rs.getString(8), 
                        rs.getBoolean(9));

            }// end while

            return client;


        } catch (SQLException e) {
            System.out.println("ClientDAOImpl : erreur getByIdAndMdp()");
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
    }// end getByIdAndMdp


	@Override
	public Client getByIdAndMdp(String pIdentifiant, String pMdp) {
		try {
			
			ps = this.connexion.prepareStatement("select * from clients WHERE identifiant_client= ? AND mot_de_passe_client=?");
			
			ps.setString(1, pIdentifiant);
			ps.setString(2, pMdp);
			
			rs = ps.executeQuery();
			
			Client client = null;
			
			while (rs.next()) {
				client = new Client(	rs.getInt(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5), 
						rs.getString(6), 
						rs.getString(7),
						rs.getString(8), 
						rs.getBoolean(9));
				
			}// end while
			
			return client;
			
			
		} catch (SQLException e) {
			System.out.println("ClientDAOImpl : erreur getByIdAndMdp()");
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
	}// end getByIdAndMdp


}// end clientDAOImpl
