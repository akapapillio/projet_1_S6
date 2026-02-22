package com.workflow.projet.controller;

import com.workflow.projet.dto.ClientDTO;
import com.workflow.projet.dto.HotelDTO;
import com.workflow.projet.dto.ReservationDTO;
import com.workflow.projet.service.ApiService;
import com.workflow.projet.exception.TokenException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReservationController {
    
    private ApiService apiService = new ApiService();

    /**
     * Page d'accès refusé
     */
    @GetMapping("/acces-refuse")
    public String showAccesRefuse(
            @RequestParam(value = "reason", required = false) String reason,
            Model model) {
        model.addAttribute("reason", reason != null ? reason : "Token invalide ou absent");
        return "acces_refuse";
    }

    /**
     * Affiche le formulaire de réservation avec token
     * URL: /{token}/reservation/formulaire
     */
    @GetMapping("/{token}/reservation/formulaire")
    public String showFormulaire(@PathVariable("token") String token, Model model) {
        try {
            apiService.setToken(token);
            
            // Récupérer les clients et hôtels depuis le BO
            List<ClientDTO> clients = apiService.getAllClients();
            List<HotelDTO> hotels = apiService.getAllHotels();
            
            model.addAttribute("clients", clients);
            model.addAttribute("hotels", hotels);
            model.addAttribute("reservation", new ReservationDTO());
            model.addAttribute("token", token);
            
            return "reservation_form";
        } catch (TokenException e) {
            return "redirect:/acces-refuse?reason=" + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erreur lors du chargement des données");
            model.addAttribute("token", token);
            return "reservation_form";
        }
    }

    /**
     * Traite la soumission du formulaire de réservation avec token
     * URL: /{token}/reservation/save
     */
    @PostMapping("/{token}/reservation/save")
    public String saveReservation(
            @PathVariable("token") String token,
            @RequestParam("idClient") int idClient,
            @RequestParam("idHotel") int idHotel,
            @RequestParam("nbPassager") int nbPassager,
            @RequestParam("dateHeureArrivee") String dateHeureArrivee,
            Model model) {
        try {
            apiService.setToken(token);
            
            // Créer l'objet ReservationDTO
            ReservationDTO reservation = new ReservationDTO();
            reservation.setIdClient(idClient);
            reservation.setIdHotel(idHotel);
            reservation.setNbPassager(nbPassager);
            reservation.setDateHeureArrivee(dateHeureArrivee);
            
            // Envoyer au BO
            apiService.createReservation(reservation);
            
            // Rediriger vers la liste avec le même token
            return "redirect:/" + token + "/reservation/liste";
        } catch (TokenException e) {
            return "redirect:/acces-refuse?reason=" + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erreur lors de la création de la réservation");
            model.addAttribute("token", token);
            return showFormulaire(token, model);
        }
    }

    /**
     * Affiche la liste des réservations avec token
     * URL: /{token}/reservation/liste
     */
    @GetMapping("/{token}/reservation/liste")
    public String showListe(
            @PathVariable("token") String token,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "heure", required = false) String heure,
            Model model) {
        try {
            apiService.setToken(token);
            
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
            model.addAttribute("token", token);
            
            return "reservation_liste";
        } catch (TokenException e) {
            return "redirect:/acces-refuse?reason=" + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erreur lors du chargement des réservations");
            model.addAttribute("reservations", List.of());
            model.addAttribute("token", token);
            return "reservation_liste";
        }
    }

    // ============ Endpoints sans token (redirige vers accès refusé) ============

    @GetMapping("/reservation/formulaire")
    public String showFormulaireNoToken() {
        return "redirect:/acces-refuse?reason=Token absent";
    }

    @GetMapping("/reservation/liste")
    public String showListeNoToken() {
        return "redirect:/acces-refuse?reason=Token absent";
    }
}

