package com.workflow.projet.service;

import com.workflow.projet.dto.ClientDTO;
import com.workflow.projet.dto.HotelDTO;
import com.workflow.projet.dto.ReservationDTO;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@Service
public class ApiService {
    
    private final String BASE_URL = "http://localhost:8080/projet_1_s6/api";
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Récupérer tous les clients
     */
    public List<ClientDTO> getAllClients() {
        String url = BASE_URL + "/clients";
        ResponseEntity<ClientDTO[]> response = restTemplate.getForEntity(url, ClientDTO[].class);
        return Arrays.asList(response.getBody());
    }

    /**
     * Récupérer tous les hôtels
     */
    public List<HotelDTO> getAllHotels() {
        String url = BASE_URL + "/hotels";
        ResponseEntity<HotelDTO[]> response = restTemplate.getForEntity(url, HotelDTO[].class);
        return Arrays.asList(response.getBody());
    }

    /**
     * Récupérer toutes les réservations
     */
    public List<ReservationDTO> getAllReservations() {
        String url = BASE_URL + "/reservations";
        ResponseEntity<ReservationDTO[]> response = restTemplate.getForEntity(url, ReservationDTO[].class);
        return Arrays.asList(response.getBody());
    }

    /**
     * Récupérer les réservations par date
     * @param date Format: YYYY-MM-DD
     */
    public List<ReservationDTO> getReservationsByDate(String date) {
        String url = BASE_URL + "/reservations/date/" + date;
        ResponseEntity<ReservationDTO[]> response = restTemplate.getForEntity(url, ReservationDTO[].class);
        return Arrays.asList(response.getBody());
    }

    /**
     * Créer une nouvelle réservation
     */
    public ReservationDTO createReservation(ReservationDTO reservation) {
        String url = BASE_URL + "/reservations";
        return restTemplate.postForObject(url, reservation, ReservationDTO.class);
    }
}

