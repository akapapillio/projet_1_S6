package com.workflow.projet.dto;

import java.util.List;

public class AssignationDTO {

    private int idReservationVehicule;
    private String nomVehicule;
    private int nbPlace;
    private String carburant;

    private List<String> clients; // liste des noms clients du groupe
    private List<String> lieux; // ordre des arrêts
    private String dateHeureDepart;
    private String dateHeureRetour;

    public AssignationDTO() {}

    // Getters et Setters
    public int getIdReservationVehicule() { return idReservationVehicule; }
    public void setIdReservationVehicule(int idReservationVehicule) { this.idReservationVehicule = idReservationVehicule; }

    public String getNomVehicule() { return nomVehicule; }
    public void setNomVehicule(String nomVehicule) { this.nomVehicule = nomVehicule; }

    public int getNbPlace() { return nbPlace; }
    public void setNbPlace(int nbPlace) { this.nbPlace = nbPlace; }

    public String getCarburant() { return carburant; }
    public void setCarburant(String carburant) { this.carburant = carburant; }

    public List<String> getClients() { return clients; }
    public void setClients(List<String> clients) { this.clients = clients; }

    public List<String> getLieux() { return lieux; }
    public void setLieux(List<String> lieux) { this.lieux = lieux; }

    public String getDateHeureDepart() { return dateHeureDepart; }
    public void setDateHeureDepart(String dateHeureDepart) { this.dateHeureDepart = dateHeureDepart; }

    public String getDateHeureRetour() { return dateHeureRetour; }
    public void setDateHeureRetour(String dateHeureRetour) { this.dateHeureRetour = dateHeureRetour; }
}