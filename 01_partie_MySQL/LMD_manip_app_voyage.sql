
/* ------ Manip des données ----*/

/* ------- SELECT ALL FROM -------- */

select * from categories;

select * from clients;

select * from commandes;

select * from lignes_commandes;

select * from produits;

select * from produits_categories;

select * from users;

select * from produit_categ;



/* ------ INSERT INTO  -----------*/ 

insert into users(id_user, mot_de_passe_user, nom_user) values ('admin', 'admin', 'Admin');


insert into clients(identifiant_client, mot_de_passe_client, nom_client, prenom_client, adresse_client, email_client, tel_client) values('123', '123', 'Leseur', 'Anais', 'Lachassagne', 'anais@mail.com','09875432');


insert into commandes(date_commande, client_id) values('2020-07-05', 1);

insert into produits(nom_produit, description_produit, prix_produit) values('Paris', 'voyage 2 jours dans la capitale francaise', 350.00);
insert into produits(nom_produit, description_produit, prix_produit) values('Chine', 'voyage 15 jours pour voir la grande muraille', 2350.00);
insert into produits(nom_produit, description_produit, prix_produit) values('Pekin', 'voyage 7 jours pour voir la grande muraille', 1350.00);

insert into produits(nom_produit, description_produit, prix_produit, quantite_produit, selectionne_produit, image_produit ) values (); 

update produits set nom_produit='' , description_produit='', prix_produit=0.0 , 
quantite_produit= 0, selectionne_produit=0, image_produit=0 
where id_produit=1 ; 

delete from produits where id_produit = 3; 

select * from produits where nom_produit like '%paris%' or description_produit like '%paris%';

select * from produits where selectionne_produit =0; 

select * from produits_categories where categorie_id=1 and produit_id=4;


insert into categories( nom_categorie, description_categorie)values ( 'france', 'voyage en france metropolitaine');
insert into categories( nom_categorie, description_categorie)values ( 'etranger', 'voyage hors de france metropolitaine');
insert into categories( nom_categorie, description_categorie)values ( 'ville', 'voyage dans les grandes metropoles');


insert into produits_categories(categorie_id, produit_id) values(1, 4);
insert into produits_categories(categorie_id, produit_id) values(2, 5);
insert into produits_categories(categorie_id, produit_id) values(3, 4);
insert into produits_categories(categorie_id, produit_id) values(2, 6);
insert into produits_categories(categorie_id, produit_id) values(3, 5);

delete from produits_categories where categorie_id = 3 and produit_id = 5; 


update produits_categories set categorie_id = 3, produit_id =4 where categorie_id=1 and produit_id = 5;

/* ------ TEST récup données produit avec les catégoties associées  -----------*/ 

/* ------
		> 'Paris', 'voyage 2 jours dans la capitale francaise', 350.00
        avec catégorie 
        > france', 'voyage en france metropolitaine'
        ET
        > 'ville', 'voyage dans les grandes metropoles'


   -----------*/ 

	create view produit_categ1 as
	select id_produit,nom_produit, description_produit, prix_produit, quantite_produit, selectionne_produit, image_produit, categorie_id 
	from produits p 
	left join produits_categories pc
	on p.id_produit = pc.produit_id;
    
    drop view produit_categ1;
    
    select * from produit_categ1;
    
    select * 
    from produit_categ 
    where categorie_id = 2;
    
    
	select nom_produit, description_produit, prix_produit, nom_categorie, description_categorie
	from produit_categ p_cat
	left join categories c
	on c.id_categorie = p_cat.categorie_id = 1
    order by nom_produit;
    
	
    
    
SELECT max(id_commande) FROM commandes;



	create view ligneCom_produit  as
	select *
	from produits p
	left join lignes_commandes lc
	on p.id_produit = lc.produit_id;


	create view commande_client  as
	select *
	from commandes c
	left join ligneCom_produit viewLigneComProduit
	on viewLigneComProduit.commande_id = c.id_commande;
    
    drop view produit_categ1;
    
    select * from commande_client where id_commande = 6;
    
    
    create view vieu_commande_client_clients  as
	select *
	from clients clt
	left join commande_client 
	on commande_client.client_id = clt.id_client;
    
    select * from vieu_commande_client_clients where id_client = 1 and id_commande= 6;
    
    
    
    select *
	from clients clt
	left join commandes c
	on c.client_id = clt.id_client
    where id_client = 1;
    
    
	select *
	from commandes
    where client_id=1;
    
    
	select *
    from lignes_commandes;
    
    select * from lignes_commandes where commande_id=6;
    
    
    truncate table commandes;
	truncate table produits_categories;
    
