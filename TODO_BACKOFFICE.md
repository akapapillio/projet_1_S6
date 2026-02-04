# Plan d'intégration Back Office - TODO

## Étape 1 : Modifier pom.xml ✅
- [x] 1.1 Ajouter dépendance PostgreSQL (42.7.1)

## Étape 2 : Créer la structure de packages ✅
- [x] 2.1 Créer com/workflow/projet/
- [x] 2.2 Créer com/workflow/projet/config/
- [x] 2.3 Créer com/workflow/projet/controller/
- [x] 2.4 Créer com/workflow/projet/model/
- [x] 2.5 Créer com/workflow/projet/repository/

## Étape 3 : Copier et adapter les fichiers du Back Office ✅
- [x] 3.1 DatabaseConnection.java
- [x] 3.2 Test.java (Model)
- [x] 3.3 TestRepository.java
- [x] 3.4 TestController.java (adapté pour Spring Boot)

## Étape 4 : Ajouter les ressources ✅
- [x] 4.1 script.sql dans src/main/resources/

## Étape 5 : Tester
- [ ] 5.1 Compiler le projet : `./mvnw clean compile`
- [ ] 5.2 Lancer l'application : `./mvnw spring-boot:run`
- [ ] 5.3 Tester endpoint : GET http://localhost:8080/api/tests

