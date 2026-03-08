package com.workflow.projet.dto.vehicule;

public class VehiculeDTO {

    private int idVehicule;
    private String model;
    private int nbPlace;
    private int idCarburant;
    private String nomCarburant;

    public VehiculeDTO() {}

    public VehiculeDTO(int idVehicule, String model, int nbPlace, int idCarburant, String nomCarburant) {
        this.idVehicule = idVehicule;
        this.model = model;
        this.nbPlace = nbPlace;
        this.idCarburant = idCarburant;
        this.nomCarburant = nomCarburant;
    }

    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }

    public int getIdCarburant() {
        return idCarburant;
    }

    public void setIdCarburant(int idCarburant) {
        this.idCarburant = idCarburant;
    }

    public String getNomCarburant() {
        return nomCarburant;
    }

    public void setNomCarburant(String nomCarburant) {
        this.nomCarburant = nomCarburant;
    }
}