# dataobject

## Tests
Nous utilisons Junit 5 pour les tests unitaires. Pour lancer les tests, il faut lancer la commande suivante :
```bash
mvn -AWS_ACCESS_KEY_ID={ID} -AWS_SECRET_ACCESS_KEY={SECRET} test
```

## Structure du projet
```
.
├── docs // contient les documents du projet
└── src  // contient le code source
    ├── main.java.ch.amt.dataobject
    │   └── aws // contient les classes pour la connexion à AWS
    └── test.java.ch.amt.dataobject // contient les tests unitaires
```