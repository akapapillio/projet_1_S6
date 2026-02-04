# projet_1_S6 - Réservation Voiture

git + deploy workflow

## Back Office API REST

API REST pour exposer les données de la base de données PostgreSQL, utilisant **Spring Boot**.

## Prérequis

- **Java JDK 17** ou supérieur
- **Spring Boot 4.0.2**
- **PostgreSQL 12** ou supérieur

## Dépendances (Maven)

| Dépendance | Version | Description |
|------------|---------|-------------|
| `spring-boot-starter-webmvc` | 4.0.2 | Framework Spring MVC |
| `postgresql` | 42.7.1 | Driver JDBC PostgreSQL |

---

## Guide des Annotations Spring

### Contrôleurs

| Annotation | Description |
|------------|-------------|
| `@Controller` | Marque une classe comme contrôleur (retourne des vues) |
| `@RestController` | Marque une classe comme contrôleur REST (retourne du JSON) |
| `@CrossOrigin(origins = "...")` | Active CORS pour le contrôleur |

### Mappings HTTP

| Annotation | Description |
|------------|-------------|
| `@GetMapping("/path")` | Gère les requêtes GET |
| `@PostMapping("/path")` | Gère les requêtes POST |
| `@PutMapping("/path")` | Gère les requêtes PUT |
| `@DeleteMapping("/path")` | Gère les requêtes DELETE |
| `@RequestMapping("/path")` | Mapping générique |

### Paramètres

| Annotation | Description |
|------------|-------------|
| `@PathVariable("name")` | Extrait une variable du chemin URL |
| `@RequestParam("name")` | Extrait un paramètre de requête |
| `@RequestBody` | Désérialise le corps JSON en objet |
| `@RequestHeader("name")` | Extrait un header HTTP |

### Réponses

| Annotation | Description |
|------------|-------------|
| `@ResponseStatus(HttpStatus.OK)` | Définit le code HTTP de réponse |

### Classes utilitaires Spring

#### ResponseEntity
```java
// Réponse OK avec données
return ResponseEntity.ok(data);

// Réponse avec statut personnalisé
return ResponseEntity.status(HttpStatus.CREATED).body(data);

// Réponse sans contenu
return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
```

#### HttpStatus
```java
HttpStatus.OK                    // 200
HttpStatus.CREATED               // 201
HttpStatus.NO_CONTENT            // 204
HttpStatus.BAD_REQUEST           // 400
HttpStatus.UNAUTHORIZED          // 401
HttpStatus.FORBIDDEN             // 403
HttpStatus.NOT_FOUND             // 404
HttpStatus.INTERNAL_SERVER_ERROR // 500
```

### Exemple de contrôleur complet

```java
package com.workflow.projet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ExempleController {

    // GET /api/items
    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAll() {
        List<Item> items = repository.findAll();
        return ResponseEntity.ok(items);
    }

    // GET /api/items/5
    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getById(@PathVariable int id) {
        Item item = repository.findById(id);
        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(item);
    }

    // POST /api/items
    @PostMapping("/items")
    public ResponseEntity<Item> create(@RequestBody Item item) {
        Item created = repository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/items/5
    @PutMapping("/items/{id}")
    public ResponseEntity<Item> update(
            @PathVariable int id,
            @RequestBody Item item) {
        item.setId(id);
        Item updated = repository.update(item);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/items/5
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        repository.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
```

---

## Configuration du projet

### Base de données PostgreSQL

```sql
CREATE DATABASE projet_1_s6;
\c projet_1_s6;

CREATE TABLE test(
    id INT PRIMARY KEY,
    text TEXT NOT NULL,
    date DATE NOT NULL
);

INSERT INTO test VALUES (1, 'Hello world', '2026-01-30');
```

### Configuration de la connexion

Modifier le fichier `src/main/java/com/workflow/projet/config/DatabaseConnection.java` :

```java
private static final String URL = "jdbc:postgresql://localhost:5433/projet_1_s6";
private static final String USER = "votre_utilisateur";
private static final String PASSWORD = "votre_mot_de_passe";
```

---

## Compilation et Déploiement

### Lancer l'application Spring Boot

```bash
# Compiler et lancer
./mvnw spring-boot:run

# Ou créer le WAR
./mvnw clean package
java -jar target/test-0.0.1-SNAPSHOT.war
```

### Accès à l'application

L'API sera accessible à : **http://localhost:8080/api/tests**

---

## Endpoints API

| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/api/tests` | Liste tous les enregistrements |
| GET | `/api/tests/{id}` | Récupère un enregistrement par ID |

---

## Exemple de réponse

### GET /api/tests

```json
[
    {
        "id": 1,
        "text": "Hello world",
        "date": "2026-01-30"
    }
]
```

### GET /api/tests/1

```json
{
    "id": 1,
    "text": "Hello world",
    "date": "2026-01-30"
}
```

---

## Utilisation depuis le Frontend

```javascript
// Exemple avec fetch
fetch('http://localhost:8080/api/tests')
    .then(response => response.json())
    .then(data => console.log(data));

// Exemple avec axios
axios.get('http://localhost:8080/api/tests')
    .then(response => console.log(response.data));
```

---

## Structure du projet

```
projet_1_S6/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── workflow/
│       │           ├── projet/
│       │           │   ├── config/
│       │           │   │   └── DatabaseConnection.java
│       │           │   ├── controller/
│       │           │   │   └── TestController.java
│       │           │   ├── model/
│       │           │   │   └── Test.java
│       │           │   └── repository/
│       │           │       └── TestRepository.java
│       │           └── test/
│       │               ├── TestApplication.java
│       │               ├── HelloWorldController.java
│       │               └── ServletInitializer.java
│       └── resources/
│           ├── application.properties
│           ├── script.sql
│           ├── static/
│           └── templates/
├── pom.xml
├── mvnw
└── README.md
```

---

## CORS

L'API est configurée pour accepter les requêtes de n'importe quelle origine (`@CrossOrigin(origins = "*")`). En production, modifiez cette configuration pour n'accepter que les origines autorisées.

---

## Thème du projet : Réservation voiture

- **Back Office** : Framework personnalisé → Spring Boot ✓
- **Front Office** : À développer (Spring Boot + Frontend)

### Fonctionnalités prévues

- Gestion des rôles
- Base de données relationnelle PostgreSQL
- 2 projets distincts (B.O et F.O)
- Mapping URL : `/back` pour F.O
- Déploiement en ligne avec protection