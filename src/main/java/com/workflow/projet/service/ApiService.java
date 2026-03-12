package com.workflow.projet.service;

import com.workflow.projet.dto.*;
import com.workflow.projet.dto.vehicule.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApiService {

    private final String BASE_URL = "http://localhost:8080/projet_1_s6/api";

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    private boolean useMock = true;

    // MOCK DATA
    private List<ClientDTO> mockClients;
    private List<HotelDTO> mockHotels;
    private List<ReservationDTO> mockReservations;
    private List<VehiculeDTO> mockVehicules;
    private ParamVehiculeDTO mockParamVehicule;
    private List<CarburantDTO> mockCarburants;
    private List<ReservationVehiculeDTO> mockReservationVehicules;

    public ApiService() {
        loadMockData();
    }

    /**
     * Chargement des données mock
     */
    private void loadMockData() {
        try {

            mockClients = readList("mock_data/clients.json", new TypeReference<List<ClientDTO>>() {});
            mockHotels = readList("mock_data/hotels.json", new TypeReference<List<HotelDTO>>() {});
            mockReservations = readList("mock_data/reservations.json", new TypeReference<List<ReservationDTO>>() {});
            mockVehicules = readList("mock_data/vehicules.json", new TypeReference<List<VehiculeDTO>>() {});

            ClassPathResource paramResource = new ClassPathResource("mock_data/paramVehicule.json");
            mockParamVehicule = objectMapper.readValue(paramResource.getInputStream(), ParamVehiculeDTO.class);

            mockReservationVehicules = readList("mock_data/reservationVehicules.json", new TypeReference<List<ReservationVehiculeDTO>>() {});

        } catch (Exception e) {
            e.printStackTrace();

            mockClients = Arrays.asList(
                    new ClientDTO(1, "Rasoaivo"),
                    new ClientDTO(2, "Andriamamy")
            );

            mockHotels = Arrays.asList(
                    new HotelDTO(1, "Hotel Colbert"),
                    new HotelDTO(2, "Hotel Carlton")
            );

            mockReservations = new ArrayList<>();
            mockVehicules = new ArrayList<>();

            mockParamVehicule = new ParamVehiculeDTO(1, 40, 10);
        }
    }

    private <T> List<T> readList(String path, TypeReference<List<T>> type) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return objectMapper.readValue(resource.getInputStream(), type);
    }

    public void setMockMode(boolean useMock) {
        this.useMock = useMock;
    }

    /**
     * CLIENTS
     */
    public List<ClientDTO> getAllClients() {

        if (useMock) return mockClients;

        String url = BASE_URL + "/clients";
        ResponseEntity<ClientDTO[]> response =
                restTemplate.getForEntity(url, ClientDTO[].class);

        return Arrays.asList(response.getBody());
    }

    /**
     * LIEUX (HOTELS)
     */
    public List<HotelDTO> getAllHotels() {

        if (useMock) return mockHotels;

        String url = BASE_URL + "/hotels";
        ResponseEntity<HotelDTO[]> response =
                restTemplate.getForEntity(url, HotelDTO[].class);

        return Arrays.asList(response.getBody());
    }

    public void saveHotel(HotelDTO hotel) {

        if (useMock) {
            mockHotels.add(hotel);
            return;
        }
    
        String url = BASE_URL + "/hotels";
    
        restTemplate.postForObject(url, hotel, HotelDTO.class);
    }
    public void updateHotel(HotelDTO hotel) {

        if (useMock) {
    
            mockHotels.stream()
                .filter(h -> h.getIdHotel() == hotel.getIdHotel())
                .forEach(h -> {
                    h.setIdLieu(hotel.getIdLieu());
                    h.setNomHotel(hotel.getNomHotel());
                });
    
            return;
        }
    
        String url = BASE_URL + "/hotels";
    
        restTemplate.put(url, hotel);
    }
    /**
     * VEHICULES
     */
    public List<VehiculeDTO> getAllVehicules() {

        if (useMock) return mockVehicules;

        String url = BASE_URL + "/vehicules";

        ResponseEntity<VehiculeDTO[]> response =
                restTemplate.getForEntity(url, VehiculeDTO[].class);

        return Arrays.asList(response.getBody());
    }
    public void saveVehicule(VehiculeDTO v) {
        if(useMock) {
            int newId = mockVehicules.size() + 1;
            v.setIdVehicule(newId);
            mockVehicules.add(v);
            return;
        }
        restTemplate.postForObject(BASE_URL + "/vehicules", v, VehiculeDTO.class);
    }
    
    public void updateVehicule(VehiculeDTO v) {
        if(useMock) {
            mockVehicules.stream()
                .filter(x -> x.getIdVehicule() == v.getIdVehicule())
                .findFirst()
                .ifPresent(x -> {
                    x.setModel(v.getModel());
                    x.setNbPlace(v.getNbPlace());
                    x.setIdCarburant(v.getIdCarburant());
                });
            return;
        }
        restTemplate.put(BASE_URL + "/vehicules", v);
    }

    /**
     * Récupérer la liste des carburants
     */
    public List<CarburantDTO> getCarburants() {

        if (useMock) {
            // Données mock
            if (mockCarburants == null) {
                mockCarburants = new ArrayList<>();
                mockCarburants.add(new CarburantDTO(1, "Essence"));
                mockCarburants.add(new CarburantDTO(2, "Diesel"));
                mockCarburants.add(new CarburantDTO(3, "Electrique"));
            }
            return mockCarburants;
        }

        // Appel API réel
        String url = BASE_URL + "/carburants";
        ResponseEntity<CarburantDTO[]> response = restTemplate.getForEntity(url, CarburantDTO[].class);
        return Arrays.asList(response.getBody());
    }

    /**
     * PARAM VEHICULE
     */
    public ParamVehiculeDTO getParamVehicule() {

        if (useMock) return mockParamVehicule;

        String url = BASE_URL + "/param_vehicule";

        return restTemplate.getForObject(url, ParamVehiculeDTO.class);
    }

    public void updateParamVehicule(ParamVehiculeDTO param) {
        if (useMock) {
            // Mettre à jour le mock
            mockParamVehicule.setVitessMoyenne(param.getVitessMoyenne());
            mockParamVehicule.setTempsAttente(param.getTempsAttente());
            return;
        }
    
        // Sinon, envoyer au backend réel
        String url = BASE_URL + "/param-vehicule";
        restTemplate.postForObject(url, param, ParamVehiculeDTO.class);
    }

    /**
     * RESERVATIONS
     */
    public List<ReservationDTO> getAllReservations() {

        if (useMock) return mockReservations;

        String url = BASE_URL + "/reservations";

        ResponseEntity<ReservationDTO[]> response =
                restTemplate.getForEntity(url, ReservationDTO[].class);

        return Arrays.asList(response.getBody());
    }

    /**
     * FILTRE DATE
     */
    public List<ReservationDTO> getReservationsByDate(String date) {

        if (useMock) {
            return mockReservations.stream()
                    .filter(r -> r.getDateHeureArrivee() != null &&
                            r.getDateHeureArrivee().startsWith(date))
                    .collect(Collectors.toList());
        }

        String url = BASE_URL + "/reservations/date/" + date;

        ResponseEntity<ReservationDTO[]> response =
                restTemplate.getForEntity(url, ReservationDTO[].class);

        return Arrays.asList(response.getBody());
    }

    /**
     * RESERVATIONS NON ASSIGNEES
     */
    public List<ReservationDTO> getReservationsNonAssignees() {

        if (useMock) {
            return mockReservations;
        }

        String url = BASE_URL + "/reservations/non-assignees";

        ResponseEntity<ReservationDTO[]> response =
                restTemplate.getForEntity(url, ReservationDTO[].class);

        return Arrays.asList(response.getBody());
    }

    /**
     * CREER RESERVATION
     */
    public ReservationDTO createReservation(ReservationDTO reservation) {

        if (useMock) {

            int newId = mockReservations.size() + 1;

            reservation.setIdReservation(newId);

            for (HotelDTO hotel : mockHotels) {
                if (hotel.getIdHotel() == reservation.getIdHotel()) {
                    reservation.setNomHotel(hotel.getNomHotel());
                }
            }

            for (ClientDTO client : mockClients) {
                if (client.getIdClient() == reservation.getIdClient()) {
                    reservation.setNomClient(client.getNomClient());
                }
            }

            List<ReservationDTO> updated = new ArrayList<>(mockReservations);
            updated.add(reservation);

            mockReservations = updated;

            return reservation;
        }

        String url = BASE_URL + "/reservations";

        return restTemplate.postForObject(url, reservation, ReservationDTO.class);
    }

        /**
     * Assignation
     */

    
    public List<AssignationDTO> getAssignations() {
    if (useMock) {
        List<AssignationDTO> assignations = new ArrayList<>();

        if (mockReservationVehicules == null || mockReservationVehicules.isEmpty()) {
            return new ArrayList<>();
        }

        for (ReservationVehiculeDTO rv : mockReservationVehicules) {
            AssignationDTO a = new AssignationDTO();
            a.setIdReservationVehicule(rv.getIdReservationVehicule());
            a.setNomVehicule(rv.getModelVehicule());
            a.setNbPlace(rv.getNbPlaceVehicule());
            a.setCarburant(rv.getNomCarburant());
            
            List<String> clients = rv.getPassagers().stream().map(PassagerDTO::getNomClient).collect(Collectors.toList());
            a.setClients(clients);
            
            List<String> lieux = rv.getPassagers().stream().map(PassagerDTO::getLibelleLieu).distinct().collect(Collectors.toList());
            a.setLieux(lieux);
            
            a.setDateHeureDepart(rv.getDateHeureDepart());
            a.setDateHeureRetour(rv.getDateHeureRetour());
            
            assignations.add(a);
        }

        return assignations;
    }

    String url = BASE_URL + "/assignations";
    ResponseEntity<AssignationDTO[]> response = restTemplate.getForEntity(url, AssignationDTO[].class);
    return Arrays.asList(response.getBody());
}

public List<AssignationDTO> lancerAssignation() {
    if (useMock) {
        // Ici on peut renvoyer le même résultat que getAssignations ou recalculer
        return getAssignations();
    }

    String url = BASE_URL + "/assignations/lancer";
    ResponseEntity<AssignationDTO[]> response = restTemplate.postForEntity(url, null, AssignationDTO[].class);
    return Arrays.asList(response.getBody());
}

}