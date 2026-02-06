package com.workflow.projet.service;

import com.workflow.projet.dto.ClientDTO;
import com.workflow.projet.dto.HotelDTO;
import com.workflow.projet.dto.ReservationDTO;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiService {
    
    private final String BASE_URL = "http://localhost:8080/projet_1_s6/api";
    private RestTemplate restTemplate = new RestTemplate();
    
    // Mode mock pour les tests (activer avec useMock=true)
    private boolean useMock = true;
    
    // Données simulées pour les tests
    private List<ClientDTO> mockClients = Arrays.asList(
        new ClientDTO(1, "Rasoaivo"),
        new ClientDTO(2, "Andriamamy"),
        new ClientDTO(3, "Ravaka"),
        new ClientDTO(4, "Tiana")
    );
    
    private List<HotelDTO> mockHotels = Arrays.asList(
        new HotelDTO(1, "Hotel Colbert"),
        new HotelDTO(2, "Hotel Carlton"),
        new HotelDTO(3, "Hotel Le Louvre"),
        new HotelDTO(4, "Hotel Ibis")
    );
    
    private List<ReservationDTO> mockReservations = Arrays.asList(
        new ReservationDTO(1, 1, "Rasoaivo", 2, "2026-02-06 14:00:00", 1, "Hotel Colbert"),
        new ReservationDTO(2, 2, "Andriamamy", 3, "2026-02-06 10:30:00", 2, "Hotel Carlton"),
        new ReservationDTO(3, 3, "Ravaka", 1, "2026-02-07 16:00:00", 3, "Hotel Le Louvre"),
        new ReservationDTO(4, 4, "Tiana", 4, "2026-02-08 09:00:00", 4, "Hotel Ibis"),
        new ReservationDTO(5, 1, "Rasoaivo", 2, "2026-02-06 18:30:00", 1, "Hotel Colbert")
    );

    /**
     * Activer/désactiver le mode mock
     */
    public void setMockMode(boolean useMock) {
        this.useMock = useMock;
    }

    /**
     * Récupérer tous les clients
     */
    public List<ClientDTO> getAllClients() {
        if (useMock) {
            return mockClients;
        }
        String url = BASE_URL + "/clients";
        ResponseEntity<ClientDTO[]> response = restTemplate.getForEntity(url, ClientDTO[].class);
        return Arrays.asList(response.getBody());
    }

    /**
     * Récupérer tous les hôtels
     */
    public List<HotelDTO> getAllHotels() {
        if (useMock) {
            return mockHotels;
        }
        String url = BASE_URL + "/hotels";
        ResponseEntity<HotelDTO[]> response = restTemplate.getForEntity(url, HotelDTO[].class);
        return Arrays.asList(response.getBody());
    }

    /**
     * Récupérer toutes les réservations
     */
    public List<ReservationDTO> getAllReservations() {
        if (useMock) {
            return mockReservations;
        }
        String url = BASE_URL + "/reservations";
        ResponseEntity<ReservationDTO[]> response = restTemplate.getForEntity(url, ReservationDTO[].class);
        return Arrays.asList(response.getBody());
    }

    /**
     * Récupérer les réservations par date
     * @param date Format: YYYY-MM-DD
     */
    public List<ReservationDTO> getReservationsByDate(String date) {
        if (useMock) {
            return mockReservations.stream()
                .filter(r -> r.getDateHeureArrivee() != null 
                    && r.getDateHeureArrivee().startsWith(date))
                .collect(Collectors.toList());
        }
        String url = BASE_URL + "/reservations/date/" + date;
        ResponseEntity<ReservationDTO[]> response = restTemplate.getForEntity(url, ReservationDTO[].class);
        return Arrays.asList(response.getBody());
    }

    /**
     * Créer une nouvelle réservation (mock: ajoute à la liste locale)
     */
    public ReservationDTO createReservation(ReservationDTO reservation) {
        if (useMock) {
            // Simuler la création
            int newId = mockReservations.size() + 1;
            reservation.setIdReservation(newId);
            
            // Ajouter le nom de l'hôtel
            for (HotelDTO hotel : mockHotels) {
                if (hotel.getIdHotel() == reservation.getIdHotel()) {
                    reservation.setNomHotel(hotel.getNomHotel());
                    break;
                }
            }
            
            // Ajouter le nom du client
            for (ClientDTO client : mockClients) {
                if (client.getIdClient() == reservation.getIdClient()) {
                    reservation.setNomClient(client.getNomClient());
                    break;
                }
            }
            
            // Ajouter à la liste mock
            List<ReservationDTO> updatedList = new java.util.ArrayList<>(mockReservations);
            updatedList.add(reservation);
            mockReservations = updatedList;
            
            return reservation;
        }
        String url = BASE_URL + "/reservations";
        return restTemplate.postForObject(url, reservation, ReservationDTO.class);
    }
}

