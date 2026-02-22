package com.workflow.projet.service;

import com.workflow.projet.dto.ClientDTO;
import com.workflow.projet.dto.HotelDTO;
import com.workflow.projet.dto.ReservationDTO;
import com.workflow.projet.exception.TokenException;
import com.workflow.projet.exception.TokenException.TokenError;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiService {
    
    // URL de base du BackOffice (sans token)
    private final String BASE_URL = "http://localhost:8080/projet_1_s6";
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();
    
    // Token courant pour les appels API
    private String currentToken;
    
    // Mode mock pour les tests (activer avec useMock=true)
    private boolean useMock = false;
    
    // Données simulées (chargées depuis fichier JSON)
    private List<ClientDTO> mockClients;
    private List<HotelDTO> mockHotels;
    private List<ReservationDTO> mockReservations;
    
    public ApiService() {
        loadMockData();
    }
    
    /**
     * Charger les données mock depuis les fichiers JSON
     */
    private void loadMockData() {
        try {
            // Charger clients.json
            ClassPathResource clientsResource = new ClassPathResource("mock_data/clients.json");
            mockClients = objectMapper.readValue(clientsResource.getInputStream(), 
                new TypeReference<List<ClientDTO>>() {});
            
            // Charger hotels.json
            ClassPathResource hotelsResource = new ClassPathResource("mock_data/hotels.json");
            mockHotels = objectMapper.readValue(hotelsResource.getInputStream(), 
                new TypeReference<List<HotelDTO>>() {});
            
            // Charger reservations.json
            ClassPathResource reservationsResource = new ClassPathResource("mock_data/reservations.json");
            mockReservations = objectMapper.readValue(reservationsResource.getInputStream(), 
                new TypeReference<List<ReservationDTO>>() {});
                
        } catch (IOException e) {
            e.printStackTrace();
            // Données par défaut si échec de chargement
            mockClients = Arrays.asList(
                new ClientDTO(1, "Rasoaivo"),
                new ClientDTO(2, "Andriamamy"),
                new ClientDTO(3, "Ravaka"),
                new ClientDTO(4, "Tiana")
            );
            mockHotels = Arrays.asList(
                new HotelDTO(1, "Hotel Colbert"),
                new HotelDTO(2, "Hotel Carlton"),
                new HotelDTO(3, "Hotel Le Louvre"),
                new HotelDTO(4, "Hotel Ibis")
            );
            mockReservations = new ArrayList<>();
        }
    }

    /**
     * Activer/désactiver le mode mock
     */
    public void setMockMode(boolean useMock) {
        this.useMock = useMock;
    }

    /**
     * Définir le token pour les appels API
     */
    public void setToken(String token) {
        this.currentToken = token;
    }

    /**
     * Obtenir le token courant
     */
    public String getToken() {
        return this.currentToken;
    }

    /**
     * Construire l'URL avec token
     * Format: http://localhost:8080/projet_1_s6/{token}/api/endpoint
     */
    private String buildUrlWithToken(String endpoint) {
        if (currentToken == null || currentToken.isEmpty()) {
            throw new TokenException(TokenError.TOKEN_ABSENT);
        }
        return BASE_URL + "/" + currentToken + "/api" + endpoint;
    }

    /**
     * Gérer les erreurs HTTP liées au token
     */
    private void handleHttpError(HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new TokenException(TokenError.TOKEN_ABSENT);
        } else if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
            // Vérifier le message d'erreur pour distinguer invalide/révoqué
            String body = e.getResponseBodyAsString();
            if (body != null && body.toLowerCase().contains("revoked")) {
                throw new TokenException(TokenError.TOKEN_REVOQUE);
            }
            throw new TokenException(TokenError.TOKEN_INVALIDE);
        }
        throw e;
    }

    /**
     * Récupérer tous les clients
     */
    public List<ClientDTO> getAllClients() {
        if (useMock) {
            return mockClients;
        }
        try {
            String url = buildUrlWithToken("/clients");
            ResponseEntity<ClientDTO[]> response = restTemplate.getForEntity(url, ClientDTO[].class);
            return Arrays.asList(response.getBody());
        } catch (HttpClientErrorException e) {
            handleHttpError(e);
            return List.of();
        }
    }

    /**
     * Récupérer tous les hôtels
     */
    public List<HotelDTO> getAllHotels() {
        if (useMock) {
            return mockHotels;
        }
        try {
            String url = buildUrlWithToken("/hotels");
            ResponseEntity<HotelDTO[]> response = restTemplate.getForEntity(url, HotelDTO[].class);
            return Arrays.asList(response.getBody());
        } catch (HttpClientErrorException e) {
            handleHttpError(e);
            return List.of();
        }
    }

    /**
     * Récupérer toutes les réservations
     */
    public List<ReservationDTO> getAllReservations() {
        if (useMock) {
            return mockReservations;
        }
        try {
            String url = buildUrlWithToken("/reservations");
            ResponseEntity<ReservationDTO[]> response = restTemplate.getForEntity(url, ReservationDTO[].class);
            return Arrays.asList(response.getBody());
        } catch (HttpClientErrorException e) {
            handleHttpError(e);
            return List.of();
        }
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
        try {
            String url = buildUrlWithToken("/reservations/date/" + date);
            ResponseEntity<ReservationDTO[]> response = restTemplate.getForEntity(url, ReservationDTO[].class);
            return Arrays.asList(response.getBody());
        } catch (HttpClientErrorException e) {
            handleHttpError(e);
            return List.of();
        }
    }

    /**
     * Créer une nouvelle réservation
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
        try {
            String url = buildUrlWithToken("/reservations");
            return restTemplate.postForObject(url, reservation, ReservationDTO.class);
        } catch (HttpClientErrorException e) {
            handleHttpError(e);
            return null;
        }
    }
}

