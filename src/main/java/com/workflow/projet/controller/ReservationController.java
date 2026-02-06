package com.workflow.projet.controller;

import com.workflow.projet.dto.ClientDTO;
import com.workflow.projet.dto.HotelDTO;
import com.workflow.projet.dto.ReservationDTO;
import com.workflow.projet.service.ApiService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReservationController {
    
    private ApiService apiService = new ApiService();

    /**
     * Affiche le formulaire de réservation avec les listes déroulantes
     */
    @GetMapping("/reservation/formulaire")
    public String showFormulaire(Model model) {
        try {
            // Récupérer les clients et hôtels depuis le BO
            List<ClientDTO> clients = apiService.getAllClients();
            List<HotelDTO> hotels = apiService.getAllHotels();
            
            model.addAttribute("clients", clients);
            model.addAttribute("hotels", hotels);
            model.addAttribute("reservation", new ReservationDTO());
            
            return "reservation_form";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erreur lors du chargement des données");
            return "reservation_form";
        }
    }

    /**
     * Traite la soumission du formulaire de réservation
     */
    @PostMapping("/reservation/save")
    public String saveReservation(
            @RequestParam("idClient") int idClient,
            @RequestParam("idHotel") int idHotel,
            @RequestParam("nbPassager") int nbPassager,
            @RequestParam("dateHeureArrivee") String dateHeureArrivee,
            Model model) {
        try {
            // Créer l'objet ReservationDTO
            ReservationDTO reservation = new ReservationDTO();
            reservation.setIdClient(idClient);
            reservation.setIdHotel(idHotel);
            reservation.setNbPassager(nbPassager);
            reservation.setDateHeureArrivee(dateHeureArrivee);
            
            // Envoyer au BO
            apiService.createReservation(reservation);
            
            // Rediriger vers la liste
            return "redirect:/reservation/liste";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erreur lors de la création de la réservation");
            return showFormulaire(model);
        }
    }

    /**
     * Affiche la liste des réservations avec filtres
     */
    @GetMapping("/reservation/liste")
    public String showListe(
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "heure", required = false) String heure,
            Model model) {
        try {
            List<ReservationDTO> reservations;
            
            // Filtrer par date si fourni
            if (date != null && !date.isEmpty()) {
                reservations = apiService.getReservationsByDate(date);
            } else {
                reservations = apiService.getAllReservations();
            }
            
            // Filtrer par heure si fourni
            if (heure != null && !heure.isEmpty()) {
                reservations = reservations.stream()
                    .filter(r -> r.getDateHeureArrivee() != null 
                        && r.getDateHeureArrivee().contains(heure))
                    .toList();
            }
            
            model.addAttribute("reservations", reservations);
            model.addAttribute("date", date);
            model.addAttribute("heure", heure);
            
            return "reservation_liste";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erreur lors du chargement des réservations");
            model.addAttribute("reservations", List.of());
            return "reservation_liste";
        }
    }
}

