
/* ------ Manip des données ----*/

/* ------- SELECT ALL FROM -------- */

select * from categories;

select * from clients;

select * from commandes;

select * from lignes_commandes;

select * from produits;

select * from produits_categories;

select * from users;



/* ------ INSERT INTO  -----------*/ 

insert into users(id_user, mot_de_passe_user, nom_user) values ('admin', 'admin', 'Admin');


insert into clients(identifiant_client, mot_de_passe_client, nom_client, prenom_client, adresse_client, email_client, tel_client) values('123', '123', 'Leseur', 'Anais', 'Lachassagne', 'anais@mail.com','09875432');


insert into commandes(date_commande, client_id) values('2020-07-05', 1);

insert into produits(nom_produit, description_produit, prix_produit) values('Paris', 'voyage 2 jours dans la capitale francaise', 350.00);
insert into produits(nom_produit, description_produit, prix_produit) values('Chine', 'voyage 15 jours pour voir la grande muraille', 2350.00);

insert into produits(nom_produit, description_produit, prix_produit, quantite_produit, selectionne_produit, image_produit ) values (); 

update produits set nom_produit='' , description_produit='', prix_produit=0.0 , 
quantite_produit= 0, selectionne_produit=0, image_produit=0 
where id_produit=1 ; 

delete from produits where id_produit = 1; 

select * from produits where nom_produit like '%paris%' or description_produit like '%paris%';

select * from produits where selectionne_produit =0; 




insert into categories(id_categorie, nom_categorie, description_categorie)values (1, 'france', 'voyage en france metropolitaine');
insert into categories(id_categorie, nom_categorie, description_categorie)values (2, 'etranger', 'voyage hors de france metropolitaine');
insert into categories(id_categorie, nom_categorie, description_categorie)values (3, 'ville', 'voyage dans les grandes metropoles');


insert into produits_categories(categorie_id, produit_id) values(1, 1);
insert into produits_categories(categorie_id, produit_id) values(2, 3);
insert into produits_categories(categorie_id, produit_id) values(3, 1);



/* ------ TEST récup données produit avec les catégoties associées  -----------*/ 

/* ------
		> 'Paris', 'voyage 2 jours dans la capitale francaise', 350.00
        avec catégorie 
        > france', 'voyage en france metropolitaine'
        ET
        > 'ville', 'voyage dans les grandes metropoles'


   -----------*/ 

	create view produit_categ as
	select nom_produit, description_produit, prix_produit, categorie_id
	from produits p 
	left join produits_categories pc
	on p.id_produit = pc.produit_id;
    
    select * from produit_categ;
    
    select * 
    from produit_categ 
    where categorie_id = 2;
    
    
    	select nom_produit, description_produit, prix_produit, nom_categorie, description_categorie
	from produit_categ p_cat
	left join categories c
	on c.id_categorie = p_cat.categorie_id = 1
    order by nom_produit;
    
	
    
    
    
