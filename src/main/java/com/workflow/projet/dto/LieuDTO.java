package com.workflow.projet.dto;

public class LieuDTO {

    private int idLieu;
    private String code;
    private String libelle;

    public LieuDTO() {}

    public LieuDTO(int idLieu, String code, String libelle) {
        this.idLieu = idLieu;
        this.code = code;
        this.libelle = libelle;
    }

    public int getIdLieu() {
        return idLieu;
    }

    public void setIdLieu(int idLieu) {
        this.idLieu = idLieu;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}