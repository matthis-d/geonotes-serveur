# Réseaux Middlewares

Ce rapport présente comment nous avons mis en place notre architecture côté serveur pour le projet et l'application Géonotes.

## Version de Java
Pour ce projet, nous avons choisi d'utiliser la dernière version de Java sortie (Java 7). Si vous lancez le projet sous Java 6, il est possible que des erreurs apparaissent en raison de l'utilisation de la notation <> lorsque la création de liste est implicite : `Set<Note> notes = new HashSet<>();` par exemple. 

## Configuration de Glassfish
Tout d'abord, il nous a fallu installer Glassfish sur notre machine et configurer le serveur afin que celui-ci stocke nos données. 

Concernant l'installation, il faut dézipper le fichier fourni par Glassfish, le placer dans le dossier de notre choix et, dans Eclipse, ajouter ce serveur. 

Ensuite, pour ce qui est de la connexion avec la base de données, il faut d'abord lancer le serveur. Ensuite, on se rend sur l'adresse de configuration ([http://locahost:4848](http://locahost:4848)) et on va dans l'onglet JDBC/JDBC Connection Pool. On crée un nouveau pool de connexion qui va nous permettre de faire communiquer le serveur avec notre base de données, ici une base MySQL. (Comme nous tournons sous MySQL, il faut penser à ajouter le driver de connexion dans les fichiers de Glassfish (`path/to/Glassfish/glassfish/modules`) )

![image](images/new_pool_connection.png)

On passe à l'étape suivante et on se rend en bas de la page afin d'indiquer toutes les ressources nécessaires pour se connecter à notre base de données (Nom d'utilisateur, mot de passe, nom du serveur, nom de la base de données et numéro du port)

![image](images/new_pool_connection_2.png)

Une fois cette configuration faite, on peut cliquer sur "Finish" et tester la connexion en cliquant sur "Ping" lorsque l'on affiche le pool de connexion: 

![image](images/test_pool_connection.png)

Une fois ce pool de connexion créé, il faut créer une ressource JDBC. Pour cela, on se rend dans l'onglet JDBC/resources et on ajoute une nouvelle ressource. Par convention, on la nomme `jdbc/geonotes` et on indique que le pool de connexion est celui que l'on vient de créer (Geonotes).

![image](images/new_resources.png)

Ainsi, notre serveur Glassfish est configuré pour se connecter à notre base de données. 

## Architecture des fichiers 

Pour ce projet, nous avons choisi d'utiliser Maven qui est un gestionnaire de dépendances et qui va nous permettre également de déployer correctement nos ressources sous Glassfish. En effet, ce serveur nécessite qu'on déploie un fichier EAR qui va contenir des fichiers EJB et des fichiers WAR. Ainsi, notre projet va être constitué de deux parties majeures : l'archive EJB qui va contenir toutes nos classes d'accès aux données et de traitements liés et l'archive WAR qui contient nos fichiers pour la partie Web service. 

Les dépendances Maven et l'architecture du projet se configurent depuis le fichier pom.xml qui se trouve à la racine du projet pour chaque partie. Nous aurons donc trois fichiers pom.xml à écrire : un pour l'EAR, un pour l'EJB et un pour le WAR. Vous trouverez ces fichiers dans le projet rendu avec ce rapport. 

Pour les EJB, nous avons choisis de faire 2 classes, une pour récupérer les parcours et une autre pour gérer l'accès à la table des notes. Nous avons également créé deux classes pour représenter les données (parcours et notes). 

## Entités

Dans le projet de l'application Géonotes, il nous est demandé de créer des notes et des parcours, un parcours possédant plusieurs notes. Nous avons naturellement choisi de créer une entité appelée Note et une autre Track. 

Le fichier de configuration de JPA (persistance.xml) fait en sorte que les tables sont automatiquement créées en base de données si elles n'existent pas mais ne les modifient pas si elles existent déjà.

Ces entités sont situées dans le package `com.ducloslaurent.ejb.domain` de la partie EJB. 

#### L'entité Note
Cette entité possède pour attributs un entier qui sert d'identifiant (celui-ci est généré automatiquement), un nom, une catégorie, une description, une date d'ajout et une date de modification . Elle possède également un attribut de type ManyToOne vers l'entité parcours. Nous avons choisi ce sens en raison de difficultés rencontrées dans le sens OneToMany depuis la classe Track. 

#### L'entité Track
Cette entité représente les parcours et possède pour attributs un identifiant généré automatiquement lors de l'ajout en base de donnée, un nom, une catégorie, une description, une distance, une date d'ajout et une date de modification. 

## Beans
Comme nous possédions deux entités, nous avons choisi de créer 2 beans, chacun associé à une entité. 

Nous avons créé deux interfaces de type `Local` pour les beans (`LocalNoteBean` et `LocalTrackBean`) et deux classes d'implémentation de type `Stateless` (`NoteBean`et `TrackBean`). 

Ces beans permettent de faire des requêtes sur la base de données. Nous pouvons ainsi récupérer tous les parcours ou toutes les notes, un parcours ou une note en connaissant son id, faire des ajouts, des mises à jour ou encore des suppressions. Il est également possible de récupérer toutes les notes depuis l'identifiant d'un parcours, de supprimer des notes associées à un parcours ou bien d'en ajouter à un parcours. 

Les EJB ainsi créés sont utilisés par le service web qui se retrouve dans le WAR déployé sur le serveur. 

## Problèmes rencontrés
Nous n'avons pas réalisé de screencast de notre application car nous n'avons pas réussi à lier la partie client avec le serveur à travers des services web. Toutefois, il est possible de tester l'application à l'aide d'un plugin sur Google Chrome que vous pouvez retrouver à cette [adresse](https://chrome.google.com/webstore/detail/simple-rest-client/fhjcajmcbmldlhcimfajhfbgofnpcjmb?hl=fr). 

Et ainsi, il est possible de faire des tests sur notre serveur : 

Récupération de toutes les notes :

![image](images/test-rest.png)

Ajout d'une note : 

![image](images/post-en-rest.png)

Et en base de donnée, nous retrouvons bien la note ajoutée : 

![image](images/update-bdd.png)

Les adresses rest pour les notes sont les suivantes : 

- GET : http://localhost:8080/war/rest/note : toutes les notes
- POST : http://localhost:8080/war/rest/note : ajout d'une note 
- PUT : http://localhost:8080/war/rest/note/{id} : modification de la note ayant pour identifiant {id}
- DELETE http://localhost:8080/war/rest/note/{id} : suppression de la note ayant pour identifiant {id}

Nous retrouvons exactement les même chemins pour les parcours à part qu'il faut remplacer note par track. Nous avons également ajouté deux chemins supplémentaires qui sont : 

- POST : http://localhost:8080/war/rest/track/add/{id}/notes : ajoute des notes au parcours ayant l'identifiant {id}. Le post doit avoir en corps un object json de la forme : `{"note": [{"name": "note1" ...}, {"name": "note2" ... }]}` .
-  POST : http://localhost:8080/war/rest/track/remove/{id}/notes : supprime des notes au parcours ayant l'identifiant {id}. Le post doit avoir en corps un object json de la forme vue précédemment.