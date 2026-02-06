package com.workflow.projet.dto;

public class HotelDTO {
    private int idHotel;
    private String nomHotel;

    public HotelDTO() {}

    public HotelDTO(int idHotel, String nomHotel) {
        this.idHotel = idHotel;
        this.nomHotel = nomHotel;
    }

    // Getters
    public int getIdHotel() {
        return idHotel;
    }

    public String getNomHotel() {
        return nomHotel;
    }

    // Setters
    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public void setNomHotel(String nomHotel) {
        this.nomHotel = nomHotel;
    }
}

