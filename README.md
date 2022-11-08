# dataobject

## Compilation

## Tests
Nous utilisons Junit 5 pour les tests unitaires. Pour lancer les tests, il faut lancer la commande suivante :
```bash
mvn dependency:resolve
export AWS_ACCESS_KEY_ID=<aws_access_key>
export AWS_SECRET_ACCESS_KEY=<aws_secret_access_key>
mvn test
```
N'oubliez pas de remplacer {ID} et {SECRET} par vos identifiants AWS.

## Structure du projet
```
.
├── docs // contient les documents du projet
└── src  // contient le code source
    ├── main.java.ch.amt.dataobject
    │   └── aws // contient les classes pour la connexion à AWS
    └── test.java.ch.amt.dataobject // contient les tests unitaires
```
