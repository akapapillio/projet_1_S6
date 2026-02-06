package com.workflow.projet.dto;

public class ReservationDTO {
    private int idReservation;
    private int idClient;
    private String nomClient;
    private int nbPassager;
    private String dateHeureArrivee;
    private int idHotel;
    private String nomHotel;

    public ReservationDTO() {}

    public ReservationDTO(int idReservation, int idClient, String nomClient, 
                           int nbPassager, String dateHeureArrivee, 
                           int idHotel, String nomHotel) {
        this.idReservation = idReservation;
        this.idClient = idClient;
        this.nomClient = nomClient;
        this.nbPassager = nbPassager;
        this.dateHeureArrivee = dateHeureArrivee;
        this.idHotel = idHotel;
        this.nomHotel = nomHotel;
    }

    // Getters
    public int getIdReservation() {
        return idReservation;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public int getNbPassager() {
        return nbPassager;
    }

    public String getDateHeureArrivee() {
        return dateHeureArrivee;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public String getNomHotel() {
        return nomHotel;
    }

    // Setters
    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public void setNbPassager(int nbPassager) {
        this.nbPassager = nbPassager;
    }

    public void setDateHeureArrivee(String dateHeureArrivee) {
        this.dateHeureArrivee = dateHeureArrivee;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public void setNomHotel(String nomHotel) {
        this.nomHotel = nomHotel;
    }
}

