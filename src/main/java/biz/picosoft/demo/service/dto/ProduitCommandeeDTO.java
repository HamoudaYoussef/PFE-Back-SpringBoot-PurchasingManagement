package biz.picosoft.demo.service.dto;

import java.io.Serializable;

public class ProduitCommandeeDTO implements Serializable {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }


    private String nom;
    private Long quantite;
    private String description;

    private Long produitId;

    public Long getDemandeDevisId() {
        return demandeDevisId;
    }

    public void setDemandeDevisId(Long demandeDevisId) {
        this.demandeDevisId = demandeDevisId;
    }

    private Long demandeDevisId;
}
