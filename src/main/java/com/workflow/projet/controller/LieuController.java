package com.workflow.projet.controller;

import com.workflow.projet.dto.HotelDTO;
import com.workflow.projet.service.ApiService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LieuController {

    private ApiService apiService = new ApiService();

    @GetMapping("/lieu")
    public String showLieux(Model model) {

        List<HotelDTO> lieux = apiService.getAllHotels();

        model.addAttribute("lieux", lieux);

        return "lieu_liste";
    }

    @PostMapping("/hotels/save")
        public String saveHotel(HotelDTO hotel) {

            apiService.saveHotel(hotel);

            return "redirect:/lieu";
        }

        @PostMapping("/hotels/update")
        public String updateHotel(HotelDTO hotel) {

            apiService.updateHotel(hotel);

            return "redirect:/lieu";
        }
}