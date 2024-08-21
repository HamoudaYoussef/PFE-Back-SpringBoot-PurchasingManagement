package biz.picosoft.demo.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitOffertDTO implements Serializable {

    private Long id;

    private String nom;

    private String description;

    private Long quantite;

    public Long getPrix() {
        return prix;
    }

    public void setPrix(Long prix) {
        this.prix = prix;
    }

    private Long prix;

    private Long fournisseurId;

    private Long offreId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Long getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public Long getOffreId() {
        return offreId;
    }

    public void setOffreId(Long offreId) {
        this.offreId = offreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitOffertDTO)) {
            return false;
        }

        ProduitOffertDTO produitOffertDTO = (ProduitOffertDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, produitOffertDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitOffertDTO{" +
            "id=" + getId() +
            ", nomProduit='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", quantite=" + getQuantite() +
            ", fournisseurId=" + getFournisseurId() +
            ", offreId=" + getOffreId() +
            "}";
    }
}
