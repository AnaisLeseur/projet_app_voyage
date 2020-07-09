
/* ------ CREATION d'une BASE DE DONNEES ----*/

	create database db_gestion_voyage;
    
    
    
/* ------ création DES TABLES d'une BASE DE DONNEES ----*/

create table users ( id_user varchar(20) primary key,
								mot_de_passe_user varchar(20),
                                nom_user  varchar(100),
                                activated_user boolean
                                );
                                



create table clients ( id_client int(10) primary key not null auto_increment,
								identifiant_client varchar(20),
								mot_de_passe_client varchar(20),
                                nom_client  varchar(100),
                                prenom_client  varchar(100),
                                adresse_client  varchar(100),
                                email_client  varchar(100),
                                tel_client  varchar(10),
                                activated_client boolean
                                );
                                
	Drop table clients;
                                

create table commandes ( id_commande int(20) primary key auto_increment,
											date_commande date,
                                            client_id int(10),
                                            constraint fk_commande_client_id foreign key (client_id) references clients (id_client) on delete cascade
										);
                                        
	Drop table commandes;
 
    


create table categories ( id_categorie int(20) primary key,
										nom_categorie varchar(20),
                                        image_categorie mediumblob,
                                        description_categorie varchar(200)
                                        );
                                        
		Drop table categories;
        
        
 create table  produits (	id_produit int(20) primary key auto_increment,
										nom_produit varchar(100),
                                        description_produit varchar(500),
                                        prix_produit decimal(7,3),
                                        quantite_produit int(5),
                                        selectionne_produit boolean not null default 0,
										image_produit mediumblob
									);
                                        
			Drop table produits;
            
            
            ALTER TABLE produits
			ALTER COLUMN selectionne_produit not null DEFAULT 0;
            
            
create table produits_categories (	categorie_id int(20),
														produit_id int(20),
                                                primary key( categorie_id, produit_id),
                                                foreign key (categorie_id) references categories (id_categorie) on delete cascade,
                                                foreign key (produit_id) references produits (id_produit) on delete cascade
													);         
         
         	Drop table produits_categories;
            
                                                        
create table lignes_commandes (	commande_id int(20),
														produit_id int(20),
														quantite_ligne int(5),
														prix_ligne decimal(7,3),
                                                primary key( commande_id, produit_id),
                                                foreign key (commande_id) references commandes (id_commande) on delete cascade,
                                                foreign key (produit_id) references produits (id_produit) on delete cascade
													);         
                                                        
             	Drop table lignes_commandes;                           
   
   
   create table paniers ( 	id_panier int(20) primary key auto_increment,
										produit_id int(20),
										commande_id int(20),
										foreign key (produit_id) references lignes_commandes (produit_id) on delete cascade,
                                        foreign key (commande_id) references lignes_commandes (commande_id) on delete cascade
										);


			Drop table paniers;       