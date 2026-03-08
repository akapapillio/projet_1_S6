package com.workflow.projet.controller;

import com.workflow.projet.dto.AssignationDTO;
import com.workflow.projet.dto.ReservationDTO;
import com.workflow.projet.service.ApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AssignationController {

    private final ApiService apiService;

    public AssignationController(ApiService apiService) {
        this.apiService = apiService;
    }

@GetMapping("/assignation")
public String showAssignation(Model model) {

    List<ReservationDTO> reservations =
            apiService.getReservationsNonAssignees();

    model.addAttribute("reservations", reservations);

    return "vehicule/assignation";
}

@PostMapping("/assignation/lancer")
public String lancerAssignation(Model model) {

    List<ReservationDTO> reservations =
            apiService.getReservationsNonAssignees();

    List<AssignationDTO> assignations =
            apiService.lancerAssignation();

    model.addAttribute("reservations", reservations);
    model.addAttribute("assignations", assignations);

    return "vehicule/assignation";
}
}