package com.workflow.projet.dto.vehicule;

public class PassagerDTO {
    private int idReservation;
    private int nbPassager;
    private String dateHeureArrivee;
    private String status;
    private int idHotel;
    private int idClient;
    private String libelleLieu;
    private String nomHotel;
private String nomClient;

    // toString for debug
    @Override
    public String toString() {
        return "PassagerDTO{" +
                "nomClient='" + nomClient + '\'' +
                ", libelleLieu='" + libelleLieu + '\'' +
                '}';
    }
    private int idLieu;

    public PassagerDTO() {}

    // Getters and Setters
    public int getIdReservation() { return idReservation; }
    public void setIdReservation(int idReservation) { this.idReservation = idReservation; }

    public int getNbPassager() { return nbPassager; }
    public void setNbPassager(int nbPassager) { this.nbPassager = nbPassager; }

    public String getDateHeureArrivee() { return dateHeureArrivee; }
    public void setDateHeureArrivee(String dateHeureArrivee) { this.dateHeureArrivee = dateHeureArrivee; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getIdHotel() { return idHotel; }
    public void setIdHotel(int idHotel) { this.idHotel = idHotel; }

    public int getIdClient() { return idClient; }
    public void setIdClient(int idClient) { this.idClient = idClient; }

    public String getLibelleLieu() { return libelleLieu; }
    public void setLibelleLieu(String libelleLieu) { this.libelleLieu = libelleLieu; }

    public String getNomHotel() { return nomHotel; }
    public void setNomHotel(String nomHotel) { this.nomHotel = nomHotel; }

    public String getNomClient() { return nomClient; }
    public void setNomClient(String nomClient) { this.nomClient = nomClient; }

    public int getIdLieu() { return idLieu; }
    public void setIdLieu(int idLieu) { this.idLieu = idLieu; }
}
