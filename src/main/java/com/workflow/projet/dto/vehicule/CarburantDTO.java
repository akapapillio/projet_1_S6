package com.workflow.projet.dto.vehicule;

public class CarburantDTO {

    private int idCarburant;
    private String nomCarburant;

    public CarburantDTO() {}

    public CarburantDTO(int idCarburant, String nomCarburant) {
        this.idCarburant = idCarburant;
        this.nomCarburant = nomCarburant;
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