package com.workflow.projet.dto.vehicule;

public class ReservationVehiculeDTO {

    private int idReservationVehicule;
    private String dateHeureDepart;
    private String dateHeureRetour;
    private int idVehicule;
    private String modelVehicule;

    public ReservationVehiculeDTO() {}

    public ReservationVehiculeDTO(int idReservationVehicule, String dateHeureDepart, 
                                  String dateHeureRetour, int idVehicule, String modelVehicule) {
        this.idReservationVehicule = idReservationVehicule;
        this.dateHeureDepart = dateHeureDepart;
        this.dateHeureRetour = dateHeureRetour;
        this.idVehicule = idVehicule;
        this.modelVehicule = modelVehicule;
    }

    public int getIdReservationVehicule() {
        return idReservationVehicule;
    }

    public void setIdReservationVehicule(int idReservationVehicule) {
        this.idReservationVehicule = idReservationVehicule;
    }

    public String getDateHeureDepart() {
        return dateHeureDepart;
    }

    public void setDateHeureDepart(String dateHeureDepart) {
        this.dateHeureDepart = dateHeureDepart;
    }

    public String getDateHeureRetour() {
        return dateHeureRetour;
    }

    public void setDateHeureRetour(String dateHeureRetour) {
        this.dateHeureRetour = dateHeureRetour;
    }

    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    public String getModelVehicule() {
        return modelVehicule;
    }

    public void setModelVehicule(String modelVehicule) {
        this.modelVehicule = modelVehicule;
    }
}