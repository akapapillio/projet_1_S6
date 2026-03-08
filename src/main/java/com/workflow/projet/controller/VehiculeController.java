package com.workflow.projet.controller;

import com.workflow.projet.dto.vehicule.VehiculeDTO;
import com.workflow.projet.service.ApiService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class VehiculeController {

    private ApiService apiService = new ApiService();

@GetMapping("/vehicules")
public String showVehicules(Model model) {
    model.addAttribute("vehicules", apiService.getAllVehicules());
    model.addAttribute("carburants", apiService.getCarburants());
    return "vehicule/vehicule_liste";
}

@PostMapping("/vehicules/save")
public String saveVehicule(VehiculeDTO vehicule) {
    apiService.saveVehicule(vehicule);
    return "redirect:/vehicules";
}

@PostMapping("/vehicules/update")
public String updateVehicule(VehiculeDTO vehicule) {
    apiService.updateVehicule(vehicule);
    return "redirect:/vehicules";
}
}