package com.workflow.projet.dto;

public class HotelDTO {
    private int idHotel;
    private String nomHotel;
    private int idLieu;

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

    public int getIdLieu() {
        return idLieu;
    }

    // Setters
    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public void setNomHotel(String nomHotel) {
        this.nomHotel = nomHotel;
    }

    public void setIdLieu(int idLieu) {
        this.idLieu= idLieu;
    }
}

