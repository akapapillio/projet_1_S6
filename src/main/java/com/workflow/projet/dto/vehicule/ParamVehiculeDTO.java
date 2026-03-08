package com.workflow.projet.dto.vehicule;

public class ParamVehiculeDTO {

    private int idParamVehicule;
    private int vitessMoyenne;
    private int tempsAttente;

    public ParamVehiculeDTO() {}

    public ParamVehiculeDTO(int idParamVehicule, int vitessMoyenne, int tempsAttente) {
        this.idParamVehicule = idParamVehicule;
        this.vitessMoyenne = vitessMoyenne;
        this.tempsAttente = tempsAttente;
    }

    public int getIdParamVehicule() {
        return idParamVehicule;
    }

    public void setIdParamVehicule(int idParamVehicule) {
        this.idParamVehicule = idParamVehicule;
    }

    public int getVitessMoyenne() {
        return vitessMoyenne;
    }

    public void setVitessMoyenne(int vitessMoyenne) {
        this.vitessMoyenne = vitessMoyenne;
    }

    public int getTempsAttente() {
        return tempsAttente;
    }

    public void setTempsAttente(int tempsAttente) {
        this.tempsAttente = tempsAttente;
    }
}