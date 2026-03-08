package com.workflow.projet.controller;

import com.workflow.projet.dto.vehicule.ParamVehiculeDTO;
import com.workflow.projet.service.ApiService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ParamVehiculeController {

    private ApiService apiService = new ApiService();

    @GetMapping("/param_vehicule")
    public String showParam(Model model) {

        ParamVehiculeDTO param = apiService.getParamVehicule();

        model.addAttribute("paramv", param);

        return "vehicule/param_vehicule";
    }
    
    @PostMapping("/param_vehicule/save")
    public String saveParamVehicule(ParamVehiculeDTO paramv, Model model) {
        apiService.updateParamVehicule(paramv);
        return "redirect:/param_vehicule"; 
    }
}