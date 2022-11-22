# Dataobject
Ce programme permet de détecter les objets dans une image.

Les étapes ci-dessous sont à faire depuis le dossier racine du projet.

## Collaborateurs
Développeurs : Luca Coduri & Chloé Fontaine

Professeur : Nicolas Glassey

Assistant : Adrien Allemand

## Prérequis
Vous devez avoir au minimum Java8 et Maven installés sur votre machine.

## Récupérer les dépendances
> TODO (pas pénalisé) ajoutez des explications sur d'ou ça vient vu que c'est pas dans le lifecycle standard
```
mvn dependency:resolve
```

## Compilation
La commande ci-dessous permet de compiler le projet sans lancer les tests unitaires.
```bash
 mvn package -Dmaven.test.skip
```
## Run

> TODO (pas pénalisé) Vous avez mis le nom de votre bucket en dur du coup les tests et le run plantent quand c'est moi qui essaie de faire tourner avec mon propre S3. Prenez l'habitude de faire une variable d'environnement pour les connections aux resources externes (S3, DB, filesystem, external services adresses etc...) Pour le coup j'ai demandé à Nicolas de me donner les droits sur votre bucket mais c'est une exception.

Une fois le projet compilé il est possible de lancer le programme avec la commande suivante :
```bash
export AWS_ACCESS_KEY_ID=<aws_access_key>
export AWS_SECRET_ACCESS_KEY=<aws_secret_access_key>
java -jar target/dataobject-1.0-SNAPSHOT-jar-with-dependencies.jar <img_path>
```

N'oubliez pas de modifier la variable d'environement avec vos accès aws.

## Tests
> TODO je n'ai pas réussi a faire passer vos tests et ce n'était pas un simple problème de permissions c'était dans votre code (business logic) que j'avais des erreurs.

Nous utilisons Junit 5 pour les tests unitaires. Pour lancer les tests, il faut lancer la commande suivante :
```bash
export AWS_ACCESS_KEY_ID=<aws_access_key>
export AWS_SECRET_ACCESS_KEY=<aws_secret_access_key>
mvn test
```
N'oubliez pas de remplacer {ID} et {SECRET} par vos identifiants AWS.

## Structure du projet
```
.
├── docs // contient les documents du projet
└── src  
    ├── main.java.ch.amt.dataobject -> contient le code source
    │   └── aws -> contient les classes pour la connexion à AWS
    └── test.java.ch.amt.dataobject -> contient les tests unitaires
```

## Sur le VPS AWS
Le projet a déjà été pull sur le VPS AWS.
Rendez-vous dans `~/dataobject` et lancez les commandes détaillées au-dessus.

> Pas pénalisé mais essayez d'avoir une seule manière de nommer les branches 

> Pas pénalisé mais si vous mettez un slyum, ça serait apprécié d'inclure un export png ou pdf pour ceux qui n'ont pas slyum. Vous pouvez essayer plantuml aussi il est pas mal et il y a un client online.