# Sprint 1 - Front Office (FO)

## ✅ TÂCHES TERMINÉES

### TÂCHE 1: DTOs
- [x] `dto/ClientDTO.java` - idClient, nomClient
- [x] `dto/HotelDTO.java` - idHotel, nomHotel
- [x] `dto/ReservationDTO.java` - idReservation, idClient, nomClient, nbPassager, dateHeureArrivee, idHotel, nomHotel

### TÂCHE 2: ApiService.java
- [x] Service pour appels API vers BO
- [x] Mode mock avec données simulées
- [x] Chargement depuis fichiers JSON

### TÂCHE 3: ReservationController.java
- [x] GET `/reservation/formulaire` - Affiche formulaire avec listes déroulantes
- [x] POST `/reservation/save` - Sauvegarde la réservation
- [x] GET `/reservation/liste` - Affiche liste avec filtres date/heure

### TÂCHE 4: Templates HTML
- [x] `templates/reservation_form.html` - Formulaire de réservation
- [x] `templates/reservation_liste.html` - Liste des réservations avec filtres

### TÂCHE 5: Fichiers JSON (Mock Data)
- [x] `mock_data/clients.json` - 4 clients (Rasoaivo, Andriamamy, Ravaka, Tiana)
- [x] `mock_data/hotels.json` - 4 hôtels (Colbert, Carlton, Le Louvre, Ibis)
- [x] `mock_data/reservations.json` - 5 réservations

### TÂCHE 6: Configuration
- [x] pom.xml - Ajout Thymeleaf et Jackson

## 📁 Structure du FO

```
src/main/java/com/workflow/projet/
├── dto/
│   ├── ClientDTO.java
│   ├── HotelDTO.java
│   └── ReservationDTO.java
├── service/
│   └── ApiService.java
└── controller/
    └── ReservationController.java

src/main/resources/
├── mock_data/
│   ├── clients.json
│   ├── hotels.json
│   └── reservations.json
└── templates/
    ├── reservation_form.html
    └── reservation_liste.html
```

## 🔄 Pour activer le vrai BO
```java
apiService.setMockMode(false);  // Dans ApiService
```

## 📝 Prochaine étape
- Compiler et tester l'application

